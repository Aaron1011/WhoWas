package org.aaron1011.namehistory.core;

import java.sql.Time;
import java.util.Date;

public class TimestampedName {

    private String name;

    private Date changedToAt;

    public TimestampedName() {
    }

    public TimestampedName(String name, Date changedToAt) {
        this.name = name;
        this.changedToAt = changedToAt;
    }

    @Override
    public String toString() {
        return "TimestampedName{" +
                "name='" + name + '\'' +
                ", changedToAt=" + changedToAt +
                '}';
    }

}
