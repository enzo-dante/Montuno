package com.crownhounds.montuno.frontend;

import com.crownhounds.montuno.backend.model.Album;
import com.crownhounds.montuno.backend.model.Artist;
import com.crownhounds.montuno.backend.model.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * always run the SQL query on a background thread using a Task
 * when ready to update UI, update using the JavaFX UI Thread: data binding automatically uses JavaFX UI Thread
 */
public class Controller {

    // CONSTANTS/static class variables assigned FINAL value before compilation/instantiation
    private static final String UPDATED_ARTIST_NAME = "AC DC";
    private static final String NO_ARTIST_SELECTED = "No artist selected";

    /*
        ! these @FXML variables need to have a matching fx:id in the main.fxml file

        ! @FXML notation allows for methods or variables to be accessed
        for example, as an event handler from the main.fxml file on the respective buttons
     */
    @FXML
    private TableView artistsTable;

    @FXML
    private ProgressBar progressBar;

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

        handleProgressBarUpdate(task);

        // use new Thread to start task and make SQL queries on db
        new Thread(task).start();
    }

    /**
     * This is the method which handles the event from the main.fxml file for listing all albums by artist name on the UI.
     */
    @FXML
    public boolean listAlbumsForArtist() {

        // ! CASTING: converting one dataType to a compatible target dataType
        final Artist artist = (Artist) artistsTable.getSelectionModel().getSelectedItem();

        if(artist == null) {
            System.out.println(NO_ARTIST_SELECTED);
            return false;
        }

        // anonymous class
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

        // use new Thread to start task and make SQL queries on db
        new Thread(task).start();
        return true;
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
                return datasource.updateArtistName(artist.get_id(), Datasource.UPDATE_ARTIST_NAME);
            }
        };

        // if task succeeds, then update UI to match updated record in the db on it's own thread
        task.setOnSucceeded(event -> {
            if(task.valueProperty().get()) {
                artist.setName(UPDATED_ARTIST_NAME);
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