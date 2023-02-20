package me.xtasy.anticheat.check.impl.autoclicker;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.*;
import org.apache.commons.math3.stat.descriptive.moment.*;
import me.xtasy.anticheat.util.math.*;
import java.util.*;
import me.xtasy.anticheat.handler.impl.*;

@CheckData(checkName = "AutoClicker (Type G)")
public class AutoClickerG extends PacketCheck
{
    Deque<Integer> samples;
    int flying;

    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public AutoClickerG(final PlayerData playerData) {
        super(playerData);
        this.samples = new ArrayDeque<Integer>();
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION && this.player.isAttacking()) {
            if (this.flying <= 5) {
                this.samples.add(this.flying);
            }
            if (this.samples.size() > 50) {
                final double evaluate = new StandardDeviation().evaluate((double[])MathUtil.dequeTranslator(this.samples));
                if (evaluate < 0.1) {
                    this.onViolation(String.format("%s < 0.1", evaluate));
                }
                this.samples.removeFirst();
            }
            this.flying = 0;

        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            ++this.flying;
        }
    }
}
