package me.xtasy.anticheat.check.impl.groundspoof;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.*;

@CheckData(checkName = "GroundSpoof (Type A)")
public class GroundSpoofA extends PositionCheck
{
    private double vl;

    public GroundSpoofA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        if (this.player.getTeleportTicks() > 0) {
            return;
        }
        if (!this.player.getBukkitPlayer().getGameMode().equals((Object)GameMode.CREATIVE) && !this.player.getBukkitPlayer().isDead() && this.player.getBukkitPlayer().getVehicle() == null && this.player.lagCheck()) {
            this.player.getPacketPlayOutPositionTicks();
            final double n = positionUpdate.getFrom().getY() - positionUpdate.getTo().getY();
            if (!this.player.isOnPiston() && !this.player.isWasOnPiston()) {
                if (n > 0.8 && this.player.getBukkitPlayer().getFallDistance() == 0.0f) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl > 3.0) {
                        this.onViolation("Invalid fall");

                    }
                }
                else {
                    this.vl = Math.max(0.0, this.vl - 0.5);
                }
            }
        }
    }
    

}
