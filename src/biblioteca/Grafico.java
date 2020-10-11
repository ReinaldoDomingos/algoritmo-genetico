package biblioteca;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jgap.IChromosome;

import java.awt.*;
import java.util.List;

import static org.jfree.chart.plot.PlotOrientation.VERTICAL;

public class Grafico extends ApplicationFrame {
    private final List<IChromosome> melhoresCromossomos;

    public Grafico(String tituloJanela, String tituloGrafico, List<IChromosome> melhores) {
        super(tituloJanela);

        this.melhoresCromossomos = melhores;

        JFreeChart graficoLinha = ChartFactory.createLineChart(tituloGrafico, "Geração", "Valor", carregarDados(), VERTICAL, true, true, false);

        ChartPanel janelaGrafico = new ChartPanel(graficoLinha);
        janelaGrafico.setPreferredSize(new Dimension(800, 600));
        setContentPane(janelaGrafico);
    }

    private DefaultCategoryDataset carregarDados() {
        DefaultCategoryDataset dados = new DefaultCategoryDataset();
        for (int i = 0; i < this.melhoresCromossomos.size(); i++) {
            dados.addValue(melhoresCromossomos.get(i).getFitnessValue(), "Melhor solução ", "" + i);
        }

        return dados;
    }
}
