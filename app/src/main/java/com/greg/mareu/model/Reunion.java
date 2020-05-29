package com.greg.mareu.model;

import androidx.annotation.Nullable;

import java.util.Date;

public class Reunion
{
    long id;
    String reunionAvatar;
    String aboutReunion;
    Date day;
    String startTime;
    String endTime;
    String room;
    String participants;

    public Reunion(long id, String reunionAvatar, String aboutReunion, Date day, String startTime, String endTime, String room, String participants) {
        this.id = id;
        this.reunionAvatar = reunionAvatar;
        this.aboutReunion = aboutReunion;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String hour) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
