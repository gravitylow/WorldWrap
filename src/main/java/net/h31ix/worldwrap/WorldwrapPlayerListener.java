package net.h31ix.worldwrap;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldwrapPlayerListener implements Listener {
    private FileConfiguration config;
    private final Worldwrap plugin;
    
    public WorldwrapPlayerListener(Worldwrap plugin) {
    this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event)
    {
        if (plugin.updated == false && event.getPlayer().isOp())
        {
            event.getPlayer().sendMessage(ChatColor.GREEN+"Your version of World Wrap is out of date!");
        }
    }
    
    @EventHandler
    public void onPlayerMove (PlayerMoveEvent event)
    {  
        config = plugin.getConfig();
        Player player = event.getPlayer();
        
        if (config.getBoolean(player.getWorld().getName()+".Teleportation Options.Sync Bottom") == true)
        {
            String method = config.getString(player.getWorld().getName()+".Bottom Settings.Teleport method");
            int height=player.getLocation().getBlockY();
            int allowedheight = Integer.parseInt(config.getString(player.getWorld().getName()+".Bottom Settings.Bottom of the world"));
            if (height<=allowedheight)
            {
                if (method.equalsIgnoreCase("world"))
                {
                        Location loc;
                        World world = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".Bottom Settings.World name"));
                        if (config.getBoolean(player.getWorld().getName()+".Bottom Settings.Keep coordinates") == false)
                        {
                            loc = new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY()+3, world.getSpawnLocation().getZ());
                        }
                        else
                        {
                            loc = new Location(world, player.getLocation().getX(), plugin.findTop(world,player.getLocation().getX(),player.getLocation().getZ()), player.getLocation().getZ());   
                        }
                        player.teleport(loc);
                }
                else if (method.equalsIgnoreCase("spawn"))
                {
                    player.teleport(new Location(player.getWorld(), player.getWorld().getSpawnLocation().getX(), player.getWorld().getSpawnLocation().getY()+3, player.getWorld().getSpawnLocation().getZ()));
                }
                else if (method.equalsIgnoreCase("sky"))
                {
                    World world = player.getWorld();
                    Location t = new Location (world, player.getLocation().getX(), config.getInt(world.getName()+".Bottom Settings.Teleport height"), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                    if (config.getInt(world.getName()+".Bottom Settings.Randomness of teleport") != 0)
                    {
                    Random randomGenerator = new Random();
                    int x = randomGenerator.nextInt(config.getInt(world.getName()+".Bottom Settings.Randomness of teleport"));
                    int z = randomGenerator.nextInt(config.getInt(world.getName()+".Bottom Settings.Randomness of teleport"));
                    int op1 = randomGenerator.nextInt(4);
                    if (op1 == 1)
                        x = x - (x*2);
                    if (op1 == 2)
                        z = z - (z*2);  
                    t.add(x, 0, z);
                    }
                    player.teleport(t);         
                    if (config.getBoolean(player.getWorld().getName()+".Bottom Settings.Place onto glass") == true)
                    {
                        final Location d = player.getLocation().add(0, -2, 0);
                        d.getBlock().setType(Material.GLASS);
                        if (config.getBoolean(player.getWorld().getName()+".Bottom Settings.Delete glass after 30 seconds") == true)
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
            if (config.getBoolean(player.getWorld().getName()+".Message Settings.Send Bottom Message") == true)
            {
                player.sendMessage(ChatColor.BLUE+config.getString(player.getWorld().getName()+".Message Settings.Bottom Message"));
            }            
            }
        } 
        
        if (config.getBoolean(player.getWorld().getName()+".Teleportation Options.Sync Top") == true)
        {
            String method = config.getString(player.getWorld().getName()+".Top Settings.Teleport method");
            int height=player.getLocation().getBlockY();
            int allowedheight = config.getInt(player.getWorld().getName()+".Top Settings.Top of the world");
                if (height>=allowedheight)
                {
                    if (method.equalsIgnoreCase("world"))
                    {
                        World world = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".Top Settings.World name"));
                        Location loc;
                        if (config.getBoolean(player.getWorld().getName()+".Top Settings.Keep coordinates") == false)
                        {
                            loc = new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY()+3, world.getSpawnLocation().getZ());
                        }
                        else
                        {
                            loc = new Location(world, player.getLocation().getX(), plugin.findTop(world,player.getLocation().getX(),player.getLocation().getZ()), player.getLocation().getZ());   
                        }
                        player.teleport(loc);
                    }
                    else if (method.equalsIgnoreCase("spawn"))
                    {
                        player.teleport(new Location(player.getWorld(), player.getWorld().getSpawnLocation().getX(), player.getWorld().getSpawnLocation().getY()+3, player.getWorld().getSpawnLocation().getZ()));
                    }
                    else if (method.equalsIgnoreCase("ground"))
                    {
                    World world = player.getWorld();
                        Location t = new Location (world, player.getLocation().getX(), config.getInt(world.getName()+".Top Settings.Teleport depth"), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        if (config.getInt(world.getName()+".TopSettings.Randomness of teleport") != 0)
                        {
                        Random randomGenerator = new Random();
                        int x = randomGenerator.nextInt(config.getInt(world.getName()+".Top Settings.Randomness of teleport"));
                        int z = randomGenerator.nextInt(config.getInt(world.getName()+".Top Settings.Randomness of teleport"));
                        int op1 = randomGenerator.nextInt(4);
                        if (op1 == 1)
                            x = x - (x*2);
                        if (op1 == 2)
                            z = z - (z*2);  
                        t.add(x, 0, z);
                        }                            
                        t.getBlock().setType(Material.AIR);
                        Block b = t.getBlock().getFace(BlockFace.UP);
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

                        player.teleport(t);
                    }
                    if (config.getBoolean(player.getWorld().getName()+".Message Settings.Send Top Message") == true)
                    {
                        player.sendMessage(ChatColor.BLUE+config.getString(player.getWorld().getName()+".Message Settings.Top Message"));
                    }            
                }
        }        
        
        if (config.getBoolean(player.getWorld().getName()+".Teleportation Options.Sync Edge") == true)
        { 
                boolean tp = false;
                World world = player.getWorld();
                int x = (int)player.getLocation().getX();
                int z = (int)player.getLocation().getZ();
                
                float yaw = player.getLocation().getYaw();
                float pitch = player.getLocation().getPitch();
                
                int sx = (int)player.getWorld().getSpawnLocation().getX();
                int sz = (int)player.getWorld().getSpawnLocation().getX();
                
                int radius = config.getInt(player.getWorld().getName()+".Edge Settings.Radius of world");
                String method = config.getString(player.getWorld().getName()+".Edge Settings.Teleport method");
                if (x >= sx+radius)
                {
                    if (method.equalsIgnoreCase("world"))       
                    {
                        World w = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".Edge Settings.World name"));
                        Location loc;
                        if (config.getBoolean(player.getWorld().getName()+".Top Settings.Keep coordinates") == false)
                        {
                            loc = new Location(w, w.getSpawnLocation().getX(), w.getSpawnLocation().getY()+3, w.getSpawnLocation().getZ());
                        }
                        else
                        {
                            loc = new Location(w, player.getLocation().getX(), plugin.findTop(w,player.getLocation().getX(),player.getLocation().getZ()), player.getLocation().getZ());   
                        }                        
                        player.teleport(loc);                   
                    }
                    else if (method.equalsIgnoreCase("spawn"))
                    {
                        player.teleport(player.getWorld().getSpawnLocation());
                    }
                    else if (method.equalsIgnoreCase("normal"))
                    {
                    Location l = new Location(world, sx-radius+3, plugin.findTop(world,sx-radius+3,z), z, yaw, pitch);
                    player.teleport(l);
                    }
                    tp = true;
                }
                
                else if (x <= sx-radius)
                {
                    if (method.equalsIgnoreCase("world"))       
                    {
                        Location loc = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".Edge Settings.World name")).getSpawnLocation();
                        player.teleport(loc);                        
                    }
                    else if (method.equalsIgnoreCase("spawn"))
                    {
                        player.teleport(player.getWorld().getSpawnLocation());
                    }
                    else if (method.equalsIgnoreCase("normal"))
                    {                    
                        Location l = new Location(world, sx+radius-3, plugin.findTop(world,sx+radius-3, z), z, yaw, pitch);
                        player.teleport(l);      
                    }
                    tp = true;
                }
                
                else if (z >= sz+radius)
                {                   
                    if (method.equalsIgnoreCase("world"))       
                    {
                        Location loc = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".Edge Settings.World name")).getSpawnLocation();
                        player.teleport(loc);                        
                    }
                    else if (method.equalsIgnoreCase("spawn"))
                    {
                        player.teleport(player.getWorld().getSpawnLocation());
                    }
                    else if (method.equalsIgnoreCase("normal"))
                    {
                        Location l = new Location(world, x, plugin.findTop(world,x, sz-radius+3), sz-radius+3, yaw, pitch);
                        player.teleport(l);
                    }
                    tp = true;
                }
                
                else if (z <= sz-radius)
                {
                    if (method.equalsIgnoreCase("world"))       
                    {
                        Location loc = plugin.getServer().getWorld(config.getString(player.getWorld().getName()+".Edge Settings.World name")).getSpawnLocation();
                        player.teleport(loc);                        
                    }
                    else if (method.equalsIgnoreCase("spawn"))
                    {
                        player.teleport(player.getWorld().getSpawnLocation());
                    }
                    else if (method.equalsIgnoreCase("normal"))
                    {
                        Location l = new Location(world, x, plugin.findTop(world,x, sz+radius-3), sz+radius-3, yaw, pitch);
                        player.teleport(l);
                    }
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