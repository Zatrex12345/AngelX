package me.xtasy.anticheat.handler;

import com.comphenix.protocol.events.PacketEvent;
import me.xtasy.anticheat.data.*;
import org.bukkit.event.player.PlayerMoveEvent;

public interface HandlersManager<T>
{

    void handle(PlayerData playerData, PlayerMoveEvent playerMoveEvent);

    void handle(PlayerData playerData, PacketEvent packetEvent);
}
