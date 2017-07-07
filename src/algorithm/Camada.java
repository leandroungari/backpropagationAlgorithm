/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

/**
 *
 * @author leandroungari
 */
public class Camada {
    
    public static final int ENTRADA = 0;
    public static final int OCULTA = 1;
    public static final int SAIDA = 2;
    
    private No[] nos;
    
    public Camada(double[] dados, int pesosPorNo){
        
        this.nos = new No[dados.length];
        
        for(int i = 0; i < this.nos.length; i++){
            
            nos[i] = new No(dados[i], pesosPorNo);
            nos[i].inicializarPesos();
        }
    }

    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder("");
        for(No no: nos){
            s.append(no);
        }
        
        return s.toString();
    }
    
    
}
