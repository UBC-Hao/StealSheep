package com.github.takasab.Game;

import com.github.takasab.GameProcess.Request;
import com.github.takasab.Request.CarryRequst;
import com.github.takasab.Request.GameStartRequest;
import com.github.takasab.Request.GameStopRequest;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class Game {
/*Link start!*/
    boolean start = false;
    List<Player> players = new ArrayList<Player>();
    public void start(){
        this.start=true;
        Request req = new GameStartRequest(this,60);
        GamePool.pushRequst(req);
    }
    public boolean contains(Player player){
        return players.contains(player);
    }
    public void join(Player player){
        if(!start) {
            players.add(player);
            Request req = new CarryRequst(this,player);
            GamePool.pushRequst(req);
        }
    }
    public List<Player> getPlayers(){
        return players;
    }
    public void left(Player player){
        players.remove(player);
        //正在写
    }
    public void stop(){
        for(Player p:getPlayers()){
            left(p);
        }

        Request req = new GameStopRequest(this);
        GamePool.pushRequst(req);
    }

}
