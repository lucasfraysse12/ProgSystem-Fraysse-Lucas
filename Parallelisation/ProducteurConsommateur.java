import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;

private static final int TAILLE_BUFFER = 5;
private final Integer[] buffer = new Integer[TAILLE_BUFFER];

// TODO: Déclarer 3 sémaphores avec les bonnes valeurs initiales
private final Semaphore placesLibres = new Semaphore(???);
private final Semaphore elementsDisponibles = new Semaphore(???);  
private final Semaphore mutexBuffer = new Semaphore(???); // quel erreur ici ? vous pouvez le résoudre plus tard