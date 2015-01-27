package org.aaron1011.whowas.core;

import com.google.common.base.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
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
        List<TimestampedName> names = history.getNames();

        assertEquals("UUIDS are not equal", uuid, history.getUuid());

        TimestampedName first = names.get(0);
        TimestampedName second = names.get(1);
        TimestampedName third = names.get(2);

        assertEquals("Djinnibone", first.getName());
        assertEquals(Optional.absent(), first.getChangedToAt());

        assertEquals("Djinnibutt", second.getName());
        assertEquals(Optional.fromNullable(new Date(1413217187000L)), second.getChangedToAt());

        assertEquals("Djinnibone", third.getName());
        assertEquals(Optional.fromNullable(new Date(1413217202000L)), third.getChangedToAt());
    }
}
