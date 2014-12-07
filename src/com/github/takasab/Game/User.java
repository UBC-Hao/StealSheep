package com.github.takasab.Game;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class User {
/*Link start!*/
    static HashMap<Player,Color> hm = new HashMap<Player, Color>();
    Player player;
    public User(Player player){
        this.player=player;
    }
    //分割玩家的经验条 份数,总数
    public void spiltExp(float per,int all){
        this.player.setExp(per/all);
    }
    //返回是否拿着特定物品
    public boolean isHandItem(String name){
        if(player.getItemInHand()==null) return false;
        if(!player.getItemInHand().hasItemMeta()) return false;
        if(!player.getItemInHand().getItemMeta().hasDisplayName()) return false;
        if(player.getItemInHand().getItemMeta().getDisplayName().contains(name)) return true;
        return false;
    }
    public int getScore(){
       return GamePool.getPoint(player);
    }
    public void addScore(int amount){
        GamePool.addPoint(player,amount);
    }
    public void clearScore(){
        GamePool.clearPlayerScores(player);
    }
    public Color getColor(){
        return hm.get(player);
    }
    public void setColor(Color color){
        ItemStack item = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        hm.put(player,color);
        player.getInventory().setHelmet(item);
    }
    public int getPassagerNum(){
        Easycounter easycounter = new Easycounter();
        return easycounter.getPassagerNumber(player);
    }
    public synchronized void addSheep(Sheep sheep){
        if(getPassagerNum()>=3) return;
        ItemStack item = new ItemStack(35);
        item . setDurability(ColorTool.getDyeToColorID(sheep.getColor()));
        
        player.getInventory().setItem(getPassagerNum()+3,item);
        System.out.print(getPassagerNum()+3);
        player.updateInventory();

        Easycounter c = new Easycounter();
        if(c.getSheep(player) == null){
            player.setPassenger(sheep);
        }else{
            c.getSheep(player).setPassenger(sheep);
        }
        
    }
    public synchronized void leaveSheep(){
      Easycounter c = new Easycounter();
      c.leaveAll(player);
        clearWool();
    }
    public synchronized void leaveWithColor(){
       System.out.print("leave with color");
       Easycounter c = new Easycounter();
       c.leaveAll(player,getColor());
       clearWool();
       this.addScore(this.getPassagerNum());
    }
    void clearWool(){
        player.getInventory().clear(3);
        player.getInventory().clear(4);
        player.getInventory().clear(5);
        player.getInventory().clear(6);
        player.updateInventory();
    }
}
class Easycounter{
    int passgernum = 0;
    Sheep sheep;
    public int getPassagerNumber(Entity entity){
        if(entity.getPassenger()!=null){
            passgernum++;
            getPassagerNumber(entity.getPassenger());
        }
        return passgernum;
    }
    public void leaveAll(Entity le){
        
        if(le.getPassenger()!=null){
            Entity passager = le.getPassenger();
            le.eject();
            passager.teleport(le.getLocation().add(0,1,0));
            leaveAll(passager);
        }
    }
    public void leaveAll(Entity le,Color color){
        if(le.getPassenger()!=null){
            ((Sheep)le.getPassenger()).setColor(ColorTool.toDyeColor(color));
            Entity entity = le.getPassenger();
            entity.teleport(le.getLocation().add(0,1,0));
            le.eject();
            leaveAll(entity,color);
        }
    }

    public Sheep getSheep(Entity entity){
        if(entity.getPassenger() != null){
            this.sheep = (Sheep)entity.getPassenger();
            getSheep(entity.getPassenger());
        }
        return sheep;
    }
}
