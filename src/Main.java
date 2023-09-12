public class Main {
    public static void main(String[] args) {

        Ambiente ambiente = new Ambiente(20, 10, 10, 0, 0);

        AgenteReativoSimples agenteReativoSimples = new AgenteReativoSimples(ambiente);

        AgenteComObjetivos agenteComObjetivos = new AgenteComObjetivos(ambiente);

       /* while (!agenteReativoSimples.isTerminado()) {
            agenteReativoSimples.executarAcao();
        }*/
      /*  while (!agenteComObjetivos.isTerminado()) {
            agenteComObjetivos.executarAcao();
        }*/

        AgenteDeUtilidade agenteDeUtilidade = new AgenteDeUtilidade(ambiente);
        /*while (!agenteDeUtilidade.isTerminado()) {
            agenteDeUtilidade.executarAcao();
        }*/

        AgenteComEstados agenteComEstados = new AgenteComEstados(ambiente);

        while (!agenteComEstados.isTerminado()) {
            agenteComEstados.executarAcao();
        }

    }
}