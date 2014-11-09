package com.github.takasab.Game;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import static org.bukkit.Color.*;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class ColorTool {
    public static Color getColor(ItemStack item){
       if(item.getType()==Material.WOOL){
           short id = item.getDurability();
           switch (id){
               case 0: return Color.WHITE;
               case 2: return Color.PURPLE;
               case 3: return Color.AQUA;
               case 4: return Color.YELLOW;
               case 10: return Color.PURPLE;
               case 5: return GREEN;
               case 15 : return Color.BLACK;
               case 14: return Color.RED;
               case 13: return GREEN;
           }
       }else if(item.getType()==Material.LEATHER){
           LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
           return meta.getColor();
       }
        return Color.BLACK;
    }
    public static short getColorID(Color color){
        switch (color){
            case WHITE: return 0;
            case PURPLE : return 2;
            case AQUA : return 3;
            case YELLOW : return 4;
            case PURPLE : return  10;
            case GREEN : return 5;
            case BLACK: return 15;
            case RED : return 14;
            case GREEN : return 13;
        }
        return 0;
    }
}
