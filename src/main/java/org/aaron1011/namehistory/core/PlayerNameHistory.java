package org.aaron1011.namehistory.core;

import java.util.List;
import java.util.UUID;

public class PlayerNameHistory {

    private UUID uuid;

    private List<String> names;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public PlayerNameHistory(UUID uuid, List<String> names) {

        this.uuid = uuid;
        this.names = names;
    }
}
