package org.aaron1011.whowas.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PlayerNameHistoryFetcher.class })
public class PlayerNameHistoryFetcherTest {

    private final static String API_DATA = "[{'name':'Djinnibone'},{'name':'Djinnibutt','changedToAt':1413217187000},{'name':'Djinnibone','changedToAt':1413217202000}]";

    @Test
    public void testGetPlayerNameHistory() throws Exception {
        stub(method(PlayerNameHistoryFetcher.class, "getAPIResponse")).toReturn(new ByteArrayInputStream(API_DATA.getBytes()));

        UUID uuid = UUID.randomUUID();

        PlayerNameHistory history = PlayerNameHistoryFetcher.getPlayerNameHistory(uuid);
        assertEquals("UUIDS are not equal", uuid, history.getUuid());
    }
}
