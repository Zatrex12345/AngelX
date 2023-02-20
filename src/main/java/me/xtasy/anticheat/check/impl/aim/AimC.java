package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;

@CheckData(checkName = "Aim (Type C)")
public class AimC extends RotationCheck {
    private double vl;
    private double oldPitchChange;

    public AimC(final PlayerData playerData) {
        super(playerData);
    }

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
        final double n = this.oldPitchChange - distanceBetweenAngles;
        if (distanceBetweenAngles2 >= 2.0f) {
            if (distanceBetweenAngles + n == 0.0 && distanceBetweenAngles < 0.07 && distanceBetweenAngles > 0.0f) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 2.0) {
                    this.onViolation("Invalid mouse movements");

                }
            } else {
                this.vl = Math.max(0.0, this.vl - 0.5);
            }
        }
        this.oldPitchChange = distanceBetweenAngles;
    }

}
