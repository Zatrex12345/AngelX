package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "Aim (Type N)")
public class AimN extends RotationCheck
{
    private double oldPitchChange;
    private double streak;

    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() >= 1000L) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        final float distanceBetweenAngles = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getPitch(), rotationUpdate.getFrom().getPitch());
        final float distanceBetweenAngles2 = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getYaw(), rotationUpdate.getFrom().getYaw());
        final double n = this.oldPitchChange - distanceBetweenAngles;
        if (distanceBetweenAngles2 >= 2.0f) {
            if (distanceBetweenAngles + n == 0.0 && distanceBetweenAngles < 0.07 && distanceBetweenAngles > 0.0f) {
                final double streak = this.streak;
                this.streak = streak + 1.0;
                if (streak > 3.0) {
                    if (this.streak >= 2.0) {
                        --this.streak;
                    }
                    this.onViolation("");
                }

            }
            else {
                this.streak = Math.max(0.0, this.streak - 0.01);
            }
        }
        this.oldPitchChange = distanceBetweenAngles;
    }
    
    public AimN(final PlayerData playerData) {
        super(playerData);
    }
    

}
