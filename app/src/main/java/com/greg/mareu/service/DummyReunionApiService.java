package com.greg.mareu.service;

import android.widget.Spinner;

import com.greg.mareu.dialog_box.DateDialog;
import com.greg.mareu.dialog_box.RoomListDialog;
import com.greg.mareu.model.Reunion;

import java.util.ArrayList;
import java.util.List;

public class DummyReunionApiService implements ReunionApiService
{
    private List<Reunion> reunions = DummyReunionGenerator.generateReunions();
    Spinner mSpinner;
    private RoomListDialog mRoom;
    private DateDialog mDateDialog;

    /**
     *  Get reunion list
     */
    @Override
    public List<Reunion> getReunions()
    {
        return reunions;
    }

    /**
     *  Get reunion list filtered by date
     */
    @Override
    public List<Reunion> getReunionByDate() {
        List<Reunion> filterByDate = new ArrayList<>();

       //for (Reunion r: reunions)
       //{
       //    if (r.getDay() >= firstDate &&  r.getDay() <= secondDate)
       //    {
       //        filterByDate.add(r);
       //    }
       //}
       return filterByDate;
    }

    /**
     *  Get reunion list filtered by room
     */
    @Override
    public List<Reunion> getReunionByRoom() {
        List<Reunion> filterByRoom = new ArrayList<>();
        //String roomFromSpinner = mSpinner.getSelectedItem().toString();
        //for (Reunion r : reunions)
//
        //{
        //    if (r.getRoom() == roomFromSpinner)
        //    {
        //        filterByRoom.add(r);
        //    }
        //}
        return filterByRoom;
    }

    /**
     * Delete reunion
     */
    @Override
    public void deleteReunion(Reunion reunion)
    {
        reunions.remove(reunion);
    }

    /**
     * Create a reunion
     */
    @Override
    public void createReunion(Reunion reunion)
    {
        reunions.add(reunion);
    }
}
