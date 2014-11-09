package com.github.takasab.Game;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class User {
/*Link start!*/
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
    public Color getColor(){
        PlayerInventory inventory=player.getInventory();
        ItemStack hat = inventory.getHelmet();
        if(hat ==null) return Color.WHITE;
        if(hat.getType() != Material.LEATHER) return Color.WHITE;
        return ((LeatherArmorMeta)hat.getItemMeta()).getColor();
    }
    public int getPassagerNum(){
        Easycounter easycounter = new Easycounter();
        return easycounter.getPassagerNumber(player);
    }
    public void addSheep(Sheep sheep){
        if(getPassagerNum()==3) return;
        final Color color = sheep.getColor().getColor();
        ItemStack item = new ItemStack(35);
        item . setDurability(ColorTool.getColorID(color));
        player.getInventory().setItem(getPassagerNum()+3,item);

        Easycounter c = new Easycounter();
        c.getSheep(player).setPassenger(sheep);
    }
    public void leaveSheep(){
      Easycounter c = new Easycounter();
      c.leaveAll(player);
    }
    public void leaveWithColor(){
       Easycounter c = new Easycounter();
       c.leaveAll(player,getColor());
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
            le.getPassenger().eject();
            leaveAll(le.getPassenger());
        }
    }
    public void leaveAll(Entity le,Color color){
        if(le.getPassenger()!=null){
            ((Sheep)le.getPassenger()).setColor(DyeColor.getByColor(color));
            le.getPassenger().eject();
            leaveAll(le.getPassenger(),color);
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
