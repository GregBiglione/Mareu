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
     * Delete a reunion
     * @param reunion
     */

    void deleteReunion(Reunion reunion);
}
