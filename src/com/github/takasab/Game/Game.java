package com.github.takasab.Game;

import com.github.takasab.GameProcess.Request;
import com.github.takasab.GameProcess.StartThread;
import com.github.takasab.Main;
import com.github.takasab.Request.CarryRequst;
import com.github.takasab.Setting.Setting;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.Navigation;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

    Location sheepblue;
    Location sheepgreen;
    Location sheepyellow;
    Location sheepred;
    HashMap<Color,Integer> map = new HashMap<Color, Integer>();
    List<Player> players = new ArrayList<Player>();
    HashMap<Player,String> job = new HashMap<Player, String>();

    Game(String name,int min,int max,
    Location blue,Location green,Location yellow,Location red,Location sheep
    ,Location lobby,Location sheepblue,Location sheepgreen,Location sheepred,Location sheepyellow)
    { this.name=name;this.max=max;this.min=min;
    this.blue=blue;this.green=green;this.yellow=yellow;this.red=red;
        this.spawn=sheep; this.lobby = lobby;
    this.sheepgreen=sheepblue;this.sheepgreen=sheepgreen;this.sheepyellow=sheepyellow;this.sheepred=sheepred;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.handle, new Runnable(){
        @Override
        public void run(){
           if(Game.this.start==false) return;
           Sheep le =  (Sheep) Game.this.spawn.getWorld().spawnCreature(spawn, EntityType.SHEEP);
           Random rand = new Random();
           if(rand.nextInt(100)>80) le.setColor(DyeColor.BLACK);
        }
        }, 15*20, 15*20);
        
        //小羊回家
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.handle, new Runnable(){
            @Override
            public void run() {
                if (Game.this.start == false) {
                    return;
                }
                System.out.print("小羊颜色检测0");
                List<LivingEntity> l1 = spawn.getWorld().getLivingEntities();
                LivingEntity le = null;
                for (int i = 0; i < l1.size(); i++) {
                    System.out.print("小羊颜色检测1");
                    le = l1.get(i);
                    if (le instanceof Sheep) {
                        Sheep sheep = (Sheep) le;
                        if (sheep.getColor().equals(DyeColor.WHITE)) {
                            continue;
                        }
                        System.out.print("小羊颜色检测2");
                        if (Game.this.getTeamSpawn(ColorTool.getByID(ColorTool.getDyeToColorID(sheep.getColor()))
                        ) == null) {
                            continue;
                        }
                        Location loc = Game.this.getSheepSpawn(ColorTool.getByID(ColorTool.getDyeToColorID(sheep.getColor())));
                        CraftLivingEntity l = (CraftLivingEntity) le;
                        EntityInsentient en = (EntityInsentient) l.getHandle();
                        Navigation na = en.getNavigation();
                        na.a(loc.getX(), loc.getY(), loc.getZ(), 0.9);
                        System.out.print("小羊回家监听器" + loc.getBlockX() + loc.getBlockZ());
                    }

                }
            }
        }, 10, 10);

        //小羊清理
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.handle, new Runnable(){
            @Override
            public void run() {
                if (Game.this.start) {
                    return;
                }
                System.out.print("小羊清理");
                List<LivingEntity> l1 = spawn.getWorld().getLivingEntities();
                LivingEntity le = null;
                for (int i = 0; i < l1.size(); i++) {
                    le = l1.get(i);
                    if (le instanceof Sheep) {
                        le.remove();
                    }
                }
            }
        }, 200, 200);
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

    public Location getSheepSpawn(Color c){
        if(c.equals(Color.BLUE)) return sheepblue;
        if(c.equals(Color.RED)) return sheepred;
        if(c.equals(Color.YELLOW)) return sheepyellow;
        if(c.equals(Color.GREEN)) return sheepgreen;
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
                     ItemStack saddle = new  ItemStack(Material.SADDLE);
            ItemMeta me = saddle.getItemMeta();
            me.setDisplayName(ChatColor.RED+"神奇的马鞍");
            saddle.setItemMeta(me);
            p.getInventory().setItem(1, saddle);
            p.updateInventory();
                continue;
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
            
            ItemStack saddle = new  ItemStack(Material.SADDLE);
            ItemMeta me = saddle.getItemMeta();
            me.setDisplayName(ChatColor.RED+"神奇的马鞍");
            saddle.setItemMeta(me);
            p.getInventory().setItem(1, saddle);
            p.updateInventory();
            
        }
        //结束线程
        new  Thread(){
            public void run(){
                try {
                    sleep(Setting.WAITTIME* 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }{
                    Game.this.broadcast("游戏结束");

                    for(Player p:getPlayers()){

                        p.sendMessage(ChatColor.BLUE+"========"+ChatColor.RED+"游戏结束"+ChatColor.BLUE+"========");
                        p.sendMessage(ChatColor.BLUE+"本次获得奖励:"+ChatColor.GREEN+(new User(p)).getScore());
                        p.sendMessage(ChatColor.BLUE+"本队得分:"+ChatColor.RED+getTeamScore(new User(p).getColor()));
                        p.sendMessage("蓝队得分:"+Game.this.getTeamScore(Color.BLUE)+"绿队得分:"+getTeamScore(Color.GREEN));
                        p.sendMessage("黄队得分:"+Game.this.getTeamScore(Color.YELLOW)+"红队得分:"+getTeamScore(Color.RED));
                    }


                    Game.this.stop();
                }
            }
        }.start();
        //小羊集合线程

        //小羊生成线程


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
            player.getInventory().clear();
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
        player.getInventory().clear();
        //正在写
    }
    public void stop(){
        for(Player p:getPlayers()){
            left(p);
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
