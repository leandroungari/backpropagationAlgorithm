/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import csv.Dados;
import java.util.ArrayList;

/**
 * 
 * @author brunolima
 * @author leandroungari
 */
public class Normalizacao {
    
    
    public static void normalizar(ArrayList<Dados> treinamento, ArrayList<Dados> teste){
        
        //Inicializando max para obter o maior valor
        Dados max = treinamento.get(0);
        
        //Os dois primeiros FOR calculam o valor maximo de cada coluna de elementos, considerando Treinamento e Teste
        for(Dados d: treinamento){
            
            for(int i = 0; i < d.getDados().length-1; i++){
                
                if (d.get(i) > max.get(i))  max.set(i, d.get(i));
            }
        }
        
        for(Dados d: teste){
            
            for(int i = 0; i < d.getDados().length-1; i++){
                
                if (d.get(i) > max.get(i))  max.set(i, d.get(i));
            }
        }
        
        //Aplica a normalização com base no valor maximo obtido anteriormente
        for(Dados d: treinamento){
            
            for(int i = 0; i < d.getDados().length-1; i++){
                
                d.set(i, d.get(i)/max.get(i));
            }
        }
        
        for(Dados d: teste){
            
            for(int i = 0; i < d.getDados().length-1; i++){
                
                d.set(i, d.get(i)/max.get(i));
            }
        }
        
    }
        
}
