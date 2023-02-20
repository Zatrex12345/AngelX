package me.xtasy.anticheat.check.impl.velocity;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "VelocityA (Type A)")
public class VelocityA extends PositionCheck
{
    private double ticks;
    private int vl;
    private int toFlag;

    @Override
    public void run(final PositionUpdate positionUpdate) {
        final Location to = positionUpdate.getTo();
        final Location from = positionUpdate.getFrom();
        final double maxVertical = this.player.getVelocityManager().getMaxVertical();
        final double ticks = to.getY() - from.getY();
        if (to.getX() - from.getX() * to.getX() - from.getX() + (to.getZ() - from.getZ() * to.getZ() - from.getZ()) < 0.1) {
            final int n = 0;
            this.toFlag = n;
            this.vl = n;
            return;
        }
        if (ticks > 0.0) {
            if (this.ticks == -1.0) {
                this.ticks = ticks;

            }
        }
        else {
            this.ticks = -1.0;
        }
        if (this.player.isOnGround() && maxVertical > 0.0 && ticks > 0.0 && this.ticks != -1.0) {
            final double n2 = (maxVertical - this.ticks) * 25000.0;
            if (n2 > 100.0) {
                if (++this.vl > 3) {
                    ++this.toFlag;
                    this.vl = 0;
                }
                if (this.toFlag > 4) {
                    this.onViolation("Difference: " + n2 + " " + this.toFlag);
                    this.toFlag = 0;

                }
            }
            else {
                this.vl = 0;
            }
        }
    }
    

    
    public VelocityA(final PlayerData playerData) {
        super(playerData);
        this.ticks = -1.0;
    }
}
