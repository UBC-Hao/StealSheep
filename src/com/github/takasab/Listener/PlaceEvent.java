package com.github.takasab.Listener;

import com.github.takasab.Game.ColorTool;
import com.github.takasab.Game.GamePool;
import com.github.takasab.Game.User;
import org.bukkit.Effect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class PlaceEvent implements Listener{
/*Link start!*/
    @EventHandler
    void onWalk(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if(p.getPassenger()==null) return;
        User user = new User(p);
        if(GamePool.getPlayerIn(p)==null) return;
        System.out.print("listen");
        System.out.print(p.getLocation().getBlock().getRelative(0, -1, 0).getData());
        System.out.print(ColorTool.getColorID(user.getColor()));
        if(p.getLocation().getBlock().getRelative(0, -1, 0).getData()==(ColorTool.getColorID(user.getColor()))){
        if(p.getPassenger().getType() == EntityType.SHEEP){
            System.out.print("drop");
            user.leaveWithColor();
            int amount = user.getPassagerNum();
            user.addScore(amount);
            p.sendMessage("偷回 "+amount+" 只羊"+",获得同等数量积分");
            p.getLocation().getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES,6);
        }
        }
     }
}
