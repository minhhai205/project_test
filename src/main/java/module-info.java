module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens app.controllers to javafx.fxml;
    opens app.domain to javafx.base;

    exports app;
}
