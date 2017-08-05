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

    private final int numEntrada;
    private int numOculta;
    private final int numSaida;

    private double taxaAprendizagem;
    private int funcao;
    private final Camada[] camadas;
    private MatrizConfusao matrizConfusao;

    public static int contadorNeuronio = 0;

    private double erroMax;
    private int numIteracoesMax;
    private boolean opcaoErro;

    public static Random random;

    public static int PESO_ALEATORIO = 1;
    public static int PESO_GAUSSIANO = 2;

    public static int tipoPeso;

    /**
     * A classe {@code BackPropagation} refere-se a uma rede neural cujo
     * algoritmo de treinamento consiste no algoritmo Backpropagation.
     *
     * @param numEntrada Número de neurônios na camada de entrada.
     * @param numSaida Número de neurônios na camada de saída.
     * @param numOculta Número de neurônios na camada oculta.
     * @param tipoPeso Constante número que define o tipo de peso entre
     * aleatório ou gaussiano.
     */
    public BackPropagation(int numEntrada, int numSaida, int numOculta, int tipoPeso) {

        if (tipoPeso == PESO_GAUSSIANO) {

            BackPropagation.random = new Random();
        }

        BackPropagation.tipoPeso = tipoPeso;

        this.numEntrada = numEntrada;
        this.numOculta = numOculta;
        this.numSaida = numSaida;

        this.camadas = new Camada[3];
        this.camadas[Camada.ENTRADA] = new Camada(numEntrada, null);
        this.camadas[Camada.OCULTA] = new Camada(numOculta, this.camadas[Camada.ENTRADA]);
        this.camadas[Camada.SAIDA] = new Camada(numSaida, this.camadas[Camada.OCULTA]);

    }

    /**
     * Atribui informações à rede.
     *
     * @param taxaAprendizagem Taxa de modificação da rede.
     * @param funcao Função matemática de processamento da rede.
     * @param erroMax Erro máximo no processo de treinamento.
     */
    public void inicializar(double taxaAprendizagem, int funcao, double erroMax) {

        this.taxaAprendizagem = taxaAprendizagem;

        this.opcaoErro = true;
        this.erroMax = erroMax;
        this.numIteracoesMax = 0;

        this.funcao = funcao;
        FuncaoTransferencia.FUNCAO_ATUAL = this.funcao;
    }

    /**
     * Atribui informações à rede.
     *
     * @param taxaAprendizagem Taxa de modificação da rede.
     * @param funcao Função matemática de processamento da rede.
     * @param numIteracoesMax Número máximo de iterações no treinamento.
     */
    public void inicializar(double taxaAprendizagem, int funcao, int numIteracoesMax) {

        this.taxaAprendizagem = taxaAprendizagem;

        this.opcaoErro = false;
        this.erroMax = 0;
        this.numIteracoesMax = numIteracoesMax;

        this.funcao = funcao;
        FuncaoTransferencia.FUNCAO_ATUAL = this.funcao;

    }

    /**
     * O método {@code teste()} executa o teste de um conjunto de dados
     * identificado pelo parâmetro {@code dados}, em seguida, categoriza-os na
     * matriz de confusão da rede.
     *
     * @param dados Conjunto de dados para o teste.
     */
    public void teste(ArrayList<Dados> dados) {

        this.matrizConfusao = new MatrizConfusao(numSaida);

        double dadosEntrada[], dadosSaida[];

        for (Dados d : dados) {

            dadosEntrada = Arrays.copyOfRange(d.getDados(), 0, d.getDados().length - 1);
            dadosSaida = CSVFile.classes.get((int) d.getDados()[d.getDados().length - 1]).clone();

            this.testar(dadosEntrada, dadosSaida);
        }

    }

    /**
     * Este método {@code testar()} executa o processo de teste para uma única
     * entrada e uma única saída esperada.
     *
     * @param dadoEntrada Conjunto de dados da entrada.
     * @param dadoSaida Conjunto de dados da saída.
     */
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

        this.atualizarMatrizConfusao(dadoSaida);
    }

    /**
     * Este método {@code atualizarMatrizConfusao} classifica a saída da rede e
     * atribui à matriz de confusão.
     *
     * @param dadoSaida Conjunto da saída esperada.
     */
    public void atualizarMatrizConfusao(double[] dadoSaida) {

        int real, obtido;
        real = this.maior(dadoSaida);
        obtido = this.maior(camadas[Camada.SAIDA].saidas());

        matrizConfusao.add(real, obtido);
    }

    /**
     * Este método {@code maior} retorna a posição do maior elemento do
     * conjunto.
     *
     * @param dadoSaida Conjunto de valores.
     * @return Retorna posição do maior elemento.
     */
    private int maior(double[] dadoSaida) {

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

    /**
     * Este método {@code treinamento} aplica o treinamento na rede a partir de
     * um conjunto de dados.
     *
     * @param dados Conjunto de dados.
     */
    public void treinamento(ArrayList<Dados> dados) {

        int numIteracoes = 0;
        double dadosEntrada[], dadosSaida[];
        boolean flag;

        do {

            flag = false;
            for (Dados d : dados) {

                dadosEntrada = Arrays.copyOfRange(d.getDados(), 0, d.getDados().length - 1);
                dadosSaida = CSVFile.classes.get((int) d.getDados()[d.getDados().length - 1]).clone();

                this.treinar(dadosEntrada, dadosSaida);

                if (this.pararTreinamento(opcaoErro, numIteracoes)) {
                    flag = true;
                }
            }

            numIteracoes++;

        } while (flag);

    }

    /**
     * Aplica o treinamento para cada entrada e saída esperada, individualmente.
     *
     * @param dadoEntrada Conjunto de entrada.
     * @param dadoSaida Conjunto de saída esperado.
     */
    private void treinar(double[] dadoEntrada, double[] dadoSaida) {

        int count = 0;
        for (Neuronio n : this.camadas[Camada.ENTRADA].getNeuronios()) {
            n.setNet(dadoEntrada[count]);
            n.setSaida(dadoEntrada[count]);
            count++;
        }

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

        count = 0;
        for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

            n.setErro((dadoSaida[count] - n.getSaida()) * FuncaoTransferencia.derivada(n.getNet()));
            count++;
        }

        for (Neuronio no : camadas[Camada.OCULTA].getNeuronios()) {

            double erro = 0;
            for (Neuronio n : camadas[Camada.SAIDA].getNeuronios()) {

                erro += n.getErro() * n.getPeso(no.getId());
            }

            no.setErro(erro * FuncaoTransferencia.derivada(no.getNet()));
        }

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

    /**
     * Este método verifica a condição de término do treinamento.
     *
     * @param opcaoErro Define a operação entre erro máximo e número de
     * iterações
     * @param numIteracoes Total de iterações executadas até o momento.
     * @return Retorna {@code true} para continuar repetindo, caso contrário
     * {@code false}.
     */
    private boolean pararTreinamento(boolean opcaoErro, int numIteracoes) {
        boolean flag = false;
        if (opcaoErro) {
            double soma = 0;
            for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

                soma += (n.getErro() * n.getErro());
            }

            soma *= 0.5;

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

    /**
     * Recupera a camada pela identificação.
     *
     * @param i Número da camada.
     * @return Retorna a instancia da camada escolhida.
     */
    public Camada getCamada(int i) {
        return camadas[i];
    }

    /**
     * Recupera a matriz de confusão da rede.
     *
     * @return Retorna a instancia atual da matriz de confusão.
     */
    public MatrizConfusao getMatrizConfusao() {

        return matrizConfusao;
    }

    public int getNumOculta() {
        return numOculta;
    }

    public void setNumOculta(int numOculta) {
        this.numOculta = numOculta;
    }

    public void modificarCamadaOculta(int num) {

        this.camadas[Camada.OCULTA].modificarCamadaOculta(num);

        for (Neuronio noSaida : this.camadas[Camada.SAIDA].getNeuronios()) {

            for (Neuronio noOculta : this.camadas[Camada.OCULTA].getNeuronios()) {

                if (noSaida.getPesos().get(noOculta.getId()) == null) {

                    if (BackPropagation.tipoPeso == BackPropagation.PESO_GAUSSIANO) {
                        
                        noSaida.getPesos().put(noOculta.getId(), BackPropagation.random.nextGaussian());
                    } else {
                        noSaida.getPesos().put(noOculta.getId(), Math.random() * (Math.random() > 0.5 ? 1 : -1));
                    }

                }
            }
        }
    }

}
