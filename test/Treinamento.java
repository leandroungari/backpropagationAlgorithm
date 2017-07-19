
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

        int funcao = FuncaoTransferencia.TANGENTE_HIPERBOLICA;
        int peso = BackPropagation.PESO_ALEATORIO;

        ArrayList<Dados> dados = CSVFile.read("Treinamento e Teste/treinamento.csv");
        CSVFile.ajustarSaida(funcao);

        Normalizacao.analisar(dados, funcao);
        dados = Normalizacao.normalizar(dados);
       

        int numOculta = (int) Math.ceil(Math.sqrt(CSVFile.numEntrada * CSVFile.numSaida));
        BackPropagation b = new BackPropagation(CSVFile.numEntrada, CSVFile.numSaida, numOculta, 1, funcao, peso, 0.001);

        //System.out.println(b);
        b.treinamento(dados);

        //System.out.println(b);
        dados = CSVFile.read("Treinamento e Teste/teste.csv");
        CSVFile.ajustarSaida(funcao);

        dados = Normalizacao.normalizar(dados);
        b.teste(dados);

        System.out.println(b.getMatrizConfusao());

    }
}
