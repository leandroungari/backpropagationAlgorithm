/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import csv.CSVFile;
import csv.Dados;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author brunolima
 * @author leandroungari
 */
public class BackPropagation {

    //private HashMap<Integer, Double> entrada;
    //private HashMap<Integer, Double> saida;
    private int numEntrada;
    private int numOculta;
    private int numSaida;

    private final double taxaAprendizagem;
    private int funcao;
    private final Camada[] camadas;
    private MatrizConfusao matrizConfusao;

    public static int contadorNeuronio = 0;

    private double erroMax;
    private int numIteracoesMax;
    private double erroRede;
    private boolean opcaoErro;
    
    public static Random random;
    
    /////////////////////////////////////////
    public static int PESO_ALEATORIO = 1;
    public static int PESO_GAUSSIANO = 2;
    
    public static int tipoPeso;
    
    public BackPropagation(int numEntrada, int numSaida, int numOculta, double taxaAprendizagem, int funcao, int tipoPeso, double erroMax) {
        
        if (tipoPeso == PESO_GAUSSIANO) {
            
            BackPropagation.random = new Random();
        }
        
        BackPropagation.tipoPeso = tipoPeso;
        
        this.numEntrada = numEntrada;
        this.numOculta = numOculta;
        this.numSaida = numSaida;

        this.opcaoErro = true;

        this.erroMax = erroMax;
        this.numIteracoesMax = 0;

        //======================================
        this.taxaAprendizagem = taxaAprendizagem;

        this.camadas = new Camada[3];

        this.camadas[Camada.ENTRADA] = new Camada(numEntrada, null);
        this.camadas[Camada.OCULTA] = new Camada(numOculta, this.camadas[Camada.ENTRADA]);
        this.camadas[Camada.SAIDA] = new Camada(numSaida, this.camadas[Camada.OCULTA]);

        this.funcao = funcao;
        FuncaoTransferencia.FUNCAO_ATUAL = this.funcao;

        this.matrizConfusao = new MatrizConfusao(numSaida);
    }

    public BackPropagation(int numEntrada, int numSaida, int numOculta, double taxaAprendizagem, int funcao, int tipoPeso, int numIteracoesMax) {
        
        if (tipoPeso == PESO_GAUSSIANO) {
            
            BackPropagation.random = new Random();
        }
        
        BackPropagation.tipoPeso = tipoPeso;
        
        this.opcaoErro = false;

        this.erroMax = 0;
        this.numIteracoesMax = numIteracoesMax;

        //======================================
        this.taxaAprendizagem = taxaAprendizagem;
        this.numEntrada = numEntrada;
        this.numOculta = numOculta;
        this.numSaida = numSaida;

        this.camadas = new Camada[3];

        this.camadas[Camada.ENTRADA] = new Camada(numEntrada, null);
        this.camadas[Camada.OCULTA] = new Camada(numOculta, this.camadas[Camada.ENTRADA]);
        this.camadas[Camada.SAIDA] = new Camada(numSaida, this.camadas[Camada.OCULTA]);

        this.funcao = funcao;

        FuncaoTransferencia.FUNCAO_ATUAL = this.funcao;

        this.matrizConfusao = new MatrizConfusao(numSaida);
    }

    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    public void teste(ArrayList<Dados> dados) {

        int tamanho, numIteracoes = 0;
        double dadosEntrada[], dadosSaida[];

        for (Dados d : dados) {

            dadosEntrada = Arrays.copyOfRange(d.getDados(), 0, d.getDados().length - 1);
            dadosSaida = CSVFile.classes.get((int) d.getDados()[d.getDados().length - 1]).clone();
            //this.inicializarVetores(dadosEntrada, dadosSaida, tamanho, d);

            //Realizando treinamento
            this.testar(dadosEntrada, dadosSaida);
        }

    }

    private void testar(double[] dadoEntrada, double[] dadoSaida) {

        int i = 0;
        for (Neuronio n : this.camadas[Camada.ENTRADA].getNeuronios()) {

            n.setNet(dadoEntrada[i]);
            n.setSaida(dadoEntrada[i]);
            i++;
        }

        for (Neuronio n : this.camadas[Camada.OCULTA].getNeuronios()) {

            double soma = 0;
            for (Neuronio no : this.camadas[Camada.ENTRADA].getNeuronios()) {

                soma += n.getPeso(no.getId()) * no.getSaida();
            }

            n.setNet(soma);
            n.setSaida(FuncaoTransferencia.funcao(soma));
        }

        for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

            double soma = 0;
            for (Neuronio no : this.camadas[Camada.OCULTA].getNeuronios()) {

                soma += n.getPeso(no.getId()) * no.getSaida();
            }

            n.setNet(soma);
            n.setSaida(FuncaoTransferencia.funcao(soma));
        }

        ///////////////////////////////////
        //Montando a matriz confusão
        
