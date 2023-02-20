package me.xtasy.anticheat.check.impl.killaura;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "KillAura (Type C)")
public class KillAuraC extends PacketCheck
{
    private double vl;

    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY && this.player.lagCheck() && this.player.isEqOrBelow(47)) {
            if (this.player.isAttacking() && this.player.isPlacing()) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 2.0) {
                    this.onViolation("Attacking and blocking");

                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 0.5);
            }
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public KillAuraC(final PlayerData playerData) {
        super(playerData);
    }
}
