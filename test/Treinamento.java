
import algorithm.BackPropagation;
import algorithm.Camada;
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
        CSVFile.ajustarSaida(FuncaoTransferencia.LOGISTICA);
        
        Normalizacao.obterMaximo(dados);
        dados = Normalizacao.normalizar(dados);

        
        int numOculta = (int) Math.round(Math.sqrt(CSVFile.numEntrada*CSVFile.numSaida));
        
      
        
        BackPropagation b = new BackPropagation(CSVFile.numEntrada, CSVFile.numSaida, numOculta, 1, FuncaoTransferencia.LOGISTICA, 0.01);
        //BackPropagation b = new BackPropagation(CSVFile.numEntrada, CSVFile.numSaida, numOculta, 1, FuncaoTransferencia.LOGISTICA, 100);
        System.out.println(b);
        
        
        /*ArrayList<Dados> dados = new ArrayList<>();
        dados.add(new Dados(new double[]{0,1,1}));
        
        //set pesos
        
        
        
        //g
        b.getCamada(Camada.SAIDA).getNeuronios()[0].setPeso(2, 1.2);
        b.getCamada(Camada.SAIDA).getNeuronios()[0].setPeso(3, 1.6);
        b.getCamada(Camada.SAIDA).getNeuronios()[0].setPeso(4, 4.3);
        b.getCamada(Camada.SAIDA).getNeuronios()[0].setPeso(5, 3.2);
        
        //c
        b.getCamada(Camada.OCULTA).getNeuronios()[0].setPeso(0, 4.1);
        b.getCamada(Camada.OCULTA).getNeuronios()[0].setPeso(1, -1.4);
        //d
        b.getCamada(Camada.OCULTA).getNeuronios()[1].setPeso(0, 3.6);
        b.getCamada(Camada.OCULTA).getNeuronios()[1].setPeso(1, -4.1);
        //e
        b.getCamada(Camada.OCULTA).getNeuronios()[2].setPeso(0, 2.1);
        b.getCamada(Camada.OCULTA).getNeuronios()[2].setPeso(1, 2.5);
        //f
        b.getCamada(Camada.OCULTA).getNeuronios()[3].setPeso(0, 0.9);
        b.getCamada(Camada.OCULTA).getNeuronios()[3].setPeso(1, -1.0);
        
        //Normalizacao.obterMaximo(dados);
        //Normalizacao.normalizar(dados);*/
        
        
        b.treinamento(dados);
        
        System.out.println(b);
        //b.toString();
        
        dados = CSVFile.read("Treinamento e Teste/teste.csv");
        CSVFile.ajustarSaida(FuncaoTransferencia.LOGISTICA);
        
        dados = Normalizacao.normalizar(dados);
        b.teste(dados);
        
        System.out.println(b.getMatrizConfusao());
        
    }
}
