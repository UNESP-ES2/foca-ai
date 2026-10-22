/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unesp.fct.focaai;

import br.unesp.fct.focaai.controller.ControladorDisciplina;
import br.unesp.fct.focaai.model.Disciplina;
import java.util.ArrayList;

/**
 *
 * @author Cauan
 */
public class TestarControlador {
    
    public static void main(String[] args) {
        
        ControladorDisciplina controladorDisciplina = new ControladorDisciplina();

        
        ArrayList<Disciplina> lista = controladorDisciplina.listarDisciplinas();
        
        for (Disciplina l : lista){
            System.out.println(l.getNome());
        }
        
        Disciplina b = controladorDisciplina.buscarDisciplina("Portugues");
        
        controladorDisciplina.editarNomeDisciplina(b, "Quimica");
        
        
        lista = controladorDisciplina.listarDisciplinas();
        
        for (Disciplina l : lista){
            System.out.println(l.getNome());
        }
        
    }
            
    
}
