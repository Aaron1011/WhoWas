package org.aaron1011.namehistory.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class PlayerNameHistoryFetcher {

    private static final String API_ROOT = "https://api.mojang.com";
    private static final String API_BASE_URL = "/user/profiles/%s/names";
    private static final GsonBuilder builder = new GsonBuilder();

    static {
        builder.registerTypeAdapter(PlayerNameHistory[].class, new PlayerNameHistoryDeserializer());
    }

    public static PlayerNameHistory getPlayerNameHistory(UUID uuid) throws MalformedURLException, IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(API_ROOT + String.format(API_BASE_URL, uuid.toString().replace("-", ""))).openConnection();

        return builder.create().fromJson(new InputStreamReader(connection.getInputStream()), PlayerNameHistory.class);
    }
}