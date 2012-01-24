package net.h31ix.worldwrap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Worldwrap extends JavaPlugin {
    
    private Configuration config;
    private WorldwrapPlayerListener playerListener = new WorldwrapPlayerListener(this);               
    File configFile = new File("plugins/WorldWrap/config.yml");
    public static Worldwrap plugin;
    
    public void onDisable() {
        System.out.println(this + " is now disabled!");
    }

    public void onEnable() {
    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvent(org.bukkit.event.Event.Type.PLAYER_MOVE, playerListener, org.bukkit.event.Event.Priority.Low, this);   
    new File("plugins/NAS").mkdir();
		if(!configFile.exists()) {
		    makeConfig();
                }
            config = getConfiguration();
	    config.load(); 
                if (config.getProperty("General Settings.Teleport to spawn") != null)
                {
                    System.out.println("Your config file is outdated. Renaming and replacing it.");
                    configFile.renameTo(new File("plugins/WorldWrap/config_old.yml"));
                    makeConfig();
                    config.load();
                }
        System.out.println(this + " is now enabled!");
    }
    
    public double findTop(World world, double x, double z)
    {
        double y = 0;
        boolean found = false;
        for (int i=100;i!=60;i--)
        {
            if (found == false)
            {
                Block block = world.getBlockAt(new Location(world, x, i, z));
                if (block.getType() != Material.AIR)
                {
                    y = block.getLocation().getY();
                    found = true;
                }
            }
        }
        return y+2;
    } 
    
private void copy(InputStream in, File file) {
    try {
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while((len=in.read(buf))>0){
            out.write(buf,0,len);
        }
        out.close();
        in.close();
    } catch (Exception e) {
        System.out.println("[WorldWrap] Error generating a new config! Is the directory writeable?");
    }
}
    
public void makeConfig() {
    try {
        //Create a new blank config file
        copy(getResource("config.yml"), configFile);  
        System.out.println("[WorldWrap] Generated a new config file");
        //configFile.createNewFile();
        } catch(Exception a) {
            System.out.println("[WorldWrap] Error generating a new config! Is the directory writeable?");
        }
    config = getConfiguration(); 
    /**Set all the config defaults, if they are not already set.
    if (configFile.length()==0) {
        config.setHeader("#World Wrap configuration file \n #For each world you want to use with World Wrap, please copy the template below and replace \"world\" with the world's name \n #Please consult http://dev.bukkit.org/server-mods/worldwrap/ for questions");
        config.setProperty("world.Teleportation Options.Sync Bottom", true);
        config.setProperty("world.Teleportation Options.Sync Top", true);
        config.setProperty("world.Teleportation Options.Sync Edge", false);
        
        config.setProperty("world.Bottom Settings.Bottom of the world", -5);
        config.setProperty("world.Bottom Settings.Place onto glass", false);
        config.setProperty("world.Bottom Settings.Delete glass after 30 seconds", false);
        config.setProperty("world.Bottom Settings.Randomness of teleport", 20);  
        config.setProperty("world.Bottom Settings.Teleport height", 127);    
        
        config.setProperty("world.Top Settings.Top of the world", 126);
        config.setProperty("world.Top Settings.Teleport Depth", 5);
        config.setProperty("world.Top Settings.Randomness of teleport", 20); 
        
        config.setProperty("world.Edge Teleportation.Radius of world", 128);
        
        config.setProperty("world.General Settings.Teleport to spawn", false);
        config.setProperty("world.General Settings.Teleport to coordinates", false);  
        config.setProperty("world.General Settings.Teleport to world", "world");
        
        config.setProperty("world.Teleport to coordinate settings.X teleport coordinate", 0);   
        config.setProperty("world.Teleport to coordinate settings.Y teleport coordinate", 0); 
        config.setProperty("world.Teleport to coordinate settings.Z teleport coordinate", 0); 
        
        config.setProperty("world.Message Settings.Send Top Message", false);
        config.setProperty("world.Message Settings.Send Bottom Message", false);
        config.setProperty("world.Message Settings.Send Edge Message", false);    
        config.setProperty("world.Message Settings.Top Message", "Down you go!");
        config.setProperty("world.Message Settings.Bottom Message", "Woosh!");
        config.setProperty("world.Message Settings.Edge Message", "You have reached the edge of the world!");        
        config.save(); **/		
	}
    }    
