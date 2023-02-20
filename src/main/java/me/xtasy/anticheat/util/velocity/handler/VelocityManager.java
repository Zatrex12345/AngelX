package me.xtasy.anticheat.util.velocity.handler;

import me.xtasy.anticheat.util.velocity.*;
import java.util.*;

public class VelocityManager
{
    private final List<Velocity> velocities;
    
    public void addVelocityEntry(final double n, final double n2, final double n3) {
        final List<Velocity> velocities = this.velocities;
        final double n4 = n * n + n3 * n3;
        double n5 = n2;
        if (n2 < 0.0) {
            n5 = -n2;
        }
        velocities.add(new Velocity(n4, n5));
    }
    
    public VelocityManager() {
        this.velocities = new ArrayList<Velocity>();
    }
    
    public List<Velocity> getVelocities() {
        return this.velocities;
    }
    
    public double getMaxVertical() {
        return this.velocities.stream().mapToDouble(Velocity::getVertical).max().orElse(0.0);
    }
    
    public double getMaxHorizontal() {
        try {
            return Math.sqrt(this.velocities.stream().mapToDouble(Velocity::getHorizontal).max().orElse(0.0));
        }
        catch (ConcurrentModificationException ex) {
            return 1.0;
        }
    }
}
