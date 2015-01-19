package org.aaron1011.namehistory.core;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerNameHistoryDeserializer implements JsonDeserializer<PlayerNameHistory> {

    @Override
    public PlayerNameHistory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<TimestampedName> names = new ArrayList<TimestampedName>();

        Iterator<JsonElement> iterator = json.getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            names.add((TimestampedName) context.deserialize(iterator.next().getAsJsonObject(), TimestampedName.class));
        }

        return new PlayerNameHistory(names);
    }
}
