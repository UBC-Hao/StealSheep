package com.github.takasab.Game;

import com.github.takasab.GameProcess.Request;
import com.github.takasab.GameProcess.RequsetBus;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class GamePool {
/*Link start!*/
    static ArrayList<Game> list = new ArrayList<Game>();
    static RequsetBus bus = new RequsetBus();
    static HashMap<Player,Integer> scores = new HashMap<Player, Integer>();
    public static void pushRequst(Request req){
        bus.pushRequst(req);
    }
    public static Game getPlayerIn(Player player){
        for(Game game:list){
            if(game.contains(player)) return game;
        }
        return null;
    }
    public static Game createGame(){
       Game game = new Game();
       list.add(game);
       return game;
    }
    public static void addPoint(Player p,int amount){
        if(scores.containsKey(p)){
            scores.put(p,scores.get(p)+amount);
        }else{
            scores.put(p,amount);
        }
    }
    public static void clearPlayerScores(Player player){
        scores.put(player,0);
    }

}
