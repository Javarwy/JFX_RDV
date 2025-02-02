module appli.lprs.jfx_rdv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires spring.security.crypto;

    opens appli to javafx.fxml;
    exports appli;
    exports appli.accueil;
    opens appli.accueil to javafx.fxml;
    exports appli.professeur;
    opens appli.professeur to javafx.fxml;
    exports appli.secretaire;
    opens appli.secretaire to javafx.fxml;
    exports modeles;
    opens modeles to javafx.base;
}