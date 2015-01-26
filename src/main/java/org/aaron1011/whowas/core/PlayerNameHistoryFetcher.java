package org.aaron1011.whowas.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class PlayerNameHistoryFetcher {

    private final static String API_ROOT = "https://api.mojang.com";
    private final static String API_BASE_URL = "/user/profiles/%s/names";
    private final static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(PlayerNameHistory[].class, new PlayerNameHistoryDeserializer());
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        gson = builder.create();
    }

    public static PlayerNameHistory getPlayerNameHistory(UUID uuid) throws MalformedURLException, IOException {

        InputStream inputStream = getAPIResponse(uuid);

        TimestampedName[] names = gson.fromJson(new InputStreamReader(inputStream), TimestampedName[].class);
        return new PlayerNameHistory(uuid, Arrays.asList(names));
    }

    private static InputStream getAPIResponse(UUID uuid) throws MalformedURLException, IOException {
        URLConnection connection = new URL(API_ROOT + String.format(API_BASE_URL, toFlatString(uuid))).openConnection();
        return connection.getInputStream();
    }

    public static String toFlatString(UUID uuid) {
        return uuid.toString().replace("-", "");
    }

    public static UUID fromFlatString(String str) {
        return UUID.fromString(str.substring(0, 8) + "-" + str.substring(8, 12) + "-" + str.substring(12, 16) +
                "-" + str.substring(16, 20) + "-" + str.substring(20, 32));
    }
}