package br.unesp.fct.focaai;

import br.unesp.fct.focaai.controller.ControladorDisciplina;
import br.unesp.fct.focaai.model.CatalogoDisciplinas;
import br.unesp.fct.focaai.view.InterfaceModuloDisciplinas;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FocaAiApplication extends Application{

    @Override
    public void start(Stage stage) {
        // Instancia o catálogo e dispara a carga dos dados do JSON (requisito do card)
        CatalogoDisciplinas catalogo = new CatalogoDisciplinas();
        catalogo.carregarJson();

        // Conecta o modelo carregado ao controlador
        ControladorDisciplina controlador = new ControladorDisciplina(catalogo);

        // Inicializa a tela principal com o controlador e o Stage
        InterfaceModuloDisciplinas telaPrincipal = new InterfaceModuloDisciplinas(controlador, stage);
        Parent raiz = telaPrincipal.abrirModuloDisciplinas();

        // Configura e exibe a janela JavaFX
        Scene cena = new Scene(raiz, 800, 550);
        var cssUrl = getClass().getResource("/br/unesp/fct/focaai/styles.css");
        if (cssUrl != null) {
            cena.getStylesheets().add(cssUrl.toExternalForm());
        }
        stage.setTitle("FocaAi - Sistema de Organização de Estudos");
        // Ícones em múltiplas resoluções para nitidez absoluta na barra de título e barra de tarefas do Windows
        var iconPng = getClass().getResource("/br/unesp/fct/focaai/images/corujinha-avatar.png");
        if (iconPng != null) {
            stage.getIcons().addAll(
                new javafx.scene.image.Image(iconPng.toExternalForm(), 16, 16, true, true),
                new javafx.scene.image.Image(iconPng.toExternalForm(), 32, 32, true, true),
                new javafx.scene.image.Image(iconPng.toExternalForm(), 64, 64, true, true),
                new javafx.scene.image.Image(iconPng.toExternalForm(), 128, 128, true, true)
            );
        }
        stage.setMinWidth(720);
        stage.setMinHeight(480);
        stage.setScene(cena);
        stage.show();
    }
}
