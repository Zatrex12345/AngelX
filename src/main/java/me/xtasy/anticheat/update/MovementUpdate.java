package me.xtasy.anticheat.update;

import org.bukkit.*;

public class MovementUpdate
{
    private final Location from;
    private final Location to;
    
    public Location getTo() {
        return this.to;
    }
    
    public MovementUpdate(final Location to, final Location from) {
        this.to = to;
        this.from = from;
    }
    
    public Location getFrom() {
        return this.from;
    }
}
