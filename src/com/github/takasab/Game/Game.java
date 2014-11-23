package com.github.takasab.Game;

import com.github.takasab.GameProcess.Request;
import com.github.takasab.GameProcess.StartThread;
import com.github.takasab.Request.CarryRequst;
import com.github.takasab.Setting.Setting;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

/*
 *@author:yudi
 *@QQ:1277832129
 *@Twitter:yudi_327
 (○´∀｀○)ﾉ
 */
public class Game {
/*Link start!*/
    boolean start = false;
    String name;
    int max;
    int min;
    boolean iswait = true;
    Location blue;
    Location green;
    Location yellow;
    Location red;
    Location spawn;
    Location lobby;
    HashMap<Color,Integer> map = new HashMap<Color, Integer>();
    List<Player> players = new ArrayList<Player>();
    HashMap<Player,String> job = new HashMap<Player, String>();

    Game(String name,int min,int max,
    Location blue,Location green,Location yellow,Location red,Location sheep
    ,Location lobby)
    { this.name=name;this.max=max;this.min=min;
    this.blue=blue;this.green=green;this.yellow=yellow;this.red=red;
        this.spawn=sheep; this.lobby = lobby;
    }
    public void 设置职业(Player p,String 职业){
        job.put(p,职业);

    }
    public String 获取玩家职业(Player p){
        return job.get(p);
    }
    public Location getTeamSpawn(Color c){
        if(c.equals(Color.BLUE)) return blue;
        if(c.equals(Color.RED)) return red;
        if(c.equals(Color.YELLOW)) return yellow;
        if(c.equals(Color.GREEN)) return green;
        return null;
    }
    public int getTeamScore(Color c){
        return map.get(c);
    }
    public void addTeamScore(Color color,int amount){
        map.put(color,amount);
    }
    public void start(){
        this.start=true;
        Random rand = new Random();
        for(Player p:players){
            User user = new User(p);
           if(rand.nextBoolean()){
               if(rand.nextBoolean()){
                 user.setColor(Color.BLUE);
               }else{
                 user.setColor(Color.RED);
               }
           }else{
               if(rand.nextBoolean()){
                 user.setColor(Color.YELLOW);
               }else{
                 user.setColor(Color.GREEN);
               }
           }
        }
        for(Player p:players){
            p.sendMessage(ChatColor.RED+"游戏开始");
            User user = new User(p);
            p.teleport(this.getTeamSpawn(user.getColor()));
            if(!job.containsKey(p)){
                p.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
            }
            String 职业 = job.get(p);
            if (职业.equalsIgnoreCase("berserker"))
                p.getInventory().setItem(0, new ItemStack(Material.STONE_AXE));
            if (职业.equalsIgnoreCase("archer")) {
                p.getInventory().setItem(0, new ItemStack(Material.BOW));
                p.getInventory().setItem(15, new ItemStack(Material.ARROW, 16));
            }
            if (职业.equalsIgnoreCase("saber"))
                p.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
        }
        //结束线程
        new  Thread(){
            public void run(){
                try {
                    sleep(Setting.WAITTIME* 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.stop();
            }
        }.start();
    }
    public void broadcast(String str){
        for(Player p:players)
            p.sendMessage(str);
    }
    public synchronized boolean contains(Player player){
        return players.contains(player);
    }
    public void join(Player player){
        if(!start) {
            if(players.size()>max){
                player.sendMessage(ChatColor.AQUA+"人满为患,请换其他房间");
                return;
            }
            players.add(player);
            //开始监听玩家手上的物品 如果不为马鞍的时候 移除小羊
            for(Player p:players){
                p.sendMessage(ChatColor.YELLOW+"玩家 "+player.getName()+" 进入游戏");
            }
            Request req = new CarryRequst(this,player);
            GamePool.pushRequst(req);
            player.teleport(lobby);
            if(players.size()>min){
                for(Player p:players){
                    p.sendMessage("人数已经达到要求游戏将在10秒内开始,请做好准备");
                    if(iswait){
                        new Thread(new StartThread(this)).start();
                    }
                    iswait = false;
                }
            }
        }
    }
    public void died(Player player){
        player.sendMessage(ChatColor.RED+"你死亡了,扣除一积分,请继续加油.你将暂停行动5秒,请耐心等待");
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*5,20*5));
        player.setHealth(player.getMaxHealth());
        User user = new User(player);
        user.addScore(-1);
        player.teleport(this.getTeamSpawn(user.getColor()));
    }
    public List<Player> getPlayers(){
        return players;
    }
    public void left(Player player){
        players.remove(player);
        player.teleport(GamePool.lobby);
        //正在写
    }
    public void stop(){
        for(Player p:getPlayers()){
            left(p);
            p.sendMessage(ChatColor.BLUE+"========"+ChatColor.RED+"游戏结束"+ChatColor.BLUE+"========");
            p.sendMessage(ChatColor.BLUE+"本次获得奖励:"+ChatColor.GREEN+(new User(p)).getScore());
            p.sendMessage(ChatColor.BLUE+"本队得分:"+ChatColor.RED+getTeamScore(new User(p).getColor()));
            p.sendMessage("蓝队得分:"+this.getTeamScore(Color.BLUE)+"绿队得分:"+getTeamScore(Color.GREEN));
            p.sendMessage("黄队得分:"+this.getTeamScore(Color.YELLOW)+"红队得分:"+getTeamScore(Color.RED));
        }
        players.clear();
        start = false;
        iswait = true;
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Game){
            Game ga = (Game) obj;
            return ga.name.equals(this.name);
        }
        return false;
    }
    @Override
    public int hashCode(){
        return name.hashCode();
    }

    static class ByValueComparator implements Comparator<String> {
        HashMap<Color, Integer> base_map;

        public ByValueComparator(HashMap<Color, Integer> base_map) {
            this.base_map = base_map;
        }

        public int compare(String arg0, String arg1) {
            if (!base_map.containsKey(arg0) || !base_map.containsKey(arg1)) {
                return 0;
            }

            if (base_map.get(arg0) < base_map.get(arg1)) {
                return 1;
            } else if (base_map.get(arg0) == base_map.get(arg1)) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
