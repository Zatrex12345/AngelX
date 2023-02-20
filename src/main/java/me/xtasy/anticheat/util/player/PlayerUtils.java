package me.xtasy.anticheat.util.player;

import java.lang.reflect.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.potion.*;
import java.util.*;

public class PlayerUtils
{
    private static final Class ENTITY_PLAYER_CLASS;
    private static final String CRAFT_BUKKIT_PACKAGE;
    private static final Class CRAFT_PLAYER_CLASS;
    private static final String NET_MINECRAFT_SERVER_PACKAGE;
    private static final Method CRAFT_PLAYER_GET_HANDLE_METHOD;
    private static final Field ENTITY_PLAYER_PING_FIELD;

    
    public static boolean isNearGround(final Location location) {
        final double n = 0.3;
        double n2 = -n;
        while (n2 <= n) {
            double n3 = -n;
            while (n3 <= n) {
                if (location.clone().add(n2, -0.5001, n3).getBlock().getType() != Material.AIR) {
                    return true;
                }
                n3 += n;

            }
            n2 += n;

        }
        return false;
    }
    
    public static int getPing(final Player player) {
        try {
            return PlayerUtils.ENTITY_PLAYER_PING_FIELD.getInt(PlayerUtils.CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(player, new Object[0]));
        }
        catch (Exception ex) {
            return 1;
        }
    }
    
    public static boolean isHalfOnWaterHalfOnAir(final Player player) {
        final Location location = player.getLocation();
        final int blockX = location.getBlockX();
        final int blockY = location.getBlockY();
        final int blockZ = location.getBlockZ();
        final Location location2 = new Location(player.getWorld(), (double)blockX, (double)(blockY + 2), (double)blockZ);
        final Location location3 = new Location(player.getWorld(), (double)blockX, (double)(blockY - 1), (double)blockZ);
        return player.getWorld().isChunkLoaded(player.getWorld().getChunkAt(location3)) && player.getWorld().isChunkLoaded(player.getWorld().getChunkAt(location2)) && player.getWorld().isChunkLoaded(location3.getBlockX() >> 4, location3.getBlockZ() >> 4) && player.getWorld().isChunkLoaded(location2.getBlockX() >> 4, location2.getBlockZ() >> 4) && (location2.getBlock().isLiquid() && location3.getBlock().getType() == Material.AIR);
    }
    
    static {
        try {
            final String s = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            CRAFT_BUKKIT_PACKAGE = "org.bukkit.craftbukkit." + s + ".";
            NET_MINECRAFT_SERVER_PACKAGE = "net.minecraft.server." + s + ".";
            CRAFT_PLAYER_CLASS = Class.forName(PlayerUtils.CRAFT_BUKKIT_PACKAGE + "entity.CraftPlayer");
            (CRAFT_PLAYER_GET_HANDLE_METHOD = PlayerUtils.CRAFT_PLAYER_CLASS.getDeclaredMethod("getHandle", (Class[])new Class[0])).setAccessible(true);
            ENTITY_PLAYER_CLASS = Class.forName(PlayerUtils.NET_MINECRAFT_SERVER_PACKAGE + "EntityPlayer");
            (ENTITY_PLAYER_PING_FIELD = PlayerUtils.ENTITY_PLAYER_CLASS.getDeclaredField("ping")).setAccessible(true);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to initialize Bukkit/NMS Reflection");
        }
    }
    
    public static int getPotionAmplifier(final Player player, final PotionEffectType potionEffectType) {
        if (player.hasPotionEffect(potionEffectType)) {
            for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
                if (potionEffect.getType().equals((Object)potionEffectType)) {
                    return potionEffect.getAmplifier() + 1;
                }

            }
        }
        return 0;
    }
    
    public static int getPotionEffectLevel(final Player player, final PotionEffectType potionEffectType) {
        for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().getName().equals(potionEffectType.getName())) {
                return potionEffect.getAmplifier() + 1;
            }

        }
        return 0;
    }
    
    public static String pingColor(final Player player) {
        String s;
        if (getPing(player) <= 50) {
            s = "�2";

        }
        else if (getPing(player) <= 100) {
            s = "�a";

        }
        else if (getPing(player) <= 150) {
            s = "�e";

        }
        else if (getPing(player) <= 200) {
            s = "�6";

        }
        else if (getPing(player) <= 350) {
            s = "�c";

        }
        else {
            s = "�4";
        }
        return s;
    }
}
