package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;

@CheckData(checkName = "Aim (Type H)")
public class AimH extends RotationCheck
{
    private double streak;
    private double lastYawAbs;
    boolean lastYaw;
    boolean lastPitch;
    private double lastPitchAbs;

    
    public AimH(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 1000L) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0 || this.player.getTeleportTicks() > 0) {
            return;
        }
        if (this.player.getStandTicks() > 0) {
            return;
        }
        if ((rotationUpdate.getTo().getYaw() == rotationUpdate.getFrom().getYaw() && !this.lastYaw && this.lastYawAbs > 1.0) || (rotationUpdate.getTo().getPitch() == rotationUpdate.getFrom().getPitch() && !this.lastPitch && this.lastPitchAbs > 1.0)) {
            ++this.streak;
            if (this.streak > 5.0) {
                if (this.streak >= 3.0) {
                    --this.streak;
                }
                this.onViolation("Detected Randomization");

            }
        }
        else {
            this.streak = Math.max(0.0, this.streak - 0.2);
        }
        boolean lastYaw;
        if (rotationUpdate.getTo().getYaw() == rotationUpdate.getTo().getYaw()) {
            lastYaw = true;

        }
        else {
            lastYaw = false;
        }
        this.lastYaw = lastYaw;
        boolean lastPitch;
        if (rotationUpdate.getTo().getPitch() == rotationUpdate.getFrom().getPitch()) {
            lastPitch = true;

        }
        else {
            lastPitch = false;
        }
        this.lastPitch = lastPitch;
        float n2;
        final float n = n2 = rotationUpdate.getTo().getPitch() - rotationUpdate.getFrom().getPitch();
        if (n < 0.0f) {
            n2 = -n;
        }
        this.lastPitchAbs = n2;
        float n4;
        final float n3 = n4 = rotationUpdate.getTo().getYaw() - rotationUpdate.getTo().getYaw();
        if (n3 < 0.0f) {
            n4 = -n3;
        }
        this.lastYawAbs = n4;
    }
    

}
