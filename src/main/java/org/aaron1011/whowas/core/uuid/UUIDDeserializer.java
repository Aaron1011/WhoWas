package org.aaron1011.whowas.core.uuid;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.aaron1011.whowas.core.namehistory.PlayerNameHistoryFetcher;

import java.lang.reflect.Type;
import java.util.UUID;

/**
 * Created by aaron on 1/27/15.
 */
public class UUIDDeserializer implements JsonDeserializer<UUID> {

    @Override
    public UUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return PlayerNameHistoryFetcher.fromFlatString(json.getAsString());
    }
}
