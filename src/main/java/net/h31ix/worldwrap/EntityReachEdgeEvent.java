package net.h31ix.worldwrap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public class EntityReachEdgeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Entity e;
    private int side;
 
    public EntityReachEdgeEvent(Entity e, int side) {
        this.e = e;
        this.side = side;
    }
 
    public Location getLocation() {
        return e.getLocation();
    }
    
    public Entity getEntity() {
        return e;
    }
    
    public int getSide() {
        return side;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}