package algoritmo;

import java.util.ArrayList;
import java.util.List;

public class Individuo implements Comparable<Individuo> {
    private List<Integer> cromossomo = new ArrayList<>();
    private List<Double> espacos;
    private List<Double> valores;
    private Double limiteEspacos;
    private Double notaAvaliacao;
    private Double espacoUsado;
    private int geracao;

    public Individuo(List<Double> espacos, List<Double> valores, Double limiteEspacos) {
        this.limiteEspacos = limiteEspacos;
        this.notaAvaliacao = 0.0;
        this.espacos = espacos;
        this.valores = valores;
        this.geracao = 0;

        for (int i = 0; i < this.espacos.size(); i++) {
            if (Math.random() < 0.5) {
                this.cromossomo.add(0);
            } else {
                this.cromossomo.add(1);
            }
        }
    }

    public void avaliacao() {
        Double nota = 0.0;
        Double somaEspacos = 0.0;

        for (int i = 0; i < this.cromossomo.size(); i++) {
            if (this.cromossomo.get(i).equals(1)) {
                nota += this.valores.get(i);
                somaEspacos += this.espacos.get(i);
            }
        }

        if (somaEspacos > this.limiteEspacos) {
            nota = 1.0;
        }

        this.notaAvaliacao = nota;
        this.espacoUsado = somaEspacos;
    }

    public List<Individuo> crossover(Individuo outroIndividuo) {
        int corte = (int) (Math.random() * this.cromossomo.size());

        List<Integer> filho1 = new ArrayList<>();
        filho1.addAll(outroIndividuo.getCromossomo().subList(0, corte));
        filho1.addAll(this.cromossomo.subList(corte, this.cromossomo.size()));

        List<Integer> filho2 = new ArrayList<>();
        filho2.addAll(this.cromossomo.subList(0, corte));
        filho2.addAll(outroIndividuo.getCromossomo().subList(corte, this.cromossomo.size()));

        List<Individuo> filhos = new ArrayList<>();
        filhos.add(new Individuo(this.espacos, this.valores, this.limiteEspacos));
        filhos.add(new Individuo(this.espacos, this.valores, this.limiteEspacos));

        filhos.get(0).setCromossomo(filho1);
        filhos.get(0).setGeracao(this.geracao + 1);

        filhos.get(1).setCromossomo(filho2);
        filhos.get(1).setGeracao(this.geracao + 1);

        return filhos;
    }

    public Individuo mutacao(Double taxaMutacao) {
//        System.out.println("Antes da mutação: " + this.cromossomo);
        for (int i = 0; i < this.cromossomo.size(); i++) {
            if (Math.random() < taxaMutacao) {
                if (this.cromossomo.get(i).equals(1)) {
                    this.cromossomo.set(i, 0);
                } else {
                    this.cromossomo.set(i, 1);
                }
            }
        }
//        System.out.println("Depois da mutação: " + this.cromossomo);
        return this;
    }

    public void setCromossomo(List<Integer> cromossomo) {
        this.cromossomo = cromossomo;
    }

    public List<Integer> getCromossomo() {
        return cromossomo;
    }

    public List<Double> getEspacos() {
        return espacos;
    }

    public void setEspacos(List<Double> espacos) {
        this.espacos = espacos;
    }

    public List<Double> getValores() {
        return valores;
    }

    public void setValores(List<Double> valores) {
        this.valores = valores;
    }

    public Double getLimiteEspacos() {
        return limiteEspacos;
    }

    public void setLimiteEspacos(Double limiteEspacos) {
        this.limiteEspacos = limiteEspacos;
    }

    public Double getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(Double notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public Double getEspacoUsado() {
        return espacoUsado;
    }

    public void setEspacoUsado(Double espacoUsado) {
        this.espacoUsado = espacoUsado;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    @Override
    public int compareTo(Individuo individuo) {
        return individuo.getNotaAvaliacao().compareTo(this.notaAvaliacao);
    }
}
