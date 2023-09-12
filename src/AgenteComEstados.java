import java.lang.reflect.AnnotatedArrayType;
import java.util.AbstractMap;

public class AgenteComEstados extends AgenteReativoSimples implements Agente {


    private Ambiente ambiente;



    public AgenteComEstados(Ambiente ambiente) {
        super(ambiente);
        this.ambiente = ambiente;
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
        if(conteudoLocal>0 ) {
            acao = "Pegar";
            coletarItem();
            ambiente.atualizarPontos();
        }

        else if (direita>0){
            acao = "direita";
            ambiente.atualizarPontos();
        }

        else if(cima>0 ) {
            acao = "cima";
            ambiente.atualizarPontos();
        }


        else if(baixo>0) {
            acao = "baixo";
            ambiente.atualizarPontos();
        }


        else {
            acao = getEstadoAtual();
            ambiente.atualizarPontos();
        }

        ambiente.imprimirAmbiente();
        realizarAcao(acao);
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
        boolean extremidadeDireita = (x == ambiente.tamanho - 1);
        boolean extremidadeDireitad = (x == ambiente.tamanho - 1);
        boolean extremidadeEsquerda = (x == 0);
        boolean extremidadeEsquerdae = (x == 0);
        boolean extremidadeCima = (y == 0);
        boolean extremidadeBaixo = (y == ambiente.tamanho - 1);

        if (acao.equals("direita") && !extremidadeDireita) {
            ambiente.agentPos[0] = x+1;
            coletarItem();
        }

        if (acao.equals("esquerda") && !extremidadeEsquerda) {
            ambiente.agentPos[0] = x-1;
        }

        if (acao.equals("cima") && !extremidadeCima) {
            ambiente.agentPos[1] = y-1;
            coletarItem();
            ambiente.agentPos[1] = y+1;
        }

        if (acao.equals("baixo") && !extremidadeBaixo) {
            ambiente.agentPos[1] = y+1;
            coletarItem();
            if(ambiente.agentPos[1] != 0){
                ambiente.agentPos[1] = y-1;
            }
          //  ambiente.agentPos[1] = y-1;
        }




        //Verifique se o agente está se movendo para a direita ou para a esquerda
        boolean movendoParaDireita = acao.equals("Direita");

        // Se o agente está na extremidade direita ou esquerda, ele deve se mover para baixo
        if (((extremidadeDireita && movendoParaDireita) ||
                (extremidadeEsquerda && !movendoParaDireita))
                && !acao.equals("Pegar")) {
            ambiente.agentPos[1] = y + 1; // Move-se para baixo
            if (ambiente.agentPos[1]%2==0) {
                this.estadoAtual = "esquerda";
            } else {
                this.estadoAtual = "Direita";
            } // Muda a direção
        }
        // Se o agente está em uma linha par, ele se move para a direita
        else if (!extremidadeDireita && ambiente.agentPos[1]%2==0 && acao.equals("Direita")) {
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