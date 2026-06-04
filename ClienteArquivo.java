
import java.io.*;
import java.net.*;

public class ClienteArquivo {
    public static void main(String[] args) {

        File file = new File("imagem de teste.jpg"); 

        if (!file.exists()) {
            System.err.println("Arquivo não encontrado!");
            return;
        }

        try (Socket socket = new Socket("localhost", 5000);
             DataOutputStream saidaDados = new DataOutputStream(socket.getOutputStream());
             FileInputStream entradaArquivo = new FileInputStream(file)) {

            saidaDados.writeUTF(file.getName());
            saidaDados.writeLong(file.length());

            System.out.println("Enviando arquivo: " + file.getName());

            byte[] buffer = new byte[4096];
            int read;
            while ((read = entradaArquivo.read(buffer)) != -1) {
                saidaDados.write(buffer, 0, read);
            }
            
            System.out.println("Arquivo enviado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro no envio: " + e.getMessage());
        }
    }
}