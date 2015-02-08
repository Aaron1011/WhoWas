package org.aaron1011.whowas.core.uuid;

import java.util.UUID;

public class PlayerNameUUID {

    private UUID id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUUID() {

        return id;
    }

    public void setUUID(UUID id) {
        this.id = id;
    }
}
