package org.aaron1011.whowas.core.uuid;

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
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.concurrent.*;

public class PlayerUUIDFetcher {
    private final static String API_ROOT = "https://api.mojang.com";
    private final static String API_BASE_URL = "/users/profiles/minecraft/%s";
    private final static Gson gson;

    private final static LoadingCache<String, Optional<UUID>> names;

    static {
        gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDDeserializer()).create();

        names = CacheBuilder.newBuilder()
                .maximumSize(500)
                .refreshAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, Optional<UUID>>() {

                            public Optional<UUID> load(String name) throws IOException {
                                return getUUIDInternal(name);
                            }

                            public ListenableFuture<Optional<UUID>> reload(final String key, Optional<UUID> prevUUID) {
                                // asynchronous!
                                ListenableFutureTask<Optional<UUID>> task = ListenableFutureTask.create(new Callable<Optional<UUID>>() {
                                    public Optional<UUID> call() throws IOException {
                                        return getUUIDInternal(key);
                                    }
                                });

                                MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1)).execute(task);
                                return task;
                            }
                        }
                );
    }

    public static Optional<UUID> getUUID(String name) throws ExecutionException {
        return names.get(name);
    }

    private static Optional<UUID> getUUIDInternal(String name) throws IOException {
        PlayerNameUUID uuid = gson.fromJson(new InputStreamReader(getAPIResponse(name)), PlayerNameUUID.class);
        if (uuid != null) {
            return Optional.fromNullable(uuid.getUUID());
        }
        return Optional.absent();
    }

    private static InputStream getAPIResponse(String name) throws IOException {
        URLConnection connection = new URL(API_ROOT + String.format(API_BASE_URL, name)).openConnection();
        return connection.getInputStream();
    }
}
