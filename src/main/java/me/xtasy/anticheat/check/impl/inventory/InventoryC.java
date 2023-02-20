package me.xtasy.anticheat.check.impl.inventory;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.*;
import com.comphenix.protocol.wrappers.*;

@CheckData(checkName = "Inventory (Type C)")
public class InventoryC extends PacketCheck
{
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public InventoryC(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (this.player.getBukkitPlayer().isInsideVehicle()) {
            return;
        }
        if (this.player.getTeleportTicks() > 0) {
            return;
        }
        if (packetEvent.getPacketType() == PacketType.Play.Client.ENTITY_ACTION && this.player.lagCheck() && packetEvent.getPacket().getPlayerActions().read(0) == EnumWrappers.PlayerAction.START_SPRINTING && this.player.isInventoryOpen()) {
            this.onViolation("Sprint");
        }
    }
}
