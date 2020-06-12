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
import com.greg.mareu.dialog_box.WarningDialog;
import com.greg.mareu.model.Reunion;
import com.greg.mareu.picker.Pick;
import com.greg.mareu.service.ReunionApiService;

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
    @BindView(R.id.addDateEdit) TextInputEditText mDayEditText; // à suppr si appel à mPick.pickDate();

    @BindView(R.id.addStartHourLyt) TextInputLayout mStartInput;
    @BindView(R.id.addStartTimeEdit) TextInputEditText mStartHourEditText; // à suppr si appel à mPick.pickHour();
    @BindView(R.id.addEndHourLyt) TextInputLayout mEndInput;
    @BindView(R.id.addEndTimeEdit) TextInputEditText mEndHourEditText;

    @BindView(R.id.spinnerRoom) Spinner mSpinner;

    @BindView(R.id.addParticipantsLyt) TextInputLayout mParticipantsInput;
    @BindView(R.id.addParticipantsEdit) TextInputEditText mParticipantsEditText;
    @BindView(R.id.create) Button mAddButton;

    private String mRandomImage;
    private ReunionApiService mApiService;
    private Pick mPick;

    String[] listOfParticipants;
    boolean[] checkedParticipants;
    ArrayList<Integer> mUserParticipants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        mApiService = DI.getReunionApiService();
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAboutEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(!hasFocus)
                {
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

       listOfParticipants = getResources().getStringArray(R.array.participants_array);
       checkedParticipants = new boolean[listOfParticipants.length];
       mParticipantsEditText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReunionActivity.this);
               mBuilder.setTitle("Liste des participants");
               mBuilder.setMultiChoiceItems(listOfParticipants, checkedParticipants, new DialogInterface.OnMultiChoiceClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int position, boolean isChecked)
                   {
                        if (isChecked){
                            if (!mUserParticipants.contains(position))
                            {
                                mUserParticipants.add(position);
                            }
                        }
                        else if(mUserParticipants.contains(position))
                        {
                            mUserParticipants.remove(mUserParticipants.indexOf(position));
                        }
                   }
               });

               mBuilder.setCancelable(false);
               mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       String item = "";
                       for (int i = 0; i< mUserParticipants.size(); i++)
                       {
                            item = item + listOfParticipants[mUserParticipants.get(i)];
                            if (i != mUserParticipants.size() - 1)
                            {
                                item = item + ", ";
                            }
                       }
                       mParticipantsEditText.setText(item);
                   }
               });

               mBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which)
                   {
                        dialog.dismiss();
                   }
               });
               mBuilder.setNeutralButton("Tout effacer", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                        for(int i =0; i < checkedParticipants.length; i++)
                        {
                            checkedParticipants[i] = false;
                            mUserParticipants.clear();
                            mParticipantsEditText.setText("");
                        }
                   }
               });
               AlertDialog mAlertDialog = mBuilder.create();
               mAlertDialog.show();
           }
       });
       mParticipantsEditText.setKeyListener(null);

        mApiService = DI.getReunionApiService();
        init();
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

        if (mAboutEditText.length() < 3 || mAboutEditText.length() > 30)
        {
            mAboutEditText.setError("Entrer un titre (3-30 caratères)");
            mAboutEditText.requestFocus();
        }
        if (mDayEditText.length() == 0)
        {
            mDayEditText.setError("Sélectionner une date");
            mDayEditText.requestFocus();
        }
        if (mStartHourEditText.length() == 0)
        {
            mStartHourEditText.setError("Sélectionner une heure de début");
            mStartHourEditText.requestFocus();
        }
        if (mEndHourEditText.length() == 0)
        {
            mEndHourEditText.setError("Sélectionner une heure de fin");
            mEndHourEditText.requestFocus();
        }
        if(mParticipantsEditText.length() == 0) //modifier si position 0 dans le spinner renvoyer setError
        {
            mParticipantsEditText.setError("Sélectionner au moins 1 participant");
            mParticipantsEditText.requestFocus();
        }
        else{

            if (mApiService.checkMatches(selectedRoom, reunionDate, startHourSelected, endHourSelected))
            {
                WarningDialog warningDialog = new WarningDialog();
                warningDialog.show(getSupportFragmentManager(), "warning box");
            }
            else
            {
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
}
