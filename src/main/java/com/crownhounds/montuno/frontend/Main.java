package com.crownhounds.montuno.frontend;

import com.crownhounds.montuno.backend.model.Datasource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import static com.crownhounds.montuno.strings.Errors.FATAL_ERROR;
import static com.crownhounds.montuno.strings.UI.*;

public class Main extends Application {

    /**
     * validate UI has compiled successfully before querying and loading artists into table view
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        URL app = Main.class.getResource(MAIN_FILE);
        FXMLLoader fxmlLoader = new FXMLLoader(app);

        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.listArtists();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle(MONTUNO_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * use lifecycle methods to manage db connection
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        Datasource.getDatasourceInstance().open();

        if(!Datasource.getDatasourceInstance().open()) {

            Controller.showMessageDialog(null, FATAL_ERROR, MONTUNO_TITLE, AlertType.ERROR, null);

            // do not let user interact with application
            Platform.exit();
        }
    }

    /**
     * close db connection
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        Datasource.getDatasourceInstance().close();
    }

    public static void main(String[] args) {
        Datasource.createArtistListViewForSongArtists();
        launch();
    }
}