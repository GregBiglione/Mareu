package com.greg.mareu.service;

import android.widget.Spinner;

import com.greg.mareu.model.Reunion;
import com.greg.mareu.reunion_list.AddReunionActivity;

import java.util.ArrayList;
import java.util.List;

public class DummyReunionApiService implements ReunionApiService
{
    private List<Reunion> reunions = DummyReunionGenerator.generateReunions();

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
    //@Override
    //public List<Reunion> getReunionByDate() {
    //    List<Reunion> filterByDate = new ArrayList<>();
    //    for (Reunion r: reunions)
    //    {
    //        if (/*valeur basse choise (ex=4/06/2020)*/ >= r.getDay() &&  r.getDay() <=/*valeur basse choise (ex=7/06/2020)*/ )
    //        {
    //            filterByDate.add(r);
    //        }
    //    }
    //    return filterByDate;
    //}

    /**
     *  Get reunion list filtered by room
     */
    //@Override
    //public List<Reunion> getReunionByRoom() {
    //    List<Reunion> filterByRoom = new ArrayList<>();
    //    Spinner mSpinner;
    //    for (Reunion r : reunions)
//
    //    {
    //        if (r.getRoom() == /*valeur correspondante dans le spinner*/)
    //        {
    //            filterByRoom.add(r);
    //        }
    //    }
    //    return filterByRoom;
    //}

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
