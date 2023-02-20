package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.RotationCheck;
import me.xtasy.anticheat.check.data.CheckData;
import me.xtasy.anticheat.data.PlayerData;
import me.xtasy.anticheat.update.impl.RotationUpdate;

@CheckData(checkName = "Aim (Type M)")
public class AimM extends RotationCheck
{
    private double streak;

    
    public AimM(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() >= 500L) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
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
        if (this.player.getStandTicks() > 0) {
            this.streak = Math.max(0.0, this.streak - 0.2);
            return;
        }
        if (n3 >= 19.0f && n6 > 5.0f) {
            final double streak = this.streak;
            this.streak = streak + 1.0;
            if (streak > 5.0) {
                if (this.streak >= 4.0) {
                    --this.streak;
                }
                this.onViolation("");
            }

        }
        else {
            this.streak = Math.max(0.0, this.streak - 0.95);
        }
    }
    

}
