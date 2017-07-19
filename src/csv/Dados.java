/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

/**
 *
 * @author leandroungari
 * @author brunoslima
 */
public class Dados {
    
    /**
     * Consiste em um linha de dados, contendo os respectivos atributos e a classe real.
     */
    private double[] dados;
    
    public Dados(double[] dados){
        this.dados = dados;
    }

    public Dados(int size) {
        
        this.dados = new double[size];
    }
    
    public double get(int i){
        
        return dados[i];
    }
    
    public void set(int i, double e){
        
        dados[i] = e;
    }

    public double[] getDados() {
        return dados;
    }

    @Override
    public String toString() {
    
        String s = "[";
        for(double e: this.dados){
            s += String.format("%.2f", e) + ",";
        }
        
        return s + "\b]";
    }

    
    @Override
    public Dados clone()  {
        
        Dados d = new Dados(this.dados.length);
        
        for(int i = 0; i < this.dados.length; i++){
            d.dados[i] = this.dados[i];
        }
        
        return d;
    }
    
    
}
