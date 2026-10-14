public class Calcul extends Thread {


    private int valeur;
    private int multiplicateur;
    private int index;
    private long resultat = 0;

    public Calcul(int valeur, int multiplicateur, int index) {
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
        this.index = index;
    }

    @Override
    public void run() {
        
        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
        }

    }

    public long getResultat() {
        return resultat;
    }

}