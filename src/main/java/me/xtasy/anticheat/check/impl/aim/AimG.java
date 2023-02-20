package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "Aim (Type G)")
public class AimG extends RotationCheck
{
    private double yawDiff;
    private double streak;
    private double pitchDiff;
    private double lastPitch;
    private double lastYaw;

    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 1000L) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0 || this.player.getTeleportTicks() > 0 || this.player.getStandTicks() > 0) {
            return;
        }
        float n2;
        final float n = n2 = rotationUpdate.getFrom().getPitch() - rotationUpdate.getTo().getPitch();
        if (n < 0.0f) {
            n2 = -n;
        }
        this.pitchDiff = n2;
        float n4;
        final float n3 = n4 = rotationUpdate.getFrom().getYaw() - rotationUpdate.getTo().getYaw();
        if (n3 < 0.0f) {
            n4 = -n3;
        }
        this.yawDiff = n4;
        if ((this.pitchDiff == this.lastPitch && this.pitchDiff != 0.0 && this.pitchDiff > 0.4) || (this.yawDiff == this.lastYaw && this.yawDiff != 0.0 && this.pitchDiff > 0.4)) {
            ++this.streak;
            if (this.streak > 6.0) {
                if (this.streak >= 8.0) {
                    --this.streak;
                }
                this.onViolation("Yaw and pitch pattern");

            }
        }
        else {
            this.streak = Math.max(0.0, this.streak - 0.2);
        }
        this.lastYaw = this.yawDiff;
        this.lastPitch = this.pitchDiff;
    }
    
    public AimG(final PlayerData playerData) {
        super(playerData);
    }
    

}
