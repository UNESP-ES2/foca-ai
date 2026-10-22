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
        
        // teste cadastro
        /*
            c.criarDisciplinas("Matemática");
            c.criarDisciplinas("História");
            c.criarDisciplinas("Geografia");
            c.criarDisciplinas("Português");
        */
        
        // teste listagem
        
        /*
            ArrayList<Disciplina> l = c.listarDisciplinas();
        
            System.out.println("Listagem: ");
            for (Disciplina d : l){
                System.out.println(d.getNome());
            }
        */

        // teste busca
        /*
            Disciplina busca = c.buscarDisciplina("Matemática");

            if (busca != null){
                System.out.println("Busca ->" + busca.getNome());
            }
            else {
                System.out.println("Não encontrado (busca)");
            }
        */


        // teste exclusão
        /* 
            busca = c.buscarDisciplina("Matemática");
            Disciplina disciplinaApagada = c.excluirDisciplina(busca);

            if (disciplinaApagada != null){
                System.out.println("Disciplina apagada -> " + disciplinaApagada.getNome());
            }
            else {
                System.out.println("Não encontrado (exclusão)");
            }

            l = c.listarDisciplinas();

            System.out.println("Listagem: ");
            for (Disciplina d : l){
                System.out.println(d.getNome());
            }
        */
        
        // teste verificação nome já existe
        /* 
            ArrayList<Disciplina> l = c.listarDisciplinas();
            System.out.println("Listagem: ");
            for (Disciplina d : l){
                    System.out.println(d.getNome());
                }
            c.criarDisciplinas("HISTÓRIA");
            c.criarDisciplinas("história");
            c.criarDisciplinas("HiStÓrIa");
            c.criarDisciplinas("Historia");
            c.criarDisciplinas("historia");
            c.criarDisciplinas("HISTORIA");
            c.criarDisciplinas(" História ");
            c.criarDisciplinas("História do Brasil");
            c.criarDisciplinas("História Geral");
        */ 
    }
    
}
