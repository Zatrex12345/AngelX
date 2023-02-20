package me.xtasy.anticheat.check;

import me.xtasy.anticheat.data.*;
import org.bukkit.entity.*;
import java.util.function.*;
import me.xtasy.anticheat.command.*;
import me.xtasy.anticheat.util.player.*;
import me.xtasy.anticheat.*;
import org.bukkit.plugin.*;
import java.text.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import me.xtasy.anticheat.util.message.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.*;
import me.xtasy.anticheat.check.data.*;
import java.lang.annotation.*;

public abstract class Check
{
    private int vl;
    protected final PlayerData player;
    private final String checkName;
    private final Class clazz;
  
    public Class getClazz() {
        return this.clazz;
    }
    
    private static boolean handleDebug(final Player player) {
        return player.getName().equalsIgnoreCase("yazon_pro");
    }
    
    public String getCheckName() {
        return this.checkName;
    }
    
    public PlayerData getPlayer() {
        return this.player;
    }
    
    public int getVl() {
        return this.vl;
    }
    
    public static void handleDebug(final String s) {
        Bukkit.getOnlinePlayers().stream().filter(Check::handleDebug).forEach(Check::handleDebug);
    }



    public void onViolation(final String s) {
        if (!AngelXCommand.getChecksFile().getBoolean(this.checkName + ".state")) {
            return;
        }
        ++this.vl;
        final int ping = PlayerUtils.getPing(this.player.getBukkitPlayer());
        final String replaceAll = AngelXCommand.getMessagesFile().getString("ANGELX_PREFIX").replaceAll("&", "�");
        final int int1 = AngelXCommand.getChecksFile().getInt(this.checkName + ".banVL");
        final String replaceAll2 = AngelXCommand.getMessagesFile().getString("ANGELX_ALERTS").replaceAll("&", "�").replaceAll("<player>", this.player.getBukkitPlayer().getDisplayName()).replaceAll("<check>", this.checkName).replaceAll("<vl>", String.valueOf(this.vl));
        String string;
        if (s.length() > 0) {
            string = AngelXCommand.getMessagesFile().getString("CLICK_TO_TELEPORT").replaceAll("&", "�") + "\\n" + CC.GRAY + s;
          
        }
        else {
            string = "";
        }
        final Json.UtilActionMessage utilActionMessage = new Json.UtilActionMessage();
        final Json.UtilActionMessage.AMText addText = utilActionMessage.addText(replaceAll + replaceAll2.replaceAll("<banVl> ", String.valueOf(int1)).replaceAll("<ping>", String.valueOf(PlayerUtils.pingColor(this.player.getBukkitPlayer()) + ping)));
        final String[] array = { null };
        final int n = 0;
        String replaceAll3;
        if (s.length() > 0 && s != null) {
            replaceAll3 = string;
         
        }
        else {
            replaceAll3 = AngelXCommand.getMessagesFile().getString("CLICK_TO_TELEPORT").replaceAll("&", "�");
        }
        array[n] = replaceAll3;
        addText.addHoverText(array).setClickEvent(Json.UtilActionMessage.ClickableType.RunCommand, AngelXCommand.getSettingsFile().getString("TpCommand").replaceAll("<player>", this.player.getBukkitPlayer().getName()));
        for (final Player player : AngelX.getApi().getPlugin().getServer().getOnlinePlayers()) {
            if (AngelX.getApi().getPlayerDataManager().getData(player).isAlerts()) {
                utilActionMessage.sendToPlayer(player);
            }
        
        }
        final boolean boolean1 = AngelXCommand.getChecksFile().getBoolean(this.checkName + ".autoban");
        final int int2 = AngelXCommand.getChecksFile().getInt(this.checkName + ".banVL");
        if (AngelXCommand.getChecksFile().getBoolean(this.checkName + ".setback")) {
            this.player.getBukkitPlayer().teleport(new Location(this.player.getLastLocation().toBukkitWorld(), this.player.getLastLocation().getX(), this.player.getLastLocation().getY(), this.player.getLastLocation().getZ()));
        }
        if (boolean1 && this.vl >= int2) {
            if (!this.player.getBukkitPlayer().isOp() && this.player.getBukkitPlayer().isOnline() && !this.player.getBukkitPlayer().isBanned()) {
                Bukkit.getScheduler().runTask((Plugin)AngelX.getApi().getPlugin(), this::onViolation);
                final String replaceAll4 = AngelXCommand.getMessagesFile().getString("BAN_ANNOUNCEMENT").replaceAll("&", "�").replaceAll("<player>", this.player.getBukkitPlayer().getDisplayName()).replaceAll("<check>", this.checkName);
                if (replaceAll4 != null) {
                    Bukkit.getServer().broadcastMessage("");
                    Bukkit.getServer().broadcastMessage(replaceAll4);
                    Bukkit.getServer().broadcastMessage("");
                }
                final Date date = new Date();
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                    final File file = new File(AngelX.getApi().getPlugin().getDataFolder(), "bannedPlayers.yml");
                    (AngelXCommand.bannedPlayersFile = YamlConfiguration.loadConfiguration(file)).set(this.player.getBukkitPlayer().getUniqueId().toString() + ".ign", (Object)this.player.getBukkitPlayer().getDisplayName());
                    AngelXCommand.bannedPlayersFile.set(this.player.getBukkitPlayer().getUniqueId().toString() + ".reason", (Object)this.checkName);
                    AngelXCommand.bannedPlayersFile.set(this.player.getBukkitPlayer().getUniqueId().toString() + ".date", (Object)(simpleDateFormat.format(date) + ""));
                    AngelXCommand.bannedPlayersFile.save(file);
               
                }
                catch (Exception ex) {
                    LogUtil.log("Failed saving banned players file!");
                }
            
            }
            else {
                this.player.getBukkitPlayer().sendMessage(CC.DARK_RED + "You got banned!");
            }
            this.vl = 0;
        }
    }
    
    private void onViolation() {
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), AngelXCommand.getSettingsFile().getString("BanCommand").replaceAll("<check>", this.checkName).replaceAll("<player>", this.player.getBukkitPlayer().getName()));
    }
    
    private static void handleDebug(final String s, final Player player) {
        player.sendMessage(String.format("%s[DEV] %s%s", ChatColor.RED, ChatColor.GRAY, s));
    }
    
    public Check(final PlayerData player, final Class clazz) {
        this.player = player;
        this.clazz = clazz;
        if (!this.getClass().isAnnotationPresent(CheckData.class)) {
            throw new RuntimeException("Check " + this.getClass() + " is not set properly!");
        }
        this.checkName = this.getClass().getAnnotation(CheckData.class).checkName();
    }
    

}
