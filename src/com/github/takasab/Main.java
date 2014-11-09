package com.github.takasab;

import com.github.takasab.Listener.CrraySheep;
import com.github.takasab.Listener.PlaceEvent;
import com.github.takasab.command.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
   @Override
   public void onEnable(){
       System.out.print("偷羊羊插件 V0.01");
       Bukkit.getPluginManager().registerEvents(new CrraySheep(), this);
       Bukkit.getPluginManager().registerEvents(new PlaceEvent(), this);
       Bukkit.getPluginManager().registerEvents(new TestCommand(), this);
   }
}
