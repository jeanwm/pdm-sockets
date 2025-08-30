import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Conectar ao servidor
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conectado ao servidor!");

            // Criar streams de entrada e saída
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Criação de thread para escutar mensagens do servidor
            Thread listener = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Servidor: " + message);
                        if(message.equals("!sair")){
                            socket.close();
                            in.close();
                            out.close();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listener.start();

            // Enviar mensagens para o servidor
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                message = userInput.readLine();
                out.println(message);
                if(message.equals("!sair")){
                    socket.close();
                    in.close();
                    out.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
