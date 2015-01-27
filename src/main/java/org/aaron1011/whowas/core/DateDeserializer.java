package org.aaron1011.whowas.core;

import com.google.common.base.Optional;
import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Optional<Date>> {

    @Override
    public Optional<Date> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        System.out.println("JSON: " + json);
        if (json.isJsonNull()) {
            return Optional.absent();
        }
        return Optional.fromNullable(new Date(Long.valueOf((json).getAsString().substring(0, 13))));
    }
}
