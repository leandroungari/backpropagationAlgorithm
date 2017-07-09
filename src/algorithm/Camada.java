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
    
    public Camada(int tamanho, Camada anterior){
        
        this.nos = new Neuronio[tamanho];
        this.anterior = anterior;
        
        for(int i = 0; i < this.nos.length; i++){
            
            nos[i] = new Neuronio(BackPropagation.contadorNeuronio++, 0);
            nos[i].inicializarPesos(anterior);
        }
    }

    public Neuronio[] getNeuronios() {
        return nos;
    }
    
    public boolean temCamadaAnterior(){
        
        return this.anterior != null;
    }
    
    public Camada getAnterior(){
        
        return this.anterior;
    }
    

    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder("");
        for(Neuronio no: nos){
            s.append(no);
        }
        
        return s.toString();
    }
    
    
}
