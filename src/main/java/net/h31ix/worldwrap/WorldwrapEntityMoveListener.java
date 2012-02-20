package net.h31ix.worldwrap;

import org.bukkit.entity.Entity;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
}