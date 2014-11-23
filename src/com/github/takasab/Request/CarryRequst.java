package com.github.takasab.Request;

import com.github.takasab.Game.Game;
import com.github.takasab.Game.User;
import com.github.takasab.GameProcess.RepeatingRequest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class CarryRequst extends RepeatingRequest{
    final Player p;
    final Game game;
    public CarryRequst(Game game,Player p){ this.p = p ; this.game=game;}
    @Override
    public void run() {
       new Thread() {
           @Override
           public void run() {
               while(true)
               {
                   try {
                       Thread.sleep(100);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   if(game.contains(p)){
                       User user = new User(p);
                       if((p.getItemInHand()==null)||(p.getItemInHand().getType()!= Material.SADDLE)) {
                          if (p.getPassenger() != null){
                             user.leaveSheep();
                             System.out.print("leave");
                          }
                       }else if(p.getItemInHand().getType()==Material.SADDLE){
                          if(p.getPassenger()!=null){
                              user.spiltExp(user.getPassagerNum(),3);
                              p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,2*20,user.getPassagerNum()-1));
                          }
                       }
                   }else{
                       return;
                   }
               }
           }
       }.start();
    }
/*Link start!*/

}
