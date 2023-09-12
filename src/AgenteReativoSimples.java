public class AgenteReativoSimples implements Agente {

    private Ambiente ambiente;

    public AgenteReativoSimples(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public void executarAcao() {
        int[] percepcao = obterPercepcao();
        int x = percepcao[0];
        int y = percepcao[1];
        int conteudoLocal = percepcao[2];
        int esquerda = percepcao[3];
        int direita = percepcao[4];
        int cima = percepcao[5];
        int baixo = percepcao[6];

        String acao = "NoOp";
        if (conteudoLocal == 1 || conteudoLocal == 3) {
            acao = "Pegar";
            coletarItem();
            ambiente.atualizarPontos();
        } else {
            acao = getEstadoAtual();
        }

        ambiente.imprimirAmbiente();
        realizarAcao(acao);
        System.out.println("Posição do Agente: (" + x + ", " + y + ")");
        System.out.println("Ação realizada: " + acao);
        System.out.println("Pontos Restantes: " + ambiente.getPontosRestantes());
        System.out.println("------");
    }

    public boolean isTerminado() {
        return ambiente.getPontosRestantes() == 0;
    }

    private String direcaoAtual = "Direita";


    public String getEstadoAtual() {
        return direcaoAtual;
    }

    public void realizarAcao(String acao) {
        int x = ambiente.agentPos[0];
        int y = ambiente.agentPos[1];

        // Verifique se o agente está na extremidade direita ou esquerda
        boolean extremidadeDireita = (x == ambiente.tamanho - 1);
        boolean extremidadeEsquerda = (x == 0);

        // Verifique se o agente está se movendo para a direita ou para a esquerda
        boolean movendoParaDireita = acao.equals("Direita");

        // Se o agente está na extremidade direita ou esquerda, ele deve se mover para baixo
        if (((extremidadeDireita && movendoParaDireita) || (extremidadeEsquerda && !movendoParaDireita)) && !acao.equals("Pegar")) {
            ambiente.agentPos[1] = y + 1; // Move-se para baixo
            if (this.direcaoAtual.equals("Direita")) {
                this.direcaoAtual = "Esquerda";
            } else {
                this.direcaoAtual = "Direita";
            } // Muda a direção
        }
        // Se o agente está em uma linha par, ele se move para a direita
        else if (!extremidadeDireita && ambiente.agentPos[1]%2==0) {
            ambiente.agentPos[0] = x + 1;
        }
        // Se o agente está em uma linha ímpar, ele se move para a esquerda
        else if (!extremidadeEsquerda && ambiente.agentPos[1]%2!=0) {
            ambiente.agentPos[0] = x - 1;
        }

    }

    public void coletarItem() {
        int x = ambiente.agentPos[0];
        int y = ambiente.agentPos[1];
        int conteudoLocal = ambiente.grid[x][y];
        if (conteudoLocal == 1 || conteudoLocal == 3) {
            ambiente.grid[x][y] = 0;
            ambiente.pontosRestantes--;
        }
    }

    public int[] obterPercepcao() {
        int x = ambiente.agentPos[0];
        int y = ambiente.agentPos[1];
        int conteudoLocal = ambiente.grid[x][y];

        int[] vizinhanca4 = new int[4];
        if (x > 0) {
            vizinhanca4[0] = ambiente.grid[x - 1][y]; // Esquerda
        }
        if (x < ambiente.tamanho - 1) {
            vizinhanca4[1] = ambiente.grid[x + 1][y]; // Direita
        }
        if (y > 0) {
            vizinhanca4[2] = ambiente.grid[x][y - 1]; // Cima
        }
        if (y < ambiente.tamanho - 1) {
            vizinhanca4[3] = ambiente.grid[x][y + 1]; // Baixo
        }

        return new int[]{x, y, conteudoLocal, vizinhanca4[0], vizinhanca4[1], vizinhanca4[2], vizinhanca4[3]};
    }

}
