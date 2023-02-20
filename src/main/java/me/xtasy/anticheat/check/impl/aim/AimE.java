package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;

@CheckData(checkName = "Aim (Type E)")
public class AimE extends RotationCheck
{
    private double streak;
    private double oldPitchChange;
    private double vl;
    private double multiplier;

    
    public AimE(final PlayerData playerData) {
        super(playerData);
        this.multiplier = Math.pow(2.0, 24.0);
    }
    

    
    private long gcd(final long n, final long n2) {
        if (n2 <= 16384L) {
            return n;
        }
        return this.gcd(n2, n % n2);
    }
    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 100L || !this.player.lagCheck()) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        if (this.player.getStandTicks() > 0) {
            return;
        }
        final float distanceBetweenAngles = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getPitch(), rotationUpdate.getFrom().getPitch());
        final float distanceBetweenAngles2 = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getYaw(), rotationUpdate.getFrom().getYaw());
        final long gcd = this.gcd((long)(distanceBetweenAngles * this.multiplier), (long)(this.oldPitchChange * this.multiplier));
        if (distanceBetweenAngles * 100.0f / this.oldPitchChange > 60.0) {
            this.vl = Math.max(0.0, this.vl - 1.0);
            this.streak = Math.max(0.0, this.streak - 0.125);
        }
        if (distanceBetweenAngles > 0.5 && distanceBetweenAngles <= 15.0f && gcd < 131072L && distanceBetweenAngles2 > 2.0f) {
            final double vl = this.vl + 1.0;
            this.vl = vl;
            if (vl > 1.0) {
                ++this.streak;
            }
            final double streak = this.streak + 1.0;
            this.streak = streak;
            if (streak > 10.0) {
                this.onViolation("Pitch Delta " + distanceBetweenAngles);
                this.streak = 0.0;

            }
        }
        else {
            this.vl = Math.max(0.0, this.vl - 1.0);
            this.streak = Math.max(0.0, this.streak - 1.0);
        }
        this.oldPitchChange = distanceBetweenAngles;
    }
}
