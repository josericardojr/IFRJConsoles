
public class OficinaGame {
	// Configurações da Simulação
    final static int TAMANHO_DEPOSITO = 8;
    final static int NUM_CARPINTEIROS = 3; // Experimente mudar isso!
    final static int VELOCIDADE_UPDATE_MS = 1000; // 10 "frames" por segundo
    
	public static void main(String[] args) {
		Deposito deposito = new Deposito(TAMANHO_DEPOSITO);
        
        // Inicia Produtor (Lenhador)
        new Thread(new Lenhador(deposito), "Lenhador").start();

        // Inicia Consumidores (Carpinteiros)
        for (int i = 0; i < NUM_CARPINTEIROS; i++) {
            new Thread(new Carpinteiro(
            		deposito, "Carpinteiro-" + i), 
            		"Carpinteiro-" + i)
            .start();
        }

        // --- O "RENDER LOOP" ---
        try {
            while (true) {
                clearConsole();
                desenharEstado(deposito);
                Thread.sleep(VELOCIDADE_UPDATE_MS); 
            }
        } catch (InterruptedException e) {
            System.out.println("Simulação interrompida.");
        }
    }
	
	/**
     * Desenha o estado atual da simulação no console.
     */
    private static void desenharEstado(Deposito deposito) {
        System.out.println("--- 🏭 Oficina de Brinquedos (Simulação de Console) ---");
        System.out.println("========================================================\n");
        
        // 1. Desenhar Produtor (Lenhador)
        int produtoresEsperando = deposito.getProdutoresEsperando();
        String estadoLenhador = "✅ CORTANDO MADEIRA";
        if (produtoresEsperando > 0) {
            estadoLenhador = "⛔ BLOQUEADO (Depósito Cheio!)";
        }
        
        System.out.println("🪓 LENHADOR: \t" + estadoLenhador + "\n");

        // 2. Desenhar Buffer (Depósito)
        System.out.println("📦 DEPÓSITO (Tamanho " + TAMANHO_DEPOSITO + "):");
        
        StringBuilder bufferUI = new StringBuilder();
        bufferUI.append("► ["); // Começo do depósito
        
        Object[] itens = deposito.getBufferSnapshot();
        
        for (int i = 0; i < deposito.getCapacidade(); i++) {
            if (i < itens.length) {
                // Slot cheio
                bufferUI.append(String.format(" %-6s ", itens[i])); 
            } else {
                // Slot vazio
                bufferUI.append(" (vazio) ");
            }
            if (i < deposito.getCapacidade() - 1) {
                bufferUI.append("|");
            }
        }
        
        bufferUI.append("] ►"); // Fim do depósito
        System.out.println(bufferUI.toString() + "\n");

        // 3. Desenhar Consumidores (Carpinteiros)
        int consumidoresEsperando = deposito.getConsumidoresEsperando();
        int consumidoresTrabalhando = NUM_CARPINTEIROS - consumidoresEsperando;
        
        System.out.println("🔨 CARPINTEIROS (" + NUM_CARPINTEIROS + " no total):");
        System.out.println("\t- ✅ Trabalhando: \t\t" + consumidoresTrabalhando);
        System.out.println("\t- ⛔ Esperando madeira: \t" + consumidoresEsperando 
                            + (consumidoresEsperando > 0 ? " (BLOQUEADOS!)" : ""));
        
        System.out.println("\n========================================================");
        System.out.println("(Pressione Ctrl+C no terminal para sair)");
    }
	
	/**
     * Limpa o console (funciona na maioria dos terminais e IDEs).
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.println("\n\n");
        System.out.flush();
    }

	

}
