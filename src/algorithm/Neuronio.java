/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.util.HashMap;

/**
 *
 * @author brunolima
 * @author leandroungari
 */
public class Neuronio {

    private int id;
    private double net;
    private double saida;
    private double erro;
    private HashMap<Integer, Double> pesos;

    public Neuronio(int id, double net) {

        this.id = id;
        this.net = net;
        this.pesos = new HashMap<>();
        this.saida = 0;
    }
    
    /**
     * Inicializa os pesos do neurônio de forma aletória ou gaussiana.
     * @param anterior Refere-se a camada antecedente na rede.
     */
    public void inicializarPesos(Camada anterior) {

        if (anterior == null) {
            return;
        }

        for (Neuronio n : anterior.getNeuronios()) {

            if (BackPropagation.tipoPeso == BackPropagation.PESO_GAUSSIANO) {
                this.pesos.put(n.getId(), BackPropagation.random.nextGaussian());
            } else {
                this.pesos.put(n.getId(), Math.random() * (Math.random() > 0.5 ? 1 : -1));
            }
        }
    }

    public double getPeso(int id) {

        return pesos.get(id);
    }

    public void setPeso(int numPeso, double valor) {

        if (pesos.containsKey(numPeso)) {

            pesos.replace(numPeso, valor);
        } else {
            pesos.put(numPeso, valor);
        }
    }

    public double getErro() {
        return erro;
    }

    public void setErro(double erro) {
        this.erro = erro;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getSaida() {
        return saida;
    }

    public void setSaida(double saida) {
        this.saida = saida;
    }

    public double getNet() {
        return net;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder("");
        s.append("(").append(this.net).append(") => [");
        for (Integer i : this.pesos.keySet()) {
            s.append(String.format("%.2f", pesos.get(i))).append(",");

        }

        if (s.toString().charAt(s.length() - 1) == '[') {
            s.append(",");
        }
        s.append("\b] ");

        return s.toString();
    }

}
