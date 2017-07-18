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
    
    public static Dados max;
    
    public static void obterMaximo(ArrayList<Dados> treinamento){
        
        Dados max = treinamento.get(0).clone();
        
        //Os dois primeiros FOR calculam o valor maximo de cada coluna de elementos, considerando Treinamento e Teste
        for(Dados d: treinamento){
            
            for(int i = 0; i < d.getDados().length-1; i++){
                
                if (d.get(i) > max.get(i))  max.set(i, d.get(i));
            }
        }
        
        /*for(Dados d: teste){
            
            for(int i = 0; i < d.getDados().length-1; i++){
                
                if (d.get(i) > max.get(i))  max.set(i, d.get(i));
            }
        }*/
        
        Normalizacao.max = max;
    }
    
    public static ArrayList<Dados> normalizar(ArrayList<Dados> conjunto){
        
        ArrayList<Dados> result = new ArrayList<>();
        //Aplica a normalização com base no valor maximo obtido anteriormente
        for(Dados d: conjunto){
            
            Dados novo = new Dados(d.getDados().length);
            for(int i = 0; i < d.getDados().length-1; i++){
                
                novo.set(i, d.get(i)/max.get(i));
            }
            
            novo.set( d.getDados().length-1, d.get(d.getDados().length-1));
            result.add(novo);
        }
        
        return result;
    }
        
}
