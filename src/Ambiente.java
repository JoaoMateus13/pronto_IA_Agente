import java.util.Random;

public class Ambiente {

    public int tamanho;
    public int[][] grid;
    public int[] agentPos;
    public int pontosRestantes;



    public Ambiente(int tamanho, int numItensTipo1, int numItensTipo2, int pontox, int pontoy) {
        this.tamanho = tamanho;
        this.grid = new int[tamanho][tamanho];
        this.agentPos = new int[]{pontox,pontoy};
        this.pontosRestantes = numItensTipo1 + numItensTipo2;

        // Coloque aleatoriamente itens do tipo 1 no ambiente
        for (int i = 0; i < numItensTipo1; i++) {
            int x = new Random().nextInt(tamanho);
            int y = new Random().nextInt(tamanho);
            this.grid[x][y] = 1;

        }

        // Coloque aleatoriamente itens do tipo 2 no ambiente
        for (int i = 0; i < numItensTipo2; i++) {
            int x = new Random().nextInt(tamanho);
            int y = new Random().nextInt(tamanho);
            this.grid[x][y] = 3;
        }
    }

    public void imprimirAmbiente() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (i == agentPos[1] && j == agentPos[0]) {
                    System.out.print('A' + " "); // Marca a posição do agente
                } else {
                    System.out.print(grid[j][i] + " ");
                }
            }
            System.out.println();
        }
    }

    public void atualizarPontos() {
        pontosRestantes = 0;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                pontosRestantes += grid[j][i];
            }
        }

    }

    public int getPontosRestantes() {
        return pontosRestantes;
    }

}