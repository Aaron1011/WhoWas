package org.aaron1011.whowas.core.namehistory;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PlayerNameHistoryFetcher {

    private final static String API_ROOT = "https://api.mojang.com";
    private final static String API_BASE_URL = "/user/profiles/%s/names";
    private final static Gson gson;

    private final static LoadingCache<UUID, Optional<PlayerNameHistory>> uuids;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TimestampedName.class, new TimestampedNameDeserializer());
        gson = builder.create();

        uuids = CacheBuilder.newBuilder()
                .maximumSize(500)
                .refreshAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<UUID, Optional<PlayerNameHistory>>() {

                            public Optional<PlayerNameHistory> load(UUID uuid) throws IOException{
                                return getPlayerNameHistoryInternal(uuid);
                            }

                            public ListenableFuture<Optional<PlayerNameHistory>> reload(final UUID key, PlayerNameHistory prevHistory) {
                                // asynchronous!
                                ListenableFutureTask<Optional<PlayerNameHistory>> task = ListenableFutureTask.create(new Callable<Optional<PlayerNameHistory>>() {
                                    public Optional<PlayerNameHistory> call() throws IOException {
                                        return getPlayerNameHistoryInternal(key);
                                    }
                                });

                                MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1)).execute(task);
                                return task;
                            }
                        }
                );
    }

    public static Optional<PlayerNameHistory> getPlayerNameHistory(UUID uuid) throws ExecutionException {
        return uuids.get(uuid);
    }

    private static Optional<PlayerNameHistory> getPlayerNameHistoryInternal(UUID uuid) throws IOException {
        InputStream inputStream = getAPIResponse(uuid);

        TimestampedName[] names = gson.fromJson(new InputStreamReader(inputStream), TimestampedName[].class);

        if (names != null) {
            return Optional.fromNullable(new PlayerNameHistory(uuid, Arrays.asList(names)));
        }
        return Optional.absent();
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