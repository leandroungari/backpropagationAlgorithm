/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

/**
 *
 * @author brunolima
 * @author leandroungari
 */
public class Camada {

    public static final int ENTRADA = 0;
    public static final int OCULTA = 1;
    public static final int SAIDA = 2;

    private Neuronio[] nos;
    private Camada anterior;

    public Camada(int tamanho, Camada anterior) {

        this.nos = new Neuronio[tamanho];
        this.anterior = anterior;

        for (int i = 0; i < this.nos.length; i++) {

            nos[i] = new Neuronio(BackPropagation.contadorNeuronio++, 0);
            nos[i].inicializarPesos(anterior);
        }
    }

    public void modificarCamadaOculta(int numNeuronios) {

        Neuronio[] novos = new Neuronio[numNeuronios];
        int i = 0;

        for (; i < numNeuronios && i < this.nos.length; i++) {

            novos[i] = this.nos[i];
        }
        
        for(; i < numNeuronios; i++){
            
            novos[i] = new Neuronio(BackPropagation.contadorNeuronio++, 0);
            novos[i].inicializarPesos(anterior);
            
        }
        
        this.nos = novos;
        

    }

    public Neuronio[] getNeuronios() {
        return nos;
    }

    public Neuronio getNeuronio(int id) {

        for (int i = 0; i < this.nos.length; i++) {

            if (nos[i].getId() == id) {
                return (nos[i]);
            }
        }

        return (null);
    }

    /**
     * Verifica se a camada atual possui camada antecedente.
     *
     * @return
     */
    public boolean temCamadaAnterior() {

        return this.anterior != null;
    }

    public Camada getAnterior() {

        return this.anterior;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder("");
        for (Neuronio no : nos) {
            s.append(no);
        }

        return s.toString();
    }

    /**
     * Este método extrai o conjunto de valores da saída dos neurônios desta
     * camada.
     *
     * @return Retorna um array com os valores de saída.
     */
    public double[] saidas() {

        double[] lista = new double[this.getNeuronios().length];
        int count = 0;
        for (Neuronio n : this.getNeuronios()) {
            lista[count] = n.getSaida();
            count++;
        }

        return lista;
    }
}
