package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("== Cliente ==\n");
        System.out.println("Comandos disponíveis:");
        System.out.println("'readdir' - Listar o conteúdo de um diretório");
        System.out.println("'rename' - Renomeia um arquivo");
        System.out.println("'create' - Cria um arquivo");
        System.out.println("'remove' - Deleta um arquivo");
        try{

            Socket socket = new Socket("localhost", 7000);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            while(true){
                System.out.println(">");
                Scanner keyboard = new Scanner(System.in);
                String command = keyboard.nextLine();
                dataOutputStream.writeUTF(command);
                String receivedMessage = dataInputStream.readUTF();
                if(!receivedMessage.equals(""))
                    System.out.println(receivedMessage);
            }
        }
        catch(IOException ioException){
            System.err.println(ioException);
        }
    }
}
