package biblioteca;

import org.jfree.ui.RefineryUtilities;
import org.jgap.InvalidConfigurationException;

import java.sql.SQLException;

public class Executar {
    public static void main(String[] args) throws InvalidConfigurationException, SQLException, ClassNotFoundException {
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        ag.carregar();
        ag.procurarMelhorSolucao();

        int geracao = 0;
            for (int i = 0; i < ag.melhoresCromossomos.size(); i++) {
            if (ag.melhor == null) {
                ag.melhor = ag.melhoresCromossomos.get(i);
            } else if (ag.melhor.getFitnessValue() < ag.melhoresCromossomos.get(i).getFitnessValue()) {
                ag.melhor = ag.melhoresCromossomos.get(i);
                geracao = i;
            }
        }

        System.out.println("\nMelhor solução");
        ag.visualizargeracao(ag.melhor, geracao);

        for (int i = 0; i < ag.listaProdutos.size(); i++) {
            if (ag.melhor.getGene(i).getAllele().toString().equals("1")) {
                System.out.println("Nome: " + ag.listaProdutos.get(i).getNome());
            }
        }


        Grafico grafico = new Grafico("Algoritmo Genético", "Evolução das soluções", ag.melhoresCromossomos);
        grafico.pack();
        RefineryUtilities.centerFrameOnScreen(grafico);
        grafico.setVisible(true);
    }
}
