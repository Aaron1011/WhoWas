package org.aaron1011.whowas.core;

import java.util.List;
import java.util.UUID;

public class PlayerNameHistory {

    private UUID uuid;

    private List<TimestampedName> names;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<TimestampedName> getNames() {
        return names;
    }

    public void setNames(List<TimestampedName> names) {
        this.names = names;
    }

    public PlayerNameHistory(List<TimestampedName> names) {
        this.names = names;
    }

    public PlayerNameHistory(UUID uuid, List<TimestampedName> names) {
        this.uuid = uuid;
        this.names = names;
    }

    @Override
    public String toString() {
        return "PlayerNameHistory{" +
                "uuid=" + uuid +
                ", names=" + names +
                '}';
    }
}