/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

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

    public static int qtdClasses;
    public static int numElementos;
    public static HashMap<Integer,Integer> classes = new HashMap();
    
    public static ArrayList<Dados> read(String filename) throws FileNotFoundException {

        int classe;
        qtdClasses = 0;
        
        Scanner leitura = new Scanner(new File(filename));
        ArrayList<Dados> dados = new ArrayList<>();

        while (leitura.hasNextLine()) {

            String linha = leitura.nextLine().replaceAll("\"", "");

            String[] elementos = linha.split(",");
            numElementos = elementos.length - 1;
            
            try {
                
                Double.parseDouble(elementos[0]);
                
                double[] d = new double[elementos.length];

                for (int i = 0; i < elementos.length; i++) {

                    d[i] = Double.parseDouble(elementos[i]);
                }

                //Identificando quantidade de classes
                classe = (int) d[elementos.length-1];
                classes.put(classe,0);
                
                //Adicionando na estrutura que armazena os valores das classes e de seus elementos
                dados.add(new Dados(d));
                
            } catch (NumberFormatException e) {
                
                //passar para o proximo
            }

        }
        
        qtdClasses = classes.size();
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
}
