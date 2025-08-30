import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor aguardando clientes...");

            // Aguardando uma conexão do cliente
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado!");

            // Criando os streams para comunicação
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Criação de thread para escutar mensagens do cliente
            Thread listener = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Cliente: " + message);
                        if(message.equals("!sair")){
                            serverSocket.close();
                            in.close();
                            out.close();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listener.start();

            // Enviar mensagens ao cliente
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                message = userInput.readLine();
                out.println(message);
                if(message.equals("!sair")){
                    serverSocket.close();
                    in.close();
                    out.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
