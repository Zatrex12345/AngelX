package me.xtasy.anticheat.check.impl.jesus;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.handler.impl.*;

@CheckData(checkName = "Jesus (Type A)")
public class JesusA extends PacketCheck
{
    private double lastY;
    private double lastTotal;
    private double vl;
    private double lastFailedY;

    
    public JesusA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            if (!this.player.lagCheck() || this.player.getBukkitPlayer().isFlying() || this.player.getVelocityManager().getMaxVertical() > 0.0 || this.player.getLoginTicks() > 0 || this.player.getToggleFlyTicks() > 0 || this.player.isInWeb() || this.player.isWasInWeb() || this.player.getSpeedXZ() < 0.2 || this.player.isHeadInLiquid() || this.player.isHeadWasInLiquid() || this.player.isOnLilyPad()) {
                return;
            }
            final boolean inLiquid = this.player.isInLiquid();
            boolean b;
            if (this.player.isOnGround() || this.player.isWasOnGround()) {
                b = true;

            }
            else {
                b = false;
            }
            if (b) {
                return;
            }
            final double y = this.player.getBukkitPlayer().getLocation().getY();
            if (inLiquid) {
                final double lastFailedY = y - this.lastY;
                final double lastTotal = this.lastFailedY + lastFailedY;
                if (lastTotal == 0.0) {
                    if (lastTotal == this.lastTotal) {
                        final double vl = this.vl + 1.0;
                        this.vl = vl;
                        if (vl > 10.0 && this.player.isEqOrBelow(47)) {
                            this.onViolation("Hovering over water");

                        }
                    }
                    else {
                        this.vl = Math.max(0.0, this.vl - 1.0);
                    }
                }
                this.lastTotal = lastTotal;
                this.lastFailedY = lastFailedY;
            }
            this.lastY = y;
        }
    }
}
