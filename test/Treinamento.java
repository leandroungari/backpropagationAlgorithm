
import algorithm.BackPropagation;
import algorithm.FuncaoTransferencia;
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
 * @author leandroungari
 */
public class Treinamento {
    
    public static void main(String[] args) throws FileNotFoundException {
        
        //BackPropagation b = new BackPropagation(2,1, 1, FuncaoTransferencia.LOGISTICA, 0.001);
        
        ArrayList<Dados> dados = CSVFile.read("Treinamento e Teste/teste.csv");
        
    }
}
