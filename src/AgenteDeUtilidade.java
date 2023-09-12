import java.util.ArrayList;

public class AgenteDeUtilidade extends AgenteComObjetivos implements Agente{

    protected ArrayList<Ponto> pontos;

    private Ambiente ambiente;



    public AgenteDeUtilidade(Ambiente ambiente) {
        super(ambiente);
        this.ambiente = ambiente;
        this.pontos = new ArrayList<Ponto>();
    }

    @Override
    public void executarAcao() {

        mapearPontos();

        this.pontos.sort((o2, o1) -> Integer.compare(o1.getPontuacao(), o2.getPontuacao()));


        for (Ponto ponto : this.pontos){
            irParaPonto(ponto);
            coletarItem();
            ambiente.atualizarPontos();


            ambiente.imprimirAmbiente();

            System.out.println("Posição do Agente: (" + ponto.x + ", " + ponto.y + ")");
            System.out.println("Ação realizada: " + "pegar");
            System.out.println("Pontos Restantes: " + ambiente.getPontosRestantes());
            System.out.println("------");
        }

    }

    public void mapearPontos() {
        System.out.println("REALIZANDO MAPEAMENTO");

        for (int i = 0; i < ambiente.tamanho; i++) {
            for (int j = 0; j < ambiente.tamanho; j++) {
                if(ambiente.grid[j][i]>0) {
                    int potuacao = ambiente.grid[j][i];
                    Ponto ponto = new Ponto(j,i, potuacao);
                    this.pontos.add(ponto);
                }
            }

        }
    }

    public void irParaPonto(Ponto ponto){
        System.out.println("Indo para o ponto: (" + ponto.x + ", " + ponto.y + ")");

        ambiente.agentPos[0] = ponto.x;
        ambiente.agentPos[1] = ponto.y;
    }

    @Override
    public boolean isTerminado() {
        return ambiente.getPontosRestantes() == 0;
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
}
