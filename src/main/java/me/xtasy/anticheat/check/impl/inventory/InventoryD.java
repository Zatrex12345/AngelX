package me.xtasy.anticheat.check.impl.inventory;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "Inventory (Type D)")
public class InventoryD extends PositionCheck
{
    private double vl;

    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        if (this.player.isInLiquid() || this.player.isWasInLiquid() || this.player.isInWeb() || this.player.isWasInWeb() || this.player.getVelocityManager().getMaxHorizontal() > 0.0 || this.player.getVelocityManager().getMaxVertical() > 0.0 || !this.player.getBukkitPlayer().getGameMode().equals((Object)GameMode.SURVIVAL) || this.player.getToggleFlyTicks() > 0 || this.player.getBukkitPlayer().isFlying() || this.player.getBukkitPlayer().getAllowFlight() || this.player.getTeleportTicks() > 0 || this.player.getFlyingTicks() > 0) {
            this.vl = 0.0;
            return;
        }
        if (this.player.lagCheck()) {
            if (this.player.getSpeedXZ() > 0.2 && this.player.isInventoryOpen()) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 10.0) {
                    this.onViolation("Moving while inventory open");

                }
            }
            else {
                this.vl = 0.0;
            }
        }
    }
    
    public InventoryD(final PlayerData playerData) {
        super(playerData);
    }
    

}
