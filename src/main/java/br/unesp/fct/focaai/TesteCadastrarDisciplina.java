/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unesp.fct.focaai;

import br.unesp.fct.focaai.model.CatalogoDisciplinas;
import br.unesp.fct.focaai.model.Disciplina;
import java.util.ArrayList;

/**
 *
 * @author Cauan
 */
public class TesteCadastrarDisciplina {
 
    public static void main(String[] args) {
        CatalogoDisciplinas c = new CatalogoDisciplinas();
        
        ArrayList<Disciplina> l = c.listarDisciplinas();
        
        for (Disciplina d : l){
            System.out.println(d.getNome());
        }
        
        Disciplina busca = c.buscarDisciplina("Matemática");
        
        if (busca != null){
            System.out.println(busca.getNome());
        }
        else {
            System.out.println("Não encontrado");
        }
        
    }
    
}