        this.atualizarMatrizConfusao(dadoSaida);
    }
    
    public void atualizarMatrizConfusao(double[] dadoSaida){
        
        int real, obtido;
        real = this.maior(dadoSaida);
        obtido = this.maior(camadas[Camada.SAIDA].saidas());

        matrizConfusao.add(real, obtido);
    }
    
    private int maior(double[] dadoSaida){
        
        double maior = dadoSaida[0];
        int posicao = 0;
        for (int a = 0; a < dadoSaida.length; a++) {
            if (dadoSaida[a] > maior) {
                maior = dadoSaida[a];
                posicao = a;
            }
        }
        
        return posicao;
    }

    /*public void inicializarVetores(double entrada[], double saida[], int tamanho, Dados d){
    
            for(int i = 0; i < tamanho-1; i++){

                entrada[i] = d.get(i);
            }

            if(this.funcao == 2){ //Logistica [0,1]
                
                saida[(int)d.get(tamanho-1)-1] = 1.0;
            }
            else if(this.funcao == 3){ //Hiperbolica [-1,1]
                
                for(int i = 0; i < tamanho-1; i++){
                    saida[i] = -1.0;
                }
                saida[(int)d.get(tamanho-1)-1] = 1.0;
            }
            
    }*/
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    public void treinamento(ArrayList<Dados> dados) {

        int numIteracoes = 0;
        double dadosEntrada[], dadosSaida[];
        boolean flag;

        do {
            
            flag = false;
            for (Dados d : dados) {

                //Tamanho do vetor de dados
                //Inicializando entrada e saida.
                dadosEntrada = Arrays.copyOfRange(d.getDados(), 0, d.getDados().length - 1);
                dadosSaida = CSVFile.classes.get((int) d.getDados()[d.getDados().length - 1]).clone();
                //this.inicializarVetores(dadosEntrada, dadosSaida, tamanho, d);

                //Realizando treinamento
                this.treinar(dadosEntrada, dadosSaida);

                //this.treinar(new double[]{d.get(0), d.get(1), d.get(2), d.get(3), d.get(4), d.get(5)}, new double[]{d.get(6)});
                //System.out.println(count++);
                
                if (this.pararTreinamento(opcaoErro, numIteracoes)) { //retorna true se o erro for maior do que o desejado
                    flag = true;
                }
                
                
            }

            numIteracoes++;
            

        } while (flag);

    }

    private void treinar(double[] dadoEntrada, double[] dadoSaida) {

        int count = 0;
        for (Neuronio n : this.camadas[Camada.ENTRADA].getNeuronios()) {
            n.setNet(dadoEntrada[count]);
            n.setSaida(dadoEntrada[count]);
            count++;
        }

        //System.out.println(this);
        //calcula net e saida da camada oculta e de saída
        for (Camada camada : this.camadas) {

            if (camada.temCamadaAnterior()) {

                for (Neuronio no : camada.getNeuronios()) {

                    double soma = 0;
                    for (Neuronio n : camada.getAnterior().getNeuronios()) {

                        soma += n.getSaida() * no.getPeso(n.getId());
                    }

                    no.setNet(soma);

                    no.setSaida(FuncaoTransferencia.funcao(no.getNet()));
                }
            }
        }

        //erro na dadoSaida
        count = 0;
        for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

            n.setErro((dadoSaida[count] - n.getSaida()) * FuncaoTransferencia.derivada(n.getNet()));
            count++;
        }

        //erro na camada oculta
        for (Neuronio no : camadas[Camada.OCULTA].getNeuronios()) {

            double erro = 0;
            for (Neuronio n : camadas[Camada.SAIDA].getNeuronios()) {

                erro += n.getErro() * n.getPeso(no.getId());
            }

            no.setErro(erro * FuncaoTransferencia.derivada(no.getNet()));

            //importante
            //no.setSaida(FuncaoTransferencia.funcao(no.getNet()));
        }

        //ajuste dos pesos da camada oculta e da saida
        for (Camada camada : this.camadas) {

            if (camada.temCamadaAnterior()) {

                for (Neuronio no : camada.getNeuronios()) {

                    for (Neuronio n : camada.getAnterior().getNeuronios()) {

                        no.setPeso(n.getId(), no.getPeso(n.getId()) + taxaAprendizagem * no.getErro() * n.getSaida());
                    }

                }
            }
        }

    }

    public boolean pararTreinamento(boolean opcaoErro, int numIteracoes) {
        boolean flag = false;
        if (opcaoErro) {
            double soma = 0;
            for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

                soma += (n.getErro() * n.getErro());
            }
            
            soma *= 0.5;
            
            //System.out.println(soma);
            
            if (soma > this.erroMax) {
                flag = true;
            }
            
        } else {
            
            if (numIteracoes < this.numIteracoesMax) {
                flag = true;
            }
        }

        return (flag);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");

        for (Camada c : camadas) {
            s.append("\n").append(c);
        }

        s.append("\n");

        return s.toString();
    }
    
    public Camada getCamada(int i){
        return camadas[i];
    }

    public MatrizConfusao getMatrizConfusao() {

        return matrizConfusao;
    }

}
