
import algorithm.BackPropagation;
import algorithm.FuncaoTransferencia;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leandroungari
 */
public class InicializacaoTeste {
    
    public static void main(String[] args) {
        
        BackPropagation b = new BackPropagation(2,1, 1, FuncaoTransferencia.LOGISTICA, 0.001);
        
        System.out.println(b);
    }
}
