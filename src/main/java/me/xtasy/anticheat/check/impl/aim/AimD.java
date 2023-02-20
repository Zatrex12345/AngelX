package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "Aim (Type D)")
public class AimD extends RotationCheck
{
    private double vl;
    private double lastPitchChange;

    

    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 1500L) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        final float distanceBetweenAngles = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getPitch(), rotationUpdate.getFrom().getPitch());
        final float distanceBetweenAngles2 = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getYaw(), rotationUpdate.getFrom().getYaw());
        final double n = this.lastPitchChange - distanceBetweenAngles;
        if (distanceBetweenAngles2 > 2.0f) {
            if (distanceBetweenAngles != 0.0f && n == 0.0) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 15.0) {
                    this.onViolation("Invalid aim behaviour");

                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 1.0);
            }
        }
        this.lastPitchChange = distanceBetweenAngles;
    }
    
    public AimD(final PlayerData playerData) {
        super(playerData);
    }
}
