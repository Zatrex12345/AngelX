package me.xtasy.anticheat.check.impl.hitbox;

import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.data.*;
import me.xtasy.anticheat.data.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.*;
import com.comphenix.protocol.wrappers.*;
import org.bukkit.entity.*;
import org.bukkit.util.*;

@CheckData(checkName = "HitBox (Type B)")
public class HitBoxB extends PacketCheck
{
    float maxExpand;
    
    public HitBoxB(final PlayerData playerData) {
        super(playerData);
        this.maxExpand = 0.4f;
    }
    
    @Override
    public void run(final Object o) {
        this.run((PacketEvent)o);
    }
    
    @Override
    public void run(final PacketEvent packetEvent) {
        if (packetEvent.getPacketType() == PacketType.Play.Client.USE_ENTITY && packetEvent.getPacket().getEntityUseActions().read(0) == EnumWrappers.EntityUseAction.INTERACT_AT) {
            final Entity entity = (Entity)packetEvent.getPacket().getEntityModifier(this.player.getBukkitPlayer().getWorld()).read(0);
            if (entity == null) {
                return;
            }
            if (entity.getType() == EntityType.PLAYER) {
                final double x = ((Vector)packetEvent.getPacket().getVectors().read(0)).getX();
                final double z = ((Vector)packetEvent.getPacket().getVectors().read(0)).getZ();
                double n2;
                final double n = n2 = x;
                if (n < 0.0) {
                    n2 = -n;
                }
                double n4;
                final double n3 = n4 = z;
                if (n3 < 0.0) {
                    n4 = -n3;
                }
                final double max = Math.max(n2, n4);
                double n6;
                final double n5 = n6 = x;
                if (n5 < 0.0) {
                    n6 = -n5;
                }
                if (n6 <= this.maxExpand) {
                    double n8;
                    final double n7 = n8 = z;
                    if (n7 < 0.0) {
                        n8 = -n7;
                    }
                    if (n8 <= this.maxExpand) {
                        return;
                    }
                }
                this.onViolation("Expand: " + max + "&8o(c" + Math.round(max * 100.0 / this.maxExpand) + "%&8&o)");
            }
        }
    }
}
