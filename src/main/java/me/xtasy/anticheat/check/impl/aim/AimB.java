package me.xtasy.anticheat.check.impl.aim;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.math.*;
import java.util.function.*;
import java.util.*;

@CheckData(checkName = "Aim (Type B)")
public class AimB extends RotationCheck
{
    private double lastDeviation;
    private Queue<Float> pitchChanges;

    public AimB(final PlayerData playerData) {
        super(playerData);
        this.pitchChanges = new LinkedList<Float>();
        this.lastDeviation = -1.0;
    }
    

    
    private static double run(final Float n) {
        return n;
    }
    
    @Override
    public void run(final RotationUpdate rotationUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() > 100L) {
            return;
        }
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        if (this.pitchChanges.add(MathUtil.getDistanceBetweenAngles(rotationUpdate.getTo().getPitch(), rotationUpdate.getFrom().getPitch())) && this.pitchChanges.size() == 20) {
            final double orElse = this.pitchChanges.stream().mapToDouble((ToDoubleFunction<? super Object>)AimB::run).average().orElse(0.0);
            double n = 0.0;
            final Iterator<Float> iterator = this.pitchChanges.iterator();
            while (iterator.hasNext()) {
                n += Math.pow(iterator.next() - orElse, 2.0);

            }
            final double lastDeviation = n / this.pitchChanges.size();
            if (this.lastDeviation != -1.0 && lastDeviation > 0.1 && lastDeviation == lastDeviation - this.lastDeviation) {
                this.onViolation("Pitch pattern");
            }
            this.lastDeviation = lastDeviation;
            this.pitchChanges.clear();
        }
    }

    private static double run(Object o) {
        return (double) o;
    }
}
