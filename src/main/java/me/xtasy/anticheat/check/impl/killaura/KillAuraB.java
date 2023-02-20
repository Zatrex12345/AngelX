package me.xtasy.anticheat.check.impl.killaura;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.handler.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "KillAura (Type B)")
public class KillAuraB extends PacketCheck
{
    private double vl;
    private boolean attacked;
    private long lastAttack;

    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            if (this.player.isAttacking() && this.player.lagCheck() && this.player.getLastLocation() != null) {
                if (System.currentTimeMillis() - this.player.getLastLocation().getTimestamp() <= 25L) {
                    this.lastAttack = System.currentTimeMillis();
                    this.attacked = true;
                }

            }
        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType()) && this.attacked) {
            if (this.player.lagCheck()) {
                if (System.currentTimeMillis() - this.lastAttack >= 40L) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl > 5.0) {
                        this.onViolation("Wrong motion");

                    }
                }
                else {
                    this.vl = Math.max(0.0, this.vl - 1.0);
                }
            }
            this.attacked = false;
        }
    }
    
    public KillAuraB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
