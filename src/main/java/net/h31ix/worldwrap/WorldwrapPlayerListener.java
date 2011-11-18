package net.h31ix.worldwrap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
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
        int height=player.getLocation().getBlockY();
        String configheight = config.getString("Teleport Settings.Teleport Height");
        int allowedheight = Integer.parseInt(configheight);
        if (height < allowedheight)
        {
            String randomdrop = config.getString("Teleport Settings.Randomness of drop");
            int randomness = Integer.parseInt(randomdrop);
            String dropheight = config.getString("Teleport Settings.Drop height");
            String worldname = config.getString("Teleport Settings.Drop world name");
            int drop = Integer.parseInt(dropheight);  
            String messageboolean = config.getString("Message Settings.Send message");
            String message = config.getString("Message Settings.Message");
            World world = plugin.getServer().getWorld(worldname);
            Location spawn1 = world.getSpawnLocation();
            Location addspawn = new Location(world,0,10,0);
            Location spawn = spawn1.add(addspawn);
            double random = Math.random()*(randomness);
            int randomnum = (int)(random);
            final double x = player.getLocation().getBlockX()+randomnum;
            final double y = drop+0.0;
            final double z = player.getLocation().getBlockZ()+randomnum;
            String spawnboolean = config.getString("Teleport Settings.Teleport to spawn");
            if (spawnboolean.equalsIgnoreCase("true"))
            {
                player.setFallDistance(0);
                player.teleport(spawn);
            if (messageboolean.equalsIgnoreCase("true"))
                player.sendMessage(message);                
            }
            else if (spawnboolean.equalsIgnoreCase("false"))
            {
            Location teleport = new Location(world,x,y,z);
            player.teleport(teleport);
            if (messageboolean.equalsIgnoreCase("true"))
                player.sendMessage(message);
            }
        }
    }
}