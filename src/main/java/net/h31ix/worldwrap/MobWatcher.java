package net.h31ix.worldwrap;

import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

public class MobWatcher implements Runnable {

	public Map<Entity,Location> mobloc = null;
        Thread thread = new Thread(this);
        public FileConfiguration config;
        public Worldwrap plugin;
        List<Entity> l;

        public MobWatcher(Map<Entity, Location> mobloc, Worldwrap plugin, List<Entity> l) {
		this.mobloc = mobloc;
                this.plugin = plugin;
                this.l = l;
        }
        
        public void Start() {
        thread.start();
        }     

	public void run() {      
        List<World> worlds = plugin.getServer().getWorlds();
        config = plugin.getConfig();
        while(true)
        {
            for (int i=0;i!=worlds.size();i++)
            {
                for (int o =0;o!=l.size();o++)
                {
                    Entity e = l.get(o);
                    if (e instanceof Animals || e instanceof Creature || e instanceof Monster && e != null)
                    {
                        if(e.getLocation() != mobloc.get(e))
                        {
                            if (config.getBoolean(e.getWorld().getName()+".Teleportation Options.Sync Bottom") == true)
                            {
                                int height=e.getLocation().getBlockY();
                                int allowedheight = Integer.parseInt(config.getString(e.getWorld().getName()+".Bottom Settings.Bottom of the world"));
                                if (height<=allowedheight)
                                {                            
                                    Bukkit.getServer().getPluginManager().callEvent(new EntityReachBottomEvent(e));
                                    mobloc.put(e, e.getLocation());
                                }
                            }
                            if (config.getBoolean(e.getWorld().getName()+".Teleportation Options.Sync Top") == true)
                            {
                                int height=e.getLocation().getBlockY();
                                int allowedheight = config.getInt(e.getWorld().getName()+".Top Settings.Top of the world");
                                    if (height>=allowedheight)
                                    {   
                                        Bukkit.getServer().getPluginManager().callEvent(new EntityReachTopEvent(e));
                                        mobloc.put(e, e.getLocation());                                        
                                    }  
                            }
                            if (config.getBoolean(e.getWorld().getName()+".Teleportation Options.Sync Edge") == true)
                            { 
                                    World world = e.getWorld();
                                    int x = (int)e.getLocation().getX();
                                    int z = (int)e.getLocation().getZ();

                                    int sx = (int)e.getWorld().getSpawnLocation().getX();
                                    int sz = (int)e.getWorld().getSpawnLocation().getX();

                                    int radius = config.getInt(world.getName()+".Edge Settings.Radius of world");
                                    if (x >= sx+radius)
                                    {  
                                        Bukkit.getServer().getPluginManager().callEvent(new EntityReachEdgeEvent(e,1));
                                        mobloc.put(e, e.getLocation());                                                   
                                    }
                                    else if (x <= sx-radius)
                                    {
                                        Bukkit.getServer().getPluginManager().callEvent(new EntityReachEdgeEvent(e,2));
                                        mobloc.put(e, e.getLocation());                                            
                                    }
                                    else if (z >= sz+radius)
                                    {
                                        Bukkit.getServer().getPluginManager().callEvent(new EntityReachEdgeEvent(e,3));
                                        mobloc.put(l.get(o), l.get(o).getLocation());                                            
                                    }      
                                    else if (z <= sz-radius)
                                    {
                                        Bukkit.getServer().getPluginManager().callEvent(new EntityReachEdgeEvent(e,4));
                                        mobloc.put(e, e.getLocation());                                           
                                    }
                             }                         
                        }
                    }
                }
            }            
        }
    }
}