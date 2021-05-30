/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseServe;

/**
 *
 * @author zequi
 */
public class Room {
    
    public String name;
    public Clinte player_1;
    public Clinte player_2;
    public String jogada1 = "interrogacao",jogada2 = "interrogacao";
    
    public Room(String name){ this.name =name; }
    
    
    public void login(Clinte clinte){
    
     if(player_1== null) {
         player_1 = clinte;        
     } else if(player_2== null) {
         player_2 = clinte;          
     }
    
       String list = player_1 != null ? player_1.nickName +","+ (player_2 != null ? player_2.nickName : "?") :  player_2 != null ? "?,"+player_2.nickName : "?";
       if(player_1!=null)player_1.send("playersRoom", list);
       if(player_2!=null)player_2.send("playersRoom", list);
       
       
       if(player_1!=null && player_2!=null){ newPartida(); }
    }
    
    
    public void newPartida(){
    
        player_1.send("newPartida", player_1.nickName);
    
    }
    
    public void jogada(String player,String jogada){
    
        if(player_1.nickName == null ? player == null : player_1.nickName.equals(player)){
            jogada1 = jogada; player_2.send("newPartida", player_2.nickName); 
        }else jogada2 = jogada;
        player_1.send("newJogada", jogada1 + "," +jogada2);
        player_2.send("newJogada", jogada1 + "," +jogada2);
        
    
    }
    
    public boolean isLivre(){
    
     return player_1 == null || player_2 == null;
    }
}
