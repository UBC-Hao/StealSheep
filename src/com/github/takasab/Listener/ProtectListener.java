package com.github.takasab.Listener;

import com.github.takasab.Game.GamePool;
import com.github.takasab.Game.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class ProtectListener implements Listener{
/*Link start!*/
    //玩家复活
@EventHandler
void onDied(EntityDamageByEntityEvent event){
    if(!(event.getEntity() instanceof Player)){return;}
    Player p = (Player) event.getEntity();
    if(GamePool.getPlayerIn(p)!=null){
        if(p.getHealth()-event.getDamage()<=0){
          GamePool.getPlayerIn(p).died(p);
          if(event.getDamager() instanceof Player) {
              GamePool.getPlayerIn(p).broadcast(ChatColor.RED+"玩家 " + p.getName() + " 被 "+((Player) event.getDamager()).getName()+" 杀死");
              Player killer = (Player) event.getDamager();
              killer.sendMessage("你成功杀死玩家 "+p.getName()+" 获得 1 积分");
              User user = new User(killer);
              user.addScore(1);
          }
        }
    }
}

    @EventHandler
    void onPlace(BlockPlaceEvent event){
        if(GamePool.getPlayerIn(event.getPlayer())!=null){
            event.setCancelled(true);
        }
    }
    @EventHandler
    void onBreak(BlockBreakEvent event){
        if(GamePool.getPlayerIn(event.getPlayer())!=null){
            event.setCancelled(true);
        }
    }
    @EventHandler
    void onDrop(PlayerDropItemEvent event){
        if(GamePool.getPlayerIn(event.getPlayer())!=null){
            event.setCancelled(true);
        }
    }
    @EventHandler
    void onClick(InventoryClickEvent event){
        if(GamePool.getPlayerIn((Player)event.getWhoClicked())!=null){
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            User user = new User((Player) event.getWhoClicked());
            user.setColor(user.getColor());
            ((Player) event.getWhoClicked()).updateInventory();
        }
    }
     @EventHandler
    void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
        if(event.getEntity() instanceof Sheep){
        if(GamePool.getPlayerIn((Player)event.getDamager())!=null){
          event.setCancelled(true);

        }
        }
        }
    }
    @EventHandler
     void onPickUP(PlayerPickupItemEvent event){
        if(GamePool.getPlayerIn(event.getPlayer()) !=null)
            event.setCancelled(true);
    }
    @EventHandler
    void onLeave(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Arrow) return;
        if(event.getEntity() instanceof  Player){
            if(!(event.getDamager() instanceof Player)) return;
            
            if(!(event.getEntity() instanceof Player)) return;
            
            
            if(GamePool.getPlayerIn((Player)event.getEntity())!=null){
                User user = new User((Player)event.getEntity());
                if(GamePool.getPlayerIn((Player)event.getDamager())==null)return;
                if(GamePool.getPlayerIn((Player)event.getEntity())==null)return;
                if(event.getDamager() instanceof Player){
                    
                    User damager = new User((Player) event.getDamager());
                    if(user.getColor().equals(damager.getColor())){
                        event.setCancelled(
                                true
                        );
                        return;
                    }
                }
                user.leaveSheep();
            }
        }
    }
    @EventHandler
    void onShoot(EntityShootBowEvent event){
        if(event.getEntity() instanceof  Player){
            Player p = (Player) event.getEntity();
            if(GamePool.getPlayerIn(p)==null) return;
            p.getInventory().setItem(15,new ItemStack(Material.ARROW,16));
            p.updateInventory();
        }
    }
    @EventHandler
    void ontar(EntityTargetEvent e){
        e.setCancelled(true);
    }

      @EventHandler
    void ontar(EntityTargetLivingEntityEvent e){
        e.setCancelled(true);
    }
   //职业选择
    @EventHandler
    public void onChoose(PlayerInteractEvent event){
        if(event.getClickedBlock()==null) return;
        if(event.getClickedBlock().getType()==Material.WALL_SIGN){
            Sign sign = (Sign)event.getClickedBlock().getState();
            User user = new User(event.getPlayer());
            if(sign.getLine(0).contains("[职业]")){
            if(GamePool.getPlayerIn(event.getPlayer())==null) return;
            GamePool.getPlayerIn(event.getPlayer()).设置职业(event.getPlayer(),sign.getLine(1));
            event.getPlayer().sendMessage("你成功选择了职业:"+sign.getLine(1));
            }
        }
    }
}
