
import algorithm.BackPropagation;
import algorithm.FuncaoTransferencia;
import algorithm.Normalizacao;
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

        

        ArrayList<Dados> dados = CSVFile.read("Treinamento e Teste/treinamento.csv");
        
        /////////////////////////////////////////////////
        
        int funcao = FuncaoTransferencia.LOGISTICA;
        
        
        CSVFile.ajustarSaida(funcao);
        Normalizacao.analisar(dados, funcao);
        dados = Normalizacao.normalizar(dados);
        
        ///////////////////////////////////////////
        int peso = BackPropagation.PESO_ALEATORIO;
        int numOculta = (int) Math.ceil(Math.sqrt(CSVFile.numEntrada * CSVFile.numSaida));
        
        BackPropagation b = new BackPropagation(CSVFile.numEntrada, CSVFile.numSaida, numOculta, peso);
        b.inicializar(1, funcao, 0.001);
        
        ////////////////////////////////////////
        
        
        b.treinamento(dados);

        /////////////////////////////////////////
        
        dados = CSVFile.read("Treinamento e Teste/teste.csv");
        CSVFile.ajustarSaida(funcao);

        dados = Normalizacao.normalizar(dados);
        b.teste(dados);

        System.out.println(b.getMatrizConfusao());
        
        
        
        
    }
}
