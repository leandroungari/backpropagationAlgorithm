/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author leandroungari
 */
public class CSVFile {

    public static ArrayList<Dados> read(String filename) throws FileNotFoundException {

        Scanner leitura = new Scanner(new File(filename));
        ArrayList<Dados> dados = new ArrayList<>();

        while (leitura.hasNextLine()) {

            String linha = leitura.nextLine().replaceAll("\"", "");

            String[] elementos = linha.split(",");

            try {
                
                Double.parseDouble(elementos[0]);
                
                double[] d = new double[elementos.length];

                for (int i = 0; i < elementos.length; i++) {

                    d[i] = Double.parseDouble(elementos[i]);
                }

                dados.add(new Dados(d));
                
            } catch (NumberFormatException e) {
                
                //passar para o proximo
            }

        }


        return dados;
    }

}
