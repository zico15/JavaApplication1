/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseServe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author zequi
 */
public class Servidor {
    
    static final int PORT = 8000;

    public static ArrayList<Clinte> clintes = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    
    
    public void conectar() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado");
       
        while (true) {
            try {
                socket = serverSocket.accept();
                Clinte clinte = new Clinte(socket);
                System.out.println("new clinte: "+socket.getInetAddress().getHostAddress());
                clintes.add(clinte);
                clinte.start();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
           
        }
         } catch (IOException e) {
            System.out.println("I/O error: " + e);

        }
    }
}
