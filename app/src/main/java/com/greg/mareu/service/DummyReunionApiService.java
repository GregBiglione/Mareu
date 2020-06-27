package com.greg.mareu.service;

import com.greg.mareu.model.Reunion;

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
    @Override
    public List<Reunion> getReunionByRoom(String roomSelected) {
        List<Reunion> filterByRoom = new ArrayList<>();
        for (Reunion r : reunions)
        {
            if (r.getRoom().equals(roomSelected))
            {
                filterByRoom.add(r);
            }
        }
        return filterByRoom;
    }

    /**
     * Check matches with existing reunion
     * @return
     */

    public boolean checkMatches(String roomPicked, Date dayPicked, String startHour, String endHour){
        for (Reunion r : reunions)
        {
            if (
                    r.getDay().equals(dayPicked) && r.getRoom().equals(roomPicked) &&
                    ((r.getStartTime().compareTo(startHour) >= 0 && r.getStartTime().compareTo(endHour) <= 0)
                    || (r.getEndTime().compareTo(startHour) >= 0 && r.getEndTime().compareTo(endHour) <= 0)
                    || (r.getStartTime().compareTo(startHour) <= 0 && r.getEndTime().compareTo(startHour) >= 0)
                    || (r.getStartTime().compareTo(endHour) <= 0 && r.getEndTime().compareTo(endHour) >= 0)
                    || (r.getStartTime().compareTo(startHour) <= 0 && r.getEndTime().compareTo(endHour) >= 0)
                ))
            {
                return true;
            }
        }
        return false;
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
