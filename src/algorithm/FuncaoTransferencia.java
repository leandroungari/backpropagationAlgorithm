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
public class FuncaoTransferencia {
    
    public static final int LINEAR = 1;
    public static final int LOGISTICA = 2;
    public static final int TANGENTE_HIPERBOLICA = 3;
    
    public static int FUNCAO_ATUAL;
    
    public static double funcao(double x){
        
        switch(FUNCAO_ATUAL){
            
            case LINEAR:
                
                return x;
                
            case LOGISTICA:
                
                return 1/(1+Math.exp(-x));
                
            case TANGENTE_HIPERBOLICA:
                
                return (1-Math.exp(-2*x))/(1+Math.exp(-2*x));
                
            default:
                
                return 1;
        }
    }
    
    public static double derivada(double x){
        
        switch(FUNCAO_ATUAL){
            
            case LINEAR:
                
                return 1;
                
            case LOGISTICA:
                
                return funcao(x)*(1-funcao(x));
                
            case TANGENTE_HIPERBOLICA:
                
                return 1 - Math.pow(funcao(x), 2);
                
            default:
                
                return 1;
        }
    }
}
