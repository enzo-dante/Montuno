package com.crownhounds.montuno.model;

import com.crownhounds.montuno.backend.model.Artist;
import com.crownhounds.montuno.backend.model.Datasource;
import com.crownhounds.montuno.backend.model.SongArtist;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatasourceTest {

    // CONSTANTS/static class variables assigned FINAL value before compilation/instantiation
    private static final String NOT_IMPLEMENTED_FAIL = "Test failure due to not being implemented";
    private static final String BEFORE_ALL_TESTS = "Before all tests";
    private static final String AFTER_ALL_TESTS = "After all tests";
    private static final String TEST = "TEST";
    private static final String TEST_DIVIDER = "----------------";
    private static final String TEST_ARTIST_IRON_MAIDEN = "Iron Maiden";
    private static final String TEST_ARTIST_GRATEFUL_DEAD = "Grateful Dead";

    private static final String TEST_SONG_HEARTLESS = "Heartless";
    private static final String TEST_SONG_TOUCH_GREY = "Touch of Grey";

    private static final String TEST_ALBUM_IN_THE_DARK = "In The Dark";
    private static final int SORT_ORDER_NULL = 5;

    // OOP ENCAPSULATION private class fields
    Datasource datasource;
    boolean isOpen;
    boolean isClosed;

    @BeforeAll
    static void beforeAll() {
        System.out.println(BEFORE_ALL_TESTS);
        assertTrue(Datasource.dropArtistListView());
        assertTrue(Datasource.createArtistListViewForSongArtists());
    }

    @AfterAll
    static void afterAll() {
        System.out.println(AFTER_ALL_TESTS);
        assertTrue(Datasource.dropArtistListView());
    }

    @BeforeEach
    void setUp() {
        datasource = Datasource.getDatasourceInstance();
        isOpen = datasource.open();
    }

    @AfterEach
    void tearDown() {
        isOpen = datasource.close();
    }

    @Test
    void open_success() {
        assertTrue(isOpen);
    }

    @Test
    void close_success() {
        isClosed = datasource.close();
        assertTrue(isOpen);
    }

    @Test
    void getDatasource_singleton() {
        assertEquals(datasource, Datasource.getDatasourceInstance());
    }

    @Test
    void getDatasource_notNull() {
        assertNotNull(Datasource.getDatasourceInstance());
    }

    @Test
    void queryArtist_success() {
        List<Artist> artists = datasource.queryArtist(Datasource.ORDER_BY_NONE);
        assertNotNull(artists);

        System.out.println(TEST_DIVIDER);

        Datasource.printArtists(artists);
    }

    @Test
    void queryArtist_asc() {
        List<Artist> artists = datasource.queryArtist(Datasource.ORDER_BY_ASC);
        assertNotNull(artists);

        Datasource.printArtists(artists);
    }

    @Test
    void queryArtist_desc() {
        List<Artist> artists = datasource.queryArtist(Datasource.ORDER_BY_DESC);
        assertNotNull(artists);

        Datasource.printArtists(artists);
    }

    @Test
    void queryArtist_null() {
        List<Artist> artists = datasource.queryArtist(SORT_ORDER_NULL);
        assertNull(artists);
    }

    @Test
    void queryAlbumsForArtist_success() {
        List<String> albums = datasource.queryAlbumsForArtist(TEST_ARTIST_IRON_MAIDEN, Datasource.ORDER_BY_DESC);
        assertNotNull(albums);
    }

    @Test
    void handleSort_null() {
        StringBuilder sb = new StringBuilder(Datasource.QUERY_ALBUMS_BY_ARTIST_START);
        sb.append(datasource.formatField(TEST_ARTIST_IRON_MAIDEN));
        sb = Datasource.handleSort(sb, SORT_ORDER_NULL, Datasource.QUERY_ALBUMS_BY_ARTIST_SORT);
        assertNull(sb);
    }

    @Test
    void formatField_success() {
        String actualResult = datasource.formatField(TEST_ARTIST_IRON_MAIDEN);
        String expectedResult = "\"" + TEST_ARTIST_IRON_MAIDEN + "\"";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void formatField_fail() {
        String actualResult = datasource.formatField(TEST_ARTIST_IRON_MAIDEN);
        String expectedResult = TEST_ARTIST_IRON_MAIDEN;
        assertNotEquals(expectedResult, actualResult);
    }

    @Test
    void insertIntoSongs_success() {
        boolean actual = datasource.insertIntoSongs(TEST_SONG_TOUCH_GREY, TEST_ARTIST_GRATEFUL_DEAD, TEST_ALBUM_IN_THE_DARK, 1);
        assertTrue(actual);
        datasource.deleteSong(TEST_SONG_TOUCH_GREY, TEST_ARTIST_GRATEFUL_DEAD);
    }

    @Test
    void insertIntoSongs_fail_dbClosed() {
        datasource.close();

        boolean actual = datasource.insertIntoSongs(TEST_SONG_TOUCH_GREY, TEST_ARTIST_GRATEFUL_DEAD, TEST_ALBUM_IN_THE_DARK, 1);
        assertFalse(actual);
    }

    @Test
    void insertIntoSongs_fail_duplicate() {
        boolean actual = datasource.insertIntoSongs(TEST_SONG_HEARTLESS, TEST_ARTIST_GRATEFUL_DEAD, TEST_ALBUM_IN_THE_DARK, 1);
        assertFalse(actual);
    }


    @Test
    void getCount_success() {
        int actualResult = datasource.getCount(Datasource.TABLE_SONGS);
        int expectedResult = 5350;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCount_fail() {
        Integer actualResult = datasource.getCount(TEST);
        Integer expectedResult = -1;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testProgressBar_true() {
        assertTrue(datasource.testProgressBar());
    }

    @Test
    void queryArtistsForSong_success() {
        List<SongArtist> songArtists = datasource.queryArtistsForSong(TEST_SONG_HEARTLESS, Datasource.ORDER_BY_DESC);
        assertNotNull(songArtists);

        System.out.println(TEST_DIVIDER);
        Datasource.printSongArtists(songArtists);
    }

    @Test
    void queryArtistsForSong_badInputSong() {
        List<SongArtist> songArtists = datasource.queryArtistsForSong(TEST, Datasource.ORDER_BY_DESC);
        assertNull(songArtists);
    }

    @Test
    void queryArtistsForSong_badInputSortOrder() {
        List<SongArtist> songArtists = datasource.queryArtistsForSong(TEST_SONG_HEARTLESS, SORT_ORDER_NULL);
        assertNull(songArtists);
    }

    @Test
    void querySongsMetadata_success() {
        boolean actualResult = datasource.querySongsMetadata();
        assertTrue(actualResult);
    }

    @Test
    void queryArtistListView_success() {
        List<SongArtist> actual = datasource.queryArtistListViewByTitle(TEST_SONG_HEARTLESS);
        assertNotNull(actual);

        System.out.println(TEST_DIVIDER);
        Datasource.printSongArtists(actual);
    }

    @Test
    void queryArtistListView_fail() {
        datasource.close();

        List<SongArtist> actual = datasource.queryArtistListViewByTitle(TEST_SONG_HEARTLESS);
        assertNull(actual);
    }

    @Test
    void validateStringBuilder_success() {
        StringBuilder test = new StringBuilder();
        test.append(TEST);
        boolean actual = datasource.validateDataStructure(test);
        assertTrue(actual);
    }

    @Test
    void validateStringBuilder_fail() {
        StringBuilder test = new StringBuilder();
        boolean actual = datasource.validateDataStructure(test);
        assertFalse(actual);
    }

    @Test
    void updateArtist_success() {
        assertTrue(datasource.updateArtistName(66, "AC DC"));
    }

    @Test
    void updateArtist_badConnection() {
        datasource.close();
        assertFalse(datasource.updateArtistName(2, TEST));
    }

    @Test
    void updateArtist_badInputId() {
        assertFalse(datasource.updateArtistName(-1, TEST));
    }

    @Test
    void updateArtist_badInputName() {
        assertFalse(datasource.updateArtistName(1, ""));
    }

    @Test
    void deleteSong_true() {
        datasource.insertIntoSongs(TEST_SONG_TOUCH_GREY, TEST_ARTIST_GRATEFUL_DEAD, TEST_ALBUM_IN_THE_DARK, 1);
        boolean actual = datasource.deleteSong(TEST_SONG_TOUCH_GREY, TEST_ARTIST_GRATEFUL_DEAD);
        assertTrue(actual);
    }

    @Test
    void deleteSong_missingFalse() {
        boolean actual = datasource.deleteSong(TEST_SONG_TOUCH_GREY, TEST_ARTIST_GRATEFUL_DEAD);
        assertFalse(actual);
    }

    @Test
    void deleteSong_badInput() {
        String testArtist = "";
        String testSong = "";

        boolean actual = datasource.deleteSong(testSong, testArtist);
        assertFalse(actual);
    }
}