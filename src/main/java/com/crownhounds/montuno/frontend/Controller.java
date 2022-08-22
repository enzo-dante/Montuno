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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.util.List;

import static com.crownhounds.montuno.strings.CSS.*;
import static com.crownhounds.montuno.strings.Errors.*;
import static com.crownhounds.montuno.strings.SQL.ORDER_BY_ASC;
import static com.crownhounds.montuno.strings.UI.*;

/**
 * always run the SQL query on a background thread using a Task
 * when ready to update UI, update using the JavaFX UI Thread: data binding automatically uses JavaFX UI Thread
 */
public class Controller {

    // CONSTANTS/static class variables assigned FINAL value before compilation/instantiation
    private static final String UPDATED_AC_DC_NAME = "AC/DC (updated name)";

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
        updateArtistName.setVisible(true);
        listSongsForAlbum.setVisible(false);
        deleteSongForAlbum.setVisible(false);
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
            Controller.showMessageDialog(null, ERROR_NO_ARTIST_SELECTED, ERROR_TITLE, AlertType.ERROR, null);
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
            Controller.showMessageDialog(null, ERROR_NO_ALBUM_SELECTED, ERROR_TITLE, AlertType.ERROR, null);
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
     * This is the method which handles the event from the main.fxml file for deleting a song from an album from the database & UI.
     */
    @FXML
    public boolean deleteSongFromAlbum() {

        // ! CASTING: converting dataType to another compatible dataType
        final Song song = (Song) artistsTable.getSelectionModel().getSelectedItem();

        if(song == null) {
            Controller.showMessageDialog(null, ERROR_NO_SONG_SELECTED, ERROR_TITLE, AlertType.ERROR, null);
            return false;
        }

        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                Datasource datasource = Datasource.getDatasourceInstance();
                return datasource.deleteSong(song.getName(), artistSource.getName());
            }
        };

        // if task succeeds, then update UI to match updated record in the db on its own thread
        task.setOnSucceeded(event -> {
            if(task.valueProperty().get()) {

                // ! ANONYMOUS INNER CLASS:
                Task<ObservableList<Song>> task2 = new Task<>() {
                    @Override
                    protected ObservableList<Song> call() throws Exception {

                        Datasource datasource = Datasource.getDatasourceInstance();
                        List<Song> songs = datasource.querySongsByAlbumId(albumSource.get_id());

                        return FXCollections.observableArrayList(songs);
                    }
                };

                // update UI by populating it with db query data on new thread
                artistsTable.itemsProperty().bind(task2.valueProperty());

                setListSongsForAlbumState(task2);

                // use new Thread to start task and make SQL queries on db
                new Thread(task2).start();
            }
        });

        // use new Thread to start task and make SQL queries on db
        new Thread(task).start();
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

        // ! CASTING: converting dataType to another compatible dataType
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

    private static void _showMessageDialog(Window parent, String message, String title, AlertType type, String font) {

        Alert alert = new Alert(type);
        DialogPane dialogPane = alert.getDialogPane();

        alert.initOwner(parent);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setResizable(false);

        dialogPane.setFocusTraversable(true);

        if (font != null) {
            Text text = new Text(message);
            text.setStyle(CSS_FX_FONT_FAMILY + font + ";");

            dialogPane.setStyle(CSS_FX_PADDING + CSS_FX_PADDING_DIMENSIONS + ";");
            dialogPane.contentProperty().set(text);
        }
        alert.showAndWait();
    }

     public static void showMessageDialog(Window parent, String message, String title, AlertType type, String font) {

         System.out.println(message);

         if (Platform.isFxApplicationThread()) {
             _showMessageDialog(parent, message, title, type, font);
         } else {
             Object lock = new Object();
             synchronized (lock) {
                 Platform.runLater(() -> {
                     _showMessageDialog(parent, message, title, type, font);
                     lock.notifyAll();
                 });
             }
             synchronized (lock) {
                 try {
                     lock.wait();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }
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
        List<Artist> artists = datasource.queryArtist(ORDER_BY_ASC);

        return FXCollections.observableArrayList(artists);
    }
}