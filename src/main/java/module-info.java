module com.libsystem.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;

    opens com.libsystem.librarymanagementsystem.controller to javafx.fxml;
    exports com.libsystem.librarymanagementsystem.controller;
    opens com.libsystem.librarymanagementsystem.model to java.base;
    exports com.libsystem.librarymanagementsystem.model;
}