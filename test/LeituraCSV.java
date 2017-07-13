
import csv.CSVFile;
import csv.Dados;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author brunolima
 * @author leandroungari
 */
public class LeituraCSV {
    
    public static void main(String[] args) throws FileNotFoundException {
        
        ArrayList<Dados> dados = CSVFile.read("Treinamento e Teste/teste.csv");

        System.out.println(dados);
        Dados.normalizacao(dados);
        System.out.println(dados);
    }
}
