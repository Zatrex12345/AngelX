package me.xtasy.anticheat.check.impl.badpackets;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import com.comphenix.protocol.wrappers.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "BadPackets (Type E)")
public class BadPacketsE extends PacketCheck
{
    private double vl;

    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.BLOCK_DIG && this.player.lagCheck()) {
            if (packetEvent.getPacket().getPlayerDigTypes().read(0) == EnumWrappers.PlayerDigType.RELEASE_USE_ITEM && this.player.isPlacing()) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 3.0) {
                    this.onViolation("Dig packet sent on same time as release");
                    this.vl = 0.0;

                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 0.5);
            }
        }
    }
    
    public BadPacketsE(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
