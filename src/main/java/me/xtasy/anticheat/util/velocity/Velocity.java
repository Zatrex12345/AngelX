package me.xtasy.anticheat.util.velocity;

public class Velocity
{
    private final double vertical;
    private final long creationTime;
    private final double horizontal;
    
    public double getHorizontal() {
        return this.horizontal;
    }
    
    public double getVertical() {
        return this.vertical;
    }
    
    public Velocity(final double horizontal, final double vertical) {
        this.creationTime = System.currentTimeMillis();
        this.horizontal = horizontal;
        this.vertical = vertical;
    }
    
    public long getCreationTime() {
        return this.creationTime;
    }
}
