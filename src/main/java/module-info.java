module br.unesp.fct.focaai {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    // Permite a reflexão para o JavaFX e o JUnit 5
    opens br.unesp.fct.focaai to javafx.fxml, org.junit.platform.commons;

    // permite que a biblioteca Jackson consiga acessar construtor, getters e setters
    opens br.unesp.fct.focaai.model to com.fasterxml.jackson.databind;

    exports br.unesp.fct.focaai;
    exports br.unesp.fct.focaai.controller;
    opens br.unesp.fct.focaai.controller to javafx.fxml, org.junit.platform.commons;
}