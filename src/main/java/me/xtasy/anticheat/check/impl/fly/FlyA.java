package me.xtasy.anticheat.check.impl.fly;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import me.xtasy.anticheat.util.player.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.handler.impl.*;
import org.bukkit.*;

@CheckData(checkName = "Fly (Type A)")
public class FlyA extends PacketCheck
{
    private double jumpMultiplier;
    private double jumpLimit;
    private double lastAscendSpeed;
    private double airTicks;
    private double vl;
    private double lastY;
    private double lastYDifference;

    
    public FlyA(final PlayerData playerData) {
        super(playerData);
    }
    
    private double allowedYValue(final Player player) {
        double n = 0.05;
        if (player.getFallDistance() < 15.0f) {
            switch (PlayerUtils.getPotionAmplifier(player, PotionEffectType.JUMP)) {
                case 1: {
                    n = 0.02;

                    break;
                }
                case 2: {
                    n = 0.035;
                    break;
                }
            }

        }
        else if (player.getFallDistance() < 25.0f) {
            n = 0.045;

        }
        else if (player.getFallDistance() < 35.0f) {
            n = 0.04;

        }
        else if (player.getFallDistance() < 40.0f) {
            n = 0.037;

        }
        else if (player.getFallDistance() < 50.0f) {
            n = 0.035;

        }
        else if (player.getFallDistance() < 60.0f) {
            n = 0.032;

        }
        else if (player.getFallDistance() < 70.0f) {
            n = 0.029;

        }
        else if (player.getFallDistance() < 80.0f) {
            n = 0.026;

        }
        else if (player.getFallDistance() < 100.0f) {
            n = 0.023;

        }
        else if (player.getFallDistance() < 120.0f) {
            n = 0.02;

        }
        else if (player.getFallDistance() < 135.0f) {
            n = 0.018;

        }
        else if (player.getFallDistance() < 150.0f) {
            n = 0.016;

        }
        else if (player.getFallDistance() <= 180.0f) {
            n = 0.0135;
        }
        return n;
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
  //      if (customLocation.isFlying(packetEvent.getPacketType())) {
            if (System.currentTimeMillis() - this.player.getLastPlaceCancel() < 1200L) {
                return;
            }
            if (System.currentTimeMillis() - this.player.getLastDelayedMovePacket() < 110L) {
                return;
            }
            if (PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.JUMP) > 3) {
                return;
            }
            if (this.player.getTeleportTicks() > 0) {
                return;
            }
            final double n = this.player.getBukkitPlayer().getLocation().getY() - this.lastY;
            final double maxVertical = this.player.getVelocityManager().getMaxVertical();
            final double maxHorizontal = this.player.getVelocityManager().getMaxHorizontal();
            final double n2 = this.player.getBukkitPlayer().getFallDistance();
            final int toggleFlyTicks = this.player.getToggleFlyTicks();
            final int glideTicks = this.player.getGlideTicks();
            final int slimeTicks = this.player.getSlimeTicks();
            final int loginTicks = this.player.getLoginTicks();
            this.player.getPacketPlayOutPositionTicks();
            final long n3 = System.currentTimeMillis() - this.player.getLastPlaceCancel();
            final long n4 = System.currentTimeMillis() - this.player.getLastOnBadBlock();
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
            final boolean onSnow = this.player.isOnSnow();
            final boolean onRail = this.player.isOnRail();
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
            if (this.player.lagCheck() && this.vl <= 8.0) {
                this.vl = Math.max(0.0, this.vl - 0.8);
            }
            if (!b4) {
                ++this.airTicks;

            }
            else {
                this.airTicks = 0.0;
            }
            if (this.player.getFlyingTicks() > 0 || toggleFlyTicks > 0 || slimeTicks > 0 || loginTicks > 0 || insideVehicle || onSnow || b2 || b4 || b6 || n3 < 1000L || n4 < 1000L || !movedSinceLogin || equals || onRail || maxHorizontal > 0.0 || maxVertical > 0.0 || flying || n2 > 180.0 || b8 || this.player.getTeleportTicks() > 0 || glideTicks > 0) {
                this.jumpMultiplier = 0.8 + 0.03 * PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.JUMP);
                this.jumpLimit = 0.42 + 0.11 * PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.JUMP);
                this.vl = 0.0;

            }
            else {
                if (PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.JUMP) > 0) {
                    this.jumpLimit += PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.JUMP) / 4;
                }
                if (b2) {
                    this.jumpLimit += 0.2;
                }
                if (this.airTicks > 5.0) {
                    if (maxVertical == 0.0) {
                        if (n > 0.0 && n != this.lastYDifference) {
                            final double vl = this.vl + 1.0;
                            this.vl = vl;
                            if (vl > 15.0) {
                                this.onViolation("Going illegally upwards " + n);
                            }
                        }
                        final double n5 = this.lastAscendSpeed - n;
                        if (n5 < 0.074 && n5 > 0.0) {
                            final double vl2 = this.vl + 1.0;
                            this.vl = vl2;
                            if (vl2 > 10.0) {
                                this.onViolation("Randomized Hover " + n5);
                            }
                        }
                        final double n6 = this.lastYDifference - n;
                        if (n6 > -1.0 && n6 < this.allowedYValue(this.player.getBukkitPlayer())) {
                            final double vl3 = this.vl + 1.0;
                            this.vl = vl3;
                            if (vl3 > 10.0) {
                                this.onViolation("Wrong fall speed " + (this.lastYDifference - n));
                            }
                        }
                    }
                    if (n == 0.0 && this.lastYDifference == 0.0) {
                        final double vl4 = this.vl + 1.0;
                        this.vl = vl4;
                        if (vl4 > 10.0) {
                            this.onViolation("Hover pattern " + n);
                        }
                    }
                    if (n > this.jumpLimit + maxVertical) {
                        final double vl5 = this.vl + 1.0;
                        this.vl = vl5;
                        if (vl5 > 10.0) {
                            this.onViolation("Wrong move " + n + " > " + (this.jumpLimit + maxVertical));

                        }
                    }
                    else {
                        this.jumpLimit *= this.jumpMultiplier;
                        final double jumpMultiplier = this.jumpMultiplier;
                        double n7;
                        if (n > 0.1 && n < 0.4) {
                            n7 = 0.05;

                        }
                        else {
                            n7 = 0.025;
                        }
                        this.jumpMultiplier = jumpMultiplier - n7;
                    }
                }
            }
            if (this.lastY > this.player.getBukkitPlayer().getLocation().getY()) {
                this.lastAscendSpeed = 0.0;

            }
            else {
                this.lastAscendSpeed = n;
            }
            this.lastY = this.player.getBukkitPlayer().getLocation().getY();
            this.lastYDifference = n;
       // }
    }
}
