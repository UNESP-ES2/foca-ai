package br.unesp.fct.focaai.view;

import br.unesp.fct.focaai.controller.ControladorDisciplina;
import br.unesp.fct.focaai.model.Disciplina;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class FormularioEdicaoDisciplina {

	private ControladorDisciplina controladorDisciplina;
	private Stage stagePrincipal;
	private Runnable acaoPosEdicao;

	private Stage stageModal;
	private TextField campoNome;
	private Label labelErro;
	private Disciplina disciplinaEmEdicao;

	public FormularioEdicaoDisciplina(ControladorDisciplina controlador, Stage stage, Runnable acaoPosEdicao) {
		this.controladorDisciplina = controlador;
		this.stagePrincipal = stage;
		this.acaoPosEdicao = acaoPosEdicao;

		criarComponentes();
	}

	public void criarComponentes() {
		stageModal = new Stage();
		stageModal.initOwner(stagePrincipal);
		stageModal.initModality(Modality.APPLICATION_MODAL);
		stageModal.setTitle("Editar Disciplina");

		campoNome = new TextField();
		labelErro = new Label();
		labelErro.setStyle("-fx-text-fill: red; -fx-font-size: 11px;");

		Button btnSalvar = new Button("Salvar Alterações");
		Button btnCancelar = new Button("Cancelar");

		btnSalvar.setOnAction(e -> {
			if(disciplinaEmEdicao != null) {
				editarNomeDisciplina(disciplinaEmEdicao, campoNome.getText());
			}
		});
		btnCancelar.setOnAction(e -> stageModal.close());

		HBox botoes = new HBox(10, btnSalvar, btnCancelar);
		VBox layout = new VBox(
				new Label("Nome da Disciplina:"),
				campoNome,
				labelErro,
				botoes
		);

		layout.setPadding(new Insets(15));
		stageModal.setScene(new Scene(layout, 320, 200));
	}

	private void editarNomeDisciplina(Disciplina disciplina, String novoNome) {
		if(validarNome(novoNome) == false) return;

		try {
			controladorDisciplina.editarNomeDisciplina(disciplina, novoNome.trim());
			stageModal.close();
			exibirMensagemSucesso();
			if(acaoPosEdicao != null) {
				acaoPosEdicao.run();
			}
		} catch(Exception e) {
			if (e.getMessage() != null) {
				labelErro.setText(e.getMessage());
			} else {
				labelErro.setText("Erro ao editar a disciplina.");
			}
		}
	}

	// TODO: verificar como generalizar para apenas uma classe a validacao, pois utiliza em duas classes diferentes
	private Boolean validarNome(String nomeDisciplina) {
		if(nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
			labelErro.setText("O nome da disciplina não pode ser vazio.");
			return false;
		}
		if(nomeDisciplina.trim().length() > 100) {
			labelErro.setText("O nome não pode exceder 100 caracteres.");
			return false;
		}
		// utiliza regex que permite apenas letras com ou sem acento, números, espaço ou hífen
		if (!nomeDisciplina.matches("[a-zA-Z0-9À-ÖØ-öø-ÿ \\-]+")) {
			labelErro.setText("O nome só pode conter caracteres alfanuméricos.");
			return false;
		}

		// verificação case-insensitive no cadastro
		if (controladorDisciplina.verificarNomeJaExiste(nomeDisciplina) == true){
			labelErro.setText("O nome já existe (case-insensitive).");
			return false;
		}

		labelErro.setText("");
		return true;
	}

	public void exibirMensagemSucesso() {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.initOwner(stagePrincipal);
		alerta.setTitle("Sucesso");
		alerta.setHeaderText(null);
		alerta.setContentText("Disciplina editada com sucesso!");
		alerta.showAndWait();
	}

	public void exibirFormularioEdicao(Disciplina disciplina) {
		if (disciplina == null) return;
		this.disciplinaEmEdicao = disciplina;
		this.campoNome.setText(disciplina.getNome());
		this.labelErro.setText("");
		this.stageModal.showAndWait();
	}

}
