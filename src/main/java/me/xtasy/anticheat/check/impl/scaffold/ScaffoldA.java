package me.xtasy.anticheat.check.impl.scaffold;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.handler.impl.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.util.location.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "Scaffold (Type A)")
public class ScaffoldA extends PacketCheck
{
    private double vl;
    private boolean placed;
    private long lastPlace;

    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            if (this.player.lagCheck() && !this.player.hasLag(500L) && this.placed) {
                if (System.currentTimeMillis() - this.lastPlace >= 25L) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl > 5.0) {
                        this.onViolation("Wrong place tick");

                    }
                }
                else {
                    this.vl = Math.max(0.0, this.vl - 0.5);
                }
                this.placed = false;

            }
        }
        else if (packetEvent.getPacketType() == PacketType.Play.Client.BLOCK_PLACE) {
            final CustomLocation lastLocation = this.player.getLastLocation();
            if (lastLocation == null) {
                return;
            }
            if (System.currentTimeMillis() - lastLocation.getTimestamp() <= 25L) {
                this.lastPlace = System.currentTimeMillis();
                this.placed = true;

            }
            else {
                this.vl = Math.max(0.0, this.vl - 0.5);
            }
        }
    }
    
    public ScaffoldA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
