public class Ponto implements Comparable {
    public int x;
    public int y;
    public int pontuacao;

    public Ponto(int x, int y, int pontuacao) {
        this.x = x;
        this.y = y;
        this.pontuacao = pontuacao;
    }

    public int getPontuacao() {
        return this.pontuacao;
    }

    @Override
    public String toString() {
        return "Ponto [ " +
                "x=" + x +
                ", y=" + y +
                ", pontuacao=" + pontuacao +
                ']';
    }

    @Override
    public int compareTo(Object o) {

        if (this.pontuacao > ((Ponto) o).pontuacao) {
            return -1;
        } else if (this.pontuacao == ((Ponto) o).pontuacao) {
            return 0;
        } else {
            return 1;
        }
    }
}
