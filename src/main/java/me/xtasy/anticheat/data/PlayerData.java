package me.xtasy.anticheat.data;

import com.google.common.collect.EvictingQueue;
import me.xtasy.anticheat.check.handler.ChecksManager;
import me.xtasy.anticheat.util.location.CustomLocation;
import me.xtasy.anticheat.util.player.PlayerUtils;
import me.xtasy.anticheat.util.velocity.handler.VelocityManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.Queue;

public class PlayerData
{
    private int underBlockTicks;
    private int toggleFlyTicks;
    private long lastFlying;
    private int glideTicks;
    private double velocityY;
    private int BedTicks;
    private int teleportTicks;
    private boolean onStairs;
    private boolean headInLiquid;
    private final Player bukkitPlayer;
    private boolean onBadBlock;
    private boolean movedSinceTeleport;
    private boolean wasOnStairs;
    private int packetPlayOutPositionTicks;
    private boolean alerts;
    private int clientVersion;
    private long lastLag;
    private final VelocityManager velocityManager;
    private boolean interacting;
    private int lastAttackTick;
    private boolean onSoulSand;
    private long lastAttackPacket;
    private boolean onPiston;
    private long lastDelayedMovePacket;
    private final ChecksManager checkRegistry;
    private float lastYawChange;
    private int loginTicks;
    private WeakReference<PlayerData> lastPlayerHit;
    private boolean wasOnPiston;
    private CustomLocation currentLocation;
    private boolean inWeb;
    private boolean digging;
    private boolean wasUnderBlock;
    private int standTicks;
    private double speedXZ;
    private boolean swinging;
    private int onIceTicks;
    private long lastBlockPlace;
    private boolean movedSinceLogin;
    private boolean wasInWeb;
    private Queue<CustomLocation> pastPositions;
    private boolean onIce;
    private boolean onRail;
    private boolean enemyisplayer;
    private boolean headWasInLiquid;
    private boolean underBlock;
    private boolean onSnow;
    private int protocolVersion;
    private long lastOnBadBlock;
    private boolean inventoryOpen;
    private boolean onCarpet;
    private boolean wasOnBadBlock;
    private boolean placing;
    private boolean wasInLiquid;
    private long lastPlaceCancel;
    private int pistonPushTicks;
    private CustomLocation lastLocation;
    private Entity lastHitEntity;
    private boolean onGround;
    private boolean attacking;
    private long lastDelayed;
    private boolean inLiquid;
    private boolean wasOnGround;
    private boolean onLilyPad;
    private int FlyingTicks;
    private int sprintTicks;
    private boolean onSlab;
    private int soulSandTicks;
    private int slimeTicks;

    public int getTeleportTicks() {
        return this.teleportTicks;
    }
    
    public int getPistonPushTicks() {
        return this.pistonPushTicks;
    }
    
    public boolean isWasOnGround() {
        return this.wasOnGround;
    }
    
    public boolean isWasOnPiston() {
        return this.wasOnPiston;
    }
    
    public boolean isWasOnBadBlock() {
        return this.wasOnBadBlock;
    }
    
