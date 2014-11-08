package com.github.takasab.Request;

import com.github.takasab.Game.Game;
import com.github.takasab.GameProcess.Request;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class GameStartRequest extends Request {
/*Link start!*/
    //wait为结束时间 单位秒
    Game game;
    public GameStartRequest(Game g,int wait){
      this.game=g;
    }
    @Override
    public void run(){

    }

}
