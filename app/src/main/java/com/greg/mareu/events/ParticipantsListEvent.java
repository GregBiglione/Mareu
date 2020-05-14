package com.greg.mareu.events;

import com.greg.mareu.model.Reunion;

/**
 * Event show reunions informations
 */
public class ParticipantsListEvent
{
    /**
     * Reunions informations
     */
    public Reunion reunion;

    /**
     * Constructor.
     * @param reunion
     */
    public ParticipantsListEvent(Reunion reunion) {
        this.reunion = reunion;
    }
}
