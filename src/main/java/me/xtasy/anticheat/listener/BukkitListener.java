package me.xtasy.anticheat.listener;

import me.xtasy.anticheat.AngelX;
import me.xtasy.anticheat.api.AntiCheatAPI;
import me.xtasy.anticheat.command.AngelXCommand;
import me.xtasy.anticheat.data.PlayerData;
import me.xtasy.anticheat.gui.AngelXInventories;
import me.xtasy.anticheat.handler.HandlersManager;
import me.xtasy.anticheat.handler.impl.MovementHandler;
import me.xtasy.anticheat.util.license.LicenseSystem;
import me.xtasy.anticheat.util.message.CC;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import protocolsupport.api.*;
import us.myles.ViaVersion.api.*;

import java.io.File;
import java.util.function.Consumer;

public class BukkitListener implements Listener
{
    private boolean protocolsupport;
    private boolean viaversion;
    private final AntiCheatAPI api;
    private YamlConfiguration licenseConfig;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(final BlockPlaceEvent blockPlaceEvent) {
        final PlayerData data = this.api.getPlayerDataManager().getData(blockPlaceEvent.getPlayer());
        data.setLastBlockPlace(System.currentTimeMillis());
        if (blockPlaceEvent.isCancelled()) {
            data.setLastPlaceCancel(System.currentTimeMillis());
        }
    }
    
