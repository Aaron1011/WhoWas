package org.aaron1011.whowas.core;

import org.aaron1011.whowas.core.namehistory.PlayerNameHistoryFetcher;
import org.aaron1011.whowas.core.uuid.PlayerUUIDFetcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PlayerUUIDFetcher.class })
public class PlayerUUIDFetcherTest {

    private final static String API_DATA = "{\"id\":\"a9e76277c4534ff098a09461fe81e458\",\"name\":\"Aaron1011\"}";

    @Test
    public void testGetPlayerNameHistory() throws Exception {
        stub(method(PlayerUUIDFetcher.class, "getAPIResponse")).toReturn(new ByteArrayInputStream(API_DATA.getBytes()));

        UUID uuid = PlayerUUIDFetcher.getUUID("Aaron1011").get();

        assertEquals(PlayerNameHistoryFetcher.fromFlatString("a9e76277c4534ff098a09461fe81e458"), uuid);
    }
}
