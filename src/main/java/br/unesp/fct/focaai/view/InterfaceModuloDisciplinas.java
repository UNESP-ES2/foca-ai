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
		layout.setPadding(new Insets(15));

		// Tabela de disciplinas (LIFO)
		tabela = new TableView<>();
		// Disciplina - tipo do objeto que representa a linha inteira
		// String - tipo do dado que será exibido na coluna
		TableColumn<Disciplina, String> coluna = new TableColumn<>("Nome da Disciplina");
		// define o que será exibido na tabela, nesse caso, o nome da disciplina
		coluna.setCellValueFactory(disciplina -> new SimpleStringProperty(disciplina.getValue().getNome()));
		coluna.setPrefWidth(450);
		tabela.getColumns().add(coluna);
		tabela.setPlaceholder(new Label("Nenhuma disciplina cadastrada."));

		// Campo de busca
		campoBusca = new TextField();
		campoBusca.setPromptText("Buscar disciplina...");
		campoBusca.setMaxWidth(Double.MAX_VALUE);
		// permite o campo crecer até o maximo da tela restante
		HBox.setHgrow(campoBusca, Priority.ALWAYS);
		// obs - Ouvinte para mudanças no texto de busca
		// oldV - valor antigo do texto
		// newV - novo valor do texto
		campoBusca.textProperty().addListener((obs, oldV, newV) -> buscarDisciplina(newV));

		// Botões de ação disparando as mensagens do diagrama
		Button btnCadastrar = new Button("Cadastrar");
		Button btnEditar = new Button("Editar");
		Button btnExcluir = new Button("Excluir");

		btnCadastrar.setOnAction(e -> selecionarOpcoesCadastro());

		btnEditar.setOnAction(e -> {
			Disciplina selecionado = tabela.getSelectionModel().getSelectedItem();
			if (selecionado != null)
				selecionarDisciplinaParaEdicao(selecionado);
		});

		btnExcluir.setOnAction(e -> {
			Disciplina selecionado = tabela.getSelectionModel().getSelectedItem();
			if (selecionado != null)
				excluirDisciplina(selecionado);
		});

		// Horizontal Box - preenche os elementos de forma horizontal
		// Vertical Box - preenche os elementos de forma vertical
		HBox botoes = new HBox(10, btnCadastrar, btnEditar, btnExcluir);
		HBox topo = new HBox(10, botoes, campoBusca);
		topo.setPadding(new Insets(0, 0, 10, 0));
		layout.setTop(topo);
		layout.setCenter(tabela);
		// Dispara a listagem inicial
		listarDisciplinas();

		return layout;
	}

	public void selecionarOpcoesCadastro() {
	formularioCadastroDisciplina.exibir();
	}

	private void listarDisciplinas() {
		ArrayList<Disciplina> disciplinas = controladorDisciplina.listarDisciplinas();
		exibirListagemDisciplinas(disciplinas);
	}

	public void exibirListagemDisciplinas(ArrayList<Disciplina> disciplinas) {
		if(disciplinas != null) {
			tabela.setItems(FXCollections.observableArrayList(disciplinas));
		} else {
			tabela.getItems().clear();
		}
	}

	public void buscarDisciplina(String nomeDisciplina) {

	}

	public void validarNome(String nomeDisciplina) {

	}

	public ArrayList exibirDisciplinasEncontradas() {
		return null;
	}

	// Guarda temporariamente a disciplina selecionada para o fluxo de confirmação
	private Disciplina disciplinaEmExclusao;

	public void excluirDisciplina(Disciplina disciplinaParaExclusao) {
		if(disciplinaParaExclusao == null) return; // retorna se nenhuma disciplina for selecionada
		this.disciplinaEmExclusao = disciplinaParaExclusao;

		// TODO: Na sprint-1 (BL-1) ainda nao foram implementadas sessoes de estudo (sempre falso, por enquanto)
		boolean temSessoes = false;

		exibirConfirmacaoComAlertas(temSessoes);
	}

	public void exibirConfirmacaoComAlertas(boolean temSessoesVinculadas) {
		if(disciplinaEmExclusao == null) return;

		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.initOwner(stagePrincipal);
		alerta.setTitle("Confirmação de Exclusão");
		alerta.setHeaderText("Excluir a disciplina \"" + disciplinaEmExclusao.getNome() + "\"?");

		// Mensagem diferenciada segundo os requisitos
		if (temSessoesVinculadas) {
			alerta.setContentText("Atenção: existem sessões de estudo vinculadas que também serão afetadas.\nDeseja realmente continuar?");
		} else {
			alerta.setContentText("Esta ação não poderá ser desfeita. Deseja prosseguir?");
		}
		// Espera a resposta do usuário
		alerta.showAndWait().ifPresent(botaoEscolhido -> {
			boolean proceder = (botaoEscolhido == ButtonType.OK);
			confirmarExclusao(disciplinaEmExclusao, proceder);
		});
	}

	public void confirmarExclusao(Disciplina disciplina, boolean proceder) {
		if(proceder) {
			// Envia a confiormacao para o controlador persistir no catalogo
			controladorDisciplina.confirmarExclusao(disciplina, true);

			// exibir alerta de sucesso e atualiza a tabela
			exibirMensagemSucesso();

			// atualizando a tablea de disciplinas
			listarDisciplinas();
		}

		this.disciplinaEmExclusao = null;
	}

	public void exibirMensagemSucesso() {
		Alert alertaSucesso = new Alert(Alert.AlertType.INFORMATION);
		alertaSucesso.initOwner(stagePrincipal);
		alertaSucesso.setTitle("Sucesso");
		alertaSucesso.setHeaderText(null);
		alertaSucesso.setContentText("Disciplina excluída com sucesso!");
		alertaSucesso.showAndWait();
	}

	public void selecionarDisciplinaParaEdicao(Disciplina disciplina) {
		formularioEdicaoDisciplina.exibirFormularioEdicao(disciplina);
	}

}
