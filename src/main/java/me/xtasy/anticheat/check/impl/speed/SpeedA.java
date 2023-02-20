package me.xtasy.anticheat.check.impl.speed;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import java.util.*;
import me.xtasy.anticheat.update.impl.*;
import java.util.function.*;
import me.xtasy.anticheat.data.*;
import org.bukkit.*;

@CheckData(checkName = "Speed (Type A)")
public class SpeedA extends PositionCheck
{
    private int threshold;
    private double blockFriction;
    private double previousDistance;
    private int liquidTicks;

    
    private int getJumpBoostAmplifier(final Player player) {
        if (player.hasPotionEffect(PotionEffectType.JUMP)) {
            for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
                if (potionEffect.getType().equals((Object)PotionEffectType.JUMP)) {
                    return potionEffect.getAmplifier() + 1;
                }

            }
        }
        return 0;
    }
    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        boolean b;
        if (this.player.isOnStairs() || this.player.isWasOnStairs()) {
            b = true;
      
        }
        else {
            b = false;
        }
        final boolean b2 = b;
        final boolean equals = this.player.getBukkitPlayer().getGameMode().equals((Object)GameMode.CREATIVE);
        final int toggleFlyTicks = this.player.getToggleFlyTicks();
        final int bedTicks = this.player.getBedTicks();
        final int underBlockTicks = this.player.getUnderBlockTicks();
        final int onIceTicks = this.player.getOnIceTicks();
        final int flyingTicks = this.player.getFlyingTicks();
        final int glideTicks = this.player.getGlideTicks();
        final boolean insideVehicle = this.player.getBukkitPlayer().isInsideVehicle();
        final boolean flying = this.player.getBukkitPlayer().isFlying();
        if (b2 || flyingTicks > 0 || equals || flying || toggleFlyTicks > 0 || insideVehicle || bedTicks > 0 || underBlockTicks > 0 || onIceTicks > 0 || glideTicks > 0) {
            this.threshold = 0;
            return;
        }
        final Location to = positionUpdate.getTo();
        final Location from = positionUpdate.getFrom();
        final double n = 1.0;
        final double blockFriction = this.blockFriction;
        boolean b3;
        if (this.player.isOnGround() || this.player.getBukkitPlayer().isOnGround()) {
            b3 = true;
         
        }
        else {
            b3 = false;
        }
        final boolean b4 = b3;
        final double n2 = to.getY() - from.getY();
        boolean b5;
        if (this.player.isUnderBlock() || this.player.isWasUnderBlock()) {
            b5 = true;
         
        }
        else {
            b5 = false;
        }
        final boolean b6 = b5;
        boolean b7;
        if (this.player.isInLiquid() || this.player.isWasInLiquid()) {
            b7 = true;
         
        }
        else {
            b7 = false;
        }
        final boolean b8 = b7;
        if (b8 && this.player.getBukkitPlayer().getEquipment().getBoots() != null && this.player.getBukkitPlayer().getEquipment().getBoots().getEnchantments().size() > 0 && this.player.getBukkitPlayer().getEquipment().getBoots().getEnchantments().toString().contains("DEPTH_STRIDER")) {
            return;
        }
        boolean b9;
        if (this.player.isOnBadBlock() || this.player.isWasOnBadBlock()) {
            b9 = true;

        }
        else {
            b9 = false;
        }
        final boolean b10 = b9;
        double n3;
        if (b6) {
            n3 = 3.2;
         
        }
        else {
            n3 = 1.0;
        }
        final double n4 = n3;
        final int toggleFlyTicks2 = this.player.getToggleFlyTicks();
        if (toggleFlyTicks2 > 0) {
            this.player.setToggleFlyTicks(toggleFlyTicks2 - 1);
        }
        double n5;
        double n10;
        if (b4) {
            n5 = blockFriction * 0.91;
            final double n6 = n;
            double n7;
            if (n5 > 0.708) {
                n7 = 1.3;
            
            }
            else {
                n7 = 0.23315;
            }
            final double n8;
            double n9;
            if ((n8 = n6 * n7 * (0.16277136 / Math.pow(n5, 3.0))) > 0.4199) {
                n9 = -0.2;
         
            }
            else {
                n9 = 0.1055;
            }
            n10 = n8 - n9;
      
        }
        else {
            n10 = 0.026;
            n5 = 0.91;
        }
        if (toggleFlyTicks2 > 0) {
            n10 *= toggleFlyTicks2 * 3.0f;
        }
        if (b8) {
            final double n11 = n10;
            double n12;
            if (++this.liquidTicks < 8) {
                if (this.liquidTicks % 2 != 0) {
                    n12 = this.liquidTicks;
         
                }
                else {
                    n12 = this.liquidTicks * 0.45;
                
                }
            }
            else if (this.player.getBukkitPlayer().isSprinting()) {
                n12 = 0.75;
       
            }
            else {
                n12 = 0.65;
            }
            n10 = n11 * n12;
       
        }
        else {
            this.liquidTicks = 0;
        }
        final double n13 = to.getX() - from.getX();
        final double n14 = to.getZ() - from.getZ();
        final double sqrt = Math.sqrt(n13 * n13 + n14 * n14);
        if (b6) {
            n10 += 0.05;
        }
        final double previousDistance = this.previousDistance;
        double n15 = n10 + this.player.getVelocityManager().getMaxHorizontal();
        final double n16 = (sqrt - previousDistance) / n15;
        final double n17 = sqrt;
        double n18;
        if (this.liquidTicks > 0) {
            n18 = 0.15;
   
        }
        else {
            n18 = 0.286;
        }
        boolean b11;
        if (n17 > n18) {
            b11 = true;
       
        }
        else {
            b11 = false;
        }
        final boolean b12 = b11;
        if (b8) {
            n15 += 0.05;
        }
        if (b12 && sqrt - previousDistance > n15) {
            double n19 = n16 * 0.98;
            if (this.player.getBukkitPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
                n19 -= (this.player.getBukkitPlayer().getActivePotionEffects().stream().filter(SpeedA::run).findFirst().orElseGet(null).getAmplifier() + 1) * 0.21;
            }
            final double n20 = n19 - n4 / n5;
            final double n21 = this.player.getBukkitPlayer().getWalkSpeed();
            if (n20 > 2.0 && sqrt < 0.5) {
                n19 *= 0.1;
            }
            if (n19 > n4 && n21 <= 0.20000000298023224 && n2 != 0.41999998688697815 && (n19 < 3.246 || n19 > 3.259)) {
                final int threshold = (int)(this.threshold + n19 * 10.0);
                this.threshold = threshold;
                int n22;
                if (b10) {
                    n22 = 350;
               
                }
                else {
                    n22 = 250;
                }
                if (threshold > n22) {
                    this.onViolation("Speed");
                }
            }
        }
        if (this.threshold > 0) {
            if (this.getJumpBoostAmplifier(this.player.getBukkitPlayer()) > 0) {
                final int threshold2 = 0;
                final int n23;
                if (threshold2 < (n23 = this.threshold - 5)) {}
                this.threshold = threshold2;
            
            }
            else {
                final int threshold3 = 0;
                final int threshold4 = this.threshold;
                int n24;
                if (b10) {
                    n24 = 4;
               
                }
                else {
                    n24 = 1;
                }
                final int n25;
                if (threshold3 < (n25 = threshold4 - n24)) {}
                this.threshold = threshold3;
            }
            if (this.threshold > 300) {
                this.threshold = 300;
            }
        }
        double n26;
        if (this.hasSpecialFriction(to)) {
            n26 = 1.0;
        
        }
        else {
            n26 = 0.6;
        }
        final double blockFriction2 = n26;
        this.previousDistance = sqrt * blockFriction2;
        this.blockFriction = blockFriction2;
    }
    
    public SpeedA(final PlayerData playerData) {
        super(playerData);
        this.blockFriction = 0.91;
    }
    

    
    private boolean hasSpecialFriction(final Location location) {
        final Material type = location.getWorld().getBlockAt(location.clone().subtract(0.0, 1.0, 0.0)).getType();
        boolean b;
        if (type == Material.PACKED_ICE || type == Material.ICE || type == Material.CARPET) {
            b = true;
      
        }
        else {
            b = false;
        }
        boolean b2;
        if (b || type == Material.SLIME_BLOCK) {
            b2 = true;
         
        }
        else {
            b2 = false;
        }
        return b2;
    }
    
    private static boolean run(final PotionEffect potionEffect) {
        return potionEffect.getType().equals((Object)PotionEffectType.SPEED);
    }
}
