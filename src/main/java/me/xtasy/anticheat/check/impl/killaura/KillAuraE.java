package me.xtasy.anticheat.check.impl.killaura;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.handler.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "KillAura (Type E)")
public class KillAuraE extends PacketCheck
{
    private boolean sent;

    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.HELD_ITEM_SLOT) {
            if (this.sent && this.player.lagCheck()) {
                this.onViolation("Switching item and attacking");

            }
        }
        else if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            if (this.player.isAttacking()) {
                this.sent = true;

            }
        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            this.sent = false;
        }
    }
    
    public KillAuraE(final PlayerData playerData) {
        super(playerData);
    }
}
