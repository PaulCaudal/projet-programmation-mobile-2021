import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class coServeur {
    public static void main(String[] args) throws Exception {

        // Example of a distant calculator
        ServerSocket ssock = new ServerSocket(9876);
        while (true) {
            Socket sock = ssock.accept();
            System.out.println("connection established");
            new Thread(new Serveur(sock)).start();

        }



    }
}
