package me.xtasy.anticheat.api;

import me.xtasy.anticheat.interf.*;
import org.bukkit.configuration.file.*;
import me.xtasy.anticheat.handler.handler.*;
import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.*;
import me.xtasy.anticheat.util.message.*;
import me.xtasy.anticheat.command.*;
import org.bukkit.command.*;
import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import org.bukkit.*;
import me.xtasy.anticheat.util.server.*;
import org.bukkit.plugin.*;
import java.io.*;
import org.apache.commons.math3.analysis.integration.*;
import me.xtasy.anticheat.listener.*;
import me.xtasy.anticheat.util.license.*;

public class AntiCheatAPI implements Startable
{
    public static YamlConfiguration bannedPlayersFile;
    private HandlersRegistry handlersRegistry;
    public static YamlConfiguration settingsFile;
    public static YamlConfiguration messagesFile;
    private YamlConfiguration licenseConfig;
    private PlayerDataManager playerDataManager;
    private final AngelX plugin;
    public static YamlConfiguration checksFile;

    public HandlersRegistry getHandlersRegistry() {
        return this.handlersRegistry;
    }
    
    @Override
    public void start() {
        LogUtil.log("Starting up...");
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.plugin == null) {
            throw new IllegalArgumentException();
        }
        (this.playerDataManager = new PlayerDataManager()).start();
        (this.handlersRegistry = new HandlersRegistry()).start();
        this.plugin.getCommand("Angel").setExecutor((CommandExecutor)new AngelXCommand());
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new ProtocolLibListener(this));
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)AngelX.getApi().getPlugin(), (Runnable)new ServerUtil(), 100L, 1L);
        if (!this.getPlugin().getDataFolder().exists()) {
            this.getPlugin().getDataFolder().mkdirs();
            LogUtil.log("Made data folder!");
        }
        final File file = new File(AngelX.getApi().getPlugin().getDataFolder(), "LicenseFile.yml");
        this.licenseConfig = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
                this.licenseConfig.set("LicenseKey", (Object)"null");
                this.licenseConfig.save(file);

            }
            catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
     //  BaseAbstractUnivariateIntegrator.loadchecks("ALL");
        new BukkitListener(this);
        if (!new LicenseSystem(this.licenseConfig.getString("LicenseKey"), "http://51.83.70.11:8880/AdvancedLicense/verify.php", (Plugin)this.plugin).setSecurityKey("jJ6giJGAEhLCgij30lKga9tXX").register()) {
            return;
        }
        LogUtil.log("S/O To ToonBasic for some dank inspiration");
        LogUtil.log("Loaded in " + (System.currentTimeMillis() - currentTimeMillis) + "ms!");
    }
    
    public PlayerDataManager getPlayerDataManager() {
        return this.playerDataManager;
    }
    
    public AntiCheatAPI(final AngelX plugin) {
        this.plugin = plugin;
    }
    
    public YamlConfiguration getLicenseConfig() {
        return this.licenseConfig;
    }
    
    public AngelX getPlugin() {
        return this.plugin;
    }
}
