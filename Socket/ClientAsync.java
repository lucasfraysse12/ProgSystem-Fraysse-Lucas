import java.net.*;
import java.io.*;

public class ClientAsync {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connecté au serveur");

        ReceptionHandler handler = new ReceptionHandler(socket);
        Thread lectureThread = new Thread(handler);
        lectureThread.setDaemon(true);
        lectureThread.start();

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader clavier = new BufferedReader(
            new InputStreamReader(System.in)
        );

        String texte;
        while ((texte = clavier.readLine()) != null) {
            out.println(texte);
        }

        handler.stop();
        socket.close();
    }
}

class ReceptionHandler implements Runnable {
    private Socket socket;
    private volatile boolean running = true;

    public ReceptionHandler(Socket socket) {
        this.socket = socket;
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            String ligne;
            while (running && (ligne = in.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException ignored) {
        }
    }
}