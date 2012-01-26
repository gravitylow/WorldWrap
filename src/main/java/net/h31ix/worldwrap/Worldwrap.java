package net.h31ix.worldwrap;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Worldwrap extends JavaPlugin {
    
    private FileConfiguration config;           
    File configFile = new File("plugins/WorldWrap/config.yml");
    public static Worldwrap plugin;
    public boolean updated = true;
    
    public void onDisable() {
        System.out.println(this + " is now disabled!");
    }

    public void onEnable() {
    config = this.getConfig();
    getServer().getPluginManager().registerEvents(new WorldwrapPlayerListener(this), this);
    new File("plugins/NAS").mkdir();
		if(!configFile.exists()) {
                    saveDefaultConfig();
                }
                if (config.get("General Settings.Teleport to coordinates") != null)
                {
                    System.out.println("Your config file is outdated. Renaming and replacing it.");
                    File f = new File("plugins/WorldWrap/config_old.yml");
                    if (f.exists())
                    {
                        f.delete();
                    }
                    configFile.renameTo(new File("plugins/WorldWrap/config_old.yml"));
                    this.saveDefaultConfig();
                }
        if(!isUpdated())     
        {
            updated = false;
            Logger.getLogger(Worldwrap.class.getName()).log(Level.WARNING, "WORLD WRAP IS OUT OF DATE.");
            Logger.getLogger(Worldwrap.class.getName()).log(Level.WARNING, "PLEASE UPDATE BY DOWNLOADING THE LATEST VERSION OF WORLD WRAP");
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
    
public boolean isUpdated()
{
       URL url = null;
       URLConnection urlConn = null;
       InputStreamReader  inStream = null;
       BufferedReader buff = null;  
       String v = "";
        try {
            url  = new URL("http://dl.dropbox.com/u/38228324/wwversion.txt");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Worldwrap.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            urlConn = url.openConnection();
            inStream = new InputStreamReader(urlConn.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Worldwrap.class.getName()).log(Level.SEVERE, null, ex);
        }
        buff= new BufferedReader(inStream);
        try {
          v =buff.readLine(); 
        } catch (IOException ex) {
            Logger.getLogger(Worldwrap.class.getName()).log(Level.SEVERE, null, ex);
        }
        double v1 = Double.parseDouble(v);
        double v2 = Double.parseDouble(this.toString().split("v")[1]);
        urlConn = null;
        inStream = null;
        try {
            buff.close();
        } catch (IOException ex) {
            Logger.getLogger(Worldwrap.class.getName()).log(Level.SEVERE, null, ex);
        }
        buff = null;
        if (v1 > v2)
        {
            return false;
        }
        else
        {
            return true;
        }
    }    
}    
