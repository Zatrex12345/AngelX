package me.xtasy.anticheat.check.impl.sprint;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.potion.*;
import me.xtasy.anticheat.util.player.*;

@CheckData(checkName = "Sprint (Type D)")
public class SprintD extends PositionCheck
{
    private double vl;

    public SprintD(final PlayerData playerData) {
        super(playerData);
    }
    

    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        if (this.player.getTeleportTicks() > 0 || this.player.getLoginTicks() > 0 || this.player.isOnIce() || this.player.getOnIceTicks() > 0 || this.player.getBukkitPlayer().getWalkSpeed() > 0.20000000298023224 || PlayerUtils.getPotionEffectLevel(this.player.getBukkitPlayer(), PotionEffectType.SPEED) > 3) {
            return;
        }
        final double speedXZ = this.player.getSpeedXZ();
        final double n = (PlayerUtils.getPotionEffectLevel(this.player.getBukkitPlayer(), PotionEffectType.SPEED) + 5) * 0.059 - 0.06;
        if (this.player.isOnSoulSand() && speedXZ > 0.1618 && speedXZ > n && this.player.isOnGround()) {
            final double vl = this.vl + 1.0;
            this.vl = vl;
            if (vl > 3.0) {
                this.onViolation("Walking fast on Soulsand");

            }
        }
        else {
            this.vl = Math.max(0.0, this.vl - 0.4);
        }
    }
}
