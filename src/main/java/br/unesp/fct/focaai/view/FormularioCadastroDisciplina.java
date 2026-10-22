package br.unesp.fct.focaai.view;

import br.unesp.fct.focaai.controller.ControladorDisciplina;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FormularioCadastroDisciplina {

    private ControladorDisciplina controladorDisciplina;
    private Stage stagePrincipal;
    private Runnable acaoPosCadastro;

    private Stage stageModal;
    private TextField campoNome;
    private Label labelErro;

    public FormularioCadastroDisciplina(ControladorDisciplina controlador, Stage stage, Runnable acaoPosCadastro) {
        this.controladorDisciplina = controlador;
        this.stagePrincipal = stage;
        this.acaoPosCadastro = acaoPosCadastro;

        criarComponentes();
    }

    private void criarComponentes() {
        // Stage é a janela do Sistema Operacional
        stageModal = new Stage();
        stageModal.initOwner(stagePrincipal);
        stageModal.initModality(Modality.APPLICATION_MODAL); // bloqueia a tela de fundo

        campoNome = new TextField();
        campoNome.setPromptText("ex: Engenharia de Software II");

        labelErro = new Label();
        labelErro.setStyle("-fx-text-fill: red; -fx-font-size: 11px;");

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnSalvar.setOnAction(e -> criarDisciplina());
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

    public void exibir() {
        campoNome.clear();
        labelErro.setText("");
        stageModal.showAndWait();
    }

    public void criarDisciplina() {
        String nome = campoNome.getText();
        if(validarNome(nome) == false) return;

        try {
            controladorDisciplina.criarDisciplina(nome.trim());
            stageModal.close();
            exibirMensagemSucesso();

            if(acaoPosCadastro != null) {
                acaoPosCadastro.run(); // atualiza a tabela na tela principal
            }
        } catch(Exception e) {
            if(e.getMessage() != null) {
                labelErro.setText(e.getMessage());
            } else {
                labelErro.setText("Erro ao cadastrar a disciplina.");
            }
        }
    }

    public Boolean validarNome(String nomeDisciplina) {
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
        alerta.setContentText("Disciplina cadastrada com sucesso!");
        alerta.showAndWait();
    }
}
