module br.unesp.fct.focaai {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.junit.jupiter.api;

    // Acesso do JavaFX e Jackson aos pacotes
    opens br.unesp.fct.focaai to javafx.fxml, org.junit.platform.commons;
    opens br.unesp.fct.focaai.controller to javafx.fxml, org.junit.platform.commons;
    opens br.unesp.fct.focaai.model to javafx.base, com.fasterxml.jackson.databind;
    opens br.unesp.fct.focaai.view to javafx.graphics, javafx.fxml;

    // permite que a biblioteca Jackson consiga acessar construtor, getters e setters
    opens br.unesp.fct.focaai.model to com.fasterxml.jackson.databind;

    exports br.unesp.fct.focaai;
    exports br.unesp.fct.focaai.controller;
    exports br.unesp.fct.focaai.model;
    exports br.unesp.fct.focaai.view;
    opens br.unesp.fct.focaai.controller to javafx.fxml, org.junit.platform.commons;
}