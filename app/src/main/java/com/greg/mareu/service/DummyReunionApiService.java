package com.greg.mareu.service;

import android.os.Bundle;

import com.greg.mareu.dialog_box.DateDialog;
import com.greg.mareu.dialog_box.RoomListDialog;
import com.greg.mareu.dialog_box.WarningDialog;
import com.greg.mareu.model.Reunion;
import com.greg.mareu.picker.Pick;
import com.greg.mareu.reunion_list.AddReunionActivity;

import java.util.ArrayList;
import java.util.Date;
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
    @Override
    public List<Reunion> getReunionByDate(Date startDate, Date endDate) {
        List<Reunion> filterByDate = new ArrayList<>();
        for (Reunion r : reunions)
        {
            if (r.getDay().after(startDate) && r.getDay().before(endDate))
            {
                filterByDate.add(r);
            }
        }
        return filterByDate;
    }

    /**
     *  Get reunion list filtered by room
     */
    //@Override
    //public List<Reunion> getReunionByRoom(Reunion reunion) {
    //    List<Reunion> filterByRoom = new ArrayList<>();
    //    mRoom = mRoomListDialog.getSelectedRoom();
    //    for (Reunion r : reunions)
    //    {
    //        if (r.getRoom() == mRoom)
    //        {
    //            filterByRoom.add(r);
    //        }
    //    }
    //    return filterByRoom;
    //}
//
    /**
     * Check matches with existing reunion
     */

    //public void checkMatches(){
    //    mAddDay = mAddReunion.dayToAdd();
    //    mAddStartHour = mAddReunion.startHourToAdd();
    //    mAddEndHour = mAddReunion.endHourToAdd();
    //    mAddRoom = mAddReunion.roomToAdd();
    //    for (Reunion r : reunions)
    //    {
    //        if (r.getDay() == mAddDay && r.getStartTime().compareTo(mAddEndHour) >= 0 && r.getEndTime().compareTo(mAddStartHour) <= 0
    //        && r.getRoom() == mAddRoom)
    //        {
//
    //        }
    //    }
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
