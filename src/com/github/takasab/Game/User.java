package com.github.takasab.Game;

import org.bukkit.entity.Player;

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
}
