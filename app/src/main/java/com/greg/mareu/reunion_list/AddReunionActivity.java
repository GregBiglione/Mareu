package com.greg.mareu.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.greg.mareu.R;
import com.greg.mareu.di.DI;
import com.greg.mareu.model.Reunion;
import com.greg.mareu.service.ReunionApiService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddReunionActivity extends AppCompatActivity{

    @BindView(R.id.addImage) CircleImageView image;
    @BindView(R.id.addAboutLyt) TextInputLayout aboutInput;

    @BindView(R.id.addDayLyt) TextInputLayout dayInput;
    @BindView(R.id.datePicked) TextInputEditText dayEditText;
    @BindView(R.id.dateReunion) Button dateButton;

    @BindView(R.id.addHourLyt) TextInputLayout hourInput;
    @BindView(R.id.hourPicked) TextInputEditText hourEditText;
    @BindView(R.id.hourReunion) Button hourButton;

    @BindView(R.id.spinnerRoom) Spinner spinner;

    @BindView(R.id.addParticipantsButton) Button mAddParticipantsButton;
    @BindView(R.id.addParticipantsLyt) TextInputLayout participantsInput;
    @BindView(R.id.addParticipants) TextInputEditText mParticipantsEditText;
    @BindView(R.id.create) Button addButton;

    private String mRandomImage;
    private ReunionApiService mApiService;

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

       hourButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
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
                            else
                            {
                                mUserParticipants.remove(position);
                            }
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
                dayEditText.setText(currentDate);
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
                String currentHour = hourOfDay + "h" + minute;
                hourEditText.setText(currentHour);
            }
        },hour, minute, true);
        timePickerDialog.show();
    }

    public void init() {
        mRandomImage = randomImage();
        Glide.with(this).load(mRandomImage).into(image);
        aboutInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) { addButton.setEnabled(s.length() > 0);}
        });
    }

    @OnClick(R.id.create)
    void addReunion() {
        Reunion reunion = new Reunion(
                System.currentTimeMillis(),
                mRandomImage,
                aboutInput.getEditText().getText().toString(),
                dayInput.getEditText().getText().toString(),
                hourInput.getEditText().getText().toString(),
                //String.valueOf(spinner.getSelectedItem()),
                spinner.getSelectedItem().toString(),
                participantsInput.getEditText().getText().toString()
        );
        mApiService.createReunion(reunion);
        finish();
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
