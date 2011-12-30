package net.h31ix.worldwrap;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.config.Configuration;

public class WorldwrapPlayerListener extends PlayerListener {
    private Configuration config;
    private final Worldwrap plugin;
    
    public WorldwrapPlayerListener(Worldwrap plugin) {
    this.plugin = plugin;
    }
    
    @Override
    public void onPlayerMove (PlayerMoveEvent event)
    {  
        config = plugin.getConfiguration();
        Player player = event.getPlayer();
        
        if (config.getString("Teleportation Options.Teleport on y").equalsIgnoreCase("true"))
        {
        int height=player.getLocation().getBlockY();
        int allowedheight = Integer.parseInt(config.getString("Y Teleportation.Bottom of the world"));
        if (height<allowedheight)
            {
            if (config.getString("General Settings.Teleport to spawn").equalsIgnoreCase("true"))
            {
                Location loc = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world")).getSpawnLocation();
                player.teleport(loc);
            }
            else if (config.getString("General Settings.Teleport to coordinates").equalsIgnoreCase("true"))
            {
                int x = Integer.parseInt(config.getString("Teleport to coordinate settings.X teleport coordinate"));
                int y = Integer.parseInt(config.getString("Teleport to coordinate settings.Y teleport coordinate"));
                int z = Integer.parseInt(config.getString("Teleport to coordinate settings.Z teleport coordinate"));
                World world = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world"));
                Location loc = new Location(world, x, y, z);
                player.teleport(loc);
            }
            else
            {
            Random randomGenerator = new Random();
            int x = randomGenerator.nextInt(Integer.parseInt(config.getString("Y Teleportation.Randomness of drop")));
            int z = randomGenerator.nextInt(Integer.parseInt(config.getString("Y Teleportation.Randomness of drop")));
            World world = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world"));
            int op1 = randomGenerator.nextInt(4);
            if (op1 == 1)
                x = x - (x*2);
            if (op1 == 2)
                z = z - (z*2);  
            Location t = new Location (world, player.getLocation().getX(), 128, player.getLocation().getZ());
            Location f = t.add(x, 0, z);
            player.teleport(f);
            }
            if (config.getString("Message Settings.Send Drop Message").equalsIgnoreCase("true"))
            {
                player.sendMessage(ChatColor.BLUE+config.getString("Message Settings.Drop Message"));
            }            
            }
        }
        
        if (config.getString("Teleportation Options.Teleport on x/y").equalsIgnoreCase("true"))
        { 
            boolean tp = false;
            World world = player.getWorld();                                 
                Location ploc = player.getLocation();
                int x = (int)ploc.getX();
                int z = (int)ploc.getZ();
                
                Location spawn = world.getSpawnLocation();
                int sx = (int)spawn.getX();
                int sz = (int)spawn.getZ();
                
                int radius = Integer.parseInt(config.getString("X/Y Teleportation.Radius of world"));
                Location b = new Location (world, 0, player.getLocation().getY(), 0);
                
                System.out.println("Players x: "+x);
                System.out.println("x Border: "+(sx+radius));
                System.out.println("x Border: "+(sx-radius));
 
               
                
                if (x == sx+radius)
                {
                    
            if (config.getString("General Settings.Teleport to coordinates").equalsIgnoreCase("true"))
            {
                int x1 = Integer.parseInt(config.getString("Teleport to coordinate settings.X teleport coordinate"));
                int y1 = Integer.parseInt(config.getString("Teleport to coordinate settings.Y teleport coordinate"));
                int z1 = Integer.parseInt(config.getString("Teleport to coordinate settings.Z teleport coordinate"));
                World w = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world"));
                Location loc = new Location(w, x1, y1, z1);
                player.teleport(loc);
            }
            
            else if (config.getString("General Settings.Teleport to spawn").equalsIgnoreCase("true"))
            {
                System.out.println("spawn");
                Location loc = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world")).getSpawnLocation();
                player.teleport(loc);
            }            
            else {
                    Location to = new Location(world, sx-radius-3, player.getLocation().getY(), z);
                    player.teleport(to);
                }
            tp = true;
             }
                if (x == sx-radius)
                {
                    
            if (config.getString("General Settings.Teleport to coordinates").equalsIgnoreCase("true"))
            {
                int x1 = Integer.parseInt(config.getString("Teleport to coordinate settings.X teleport coordinate"));
                int y1 = Integer.parseInt(config.getString("Teleport to coordinate settings.Y teleport coordinate"));
                int z1 = Integer.parseInt(config.getString("Teleport to coordinate settings.Z teleport coordinate"));
                World w = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world"));
                Location loc = new Location(w, x1, y1, z1);
                player.teleport(loc);
            }
            
            else if (config.getString("General Settings.Teleport to spawn").equalsIgnoreCase("true"))
            {
                System.out.println("spawn");
                Location loc = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world")).getSpawnLocation();
                player.teleport(loc);
            }    
            else {
                    Location to = new Location(world, sx+radius+3, player.getLocation().getY(), z);
                    player.teleport(to);                   
                }
            tp = true;
                }
                
                if (z == sz+radius)
                {
                    
            if (config.getString("General Settings.Teleport to coordinates").equalsIgnoreCase("true"))
            {
                int x1 = Integer.parseInt(config.getString("Teleport to coordinate settings.X teleport coordinate"));
                int y1 = Integer.parseInt(config.getString("Teleport to coordinate settings.Y teleport coordinate"));
                int z1 = Integer.parseInt(config.getString("Teleport to coordinate settings.Z teleport coordinate"));
                World w = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world"));
                Location loc = new Location(w, x1, y1, z1);
                player.teleport(loc);
            }
            
            else if (config.getString("General Settings.Teleport to spawn").equalsIgnoreCase("true"))
            {
                System.out.println("spawn");
                Location loc = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world")).getSpawnLocation();
                player.teleport(loc);
            }      
            else {
            
                    Location to = new Location(world, x, player.getLocation().getY(), sz-radius-3);
                    player.teleport(to);
            }
            tp = true;
                }
                if (z == sz-radius)
                {
                    
            if (config.getString("General Settings.Teleport to coordinates").equalsIgnoreCase("true"))
            {
                int x1 = Integer.parseInt(config.getString("Teleport to coordinate settings.X teleport coordinate"));
                int y1 = Integer.parseInt(config.getString("Teleport to coordinate settings.Y teleport coordinate"));
                int z1 = Integer.parseInt(config.getString("Teleport to coordinate settings.Z teleport coordinate"));
                World w = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world"));
                Location loc = new Location(w, x1, y1, z1);
                player.teleport(loc);
            }
            
            else if (config.getString("General Settings.Teleport to spawn").equalsIgnoreCase("true"))
            {
                System.out.println("spawn");
                Location loc = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world")).getSpawnLocation();
                player.teleport(loc);
            } 
            else {
            
                    Location to = new Location(world, x, player.getLocation().getY(), z+radius+3);
                    player.teleport(to);   
            }
            tp = true;
                }               
            if (config.getString("Message Settings.Send Edge Message").equalsIgnoreCase("true") && tp == true)
            {
                player.sendMessage(ChatColor.BLUE+config.getString("Message Settings.Edge Message"));
                tp = false;
            }            
        }       
    }
}