package com.github.takasab;

import com.github.takasab.Game.GamePool;
import com.github.takasab.Listener.CrraySheep;
import com.github.takasab.Listener.PlaceEvent;
import com.github.takasab.Listener.ProtectListener;
import com.github.takasab.Setting.EasySetting;
import com.github.takasab.command.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
   public static EasySetting games ;
   public static EasySetting config ;
   public static Main handle;
   public static EasySetting players ;
   @Override
   public void onEnable(){
       handle = this;
       System.out.print("偷羊羊插件 V0.01");
       Bukkit.getPluginManager().registerEvents(new CrraySheep(), this);
       Bukkit.getPluginManager().registerEvents(new PlaceEvent(), this);
       Bukkit.getPluginManager().registerEvents(new TestCommand(), this);
       Bukkit.getPluginManager().registerEvents(new ProtectListener(),this);

       //初始化配置
       games = new EasySetting(getDataFolder(),"Games.yml");
       config = new EasySetting(getDataFolder(),"Config.yml");
       players = new EasySetting(getDataFolder(),"Players.yml");

       //自动修复小羊飞空的线程

       //小游戏创建
       for(String str:games.object().getKeys(false)){
          GamePool.createGame(str);
       }
       GamePool.setLobby(config.getLocation("lobby"));
       //自动恢复玩家饥饿
       new Thread(){
           public void run(){
               while(true) {
                   try {
                       Thread.sleep(10000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   for (Player p : Bukkit.getOnlinePlayers()) {
                       p.setFoodLevel(20);
                   }
               }
           }
       }.start();
   }
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
       if (sender instanceof Player) {
           if (args == null) return false;
           if(args.length<=0) return false;
           Player player = (Player) sender;
           if (GamePool.getGame(args[0]) == null) {
               player.sendMessage("不正确的游戏房间");
               return true;
           }
           GamePool.getGame(args[0]).join(player);
           return true;
       }
       return false;
   }
}
