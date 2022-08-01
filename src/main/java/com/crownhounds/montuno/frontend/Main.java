package com.crownhounds.montuno.frontend;

import com.crownhounds.montuno.backend.model.Datasource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    // CONSTANTS/static class variables assigned FINAL value before compilation/instantiation
    private static final String MONTUNO_TITLE = "Montuno";
    private static final String MAIN_FILE = "main.fxml";
    private static final String FATAL_ERROR = "FATAL ERROR: Couldn't connect to database";

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
            // implement popup dialog
            System.out.println(FATAL_ERROR);

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
        launch();
    }
}