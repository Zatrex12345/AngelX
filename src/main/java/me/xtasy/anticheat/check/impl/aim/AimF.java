package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;

@CheckData(checkName = "Aim (Type F)")
public class AimF extends RotationCheck
{
    private double lastDiff;
    private double last;
    private double vl;
    private double previous;

    public AimF(final PlayerData playerData) {
        super(playerData);
    }


    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() >= 3000L || this.player.getTeleportTicks() > 0 || !this.player.lagCheck() || this.player.getStandTicks() > 0) {
            this.vl = 0.0;
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        final double min = Math.min(Math.atan2(rotationUpdate.getFrom().getPitch(), rotationUpdate.getTo().getPitch()), this.last);
        float n2;
        final float n = n2 = rotationUpdate.getFrom().getPitch() - rotationUpdate.getTo().getPitch();
        if (n < 0.0f) {
            n2 = -n;
        }
        final double previous = n2;
        final double gcd = MathUtil.gcd(16384.0, previous * Math.pow(2.0, 24.0), this.previous * Math.pow(2.0, 24.0));
        double n4;
        final double n3 = n4 = min - this.lastDiff;
        if (n3 < 0.0) {
            n4 = -n3;
        }
        Label_0274: {
            if (n4 == 0.0) {
                float n6;
                final float n5 = n6 = rotationUpdate.getFrom().getPitch() - rotationUpdate.getTo().getPitch();
                if (n5 < 0.0f) {
                    n6 = -n5;
                }
                if (n6 > 0.0f && gcd < 131072.0 && gcd > 0.0) {
                    if (this.vl < 15.0) {
                        ++this.vl;
                    }
                    if (this.vl <= 6.0) {
                        break Label_0274;
                    }
                    this.onViolation("Invalid Aim");

                    break Label_0274;
                }
            }
            this.vl = Math.max(0.0, this.vl - 0.2);
        }
        this.lastDiff = Math.min(Math.atan2(rotationUpdate.getFrom().getPitch(), rotationUpdate.getTo().getPitch()), this.last);
        this.last = Math.atan2(rotationUpdate.getFrom().getPitch(), rotationUpdate.getTo().getPitch());
        this.previous = previous;
    }
}
