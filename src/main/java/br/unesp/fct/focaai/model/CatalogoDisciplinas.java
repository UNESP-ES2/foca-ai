/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package br.unesp.fct.focaai.model;

import br.unesp.fct.focaai.model.Disciplina;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Cauan
 */

public class CatalogoDisciplinas{
    
    private ArrayList<Disciplina> disciplinas = new ArrayList<>();
    private final File arquivoJson;
    private final ObjectMapper objectMapper;
    
    public CatalogoDisciplinas(){
        
        String caminhoPacote = "src/main/java/br/unesp/fct/focaai/Json"; 
        
        File pastaDiretorio = new File(caminhoPacote);
        
        arquivoJson = new File(pastaDiretorio, "disciplinas.json");
        
        // o object mapper serve para formatar o JSON com quebras de linha!
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        carregarJson();
    }
    
    
    public ArrayList<Disciplina> getDisciplinas() {
        
        // precisa ler para manter atualizado!
        carregarJson();
        
        return disciplinas;
    }

    public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
    
    public void criarDisciplinas (String nomeDisciplina){
        
        boolean exist = verificarNomeJaExiste(nomeDisciplina);
        
        
        if (exist == false){
            // precisa ler para manter atualizado!
            carregarJson();

            Disciplina d = new Disciplina(nomeDisciplina);

            disciplinas.add(d);

            salvarJson();
        }
    }
    
    
    public Disciplina buscarDisciplina (String nomeDisciplina){
        
        // precisa ler para manter atualizado!
        carregarJson();
        
        for (Disciplina c: disciplinas){
            if (c.getNome().equals(nomeDisciplina)){
                return c;
            }
        }
        
        // tem que fazer o tratamento no controlador!
        return null;
        
    }
    
    
    public ArrayList<Disciplina> listarDisciplinas (){
        
        // precisa ler para manter atualizado!
        carregarJson();
        return disciplinas;
    }
    
    
    
    public Disciplina excluirDisciplina (Disciplina disciplina){
        // precisa ler para manter atualizado!
        carregarJson();
        
        Disciplina removida = null;
        
        if (disciplina == null){
            return null;
        }
        
        for (Disciplina d: disciplinas){
            if (disciplina.getNome().equals(d.getNome())){
                removida = d;
                
                disciplinas.remove(d);
                
                break;
            }
        }
        
        // após remover da lista, deve-se reescrever o Json!
        salvarJson();
        
        return removida;
    }
    
    
    
    public boolean verificarNomeJaExiste (String nomeDisciplina){
        // case insensitive -> verifica se as palavras são iguais (independente se estão em maiúsculo ou minúsculo cada letra)
        // IMPORTANTE: para caracteres especiais como acentos, a função entende como palavra diferente 
        //      (Ex: "História" != "Historia") -> portanto cadastra as duas
        // se a palavra tiver espaços, ela é considerada diferente pela função (Ex: " Quimica" != "Quimica)
        //      porém essa verificação ocorre na interface (diagrama cadastrar e editar disciplina) -> .trim()
        
        carregarJson();
        
        for (Disciplina d: disciplinas){
            
            // compara ignorando o letra maiúscula e letra minúscula
            if (d.getNome().equalsIgnoreCase(nomeDisciplina)){
                System.out.println("PAM " + d.getNome());
                // existe
                return true;
            }
        }
        
        // não existe
        return false;
    }
    
    
    
    // parâmetro: Object registros (PRÓXIMA SPRINT)
    public boolean verificarRegistrosSessoes (Disciplina disciplinaParaExclusao){
        
        // precisa ler para manter atualizado!
        carregarJson();
        
        // como nessa sprint nn temos 'registros'
        // ENTRETANTO, no futuro ela nn puxará os registros!
        

        return true;
    }
    
    

    public void salvarJson(){
        try {
            
            // converte ArrayList -> JSON + reescreve o arquivo
            objectMapper.writeValue(arquivoJson, this.disciplinas);
            
            System.out.println("Arquivo JSON salvo com sucesso");
        
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo JSON: " + e.getMessage());
        }
    }
    
    public void carregarJson(){
        if (arquivoJson.exists() && arquivoJson.length() > 0) {
            try {
                // le o JSON e joga para o arraylist
                this.disciplinas = objectMapper.readValue(arquivoJson, new TypeReference<ArrayList<Disciplina>>() {});
            } catch (IOException e) {
                System.err.println("Erro ao carregar o arquivo JSON: " + e.getMessage());
                this.disciplinas = new ArrayList<>();
            }
        }
    }
    
}
