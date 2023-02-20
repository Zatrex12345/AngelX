package me.xtasy.anticheat.util.location;

import org.bukkit.*;

public class CustomLocation
{
    private double x;
    private String world;
    private final long timestamp;
    private float pitch;
    private float yaw;
    private double y;
    private double z;

    
    public double getX() {
        return this.x;
    }
    
    public CustomLocation(final String world, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.timestamp = System.currentTimeMillis();
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public double getY() {
        return this.y;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof CustomLocation)) {
            return false;
        }
        final CustomLocation customLocation = (CustomLocation)o;
        boolean b;
        if (customLocation.x == this.x && customLocation.y == this.y && customLocation.z == this.z && customLocation.pitch == this.pitch && customLocation.yaw == this.yaw) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public World toBukkitWorld() {
        World world;
        if (this.world == null) {
            world = Bukkit.getServer().getWorlds().get(0);

        }
        else {
            world = Bukkit.getServer().getWorld(this.world);
        }
        return world;
    }
    
    public void setWorld(final String world) {
        this.world = world;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public String getWorld() {
        return this.world;
    }
    
    public CustomLocation(final double n, final double n2, final double n3, final float n4, final float n5) {
        this("world", n, n2, n3, n4, n5);
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
}
