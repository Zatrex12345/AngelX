package me.xtasy.anticheat.check.impl.step;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.potion.*;
import me.xtasy.anticheat.util.player.*;
import org.bukkit.entity.*;
import java.util.*;

@CheckData(checkName = "Step (Type A)")
public class StepA extends PositionCheck
{

    public StepA(final PlayerData playerData) {
        super(playerData);
    }
    

    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        if (!this.player.lagCheck() || this.player.getBukkitPlayer().isFlying() || this.player.getPacketPlayOutPositionTicks() > 0 || this.player.getVelocityManager().getMaxVertical() > 0.0 || this.player.getLoginTicks() > 0 || this.player.getToggleFlyTicks() > 0 || this.player.getSlimeTicks() > 0 || PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.JUMP) > 2 || this.player.isOnPiston() || this.player.isWasOnPiston() || this.player.isOnCarpet() || this.player.getBukkitPlayer().getVehicle() != null || this.player.getFlyingTicks() > 0 || this.player.getBukkitPlayer().isInsideVehicle() || this.player.getBukkitPlayer().isSleeping() || this.player.getTeleportTicks() > 0 || this.player.getGlideTicks() > 0) {
            return;
        }
        final Iterator<Entity> iterator = this.player.getBukkitPlayer().getNearbyEntities(2.0, 2.0, 2.0).iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof Boat) {
                return;
            }

        }
        if (positionUpdate.getTo().getY() - positionUpdate.getFrom().getY() > 0.6 + PlayerUtils.getPotionAmplifier(this.player.getBukkitPlayer(), PotionEffectType.JUMP) * 0.1) {
            this.onViolation("High motionY compared to predicted");
        }
    }
}
