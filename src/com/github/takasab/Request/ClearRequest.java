package com.github.takasab.Request;

import com.github.takasab.GameProcess.RepeatingRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class ClearRequest extends RepeatingRequest{
   String world;
    public ClearRequest(String world){this.world=world; }
    @Override
    public void run() {
       new Thread() {
           @Override
           public void run() {
               while(true)
               {
                   for(LivingEntity le: Bukkit.getWorld(world).getLivingEntities()) {
                       if(le instanceof Sheep) {
                           boolean on = le.isOnGround();
                           if (on == false) continue;
                           try {
                               Thread.sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                           if (le.isOnGround() == false) {
                              le.teleport(le.getLocation().add(0,-1,0));
                           }
                       }
                   }

               }
           }
       }.start();
    }
/*Link start!*/

}
