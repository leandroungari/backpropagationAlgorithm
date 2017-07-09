/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.util.HashMap;

/**
 *
 * @author leandroungari
 */
public class BackPropagation {

    private final HashMap<Integer, Double> entrada;
    private final HashMap<Integer, Double> saida;

    private final double taxaAprendizagem;

    private final Camada[] camadas;
    private int tamanhoCamadaOculta;

    public static int contadorNeuronio = 0;

    private FuncaoTransferencia funcao;

    private double erroMax;
    private int numIteracoesMax;

    private double erroRede;

    private boolean opcaoErro;

    public BackPropagation(double[] entrada, double[] saida, double taxaAprendizagem, FuncaoTransferencia funcao, double erroMax) {

        this.opcaoErro = true;

        this.erroMax = erroMax;
        this.numIteracoesMax = 0;
        
        //======================================
        this.taxaAprendizagem = taxaAprendizagem;

        this.tamanhoCamadaOculta = (int) (Math.ceil(Math.sqrt(entrada.length * saida.length)));

        this.camadas = new Camada[3];

        this.camadas[Camada.ENTRADA] = new Camada(entrada, null);
        this.camadas[Camada.OCULTA] = new Camada(new double[this.tamanhoCamadaOculta], this.camadas[Camada.ENTRADA]);
        this.camadas[Camada.SAIDA] = new Camada(saida, this.camadas[Camada.SAIDA]);

        this.funcao = funcao;

        this.entrada = new HashMap<>();
        int i = 0;
        for (Neuronio n : this.camadas[Camada.ENTRADA].getNeuronios()) {

            this.entrada.put(n.getId(), entrada[i++]);
        }

        this.saida = new HashMap<>();
        i = 0;
        for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

            this.saida.put(n.getId(), saida[i++]);
        }

    }
    
    public BackPropagation(double[] entrada, double[] saida, double taxaAprendizagem, FuncaoTransferencia funcao, int numIteracoesMax) {

        this.opcaoErro = false;

        this.erroMax = 0;
        this.numIteracoesMax = numIteracoesMax;
        
        //======================================
        this.taxaAprendizagem = taxaAprendizagem;

        this.tamanhoCamadaOculta = (int) (Math.ceil(Math.sqrt(entrada.length * saida.length)));

        this.camadas = new Camada[3];

        this.camadas[Camada.ENTRADA] = new Camada(entrada, null);
        this.camadas[Camada.OCULTA] = new Camada(new double[this.tamanhoCamadaOculta], this.camadas[Camada.ENTRADA]);
        this.camadas[Camada.SAIDA] = new Camada(saida, this.camadas[Camada.SAIDA]);

        this.funcao = funcao;

        this.entrada = new HashMap<>();
        int i = 0;
        for (Neuronio n : this.camadas[Camada.ENTRADA].getNeuronios()) {

            this.entrada.put(n.getId(), entrada[i++]);
        }

        this.saida = new HashMap<>();
        i = 0;
        for (Neuronio n : this.camadas[Camada.SAIDA].getNeuronios()) {

            this.saida.put(n.getId(), saida[i++]);
        }

    }

    public void treinar() {

        int numIteracoes = 0;
        boolean flag;

        do {
            for (Camada camada : this.camadas) {

                if (camada.temCamadaAnterior()) {

                    for (Neuronio no : camada.getNeuronios()) {

                        double soma = 0;
                        for (Neuronio entrada : camada.getAnterior().getNeuronios()) {

                            soma += no.getNet() * no.getPeso(entrada.getId());
                        }

                        no.setNet(soma);

                        no.setSaida(FuncaoTransferencia.funcao(no.getNet()));
                    }
                }
            }

            //erro na saida
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

            //ajuste dos pesos da camada oculta e da saida
            for (Camada camada : this.camadas) {

                if (camada.temCamadaAnterior()) {

                    for (Neuronio no : camada.getNeuronios()) {

                        for (Neuronio n : camada.getAnterior().getNeuronios()) {

                            no.setPeso(n.getId(), no.getPeso(n.getId()) + taxaAprendizagem * no.getErro() * this.entrada.get(n.getId()));
                        }
                    }
                }
            }

            numIteracoes++;

            flag = false;
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
        } while (flag);

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
