package com.greg.mareu.service;

import com.greg.mareu.model.Reunion;

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
