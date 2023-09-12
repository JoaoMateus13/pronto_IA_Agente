public class AgenteComEstadosTest extends AgenteReativoSimples implements Agente {
    private Ambiente ambiente;
    private int posicaoInicialY; // Armazena a posição vertical inicial

    public AgenteComEstadosTest(Ambiente ambiente) {
        super(ambiente);
        this.ambiente = ambiente;
        this.posicaoInicialY = 0; // Inicialmente, o agente começa na linha superior (y=0)
    }

    @Override
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

        // Verifica se há pontos acima ou abaixo para coletar
        if (conteudoLocal == 0) {
            if (cima > 0) {
                acao = "cima";
            } else if (baixo > 0) {
                acao = "baixo";
            } else if (esquerda > 0) {
                acao = "esqueda";
            } else{
                acao = "direita";
            }
        } else {
            acao = "Pegar";
            coletarItem();
        }

        ambiente.imprimirAmbiente();
        realizarAcao(acao);

        // Verifica se o agente deve retornar à posição inicial após coletar
        if (conteudoLocal == 0 && (acao.equals("cima") || acao.equals("baixo"))) {
            posicaoInicialY = y; // Atualiza a posição vertical inicial
        } else if (conteudoLocal > 0 && y != posicaoInicialY) {
            acao = "Baixo"; // Volta para a posição inicial após coletar pontos
            realizarAcao(acao);
        }

        System.out.println("Posição do Agente: (" + x + ", " + y + ")");
        System.out.println("Ação realizada: " + acao);
        System.out.println("Pontos Restantes: " + ambiente.getPontosRestantes());
        System.out.println("------");
    }

    @Override
    public boolean isTerminado() {
        return ambiente.getPontosRestantes() == 0;
    }

    private String estadoAtual = "Direita";

    @Override
    public String getEstadoAtual() {
        return estadoAtual;
    }

    @Override
    public void realizarAcao(String acao) {

        int x = ambiente.agentPos[0];
        int y = ambiente.agentPos[1];

        // Verifique se o agente está na extremidade direita ou esquerda
        boolean extremidadeDireita = (x < ambiente.tamanho - 1);
        boolean extremidadeDireitad = (x == ambiente.tamanho - 1);
        boolean extremidadeEsquerda = (x > 0);
        boolean extremidadeEsquerdae = (x > 0);
        boolean extremidadeCima = (y < ambiente.tamanho - 1);
        boolean extremidadeBaixo = (y > 0);

        if (acao.equals("direita") && extremidadeDireita) {
            ambiente.agentPos[0] = x+1;
            coletarItem();
        }

        if (acao.equals("esquerda") && extremidadeEsquerda) {
            ambiente.agentPos[0] = x-1;
        }

        if (acao.equals("cima") && extremidadeCima) {
            ambiente.agentPos[1] = y-1;
            coletarItem();
          //  ambiente.agentPos[1] = y+1;
        }

        if (acao.equals("baixo") && extremidadeBaixo) {
            ambiente.agentPos[1] = y+1;
            coletarItem();
           // ambiente.agentPos[1] = y-1;
        }




        //Verifique se o agente está se movendo para a direita ou para a esquerda
        boolean movendoParaDireita = acao.equals("Direita");

        // Se o agente está na extremidade direita ou esquerda, ele deve se mover para baixo
        if (((extremidadeDireitad && movendoParaDireita) ||
                (extremidadeEsquerdae && !movendoParaDireita))
                && !acao.equals("Pegar") && !acao.equals("cima") && !acao.equals("baixo")) {
            ambiente.agentPos[1] = y + 1; // Move-se para baixo
            if (this.estadoAtual.equals("Direita")) {
                this.estadoAtual = "Esquerda";
            } else {
                this.estadoAtual = "Direita";
            } // Muda a direção
        }
        // Se o agente está em uma linha par, ele se move para a direita
        else if (extremidadeDireita && ambiente.agentPos[1]%2==0) {
            ambiente.agentPos[0] = x + 1;
        }
        // Se o agente está em uma linha ímpar, ele se move para a esquerda
        else if (extremidadeEsquerda && ambiente.agentPos[1]%2!=0) {
            ambiente.agentPos[0] = x - 1;
        }






    }

    @Override
    public void coletarItem() {
        int x = ambiente.agentPos[0];
        int y = ambiente.agentPos[1];
        int conteudoLocal = ambiente.grid[x][y];
        if (conteudoLocal == 1 || conteudoLocal == 3) {
            ambiente.grid[x][y] = 0;
            ambiente.pontosRestantes--;
        }
    }

    @Override
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