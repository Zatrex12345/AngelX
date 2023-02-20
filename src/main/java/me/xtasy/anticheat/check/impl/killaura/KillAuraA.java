package me.xtasy.anticheat.check.impl.killaura;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.handler.impl.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "KillAura (Type A)")
public class KillAuraA extends PacketCheck
{
    private int vl;
    private boolean swung;

    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            if (this.player.lagCheck() && this.player.isEqOrBelow(47)) {
                if (this.player.isAttacking()) {
                    if (!this.swung) {
                        if (++this.vl > 2) {
                            this.onViolation("No Swing");

                        }
                    }
                    else {
                        this.vl = 0;

                    }
                }
                else {
                    this.swung = false;

                }
            }
            else {
                this.vl = 0;

            }
        }
        else if (packetEvent.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
            this.swung = true;

        }
        else if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            this.swung = false;
        }
    }
    
    public KillAuraA(final PlayerData playerData) {
        super(playerData);
    }
}
