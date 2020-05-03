package com.greg.mareu.model;

import androidx.annotation.Nullable;

public class Reunion
{
    long id;
    String reunionAvatar;
    String aboutReunion;
    String day;
    String hour;
    String room;
    String participants;

    public Reunion(long id, String reunionAvatar, String aboutReunion, String day, String hour, String room, String participants) {
        this.id = id;
        this.reunionAvatar = reunionAvatar;
        this.aboutReunion = aboutReunion;
        this.day = day;
        this.hour = hour;
        this.room = room;
        this.participants = participants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReunionAvatar() {
        return reunionAvatar;
    }

    public void setReunionAvatar(String reunionAvatar) {
        this.reunionAvatar = reunionAvatar;
    }

    public String getAboutReunion() {
        return aboutReunion;
    }

    public void setAboutReunion(String aboutReunion) {
        this.aboutReunion = aboutReunion;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(@Nullable Object obj)
    {
        if (this == obj) return  true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reunion reunion = (Reunion) obj;
        return id == reunion.id;
    }
}
