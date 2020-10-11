package biblioteca;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.SwappingMutationOperator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {
    Configuration configuration;
    int tamanhoPopulacao = 20;
    int taxaMutacao = 100;
    int geracoes = 100;
    double limite = 10.0;

    List<IChromosome> melhoresCromossomos = new ArrayList<>();
    List<Produto> listaProdutos = new ArrayList<>();
    IChromosome melhor;

    public void carregar() throws ClassNotFoundException, SQLException {
//        listaProdutos.add(new Produto("Geladeira Dako", 0.751, 999.90));
//        listaProdutos.add(new Produto("Iphone 6", 0.000089, 2911.12));
//        listaProdutos.add(new Produto("Notebook Dell", 0.00350, 2499.90));
//        listaProdutos.add(new Produto("Microondas Panasonic", 0.0319, 299.29));
//        listaProdutos.add(new Produto("Notebook Asus", 0.527, 3999.00));
//        listaProdutos.add(new Produto("Ventilador Panasonic", 0.496, 199.90));
//        listaProdutos.add(new Produto("Geladeira Brastemp", 0.635, 849.00));
//        listaProdutos.add(new Produto("TV 55’", 0.400, 4346.99));
//        listaProdutos.add(new Produto("TV 42’", 0.200, 2999.90));
//        listaProdutos.add(new Produto("TV 50’", 0.290, 3999.90));
//        listaProdutos.add(new Produto("Microondas Electrolux", 0.0424, 308.66));
//        listaProdutos.add(new Produto("Geladeira Consul", 0.870, 1199.89));
//        listaProdutos.add(new Produto("Microondas LG", 0.0544, 429.90));
//        listaProdutos.add(new Produto("Notebook Lenovo", 0.498, 1999.90));

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/produtos", "root", "SUA_SENHA");
        Statement consulta = connection.createStatement();
        ResultSet resultSet = consulta.executeQuery("select  nome, valor, espaco, quantidade from produtos");
        while (resultSet.next()) {
            int quantidade = resultSet.getInt("quantidade");
            double espaco = resultSet.getDouble("espaco");
            double valor = resultSet.getDouble("valor");
            String nome = resultSet.getString("nome");

            for (int i = 0; i < quantidade; i++) {
                listaProdutos.add(new Produto(nome, espaco, valor));
            }
        }
    }

    public double somaEspacos(IChromosome cromossomo) {
        double soma = 0.0;

        for (int i = 0; i < cromossomo.size(); i++) {
            if (cromossomo.getGene(i).getAllele().toString().equals("1")) {
                soma += this.listaProdutos.get(i).getEspaco();
            }
        }

        return soma;
    }

    public void visualizargeracao(IChromosome cromossomo, int geracao) {
        List<String> lista = new ArrayList<>();
        Gene[] genes = cromossomo.getGenes();

        for (int i = 0; i < cromossomo.size(); i++) {
            lista.add(genes[i].getAllele().toString() + " ");
        }

        System.out.println("G: " + geracao
                + " Valor: " + cromossomo.getFitnessValue()
                + " Espaço: " + somaEspacos(cromossomo)
                + " Cromossomo: " + lista);
    }

    public IChromosome criarComossomo() throws InvalidConfigurationException {
        Gene[] genes = new Gene[listaProdutos.size()];

        for (int i = 0; i < genes.length; i++) {
            genes[i] = new IntegerGene(configuration, 0, 1);
            genes[i].setAllele(i);
        }

        return new Chromosome(configuration, genes);
    }

    public FitnessFunction criarFitnessFunction() {
        return new Avaliacao(this);
    }

    public Configuration criarConfiguracao() throws InvalidConfigurationException {
        Configuration configuration = new Configuration();

        configuration.removeNaturalSelectors(true);
        configuration.addNaturalSelector(new BestChromosomesSelector(configuration, 0.4), true);
        configuration.addGeneticOperator(new SwappingMutationOperator(configuration, taxaMutacao));
        configuration.addGeneticOperator(new CrossoverOperator(configuration));
        configuration.setFitnessEvaluator(new DefaultFitnessEvaluator());
        configuration.setRandomGenerator(new StockRandomGenerator());
        configuration.setKeepPopulationSizeConstant(true);
        configuration.setEventManager(new EventManager());

        return configuration;
    }

    public void procurarMelhorSolucao() throws InvalidConfigurationException {
        this.configuration = criarConfiguracao();
        configuration.setFitnessFunction(criarFitnessFunction());

        IChromosome modelo = criarComossomo();
        configuration.setSampleChromosome(modelo);

        configuration.setPopulationSize(this.tamanhoPopulacao);
        IChromosome[] cromossomos = new IChromosome[this.tamanhoPopulacao];

        for (int i = 0; i < this.tamanhoPopulacao; i++) {
            cromossomos[i] = criarComossomo();
        }

        Genotype populacao = new Genotype(configuration, new Population(configuration, cromossomos));
        for (int i = 0; i < this.geracoes; i++) {
            IChromosome melhor = populacao.getFittestChromosome();

            visualizargeracao(melhor, i);
            this.melhoresCromossomos.add(melhor);
            populacao.evolve();
        }
    }
}
