import java.net.*;
import java.io.*;

public class ServeurMulti {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Serveur multi-clients démarré");

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Nouveau client : " + client.getInetAddress());

            Thread t = new Thread(new ClientHandler(client));
            t.start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket client;

    public ClientHandler(Socket socket) {
        this.client = socket;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
            );
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            String ligne;
            while ((ligne = in.readLine()) != null) {
                System.out.println("[" + threadName + "] Reçu : " + ligne);
                out.println("ECHO: " + ligne);
            }
            System.out.println("[" + threadName + "] Client déconnecté");
        } catch (IOException e) {
            System.err.println("[" + threadName + "] Erreur : " + e.getMessage());
        } finally {
            try { 
                client.close(); 
            } catch (IOException ignored) {}
        }
    }
}