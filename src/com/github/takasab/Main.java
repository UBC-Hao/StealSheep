package com.github.takasab;

import com.github.takasab.Game.GamePool;
import com.github.takasab.Listener.CrraySheep;
import com.github.takasab.Listener.PlaceEvent;
import com.github.takasab.Listener.ProtectListener;
import com.github.takasab.Request.ClearRequest;
import com.github.takasab.Setting.EasySetting;
import com.github.takasab.command.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
   public static EasySetting games ;
   public static EasySetting config ;
   public static EasySetting players ;
   @Override
   public void onEnable(){
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
       for(World world:Bukkit.getWorlds())
       GamePool.pushRequst(new ClearRequest(world.getName()));
       //小游戏创建
       for(String str:games.object().getKeys(false)){
          GamePool.createGame(str);
       }
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
}
