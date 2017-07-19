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
    public static Dados min;
    
    public static int funcaoTreinamento;
    
    /**
     * Este método obtém informações do conjunto de dados, como valores mínimo e máximo.
     * @param treinamento
     * @param funcaoTreinamento 
     */
    public static void analisar(ArrayList<Dados> treinamento, int funcaoTreinamento){
        
        Normalizacao.funcaoTreinamento = funcaoTreinamento;
        
        Dados max = treinamento.get(0).clone();
        Dados min = treinamento.get(0).clone();
        
        //Os dois primeiros FOR calculam o valor maximo de cada coluna de elementos, considerando Treinamento e Teste
        for(Dados d: treinamento){
            
            for(int i = 0; i < d.getDados().length-1; i++){
                
                if (d.get(i) > max.get(i))  max.set(i, d.get(i));
                else if (d.get(i) < min.get(i)) min.set(i, d.get(i));
            }
        }
        
        
        Normalizacao.max = max;
        Normalizacao.min = min;
    }
    
    /**
     * Aplica a normalização no conjunto de dados com base no domínio de dados da função de transferência escolhida para a rede, 
     * @param conjunto Conjunto de dados.
     * @return Retorna o conjunto de dados normalizado.
     */
    public static ArrayList<Dados> normalizar(ArrayList<Dados> conjunto){
        
        ArrayList<Dados> result = new ArrayList<>();
        double a, b;
        if (funcaoTreinamento == FuncaoTransferencia.LOGISTICA) {
            a = 0; b = 1;
        }
        else{
            a = -1; b = 1;
        }
        
        double valor;
        //Aplica a normalização com base no valor maximo obtido anteriormente
        for(Dados d: conjunto){
            
            Dados novo = new Dados(d.getDados().length);
            for(int i = 0; i < d.getDados().length-1; i++){
                
                valor = ((d.get(i) - min.get(i))*(b-a))/(max.get(i) - min.get(i)) + a;
                novo.set(i, valor);
                //novo.set(i, d.get(i)/max.get(i));
            }
            
            novo.set( d.getDados().length-1, d.get(d.getDados().length-1));
            result.add(novo);
        }
        
        return result;
    }
        
}
