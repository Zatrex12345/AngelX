package me.xtasy.anticheat.check.impl.badpackets;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.handler.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "BadPackets (Type H)")
public class BadPacketsH extends PacketCheck
{
    private double vl;

    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION && this.player.isAttacking()) {
            final double vl = this.vl + 1.0;
            this.vl = vl;
            if (vl > 40.0) {
                this.onViolation("Spaming arm packet: " + this.vl);

            }
        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            this.vl = Math.max(0.0, this.vl - 25.0);
        }
    }
    
    public BadPacketsH(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
