package me.xtasy.anticheat.check.impl.inventory;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.*;

@CheckData(checkName = "Inventory (Type B)")
public class InventoryB extends PacketCheck
{
    private int vl;

    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public InventoryB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION && this.player.lagCheck() && !this.player.hasLag(500L)) {
            if (this.player.isInventoryOpen()) {
                if (++this.vl > 1) {
                    this.onViolation("Swing");

                }
            }
            else {
                this.vl = 0;
            }
        }
    }
}
