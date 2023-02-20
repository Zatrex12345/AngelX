package me.xtasy.anticheat.check.impl.badpackets;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.handler.impl.*;

@CheckData(checkName = "BadPackets (Type G)")
public class BadPacketsG extends PacketCheck
{
    private double vl;

    
    public BadPacketsG(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            final int loginTicks = this.player.getLoginTicks();
            if (System.currentTimeMillis() - this.player.getLastDelayedMovePacket() <= 50L && loginTicks == 0) {
                ++this.vl;

            }
            else {
                this.vl = 0.0;
            }
            if (this.vl > 70.0) {}
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
