package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;

@CheckData(checkName = "Aim (Type I)")
public class AimI extends RotationCheck
{
    private double lastResult;
    private double previous;
    private int vl;

    
    public AimI(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 1500L || this.player.getTeleportTicks() > 0 || !this.player.lagCheck() || this.player.getStandTicks() > 0) {
            this.vl = 0;
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        double n2;
        final double n = n2 = Math.min(rotationUpdate.getTo().getYaw(), (double) rotationUpdate.getFrom().getYaw()) - Math.min(rotationUpdate.getTo().getPitch(), (double) rotationUpdate.getFrom().getPitch());
        if (n < 0.0) {
            n2 = -n;
        }
        final double lastResult = n2;
        float n4;
        final float n3 = n4 = rotationUpdate.getFrom().getPitch() - rotationUpdate.getTo().getPitch();
        if (n3 < 0.0f) {
            n4 = -n3;
        }
        final double previous = n4;
        final double gcd = MathUtil.gcd(16384.0, previous * Math.pow(2.0, 24.0), this.previous * Math.pow(2.0, 24.0));
        if (lastResult <= 150.0 && this.lastResult != lastResult && gcd < 131072.0 && gcd > 0.0) {
            if (this.vl < 20) {
                ++this.vl;
            }
            if (this.vl > 17) {
                this.onViolation("r: " + lastResult);
            }

        }
        else if (this.vl > 1) {
            this.vl -= 5;
        }
        this.lastResult = lastResult;
        this.previous = previous;
    }
    

}
