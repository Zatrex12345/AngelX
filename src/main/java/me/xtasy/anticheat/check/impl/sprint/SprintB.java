package me.xtasy.anticheat.check.impl.sprint;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import org.bukkit.potion.*;
import me.xtasy.anticheat.util.player.*;

@CheckData(checkName = "Sprint (Type B)")
public class SprintB extends PositionCheck
{
    private double streak;
    private double lastspeed;

    public SprintB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        if (System.currentTimeMillis() - this.player.getLastAttackPacket() >= 90L || this.player.getTeleportTicks() > 0 || this.player.getBukkitPlayer().getWalkSpeed() > 0.20000000298023224 || this.player.getBukkitPlayer().isFlying()) {
            return;
        }
        double speedXZ = this.player.getSpeedXZ();
        final double n = (PlayerUtils.getPotionEffectLevel(this.player.getBukkitPlayer(), PotionEffectType.SPEED) + 5) * 0.059 - 0.06;
        if (this.player.isEnemyisplayer() && this.player.isOnGround()) {
            if (speedXZ > n && this.lastspeed > n) {
                final double streak = this.streak;
                this.streak = streak + 1.0;
                if (streak > 3.0) {
                    this.onViolation("KeepSprint");
                }

            }
            else if (this.lastspeed > 0.0) {
                speedXZ = 0.0;
                this.streak -= 0.3;
            }
            this.lastspeed = speedXZ;
        }
    }
    

}
