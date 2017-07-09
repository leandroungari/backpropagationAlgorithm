/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import java.util.ArrayList;

/**
 *
 * @author leandroungari
 */
public class Dados {
    
    private double[] dados;
    
    public Dados(double[] dados){
        this.dados = dados;
    }
    
    public double get(int i){
        
        return dados[i];
    }
    
    public void set(int i, double e){
        
        dados[i] = e;
    }

    @Override
    public String toString() {
    
        String s = "[";
        for(double e: this.dados){
            s += String.format("%.2f", e) + ",";
        }
        
        return s + "\b]";
    }
    
    public static <E> ArrayList<Dados> normalizacao(ArrayList<Dados> entrada){
        
        Dados max = entrada.get(0);
        
        for(Dados d: entrada){
            
            
            for(int i = 0; i < d.dados.length; i++){
                
                if (d.get(i) > max.get(i))  max.set(i, d.get(i));
            }
            
        }
        
        for(Dados d: entrada){
            
            for(int i = 0; i < d.dados.length; i++){
                
                d.set(i, d.get(i)/max.get(i));
            }
            
            
        }
        
        return entrada;
    }
}
