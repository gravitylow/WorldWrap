package net.h31ix.worldwrap;

import java.io.File;
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
        System.out.println(this + " is now enabled!");
    }
    
public void makeConfig() {
    try {
        //Create a new blank config file
        configFile.createNewFile();
        } catch(Exception a) {
            System.out.println("[wW] Generated a new config file");
        }
    config = getConfiguration(); 
    //Set all the config defaults, if they are not already set.
    if (configFile.length()==0) {
        config.setHeader("#World Wrap configuration file");
        config.setProperty("Teleportation Options.Teleport on y", "true");
        config.setProperty("Teleportation Options.Teleport on x/z", "false");
        config.setProperty("Y Teleportation.Bottom of the world", "-5");
        config.setProperty("Y Teleportation.Randomness of drop", "20");
        config.setProperty("Y Teleportation.Drop height", "128");
        config.setProperty("X/Y Teleportation.Radius of world", "128");
        config.setProperty("General Settings.Teleport to spawn", "false");
        config.setProperty("General Settings.Teleport to coordinates", "false");  
        config.setProperty("General Settings.Teleport to world", "world");
        config.setProperty("Teleport to coordinate settings.X teleport coordinate", "0");   
        config.setProperty("Teleport to coordinate settings.Y teleport coordinate", "0"); 
        config.setProperty("Teleport to coordinate settings.Z teleport coordinate", "0"); 
        config.setProperty("Message Settings.Send Drop Message", "false");
        config.setProperty("Message Settings.Send Edge Message", "false");        
        config.setProperty("Message Settings.Drop Message", "Woosh!");
        config.setProperty("Message Settings.Edge Message", "You have reached the edge of the world!");        
        config.save(); 		
	}
    }    
}
