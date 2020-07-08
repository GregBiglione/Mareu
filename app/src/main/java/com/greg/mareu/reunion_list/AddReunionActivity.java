package com.greg.mareu.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.greg.mareu.R;
import com.greg.mareu.di.DI;
import com.greg.mareu.dialog_box.ParticipantsCheckBox;
import com.greg.mareu.dialog_box.WarningDialog;
import com.greg.mareu.events.SetParticipantEvent;
import com.greg.mareu.model.Reunion;
import com.greg.mareu.picker.Pick;
import com.greg.mareu.service.ReunionApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class AddReunionActivity extends AppCompatActivity
{
    @BindView(R.id.addImage) CircleImageView mImage;
    @BindView(R.id.addAboutLyt) TextInputLayout mAboutInput;
    @BindView(R.id.addAbout) TextInputEditText mAboutEditText;

    @BindView(R.id.addDayLyt) TextInputLayout mDayInput;
    @BindView(R.id.addDateEdit) TextInputEditText mDayEditText;

    @BindView(R.id.addStartHourLyt) TextInputLayout mStartInput;
    @BindView(R.id.addStartTimeEdit) TextInputEditText mStartHourEditText;
    @BindView(R.id.addEndHourLyt) TextInputLayout mEndInput;
    @BindView(R.id.addEndTimeEdit) TextInputEditText mEndHourEditText;

    @BindView(R.id.spinnerRoom) Spinner mSpinner;

    @BindView(R.id.addParticipantsLyt) TextInputLayout mParticipantsInput;
    @BindView(R.id.addParticipantsEdit) TextInputEditText mParticipantsEditText;
    @BindView(R.id.create) Button mAddButton;

    private String mRandomImage;
    private ReunionApiService mApiService;
    private Pick mPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        mApiService = DI.getReunionApiService();
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAboutEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(!hasFocus) {
                    imm.hideSoftInputFromWindow(mAboutEditText.getWindowToken(), 0);
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        dayAndHour();

       mParticipantsEditText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               OpenSetParticipantsChecked();
           }
       });
       mParticipantsEditText.setKeyListener(null);
       mApiService = DI.getReunionApiService();
       init();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSetParticipants(SetParticipantEvent event){
        mParticipantsEditText.setText(event.item);
    }

    public void OpenSetParticipantsChecked(){
        ParticipantsCheckBox participantsCheckBox = new ParticipantsCheckBox();
        participantsCheckBox.show(getSupportFragmentManager(), "participants check box");
    }

    public void init() {
        mRandomImage = randomImage();
        Glide.with(this).load(mRandomImage).into(mImage);
        mAboutInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) { mAddButton.setEnabled(s.length() > 0);}
        });
    }

    @OnClick(R.id.create)
    void addReunion(View view){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date reunionDate = null;
        try {
            reunionDate = simpleDateFormat.parse(mDayInput.getEditText().getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String startHourSelected = mStartInput.getEditText().getText().toString().trim();
        String endHourSelected = mEndInput.getEditText().getText().toString().trim();
        String selectedRoom =  mSpinner.getSelectedItem().toString().trim();

        boolean isValid = true;
        if (isValid()){
            if (mApiService.checkMatches(selectedRoom, reunionDate, startHourSelected, endHourSelected)) {
                WarningDialog warningDialog = new WarningDialog();
                warningDialog.show(getSupportFragmentManager(), "warning box");
            }
            else {
                if (isValid) {
                    Reunion reunion = new Reunion(
                            System.currentTimeMillis(),
                            mRandomImage,
                            mAboutInput.getEditText().getText().toString().trim(),
                            reunionDate,
                            startHourSelected ,
                            endHourSelected,
                            selectedRoom,
                            mParticipantsInput.getEditText().getText().toString().trim()
                    );
                    mApiService.createReunion(reunion);
                    Toasty.success(this, "Réunion enregistrée", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    /**
     * Generate a random image
     * @return String
     */

    String randomImage() { return "https://placeimg.com/640/480/any" + System.currentTimeMillis(); }

    /**
     * Used to navigate to this AddReunionActivity
     * @param listReunionActivity
     */
    public static void navigate(ListReunionActivity listReunionActivity) {
        Intent i = new Intent(listReunionActivity, AddReunionActivity.class);
        ActivityCompat.startActivity(listReunionActivity, i, null);
    }

    public boolean isValid(){
        boolean isValid = true;
        if (mAboutEditText.length() < 3 || mAboutEditText.length() > 30)
        {
            mAboutEditText.setError("Entrer un titre (3-30 caratères)");
            mAboutEditText.requestFocus();
            isValid = false;
        }
        if (mDayEditText.length() == 0)
        {
            mDayEditText.setError("Sélectionner une date");
            mDayEditText.requestFocus();
            isValid = false;
        }
        if (mStartHourEditText.length() == 0)
        {
            mStartHourEditText.setError("Sélectionner une heure de début");
            mStartHourEditText.requestFocus();
            isValid = false;
        }
        if (mEndHourEditText.length() == 0)
        {
            mEndHourEditText.setError("Sélectionner une heure de fin");
            mEndHourEditText.requestFocus();
            isValid = false;
        }
        if(mParticipantsEditText.length() == 0) //modifier si position 0 dans le spinner renvoyer setError
        {
            mParticipantsEditText.setError("Sélectionner au moins 1 participant");
            mParticipantsEditText.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    public void dayAndHour(){
        mDayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pick.pickDate(mDayEditText, AddReunionActivity.this);
            }
        });
        mDayEditText.setKeyListener(null);

        mStartHourEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pick.pickHour(mStartHourEditText, mEndHourEditText ,AddReunionActivity.this);
            }
        });
        mStartHourEditText.setKeyListener(null);

        mEndHourEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pick.pickHour(mStartHourEditText, mEndHourEditText, AddReunionActivity.this);
            }
        });
        mEndHourEditText.setKeyListener(null);
    }
}
