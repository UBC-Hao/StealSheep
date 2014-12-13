package com.github.takasab.Game;

import org.bukkit.Color;
import org.bukkit.DyeColor;
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
               case 11: return Color.BLUE;//
               case 4: return Color.YELLOW;//
               case 5: return Color.GREEN;//
               case 15 : return Color.BLACK;
               case 14: return Color.RED;//
           }
       }else if(item.getType()==Material.LEATHER){
           LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
           return meta.getColor();
       }
        return Color.BLACK;
    }
    public static short getColorID(final Color color){
        if(color .equals( WHITE )) return 0;
        if(color .equals( PURPLE )) return 2;
        if(color .equals( BLUE)) return 11;
        if(color .equals( YELLOW)) return 4;
        if(color .equals( GREEN)) return 5;
        if(color .equals( BLACK)) return 15;
        if(color .equals(RED)) return 14;
        return 0;
    }
    public static short getDyeToColorID(DyeColor color){
        switch(color){
            case BLACK:return 15;
            case BLUE: return 11;
            case BROWN: return 12;
            case RED : return 14;
            case YELLOW: return 4;
            case PURPLE: return 2;
            case WHITE: return 0;
            case GREEN: return 5;
        }
        return 0;
    }
    public static Color getByID(short id){
               switch (id){
               case 0: return Color.WHITE;
               case 2: return Color.PURPLE;
               case 3: return Color.AQUA;
               case 4: return Color.YELLOW;
               case 5: return Color.GREEN;
               case 15 : return Color.BLACK;
               case 14: return Color.RED;
               case 11: return Color.BLUE;
           }
               return Color.WHITE;
    }
    
    public static DyeColor toDyeColor(Color color){
        if(color .equals( WHITE )) return DyeColor.WHITE;
        if(color .equals( PURPLE )) return DyeColor.PURPLE;
        if(color .equals( AQUA)) return DyeColor.BLUE;
        if(color .equals( YELLOW)) return DyeColor.YELLOW;
        if(color .equals( GREEN)) return DyeColor.GREEN;
        if(color .equals( BLACK)) return DyeColor.BLACK;
        if(color .equals(RED)) return DyeColor.RED;
        if(color.equals(BLUE)) return DyeColor.BLUE;
        return DyeColor.WHITE;
    }
}
