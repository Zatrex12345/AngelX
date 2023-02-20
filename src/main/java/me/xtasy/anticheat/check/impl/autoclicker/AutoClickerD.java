package me.xtasy.anticheat.check.impl.autoclicker;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.data.*;
import java.util.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.handler.impl.*;
import java.util.function.*;

@CheckData(checkName = "AutoClicker (Type D)")
public class AutoClickerD extends PacketCheck
{
    private Queue<Integer> clicksIntervals;
    private boolean swung;
    private double vl;
    private int ticks;
    private double lastAverage;
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public AutoClickerD(final PlayerData playerData) {
        super(playerData);
        this.clicksIntervals = new LinkedList<Integer>();
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
            if (this.player.canClickerCheck()) {
                this.swung = true;

            }
            else {
                this.swung = false;

            }
        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            if (this.swung) {
                if (this.ticks <= 7 && this.clicksIntervals.add(this.ticks) && this.clicksIntervals.size() == 350) {
                    final double orElse = this.clicksIntervals.stream().mapToDouble((ToDoubleFunction<? super Integer>)AutoClickerD::run).average().orElse(0.0);
                    final double n = orElse - this.lastAverage;
                    if (n < 0.001 && n > -0.001) {
                        final double vl = this.vl + 1.0;
                        this.vl = vl;
                        if (vl > 1.0) {
                            this.onViolation("" + n);

                        }
                    }
                    else {
                        this.vl = Math.max(0.0, this.vl - 1.0);
                    }
                    this.lastAverage = orElse;
                    this.clicksIntervals.clear();
                }
                this.ticks = 0;
            }
            this.swung = false;
            ++this.ticks;
        }
    }



    private static double run(final Integer n) {
        return n;
    }
}
