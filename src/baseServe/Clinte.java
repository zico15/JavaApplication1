/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseServe;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zequi
 */
public class Clinte extends Thread{
    
    public Socket socket;
    public InputStream inp = null;
    public BufferedReader brinp = null;
    public DataOutputStream out = null;
    public String nickName = "local";
    public HashMap<String,comandoEvent> events = new HashMap<>();
    
    public Clinte(int porta){
    
    
        try {
            socket = new Socket("localhost", porta);
        } catch (IOException ex) {
            Logger.getLogger(Clinte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Clinte(Socket clientSocket) {
             this.socket = clientSocket;
    }

    @Override
    public void run() {
       conectar();
    }

    
    
    
    public void conectar() {
        
   
            System.out.println("EchoThread: "+socket.isConnected());
            
            try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
           
            System.out.println("inp: "+ (inp!=null )+  " brinp: " + (brinp!=null )+  " out: "+ (out!=null));
            String line;
            
            
           
            
            
        while (true) {
            try {
               
                line = brinp.readLine();
               
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                }else  if(!line.trim().isEmpty()){ 
                    
                    
                    comandos(line);
                    //System.out.println("Line: "+line);
                }
                
            } catch (IOException e) {
                return;
            }
        }
        } catch (IOException e) {
            System.out.println("EchoThread: "+e.getLocalizedMessage());
        }
    }


    public boolean isConnected(){
    
    
        return socket != null && socket.isConnected();
    }
    
    public boolean send(String type,String msg){
    
        try {
           out.writeBytes(type+ "{:}"+msg+"\r\n");
           out.flush();
           return true;
           // out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Clinte.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    
    public void comandos(String msg){
    
        String type = msg.substring(0,msg.indexOf("{:}"));
        String value = msg.substring(msg.indexOf("{:}") +  3,msg.length());
    
    
        switch(type){
        
            
            case "nickName":
                nickName = value;
                for(Clinte c : Servidor.clintes){                
                  if(c.isConnected()){  
                  c.sendListPlayers();
                }}
                break;
                
            case "newRoom":        
                Room room = new Room(value);
                Servidor.rooms.add(room);
                room.login(this);
                for(Clinte c : Servidor.clintes){                
                  if(c.isConnected()){  
                  c.sendListRooms();
                }}
                break;
                
            case "players":
                
               sendListPlayers();
                break;
                
            case "rooms":
                
               sendListRooms();
                break;   
             case "roomJogada":
                String[] values = value.split(",");
                
                int rj = Integer.valueOf(values[0]);
                String jogador = values[1];
                String jogada = values[2];
                
                
                Room sala = Servidor.rooms.get(rj);
                if(sala!=null){                
                
                sala.jogada(jogador,jogada);
                }
                
                break;   
                
                
            case "loginRoom":
                
            Room r =  Servidor.rooms.get(Integer.valueOf(value));
            
            r.login(this);
                
           break;  
            
                default: 
                    if(events.containsKey(type)){                    
                    comandoEvent event = events.get(type);
                    event.event(value);
                }
                    
                
                
        }
        System.out.println("nickName: "+nickName + " type: "+type+ " value: "+value);
    
    }
    
    
    public void sendListPlayers(){
     String list = null;
                
                for(Clinte c : Servidor.clintes){
                
                  if(!"local".equals(c.nickName) && c.isConnected()){  
                      if(list == null){ list = c.nickName; } else { list += ","+ c.nickName; } 
                  }
                
                }
                send("playersList", list);
    
    }
    
     public void sendListRooms(){
     String list = null;
                
                for(Room c : Servidor.rooms){
                
                  
                      if(list == null){ list = c.name; } else { list += ","+ c.name; } 
              
                
                }
                send("roomsList", list);
    
    }
}