    @EventHandler
    public void onInvClick(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getWhoClicked() instanceof Player) {
            final Player player = (Player)inventoryClickEvent.getWhoClicked();
            final PlayerData data = this.api.getPlayerDataManager().getData(player);
            final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
            final AngelXInventories angelXInventories = new AngelXInventories(player);
            final String title = inventoryClickEvent.getView().getTitle();
            if (title.equals("�5�lAngelX �7Menu") || title.equals("�5�lAngelX �aChecks!")) {
                if (currentItem == null || currentItem.toString().contains("AIR")) {
                    return;
                }
                if (title.equals("�5�lAngelX �7Menu") || title.equals("�5�lAngelX �aChecks!")) {
                    inventoryClickEvent.setCancelled(true);
                }
                final ItemStack alertsToggleGlow = angelXInventories.getAlertsToggleGlow();
                final ItemStack checksListEmrald = angelXInventories.getChecksListEmrald();
                if (currentItem.equals((Object)alertsToggleGlow)) {
                    final PlayerData playerData = data;
                    boolean alerts;
                    if (!data.isAlerts()) {
                        alerts = true;

                    }
                    else {
                        alerts = false;
                    }
                    playerData.setAlerts(alerts);
                    if (data.isAlerts()) {
                        player.sendMessage(AngelXCommand.getMessagesFile().getString("ALERTS_ENABLE").replaceAll("&", "�"));
                
                    }
                    else {
                        player.sendMessage(AngelXCommand.getMessagesFile().getString("ALERTS_DISABLE").replaceAll("&", "�"));
                    }
                }
                if (currentItem.equals((Object)checksListEmrald)) {
                    player.openInventory(angelXInventories.getAngelXChecksInventory());
                }
            }
        }
    }
    
    private static void onPlayerMove(final PlayerData playerData, final PlayerMoveEvent playerMoveEvent, final HandlersManager handlersManager) {
        handlersManager.handle(playerData, playerMoveEvent);
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(final PlayerJoinEvent playerJoinEvent) {
        final Player player = playerJoinEvent.getPlayer();
        final PlayerData data = this.api.getPlayerDataManager().getData(player);
        data.setLoginTicks(40);
        data.setMovedSinceLogin(false);
        final PlayerData playerData = data;
        boolean alerts;
        if (!data.isAlerts()) {
            alerts = true;

        }
        else {
            alerts = false;
        }
        playerData.setAlerts(alerts);
        if (this.viaversion) {
            data.setProtocolVersion(Via.getAPI().getPlayerVersion(player.getUniqueId()));
        }
        if (this.protocolsupport) {
            data.setProtocolVersion(ProtocolSupportAPI.getProtocolVersion(player).getId());
        }
        if (!this.protocolsupport && !this.viaversion) {
            data.setProtocolVersion(47);
        }
        if (player.hasPermission("angelx.staff") || player.hasPermission("angelx.alerts") || player.isOp()) {
            if (data.isAlerts()) {
                data.setAlerts(true);

            }
        }
        else {
            data.setAlerts(false);
        }
        if (player.isOp() && (!AngelXCommand.getMessagesFile().contains("ANGELX_PREFIX") || !AngelXCommand.getChecksFile().contains("Aim (Type A)") || !AngelXCommand.getSettingsFile().contains("BanCommand"))) {
            player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
            player.sendMessage(CC.DARK_PURPLE + "" + CC.BOLD + "run the the command below to startup AngelX please!");
            player.sendMessage(CC.GRAY + " * " + CC.PINK + "/AngelX Setup - setup AngelX config!");
            player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
        }
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent playerQuitEvent) {
        this.api.getPlayerDataManager().destroyData(playerQuitEvent.getPlayer());
    }
    
    @EventHandler
    public void onFlyToggle(final PlayerToggleFlightEvent playerToggleFlightEvent) {
        this.api.getPlayerDataManager().getData(playerToggleFlightEvent.getPlayer()).setToggleFlyTicks(40);
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent playerDeathEvent) {
        final PlayerData data = this.api.getPlayerDataManager().getData(playerDeathEvent.getEntity());
        data.setMovedSinceLogin(false);
        data.setTeleportTicks(50);
    }
    
    @EventHandler
    public  void onPlayerMove(final PlayerMoveEvent playerMoveEvent) {
        final Player player = playerMoveEvent.getPlayer();
        final PlayerData data = this.api.getPlayerDataManager().getData(player);
        if (!player.getWorld().isChunkLoaded(player.getWorld().getChunkAt(player.getLocation()))) {
            return;
        }
        data.setMovedSinceLogin(true);
        data.setMovedSinceTeleport(true);
        final double n = playerMoveEvent.getTo().getX() - playerMoveEvent.getFrom().getX();
        final double n2 = playerMoveEvent.getTo().getZ() - playerMoveEvent.getFrom().getZ();
        final double sqrt = Math.sqrt(n * n + n2 * n2);
        final PlayerData playerData = data;
        double speedXZ;
        if (sqrt > 0.005) {
            speedXZ = sqrt;

        }
        else {
            speedXZ = 0.0;
        }
        playerData.setSpeedXZ(speedXZ);
        this.api.getHandlersRegistry().getHandlers().stream().filter(MovementHandler.class::isInstance).forEach((Consumer<? super Object>)BukkitListener::onPlayerMove);
    }

    private static void onPlayerMove(Object o) {
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent playerRespawnEvent) {
        final PlayerData data = this.api.getPlayerDataManager().getData(playerRespawnEvent.getPlayer());
        data.setMovedSinceLogin(false);
        data.setTeleportTicks(50);
    }
    
    public BukkitListener(final AntiCheatAPI api) {
        this.api = api;
        api.getPlugin().getServer().getPluginManager().registerEvents((Listener)this, (Plugin)AngelX.getApi().getPlugin());
        this.protocolsupport = AngelX.getApi().getPlugin().getServer().getPluginManager().isPluginEnabled("ProtocolSupport");
        this.viaversion = AngelX.getApi().getPlugin().getServer().getPluginManager().isPluginEnabled("ViaVersion");
        this.licenseConfig = YamlConfiguration.loadConfiguration(new File(AngelX.getApi().getPlugin().getDataFolder(), "LicenseFile.yml"));
        if (!new LicenseSystem(this.licenseConfig.getString("LicenseKey"), "http://51.83.70.11/AdvancedLicense/verify.php", (Plugin)AngelX.getApi().getPlugin()).setSecurityKey("jJ6giJGAEhLCgij30lKga9tXX").register()) {
            return;
        }
    }
    
    @EventHandler
    public void onGamemodeChange(final PlayerGameModeChangeEvent playerGameModeChangeEvent) {
        if (!playerGameModeChangeEvent.getNewGameMode().equals((Object)GameMode.CREATIVE)) {
            this.api.getPlayerDataManager().getData(playerGameModeChangeEvent.getPlayer()).setToggleFlyTicks(40);
        }
    }
    
    @EventHandler
    public void onTeleport(final PlayerTeleportEvent playerTeleportEvent) {
        final PlayerData data = this.api.getPlayerDataManager().getData(playerTeleportEvent.getPlayer());
        data.setMovedSinceTeleport(false);
        if (playerTeleportEvent.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN) {
            data.setPacketPlayOutPositionTicks(0);
            data.setTeleportTicks(50);
            data.setInventoryOpen(false);
        }
    }
}
