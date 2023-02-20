package me.xtasy.anticheat.check.impl.sprint;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.potion.*;
import me.xtasy.anticheat.util.player.*;

@CheckData(checkName = "Sprint (Type C)")
public class SprintC extends PositionCheck
{
    private double vl;

    public SprintC(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        if (this.player.getTeleportTicks() > 0 || this.player.getLoginTicks() > 0 || this.player.isOnIce() || this.player.getOnIceTicks() > 0 || this.player.getBukkitPlayer().getWalkSpeed() > 0.20000000298023224 || PlayerUtils.getPotionEffectLevel(this.player.getBukkitPlayer(), PotionEffectType.SPEED) > 3 || !this.player.lagCheck()) {
            return;
        }
        final double speedXZ = this.player.getSpeedXZ();
        final double n = (PlayerUtils.getPotionEffectLevel(this.player.getBukkitPlayer(), PotionEffectType.SPEED) + 5) * 0.059 - 0.06;
        if (this.player.getBukkitPlayer().isBlocking() && speedXZ > 0.28 && speedXZ > n && this.player.isOnGround() && !this.player.getBukkitPlayer().isFlying()) {
            final double vl = this.vl + 1.0;
            this.vl = vl;
            if (vl > 3.0) {
                this.onViolation("Blocking while walking (experimental check)");

            }
        }
        else {
            this.vl = Math.max(0.0, this.vl - 0.4);
        }
    }
    

}