    public boolean isEqOrBelow(final int n) {
        boolean b;
        if (this.protocolVersion <= n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public void setLastDelayed(final long lastDelayed) {
        this.lastDelayed = lastDelayed;
    }
    
    public Queue<CustomLocation> getPastPositions() {
        return this.pastPositions;
    }
    
    public void setOnLilyPad(final boolean onLilyPad) {
        this.onLilyPad = onLilyPad;
    }
    
    public void setWasInWeb(final boolean wasInWeb) {
        this.wasInWeb = wasInWeb;
    }
    
    public void setLastOnBadBlock(final long lastOnBadBlock) {
        this.lastOnBadBlock = lastOnBadBlock;
    }
    
    public long getLastBlockPlace() {
        return this.lastBlockPlace;
    }
    
    public void setLastPlayerHit(final PlayerData playerData) {
        this.lastPlayerHit = new WeakReference<PlayerData>(playerData);
    }
    
    public void setUnderBlockTicks(final int underBlockTicks) {
        this.underBlockTicks = underBlockTicks;
    }
    
    public void updateFlags(final Location location) {
        final World world = this.getBukkitPlayer().getWorld();
        final int locToBlock = Location.locToBlock(location.getX() - 0.3);
        final int locToBlock2 = Location.locToBlock(location.getX() + 0.3);
        final int locToBlock3 = Location.locToBlock(location.getZ() - 0.3);
        final int locToBlock4 = Location.locToBlock(location.getZ() + 0.3);
        this.headWasInLiquid = this.headInLiquid;
        this.headInLiquid = false;
        int i = locToBlock;
        while (i <= locToBlock2) {
            int j = locToBlock3;
            while (j <= locToBlock4) {
                final int locToBlock5 = Location.locToBlock(location.getY());
                final Material type = world.getBlockAt(i, locToBlock5 - 1, j).getType();
                final Material type2 = world.getBlockAt(i, locToBlock5, j).getType();
                final Material type3 = world.getBlockAt(i, locToBlock5 + 1, j).getType();
                final Material type4 = world.getBlockAt(i, locToBlock5 + 2, j).getType();
                if (type3 == Material.WATER || type3 == Material.STATIONARY_WATER || type3 == Material.STATIONARY_LAVA || type3 == Material.LAVA || type4 == Material.WATER || type4 == Material.STATIONARY_WATER || type4 == Material.STATIONARY_LAVA || type4 == Material.LAVA) {
                    this.headInLiquid = true;
                }
                if (this.hasBadBoundingBox(type2) || this.hasBadBoundingBox(type)) {
                    this.onBadBlock = true;

                }
                else {
                    this.onBadBlock = false;
                }
                if (this.onBadBlock) {
                    this.setLastOnBadBlock(System.currentTimeMillis());
                }
                ++j;

            }
            ++i;

        }
    }
    
    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }
    
    public long getLastFlying() {
        return this.lastFlying;
    }
    
    public void setTeleportTicks(final int teleportTicks) {
        this.teleportTicks = teleportTicks;
    }
    
    public boolean canClickerCheck() {
        return !this.isDigging() && !this.isPlacing();
    }
    
    public void setToggleFlyTicks(final int toggleFlyTicks) {
        this.toggleFlyTicks = toggleFlyTicks;
    }
    
    public void setLastYawChange(final float lastYawChange) {
        this.lastYawChange = lastYawChange;
    }
    
    public void setBedTicks(final int bedTicks) {
        this.BedTicks = bedTicks;
    }
    
    public void setUnderBlock(final boolean underBlock) {
        this.underBlock = underBlock;
    }
    
    public void setMovedSinceTeleport(final boolean movedSinceTeleport) {
        this.movedSinceTeleport = movedSinceTeleport;
    }
    
    public void setVelocityY(final double velocityY) {
        this.velocityY = velocityY;
    }
    
    public void setLastAttackPacket(final long lastAttackPacket) {
        this.lastAttackPacket = lastAttackPacket;
    }
    
    public long getLastDelayedMovePacket() {
        return this.lastDelayedMovePacket;
    }
    
    public void setSlimeTicks(final int slimeTicks) {
        this.slimeTicks = slimeTicks;
    }
    
    public void setCurrentLocation(final CustomLocation currentLocation) {
        this.currentLocation = currentLocation;
    }
    
    public boolean PingisAbove(final int n) {
        boolean b;
        if (PlayerUtils.getPing(this.getBukkitPlayer()) > n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public void setOnSoulSand(final boolean onSoulSand) {
        this.onSoulSand = onSoulSand;
    }
    
    public void setOnRail(final boolean onRail) {
        this.onRail = onRail;
    }
    
    public float getLastYawChange() {
        return this.lastYawChange;
    }
    
    public ChecksManager getCheckRegistry() {
        return this.checkRegistry;
    }
    
    public boolean isAlerts() {
        return this.alerts;
    }
    
    public boolean isOnIce() {
        return this.onIce;
    }
    
    public boolean isEnemyisplayer() {
        return this.enemyisplayer;
    }
    
    public void setLastDelayedMovePacket(final long lastDelayedMovePacket) {
        this.lastDelayedMovePacket = lastDelayedMovePacket;
    }
    
    public boolean hasLag(final long n) {
        boolean b;
        if (System.currentTimeMillis() - this.lastDelayed < n || System.currentTimeMillis() - this.lastLag < n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public void setWasOnStairs(final boolean wasOnStairs) {
        this.wasOnStairs = wasOnStairs;
    }
    
    public long getLastLag() {
        return this.lastLag;
    }
    
    public void setInteracting(final boolean interacting) {
        this.interacting = interacting;
    }
    
    public int getSprintTicks() {
        return this.sprintTicks;
    }
    
    public void setInventoryOpen(final boolean inventoryOpen) {
        this.inventoryOpen = inventoryOpen;
    }
    
    public boolean isHeadWasInLiquid() {
        return this.headWasInLiquid;
    }
    
    public boolean isPlacing() {
        return this.placing;
    }
    
    public boolean isWasOnStairs() {
        return this.wasOnStairs;
    }
    
    public boolean isOnStairs() {
        return this.onStairs;
    }
    
    public void setSprintTicks(final int sprintTicks) {
        this.sprintTicks = sprintTicks;
    }
    
    public boolean isAttacking() {
        return this.attacking;
    }
    
    public void setWasInLiquid(final boolean wasInLiquid) {
        this.wasInLiquid = wasInLiquid;
    }
    
    public void setInLiquid(final boolean inLiquid) {
        this.inLiquid = inLiquid;
    }
    
    public int getStandTicks() {
        return this.standTicks;
    }
    
    public boolean isOnSnow() {
        return this.onSnow;
    }
    
    public void setOnPiston(final boolean onPiston) {
        this.onPiston = onPiston;
    }
    
    public void setPistonPushTicks(final int pistonPushTicks) {
        this.pistonPushTicks = pistonPushTicks;
    }
    
    public void setLastHitEntity(final Entity lastHitEntity) {
        this.lastHitEntity = lastHitEntity;
    }
    
    public boolean isInteracting() {
        return this.interacting;
    }
    
    public void setWasOnGround(final boolean wasOnGround) {
        this.wasOnGround = wasOnGround;
    }
    
    public void setOnSlab(final boolean onSlab) {
        this.onSlab = onSlab;
    }
    
    public void setPlacing(final boolean placing) {
        this.placing = placing;
    }
    
    public boolean isAbove(final int n) {
        boolean b;
        if (this.protocolVersion > n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean PingisBelow(final int n) {
        boolean b;
        if (PlayerUtils.getPing(this.getBukkitPlayer()) < n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public void setWasOnPiston(final boolean wasOnPiston) {
        this.wasOnPiston = wasOnPiston;
    }
    
    public int getLoginTicks() {
        return this.loginTicks;
    }
    
    public void setOnSnow(final boolean onSnow) {
        this.onSnow = onSnow;
    }
    
    public double getSpeedXZ() {
        return this.speedXZ;
    }
    
    public int getLastAttackTick() {
        return this.lastAttackTick;
    }
    
    public void setSoulSandTicks(final int soulSandTicks) {
        this.soulSandTicks = soulSandTicks;
    }
    
    public boolean isOnBadBlock() {
        return this.onBadBlock;
    }
    
    public void setLastLocation(final CustomLocation lastLocation) {
        this.lastLocation = lastLocation;
    }
    
    public void setOnIceTicks(final int onIceTicks) {
        this.onIceTicks = onIceTicks;
    }
    
    public boolean lagCheck() {
        return System.currentTimeMillis() - this.lastDelayedMovePacket >= 220L;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public void setGlideTicks(final int glideTicks) {
        this.glideTicks = glideTicks;
    }
    
    public boolean isHeadInLiquid() {
        return this.headInLiquid;
    }
    
    public boolean canRangeCheck() {
        return this.lagCheck() && this.lastLocation != null && this.lastPlayerHit != null && this.lastAttackTick <= 1;
    }
    
    public void setInWeb(final boolean inWeb) {
        this.inWeb = inWeb;
    }
    
    public boolean isOnRail() {
        return this.onRail;
    }
    
    public void setPacketPlayOutPositionTicks(final int packetPlayOutPositionTicks) {
        this.packetPlayOutPositionTicks = packetPlayOutPositionTicks;
    }
    
    public int getToggleFlyTicks() {
        return this.toggleFlyTicks;
    }
    
    public boolean isInLiquid() {
        return this.inLiquid;
    }
    
    public void setWasUnderBlock(final boolean wasUnderBlock) {
        this.wasUnderBlock = wasUnderBlock;
    }
    
    public long getLastPlaceCancel() {
        return this.lastPlaceCancel;
    }
    
    public PlayerData getLastPlayerHit() {
        PlayerData playerData;
        if (this.lastPlayerHit == null) {
            playerData = null;

        }
        else {
            playerData = this.lastPlayerHit.get();
        }
        return playerData;
    }
    
    public void setOnCarpet(final boolean onCarpet) {
        this.onCarpet = onCarpet;
    }
    
    public boolean isMovedSinceLogin() {
        return this.movedSinceLogin;
    }
    
    public long getLastDelayed() {
        return this.lastDelayed;
    }
    
    public void setLastAttackTick(final int lastAttackTick) {
        this.lastAttackTick = lastAttackTick;
    }
    
    public boolean isOnSoulSand() {
        return this.onSoulSand;
    }
    
    public int getBedTicks() {
        return this.BedTicks;
    }
    
    public boolean isSwinging() {
        return this.swinging;
    }
    
    public void setOnIce(final boolean onIce) {
        this.onIce = onIce;
    }
    
    public void setHeadInLiquid(final boolean headInLiquid) {
        this.headInLiquid = headInLiquid;
    }
    
    public void setHeadWasInLiquid(final boolean headWasInLiquid) {
        this.headWasInLiquid = headWasInLiquid;
    }
    
    public long getLastAttackPacket() {
        return this.lastAttackPacket;
    }
    
    public boolean isWasUnderBlock() {
        return this.wasUnderBlock;
    }
    
    public VelocityManager getVelocityManager() {
        return this.velocityManager;
    }
    
    public void setFlyingTicks(final int flyingTicks) {
        this.FlyingTicks = flyingTicks;
    }
    
    public void setOnBadBlock(final boolean onBadBlock) {
        this.onBadBlock = onBadBlock;
    }
    
    public void setLastPlaceCancel(final long lastPlaceCancel) {
        this.lastPlaceCancel = lastPlaceCancel;
    }
    
    public boolean isOnSlab() {
        return this.onSlab;
    }
    
    public int getOnIceTicks() {
        return this.onIceTicks;
    }
    
    public void setOnStairs(final boolean onStairs) {
        this.onStairs = onStairs;
    }
    
    public Entity getLastHitEntity() {
        return this.lastHitEntity;
    }
    
    public void setDigging(final boolean digging) {
        this.digging = digging;
    }
    
    public double getVelocityY() {
        return this.velocityY;
    }
    
    public void setSpeedXZ(final double speedXZ) {
        this.speedXZ = speedXZ;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public boolean isWasInLiquid() {
        return this.wasInLiquid;
    }
    
    public void setAlerts(final boolean alerts) {
        this.alerts = alerts;
    }
    
    public boolean isDigging() {
        return this.digging;
    }
    
    public int getGlideTicks() {
        return this.glideTicks;
    }
    
    public void setEnemyisplayer(final boolean enemyisplayer) {
        this.enemyisplayer = enemyisplayer;
    }
    
    public void setStandTicks(final int standTicks) {
        this.standTicks = standTicks;
    }
    
    public int getUnderBlockTicks() {
        return this.underBlockTicks;
    }
    
    public void setLoginTicks(final int loginTicks) {
        this.loginTicks = loginTicks;
    }
    
    public void setWasOnBadBlock(final boolean wasOnBadBlock) {
        this.wasOnBadBlock = wasOnBadBlock;
    }
    
    public void setClientVersion(final int clientVersion) {
        this.clientVersion = clientVersion;
    }
    
    public int getPacketPlayOutPositionTicks() {
        return this.packetPlayOutPositionTicks;
    }
    
    public void setLastFlying(final long lastFlying) {
        this.lastFlying = lastFlying;
    }
    
    public void setLastBlockPlace(final long lastBlockPlace) {
        this.lastBlockPlace = lastBlockPlace;
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    public void setProtocolVersion(final int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
    
    private boolean hasBadBoundingBox(final Material material) {
        boolean b;
        if (material == Material.FENCE || material == Material.NETHER_FENCE || material == Material.BOAT || material == Material.WATER_LILY || material == Material.ENDER_PORTAL_FRAME || material == Material.ENCHANTMENT_TABLE || material == Material.FENCE_GATE || material == Material.ACACIA_FENCE_GATE || material == Material.DARK_OAK_FENCE_GATE || material == Material.JUNGLE_FENCE_GATE || material == Material.BIRCH_FENCE_GATE || material == Material.SPRUCE_FENCE_GATE || material == Material.DARK_OAK_FENCE_GATE || material == Material.ACACIA_FENCE || material == Material.DARK_OAK_FENCE || material == Material.JUNGLE_FENCE || material == Material.BIRCH_FENCE || material == Material.SPRUCE_FENCE || material == Material.DARK_OAK_FENCE || material == Material.FLOWER_POT || material == Material.SKULL || material == Material.TRAP_DOOR || material == Material.IRON_TRAPDOOR || material == Material.CHEST || material == Material.ENDER_CHEST || material == Material.STEP || material == Material.SOUL_SAND) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean isOnCarpet() {
        return this.onCarpet;
    }
    
    public boolean isMovedSinceTeleport() {
        return this.movedSinceTeleport;
    }
    
    public boolean isUnderBlock() {
        return this.underBlock;
    }
    
    public int getSoulSandTicks() {
        return this.soulSandTicks;
    }
    
    public boolean PingisEqOrBelow(final int n) {
        boolean b;
        if (PlayerUtils.getPing(this.getBukkitPlayer()) <= n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public void setSwinging(final boolean swinging) {
        this.swinging = swinging;
    }
    
    public boolean isInWeb() {
        return this.inWeb;
    }
    
    public boolean PingisEqOrAbove(final int n) {
        boolean b;
        if (PlayerUtils.getPing(this.getBukkitPlayer()) >= n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public CustomLocation getLastLocation() {
        return this.lastLocation;
    }
    
    public boolean isOnLilyPad() {
        return this.onLilyPad;
    }
    
    public boolean isWasInWeb() {
        return this.wasInWeb;
    }
    
    public boolean isOnPiston() {
        return this.onPiston;
    }
    
    public CustomLocation getCurrentLocation() {
        return this.currentLocation;
    }
    
    public boolean isEqOrAbove(final int n) {
        boolean b;
        if (this.protocolVersion >= n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public int getFlyingTicks() {
        return this.FlyingTicks;
    }
    
    public PlayerData(final Player bukkitPlayer) {
        this.alerts = false;
        this.velocityManager = new VelocityManager();
 //       this.pastPositions = (Queue<CustomLocation>)EvictingQueue.create(8);
        this.bukkitPlayer = bukkitPlayer;
        this.checkRegistry = new ChecksManager(this);
    }
    
    public void setAttacking(final boolean attacking) {
        this.attacking = attacking;
    }
    
    public void setMovedSinceLogin(final boolean movedSinceLogin) {
        this.movedSinceLogin = movedSinceLogin;
    }
    
    public boolean isInventoryOpen() {
        return this.inventoryOpen;
    }
    
    public boolean isBelow(final int n) {
        boolean b;
        if (this.protocolVersion < n) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    public long getLastOnBadBlock() {
        return this.lastOnBadBlock;
    }
    
    public int getClientVersion() {
        return this.clientVersion;
    }
    
    public int getSlimeTicks() {
        return this.slimeTicks;
    }
    
    public void setLastLag(final long lastLag) {
        this.lastLag = lastLag;
    }
}
