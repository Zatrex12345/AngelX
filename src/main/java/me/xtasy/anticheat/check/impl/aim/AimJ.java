package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "Aim (Type J)")
public class AimJ extends RotationCheck
{
    private double streak;

    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 1000L || this.player.getTeleportTicks() > 0 || !this.player.lagCheck()) {
            this.streak = 0.0;
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        if (rotationUpdate.getFrom().getYaw() == Math.round(rotationUpdate.getFrom().getYaw()) || (rotationUpdate.getFrom().getPitch() != -90.0f && rotationUpdate.getFrom().getPitch() != 90.0f && rotationUpdate.getFrom().getPitch() == Math.round(rotationUpdate.getFrom().getPitch()))) {
            final double streak = this.streak;
            this.streak = streak + 1.0;
            if (streak > 11.0) {
                if (this.streak >= 14.0) {
                    --this.streak;
                }
                this.onViolation("Rounded Rotation Values");
            }

        }
        else {
            this.streak = Math.max(0.0, this.streak - 0.25);
        }
    }
    
    public AimJ(final PlayerData playerData) {
        super(playerData);
    }

}
