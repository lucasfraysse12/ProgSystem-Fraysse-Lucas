public class TestCompteur {
    public static void main(String[] args) {
        
        
        Thread[] threads = new Thread[9];
        int[] resultats = new int[50];
        int compteurCorrect = 0;
        int minimum;

        for (int i = 0; i < 50; i++) {
            CompteurDangereux.resetCompteur();
            for (int j = 0; j < 9; j++) {
                threads[j] = new Thread(new CompteurDangereux.Incrementeur("inc", 1111111));
            }
            for (int j = 0; j < 9; j++) {
                threads[j].start();
            }
            for (int j = 0; j < 9; j++) {
                try {
                    threads[j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            resultats[i] = CompteurDangereux.getCompteur();
        }

        minimum = resultats[0];
        for (int res : resultats) {
            if (res == 9 * 1111111) {
                compteurCorrect++;
            }
            if (res < minimum) {
                minimum = res;
            }
        }
        
        
        System.out.println("Nombre bonnes réponses : " + compteurCorrect);
        System.out.println("Pourcentage réussite : " + (float)(compteurCorrect)/50*100);
        System.out.println("Minimum obtenu : " + minimum);


    }
}