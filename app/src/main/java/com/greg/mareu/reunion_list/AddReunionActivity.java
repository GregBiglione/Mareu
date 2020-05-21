package com.greg.mareu.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.greg.mareu.R;
import com.greg.mareu.di.DI;
import com.greg.mareu.dialog_box.DateAndTimePickerFragment;
import com.greg.mareu.model.Reunion;
//import com.greg.mareu.picker.Pick;
import com.greg.mareu.picker.Pick;
import com.greg.mareu.service.ReunionApiService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class AddReunionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Sélectionner une salle"))
                {
                    //Message d'erreur
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //PickDate dans AddReunionActivity/////////////////////////////////////////////////////////////////////////////////////////
        mDayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mPick.pickDate(mDayEditText);
                pickDate();
            }
        });

        //PICK DATE FROM DATAE&TIME//////////////////////////////////////////////////////////////////////////////////////////////
        //mDayEditText.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        DialogFragment datePicker = new DateAndTimePickerFragment();
        //        datePicker.show(getSupportFragmentManager(), "Date picker");
        //    }
        //});
        //PickDate dans AddReunionActivity
       mStartHourEditText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //mPick.pickHour();
               pickHour();

           }
       });

        mEndHourEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPick.pickHour();
                pickHour();

            }
        });

       //PICK HOUR FROM DATAE&TIME///////////////////////////////////////////////////////////////////////////////////////
       //mStartHourEditText.setOnClickListener(new View.OnClickListener() {
       //    @Override
       //    public void onClick(View v) {
       //         DialogFragment timePicker = new DateAndTimePickerFragment();
       //         timePicker.show(getSupportFragmentManager(), "Time picker");
       //    }
       //});

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
        mApiService = DI.getReunionApiService();
        init();
    }

    /**
     * Date picker generate calendar to pick a date
     */

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mDayEditText.setText(currentDate);
    }

    /**
     * Time picker generate a clock to pick an hour
     */
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
        mStartHourEditText.setText(currentHour);
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
                Calendar cPicked = Calendar.getInstance();
                cPicked.set(Calendar.YEAR, year);
                cPicked.set(Calendar.MONTH, month);
                cPicked.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String pickedDate = DateFormat.getDateInstance(DateFormat.FULL).format(cPicked.getTime());

                //Comparer les dates et interdire les dates dans le passé
                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String getSimpleToday = mSimpleDateFormat.format(c.getTime());
                String getSimplePicked = mSimpleDateFormat.format(cPicked.getTime());
                if (getSimplePicked.compareTo(getSimpleToday) < 0)
                {
                    mDayEditText.setError("Impossible de choisir une date antérieure à aujourd'hui");
                }
                else
                {
                    mDayEditText.setText(pickedDate);
                }
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
                String pickedHour = hourOfDay + "h" + minuteString;

                if (mStartHourEditText.hasFocus())
                {
                    mStartHourEditText.setText(pickedHour);
                }
                else if (mEndHourEditText.hasFocus())
                {

                    //Comparaison des heures et interdiction heure de début > heures de fin
                    //test 1
                    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm");
                    String startHour = hourOfDay + ":" + minuteString;
                    String simpleStartHour = mSimpleDateFormat.format(c.getTime()); //if non exec pb ligne
                    String simpleEndHour = mSimpleDateFormat.format(c.getTime());//if non exec pb ligne

                    if (pickedHour.compareTo(pickedHour) < 0)
                    {
                        mEndHourEditText.setError("Heure de fin incorrecte");
                    }
                    else
                    {
                        mEndHourEditText.setText(pickedHour);
                    }
                }


                }
        },hour, minute, true);
        timePickerDialog.show();
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
        //Version courte
        //switch (view.getId()){
        //    case R.id.addAbout:
        //        if (mAboutEditText.length() < 3 || mAboutEditText.length() > 30)
        //        {
        //            mAboutEditText.setError("Enter un titre (3-30 caratères)");
        //            mAboutEditText.requestFocus();
        //        }
        //        break;
        //    case R.id.addStartTimeEdit:
        //        if (mDayEditText.length() == 0)
        //        {
        //            mDayEditText.setError("Sélectionner une date");
        //            mDayEditText.requestFocus();
        //        }
        //        break;
        //    case R.id.addEndTimeEdit:
        //        if (mEndHourEditText.length() == 0)
        //        {
        //            mEndHourEditText.setError("Sélectionner une heure de fin");
        //            mEndHourEditText.requestFocus();
        //        }
        //        break;
        //    case R.id.addParticipantsEdit:
        //        if(mParticipantsEditText.length() == 0) //modifier si position 0 dans le spinner renvoyer setError
        //        {
        //            mParticipantsEditText.setError("Sélectionner au moins 1 participant");
        //            mParticipantsEditText.requestFocus();
        //        }
        //        break;
        //}

        if (mAboutEditText.length() < 3 || mAboutEditText.length() > 30)
        {
            mAboutEditText.setError("Enter un titre (3-30 caratères)");
            mAboutEditText.requestFocus();
        }
        //version regroupée
        if (mDayEditText.length() == 0 ||mStartHourEditText.length() == 0 || mEndHourEditText.length() == 0)
        {
            //Message d'erreur setError ne peut pas être utilisé car où mettre le message d'erreur
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
            Reunion reunion = new Reunion(
                    System.currentTimeMillis(),
                    mRandomImage,
                    mAboutInput.getEditText().getText().toString().trim(),
                    mDayInput.getEditText().getText().toString().trim(),
                    mStartInput.getEditText().getText().toString().trim(),
                    mEndInput.getEditText().getText().toString().trim(),
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
