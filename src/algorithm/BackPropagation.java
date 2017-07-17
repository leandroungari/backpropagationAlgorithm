/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import csv.Dados;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author brunolima
 * @author leandroungari
 */
public class BackPropagation {

    private HashMap<Integer, Double> entrada;
    private HashMap<Integer, Double> saida;

    private final double taxaAprendizagem;

    private final Camada[] camadas;
    private int tamanhoCamadaOculta;
    public static int contadorNeuronio = 0;

    private int funcao;
    private double erroMax;
    private int numIteracoesMax;

    private double erroRede;
    private boolean opcaoErro;

    private MatrizConfusao matrizConfusao;

    
    public BackPropagation(int numEntrada, int numSaida, int numCamadaOculta, double taxaAprendizagem, int funcao, double erroMax) {

        this.opcaoErro = true;

        this.erroMax = erroMax;
        this.numIteracoesMax = 0;

        //======================================
        this.taxaAprendizagem = taxaAprendizagem;
        //this.tamanhoCamadaOculta = (int) (Math.ceil(Math.sqrt(numEntrada * numSaida))); //OBS: permitir que o usuario insira;
        this.tamanhoCamadaOculta = numCamadaOculta;

        this.camadas = new Camada[3];

        this.camadas[Camada.ENTRADA] = new Camada(numEntrada, null);
        this.camadas[Camada.OCULTA] = new Camada(tamanhoCamadaOculta, this.camadas[Camada.ENTRADA]);
        this.camadas[Camada.SAIDA] = new Camada(numSaida, this.camadas[Camada.OCULTA]);

        this.funcao = funcao;

        this.matrizConfusao = new MatrizConfusao(numSaida);
    }

    public BackPropagation(int numEntrada, int numSaida, int numCamadaOculta, double taxaAprendizagem, int funcao, int numIteracoesMax) {

        this.opcaoErro = false;

        this.erroMax = 0;
        this.numIteracoesMax = numIteracoesMax;

        //======================================
        this.taxaAprendizagem = taxaAprendizagem;
        //this.tamanhoCamadaOculta = (int) (Math.ceil(Math.sqrt(numEntrada * numSaida))); //OBS: permitir que o usuario insira;
        this.tamanhoCamadaOculta = numCamadaOculta;
        
        this.camadas = new Camada[3];

        this.camadas[Camada.ENTRADA] = new Camada(numEntrada, null);
        this.camadas[Camada.OCULTA] = new Camada(tamanhoCamadaOculta, this.camadas[Camada.ENTRADA]);
        this.camadas[Camada.SAIDA] = new Camada(numSaida, this.camadas[Camada.OCULTA]);

        this.funcao = funcao;
        
        this.matrizConfusao = new MatrizConfusao(numSaida);
    }

    public void teste(ArrayList<Dados> dados) {

        int tamanho, numIteracoes = 0;
        double dadosEntrada[], dadosSaida[];
        
        for (Dados d : dados) {

            //Tamanho do vetor de dados
            tamanho = d.getDados().length;

            //Inicializando entrada e saida
            dadosEntrada = new double[tamanho-1];
            dadosSaida = new double[tamanho-2];
            this.inicializarVetores(dadosEntrada, dadosSaida, tamanho, d);
            
            //Realizando treinamento
            this.testar(dadosEntrada, dadosSaida);
        }
        
    }

    private void testar(double[] dadoEntrada, double[] dadoSaida) {

        int i = 0;
        for (Neuronio n : this.camadas[Camada.ENTRADA].getNeuronios()) {

            n.setNet(dadoEntrada[i++]);
        }

        for (Neuronio n : this.camadas[Camada.OCULTA].getNeuronios()) {
            
            double soma = 0;
            for (Neuronio no : this.camadas[Camada.ENTRADA].getNeuronios()) {

                soma += n.getPeso(no.getId())*no.getNet();
            }
            
            n.setNet(soma);
            n.setSaida(FuncaoTransferencia.funcao(soma));
        }
        
        for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {
            
            double soma = 0;
            for (Neuronio no : this.camadas[Camada.OCULTA].getNeuronios()) {

                soma += n.getPeso(no.getId())*no.getNet();
            }
            
            n.setNet(soma);
            n.setSaida(FuncaoTransferencia.funcao(soma));
        }
        
        //Montando a matriz confusão
        int real, obtido; 
        
        ArrayList<Double> obtidos = new ArrayList();
        for(Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()){
            
           obtidos.add(n.getSaida());
        }
        obtidos.sort(null);
        obtido =(int) Math.ceil(obtidos.get(obtidos.size()));
        
        real = (int) Math.ceil(dadoSaida[0]);
        
        matrizConfusao.add(real, obtido);
        
    }

