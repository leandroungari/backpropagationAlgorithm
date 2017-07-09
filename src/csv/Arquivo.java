/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import algorithm.Classe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author leandroungari
 */
public class Arquivo {
    
    public ArrayList<Classe> classes = new ArrayList();
    
    public Arquivo(){
        //Construtor da classe Arquivo
    }
    
    public void readCSV(String filename){
        
        try{
            
            int contadorClasses = 0;
            int classeAtual;
            int n1, n2, n3, n4, n5, n6;
            String linha = "";
            
            BufferedReader arquivo = new BufferedReader(new FileReader(filename));
            
            //Leitura da linha inicial = X1,X2,X3,X4,X5,X6,classe
            linha = arquivo.readLine();
            
            //Realiza a leitura do arquivo CSV linha por linha
            while((linha = arquivo.readLine()) != null){
                
                //Separando os tokens
                String[] row = linha.split(",");
                
                classeAtual = Integer.parseInt(row[6]);
                //Encontra uma classe nova, insere um novo objeto Classe na lista de classes.
                if(classeAtual != contadorClasses){
                    
                    Classe c = new Classe(classeAtual, 6);
                    this.classes.add(c);
                    contadorClasses++;
                }
                     
                //Adicionando os neuronios de uma classe, são 6 por padrão, não necessario tratar erros de quantidade
                n1 = Integer.parseInt(row[0]);
                n2 = Integer.parseInt(row[1]);
                n3 = Integer.parseInt(row[2]);
                n4 = Integer.parseInt(row[3]);
                n5 = Integer.parseInt(row[4]);
                n6 = Integer.parseInt(row[5]);
                this.classes.get(classeAtual-1).addNeuronios(n1, n2, n3, n4, n5, n6);
                
            }
            
        }catch(Exception e){
            System.out.println("Erro na arbertura do arquivo!");
        }

    }
}
