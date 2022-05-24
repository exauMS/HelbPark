module com.helb.helbpark {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.helb.helbpark to javafx.fxml;
    exports com.helb.helbpark;
}