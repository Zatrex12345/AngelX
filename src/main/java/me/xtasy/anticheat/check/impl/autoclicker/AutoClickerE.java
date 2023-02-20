package me.xtasy.anticheat.check.impl.autoclicker;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.handler.impl.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "AutoClicker (Type E)")
public class AutoClickerE extends PacketCheck
{
    private int swings;
    private int flying;

    @Override
    public void run(final PacketEvent packetEvent) {
        if (PacketHandler.isFlying(packetEvent.getPacketType()) && this.player.lagCheck()) {
            if (this.player.isEqOrBelow(47)) {
                if (++this.flying == 20) {
                    if (this.swings > 21) {
                        this.onViolation("CPS " + this.swings);
                    }
                    final int n = 0;
                    this.swings = n;
                    this.flying = n;

                }
            }
            else if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
                if (this.player.canClickerCheck() && this.player.lagCheck()) {
                    ++this.swings;

                }
                else {
                    this.swings = 0;
                }
            }
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public AutoClickerE(final PlayerData playerData) {
        super(playerData);
    }
}
