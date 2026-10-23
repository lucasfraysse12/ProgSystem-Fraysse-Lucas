import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;

public class ProducteurConsommateur {

    private static final int TAILLE_BUFFER = 5;
    private final Integer[] buffer = new Integer[TAILLE_BUFFER];
    private int indexAjout = 0;
    private int indexRetrait = 0;

    public int totalProduit = 0;
    public int totalConsomme = 0;

    private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);
    private final Semaphore elementsDisponibles = new Semaphore(0);
    private final Semaphore mutexBuffer = new Semaphore(1);

    private int genererProduit() {
        return (int) (Math.random() * 100);
    }

    public Integer[] getBuffer() {
        return buffer;
    }

    class Producteur implements Runnable {
        public void run() {
            for (int i = 0; i < 10; i++) {
                int produit = genererProduit();
                try {
                    placesLibres.acquire();
                    mutexBuffer.acquire();

                    buffer[indexAjout] = produit;
                    indexAjout = (indexAjout + 1) % TAILLE_BUFFER;
                    totalProduit++;

                    mutexBuffer.release();
                    elementsDisponibles.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    class Consommateur implements Runnable {
        public void run() {
            for (int i = 0; i < 7; i++) {
                try {
                    elementsDisponibles.acquire();
                    mutexBuffer.acquire();

                    Integer produit = buffer[indexRetrait];
                    buffer[indexRetrait] = null;
                    indexRetrait = (indexRetrait + 1) % TAILLE_BUFFER;
                    totalConsomme++;

                    mutexBuffer.release();
                    placesLibres.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}