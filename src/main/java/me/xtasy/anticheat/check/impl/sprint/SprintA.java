package me.xtasy.anticheat.check.impl.sprint;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.potion.*;
import me.xtasy.anticheat.util.player.*;
import me.xtasy.anticheat.data.*;
import org.bukkit.util.*;
import org.bukkit.*;

@CheckData(checkName = "Sprint (Type A)")
public class SprintA extends PositionCheck
{
    private double vl;


    private void doOmniSprint(final double n) {
        if (this.player.getLoginTicks() > 0) {
            return;
        }
        if (this.player.getOnIceTicks() > 0) {
            return;
        }
        if (this.player.getSoulSandTicks() > 0) {
            return;
        }
        final Material type = this.player.getBukkitPlayer().getItemInHand().getType();
        if (type != null && type.equals((Object)Material.FISHING_ROD)) {
            return;
        }
        final double n2 = 3.5 - PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.SPEED) * 0.6;
        if (this.player.getBukkitPlayer().isSprinting()) {
            if (n > n2) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 15.0) {
                    this.onViolation("Omni");

                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 1.0);

            }
        }
        else {
            this.vl = 0.0;
        }
    }
    
    public SprintA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        final long n = System.currentTimeMillis() - this.player.getLastOnBadBlock();
        if (this.player.getBukkitPlayer().getVehicle() != null || this.player.getBukkitPlayer().isDead() || PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.SPEED) > 3 || this.player.getPacketPlayOutPositionTicks() > 0 || this.player.getVelocityManager().getMaxHorizontal() > 0.0 || this.player.getBukkitPlayer().getGameMode().equals((Object)GameMode.CREATIVE) || this.player.getToggleFlyTicks() > 0 || n < 1000L || this.player.getTeleportTicks() > 0 || this.player.isOnSlab()) {
            return;
        }
        if (this.player.getTeleportTicks() > 0) {
            this.vl = 0.0;
        }
        final Location to = positionUpdate.getTo();
        final Location from = positionUpdate.getFrom();
        final double n2 = to.getX() - from.getX();
        final double n3 = to.getZ() - from.getZ();
        final float yaw = this.player.getBukkitPlayer().getEyeLocation().getYaw();
        final double n4 = new Vector(n2, 0.0, n3).distanceSquared(new Vector(-Math.sin(yaw * 3.1415927f / 180.0f), 0.0, Math.cos(yaw * 3.1415927f / 180.0f))) / this.player.getSpeedXZ();
        if (this.player.getSpeedXZ() > 0.2 && this.player.isOnGround()) {
            this.doOmniSprint(n4);
        }
    }
}
