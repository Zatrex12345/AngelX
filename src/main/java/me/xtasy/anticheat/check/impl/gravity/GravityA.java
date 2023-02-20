package me.xtasy.anticheat.check.impl.gravity;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.data.*;
import org.bukkit.potion.*;
import me.xtasy.anticheat.util.player.*;
import org.bukkit.*;

@CheckData(checkName = "Gravity (Type A)")
public class GravityA extends PositionCheck
{
    private int airTicks;
    private double lastMotionY;
    private double vl;

    

    
    public GravityA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        if (PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.JUMP) > 3) {
            return;
        }
        if (System.currentTimeMillis() - this.player.getLastDelayedMovePacket() < 110L) {
            return;
        }
        if (this.player.getTeleportTicks() > 0) {
            return;
        }
        final double maxVertical = this.player.getVelocityManager().getMaxVertical();
        final double n = this.player.getBukkitPlayer().getFallDistance();
        final int toggleFlyTicks = this.player.getToggleFlyTicks();
        final int slimeTicks = this.player.getSlimeTicks();
        final int loginTicks = this.player.getLoginTicks();
        final int packetPlayOutPositionTicks = this.player.getPacketPlayOutPositionTicks();
        final long n2 = System.currentTimeMillis() - this.player.getLastPlaceCancel();
        final long n3 = System.currentTimeMillis() - this.player.getLastOnBadBlock();
        final boolean insideVehicle = this.player.getBukkitPlayer().isInsideVehicle();
        boolean b;
        if (this.player.isInLiquid() || this.player.isWasInLiquid()) {
            b = true;

        }
        else {
            b = false;
        }
        final boolean b2 = b;
        boolean b3;
        if (this.player.isOnGround() || this.player.isWasOnGround()) {
            b3 = true;

        }
        else {
            b3 = false;
        }
        final boolean b4 = b3;
        boolean b5;
        if (this.player.isInWeb() || this.player.isWasInWeb()) {
            b5 = true;

        }
        else {
            b5 = false;
        }
        final boolean b6 = b5;
        final boolean movedSinceLogin = this.player.isMovedSinceLogin();
        final boolean equals = this.player.getBukkitPlayer().getGameMode().equals((Object)GameMode.CREATIVE);
        final boolean flying = this.player.getBukkitPlayer().isFlying();
        boolean b7;
        if (this.player.getBukkitPlayer().getFallDistance() > 10.0f && this.player.getBukkitPlayer().getLocation().getY() < 5.0) {
            b7 = true;

        }
        else {
            b7 = false;
        }
        final boolean b8 = b7;
        final boolean eqOrBelow = this.player.isEqOrBelow(47);
        if (!b4) {
            ++this.airTicks;

        }
        else {
            this.airTicks = 0;
        }
        if (toggleFlyTicks > 0 || slimeTicks > 0 || loginTicks > 0 || insideVehicle || b2 || b4 || b6 || n2 < 1000L || n3 < 1000L || !movedSinceLogin || equals || packetPlayOutPositionTicks > 20 || maxVertical != 0.0 || flying || n > 180.0 || b8 || !eqOrBelow) {
            return;
        }
        final double lastMotionY = positionUpdate.getTo().getY() - positionUpdate.getFrom().getY();
        if (this.airTicks > 2) {
            double n5;
            final double n4 = n5 = lastMotionY - this.lastMotionY;
            if (n4 < 0.0) {
                n5 = -n4;
            }
            final double n6 = n5;
            if (n6 < 0.001 && n6 > 0.0) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 2.0) {
                    this.onViolation("Not following MC Gravity " + n6);

                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 0.5);
            }
        }
        this.lastMotionY = lastMotionY;
    }
}
