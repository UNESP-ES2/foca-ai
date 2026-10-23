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
    
    // atributos relacionados com o Json
    private final File arquivoJson;
    private final ObjectMapper objectMapper;

    // construtor de CatalogoDisciplinas para persistência
    public CatalogoDisciplinas() {
        this("src/main/java/br/unesp/fct/focaai/Json");
    }

    // construtor para persistência dos dados originais + persistência dos dados de teste
    public CatalogoDisciplinas(String caminhoPacote) {
        File pastaDiretorio = new File(caminhoPacote);
        arquivoJson = new File(pastaDiretorio, "disciplinas.json");

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
        
        return null;
        
    }

    public ArrayList<Disciplina> buscarDisciplinaTrecho(String nomeDisciplina){
        // precisa ler para manter atualizado!
        carregarJson();

        ArrayList<Disciplina> lista = new ArrayList<>();

        for (Disciplina c: disciplinas){
            if (c.getNome().toLowerCase().contains(nomeDisciplina.toLowerCase())){
                lista.add(c);
            }
        }

        return lista;
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
    
    

    // grava com integridade atômica -> evita que caso programa falhe, o disciplinas.json fique pela metade ou corrompido
    public void salvarJson() {

        // cria um arquivo temporário na mesma pasta do arquivo oficial
        File arquivoTemp = new File(arquivoJson.getParent(), arquivoJson.getName() + ".tmp");

        try {
            //  escreve primeiro no arquivo temporário
            objectMapper.writeValue(arquivoTemp, this.disciplinas);

            //  substitui o arquivo original pelo temporário de forma atômica
            // o temp vira oficial
            java.nio.file.Files.move(
                    arquivoTemp.toPath(),
                    arquivoJson.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                    java.nio.file.StandardCopyOption.ATOMIC_MOVE
            );

            System.out.println("Arquivo JSON salvo com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo JSON: " + e.getMessage());

            // se algo der errado -> limpa o temp
            if (arquivoTemp.exists()) {
                arquivoTemp.delete();
            }
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
