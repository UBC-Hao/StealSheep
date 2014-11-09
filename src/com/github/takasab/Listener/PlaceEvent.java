package com.github.takasab.Listener;

import com.github.takasab.Game.ColorTool;
import com.github.takasab.Game.User;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class PlaceEvent {
/*Link start!*/
    @EventHandler
    void onWalk(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if(p.getPassenger()==null) return;
        User user = new User(p);
        if(ColorTool.getColor(p.getLocation().getBlock().getState().getData().toItemStack())==user.getColor()){
        if(p.getPassenger() instanceof Sheep){
            user.leaveWithColor();
        }
        }
     }
}
