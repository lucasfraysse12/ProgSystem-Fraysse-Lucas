public class TestCompteur {
    public static void main(String[] args) {
        
        CompteurDangereux[] threads = new CompteurDangereux[50*9];

        for (int i = 0; i < 50; i++) {
            CompteurDangereux.resetCompteur();
            threads[i] = new CompteurDangereux();
            threads[i + 1] = new CompteurDangereux();
            threads[i + 2] = new CompteurDangereux();
            threads[i + 3] = new CompteurDangereux();
            threads[i + 4] = new CompteurDangereux();
            threads[i + 5] = new CompteurDangereux();
            threads[i + 6] = new CompteurDangereux();
            threads[i + 7] = new CompteurDangereux();
            threads[i + 8] = new CompteurDangereux();
            threads[i].start();
            threads[i + 1].start();
            threads[i + 2].start();
            threads[i + 3].start();
            threads[i + 4].start();
            threads[i + 5].start();
            threads[i + 6].start();
            threads[i + 7].start();
            threads[i + 8].start();
        }
    }
}