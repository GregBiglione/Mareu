package com.greg.mareu.events;

import com.greg.mareu.model.Reunion;

public class DeleteReunionEvent
{
    public Reunion reunion;

    /**
     * Constructor
     * @param  reunion
     */
    public DeleteReunionEvent(Reunion reunion)
    {
        this.reunion = reunion;
    }
}
