package me.xtasy.anticheat.check.impl.autoclicker;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;

@CheckData(checkName = "AutoClicker (Type A)")
public class AutoClickerA extends PacketCheck
{
    private int skips;
    private int validClicks;
    private long lastSwing;
    private int clickDrops;
    private int zeroClicks;

    
    public AutoClickerA(final PlayerData playerData) {
        super(playerData);
    }
    
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
                if (this.validClicks == 500) {
                    if (this.zeroClicks >= 200 && this.skips >= 300 && this.clickDrops <= 10) {
                        this.onViolation(this.zeroClicks + ":" + this.skips + ":" + this.clickDrops);
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
