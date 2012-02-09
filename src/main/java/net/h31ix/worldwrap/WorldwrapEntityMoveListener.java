package net.h31ix.worldwrap;

import org.bukkit.entity.Entity;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class WorldwrapEntityMoveListener implements Listener {
    private FileConfiguration config;
    private final Worldwrap plugin;
    
    public WorldwrapEntityMoveListener(Worldwrap plugin) {
    this.plugin = plugin;
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        config = plugin.getConfig();
        Entity e = event.getEntity();
        if (config.getBoolean("General Settings.Mobs follow rules") == true)
        {
            if (event.getCause() == DamageCause.VOID)
            {
            String method = config.getString(e.getWorld().getName()+".Bottom Settings.Teleport method");
            if (method.equalsIgnoreCase("world"))
            {
                    Location loc;
                    World world = e.getServer().getWorld(config.getString(e.getWorld().getName()+".Bottom Settings.World name"));
                    if (config.getBoolean(e.getWorld().getName()+".Bottom Settings.Keep coordinates") == false)
                    {
                        loc = new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY()+3, world.getSpawnLocation().getZ());
                    }
                    else
                    {
                        loc = new Location(world, e.getLocation().getX(), plugin.findTop(world,e.getLocation().getX(),e.getLocation().getZ()), e.getLocation().getZ());   
                    }
                    e.teleport(loc);
            }
            else if (method.equalsIgnoreCase("spawn"))
            {
                e.teleport(new Location(e.getWorld(), e.getWorld().getSpawnLocation().getX(), e.getWorld().getSpawnLocation().getY()+3, e.getWorld().getSpawnLocation().getZ()));
            }
            else if (method.equalsIgnoreCase("sky"))
            {
                World world = e.getWorld();
                Location t = new Location (world, e.getLocation().getX(), config.getInt(world.getName()+".Bottom Settings.Teleport height"), e.getLocation().getZ(), e.getLocation().getYaw(), e.getLocation().getPitch());
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
                e.teleport(t);         
                if (config.getBoolean(e.getWorld().getName()+".Bottom Settings.Place onto glass") == true)
                {
                    final Location d = e.getLocation().add(0, -2, 0);
                    d.getBlock().setType(Material.GLASS);
                    if (config.getBoolean(e.getWorld().getName()+".Bottom Settings.Delete glass after 30 seconds") == true)
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
            }
        }
    }
    
    @EventHandler
    public void onEntityReachEdge(EntityReachEdgeEvent event)
    {
        config = plugin.getConfig();
        Entity e = event.getEntity();
        int side = event.getSide();
        World world = e.getWorld();
        String method = config.getString(world.getName()+".Edge Settings.Teleport method");
        int radius = config.getInt(world.getName()+".Edge Settings.Radius of world");
        
        int x = (int)e.getLocation().getX();
        int z = (int)e.getLocation().getZ();
                
        float yaw = e.getLocation().getYaw();
        float pitch = e.getLocation().getPitch();
                
        int sx = (int)e.getWorld().getSpawnLocation().getX();
        int sz = (int)e.getWorld().getSpawnLocation().getX();
                
        if (side == 1)
        {
            if (method.equalsIgnoreCase("world"))       
            {
                World w = plugin.getServer().getWorld(config.getString(e.getWorld().getName()+".Edge Settings.World name"));
                Location loc;
                if (config.getBoolean(e.getWorld().getName()+".Top Settings.Keep coordinates") == false)
                {
                    loc = new Location(w, w.getSpawnLocation().getX(), w.getSpawnLocation().getY()+3, w.getSpawnLocation().getZ());
                }
                else
                {
                    loc = new Location(w, e.getLocation().getX(), plugin.findTop(w,e.getLocation().getX(),e.getLocation().getZ()), e.getLocation().getZ());   
                }                        
                e.teleport(loc);                   
            }
            else if (method.equalsIgnoreCase("spawn"))
            {
                e.teleport(e.getWorld().getSpawnLocation());
            }
            else if (method.equalsIgnoreCase("normal"))
            {
            Location l = new Location(world, sx-radius+3, plugin.findTop(world,sx-radius+3,z), z, yaw, pitch);
            e.teleport(l);
            }
        } 
        else if (side == 2)
        {
            if (method.equalsIgnoreCase("world"))       
            {
                Location loc = plugin.getServer().getWorld(config.getString(e.getWorld().getName()+".Edge Settings.World name")).getSpawnLocation();
                e.teleport(loc);                        
            }
            else if (method.equalsIgnoreCase("spawn"))
            {
                e.teleport(e.getWorld().getSpawnLocation());
            }
            else if (method.equalsIgnoreCase("normal"))
            {                    
                Location l = new Location(world, sx+radius-3, plugin.findTop(world,sx+radius-3, z), z, yaw, pitch);
                e.teleport(l);      
            }           
        }
        else if (side == 3)
        {                   
            if (method.equalsIgnoreCase("world"))       
            {
                Location loc = plugin.getServer().getWorld(config.getString(e.getWorld().getName()+".Edge Settings.World name")).getSpawnLocation();
                e.teleport(loc);                        
            }
            else if (method.equalsIgnoreCase("spawn"))
            {
                e.teleport(e.getWorld().getSpawnLocation());
            }
            else if (method.equalsIgnoreCase("normal"))
            {
                Location l = new Location(world, x, plugin.findTop(world,x, sz-radius+3), sz-radius+3, yaw, pitch);
                e.teleport(l);
            }
        }
        else if (side == 4)
        {
            if (method.equalsIgnoreCase("world"))       
            {
                Location loc = plugin.getServer().getWorld(config.getString(e.getWorld().getName()+".Edge Settings.World name")).getSpawnLocation();
                e.teleport(loc);                        
            }
            else if (method.equalsIgnoreCase("spawn"))
            {
                e.teleport(e.getWorld().getSpawnLocation());
            }
            else if (method.equalsIgnoreCase("normal"))
            {
                Location l = new Location(world, x, plugin.findTop(world,x, sz+radius-3), sz+radius-3, yaw, pitch);
                e.teleport(l);
            }
        }
    }
    
    @EventHandler
    public void onEntityReachTop(EntityReachTopEvent event)
    {
        config = plugin.getConfig();
        Entity e = event.getEntity();     
        String method = config.getString(e.getWorld().getName()+".Top Settings.Teleport method");
        if (method.equalsIgnoreCase("world"))
        {
            World world = plugin.getServer().getWorld(config.getString(e.getWorld().getName()+".Top Settings.World name"));
            Location loc;
            if (config.getBoolean(e.getWorld().getName()+".Top Settings.Keep coordinates") == false)
            {
                loc = new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY()+3, world.getSpawnLocation().getZ());
            }
            else
            {
                loc = new Location(world, e.getLocation().getX(), plugin.findTop(world,e.getLocation().getX(),e.getLocation().getZ()), e.getLocation().getZ());   
            }
            e.teleport(loc);
        }
        else if (method.equalsIgnoreCase("spawn"))
        {
            e.teleport(new Location(e.getWorld(), e.getWorld().getSpawnLocation().getX(), e.getWorld().getSpawnLocation().getY()+3, e.getWorld().getSpawnLocation().getZ()));
        }
        else if (method.equalsIgnoreCase("ground"))
        {
        World world = e.getWorld();
            Location t = new Location (world, e.getLocation().getX(), config.getInt(world.getName()+".Top Settings.Teleport depth"), e.getLocation().getZ(), e.getLocation().getYaw(), e.getLocation().getPitch());
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

            e.teleport(t);
        }             
    }
    
    @EventHandler
    public void onEntityReachBottom(EntityReachBottomEvent event) {
        config = plugin.getConfig();
        Entity e = event.getEntity();
        String method = config.getString(e.getWorld().getName()+".Bottom Settings.Teleport method");
        if (method.equalsIgnoreCase("world"))
        {
                Location loc;
                World world = plugin.getServer().getWorld(config.getString(e.getWorld().getName()+".Bottom Settings.World name"));
                if (config.getBoolean(e.getWorld().getName()+".Bottom Settings.Keep coordinates") == false)
                {
                    loc = new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY()+3, world.getSpawnLocation().getZ());
                }
                else
                {
                    loc = new Location(world, e.getLocation().getX(), plugin.findTop(world,e.getLocation().getX(),e.getLocation().getZ()), e.getLocation().getZ());   
                }
                e.teleport(loc);
        }
        else if (method.equalsIgnoreCase("spawn"))
        {
            e.teleport(new Location(e.getWorld(), e.getWorld().getSpawnLocation().getX(), e.getWorld().getSpawnLocation().getY()+3, e.getWorld().getSpawnLocation().getZ()));
        }
        else if (method.equalsIgnoreCase("sky"))
        {
            World world = e.getWorld();
            Location t = new Location (world, e.getLocation().getX(), config.getInt(world.getName()+".Bottom Settings.Teleport height"), e.getLocation().getZ(), e.getLocation().getYaw(), e.getLocation().getPitch());
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
            e.teleport(t);         
            if (config.getBoolean(e.getWorld().getName()+".Bottom Settings.Place onto glass") == true)
            {
                final Location d = e.getLocation().add(0, -2, 0);
                d.getBlock().setType(Material.GLASS);
                if (config.getBoolean(e.getWorld().getName()+".Bottom Settings.Delete glass after 30 seconds") == true)
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
    }
}