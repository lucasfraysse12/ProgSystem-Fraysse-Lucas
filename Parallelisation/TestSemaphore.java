import java.util.Arrays;

public class TestSemaphore {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Scenario equilibre");
        lancerTest(2, 3);

        System.out.println("Scenario desequilibre");
        lancerTest(5, 2);
    }

    private static void lancerTest(int nbProducteurs, int nbConsommateurs) throws InterruptedException {
        ProducteurConsommateur pc = new ProducteurConsommateur();

        for (int i = 0; i < nbProducteurs; i++) {
            Thread t = new Thread(pc.new Producteur());
            t.setDaemon(true);
            t.start();
        }

        for (int i = 0; i < nbConsommateurs; i++) {
            Thread t = new Thread(pc.new Consommateur());
            t.setDaemon(true);
            t.start();
        }

        for (int i = 0; i < 5; i++) {
            Thread.sleep(100);
            System.out.println("Produits: " + pc.totalProduit + 
                               " | Consommes: " + pc.totalConsomme + 
                               " | Buffer: " + Arrays.toString(pc.getBuffer()));
        }
    }
}