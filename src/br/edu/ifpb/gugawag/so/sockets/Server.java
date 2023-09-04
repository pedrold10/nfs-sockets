package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7000);
        System.out.println("==Servidor==");
        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

            try{
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                String resultado = "";
                String[] list = new String[0];
                while(true){
                    String clienteInput = dataInputStream.readUTF();
                    if(clienteInput.equalsIgnoreCase("readdir")){
                        File path = new File(list[1]);
                        if(path.exists()){
                            File[] files = path.listFiles();
                            for(File file : files){
                                resultado+=file.getName();
                            }
                        }
                        dataOutputStream.writeUTF(resultado);
                        
                    }
                    else if(clienteInput.split(" ")[0].equals("create")){
                        File novo = new File(list[1]);
                        if(!novo.exists()){
                            novo.createNewFile();
                            dataOutputStream.writeUTF("Arquivo criado com sucesso!");
                        }
                    }
                    else if(clienteInput.split(" ")[0].equalsIgnoreCase("rename")){
                        File antigo = new File(list[1]);
                        File novo = new File(list[2]);

                        if(antigo.exists()){
                            antigo.renameTo(novo);
                            dataOutputStream.writeUTF("Arquivo renomeado com sucesso!");
                        }

                    }
                    else if(clienteInput.split(" ")[0].equalsIgnoreCase("remove")){
                        File arquivoRemove = new File(list[1]);
                        if(arquivoRemove.exists()){
                            arquivoRemove.delete();
                            dataOutputStream.writeUTF("Arquivo removido com sucesso!");
                        }
                    }

                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            out.println("Tchau!");
            out.close();
            clientSocket.close();
        }

    }
}
