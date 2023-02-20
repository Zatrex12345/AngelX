package me.xtasy.anticheat.check.impl.inventory;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.*;

@CheckData(checkName = "Inventory (Type A)")
public class InventoryA extends PacketCheck
{
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public InventoryA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY && this.player.lagCheck() && this.player.isInventoryOpen()) {
            this.onViolation("Invalid Action");
        }
    }
}
