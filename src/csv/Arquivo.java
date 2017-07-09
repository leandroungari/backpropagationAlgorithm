/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author leandroungari
 */
public class Arquivo {
    
public static ArrayList<Double[]> dados = new ArrayList<>();
    
    public static void readCSV(String filename){
        
        try{
            
            String linha = "";
            BufferedReader arquivo = new BufferedReader(new FileReader(filename));
            
            //Leitura da linha inicial = X1,X2,X3,X4,X5,X6,classe
            linha = arquivo.readLine();
            
            //Realiza a leitura do arquivo CSV linha por linha
            while((linha = arquivo.readLine()) != null){
                
                //Separando os tokens
                String[] row = linha.split(",");
                
                System.out.println(row[0] + "," + row[1] + "," + row[2] + "," + row[3] + "," +
                        row[4] + "," + row[5] + "," + row[6]);
                
                //Pensar em uma estrutura que seja boa para armazenar as classes e seus dados
            }
            
        }catch(Exception e){
            System.out.println("Erro na arbertura do arquivo!");
        }

    }
}
