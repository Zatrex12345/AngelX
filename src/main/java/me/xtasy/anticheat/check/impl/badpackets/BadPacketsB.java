package me.xtasy.anticheat.check.impl.badpackets;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import com.comphenix.protocol.wrappers.*;
import me.xtasy.anticheat.handler.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "BadPackets (Type B)")
public class BadPacketsB extends PacketCheck
{
    private boolean sentSneak;
    private int vl;

    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (this.player.getBukkitPlayer().isInsideVehicle()) {
            return;
        }
        if (packetEvent.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
            if (this.player.lagCheck() && this.player.isEqOrBelow(47) && (packetEvent.getPacket().getPlayerActions().read(0) == EnumWrappers.PlayerAction.START_SNEAKING || packetEvent.getPacket().getPlayerActions().read(0) == EnumWrappers.PlayerAction.STOP_SNEAKING)) {
                if (this.sentSneak) {
                    if (++this.vl > 1) {
                        this.onViolation("Invalid sneak packets");
                        this.vl = 0;

                    }
                }
                else {
                    this.sentSneak = true;

                }
            }
        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            this.sentSneak = false;
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public BadPacketsB(final PlayerData playerData) {
        super(playerData);
    }
}
