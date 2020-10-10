package algoritmo;

import org.jfree.ui.RefineryUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Executar {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<Produto> listaProdutos = new ArrayList<>();
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

        List<Double> espacos = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        List<String> nomes = new ArrayList<>();

        for (Produto p : listaProdutos) {
            espacos.add(p.getEspaco());
            valores.add(p.getValor());
            nomes.add(p.getNome());
        }

        Double limite = 10.0;
        int tamanhoPopulacao = 20;
        double taxaMutacao = 0.05;
        int numeroGeracoes = 100;
        AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanhoPopulacao);
        ag.inicializarPopulacao(espacos, valores, limite);

        List<Integer> resultado = ag.resolver(taxaMutacao, numeroGeracoes, espacos, valores, limite);

        for (int i = 0; i < listaProdutos.size(); i++) {
            if (resultado.get(i).equals(1)) {
                Produto produto = listaProdutos.get(i);
                System.out.println("Nome: " + produto.getNome());
            }
        }

        Grafico grafico = new Grafico("Algoritmo Genético", "Evolução das soluções", ag.getMelhoresCromossomos());
        grafico.pack();
        RefineryUtilities.centerFrameOnScreen(grafico);
        grafico.setVisible(true);
    }
}
