package br.unesp.fct.focaai.view;

import br.unesp.fct.focaai.controller.ControladorDisciplina;
import br.unesp.fct.focaai.model.Disciplina;
import javafx.stage.Stage;

public class FormularioEdicaoDisciplina {

	private ControladorDisciplina controladorDisciplina;
	private Stage stagePrincipal;
	private Runnable acaoPosEdicao;

	public FormularioEdicaoDisciplina(ControladorDisciplina controlador, Stage stage, Runnable acaoPosEdicao) {
		this.controladorDisciplina = controlador;
		this.stagePrincipal = stage;
		this.acaoPosEdicao = acaoPosEdicao;
	}

	private void editarNomeDisciplina(Disciplina disciplina, String novoNome) {

	}

	private Boolean validarNome(String nomeDisicplina) {
		return null;
	}

	public void exibirMensagemSucesso() {

	}

	public void exibirFormularioEdicao(Disciplina disciplina) {

	}

}
