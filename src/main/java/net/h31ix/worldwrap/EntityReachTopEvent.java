package net.h31ix.worldwrap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public class EntityReachTopEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Entity e;
 
    public EntityReachTopEvent(Entity e) {
        this.e = e;
    }
 
    public Location getLocation() {
        return e.getLocation();
    }
    
    public Entity getEntity() {
        return e;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}