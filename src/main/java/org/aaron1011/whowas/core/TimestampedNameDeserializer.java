package org.aaron1011.whowas.core;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

public class TimestampedNameDeserializer implements JsonDeserializer<TimestampedName> {

    @Override
    public TimestampedName deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        String name = obj.get("name").getAsString();
        Date date = null;
        if (obj.has("changedToAt")) {
            date = new Date(obj.get("changedToAt").getAsLong());
        }
        return new TimestampedName(name, date);
    }
}
