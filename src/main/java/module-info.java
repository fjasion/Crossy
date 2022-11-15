module Main {
        requires javafx.controls;
        requires javafx.fxml;

        requires org.controlsfx.controls;
        requires com.dlsc.formsfx;
        requires org.kordamp.bootstrapfx.core;

        opens Main to javafx.fxml;
        exports Main;
        }