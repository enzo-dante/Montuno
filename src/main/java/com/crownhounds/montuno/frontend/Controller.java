package com.crownhounds.montuno.frontend;

import com.crownhounds.montuno.backend.model.Album;
import com.crownhounds.montuno.backend.model.Artist;
import com.crownhounds.montuno.backend.model.Datasource;
import com.crownhounds.montuno.backend.model.Song;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * always run the SQL query on a background thread using a Task
 * when ready to update UI, update using the JavaFX UI Thread: data binding automatically uses JavaFX UI Thread
 */
public class Controller {

    // CONSTANTS/static class variables assigned FINAL value before compilation/instantiation
    private static final String NOT_IMPLEMENTED_FAIL = "Test failure due to not being implemented";
    private static final String MONTUNO_ARTISTS = "Montuno Artists";
    private static final String MONTUNO_ARTIST_ALBUMS = " Albums";
    private static final String MONTUNO_ARTIST_ALBUM_SONGS = " Songs";
    private static final String UPDATED_AC_DC_NAME = "AC/DC (updated name)";
    private static final String NO_ARTIST_SELECTED = "No artist selected";
    private static final String NO_ALBUM_SELECTED = "No album selected";
    private static final String SEMICOLON= ";";
    private static final String CSS_FX_BACKGROUND_COLOR = "-fx-background-color: ";
    private static final String CSS_FX_TEXT_COLOR = "-fx-text-fill: ";
    private static final String CSS_EXIT = CSS_FX_BACKGROUND_COLOR + "red" + SEMICOLON +
            CSS_FX_TEXT_COLOR + "white" + SEMICOLON;

    // OOP ENCAPSULATION private class fields
    private Artist artistSource;
    private Album albumSource;

    /*
        ! these @FXML variables need to have a matching fx:id in the main.fxml file

        ! @FXML notation allows for methods or variables to be accessed
        for example, as an event handler from the main.fxml file on the respective buttons
     */
    @FXML
    private TableView artistsTable;
    @FXML
    private Button deleteSongForAlbum;
    @FXML
    private Button exit;
    @FXML
    private Button listSongsForAlbum;
    @FXML
    private Button listAlbumsForArtist;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableColumn tableColumnMain;
    @FXML
    private Button updateArtistName;

    private void handleProgressBarUpdate(Task task) {
        // using lambda expressions to manage visibility of progress bar regardless of success or failure
        progressBar.progressProperty().bind(task.progressProperty());

        progressBar.setVisible(true);

        task.setOnSucceeded(event -> progressBar.setVisible(false));
        task.setOnFailed(event -> progressBar.setVisible(false));
    }

    /**
     * This is the method which handles the event from the main.fxml file for listing all artists on the UI.
     */
    @FXML
    public void listArtists() {
        Task<ObservableList<Artist>> task = new GetAllArtistsTask();

        artistsTable.itemsProperty().bind(task.valueProperty());

        setListArtistsState(task);

        // use new Thread to start task and make SQL queries on db
        new Thread(task).start();
    }

    private void setListArtistsState(Task task) {
        handleProgressBarUpdate(task);
        listAlbumsForArtist.setVisible(true);
        listSongsForAlbum.setVisible(false);
        deleteSongForAlbum.setVisible(false);
        exit.setStyle(CSS_EXIT);
        tableColumnMain.setText(MONTUNO_ARTISTS);
        artistSource = null;
        albumSource = null;
    }

    private void setListAlbumsForArtistState(Task task) {
        listAlbumsForArtist.setVisible(false);
        updateArtistName.setVisible(false);
        listSongsForAlbum.setVisible(true);
        deleteSongForAlbum.setVisible(false);
        tableColumnMain.setText(artistSource.getName() + MONTUNO_ARTIST_ALBUMS);
    }

    private void setListSongsForAlbumState(Task task) {
        listSongsForAlbum.setVisible(false);
        deleteSongForAlbum.setVisible(true);
        tableColumnMain.setText(albumSource.getName() + MONTUNO_ARTIST_ALBUM_SONGS);
    }

