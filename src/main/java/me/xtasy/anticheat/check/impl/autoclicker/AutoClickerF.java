package me.xtasy.anticheat.check.impl.autoclicker;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;

@CheckData(checkName = "AutoClicker (Type F)")
public class AutoClickerF extends PacketCheck
{
    private long lastSwing;
    private int clickDrops;
    private int validClicks;
    private int zeroClicks;
    private double vl;
    private int skips;

    private void clearData() {
        final int n = 0;
        this.validClicks = n;
        this.clickDrops = n;
        this.skips = n;
        this.zeroClicks = n;
    }
    
    public AutoClickerF(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
            if (this.player.canClickerCheck()) {
                final long n = System.currentTimeMillis() - this.lastSwing;
                if (n < 250L) {
                    ++this.validClicks;
                    if (n <= 2L) {
                        ++this.zeroClicks;
                    }
                    if (n > 150L) {
                        ++this.clickDrops;
                    }
                    if (n < 100L) {
                        ++this.skips;
                    }
                }
                if (this.validClicks == 200) {
                    if (this.zeroClicks <= 10 && this.skips <= 100 && this.skips >= 70 && this.clickDrops <= 5) {
                        this.onViolation("A " + this.zeroClicks + ":" + this.skips + ":" + this.clickDrops);
                    }
                    if (this.zeroClicks <= 2 && this.skips >= 150 && this.clickDrops <= 2) {
                        final double vl = this.vl + 1.0;
                        this.vl = vl;
                        if (vl > 1.0) {
                            this.onViolation("B " + this.zeroClicks + ":" + this.skips + ":" + this.clickDrops);

                        }
                    }
                    else {
                        this.vl = Math.max(0.0, this.vl - 0.5);
                    }
                    this.clearData();
                }
                this.lastSwing = System.currentTimeMillis();

            }
            else {
                this.clearData();
            }
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
