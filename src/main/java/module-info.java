module com.crownhounds.montuno.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.crownhounds.montuno.backend.model;
    exports com.crownhounds.montuno.frontend;
    opens com.crownhounds.montuno.frontend to javafx.fxml;
}



