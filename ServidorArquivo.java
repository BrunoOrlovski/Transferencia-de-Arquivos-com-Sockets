
import java.io.*;
import java.net.*;

public class ServidorArquivo {
    public static void main(String[] args) {
        int porta = 5000;
        
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta " + porta);
            System.out.println("Aguardando conexão do cliente...");

            try (Socket socket = serverSocket.accept();
                 DataInputStream entradaDados = new DataInputStream(socket.getInputStream())) {
                
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                String fileName = entradaDados.readUTF();
                long fileSize = entradaDados.readLong();
                
                System.out.println("Recebendo arquivo: " + fileName + " (" + fileSize + " bytes)");

                try (FileOutputStream saidaArquivo = new FileOutputStream("recebido_" + fileName)) {
                    byte[] buffer = new byte[4096];
                    int read;
                    long totalRead = 0;

                    while (totalRead < fileSize && (read = entradaDados.read(buffer)) != -1) {
                        saidaArquivo.write(buffer, 0, read);
                        totalRead += read;
                    }
                }
                System.out.println("Arquivo recebido com sucesso!");
            }
        } catch (IOException e) {
            System.err.println("Erro de comunicação: " + e.getMessage());
        }
    }
}