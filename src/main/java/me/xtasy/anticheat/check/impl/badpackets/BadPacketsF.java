package me.xtasy.anticheat.check.impl.badpackets;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.handler.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "BadPackets (Type F)")
public class BadPacketsF extends PacketCheck
{
    boolean block;

    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.BLOCK_PLACE || packetEvent.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
            this.block = true;

        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            this.block = false;

        }
        else if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY && this.block && this.player.isAttacking() && this.player.isEqOrBelow(47)) {
            this.onViolation("sent block before flying" + packetEvent.getPacketType());
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public BadPacketsF(final PlayerData playerData) {
        super(playerData);
    }
}
