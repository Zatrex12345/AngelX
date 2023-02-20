package me.xtasy.anticheat.check.impl.groundspoof;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.*;
import me.xtasy.anticheat.util.player.*;
import org.bukkit.entity.*;
import java.util.*;

@CheckData(checkName = "GroundSpoof (Type B)")
public class GroundSpoofB extends PositionCheck
{
    private int airTicks;
    private int vl;

    
    public GroundSpoofB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void run(final PositionUpdate positionUpdate) {
        final long n = System.currentTimeMillis() - this.player.getLastOnBadBlock();
        final long n2 = System.currentTimeMillis() - this.player.getLastPlaceCancel();
        boolean b;
        if (this.player.isOnPiston() || this.player.isWasOnPiston()) {
            b = true;
        }
        else {
            b = false;
        }
        final boolean b2 = b;
        final boolean onSlabEdge = BlockUtil.isOnSlabEdge(this.player.getBukkitPlayer());
        if (this.player.getPacketPlayOutPositionTicks() > 0) {
            return;
        }
        if (n < 1000L || n2 < 1000L) {
            return;
        }
        if (b2 || onSlabEdge || this.player.isOnStairs()) {
            return;
        }
        if (!PlayerUtils.isNearGround(this.player.getBukkitPlayer().getLocation()) && ++this.airTicks > 3) {
            if (this.player.getBukkitPlayer().isOnGround()) {
                final Iterator<Entity> iterator = (Iterator<Entity>)this.player.getBukkitPlayer().getNearbyEntities(2.0, 2.0, 2.0).iterator();
                while (iterator.hasNext()) {
                    if (!(iterator.next() instanceof Boat)) {
                        if (++this.vl > 4) {
                            this.onViolation("Invalid State");

                        }
                    }
                    else {
                        this.vl = 0;
                    }

                }

            }
            else {
                this.airTicks = 0;
                this.vl = 0;
            }
        }
    }
    

}
