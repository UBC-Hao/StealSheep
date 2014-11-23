package com.github.takasab.Setting;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class EasySetting {
/*Link start!*/
    File file;
    FileConfiguration configuration;
    public EasySetting(File datafolder,String path){
        File f = new File(datafolder,path);
        if(!f.exists()){
            try {
                f.createNewFile();
            }catch(Exception e){}
        }
        configuration = YamlConfiguration.loadConfiguration(f);
    }
    public FileConfiguration object(){
        return configuration;
    }
    public void save(){
        try{
            configuration.save(file);
        }catch(Exception e)
        {}
    }
    public Location getLocation(String str){
        int x = configuration.getInt(str+".x");
        int y = configuration.getInt(str+".y");
        int z = configuration.getInt(str+".z");
        String world = configuration.getString(str+".world");
        return new Location(Bukkit.getWorld(world),x,y,z);
    }
    public void setLocation(String str,Location loc){
        configuration.set(str+".x",loc.getBlockX());
        configuration.set(str+".y",loc.getBlockY());
        configuration.set(str+".z",loc.getBlockZ());
        configuration.set(str+".world",loc.getWorld().getName());
    }
}