    public void inicializarVetores(double entrada[], double saida[], int tamanho, Dados d){
    
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
            
    }
    
    public void treinamento(ArrayList<Dados> dados) {

        int tamanho, numIteracoes = 0;
        double dadosEntrada[], dadosSaida[];
        boolean flag = false;
        
        do{
            
            for (Dados d : dados) {
                
                //Tamanho do vetor de dados
                tamanho = d.getDados().length;

                //Inicializando entrada e saida.
                dadosEntrada = new double[tamanho-1];
                dadosSaida = new double[tamanho-2];
                this.inicializarVetores(dadosEntrada, dadosSaida, tamanho, d);
                
                //Realizando treinamento
                this.treinar(dadosEntrada, dadosSaida);
                
                //this.treinar(new double[]{d.get(0), d.get(1), d.get(2), d.get(3), d.get(4), d.get(5)}, new double[]{d.get(6)});

            }
            
            numIteracoes++;
            flag = this.pararTreinamento(opcaoErro, numIteracoes);
            
        }while(!flag);
        
    }

    private void treinar(double[] dadoEntrada, double[] dadoSaida) {

        //Inicializando estruturas de entrada e saida da rede
        this.entrada = new HashMap<>();
        int i = 0;
        for (Neuronio n : this.camadas[Camada.ENTRADA].getNeuronios()) {

            this.entrada.put(n.getId(), dadoEntrada[i++]);
        }

        this.saida = new HashMap<>();
        i = 0;
        for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

            this.saida.put(n.getId(), dadoSaida[i++]);
        }

        //int numIteracoes = 0;
        //boolean flag;

        //do {
            for (Camada camada : this.camadas) {

                if (camada.temCamadaAnterior()) {

                    for (Neuronio no : camada.getNeuronios()) {

                        double soma = 0;
                        for (Neuronio n : camada.getAnterior().getNeuronios()) {

                            soma += no.getNet() * no.getPeso(n.getId());
                        }

                        no.setNet(soma);

                        no.setSaida(FuncaoTransferencia.funcao(no.getNet()));
                    }
                }
            }

            //erro na dadoSaida
            for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

                n.setErro((n.getNet() - n.getSaida()) * FuncaoTransferencia.derivada(n.getNet()));
            }

            //erro na camada oculta
            Camada anterior = camadas[Camada.OCULTA].getAnterior();
            for (Neuronio no : camadas[Camada.OCULTA].getNeuronios()) {

                double erro = 0;
                for (Neuronio n : anterior.getNeuronios()) {

                    erro += n.getErro() * no.getPeso(n.getId()) * FuncaoTransferencia.derivada(no.getNet());
                }

                no.setErro(erro);

                no.setSaida(FuncaoTransferencia.funcao(no.getNet()));
            }

            //ajuste dos pesos da camada oculta e da dadoSaida
            for (Camada camada : this.camadas) {

                if (camada.temCamadaAnterior()) {

                    for (Neuronio no : camada.getNeuronios()) {

                        for (Neuronio n : camada.getAnterior().getNeuronios()) {

                            no.setPeso(n.getId(), no.getPeso(n.getId()) + taxaAprendizagem * no.getErro() * this.entrada.get(n.getId()));
                        }
                    }
                }
            }

            //numIteracoes++;

            //flag = false;
            /*
            if (opcaoErro) {

                for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

                    if (0.5 * (n.getSaida() * n.getSaida()) > this.erroMax) {
                        flag = true;
                        break;
                    }
                }

            } else {

                if (numIteracoes < this.numIteracoesMax) {
                    flag = true;
                }
            }
            */
        //} while (flag);

    }
    
    public boolean pararTreinamento(boolean opcaoErro, int numIteracoes){
            boolean flag = true;
            if (opcaoErro) {
                for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

                    if (0.5 * (n.getSaida() * n.getSaida()) > this.erroMax) {
                        flag = false;
                        break;
                    }
                }
            }
            else {
                if (numIteracoes == this.numIteracoesMax) flag = true;
            }
            
            return(flag);
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

}
