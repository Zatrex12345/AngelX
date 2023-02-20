package me.xtasy.anticheat.handler.impl;

import me.xtasy.anticheat.handler.*;
import com.comphenix.protocol.*;
import me.xtasy.anticheat.util.velocity.*;
import me.xtasy.anticheat.data.*;
import org.bukkit.*;
import me.xtasy.anticheat.check.*;
import me.xtasy.anticheat.update.impl.*;
import me.xtasy.anticheat.util.location.*;
import java.util.function.*;
import com.comphenix.protocol.wrappers.*;
import me.xtasy.anticheat.*;
import org.bukkit.entity.*;
import me.xtasy.anticheat.check.checks.*;
import com.comphenix.protocol.events.*;
import org.bukkit.event.player.PlayerMoveEvent;

public class PacketHandler implements HandlersManager
{

    public static boolean isFlying(final PacketType packetType) {
        boolean b;
        if (packetType == PacketType.Play.Client.FLYING || packetType == PacketType.Play.Client.POSITION || packetType == PacketType.Play.Client.POSITION_LOOK || packetType == PacketType.Play.Client.LOOK) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    private static boolean handle(final Velocity velocity) {
        boolean b;
        if (velocity.getCreationTime() + 2000L < System.currentTimeMillis()) {
            b = true;
        
        }
        else {
            b = false;
        }
        return b;
    }
    

    /**
    private static void handle(final Location location, final Location location2, final Check check) {
        check.run(new RotationUpdate(location, location2));
    }
    
    private static void handle1(final PacketEvent packetEvent, final Check check) {
        check.run(packetEvent);
    }
    **/

    @Override
    public void handle(final PlayerData playerData, final PlayerMoveEvent packetEvent) {
        // im lazy to fix it xd
     //   final PacketType packetType = packetEvent.getPacketType();
        //final PacketContainer packet = packetEvent.getPacket();
           final PacketType packetType = null;
        final PacketContainer packet = null;
        if (isFlying(packetType)) {
            final long currentTimeMillis = System.currentTimeMillis();
            final long n = currentTimeMillis - playerData.getLastFlying();
            if (n > 100L) {
                playerData.setLastDelayed(currentTimeMillis);
             
            }
            else if (n < 25L) {
                playerData.setLastLag(currentTimeMillis);
            }
            playerData.setLastFlying(currentTimeMillis);
            playerData.getVelocityManager().getVelocities().removeIf(PacketHandler::handle);
            final double doubleValue = (double)packet.getDoubles().read(0);
            final double doubleValue2 = (double)packet.getDoubles().read(1);
            final double doubleValue3 = (double)packet.getDoubles().read(2);
            final float floatValue = (float)packet.getFloat().read(0);
            final float floatValue2 = (float)packet.getFloat().read(1);
            final boolean booleanValue = (boolean)packet.getBooleans().read(1);
            final boolean booleanValue2 = (boolean)packet.getBooleans().read(2);
            final CustomLocation customLocation = new CustomLocation(doubleValue, doubleValue2, doubleValue3, floatValue, floatValue2);
            playerData.setCurrentLocation(customLocation);
            final CustomLocation lastLocation = playerData.getLastLocation();
            playerData.setLastAttackTick(playerData.getLastAttackTick() + 1);
            if (lastLocation != null) {
                if (!booleanValue) {
                    customLocation.setX(lastLocation.getX());
                    customLocation.setY(lastLocation.getY());
                    customLocation.setZ(lastLocation.getZ());
                    playerData.setStandTicks(playerData.getStandTicks() + 1);
               
                }
                else {
                    playerData.setStandTicks(0);
                }
                if (!booleanValue2) {
                    lastLocation.setYaw(lastLocation.getYaw());
                    lastLocation.setPitch(lastLocation.getPitch());
                
                }
                else {
                    final Location location = new Location(playerData.getBukkitPlayer().getWorld(), lastLocation.getX(), lastLocation.getY(), lastLocation.getZ(), lastLocation.getYaw(), lastLocation.getPitch());
                    final Location location2 = new Location(playerData.getBukkitPlayer().getWorld(), customLocation.getX(), customLocation.getY(), customLocation.getZ(), customLocation.getYaw(), customLocation.getPitch());
                    if (booleanValue2) {
                        playerData.getCheckRegistry().getChecks().stream().filter(RotationCheck.class::isInstance).forEach((Consumer<? super Check>)PacketHandler::handle);
                    }
                }
                if (System.currentTimeMillis() - playerData.getLastLocation().getTimestamp() > 110L) {
                    playerData.setLastDelayedMovePacket(System.currentTimeMillis());
                }
            }
            playerData.setLastLocation(customLocation);
            playerData.getPastPositions().add(customLocation);
            playerData.setAttacking(false);
            playerData.setSwinging(false);
            playerData.setInteracting(false);
            playerData.setPlacing(false);
            if (playerData.getPacketPlayOutPositionTicks() > 0) {
                playerData.setPacketPlayOutPositionTicks(playerData.getPacketPlayOutPositionTicks() - 1);
            }
            if (playerData.getToggleFlyTicks() > 0) {
                playerData.setToggleFlyTicks(playerData.getToggleFlyTicks() - 1);
            }
            if (playerData.getLoginTicks() > 0) {
                playerData.setLoginTicks(playerData.getLoginTicks() - 1);
            }
            if (playerData.getSoulSandTicks() > 0) {
                playerData.setSoulSandTicks(playerData.getSoulSandTicks() - 1);
            }
            if (playerData.getSlimeTicks() > 0) {
                playerData.setSlimeTicks(playerData.getSlimeTicks() - 1);
            }
            if (playerData.getBedTicks() > 0) {
                playerData.setBedTicks(playerData.getBedTicks() - 1);
            }
            if (playerData.getUnderBlockTicks() > 0) {
                playerData.setUnderBlockTicks(playerData.getUnderBlockTicks() - 1);
            }
            if (playerData.getOnIceTicks() > 0) {
                playerData.setOnIceTicks(playerData.getOnIceTicks() - 1);
            }
            if (playerData.getPistonPushTicks() > 0) {
                playerData.setPistonPushTicks(playerData.getPistonPushTicks() - 1);
            }
            if (playerData.getTeleportTicks() > 0) {
                playerData.setTeleportTicks(playerData.getTeleportTicks() - 1);
            }
            if (playerData.getFlyingTicks() > 0) {
                playerData.setFlyingTicks(playerData.getFlyingTicks() - 1);
            }
            if (playerData.getGlideTicks() > 0) {
                playerData.setGlideTicks(playerData.getGlideTicks() - 1);
            }
            if (playerData.getBukkitPlayer().isSprinting()) {
                playerData.setSprintTicks(playerData.getSprintTicks() + 1);
            
            }
            else {
                playerData.setSprintTicks(0);
            }
     
        }
        else if (packetType == PacketType.Play.Client.ARM_ANIMATION) {
            playerData.setSwinging(true);
          
        }
        else if (packetType == PacketType.Play.Client.USE_ENTITY) {
            if (packet.getEntityUseActions().read(0) == EnumWrappers.EntityUseAction.ATTACK) {
                playerData.setAttacking(true);
                playerData.setLastAttackPacket(System.currentTimeMillis());
                final Entity lastHitEntity = (Entity)packet.getEntityModifier(playerData.getBukkitPlayer().getWorld()).read(0);
                if (lastHitEntity != null) {
                    playerData.setLastHitEntity(lastHitEntity);
                    if (lastHitEntity.getType() == EntityType.PLAYER) {
                        playerData.setLastPlayerHit(AngelX.getApi().getPlayerDataManager().getData((Player)lastHitEntity));
                        playerData.setEnemyisplayer(true);
               
                    }
                    else {
                        playerData.setLastPlayerHit(null);
                        playerData.setEnemyisplayer(false);
                  
                    }
                }
                else {
                    playerData.setLastHitEntity(null);
                }
                playerData.setLastAttackTick(0);
             
            }
            else if (packet.getEntityUseActions().read(0) == EnumWrappers.EntityUseAction.INTERACT || packet.getEntityUseActions().read(0) == EnumWrappers.EntityUseAction.INTERACT_AT) {
                playerData.setInteracting(true);
          
            }
        }
        else if (packetType == PacketType.Play.Client.BLOCK_DIG) {
            if (packet.getPlayerDigTypes().read(0) == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                playerData.setDigging(true);
          
            }
            else if (packet.getPlayerDigTypes().read(0) == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK || packet.getPlayerDigTypes().read(0) == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                playerData.setDigging(false);
     
            }
        }
        else if (packetType == PacketType.Play.Client.BLOCK_PLACE) {
            playerData.setPlacing(true);
        
        }
        else if (packetType == PacketType.Play.Client.CLIENT_COMMAND) {
            if (packet.getClientCommands().read(0) == EnumWrappers.ClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
                playerData.setInventoryOpen(true);
             
            }
        }
        else if (packetType == PacketType.Play.Client.CLOSE_WINDOW) {
            playerData.setInventoryOpen(false);
          
        }
        else if (packetType == PacketType.Play.Server.POSITION) {
            playerData.setPacketPlayOutPositionTicks(40);
            playerData.setInventoryOpen(false);
          
        }
        else if (packetType == PacketType.Play.Server.ENTITY_VELOCITY && (int)packet.getIntegers().read(0) == playerData.getBukkitPlayer().getEntityId()) {
            double n3;
            final double n2 = n3 = (int)packet.getIntegers().read(1) / 8000.0;
            if (n2 < 0.0) {
                n3 = -n2;
            }
            final double n4 = n3;
            final double n5 = (int)packet.getIntegers().read(2) / 8000.0;
            double n7;
            final double n6 = n7 = (int)packet.getIntegers().read(3) / 8000.0;
            if (n6 < 0.0) {
                n7 = -n6;
            }
            playerData.getVelocityManager().addVelocityEntry(n4, n5, n7);
        }
        playerData.getCheckRegistry().getChecks().stream().filter(PacketCheck.class::isInstance).forEach(PacketHandler::handle);
    }

    @Override
    public void handle(PlayerData playerData, PacketEvent packetEvent) {

    }

    private static void handle(Check check) {

    }


}
