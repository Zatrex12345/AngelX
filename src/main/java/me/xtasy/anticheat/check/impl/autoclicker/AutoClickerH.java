package me.xtasy.anticheat.check.impl.autoclicker;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.util.math.*;
import org.apache.commons.math3.stat.descriptive.moment.*;
import me.xtasy.anticheat.handler.impl.*;
import me.xtasy.anticheat.data.*;
import java.util.*;

@CheckData(checkName = "AutoClicker (Type H)")
public class AutoClickerH extends PacketCheck
{
    Deque<Double> means;
    Deque<Integer> deque;
    int flying;
    int vl;

    
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
                    final double n = 1000.0 / (MathUtil.average(this.deque) * 50.0);
                    if (n > 8.0 && evaluate < 0.015 && evaluate2 > 0.05) {
                        ++this.vl;
                        if (this.vl > 1) {
                            this.onViolation(String.format("%s > 8 cps : %s < 0.015 : %s > 0.05", n, evaluate, evaluate2));

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
    
    public AutoClickerH(final PlayerData playerData) {
        super(playerData);
        this.deque = new ArrayDeque<Integer>();
        this.means = new ArrayDeque<Double>();
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
