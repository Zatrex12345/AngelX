package me.xtasy.anticheat.check.impl.badpackets;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.handler.impl.*;

@CheckData(checkName = "BadPackets (Type C)")
public class BadPacketsC extends PacketCheck
{
    private double vl;
    private boolean placed;

    
    public BadPacketsC(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.HELD_ITEM_SLOT) {
            if (this.player.getLoginTicks() == 0 && this.player.lagCheck() && !this.player.hasLag(500L)) {
                if (this.placed) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl > 1.0) {
                        this.onViolation("Switching item too fast");

                    }
                }
                else {
                    this.vl = Math.max(0.0, this.vl - 0.25);

                }
            }
        }
        else if (packetEvent.getPacketType() == PacketType.Play.Client.BLOCK_PLACE) {
            this.placed = true;

        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            this.placed = false;
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
