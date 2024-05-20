module progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens progetto to javafx.fxml, javafx.base, javafx.graphics;
    opens progetto.controller to javafx.fxml, javafx.base, javafx.graphics;
    opens progetto.models to javafx.fxml, javafx.base, javafx.graphics;
    exports progetto.controller;
    exports progetto.models;
    exports progetto;
}
