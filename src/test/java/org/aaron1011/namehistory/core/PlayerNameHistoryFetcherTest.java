package org.aaron1011.namehistory.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PlayerNameHistoryFetcher.class })
public class PlayerNameHistoryFetcherTest {

    private final static String API_DATA = "[{'name':'Djinnibone'},{'name':'Djinnibutt','changedToAt':1413217187000},{'name':'Djinnibone','changedToAt':1413217202000}]";

    @Test
    public void testGetPlayerNameHistory() throws Exception {
        //mockStatic(PlayerNameHistoryFetcher.class);
        stub(method(PlayerNameHistoryFetcher.class, "getAPIResponse")).toReturn(new ByteArrayInputStream(API_DATA.getBytes()));
        //doReturn(new ByteArrayInputStream(API_DATA.getBytes())).when(PlayerNameHistoryFetcher.);

        PlayerNameHistory history = PlayerNameHistoryFetcher.getPlayerNameHistory(UUID.randomUUID());
        System.out.println("Got history");
        System.out.println(history);
        /*URL url = mock(URL.class);
        URLConnection urlConnectionMock = mock(URLConnection.class);

        when(url.openConnection()).thenReturn(urlConnectionMock);

        URLConnection openConnection = url.openConnection();

        assertSame(openConnection, urlConnectionMock);*/
        /*mockStatic(URL.class);

        URLConnection urlConnectionMock = PowerMockito.mock(URLConnection.class);

        when(URL.openConnection).thenReturn(urlConnectionMock);
        PowerMockito.when(urlConnectionMock.getInputStream()).thenReturn(new ByteArrayInputStream(API_DATA.getBytes()));

        PlayerNameHistory history = PlayerNameHistoryFetcher.getPlayerNameHistory(UUID.fromString("3e50893f4903402fb3b810158c6ed75b"));
        assertEquals(history.getUuid(), "3e50893f4903402fb3b810158c6ed75b");

        TimestampedName[] names = { new TimestampedName("Djinnibone", null), new TimestampedName("Djinnibutt", new Date(1413217187000L)),
                new TimestampedName("Djinnibone", new Date(1413217202000L)) };

        assertEquals(history.getNames(), names);*/
    }
}
