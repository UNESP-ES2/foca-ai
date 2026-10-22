package br.unesp.fct.focaai.controller;

import br.unesp.fct.focaai.model.CatalogoDisciplinas;
import br.unesp.fct.focaai.model.Disciplina;
import java.util.ArrayList;

public class ControladorDisciplina {

    private CatalogoDisciplinas catalogoDisciplinas;

    public ControladorDisciplina() {
        this.catalogoDisciplinas = new CatalogoDisciplinas();
    }

    public void criarDisciplina(String nomeDisciplina) {
        catalogoDisciplinas.criarDisciplinas(nomeDisciplina);
    }

    public ArrayList<Disciplina> listarDisciplinas() {
        return catalogoDisciplinas.getDisciplinas();
    }

    public void editarNomeDisciplina(Disciplina disciplina, String novoNome) {

        boolean existe = catalogoDisciplinas.verificarNomeJaExiste(novoNome);

        if (existe == false && disciplina != null) {
            for (Disciplina d: catalogoDisciplinas.listarDisciplinas()){
                if (d.getNome().equals(disciplina.getNome())){
                    
                    // procura na lista a disciplina e altera o nome dela
                    d.setNome(novoNome);
                    
                    break;
                }
            }
            catalogoDisciplinas.salvarJson();
        }
    }

    public void confirmarExclusao(Disciplina disciplina, boolean proceder) {
        if (proceder == true && disciplina != null) {
            catalogoDisciplinas.excluirDisciplina(disciplina);
        }
    }

    // função ainda não completa, pois 'registros' não é dessa sprint
    public void excluirDisciplina(Disciplina disciplinaParaExclusao) {
        catalogoDisciplinas.verificarRegistrosSessoes(disciplinaParaExclusao);
    }

    public Disciplina buscarDisciplina(String nomeDisciplina) {
        Disciplina busca = catalogoDisciplinas.buscarDisciplina(nomeDisciplina);

        return busca;
    }

}
