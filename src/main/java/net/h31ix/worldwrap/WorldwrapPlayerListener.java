package net.h31ix.worldwrap;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
        
        if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".Teleportation Options.Sync Bottom")) == true)
        {
        int height=player.getLocation().getBlockY();
        int allowedheight = Integer.parseInt(config.getString(player.getWorld().getName()+".Bottom Settings.Bottom of the world"));
        if (height<=allowedheight)
            {
            if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".General Settings.Teleport to spawn")) == true)
            {
                Location loc = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world")).getSpawnLocation();
                player.teleport(loc);
            }
            else if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".General Settings.Teleport to coordinates")) == true)
            {
                int x = Integer.parseInt(config.getString(player.getWorld().getName()+".Teleport to coordinate settings.X teleport coordinate"));
                int y = Integer.parseInt(config.getString(player.getWorld().getName()+".Teleport to coordinate settings.Y teleport coordinate"));
                int z = Integer.parseInt(config.getString(player.getWorld().getName()+".Teleport to coordinate settings.Z teleport coordinate"));
                World world = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".General Settings.Teleport to world"));
                Location loc = new Location(world, x, y, z);
                player.teleport(loc);
            }
            else
            {
            Random randomGenerator = new Random();
            int x = randomGenerator.nextInt(Integer.parseInt(config.getString(player.getWorld().getName()+".Bottom Settings.Randomness of teleport")));
            int z = randomGenerator.nextInt(Integer.parseInt(config.getString(player.getWorld().getName()+".Bottom Settings.Randomness of teleport")));
            World world = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".General Settings.Teleport to world"));
            int op1 = randomGenerator.nextInt(4);
            if (op1 == 1)
                x = x - (x*2);
            if (op1 == 2)
                z = z - (z*2);  
            Location t = new Location (world, player.getLocation().getX(), Integer.parseInt(config.getString(player.getWorld().getName()+".Bottom Settings.Teleport height")), player.getLocation().getZ());
            Location f = t.add(x, 0, z);
            player.teleport(f);         
            if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".Bottom Settings.Place onto glass")) == true)
            {
                final Location d = player.getLocation().add(0, -2, 0);
                d.getBlock().setType(Material.GLASS);
                if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".Bottom Settings.Delete glass after 30 seconds")) == true)
                {
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                            if (d.getBlock().getType() == Material.GLASS)
                            {
                            d.getBlock().setType(Material.AIR);
                            }
                        }
                    }, 600L);                    
                }
            }            
            }
            if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".Message Settings.Send Bottom Message")) == true)
            {
                player.sendMessage(ChatColor.BLUE+config.getString(player.getWorld().getName()+".Message Settings.Bottom Message"));
            }            
            }
        } 
        
        if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".Teleportation Options.Sync Top")) == true)
        {
        int height=player.getLocation().getBlockY();
        int allowedheight = Integer.parseInt(config.getString(player.getWorld().getName()+".Top Settings.Top of the world"));
        if (height>=allowedheight)
            {
            if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".General Settings.Teleport to spawn")) == true)
            {
                Location loc = plugin.getServer().getWorld(config.getString("General Settings.Teleport to world")).getSpawnLocation();
                player.teleport(loc);
            }
            else if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".General Settings.Teleport to coordinates")) == true)
            {
                int x = Integer.parseInt(config.getString(player.getWorld().getName()+".Teleport to coordinate settings.X teleport coordinate"));
                int y = Integer.parseInt(config.getString(player.getWorld().getName()+".Teleport to coordinate settings.Y teleport coordinate"));
                int z = Integer.parseInt(config.getString(player.getWorld().getName()+".Teleport to coordinate settings.Z teleport coordinate"));
                World world = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".General Settings.Teleport to world"));
                Location loc = new Location(world, x, y, z);
                player.teleport(loc);
            }
            else
            {
            Random randomGenerator = new Random();
            int x = randomGenerator.nextInt(Integer.parseInt(config.getString(player.getWorld().getName()+".Top Settings.Randomness of teleport")));
            int z = randomGenerator.nextInt(Integer.parseInt(config.getString(player.getWorld().getName()+".Bottom Settings.Randomness of teleport")));
            World world = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".General Settings.Teleport to world"));
            int op1 = randomGenerator.nextInt(4);
            if (op1 == 1)
                x = x - (x*2);
            if (op1 == 2)
                z = z - (z*2);  
            Location t = new Location (world, player.getLocation().getX(), Integer.parseInt(config.getString(player.getWorld().getName()+".Top Settings.Teleport Depth")), player.getLocation().getZ());
            Location f = t.add(x, 0, z);
            f.getBlock().setType(Material.AIR);
            Block b = f.getBlock().getFace(BlockFace.UP);
            Block j = b.getFace(BlockFace.DOWN);
            b.setType(Material.AIR);
            b.getFace(BlockFace.EAST).setType(Material.AIR);
            b.getFace(BlockFace.NORTH).setType(Material.AIR);
            b.getFace(BlockFace.SOUTH).setType(Material.AIR);
            b.getFace(BlockFace.WEST).setType(Material.AIR);
            b.getFace(BlockFace.NORTH_EAST).setType(Material.TORCH);
            b.getFace(BlockFace.NORTH_WEST).setType(Material.AIR);
            b.getFace(BlockFace.SOUTH_EAST).setType(Material.AIR);
            b.getFace(BlockFace.SOUTH_WEST).setType(Material.AIR);
            j.setType(Material.AIR);
            j.getFace(BlockFace.EAST).setType(Material.AIR);
            j.getFace(BlockFace.NORTH).setType(Material.AIR);
            j.getFace(BlockFace.SOUTH).setType(Material.AIR);
            j.getFace(BlockFace.WEST).setType(Material.AIR);
            j.getFace(BlockFace.NORTH_EAST).setType(Material.AIR);
            j.getFace(BlockFace.NORTH_WEST).setType(Material.AIR);
            j.getFace(BlockFace.SOUTH_EAST).setType(Material.AIR);
            j.getFace(BlockFace.SOUTH_WEST).setType(Material.AIR);            
            
            player.teleport(f);
            }
            if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".Message Settings.Send Top Message")) == true)
            {
                player.sendMessage(ChatColor.BLUE+config.getString(player.getWorld().getName()+".Message Settings.Top Message"));
            }            
            }
        }        
        
        if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".Teleportation Options.Sync Edge")) == true)
        { 
                boolean tp = false;
                World world = player.getWorld();
                int x = (int)player.getLocation().getX();
                int y = (int)player.getLocation().getY();
                int z = (int)player.getLocation().getZ();
                
                float yaw = player.getLocation().getYaw();
                float pitch = player.getLocation().getPitch();
                
                int sx = (int)player.getWorld().getSpawnLocation().getX();
                int sz = (int)player.getWorld().getSpawnLocation().getX();
                
                int radius = Integer.parseInt(config.getString(player.getWorld().getName()+".Edge Teleportation.Radius of world"));
                if (x >= sx+radius)
                {
                    Location l = new Location(world, sx-radius+3, plugin.findTop(world,sx-radius+3,z), z, yaw, pitch);
                    /**world.getBlockAt(l).setType(Material.AIR);
                    world.getBlockAt(l).getFace(BlockFace.UP).setType(Material.AIR);**/
                    player.teleport(l);
                    tp = true;
                }
                
                else if (x <= sx-radius)
                {
                    Location l = new Location(world, sx+radius-3, plugin.findTop(world,sx+radius-3, z), z, yaw, pitch);
                    //world.getBlockAt(l).setType(Material.AIR);
                    //world.getBlockAt(l).getFace(BlockFace.UP).setType(Material.AIR); 
                    player.teleport(l);                    
                    tp = true;
                }
                
                else if (z >= sz+radius)
                {
                    Location l = new Location(world, x, plugin.findTop(world,x, sz-radius+3), sz-radius+3, yaw, pitch);
                    //world.getBlockAt(l).setType(Material.AIR);
                    //world.getBlockAt(l).getFace(BlockFace.UP).setType(Material.AIR);
                    player.teleport(l);
                    tp = true;
                }
                
                else if (z <= sz-radius)
                {
                    Location l = new Location(world, x, plugin.findTop(world,x, sz+radius-3), sz+radius-3, yaw, pitch);
                    //world.getBlockAt(l).setType(Material.AIR);
                    //world.getBlockAt(l).getFace(BlockFace.UP).setType(Material.AIR);
                    player.teleport(l);
                    tp = true;
                }
                
                if (tp == true)
                {
                    if (Boolean.parseBoolean(config.getString(player.getWorld().getName()+".Message Settings.Send Edge Message")) == true)
                    {
                        player.sendMessage(ChatColor.BLUE+config.getString(player.getWorld().getName()+".Message Settings.Edge Message"));
                    }    
                    tp = false;
                }
        }       
    }
}