package algoritmo;

import java.util.ArrayList;
import java.util.List;

public class Executar {
    public static void main(String[] args) {
        List<Produto> listaProdutos = new ArrayList<>();

        listaProdutos.add(new Produto("Geladeira Dako", 0.751, 999.90));
        listaProdutos.add(new Produto("Iphone 6", 0.000089, 2911.12));
        listaProdutos.add(new Produto("Notebook Dell", 0.00350, 2499.90));
        listaProdutos.add(new Produto("Microondas Panasonic", 0.0319, 299.29));
        listaProdutos.add(new Produto("Notebook Asus", 0.527, 3999.00));
        listaProdutos.add(new Produto("Ventilador Panasonic", 0.496, 199.90));
        listaProdutos.add(new Produto("Geladeira Brastemp", 0.635, 849.00));
        listaProdutos.add(new Produto("TV 55’", 0.400, 4346.99));
        listaProdutos.add(new Produto("TV 42’", 0.200, 2999.90));
        listaProdutos.add(new Produto("TV 50’", 0.290, 3999.90));
        listaProdutos.add(new Produto("Microondas Electrolux", 0.0424, 308.66));
        listaProdutos.add(new Produto("Geladeira Consul", 0.870, 1199.89));
        listaProdutos.add(new Produto("Microondas LG", 0.0544, 429.90));
        listaProdutos.add(new Produto("Notebook Lenovo", 0.498, 1999.90));

        List<Double> espacos = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        List<String> nomes = new ArrayList<>();

        for (Produto p : listaProdutos) {
            espacos.add(p.getEspaco());
            valores.add(p.getValor());
            nomes.add(p.getNome());
        }

        Double limite = 3.0;
        int tamanhoPopulacao = 4;
        double taxaMutacao = 0.05;
        int numeroGeracoes = 3;
        AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanhoPopulacao);
        ag.inicializarPopulacao(espacos, valores, limite);

        List<Integer> resultado = ag.resolver(taxaMutacao, numeroGeracoes, espacos, valores, limite);

        for (int i = 0; i < listaProdutos.size(); i++) {
            if (resultado.get(i).equals(1)) {
                Produto produto = listaProdutos.get(i);
                System.out.println("Nome: " + produto.getNome());
            }
        }
    }
}
