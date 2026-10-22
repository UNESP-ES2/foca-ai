package br.unesp.fct.focaai.view;

import br.unesp.fct.focaai.controller.ControladorDisciplina;
import javafx.stage.Stage;

public class FormularioCadastroDisciplina {

    private ControladorDisciplina controladorDisciplina;
    private Stage stagePrincipal;
    private Runnable acaoPosCadastro;

    public FormularioCadastroDisciplina(ControladorDisciplina controlador, Stage stage, Runnable acaoPosCadastro) {
        this.controladorDisciplina = controlador;
        this.stagePrincipal = stage;
        this.acaoPosCadastro = acaoPosCadastro;
    }

}
