package me.xtasy.anticheat.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.xtasy.anticheat.api.AntiCheatAPI;
import me.xtasy.anticheat.data.PlayerData;
import me.xtasy.anticheat.handler.HandlersManager;
import me.xtasy.anticheat.handler.impl.PacketHandler;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class ProtocolLibListener extends PacketAdapter
{
    private final AntiCheatAPI api;
    
    public ProtocolLibListener(final AntiCheatAPI api) {
        super((Plugin)api.getPlugin(), ListenerPriority.HIGH, new PacketType[] { PacketType.Play.Client.BLOCK_DIG, PacketType.Play.Client.BLOCK_PLACE, PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.FLYING, PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.ARM_ANIMATION, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CLIENT_COMMAND, PacketType.Play.Client.CLOSE_WINDOW, PacketType.Play.Client.HELD_ITEM_SLOT, PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, PacketType.Play.Server.ENTITY_TELEPORT, PacketType.Play.Server.ENTITY_VELOCITY, PacketType.Play.Server.POSITION });
        this.api = api;
    }
    
    public void onPacketReceiving(final PacketEvent packetEvent) {
        Consumer<? super HandlersManager> onPacketReceiving = null;
        this.api.getHandlersRegistry().getHandlers().stream().filter(PacketHandler.class::isInstance).forEach(onPacketReceiving);
    }

    public void onPacketSending(final PacketEvent packetEvent) {
     //   this.api.getHandlersRegistry().getHandlers().stream().filter(PacketHandler.class::isInstance).forEach(ProtocolLibListener::onPacketSending1);
    }

    private static void onPacketSending(final PlayerData playerData, final PacketEvent packetEvent, final HandlersManager handlersManager) {
        handlersManager.handle(playerData, packetEvent);
    }

    private static void onPacketReceiving(final PlayerData playerData, final PacketEvent packetEvent, final HandlersManager handlersManager) {
        handlersManager.handle(playerData, packetEvent);
    }
}
