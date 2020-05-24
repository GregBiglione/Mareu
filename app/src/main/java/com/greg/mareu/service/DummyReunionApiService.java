package com.greg.mareu.service;

import com.greg.mareu.dialog_box.DateDialog;
import com.greg.mareu.dialog_box.RoomListDialog;
import com.greg.mareu.model.Reunion;

import java.util.ArrayList;
import java.util.List;

public class DummyReunionApiService implements ReunionApiService
{
    private List<Reunion> reunions = DummyReunionGenerator.generateReunions();
    private RoomListDialog mRoomListDialog;
    private DateDialog mDateDialog;
    String mRoom;
    String mFirstDate;
    String mSecondDate;

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
        mFirstDate = mDateDialog.selectedStartHour();
        mSecondDate = mDateDialog.selectedEndHour();
        for (Reunion r : reunions)
        {
            if (r.getDay().compareTo(mFirstDate) >= 0 && r.getDay().compareTo(mSecondDate) <= 0)
            {
                filterByDate.add(r);
            }
        }
        return filterByDate;
    }

    /**
     *  Get reunion list filtered by room
     */
    @Override
    public List<Reunion> getReunionByRoom() {
        List<Reunion> filterByRoom = new ArrayList<>();
        mRoom = mRoomListDialog.getSelectedRoom();
        for (Reunion r : reunions)
        {
            if (r.getRoom() == mRoom)
            {
                filterByRoom.add(r);
            }
        }
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
