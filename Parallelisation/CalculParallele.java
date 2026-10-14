public class CalculParallele {
    
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MULTIPLICATEUR = 1000000000;

    public static void main(String[] args) {
        System.out.println("=== Calcul Parallele ===");
        Calcul[] threads = new Calcul[DONNEES.length];
        long debut = System.nanoTime();
        long sommeTotal = 0;

        // TODO: Ce code sera remplacé par une version parallèle
        for (int i = 0; i < DONNEES.length; i++) {
            threads[i] = new Calcul(DONNEES[i], MULTIPLICATEUR, i);
        }
        for (int i = 0; i < DONNEES.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < DONNEES.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < DONNEES.length; i++) {
            sommeTotal += threads[i].getResultat();
        }


        long duree = (System.nanoTime() - debut) / 1_000_000;
        System.out.println("Résultat total : " + sommeTotal);
        System.out.println("Durée : " + duree + " ms");
    }
}