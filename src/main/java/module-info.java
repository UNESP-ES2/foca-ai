module br.unesp.fct.focaai {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;

    // Acesso por reflexão para o JavaFX, JUnit Platform e Jackson
    opens br.unesp.fct.focaai to javafx.fxml, org.junit.platform.commons;
    opens br.unesp.fct.focaai.controller to javafx.fxml, org.junit.platform.commons;
    opens br.unesp.fct.focaai.model to javafx.base, com.fasterxml.jackson.databind;
    opens br.unesp.fct.focaai.view to javafx.graphics, javafx.fxml;

    // Exportação dos pacotes públicos
    exports br.unesp.fct.focaai;
    exports br.unesp.fct.focaai.controller;
    exports br.unesp.fct.focaai.model;
    exports br.unesp.fct.focaai.view;
}