module br.unesp.fct.focaai {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    // Permite ao módulo ler e utilizar a API do JUnit 5 nos testes
    requires org.junit.jupiter.api;

    // Permite a reflexão para o JavaFX e o JUnit 5
    opens br.unesp.fct.focaai to javafx.fxml, org.junit.platform.commons;

    exports br.unesp.fct.focaai;
}