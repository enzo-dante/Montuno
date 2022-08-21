package com.crownhounds.montuno.frontend;

import com.crownhounds.montuno.backend.model.Datasource;
import org.junit.jupiter.api.*;

import static com.crownhounds.montuno.strings.Tests.*;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    // OOP ENCAPSULATION private class fields
    Datasource datasource;
    Controller controller;
    boolean isOpen;
    boolean isClosed;

    @BeforeAll
    static void beforeAll() {
        System.out.println(BEFORE_ALL_TESTS);
        Datasource.dropArtistListView();
        Datasource.createArtistListViewForSongArtists();
    }

    @AfterAll
    static void afterAll() {
        System.out.println(AFTER_ALL_TESTS);
        Datasource.dropArtistListView();
    }

    @BeforeEach
    void setUp() {
        datasource = Datasource.getDatasourceInstance();
        controller = new Controller();
        isOpen = datasource.open();
    }

    @AfterEach
    void tearDown() {
        isClosed = datasource.close();
    }

    @Test
    void listArtists() {
        fail(NOT_IMPLEMENTED_FAIL);

    }

    @Test
    void listAlbumsForArtist() {
         fail(NOT_IMPLEMENTED_FAIL);

    }

    @Test
    void listSongsForAlbum() {
         fail(NOT_IMPLEMENTED_FAIL);

    }

    @Test
    void deleteSongFromAlbum() {
        fail(NOT_IMPLEMENTED_FAIL);

    }

    @Test
    void exit() {
         fail(NOT_IMPLEMENTED_FAIL);

    }

    @Test
    void updateArtistName() {
         fail(NOT_IMPLEMENTED_FAIL);

    }

    @Test
    void showMessageDialog() {
         fail(NOT_IMPLEMENTED_FAIL);

    }
}