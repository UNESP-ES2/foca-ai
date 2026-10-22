package br.unesp.fct.focaai.view;

import java.util.ArrayList;

import br.unesp.fct.focaai.controller.ControladorDisciplina;
import br.unesp.fct.focaai.model.Disciplina;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InterfaceModuloDisciplinas {

	private FormularioCadastroDisciplina formularioCadastroDisciplina;

	private FormularioEdicaoDisciplina formularioEdicaoDisciplina;

	private ControladorDisciplina controladorDisciplina;

	private InterfaceApresentacaoDisciplinas interfaceApresentacaoDisciplinas;

	// Componentes gráficos JavaFX
	private Stage stagePrincipal;
	private TableView<Disciplina> tabela;
	private TextField campoBusca;

	public InterfaceModuloDisciplinas(ControladorDisciplina controlador, Stage stage) {
		this.controladorDisciplina = controlador;
		this.stagePrincipal = stage;
		this.formularioCadastroDisciplina = new FormularioCadastroDisciplina(controlador, stage,
				this::listarDisciplinas);
		this.formularioEdicaoDisciplina = new FormularioEdicaoDisciplina(controlador, stage, this::listarDisciplinas);
	}

	// métoddo que inicia o módulo e monta a composiçao visual
	public Parent abrirModuloDisciplinas() {
		BorderPane layout = new BorderPane();

		return layout;
	}

	public void selecionarOpcoesCadastro() {

	}

	private void listarDisciplinas() {

	}

	public void exibirListagemDisciplinas(ArrayList Disciplinas) {

	}

	public void buscarDisciplina(String nomeDisciplina) {

	}

	public void validarNome(String nomeDisciplina) {

	}

	public ArrayList exibirDisciplinasEncontradas() {
		return null;
	}

	public void excluirDisciplina(Disciplina disciplinaParaExclusao) {

	}

	public void exibirConfirmacaoComAlertas(boolean temSessoesVinculadas) {

	}

	public void confirmarExclusao(Disciplina disciplina, boolean proceder) {

	}

	public void exibirMensagemSucesso() {

	}

	public void selecionarDisciplinaParaEdicao(Disciplina disciplina) {

	}

}
