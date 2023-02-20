package me.xtasy.anticheat.check.impl.killaura;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import com.comphenix.protocol.wrappers.*;
import me.xtasy.anticheat.handler.impl.*;

@CheckData(checkName = "KillAura (Type D)")
public class KillAuraD extends PacketCheck
{
    private boolean sent;

    
    public KillAuraD(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (this.player.getBukkitPlayer().isInsideVehicle()) {
            return;
        }
        if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            if (this.sent && this.player.isAttacking() && this.player.lagCheck()) {
                this.onViolation("Sprint/Sneak");

            }
        }
        else if (packetEvent.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
            final EnumWrappers.PlayerAction playerAction = (EnumWrappers.PlayerAction)packetEvent.getPacket().getPlayerActions().read(0);
            if (playerAction == EnumWrappers.PlayerAction.START_SPRINTING || playerAction == EnumWrappers.PlayerAction.STOP_SPRINTING || playerAction == EnumWrappers.PlayerAction.START_SNEAKING || playerAction == EnumWrappers.PlayerAction.STOP_SNEAKING) {
                this.sent = true;
            }

        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            this.sent = false;
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
