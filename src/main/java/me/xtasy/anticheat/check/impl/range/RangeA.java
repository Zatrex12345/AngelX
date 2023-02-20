package me.xtasy.anticheat.check.impl.range;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import me.xtasy.anticheat.handler.impl.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.xtasy.anticheat.util.range.*;
import org.bukkit.inventory.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.util.location.*;

@CheckData(checkName = "Range (Type A)")
public class RangeA extends PacketCheck
{
    private double vl;

    @Override
    public void run(final PacketEvent packetEvent) {
        if (PacketHandler.isFlying(packetEvent.getPacketType())) {
            if (this.player.getBukkitPlayer().getVehicle() != null) {
                return;
            }
            if (this.player.getBukkitPlayer().getGameMode().equals((Object)GameMode.CREATIVE) || this.player.getBukkitPlayer().isInsideVehicle()) {
                return;
            }
            final ItemStack itemInHand = this.player.getBukkitPlayer().getItemInHand();
            if (itemInHand != null && itemInHand.getType() == Material.FISHING_ROD) {
                this.vl = 0.0;
                return;
            }
            if (this.player.canRangeCheck() && !this.player.getBukkitPlayer().isSneaking() && this.player.isOnGround() && this.player.getLastHitEntity() != null && this.player.getLastHitEntity().getType() == EntityType.PLAYER) {
                final PlayerData lastPlayerHit = this.player.getLastPlayerHit();
                if (lastPlayerHit.lagCheck() && this.player.lagCheck() && !lastPlayerHit.getBukkitPlayer().isSneaking() && !this.player.hasLag(500L)) {
                    final CustomLocation currentLocation = this.player.getCurrentLocation();
                    final CustomLocation lastLocation = this.player.getLastLocation();
                    if (currentLocation != null || lastLocation != null) {
                        final double range = RangeUtils.getRange(lastPlayerHit, currentLocation, lastLocation);
                        final double n = (double)(System.currentTimeMillis() - this.player.getLastDelayedMovePacket());
                        final double n2 = (double)(System.currentTimeMillis() - lastPlayerHit.getLastDelayedMovePacket());
                        if (n < 1001.0 || n2 < 1001.0) {
                            return;
                        }
                        if (range > 3.1 && range <= 6.0) {
                            final double vl = this.vl + 1.0;
                            this.vl = vl;
                            if (vl > 1.0) {
                                this.onViolation("Far range hit " + range);

                            }
                        }
                        else {
                            this.vl = Math.max(0.0, this.vl - 0.25);
                        }
                    }
                }
            }
        }
    }
    
    public RangeA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
}
