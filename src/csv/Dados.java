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
            
            if (d.get(0) > max.get(0))  d.set(0, d.get(0));
            if (d.get(1) > max.get(1))  d.set(1, d.get(1));
            if (d.get(2) > max.get(2))  d.set(2, d.get(2));
            if (d.get(3) > max.get(3))  d.set(3, d.get(3));
            if (d.get(4) > max.get(4))  d.set(4, d.get(4));
            if (d.get(5) > max.get(5))  d.set(5, d.get(5));
            if (d.get(6) > max.get(6))  d.set(6, d.get(6));
            
        }
        
        for(Dados d: entrada){
            
            d.set(0, d.get(0)/max.get(0));
        }
        
        return entrada;
    }
}
