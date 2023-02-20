package me.xtasy.anticheat.check.impl.badpackets;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.*;
import com.comphenix.protocol.wrappers.*;
import me.xtasy.anticheat.handler.impl.*;

@CheckData(checkName = "BadPackets (Type A)")
public class BadPacketsA extends PacketCheck
{
    private int vl;
    private boolean sentSprint;

    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public BadPacketsA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (this.player.getBukkitPlayer().isInsideVehicle()) {
            return;
        }
        if (packetEvent.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
            if (this.player.lagCheck() && this.player.isEqOrBelow(47) && packetEvent.getPacket().getPlayerActions().read(0) == EnumWrappers.PlayerAction.START_SPRINTING) {
                if (this.sentSprint) {
                    if (++this.vl > 1) {
                        this.onViolation("Invalid sprint packets");
                        this.vl = 0;

                    }
                }
                else {
                    this.sentSprint = true;

                }
            }
        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            this.sentSprint = false;
        }
    }
}
