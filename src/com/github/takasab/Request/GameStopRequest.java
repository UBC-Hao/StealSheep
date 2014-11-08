package com.github.takasab.Request;

import com.github.takasab.Game.Game;
import com.github.takasab.GameProcess.Request;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class GameStopRequest extends Request{
/*Link start!*/
    Game game;
    public GameStopRequest(Game game){
        this.game=game;
    }
    @Override
    public void run(){
         new Thread(){
             public void run(){
                 game.stop();
             }
         }.start();
    }
}
