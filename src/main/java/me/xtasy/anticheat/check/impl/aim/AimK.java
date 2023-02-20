package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "Aim (Type K)")
public class AimK extends RotationCheck
{
    private double streak;

    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 250L) {
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
        final float distanceBetweenAngles = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getYaw(), rotationUpdate.getFrom().getYaw());
        if (n3 > 25.0f && distanceBetweenAngles > 5.0f) {
            ++this.streak;
            if (this.streak > 5.0 && this.streak >= 3.0) {
                --this.streak;

            }
        }
        else {
            this.streak = Math.max(0.0, this.streak - 0.2);
        }
    }
    
    public AimK(final PlayerData playerData) {
        super(playerData);
    }
    

}
