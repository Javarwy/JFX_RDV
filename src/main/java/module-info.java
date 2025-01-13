module appli.lprs.jfx_rdv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens appli.lprs.jfx_rdv to javafx.fxml;
    exports appli.lprs.jfx_rdv;
    exports appli.lprs.jfx_rdv.accueil;
    opens appli.lprs.jfx_rdv.accueil to javafx.fxml;
}