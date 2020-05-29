package com.greg.mareu.events;

import java.util.Date;

public class FilterByDateEvent {
    Date startDate;
    Date endDate;

    public FilterByDateEvent(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
