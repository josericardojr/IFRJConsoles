
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

// Deposito compartilhado
public class Deposito {
	private final Queue<Madeira> buffer;
	private final int capacidade;

	// --- Os Semáforos ---
	// Inicia com N permissões (N espaços vazios)
	public final Semaphore empty; 

	// Inicia com 0 permissões (0 itens)
	public final Semaphore full;   

	// Inicia com 1 permissão (acesso exclusivo)
	private final Semaphore mutex;  

	public Deposito(int capacidade) {
		this.capacidade = capacidade;
		this.buffer = new LinkedList<>();

		this.empty = new Semaphore(capacidade);
		this.full = new Semaphore(0);
		this.mutex = new Semaphore(1);
	}

	// Lógica do PRODUTOR (Lenhador)
	public void produzir(Madeira item) throws InterruptedException {
		// 1. Espera por um espaço VAZIO. 
		// (Se empty=0, buffer está cheio, ele BLOQUEIA AQUI)
		empty.acquire(); // Decrementa 'empty'

		// 2. Garante acesso exclusivo ao buffer
		mutex.acquire();
		try {
			// --- Seção Crítica ---
			buffer.add(item);
			// --- Fim da Seção Crítica ---
		} finally {
			mutex.release(); // Libera o acesso
		}

		// 3. Sinaliza que há um novo item CHEIO
		full.release(); // Incrementa 'full'
	}

	// Lógica do CONSUMIDOR (Carpinteiro)
	public Madeira consumir() throws InterruptedException {
		Madeira item = null;

		// 1. Espera por um item CHEIO.
		// (Se full=0, buffer está vazio, ele BLOQUEIA AQUI)
		full.acquire(); // Decrementa 'full'

		// 2. Garante acesso exclusivo ao buffer
		mutex.acquire();
		try {
			// --- Seção Crítica ---
			item = buffer.remove();
			// --- Fim da Seção Crítica ---
		} finally {
			mutex.release(); // Libera o acesso
		}

		// 3. Sinaliza que há um novo espaço VAZIO
		empty.release(); // Incrementa 'empty'

		return item;
	}

	// --- Métodos para a Visualização (Thread-Safe) ---
	// Pega um "snapshot" da fila para desenhar
	public Object[] getBufferSnapshot() {
		Object[] snapshot = null;
		try {
			mutex.acquire(); // Precisa do mutex para ler a fila com segurança
			snapshot = buffer.toArray();
			mutex.release();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return snapshot;
	}

	// Retorna o número de produtores bloqueados (esperando espaço)
	public int getProdutoresEsperando() {
		return empty.getQueueLength(); 
	}

	public int getConsumidoresEsperando() {
		return full.getQueueLength();
	}

	public int getCapacidade() {
		return capacidade;
	}
}
