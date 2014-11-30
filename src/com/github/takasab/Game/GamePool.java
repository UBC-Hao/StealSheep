package com.github.takasab.Game;

import com.github.takasab.GameProcess.Request;
import com.github.takasab.GameProcess.RequsetBus;
import com.github.takasab.Main;
import org.bukkit.Location;
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
    public static Location lobby ;

    static ArrayList<Game> list = new ArrayList<Game>();
    static HashMap<String,Game> map = new HashMap<String, Game>();
    static RequsetBus bus = new RequsetBus();
    static HashMap<Player,Integer> scores = new HashMap<Player, Integer>();
    public static void setLobby(Location loc){
        lobby=loc;
    }
    public static void pushRequst(Request req){
        bus.pushRequst(req);
    }
    public static Game getPlayerIn(Player player){
        for(Game game:list){
            if(game.contains(player)) return game;
        }
        return null;
    }
    public static Game createGame(String name){
       Location blue = Main.games.getLocation(name+".blue");
       Location green = Main.games.getLocation(name+".green");
       Location yellow = Main.games.getLocation(name+".yellow");
       Location red = Main.games.getLocation(name+".red");
       Location sheepspawn = Main.games.getLocation(name+".spawn");
       Location lobby = Main.games.getLocation(name+".lobby");

        Location sheepblue = Main.games.getLocation(name+".sheepblue");
        Location sheepgreen = Main.games.getLocation(name+".sheepgreen");
        Location sheepyellow = Main.games.getLocation(name+".sheepyellow");
        Location sheepred = Main.games.getLocation(name+".sheepred");
       Game game = new Game(name, Main.games.object().getInt(name+".min"),
                                  Main.games.object().getInt(name+".max")
       ,blue,green,yellow,red,sheepspawn,lobby,
        sheepblue,sheepgreen,sheepred,sheepyellow );

       if(list.contains(game)){
           for(Game g:list){
               if(g.equals(game)) {
                   return g;
               }
           }
       }else {
           list.add(game);
           map.put(name,game);
       }
       return game;
    }
    public static void addPoint(Player p,int amount){
        if(scores.containsKey(p)){
            scores.put(p,scores.get(p)+amount);
        }else{
            scores.put(p,amount);
        }
        Main.players.object().set(p.getName(),scores.get(p));
        Main.players.save();
    }
    public static int getPoint(Player p){
        return scores.get(p);
    }
    public static void clearPlayerScores(Player player){
        scores.put(player,0);
    }
    public static Game getGame(String room){
        if(!map.containsKey(room)){

            return null;
        }
        return map.get(room);
    }
}
