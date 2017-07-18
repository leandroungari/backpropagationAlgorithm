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
public class MatrizConfusao {
    
    private int dimensao;
    private int[][] dados;
    
    public MatrizConfusao(int dimensao){
        
        this.dimensao = dimensao;
        this.dados = new int[dimensao][dimensao];
    }
    
    public void add(int real, int obtida){
        
        dados[real][obtida]++;
    }

    @Override
    public String toString() {
        
        String s = "\n";
        for(int i = 0; i < this.dimensao; i++){
            
            for(int j = 0; j < this.dimensao; j++){
                
                s += dados[i][j] + " ";
            }
            
            s += "\n";
        }
        
        return s;
    }
    
    
}
