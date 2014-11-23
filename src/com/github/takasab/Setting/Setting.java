package com.github.takasab.Setting;

import com.github.takasab.Main;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class Setting {
/*Link start!*/
    /*

Games.yml 储存游戏选项 可读可写
name:
  max:
  min:
  spawn:
    x:
    y:
    z:
    world:
  blue:
    x:
    y:
    z:
    world:
  green:
    ···
  yellow:
    ···
  red:
    ···

config.yml
  wait: 等待时间,int

     */
    public static int WAITTIME = Main.config.object().getInt("wait");//等待时间
}
