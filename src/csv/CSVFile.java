/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import algorithm.FuncaoTransferencia;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * @author brunolima
 * @author leandroungari
 */
public class CSVFile {

    public static int numEntrada;
    public static HashMap<Integer, double[]> classes = new HashMap();
    public static int numSaida;
    
    public static ArrayList<Dados> read(String filename) throws FileNotFoundException {

        int classe;
        
        
        Scanner leitura = new Scanner(new File(filename));
        ArrayList<Dados> dados = new ArrayList<>();

        while (leitura.hasNextLine()) {

            String linha = leitura.nextLine().replaceAll("\"", "");

            String[] elementos = linha.split(",");
            numEntrada = elementos.length - 1;
            
            try {
                
                Double.parseDouble(elementos[0]);
                
                double[] d = new double[elementos.length];

                for (int i = 0; i < elementos.length; i++) {

                    d[i] = Double.parseDouble(elementos[i]);
                }

                //Identificando quantidade de classes
                classe = (int) d[elementos.length-1];
                classes.put(classe, null);
                
                //Adicionando na estrutura que armazena os valores das classes e de seus elementos
                dados.add(new Dados(d));
                
            } catch (NumberFormatException e) {
                
                //passar para o proximo
            }

        }
        
        numSaida = classes.keySet().size();
        
        
        return(dados);
    }

    public static String getClasses() {
    
        String s = "[";
        for(Integer e: classes.keySet()){
            s +=  e + ",";
        }
        s += "]";
        return(s);
    }
    
    public static void ajustarSaida(int funcaoTransferencia){
        
        double min, max;
        
        if(funcaoTransferencia == FuncaoTransferencia.LOGISTICA || funcaoTransferencia == FuncaoTransferencia.LINEAR){
            min = 0; max = 1;
        }
        else{
            min = -1; max = 1;
        }
        
        int posicao = 0;
        for(Integer i: classes.keySet()){
            
            double[] vetor = new double[classes.keySet().size()];
            for(int j = 0; j < vetor.length; j++){
                if(posicao == j){
                    vetor[j] = max;
                }
                else{
                    vetor[j] = min;
                }
            }
            
            posicao++;
            classes.put(i, vetor);
        }
    }
}
