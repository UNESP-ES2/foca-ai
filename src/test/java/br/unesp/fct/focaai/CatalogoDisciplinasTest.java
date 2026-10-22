/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unesp.fct.focaai;

import br.unesp.fct.focaai.model.CatalogoDisciplinas;
import br.unesp.fct.focaai.model.Disciplina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Cauan
 */
public class CatalogoDisciplinasTest {
    
    
    private CatalogoDisciplinas catalogoDisciplinas;

    // cria uma pasta temporária
    @TempDir
    File pastaTemporaria;

    @BeforeEach
    public void setUp() {
        // passa o caminho da pasta temporária para o novo construtor
        catalogoDisciplinas = new CatalogoDisciplinas(pastaTemporaria.getAbsolutePath());

        catalogoDisciplinas.setDisciplinas(new ArrayList<>());
        catalogoDisciplinas.salvarJson(); // salva só no arquivo temporário!
    }
    
    @Test
    public void deveCriarDisciplinaComSucesso() {
        catalogoDisciplinas.criarDisciplinas("Matemática");

        Disciplina encontrada = catalogoDisciplinas.buscarDisciplina("Matemática");
        
        assertNotNull(encontrada, "A disciplina deveria ter sido criada");
        assertEquals("Matemática", encontrada.getNome());
    }
    
    @Test
    public void naoDeveCriarDisciplina(){
        catalogoDisciplinas.criarDisciplinas("Matemática");
        catalogoDisciplinas.criarDisciplinas("MATEMÁTICA");
        
        ArrayList<Disciplina> lista = catalogoDisciplinas.listarDisciplinas();
        
        assertEquals(1, lista.size(), "Não deve permitir disciplinas com mesmo nome");
    }
    
    @Test
    public void deveVerificarSeNomeJaExiste(){
        catalogoDisciplinas.criarDisciplinas("Matemática");
        
        boolean verif1 = catalogoDisciplinas.verificarNomeJaExiste("Matemática");
        boolean verif2 = catalogoDisciplinas.verificarNomeJaExiste("Quimica");
        boolean verif3 = catalogoDisciplinas.verificarNomeJaExiste("História");
        
        
        assertTrue(verif1, "Deve retornar true para nome já existente");
        assertFalse(verif2, "Deve retornar false para nome inexistente");
        assertFalse(verif3, "Deve retornar false para nome inexistente");
    }
    
    @Test
    public void deveExcluirDisciplinaComSucesso() {
        catalogoDisciplinas.criarDisciplinas("Geografia");
        Disciplina d = catalogoDisciplinas.buscarDisciplina("Geografia");

        assertNotNull(d);

        Disciplina removida = catalogoDisciplinas.excluirDisciplina(d);

        assertNotNull(removida);
        assertEquals("Geografia", removida.getNome());
        assertNull(catalogoDisciplinas.buscarDisciplina("Geografia"), "A disciplina não deve mais existir no catálogo");
    }
}
