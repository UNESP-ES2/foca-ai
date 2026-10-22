package br.unesp.fct.focaai.controller;

import br.unesp.fct.focaai.model.CatalogoDisciplinas;
import br.unesp.fct.focaai.model.Disciplina;
import java.util.ArrayList;

public class ControladorDisciplina {

	private CatalogoDisciplinas catalogoDisciplinas;

	// construtor que inicializa o catalogoDisciplinas
	public ControladorDisciplina() {
		this.catalogoDisciplinas = new CatalogoDisciplinas();
	}

	public ControladorDisciplina(CatalogoDisciplinas catalogo) {
	}

	public void editarNomeDisciplina(Disciplina disciplina, String novoNome) {

	}

	public void criarDisciplina(String nomeDisciplina) {

	}

	public Disciplina buscarDisciplina(String nomeDisciplina) {
		return null;
	}

	public ArrayList listarDisciplinas() {
		return catalogoDisciplinas.listarDisciplinas();
	}

	public void excluirDisciplina(Disciplina disciplinaParaExclusao) {

	}

	public void confirmarExclusao(Disciplina disciplina, boolean proceder) {

	}

}
