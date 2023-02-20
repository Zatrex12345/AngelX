package me.xtasy.anticheat.check.impl.motion;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.potion.*;
import me.xtasy.anticheat.util.player.*;
import org.bukkit.*;

@CheckData(checkName = "MotionXZ (Type A)")
public class MotionXZ extends PositionCheck
{
    private int fallTicks;
    private double vlair;
    private int jumps;
    private double vlground;
    private long lastFalling;
    private int iceTicks;
    private int underBlockTicks;
    private int groundTicks;
    private double mcFriction;

    
    public MotionXZ(final PlayerData playerData) {
        super(playerData);
        this.mcFriction = 0.9100000262260437;
    }
    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        final int loginTicks = this.player.getLoginTicks();
        final int toggleFlyTicks = this.player.getToggleFlyTicks();
        final int potionAmplifier = PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.SPEED);
        final double maxHorizontal = this.player.getVelocityManager().getMaxHorizontal();
        final double maxVertical = this.player.getVelocityManager().getMaxVertical();
        final double n = this.player.getBukkitPlayer().getWalkSpeed();
        final long n2 = System.currentTimeMillis() - this.player.getLastOnBadBlock();
        final long n3 = System.currentTimeMillis() - this.player.getLastDelayedMovePacket();
        final boolean onCarpet = this.player.isOnCarpet();
        final boolean onStairs = this.player.isOnStairs();
        final boolean onRail = this.player.isOnRail();
        final int glideTicks = this.player.getGlideTicks();
        final boolean equals = this.player.getBukkitPlayer().getGameMode().equals((Object)GameMode.CREATIVE);
        final boolean flying = this.player.getBukkitPlayer().isFlying();
        final boolean insideVehicle = this.player.getBukkitPlayer().isInsideVehicle();
        boolean b;
        if (maxHorizontal > 0.0 && (this.player.isInLiquid() || this.player.isWasInLiquid())) {
            b = true;

        }
        else {
            b = false;
        }
        final boolean b2 = b;
        if (loginTicks > 0 || toggleFlyTicks > 0 || equals || onRail || insideVehicle || maxHorizontal > 0.0 || maxVertical > 0.0 || n > 0.21 || onStairs || n2 < 1001L || potionAmplifier > 2 || flying || b2 || onCarpet || this.player.getFlyingTicks() > 0 || glideTicks > 0) {
            return;
        }
        final Location to = positionUpdate.getTo();
        final Location from = positionUpdate.getFrom();
        final boolean onGround = this.player.isOnGround();
        final boolean wasOnGround = this.player.isWasOnGround();
        boolean b3;
        if (this.player.isUnderBlock() || this.player.isWasUnderBlock()) {
            b3 = true;

        }
        else {
            b3 = false;
        }
        final boolean b4 = b3;
        final boolean onIce = this.player.isOnIce();
        final double n4 = to.getX() - from.getX();
        final double n5 = to.getY() - from.getY();
        final double n6 = to.getZ() - from.getZ();
        double n7 = (n4 * n4 + n6 * n6) * this.mcFriction;
        if (n7 == 0.0) {
            return;
        }
        if (onGround) {
            ++this.groundTicks;
            if (this.groundTicks > 10) {
                this.jumps = 0;

            }
        }
        else {
            this.groundTicks = 0;
        }
        if (this.groundTicks > 4 && n5 >= 0.0) {
            this.fallTicks = 0;
        }
        if (n5 > 0.41) {
            ++this.jumps;
        }
        if (this.jumps == 1) {
            n7 *= 0.36;

        }
        else if (this.jumps >= 2) {
            n7 *= 0.44;
        }
        if (n5 < 0.0) {
            ++this.fallTicks;
            if (this.fallTicks == 1) {
                this.lastFalling = System.currentTimeMillis();

            }
        }
        else {
            this.fallTicks = 0;
        }
        int fallTicks;
        if ((fallTicks = 0) < (fallTicks = this.fallTicks - 1)) {}
        this.fallTicks = fallTicks;
        final double n8 = PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.SPEED);
        double n9 = 0.085;
        double n10 = n7 - n8 * 0.038;
        if (this.underBlockTicks > 10) {
            this.underBlockTicks = 10;
        }
        if (b4) {
            ++this.underBlockTicks;

        }
        else {
            final int underBlockTicks = 0;
            final int n11;
            if (underBlockTicks < (n11 = this.underBlockTicks - 1)) {}
            this.underBlockTicks = underBlockTicks;
        }
        if (this.iceTicks > 10) {
            this.iceTicks = 10;
        }
        if (onIce) {
            ++this.iceTicks;

        }
        else {
            final int iceTicks = 0;
            final int n12;
            if (iceTicks < (n12 = this.iceTicks - 1)) {}
            this.iceTicks = iceTicks;
        }
        if (this.underBlockTicks > 0) {
            if (n5 > 0.2) {
                n10 *= 0.19;

            }
            else {
                n10 *= 0.52;
            }
        }
        if (this.iceTicks > 0) {
            n9 += 0.6;
        }
        final long n13 = System.currentTimeMillis() - this.lastFalling;
        if (n13 < 52L) {
            n10 *= 0.55;

        }
        else if (n13 < 102L) {
            n10 *= 0.75;

        }
        else if (n13 < 202L) {
            n10 *= 0.88;
        }
        if (onGround && wasOnGround) {
            if (n10 > n9) {
                final double vlground = this.vlground + 1.0;
                this.vlground = vlground;
                if (vlground > 10.0) {
                    this.onViolation("Ground Speed Limit (" + n3 + ")");

                }
            }
            else {
                this.vlground = Math.max(0.0, this.vlground - 0.5);

            }
        }
        else if (this.player.getBukkitPlayer().getFallDistance() <= 3.0f) {
            final double n14 = n10;
            double n15;
            if (this.iceTicks > 0) {
                n15 = 0.4;

            }
            else {
                n15 = 0.08;
            }
            if (n14 > n15) {
                final double vlair = this.vlair + 1.0;
                this.vlair = vlair;
                if (vlair > 10.0) {
                    this.onViolation("Air Speed Limit (" + n3 + ")");

                }
            }
            else {
                this.vlair = Math.max(0.0, this.vlair - 0.5);
            }
        }
    }
    

}
