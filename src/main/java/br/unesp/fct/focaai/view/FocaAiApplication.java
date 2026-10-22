package br.unesp.fct.focaai.view;

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

        // COnfigura e exibe a janela JavaFX
        Scene cena = new Scene(raiz, 650, 480);
        stage.setTitle("Foca-aí - Sistema de Organização de Estudos");
        stage.setScene(cena);
        stage.show();
    }
}
