package com.greg.mareu.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.greg.mareu.R;
import com.greg.mareu.di.DI;
import com.greg.mareu.model.Reunion;
//import com.greg.mareu.picker.Pick;
import com.greg.mareu.service.ReunionApiService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class AddReunionActivity extends AppCompatActivity{

    @BindView(R.id.addImage) CircleImageView mImage;
    @BindView(R.id.addAboutLyt) TextInputLayout mAboutInput;
    @BindView(R.id.addAbout) TextInputEditText mAboutEditText;

    @BindView(R.id.addDayLyt) TextInputLayout mDayInput;
    @BindView(R.id.datePicked) TextInputEditText mDayEditText; // à suppr si appel à mPick.pickDate();
    @BindView(R.id.dateReunion) Button mDateButton;

    @BindView(R.id.addHourLyt) TextInputLayout mHourInput;
    @BindView(R.id.hourPicked) TextInputEditText mHourEditText; // à suppr si appel à mPick.pickHour();
    @BindView(R.id.hourReunion) Button mHourButton;

    @BindView(R.id.spinnerRoom) Spinner mSpinner;

    @BindView(R.id.addParticipantsButton) Button mAddParticipantsButton;
    @BindView(R.id.addParticipantsLyt) TextInputLayout mParticipantsInput;
    @BindView(R.id.addParticipants) TextInputEditText mParticipantsEditText;
    @BindView(R.id.create) Button mAddButton;

    private String mRandomImage;
    private ReunionApiService mApiService;
    //private Pick mPick;

    String[] listOfParticipants;
    boolean[] checkedParticipants;
    ArrayList<Integer> mUserParticipants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAboutEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    closeKeyboard();
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

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPick.pickDate();
                pickDate();
            }
        });

       mHourButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //mPick.pickHour();
               pickHour();
           }
       });

       listOfParticipants = getResources().getStringArray(R.array.participants_array);
       checkedParticipants = new boolean[listOfParticipants.length];
       mAddParticipantsButton.setOnClickListener(new View.OnClickListener() {
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
        mApiService = DI.getReunionApiService();
        init();
    }

    /**
     * Date picker generate calendar to pick a date
     */

    public void pickDate()
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                mDayEditText.setText(currentDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Time picker generate a clock to pick an hour
     */

    public void pickHour(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String minuteString = "";
                if(minute < 10)
                {
                    minuteString = "0" + minute;
                }
                else
                {
                    minuteString = "" + minute;
                }
                String currentHour = hourOfDay + "h" + minuteString;
                mHourEditText.setText(currentHour);
            }
        },hour, minute, true);
        timePickerDialog.show();
    }

    /**
     * Close keyboard
     */

    public void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
        if (mAboutEditText.length() < 3)
        {
            mAboutEditText.setError("Enter un titre (3 caratères minimum)");
            mAboutEditText.requestFocus();
            return;
        }
        if (mDayEditText.length() == 0)
        {
            mDayEditText.setError("Sélectionner une date");
            mDayEditText.requestFocus();
            return;
        }
        if (mHourEditText.length() == 0)
        {
            mHourEditText.setError("Sélectionner une heure");
            mHourEditText.requestFocus();
            return;
        }
        if(mParticipantsEditText.length() == 0) //modifier si position 0 dans le spinner renvoyer setError
        {
            mParticipantsEditText.setError("Sélectionner au moins 1 participant");
            mParticipantsEditText.requestFocus();
            return;
        }
        else{
            Reunion reunion = new Reunion(
                    System.currentTimeMillis(),
                    mRandomImage,
                    mAboutInput.getEditText().getText().toString().trim(),
                    mDayInput.getEditText().getText().toString().trim(),
                    mHourInput.getEditText().getText().toString().trim(),
                    //String.valueOf(spinner.getSelectedItem()),
                    mSpinner.getSelectedItem().toString(),
                    mParticipantsInput.getEditText().getText().toString().trim()
            );
            mApiService.createReunion(reunion);
            Toasty.success(this, "Réunion enregistrée", Toast.LENGTH_SHORT).show();
            finish();
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
