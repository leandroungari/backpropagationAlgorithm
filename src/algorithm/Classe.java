/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.util.ArrayList;

/**
 * 
 * @author brunolima
 * @author leandroungari
 */
public class Classe {

    private int identificador;
    private ArrayList<Elementos> neuronios;
    
    public Classe(int identificador, int numNeuronios){
        
        this.identificador = identificador;
        this.neuronios = new ArrayList();
    }

    public int getIdentificador() {
        return identificador;
    }

    public ArrayList<Elementos> getNeuronios() {
        return neuronios;
    }

    public void setNeuronios(ArrayList<Elementos> neuronios) {
        this.neuronios = neuronios;
    }

    public void addNeuronios(int e1, int e2, int e3, int e4, int e5, int e6){
        
        Elementos e = new Elementos(e1,e2,e3,e4,e5,e6);
        this.neuronios.add(e);
        
    }
    
    public void exibir(){
        
        System.out.println("CLASSE: " + this.identificador);
        for(Elementos e : this.neuronios){

            System.out.println(e.elementos[0] + "," + e.elementos[1] + "," + e.elementos[2] + 
                               "," + e.elementos[3] + "," + e.elementos[4] + "," + e.elementos[5]);
        }
        
        System.out.println();
    }
    
    //Talvez necessario um método Clone()
    
    public class Elementos{
        
        public int elementos[];
        
        public Elementos(int e1, int e2, int e3, int e4, int e5, int e6){
            
            this.elementos = new int[6];
            this.elementos[0] = e1;
            this.elementos[1] = e2;
            this.elementos[2] = e3;
            this.elementos[3] = e4;
            this.elementos[4] = e5;
            this.elementos[5] = e6;

        }
    }
}
