package com.greg.mareu.service;

import com.greg.mareu.model.Reunion;

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

    List<Reunion> getReunionByDate();

    /**
     * Get Reunion by room
     */

    List<Reunion> getReunionByRoom();

    /**
     * Delete a reunion
     * @param reunion
     */

    void deleteReunion(Reunion reunion);

    /**
     *  Create a reunion
     * @param reunion
     */

    void createReunion(Reunion reunion);
}
