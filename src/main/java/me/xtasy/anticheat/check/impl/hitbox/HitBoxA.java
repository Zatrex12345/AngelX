package me.xtasy.anticheat.check.impl.hitbox;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import org.bukkit.entity.*;
import me.xtasy.anticheat.*;
import me.xtasy.anticheat.data.*;

@CheckData(checkName = "HitBox (Type A)")
public class HitBoxA extends PacketCheck
{
    private double vl;

    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY && this.player.isAttacking() && this.player.getLastDelayedMovePacket() - System.currentTimeMillis() > 1000L) {
            final Entity entity = (Entity)packetEvent.getPacket().getEntityModifier(this.player.getBukkitPlayer().getWorld()).read(0);
            if (entity instanceof Player) {
                final Player player = (Player)entity;
                if (AngelX.getApi().getPlayerDataManager().getData(player).getLastDelayedMovePacket() - System.currentTimeMillis() > 1000L) {
                    final double distance = player.getLocation().distance(this.player.getBukkitPlayer().getLocation());
                    if (this.player.getLastYawChange() < 2.0f && distance > 2.0) {
                        final float angle = player.getLocation().toVector().subtract(this.player.getBukkitPlayer().getLocation().toVector()).setY(0).angle(this.player.getBukkitPlayer().getLocation().getDirection().setY(0));
                        if (angle > 0.8) {
                            final double vl = this.vl + 1.0;
                            this.vl = vl;
                            if (vl > 2.0) {
                                this.onViolation("Angle:" + angle);

                            }
                        }
                        else {
                            this.vl = Math.max(0.0, this.vl - 0.25);
                        }

                    }
                    else {
                        this.vl = 0.0;
                    }
                }
            }
        }
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    public HitBoxA(final PlayerData playerData) {
        super(playerData);
    }
}
