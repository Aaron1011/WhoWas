package org.aaron1011.whowas.core.namehistory;

import com.google.common.base.Optional;

import java.util.Date;

public class TimestampedName {

    private String name;

    private Optional<Date> changedToAt;

    public TimestampedName() {
    }

    public TimestampedName(String name, Optional<Date> changedToAt) {
        this.name = name;
        this.changedToAt = changedToAt;
    }

    public TimestampedName(String name, Date changedToAt) {
        this.name = name;
        this.changedToAt = Optional.fromNullable(changedToAt);
    }

    @Override
    public String toString() {
        return "TimestampedName{" +
                "name='" + name + '\'' +
                ", changedToAt=" + changedToAt +
                '}';
    }

    public Optional<Date> getChangedToAt() {
        return changedToAt;
    }

    public void setChangedToAt(Date changedToAt) {
        this.changedToAt = Optional.fromNullable(changedToAt);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
