package me.xtasy.anticheat.check.impl.autoclicker;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "AutoClicker (Type B)")
public class AutoClickerB extends PacketCheck
{
    private int validClicks;
    private double vl;
    private int skips;
    private long lastSwing;
    private int zeroClicks;
    private int clickDrops;

    private void clearData() {
        final int n = 0;
        this.validClicks = n;
        this.clickDrops = n;
        this.skips = n;
        this.zeroClicks = n;
    }

    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
            if (this.player.canClickerCheck()) {
                final long n = System.currentTimeMillis() - this.lastSwing;
                if (n < 250L) {
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
                if (this.validClicks == 300) {
                    if (this.zeroClicks <= 3 && this.clickDrops >= 40 && this.skips >= 45) {
                        final double vl = this.vl + 1.0;
                        this.vl = vl;
                        if (vl > 1.0) {
                            this.onViolation(this.zeroClicks + ":" + this.clickDrops + ":" + this.skips);

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
    
    public AutoClickerB(final PlayerData playerData) {
        super(playerData);
    }
}
