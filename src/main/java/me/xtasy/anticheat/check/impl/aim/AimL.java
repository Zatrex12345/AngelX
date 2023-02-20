package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "Aim (Type L)")
public class AimL extends RotationCheck
{
    private double lastpitchChange;
    private double streak;
    private double lastLastpitchChange;

    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() >= 90L) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        if (this.player.getStandTicks() > 0) {
            this.streak = 0.0;
            return;
        }
        float n2;
        final float n = n2 = rotationUpdate.getTo().getPitch() - rotationUpdate.getFrom().getPitch();
        if (n < 0.0f) {
            n2 = -n;
        }
        final float n3 = n2;
        float n5;
        final float n4 = n5 = rotationUpdate.getTo().getYaw() - rotationUpdate.getFrom().getYaw();
        if (n4 < 0.0f) {
            n5 = -n4;
        }
        final float n6 = n5;
        if (n3 == 0.0f && this.lastpitchChange == 0.0 && this.lastLastpitchChange == n3 && n6 > 0.026) {
            final double streak = this.streak;
            this.streak = streak + 1.0;
            if (streak > 27.0) {
                if (this.streak >= 24.0) {
                    --this.streak;
                }
                this.onViolation("Aim Modification");
            }

        }
        else {
            this.streak = Math.max(0.0, this.streak - 8.0);
            this.lastpitchChange = n3;
            this.lastLastpitchChange = this.lastpitchChange;
        }
    }
    
    public AimL(final PlayerData playerData) {
        super(playerData);
    }
    

}
