package biblioteca;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class Avaliacao extends FitnessFunction {
    private final AlgoritmoGenetico algoritmoGenetico;

    public Avaliacao(AlgoritmoGenetico algoritmoGenetico) {
        this.algoritmoGenetico = algoritmoGenetico;
    }

    @Override
    protected double evaluate(IChromosome cromossomo) {
        double somaEspacos = 0;
        double nota = 0;

        for (int i = 0; i < cromossomo.size(); i++) {
            if (cromossomo.getGene(i).getAllele().toString().equals("1")) {
                nota += this.algoritmoGenetico.listaProdutos.get(i).getValor();
                somaEspacos += this.algoritmoGenetico.listaProdutos.get(i).getEspaco();
            }
        }

        if (somaEspacos > this.algoritmoGenetico.limite) {
            nota = 1.0;
        }

        return nota;
    }
}
