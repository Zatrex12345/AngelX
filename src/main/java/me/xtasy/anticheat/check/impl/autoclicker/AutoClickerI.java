package me.xtasy.anticheat.check.impl.autoclicker;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.util.math.*;
import java.util.*;
import org.apache.commons.math3.stat.descriptive.moment.*;
import me.xtasy.anticheat.handler.impl.*;

@CheckData(checkName = "AutoClicker (Type I)")
public class AutoClickerI extends PacketCheck
{
    Deque<Double> means;
    int vl;
    Deque<Integer> deque;
    int flying;

    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public AutoClickerI(final PlayerData playerData) {
        super(playerData);
        this.deque = new ArrayDeque<Integer>();
        this.means = new ArrayDeque<Double>();
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION && this.player.isAttacking()) {
            if (this.flying <= 5) {
                this.deque.add(this.flying);
            }
            if (this.deque.size() > 50) {
                this.means.add(new GeometricMean().evaluate((double[])MathUtil.dequeTranslator(this.deque)));
                if (this.means.size() > 20) {
                    final double evaluate = new StandardDeviation().evaluate((double[])MathUtil.dequeTranslator(this.means));
                    final double evaluate2 = new Skewness().evaluate((double[])MathUtil.dequeTranslator(this.deque));
                    final double evaluate3 = new Variance().evaluate((double[])MathUtil.dequeTranslator(this.deque));
                    final double n = 1000.0 / (MathUtil.average(this.deque) * 50.0);
                    if (n > 8.0 && evaluate > 0.01 && evaluate2 >= 1.01 && evaluate3 >= 1.16) {
                        ++this.vl;
                        if (this.vl > 1) {
                            this.onViolation(String.format("CPS: %s", n));

                        }
                    }
                    else {
                        this.vl = 0;
                    }
                    this.means.clear();
                }
                this.deque.removeFirst();
            }
            this.flying = 0;

        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            ++this.flying;
        }
    }
}
