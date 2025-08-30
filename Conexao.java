import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Conexao extends Thread {
    private Socket socket;

    public Conexao(final Socket socket) { this.socket = socket; }

    public void run() {
        var saida = new Scanner(cliente.getInputStream());
        while (saida.hasNextLine()) { System.out.println(saida.nextLine()); }
    }

    public static void main(String args[]) throws IOException {
        var servidor = new ServerSocket(12345);
        System.out.println("Servidor iniciado na 12345!");

        while (true) {
            var socket = servidor.accept();

            System.out.println("Conexão estabelecida!");

            new Conexao(socket).start();
        }
    }
}
