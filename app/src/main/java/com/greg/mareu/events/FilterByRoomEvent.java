package com.greg.mareu.events;

public class FilterByRoomEvent {
    String roomSelected;

    public FilterByRoomEvent(String roomSelected) {
        this.roomSelected = roomSelected;
    }

    public String getRoomSelected() {
        return roomSelected;
    }
}
