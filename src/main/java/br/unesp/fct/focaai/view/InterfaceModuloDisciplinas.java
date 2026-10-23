package br.unesp.fct.focaai.view;

import java.util.ArrayList;

import br.unesp.fct.focaai.controller.ControladorDisciplina;
import br.unesp.fct.focaai.model.Disciplina;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		layout.getStyleClass().add("main-window-root");

		VBox cardContainer = new VBox();
		cardContainer.getStyleClass().add("content-card");

		// Cabeçalho amigável voltado ao estudante / usuário
		Label lblTitulo = new Label("Minhas Disciplinas");
		lblTitulo.getStyleClass().add("app-title");

		Label lblSubtitulo = new Label("Organize suas matérias e planeje seus estudos no FocaAi");
		lblSubtitulo.getStyleClass().add("app-subtitle");

		VBox titulos = new VBox(2, lblTitulo, lblSubtitulo);

		// Mascote Corujinha carismática e balãozinho de boas-vindas
		ImageView imgCorujinha = new ImageView();
		Image imgMascote = carregarImagemMascote(80, 80);
		if (imgMascote != null) {
			imgCorujinha.setImage(imgMascote);
			imgCorujinha.setFitWidth(80);
			imgCorujinha.setFitHeight(80);
			imgCorujinha.setPreserveRatio(true);
			imgCorujinha.setSmooth(true);
		}

		Label lblBalaoTitulo = new Label("Bora focar hoje? 🦉✨");
		lblBalaoTitulo.getStyleClass().add("speech-bubble-title");

		Label lblBalaoSub = new Label("Vamos organizar suas matérias com calma!");
		lblBalaoSub.getStyleClass().add("speech-bubble-subtitle");

		VBox speechBubble = new VBox(2, lblBalaoTitulo, lblBalaoSub);
		speechBubble.getStyleClass().add("speech-bubble");
		speechBubble.setAlignment(Pos.CENTER_LEFT);

		HBox mascotBox = new HBox(10, speechBubble, imgCorujinha);
		mascotBox.setAlignment(Pos.CENTER_RIGHT);

		Region spacerHeader = new Region();
		HBox.setHgrow(spacerHeader, Priority.ALWAYS);

		HBox header = new HBox(titulos, spacerHeader, mascotBox);
		header.getStyleClass().add("header-container");
		header.setAlignment(Pos.CENTER_LEFT);

		// Tabela de disciplinas (LIFO)
		tabela = new TableView<>();
		// Disciplina - tipo do objeto que representa a linha inteira
		// String - tipo do dado que será exibido na coluna
		tabela.getStyleClass().add("custom-table");
		tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox.setVgrow(tabela, Priority.ALWAYS);

		TableColumn<Disciplina, String> coluna = new TableColumn<>("Nome da Disciplina");
		// define o que será exibido na tabela, nesse caso, o nome da disciplina
		coluna.setCellValueFactory(disciplina -> new SimpleStringProperty(disciplina.getValue().getNome()));
		tabela.getColumns().add(coluna);

		// Campo de busca
		VBox placeholder = new VBox(10);
		placeholder.setAlignment(Pos.CENTER);
		Image imgPlaceholderMascote = carregarImagemMascote(96, 96);
		if (imgPlaceholderMascote != null) {
			ImageView imgPlaceholder = new ImageView(imgPlaceholderMascote);
			imgPlaceholder.setFitWidth(96);
			imgPlaceholder.setFitHeight(96);
			imgPlaceholder.setPreserveRatio(true);
			imgPlaceholder.setSmooth(true);
			placeholder.getChildren().add(imgPlaceholder);
		}
		Label lblVazioTitulo = new Label("Nenhuma matéria por aqui ainda!");
		lblVazioTitulo.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1E1B4B;");
		Label lblVazioSub = new Label("Clique em '+ Cadastrar' para começar sua jornada no FocaAi 🦉✨");
		lblVazioSub.setStyle("-fx-font-size: 12.5px; -fx-text-fill: #64748B;");
		placeholder.getChildren().addAll(lblVazioTitulo, lblVazioSub);
		tabela.setPlaceholder(placeholder);

		// Campo de busca estilo pílula
		campoBusca = new TextField();
		campoBusca.setPromptText("🔍 Buscar disciplina por nome...");
		campoBusca.getStyleClass().add("search-field");
		campoBusca.setMaxWidth(Double.MAX_VALUE);
		// permite o campo crecer até o maximo da tela restante
		HBox.setHgrow(campoBusca, Priority.ALWAYS);
		// obs - Ouvinte para mudanças no texto de busca
		// oldV - valor antigo do texto
		// newV - novo valor do texto
		campoBusca.textProperty().addListener((obs, oldV, newV) -> buscarDisciplina(newV));

		// Botões de ação estilizados com semântica de cores
		Button btnCadastrar = new Button("+ Cadastrar");
		btnCadastrar.getStyleClass().add("btn-primary");

		Button btnEditar = new Button("✏ Editar");
		btnEditar.getStyleClass().add("btn-secondary");

		Button btnExcluir = new Button("🗑 Excluir");
		btnExcluir.getStyleClass().add("btn-danger");

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
		HBox toolbar = new HBox(12, botoes, campoBusca);
		toolbar.getStyleClass().add("toolbar-container");
		toolbar.setAlignment(Pos.CENTER_LEFT);

		cardContainer.getChildren().addAll(header, toolbar, tabela);
		layout.setCenter(cardContainer);

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
		ArrayList<Disciplina> disciplina = controladorDisciplina.buscarDisciplinaTrecho(nomeDisciplina);

		if(disciplina != null) {
			tabela.setItems(FXCollections.observableArrayList(disciplina));
		} else{
			tabela.getItems().clear();
		}

		if("".equals(nomeDisciplina)) {
			listarDisciplinas();
		}
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

		var cssUrl = getClass().getResource("/br/unesp/fct/focaai/styles.css");
		if (cssUrl != null) {
			alerta.getDialogPane().getStylesheets().add(cssUrl.toExternalForm());
		}

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

		var cssUrl = getClass().getResource("/br/unesp/fct/focaai/styles.css");
		if (cssUrl != null) {
			alertaSucesso.getDialogPane().getStylesheets().add(cssUrl.toExternalForm());
		}

		alertaSucesso.showAndWait();
	}

	public void selecionarDisciplinaParaEdicao(Disciplina disciplina) {
		formularioEdicaoDisciplina.exibirFormularioEdicao(disciplina);
	}

	/**
	 * Carrega a imagem da mascote com supersampling de 2x e filtragem suave acelerada por hardware.
	 * Garante curvas anti-aliased perfeitas, eliminando qualquer efeito serrilhado ou borrado em telas modernas e HiDPI.
	 */
	private Image carregarImagemMascote(int larguraAlvo, int alturaAlvo) {
		var pngUrl = getClass().getResource("/br/unesp/fct/focaai/images/corujinha-char.png");
		if (pngUrl != null) {
			return new Image(pngUrl.toExternalForm(), larguraAlvo * 2, alturaAlvo * 2, true, true);
		}
		return null;
	}

}
