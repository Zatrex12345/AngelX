package me.xtasy.anticheat.util;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.block.*;
import java.util.*;

public class BlockUtil
{
    private static Set<Byte> blockWebsSet;
    private static Set<Byte> blockStairsSet;
    private static Set<Byte> blockIceSet;
    private static Set<Byte> blockSolidPassSet;
    public static List<Material> blocked;
    private static Set<Byte> blockLiquidsSet;
    private static Set<Byte> blockCarpetSet;

    
    public static boolean isOnSlab(final Player player) {
        return BlockUtil.blocked.contains(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType());
    }
    
    public static boolean isOnLiquid(final Location location, final int n) {
        return isUnderBlock(location, BlockUtil.blockLiquidsSet, n);
    }
    
    private static boolean isUnderBlock(final Location location, final Set<Byte> set, final int n) {
        final double x = location.getX();
        final double z = location.getZ();
        double n3;
        if (x % 1.0 > 0.0) {
            final double n2 = n3 = x % 1.0;
            if (n2 < 0.0) {
                n3 = -n2;
            }
        }
        else {
            final double n4 = 1.0;
            double n6;
            final double n5 = n6 = x % 1.0;
            if (n5 < 0.0) {
                n6 = -n5;
            }
            n3 = n4 - n6;
        }
        final double n7 = n3;
        double n9;
        if (z % 1.0 > 0.0) {
            final double n8 = n9 = z % 1.0;
            if (n8 < 0.0) {
                n9 = -n8;
            }

        }
        else {
            final double n10 = 1.0;
            double n12;
            final double n11 = n12 = z % 1.0;
            if (n11 < 0.0) {
                n12 = -n11;
            }
            n9 = n10 - n12;
        }
        final double n13 = n9;
        final int blockX = location.getBlockX();
        final int n14 = location.getBlockY() - n;
        final int blockZ = location.getBlockZ();
        final World world = location.getWorld();
        if (set.contains((byte)world.getBlockAt(blockX, n14, blockZ).getTypeId())) {
            return true;
        }
        if (n7 < 0.3) {
            if (set.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ).getTypeId())) {
                return true;
            }
            if (n13 < 0.3) {
                boolean b;
                if (set.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ - 1).getTypeId()) || set.contains((byte)world.getBlockAt(blockX, n14, blockZ - 1).getTypeId()) || set.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ - 1).getTypeId())) {
                    b = true;

                }
                else {
                    b = false;
                }
                return b;
            }
            if (n13 > 0.7) {
                boolean b2;
                if (set.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ + 1).getTypeId()) || set.contains((byte)world.getBlockAt(blockX, n14, blockZ + 1).getTypeId()) || set.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ + 1).getTypeId())) {
                    b2 = true;

                }
                else {
                    b2 = false;
                }
                return b2;
            }
        }
        else if (n7 > 0.7) {
            if (set.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ).getTypeId())) {
                return true;
            }
            if (n13 < 0.3) {
                boolean b3;
                if (set.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ - 1).getTypeId()) || set.contains((byte)world.getBlockAt(blockX, n14, blockZ - 1).getTypeId()) || set.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ - 1).getTypeId())) {
                    b3 = true;

                }
                else {
                    b3 = false;
                }
                return b3;
            }
            if (n13 > 0.7) {
                boolean b4;
                if (set.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ + 1).getTypeId()) || set.contains((byte)world.getBlockAt(blockX, n14, blockZ + 1).getTypeId()) || set.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ + 1).getTypeId())) {
                    b4 = true;

                }
                else {
                    b4 = false;
                }
                return b4;
            }
        }
        else {
            if (n13 < 0.3) {
                return set.contains((byte)world.getBlockAt(blockX, n14, blockZ - 1).getTypeId());
            }
            boolean b5;
            if (n13 > 0.7 && set.contains((byte)world.getBlockAt(blockX, n14, blockZ + 1).getTypeId())) {
                b5 = true;

            }
            else {
                b5 = false;
            }
            return b5;
        }
        return false;
    }
    
    public static boolean isOnSlabEdge(final Player player) {
        final double n = 0.5;
        double n2 = -n;
        while (n2 <= n) {
            double n3 = -n;
            while (n3 <= n) {
                if (player.getLocation().clone().add(n2, 0.0, n3).getBlock().getType().toString().contains("STEP")) {
                    return true;
                }
                n3 += n;

            }
            n2 += n;

        }
        return false;
    }
    
    public static boolean isOnStairs(final Location location, final int n) {
        return isUnderBlock(location, BlockUtil.blockStairsSet, n);
    }
    
    public static boolean isOnIce(final Location location, final int n) {
        return isUnderBlock(location, BlockUtil.blockIceSet, n);
    }
    
    public static boolean isBlockFaceAir(final Player player) {
        final Block relative = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        boolean b;
        if (relative.getType().equals((Object)Material.AIR) && relative.getRelative(BlockFace.WEST).getType().equals((Object)Material.AIR) && relative.getRelative(BlockFace.NORTH).getType().equals((Object)Material.AIR) && relative.getRelative(BlockFace.EAST).getType().equals((Object)Material.AIR) && relative.getRelative(BlockFace.SOUTH).getType().equals((Object)Material.AIR)) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public static boolean isOnGround(final Location location, final int n) {
        final double x = location.getX();
        final double z = location.getZ();
        double n3;
        if (x % 1.0 > 0.0) {
            final double n2 = n3 = x % 1.0;
            if (n2 < 0.0) {
                n3 = -n2;
            }

        }
        else {
            final double n4 = 1.0;
            double n6;
            final double n5 = n6 = x % 1.0;
            if (n5 < 0.0) {
                n6 = -n5;
            }
            n3 = n4 - n6;
        }
        final double n7 = n3;
        double n9;
        if (z % 1.0 > 0.0) {
            final double n8 = n9 = z % 1.0;
            if (n8 < 0.0) {
                n9 = -n8;
            }

        }
        else {
            final double n10 = 1.0;
            double n12;
            final double n11 = n12 = z % 1.0;
            if (n11 < 0.0) {
                n12 = -n11;
            }
            n9 = n10 - n12;
        }
        final double n13 = n9;
        final int blockX = location.getBlockX();
        final int n14 = location.getBlockY() - n;
        final int blockZ = location.getBlockZ();
        final World world = location.getWorld();
        if (!BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX, n14, blockZ).getTypeId())) {
            return true;
        }
        if (n7 < 0.3) {
            if (!BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ).getTypeId())) {
                return true;
            }
            if (n13 < 0.3) {
                boolean b;
                if (!BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ - 1).getTypeId()) || !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX, n14, blockZ - 1).getTypeId()) || !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ - 1).getTypeId())) {
                    b = true;

                }
                else {
                    b = false;
                }
                return b;
            }
            if (n13 > 0.7) {
                boolean b2;
                if (!BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ + 1).getTypeId()) || !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX, n14, blockZ + 1).getTypeId()) || !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ + 1).getTypeId())) {
                    b2 = true;

                }
                else {
                    b2 = false;
                }
                return b2;
            }
        }
        else {
            if (n7 <= 0.7) {
                boolean b3;
                if (n13 < 0.3) {
                    if (!BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX, n14, blockZ - 1).getTypeId())) {
                        b3 = true;

                    }
                    else {
                        b3 = false;

                    }
                }
                else if (n13 > 0.7 && !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX, n14, blockZ + 1).getTypeId())) {
                    b3 = true;

                }
                else {
                    b3 = false;
                }
                return b3;
            }
            if (!BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ).getTypeId())) {
                return true;
            }
            if (n13 < 0.3) {
                boolean b4;
                if (!BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ - 1).getTypeId()) || !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX, n14, blockZ - 1).getTypeId()) || !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ - 1).getTypeId())) {
                    b4 = true;

                }
                else {
                    b4 = false;
                }
                return b4;
            }
            if (n13 > 0.7) {
                boolean b5;
                if (!BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, n14, blockZ + 1).getTypeId()) || !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX, n14, blockZ + 1).getTypeId()) || !BlockUtil.blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, n14, blockZ + 1).getTypeId())) {
                    b5 = true;

                }
                else {
                    b5 = false;
                }
                return b5;
            }
        }
        return false;
    }
    
    public static boolean isOnCarpet(final Location location, final int n) {
        return isUnderBlock(location, BlockUtil.blockCarpetSet, n);
    }
    
    static {
        BlockUtil.blocked = new ArrayList<Material>();
        BlockUtil.blockSolidPassSet = new HashSet<Byte>();
        BlockUtil.blockStairsSet = new HashSet<Byte>();
        BlockUtil.blockLiquidsSet = new HashSet<Byte>();
        BlockUtil.blockWebsSet = new HashSet<Byte>();
        BlockUtil.blockIceSet = new HashSet<Byte>();
        BlockUtil.blockCarpetSet = new HashSet<Byte>();
/**
        BlockUtil.blockSolidPassSet.add((Byte)0);
        BlockUtil.blockSolidPassSet.add((Byte)6);
        BlockUtil.blockSolidPassSet.add((Byte)8);
        BlockUtil.blockSolidPassSet.add((Byte)9);
        BlockUtil.blockSolidPassSet.add((Byte)10);
        BlockUtil.blockSolidPassSet.add((Byte)11);
        BlockUtil.blockSolidPassSet.add((Byte)27);
        BlockUtil.blockSolidPassSet.add((Byte)28);
        BlockUtil.blockSolidPassSet.add((Byte)30);
        BlockUtil.blockSolidPassSet.add((Byte)31);
        BlockUtil.blockSolidPassSet.add((Byte)32);
        BlockUtil.blockSolidPassSet.add((Byte)37);
        BlockUtil.blockSolidPassSet.add((Byte)38);
        BlockUtil.blockSolidPassSet.add((Byte)39);
        BlockUtil.blockSolidPassSet.add((Byte)40);
        BlockUtil.blockSolidPassSet.add((Byte)50);
        BlockUtil.blockSolidPassSet.add((Byte)51);
        BlockUtil.blockSolidPassSet.add((Byte)55);
        BlockUtil.blockSolidPassSet.add((Byte)59);
        BlockUtil.blockSolidPassSet.add((Byte)63);
        BlockUtil.blockSolidPassSet.add((Byte)66);
        BlockUtil.blockSolidPassSet.add((Byte)68);
        BlockUtil.blockSolidPassSet.add((Byte)69);
        BlockUtil.blockSolidPassSet.add((Byte)70);
        BlockUtil.blockSolidPassSet.add((Byte)72);
        BlockUtil.blockSolidPassSet.add((Byte)75);
        BlockUtil.blockSolidPassSet.add((Byte)76);
        BlockUtil.blockSolidPassSet.add((Byte)77);
        BlockUtil.blockSolidPassSet.add((Byte)78);
        BlockUtil.blockSolidPassSet.add((Byte)83);
        BlockUtil.blockSolidPassSet.add((Byte)90);
        BlockUtil.blockSolidPassSet.add((Byte)104);
        BlockUtil.blockSolidPassSet.add((Byte)105);
        BlockUtil.blockSolidPassSet.add((Byte)115);
        BlockUtil.blockSolidPassSet.add((Byte)119);
        BlockUtil.blockSolidPassSet.add((Byte)(-124));
        BlockUtil.blockSolidPassSet.add((Byte)(-113));
        BlockUtil.blockSolidPassSet.add((Byte)(-81));
        BlockUtil.blockStairsSet.add((Byte)53);
        BlockUtil.blockStairsSet.add((Byte)67);
        BlockUtil.blockStairsSet.add((Byte)108);
        BlockUtil.blockStairsSet.add((Byte)109);
        BlockUtil.blockStairsSet.add((Byte)114);
        BlockUtil.blockStairsSet.add((Byte)(-128));
        BlockUtil.blockStairsSet.add((Byte)(-122));
        BlockUtil.blockStairsSet.add((Byte)(-121));
        BlockUtil.blockStairsSet.add((Byte)(-120));
        BlockUtil.blockStairsSet.add((Byte)(-100));
        BlockUtil.blockStairsSet.add((Byte)(-93));
        BlockUtil.blockStairsSet.add((Byte)(-92));
        BlockUtil.blockStairsSet.add((Byte)(-76));
        BlockUtil.blockStairsSet.add((Byte)126);
        BlockUtil.blockStairsSet.add((Byte)(-74));
        BlockUtil.blockStairsSet.add((Byte)44);
        BlockUtil.blockStairsSet.add((Byte)78);
        BlockUtil.blockStairsSet.add((Byte)99);
        BlockUtil.blockStairsSet.add((Byte)(-112));
        BlockUtil.blockStairsSet.add((Byte)(-115));
        BlockUtil.blockStairsSet.add((Byte)(-116));
        BlockUtil.blockStairsSet.add((Byte)(-105));
        BlockUtil.blockStairsSet.add((Byte)(-108));
        BlockUtil.blockStairsSet.add((Byte)100);

        BlockUtil.blockLiquidsSet.add((Byte)8);
        BlockUtil.blockLiquidsSet.add((Byte)9);
        BlockUtil.blockLiquidsSet.add((Byte)10);
        BlockUtil.blockLiquidsSet.add((Byte)11);
        BlockUtil.blockWebsSet.add((Byte)30);
        BlockUtil.blockIceSet.add((Byte)79);
        BlockUtil.blockIceSet.add((Byte)(-82));
        BlockUtil.blockCarpetSet.add((Byte)(-85));
 **/
        BlockUtil.blocked.add(Material.ACTIVATOR_RAIL);
        BlockUtil.blocked.add(Material.ANVIL);
        BlockUtil.blocked.add(Material.BED_BLOCK);
        BlockUtil.blocked.add(Material.POTATO);
        BlockUtil.blocked.add(Material.POTATO_ITEM);
        BlockUtil.blocked.add(Material.CARROT);
        BlockUtil.blocked.add(Material.CARROT_ITEM);
        BlockUtil.blocked.add(Material.BIRCH_WOOD_STAIRS);
        BlockUtil.blocked.add(Material.BREWING_STAND);
        BlockUtil.blocked.add(Material.BOAT);
        BlockUtil.blocked.add(Material.BRICK_STAIRS);
        BlockUtil.blocked.add(Material.BROWN_MUSHROOM);
        BlockUtil.blocked.add(Material.CAKE_BLOCK);
        BlockUtil.blocked.add(Material.CARPET);
        BlockUtil.blocked.add(Material.CAULDRON);
        BlockUtil.blocked.add(Material.COBBLESTONE_STAIRS);
        BlockUtil.blocked.add(Material.COBBLE_WALL);
        BlockUtil.blocked.add(Material.DARK_OAK_STAIRS);
        BlockUtil.blocked.add(Material.DIODE);
        BlockUtil.blocked.add(Material.DIODE_BLOCK_ON);
        BlockUtil.blocked.add(Material.DIODE_BLOCK_OFF);
        BlockUtil.blocked.add(Material.DEAD_BUSH);
        BlockUtil.blocked.add(Material.DETECTOR_RAIL);
        BlockUtil.blocked.add(Material.DOUBLE_PLANT);
        BlockUtil.blocked.add(Material.DOUBLE_STEP);
        BlockUtil.blocked.add(Material.DRAGON_EGG);
        BlockUtil.blocked.add(Material.PAINTING);
        BlockUtil.blocked.add(Material.FLOWER_POT);
        BlockUtil.blocked.add(Material.GOLD_PLATE);
        BlockUtil.blocked.add(Material.HOPPER);
        BlockUtil.blocked.add(Material.STONE_PLATE);
        BlockUtil.blocked.add(Material.IRON_PLATE);
        BlockUtil.blocked.add(Material.HUGE_MUSHROOM_1);
        BlockUtil.blocked.add(Material.HUGE_MUSHROOM_2);
        BlockUtil.blocked.add(Material.IRON_DOOR_BLOCK);
        BlockUtil.blocked.add(Material.IRON_DOOR);
        BlockUtil.blocked.add(Material.FENCE);
        BlockUtil.blocked.add(Material.IRON_FENCE);
        BlockUtil.blocked.add(Material.IRON_PLATE);
        BlockUtil.blocked.add(Material.ITEM_FRAME);
        BlockUtil.blocked.add(Material.JUKEBOX);
        BlockUtil.blocked.add(Material.JUNGLE_WOOD_STAIRS);
        BlockUtil.blocked.add(Material.LADDER);
        BlockUtil.blocked.add(Material.LEVER);
        BlockUtil.blocked.add(Material.LONG_GRASS);
        BlockUtil.blocked.add(Material.NETHER_FENCE);
        BlockUtil.blocked.add(Material.NETHER_STALK);
        BlockUtil.blocked.add(Material.NETHER_WARTS);
        BlockUtil.blocked.add(Material.MELON_STEM);
        BlockUtil.blocked.add(Material.PUMPKIN_STEM);
        BlockUtil.blocked.add(Material.QUARTZ_STAIRS);
        BlockUtil.blocked.add(Material.RAILS);
        BlockUtil.blocked.add(Material.RED_MUSHROOM);
        BlockUtil.blocked.add(Material.RED_ROSE);
        BlockUtil.blocked.add(Material.SAPLING);
        BlockUtil.blocked.add(Material.SEEDS);
        BlockUtil.blocked.add(Material.SIGN);
        BlockUtil.blocked.add(Material.SIGN_POST);
        BlockUtil.blocked.add(Material.SKULL);
        BlockUtil.blocked.add(Material.SMOOTH_STAIRS);
        BlockUtil.blocked.add(Material.NETHER_BRICK_STAIRS);
        BlockUtil.blocked.add(Material.SPRUCE_WOOD_STAIRS);
        BlockUtil.blocked.add(Material.STAINED_GLASS_PANE);
        BlockUtil.blocked.add(Material.REDSTONE_COMPARATOR);
        BlockUtil.blocked.add(Material.REDSTONE_COMPARATOR_OFF);
        BlockUtil.blocked.add(Material.REDSTONE_COMPARATOR_ON);
        BlockUtil.blocked.add(Material.REDSTONE_LAMP_OFF);
        BlockUtil.blocked.add(Material.REDSTONE_LAMP_ON);
        BlockUtil.blocked.add(Material.REDSTONE_TORCH_OFF);
        BlockUtil.blocked.add(Material.REDSTONE_TORCH_ON);
        BlockUtil.blocked.add(Material.REDSTONE_WIRE);
        BlockUtil.blocked.add(Material.SANDSTONE_STAIRS);
        BlockUtil.blocked.add(Material.STEP);
        BlockUtil.blocked.add(Material.ACACIA_STAIRS);
        BlockUtil.blocked.add(Material.SUGAR_CANE);
        BlockUtil.blocked.add(Material.SUGAR_CANE_BLOCK);
        BlockUtil.blocked.add(Material.ENCHANTMENT_TABLE);
        BlockUtil.blocked.add(Material.SOUL_SAND);
        BlockUtil.blocked.add(Material.TORCH);
        BlockUtil.blocked.add(Material.TRAP_DOOR);
        BlockUtil.blocked.add(Material.TRIPWIRE);
        BlockUtil.blocked.add(Material.TRIPWIRE_HOOK);
        BlockUtil.blocked.add(Material.WALL_SIGN);
        BlockUtil.blocked.add(Material.VINE);
        BlockUtil.blocked.add(Material.WATER_LILY);
        BlockUtil.blocked.add(Material.WEB);
        BlockUtil.blocked.add(Material.WOOD_DOOR);
        BlockUtil.blocked.add(Material.WOOD_DOUBLE_STEP);
        BlockUtil.blocked.add(Material.WOOD_PLATE);
        BlockUtil.blocked.add(Material.WOOD_STAIRS);
        BlockUtil.blocked.add(Material.WOOD_STEP);
        BlockUtil.blocked.add(Material.HOPPER);
        BlockUtil.blocked.add(Material.WOODEN_DOOR);
        BlockUtil.blocked.add(Material.YELLOW_FLOWER);
        BlockUtil.blocked.add(Material.LAVA);
        BlockUtil.blocked.add(Material.WATER);
        BlockUtil.blocked.add(Material.STATIONARY_WATER);
        BlockUtil.blocked.add(Material.STATIONARY_LAVA);
        BlockUtil.blocked.add(Material.CACTUS);
        BlockUtil.blocked.add(Material.CHEST);
        BlockUtil.blocked.add(Material.PISTON_BASE);
        BlockUtil.blocked.add(Material.PISTON_MOVING_PIECE);
        BlockUtil.blocked.add(Material.PISTON_EXTENSION);
        BlockUtil.blocked.add(Material.PISTON_STICKY_BASE);
        BlockUtil.blocked.add(Material.TRAPPED_CHEST);
        BlockUtil.blocked.add(Material.SNOW);
        BlockUtil.blocked.add(Material.ENDER_CHEST);
        BlockUtil.blocked.add(Material.THIN_GLASS);
        BlockUtil.blocked.add(Material.ENDER_PORTAL_FRAME);
    }
    
    public static boolean isOnWeb(final Location location, final int n) {
        return isUnderBlock(location, BlockUtil.blockWebsSet, n);
    }
}
