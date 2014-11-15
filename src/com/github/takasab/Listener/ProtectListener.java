package com.github.takasab.Listener;

import com.github.takasab.Game.GamePool;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class ProtectListener implements Listener{
/*Link start!*/
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
            event.getWhoClicked().closeInventory();
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

}
