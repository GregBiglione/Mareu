package com.greg.mareu.picker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pick
{
    private static Date mStartDate;
    private static Date mEndDate;
    /**
     * Date picker generate calendar to pick a date
     */
    public static void pickDate(EditText mDayEditText, Context context)
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                Calendar cPicked = Calendar.getInstance();
                cPicked.set(Calendar.YEAR, year);
                cPicked.set(Calendar.MONTH, month);
                cPicked.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String pickedDate = DateFormat.getDateInstance(DateFormat.FULL).format(cPicked.getTime());

                //Comparer les dates et interdire les dates dans le passé
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String getSimpleToday = simpleDateFormat.format(c.getTime());
                String getSimplePicked = simpleDateFormat.format(cPicked.getTime());
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

    public static void pickHour(EditText mStartHourEditText, EditText mEndHourEditText, Context context)
    {
        Calendar cStart = Calendar.getInstance();
        int hour = cStart.get(Calendar.HOUR);
        int minute = cStart.get(Calendar.MINUTE);

        Calendar cEnd = Calendar.getInstance();
        int hourEnd = cEnd.get(Calendar.HOUR);
        int minuteEnd = cEnd.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
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
                    SimpleDateFormat stf = new SimpleDateFormat("MM/dd/yyyy:HH:mm");
                    mStartHourEditText.setText(pickedHour);
                    Calendar cStart = Calendar.getInstance();
                    cStart.set(Calendar.HOUR, hourOfDay);
                    cStart.set(Calendar.MINUTE, minute);
                    mStartDate = cStart.getTime();
                }
                else if (mEndHourEditText.hasFocus())
                {

                    Calendar cEnd = Calendar.getInstance();
                    cEnd.set(Calendar.HOUR, hourOfDay);
                    cEnd.set(Calendar.MINUTE, minute);
                    Date mEndDate;
                    mEndDate = cEnd.getTime();

                    if (cEnd.before(cStart))
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
}

