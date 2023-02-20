package me.xtasy.anticheat.check.impl.badpackets;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.handler.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "BadPackets (Type D)")
public class BadPacketsD extends PacketCheck
{
    @Override
    public void run(final PacketEvent packetEvent) {
        if (PacketHandler.isFlying(packetEvent.getPacketType()) && this.player.lagCheck() && (float)packetEvent.getPacket().getFloat().read(1) < -90.0f) {
            this.onViolation("Invalid Pitch");
        }
    }
    
    public BadPacketsD(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