    /**
     * This is the method which handles the event from the main.fxml file for listing all albums by artist name on the UI.
     */
    @FXML
    public boolean listAlbumsForArtist() {

        // ! CASTING: converting one dataType to a compatible target dataType
        final Artist artist = (Artist) artistsTable.getSelectionModel().getSelectedItem();
        artistSource = artist;

        if(artist == null) {
            System.out.println(NO_ARTIST_SELECTED);
            return false;
        }

        // ! ANONYMOUS INNER CLASS:
        Task<ObservableList<Album>> task = new Task<>() {
            @Override
            protected ObservableList<Album> call() throws Exception {

                Datasource datasource = Datasource.getDatasourceInstance();
                List<Album> albums = datasource.queryAlbumsForArtistId(artist.get_id());

                return FXCollections.observableArrayList(albums);
            }
        };

        // update UI by populating it with db query data on new thread
        artistsTable.itemsProperty().bind(task.valueProperty());

        setListAlbumsForArtistState(task);

        // use new Thread to start task and make SQL queries on db
        new Thread(task).start();
        return true;
    }

    /**
     * This is the method which handles the event from the main.fxml file for listing all albums by artist name on the UI.
     */
    @FXML
    public boolean listSongsForAlbum() {

        // ! CASTING: converting one dataType to a compatible target dataType
        final Album src = (Album) artistsTable.getSelectionModel().getSelectedItem();

        if(src == null) {
            System.out.println(NO_ALBUM_SELECTED);
            return false;
        }

        // ? NEED TO FIX: return is mapped incorrectly to a compatible target dataType
        int album_id = src.getArtistId();
        String albumName = src.getName();
        int artistId = src.get_id();

        Album album = new Album();
        album.set_id(album_id);
        album.setName(albumName);
        album.setArtistId(artistId);

        albumSource = album;

        // ! ANONYMOUS INNER CLASS:
        Task<ObservableList<Song>> task = new Task<>() {
            @Override
            protected ObservableList<Song> call() throws Exception {

                Datasource datasource = Datasource.getDatasourceInstance();
                List<Song> songs = datasource.querySongsByAlbumId(album.get_id());

                return FXCollections.observableArrayList(songs);
            }
        };

        // update UI by populating it with db query data on new thread
        artistsTable.itemsProperty().bind(task.valueProperty());

        setListSongsForAlbumState(task);

        // use new Thread to start task and make SQL queries on db
        new Thread(task).start();
        return true;
    }

    /**
     * This is the method which handles the event from the main.fxml file for listing all albums by artist name on the UI.
     */
    @FXML
    public boolean deleteSongFromAlbum() {

        System.out.println(NOT_IMPLEMENTED_FAIL);
        return true;
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    /**
     * This is the method which handles the event from the main.fxml file for updating an artist name on the backend and for the UI.
     */
    @FXML
    public void updateArtistName() {
        // get SQL record that is cast as an Artist class
        final Artist artist = (Artist) artistsTable.getItems().get(2);

        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                Datasource datasource = Datasource.getDatasourceInstance();
                return datasource.updateArtistName(artist.get_id(), UPDATED_AC_DC_NAME);
            }
        };

        // if task succeeds, then update UI to match updated record in the db on its own thread
        task.setOnSucceeded(event -> {
            if(task.valueProperty().get()) {
                artist.setName(UPDATED_AC_DC_NAME);

                // refresh() forces table to re-draw UI rows for proper table alignment with new db data
                artistsTable.refresh();
            }
        });

        // use new Thread to start task and make SQL queries on db
        new Thread(task).start();
    }
}

class GetAllArtistsTask extends Task {

    /**
     * to maintain model and UI compartmentalization, override call() to return ObservableArrayList
     * ! POLYMORPHISM + INTERFACE: uniquely implement/@Override all publicly shared signatures for designated classes
     * @return a list Artists
     */
    @Override
    public ObservableList<Artist> call() {

        Datasource datasource = Datasource.getDatasourceInstance();
        List<Artist> artists = datasource.queryArtist(Datasource.ORDER_BY_ASC);

        return FXCollections.observableArrayList(artists);
    }
}