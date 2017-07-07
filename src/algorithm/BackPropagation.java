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
public class BackPropagation {

    
    private final double[] entrada;
    private final double[] saida;

    private final double taxaAprendizagem;

    private Camada[] camadas;
    private final int tamanhoCamadaOculta;

    public BackPropagation(double[] entrada, double[] saida, double taxaAprendizagem) {

        this.entrada = entrada;
        this.saida = saida;

        this.taxaAprendizagem = taxaAprendizagem;

        this.tamanhoCamadaOculta = (int)(Math.ceil(Math.sqrt(this.entrada.length * this.saida.length)));

        this.camadas = new Camada[3];
        
        this.camadas[Camada.ENTRADA] = new Camada(entrada, 0);
        this.camadas[Camada.OCULTA] = new Camada(new double[this.tamanhoCamadaOculta], this.entrada.length);
        this.camadas[Camada.SAIDA] = new Camada(saida, this.tamanhoCamadaOculta);

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        
        for(Camada c: camadas){
            s.append("\n").append(c);
        }
        
        s.append("\n");
        
        return s.toString();
    }
    
    

}
