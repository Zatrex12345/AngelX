package me.xtasy.anticheat.check.impl.timer;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import java.util.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.handler.impl.*;
import java.util.function.*;

@CheckData(checkName = "Timer (Type A)")
public class TimerA extends PacketCheck
{
    private double vl;
    private long lastFlying;
    private Queue<Long> flyingIntervals;

    
    public TimerA(final PlayerData playerData) {
        super(playerData);
        this.flyingIntervals = new LinkedList<Long>();
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    private static double run(final Long n) {
        return n;
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            final long n = System.currentTimeMillis() - this.player.getLastDelayedMovePacket();
            final long n2 = System.currentTimeMillis() - this.lastFlying;
            final int packetPlayOutPositionTicks = this.player.getPacketPlayOutPositionTicks();
            final int loginTicks = this.player.getLoginTicks();
            if (this.player.lagCheck() && n > 999L && packetPlayOutPositionTicks == 0 && loginTicks == 0 && this.flyingIntervals.add(n2) && this.flyingIntervals.size() == 20) {
                if (1.0 - this.flyingIntervals.stream().mapToDouble((ToDoubleFunction<? super Long>)TimerA::run).average().orElse(0.0) * 2.0 / 100.0 > 0.03) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl > 3.0) {
                        this.onViolation("Too many packets");

                    }
                }
                else {
                    this.vl = Math.max(0.0, this.vl - 1.0);
                }
                this.flyingIntervals.clear();
            }
            this.lastFlying = System.currentTimeMillis();
        }
    }
}
