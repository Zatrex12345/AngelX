package me.xtasy.anticheat.check.impl.motion;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "MotionY (Type AC)")
public class MotionY extends PositionCheck
{
    private double vl2;
    private double vl1;
    private double lastYDiff;

    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        final boolean insideVehicle = this.player.getBukkitPlayer().isInsideVehicle();
        if (this.player.getVelocityManager().getMaxHorizontal() > 0.0 || this.player.getBukkitPlayer().isFlying() || this.player.isUnderBlock() || this.player.isWasUnderBlock() || this.player.isInLiquid() || this.player.isOnPiston() || this.player.isWasOnPiston() || this.player.getBukkitPlayer().getFallDistance() > 0.0f || this.player.getTeleportTicks() > 0 || this.player.getBukkitPlayer().getGameMode().equals((Object)GameMode.CREATIVE) || this.player.getToggleFlyTicks() > 0 || insideVehicle || this.player.isOnSlab()) {
            return;
        }
        final double lastYDiff = positionUpdate.getTo().getY() - positionUpdate.getFrom().getY();
        final double n = this.lastYDiff - lastYDiff;
        final double n2 = lastYDiff - (n - 0.07840000092983246) - 0.07840000092983246;
        if (n2 <= -1.0) {
            final double vl1 = this.vl1 + 1.0;
            this.vl1 = vl1;
            if (vl1 > 10.0) {
                this.onViolation("A " + n2);

            }
        }
        else {
            this.vl1 = Math.max(0.0, this.vl1 - 1.0);
        }
        if (n2 < 0.001 && n2 > 0.0) {
            final double vl2 = this.vl2 + 1.0;
            this.vl2 = vl2;
            if (vl2 > 10.0) {
                this.onViolation("B " + n2);

            }
        }
        else {
            this.vl2 = Math.max(0.0, this.vl2 - 1.0);
        }
        if (n2 == 0.8899999737739535 || (n == -0.4699 && n2 == -0.5199999) || n == 0.4699999) {
            this.onViolation("C " + n2);
        }
        this.lastYDiff = lastYDiff;
    }
    
    public MotionY(final PlayerData playerData) {
        super(playerData);
    }
    

}
