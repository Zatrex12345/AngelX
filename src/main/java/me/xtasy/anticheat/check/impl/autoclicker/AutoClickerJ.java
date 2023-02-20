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

@CheckData(checkName = "AutoClicker (Type J)")
public class AutoClickerJ extends PacketCheck
{
    int vl;
    Deque<Integer> deque;
    Deque<Double> means;
    int flying;

    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public AutoClickerJ(final PlayerData playerData) {
        super(playerData);
        this.deque = new ArrayDeque<Integer>();
        this.means = new ArrayDeque<Double>();
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
            if (this.flying <= 5) {
                this.deque.add(this.flying);
            }
            if (this.deque.size() > 60) {
                this.means.add(new GeometricMean().evaluate((double[])MathUtil.dequeTranslator(this.deque)));
                if (this.means.size() > 25) {
                    final double evaluate = new StandardDeviation().evaluate((double[])MathUtil.dequeTranslator(this.means));
                    final double evaluate2 = new Skewness().evaluate((double[])MathUtil.dequeTranslator(this.deque));
                    final double evaluate3 = new Variance().evaluate((double[])MathUtil.dequeTranslator(this.deque));
                    final double n = 1000.0 / (MathUtil.average(this.deque) * 50.0);
                    if (evaluate > 0.01 && evaluate2 <= 0.5 && evaluate3 <= 0.4 && n > 6.0) {
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
