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

    private static final String TEST_ARTIST_IRON_MAIDEN = "Iron Maiden";
    private static final String TEST_ARTIST_GRATEFUL_DEAD = "Grateful Dead";

    private static final String TEST_SONG_HEARTLESS = "Heartless";
    private static final String TEST_SONG_TOUCH_GREY = "Touch of Grey";

    private static final String TEST_ALBUM_IN_THE_DARK = "In The Dark";

    // OOP ENCAPSULATION private class fields
    Datasource datasource;
    boolean isOpen;
    boolean isClosed;

    @BeforeAll
    static void beforeAll() {
        System.out.println(BEFORE_ALL_TESTS);
        assertTrue(Datasource.createArtistListViewForSongArtists());
    }

    @AfterAll
    static void afterAll() {
        System.out.println(AFTER_ALL_TESTS);
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

        System.out.println("----------------");

        for (Artist artist : artists) {
            System.out.println(Datasource.ARTIST_NAME + artist.getName() + "\n" +
                    Datasource.ARTIST_ID + artist.get_id() + "\n");
        }
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
        List<Artist> artists = datasource.queryArtist(5);
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
        sb = Datasource.handleSort(sb, 5, Datasource.QUERY_ALBUMS_BY_ARTIST_SORT);
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
        int expectedResult = 5351;
        assertEquals(expectedResult, actualResult, 10);
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
        List<SongArtist> songArtists = datasource.queryArtistForSong(TEST_SONG_HEARTLESS, 2);
        assertNotNull(songArtists);

        System.out.println("----------------");
        Datasource.printSongArtists(songArtists);
    }

    @Test
    void queryArtistsForSong_null() {
        List<SongArtist> songArtists = datasource.queryArtistForSong(TEST, 3);
        assertNull(songArtists);
    }

    @Test
    void querySongsMetadata_success() {
        boolean actualResult = datasource.querySongsMetadata();
        assertTrue(actualResult);
    }

    @Test
    void queryArtistListView_success() {
        List<SongArtist> actual = datasource.queryArtistListView(TEST_SONG_HEARTLESS);
        assertNotNull(actual);

        System.out.println("----------------");
        Datasource.printSongArtists(actual);
    }

    @Test
    void queryArtistListView_fail() {
        datasource.close();

        List<SongArtist> actual = datasource.queryArtistListView(TEST_SONG_HEARTLESS);
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
        fail(NOT_IMPLEMENTED_FAIL);
//        insertIntoSongs_success();
//        boolean actual = datasource.deleteSong(TEST_SONG_TOUCH_GREY, TEST_ARTIST_GRATEFUL_DEAD);
//        assertTrue(actual);
    }

    @Test
    void deleteSong_false() {
        fail(NOT_IMPLEMENTED_FAIL);
//        insertIntoSongs_success();
//        boolean actual = datasource.deleteSong(TEST_SONG_TOUCH_GREY, TEST_ARTIST_GRATEFUL_DEAD);
//        assertTrue(actual);
    }

    @Test
    void deleteSong_badInput() {
        String testArtist = "";
        String testSong = "";

        boolean actual = datasource.deleteSong(testSong, testArtist);
        assertFalse(actual);
    }
}