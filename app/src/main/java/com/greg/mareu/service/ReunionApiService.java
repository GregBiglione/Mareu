package com.greg.mareu.service;

import com.greg.mareu.model.Reunion;

import java.util.Date;
import java.util.List;

public interface ReunionApiService
{
    /**
     * Get Reunions list
     */

    List<Reunion> getReunions();

    /**
     * Get Reunion by date
     */

    List<Reunion> getReunionByDate(Date startDate, Date endDate);

    /**
     * Get Reunion by room
     */

    List<Reunion> getReunionByRoom(String roomSelected);

    /**
     * Delete a reunion
     * @param reunion
     */

    /**
     * Check matches with existing reunion
     * @return
     */

    boolean checkMatches(String roomPicked, Date dayPicked, String startHour, String endHour);

    void deleteReunion(Reunion reunion);

    /**
     *  Create a reunion
     * @param reunion
     */

    void createReunion(Reunion reunion);
}
