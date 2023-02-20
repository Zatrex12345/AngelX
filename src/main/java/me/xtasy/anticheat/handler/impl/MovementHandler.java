package me.xtasy.anticheat.handler.impl;

import com.comphenix.protocol.events.PacketEvent;
import me.xtasy.anticheat.handler.*;
import org.bukkit.event.player.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.util.*;
import me.xtasy.anticheat.util.server.*;
import me.xtasy.anticheat.util.math.*;
import me.xtasy.anticheat.check.checks.*;
import me.xtasy.anticheat.check.*;
import java.util.function.*;
import org.bukkit.*;
import me.xtasy.anticheat.update.impl.*;

public class MovementHandler implements HandlersManager
{

    
@Override
    public void handle(final PlayerData playerData, final PlayerMoveEvent playerMoveEvent) {
        final Location from = playerMoveEvent.getFrom();
        final Location to = playerMoveEvent.getTo();
        final Material type = playerData.getBukkitPlayer().getLocation().add(0.0, -1.0, 0.0).getBlock().getType();
        final Material type2 = playerData.getBukkitPlayer().getLocation().add(0.0, 0.0, 0.0).getBlock().getType();
        playerData.setOnLilyPad(false);
        playerData.setOnPiston(false);
        playerData.setOnSoulSand(false);
        playerData.setOnSnow(false);
        playerData.setOnRail(false);
        playerData.setWasOnGround(playerData.isOnGround());
        playerData.setWasOnStairs(playerData.isOnStairs());
        playerData.setWasInLiquid(playerData.isInLiquid());
        playerData.setWasUnderBlock(playerData.isUnderBlock());
        playerData.setWasOnBadBlock(playerData.isOnBadBlock());
        playerData.setWasInWeb(playerData.isInWeb());
        playerData.setWasOnPiston(playerData.isOnPiston());
        boolean onGround;
        if (BlockUtil.isOnGround(to, 0) || BlockUtil.isOnGround(to, 1)) {
            onGround = true;

        }
        else {
            onGround = false;
        }
        playerData.setOnGround(onGround);
        boolean inLiquid;
        if (BlockUtil.isOnLiquid(to, 0) || BlockUtil.isOnLiquid(to, 1) || BlockUtil.isOnLiquid(to, -1)) {
            inLiquid = true;

        }
        else {
            inLiquid = false;
        }
        playerData.setInLiquid(inLiquid);
        boolean inWeb;
        if (BlockUtil.isOnWeb(to, 0) || BlockUtil.isOnWeb(to, 1) || BlockUtil.isOnWeb(to, -1) || BlockUtil.isOnWeb(to, -2)) {
            inWeb = true;

        }
        else {
            inWeb = false;
        }
        playerData.setInWeb(inWeb);
        boolean onIce;
        if (BlockUtil.isOnIce(to, 1) || BlockUtil.isOnIce(to, 2)) {
            onIce = true;

        }
        else {
            onIce = false;
        }
        playerData.setOnIce(onIce);
        playerData.setOnSlab(BlockUtil.isOnSlab(playerData.getBukkitPlayer()));
        if (!ServerUtil.getVersion().equalsIgnoreCase("v1_8_R3") && !ServerUtil.getVersion().equalsIgnoreCase("v1_7_R4") && playerData.getBukkitPlayer().isFlying()) {
            playerData.setGlideTicks(20);
        }
        if (playerData.isOnIce()) {
            playerData.setOnIceTicks(20);
        }
        if (playerData.getBukkitPlayer().isFlying()) {
            playerData.setFlyingTicks(40);
        }
        if (type == Material.SLIME_BLOCK) {
            playerData.setSlimeTicks(40);
        }
        if (type2 == Material.WATER_LILY) {
            playerData.setOnLilyPad(true);
        }
        if (type == Material.BED_BLOCK) {
            playerData.setBedTicks(20);
        }
        if (type == Material.SNOW_BLOCK || type == Material.SNOW) {
            playerData.setOnSnow(true);
        }
        if (type == Material.RAILS || type == Material.ACTIVATOR_RAIL || type == Material.DETECTOR_RAIL || type == Material.POWERED_RAIL) {
            playerData.setOnRail(true);
        }
        if (type == Material.PISTON_BASE || type == Material.PISTON_EXTENSION || type == Material.PISTON_MOVING_PIECE || type == Material.PISTON_STICKY_BASE) {
            playerData.setOnPiston(true);
        }
        if (type2 == Material.SOUL_SAND) {
            playerData.setSoulSandTicks(40);
            playerData.setOnSoulSand(true);
        }
        boolean onStairs;
        if (BlockUtil.isOnStairs(to, 0) || BlockUtil.isOnStairs(to, 1)) {
            onStairs = true;

        }
        else {
            onStairs = false;
        }
        playerData.setOnStairs(onStairs);
        boolean onCarpet;
        if (BlockUtil.isOnCarpet(to, 0) || BlockUtil.isOnCarpet(to, 1)) {
            onCarpet = true;

        }
        else {
            onCarpet = false;
        }
        playerData.setOnCarpet(onCarpet);
        playerData.setUnderBlock(BlockUtil.isOnGround(to, -2));
        if (playerData.isUnderBlock() || playerData.isWasUnderBlock()) {
            playerData.setUnderBlockTicks(20);
        }
        playerData.setLastYawChange(MathUtil.getDistanceBetweenAngles(playerMoveEvent.getTo().getYaw(), playerMoveEvent.getFrom().getYaw()));
        playerData.updateFlags(to);
        playerData.getCheckRegistry().getChecks().stream().filter(PositionCheck.class::isInstance).forEach((Consumer<? super Check>)MovementHandler::handle);
    }

    private static void handle(Check check) {
    }

    @Override
    public void handle(PlayerData playerData, PacketEvent packetEvent) {

    }


    
    private static void handle(final Location location, final Location location2, final Check check) {
    //    check.run(new PositionUpdate(location, location2));
    }
}
