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
        // TODO: Place any custom disable code here.
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
        config.setProperty("Teleport Settings.Teleport Height", "-5");
        config.setProperty("Teleport Settings.Randomness of drop", "20");
        config.setProperty("Teleport Settings.Drop height", "128");
        config.setProperty("Teleport Settings.Drop world name", "world");
        config.setProperty("Teleport Settings.Teleport to spawn", "false");
        config.setProperty("Message Settings.Send message", "false");
        config.setProperty("Message Settings.Message", "Woosh!");
        config.save(); 		
	}
    }    
}
