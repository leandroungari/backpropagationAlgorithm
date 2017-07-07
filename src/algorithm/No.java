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
public class No {
    
    private double net;
    private double saida;
    private double[] pesos;
    
    public No(double net, int numPesos){
        
        this.net = net;
        this.pesos = new double[numPesos];
        this.saida = 0;
    }
    
    public void inicializarPesos(){
        
        for(int i = 0; i < pesos.length; i++){
        
            pesos[i] = Math.random()*(Math.random() > 0.5 ? 1 : -1);
        }
    }
    
    @Override
    public String toString(){
        
        StringBuilder s = new StringBuilder("");
        s.append("(").append(this.net).append(") => [");
        for(Double d: this.pesos){
            s.append(String.format("%.2f", d)).append(",");
            
        }
        
        if(s.toString().charAt(s.length()-1) == '[') s.append(",");
        s.append("\b] ");
        
        return s.toString();
    }
}
