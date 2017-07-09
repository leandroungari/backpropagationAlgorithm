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
public class Classe {

    private int identificador;
    private int neuronios[];
    
    public Classe(int identificador, int numNeuronios){
        
        this.identificador = identificador;
        this.neuronios = new int[numNeuronios];
    }

    public int getIdentificador() {
        return identificador;
    }

    public int[] getNeuronios() {
        return neuronios;
    }

    public void setNeuronios(int[] neuronios) {
        this.neuronios = neuronios;
    }
    
}
