package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;
import java.util.function.*;
import me.xtasy.anticheat.data.*;
import java.util.*;

@CheckData(checkName = "Aim (Type A)")
public class AimA extends RotationCheck
{
    private double vl;
    private Queue<Float> pitchChanges;
    private double multiplier;
    private double oldPitchChange;

    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 250L) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        final float distanceBetweenAngles = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getPitch(), rotationUpdate.getFrom().getPitch());
        final float distanceBetweenAngles2 = MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getYaw(), rotationUpdate.getFrom().getYaw());
        if (this.gcd((long)(distanceBetweenAngles * this.multiplier), (long)(this.oldPitchChange * this.multiplier)) < 131072L && distanceBetweenAngles2 > 1.0f && distanceBetweenAngles > 0.5 && distanceBetweenAngles < 20.0f) {
            if (this.pitchChanges.add(distanceBetweenAngles) && this.pitchChanges.size() == 20) {
                final double orElse = this.pitchChanges.stream().mapToDouble((ToDoubleFunction<? super Float>)AimA::run).average().orElse(0.0);
                double n = 0.0;
                final Iterator<Float> iterator = (Iterator<Float>)this.pitchChanges.iterator();
                while (iterator.hasNext()) {
                    n += Math.pow(iterator.next() - orElse, 2.0);

                }
                if (n / this.pitchChanges.size() > 2.0) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl > 20.0) {
                        this.onViolation("Invalid pitch deviation");

                    }
                }
                else {
                    this.vl = 0.0;
                }
                this.pitchChanges.clear();

            }
        }
        else {
            this.vl = 0.0;
        }
        this.oldPitchChange = distanceBetweenAngles;
    }
    
    public AimA(final PlayerData playerData) {
        super(playerData);
        this.pitchChanges = new LinkedList<Float>();
        this.multiplier = Math.pow(2.0, 24.0);
    }
    
    private long gcd(final long n, final long n2) {
        if (n2 <= 16384L) {
            return n;
        }
        return this.gcd(n2, n % n2);
    }
    
    private static double run(final Float n) {
        return n;
    }
    
    public void run(final Object o) {
        this.run((RotationUpdate)o);
    }
}
