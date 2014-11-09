package com.github.takasab.Listener;

import com.github.takasab.Game.GamePool;
import com.github.takasab.Game.User;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;


/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class CrraySheep implements Listener{
/*Link start!*/
    //右键举起小羊
    @EventHandler
    void onCrray(PlayerInteractEntityEvent event){
        System.out.print("listen");
        if(GamePool.getPlayerIn(event.getPlayer())!=null){
            System.out.print("crray");
            User user = new User(event.getPlayer());
            if(user.isHandItem("神奇的马鞍")){
                if(event.getRightClicked() instanceof Sheep) {
                    System.out.print("add");
                    user.addSheep((Sheep)event.getRightClicked());
                }
            }
        }
    }
}
