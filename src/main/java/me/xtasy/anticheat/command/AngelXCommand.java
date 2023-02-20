package me.xtasy.anticheat.command;

import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import me.xtasy.anticheat.check.*;
import me.xtasy.anticheat.*;
import java.io.*;
import org.bukkit.command.*;
import me.xtasy.anticheat.util.message.*;
import org.bukkit.*;
import me.xtasy.anticheat.util.player.*;
import org.apache.commons.math3.analysis.differentiation.*;
import me.xtasy.anticheat.gui.*;
import java.util.function.*;
import me.xtasy.anticheat.data.*;
import java.util.*;

public class AngelXCommand implements CommandExecutor
{
    public static YamlConfiguration bannedPlayersFile;
    public static YamlConfiguration messagesFile;
    public static YamlConfiguration checksFile;
    public static YamlConfiguration settingsFile;
   
    
    private static void onCommand(final Player player, final Check check) {
        player.sendMessage(getMessagesFile().getString("PLAYER_VIOLATIONS").replaceAll("&", "�").replaceAll("<check>", check.getCheckName()).replaceAll("<checkVL>", String.valueOf(check.getVl())));
    }
    
    private static void onCommand$2(final Check check) {
        if (!AngelXCommand.checksFile.contains(check.getCheckName())) {
            AngelXCommand.checksFile.set(check.getCheckName() + ".state", (Object)true);
            AngelXCommand.checksFile.set(check.getCheckName() + ".autoban", (Object)false);
            AngelXCommand.checksFile.set(check.getCheckName() + ".setback", (Object)false);
            AngelXCommand.checksFile.set(check.getCheckName() + ".banVL", (Object)20);
            AngelXCommand.checksFile.set(check.getCheckName() + ".desc", (Object)"null");
        }
    }

    public static YamlConfiguration getMessagesFile() {
        return YamlConfiguration.loadConfiguration(new File(AngelX.getApi().getPlugin().getDataFolder(), "messagesFile.yml"));
    }

    public static YamlConfiguration getSettingsFile() {
        return YamlConfiguration.loadConfiguration(new File(AngelX.getApi().getPlugin().getDataFolder(), "settingsFile.yml"));
    }

    public static YamlConfiguration getChecksFile() {
        return YamlConfiguration.loadConfiguration(new File(AngelX.getApi().getPlugin().getDataFolder(), "checksFile.yml"));
    }

    private static void onCommand$3(final Check check) {
        AngelXCommand.checksFile.set(check.getCheckName() + ".state", (Object)true);
        AngelXCommand.checksFile.set(check.getCheckName() + ".autoban", (Object)false);
        AngelXCommand.checksFile.set(check.getCheckName() + ".setback", (Object)false);
        AngelXCommand.checksFile.set(check.getCheckName() + ".banVL", (Object)20);
        AngelXCommand.checksFile.set(check.getCheckName() + ".desc", (Object)"null");
    }

    private static boolean onCommand(final Check check) {
        boolean b;
        if (check.getVl() > 0) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }

    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        final Player player = (Player)commandSender;
        final PlayerData data = AngelX.getApi().getPlayerDataManager().getData(player);
        if (array.length == 0) {
            if (player.isOp() || player.hasPermission("angelX.staff")) {
                player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
                player.sendMessage(CC.DARK_PURPLE + "" + CC.BOLD + "AngelX Commands:");
                player.sendMessage(getMessagesFile().getString("HELP_COMMAND_LINE_1").replaceAll("&", "�"));
                player.sendMessage(getMessagesFile().getString("HELP_COMMAND_LINE_2").replaceAll("&", "�"));
                player.sendMessage(getMessagesFile().getString("HELP_COMMAND_LINE_3").replaceAll("&", "�"));
                player.sendMessage(getMessagesFile().getString("HELP_COMMAND_LINE_4").replaceAll("&", "�"));
                player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");

            }
            else {
                player.sendMessage(getMessagesFile().getString("NO_PERMISSIONS").replaceAll("&", "�"));
            }
        }
        if (array.length == 2 && ((array[0].equalsIgnoreCase("Check") && player.isOp()) || player.hasPermission("angelX.staff"))) {
            final Player player2 = Bukkit.getPlayer(array[1]);
            if (player2 != null) {
                final PlayerData data2 = AngelX.getApi().getPlayerDataManager().getData(player2);
                boolean b = false;
                final Iterator<Check> iterator = (Iterator<Check>)data2.getCheckRegistry().getChecks().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getVl() > 0) {
                        b = true;
                    }

                }
                if (b) {
                    player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
                    player.sendMessage("");
                    player.sendMessage(getMessagesFile().getString("SHOWING_VIOLATIONS_OF").replaceAll("&", "�").replaceAll("<player>", player2.getDisplayName()));
                    player.sendMessage("");
                    data2.getCheckRegistry().getChecks().stream().filter(AngelXCommand::onCommand).forEach(AngelXCommand::onCommand);
                    player.sendMessage("");
                    player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
                    player.sendMessage("");

                }
                else {
                    player.sendMessage(getMessagesFile().getString("PLAYER_NO_VIOLATIONS").replaceAll("&", "�").replaceAll("<player>", player2.getName()));
                }

            }
            else {
                player.sendMessage(getMessagesFile().getString("PLAYER_NOT_FOUND").replaceAll("&", "�"));
            }
        }
        if (array.length == 2 && array[0].equalsIgnoreCase("info") && (player.isOp() || player.hasPermission("angelX.staff"))) {
            final Player player3 = Bukkit.getPlayer(array[1]);
            if (player3 != null) {
                AngelX.getApi().getPlayerDataManager().getData(player3);
                player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
                player.sendMessage("�7" + player3.getDisplayName() + "�7's ping");
                player.sendMessage("�7Ping �8� " + PlayerUtils.pingColor(player3) + PlayerUtils.getPing(player3));
                player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
            }
        }
        if (array.length == 1) {
            if (array[0].equalsIgnoreCase("alerts")) {
 
                if (player.isOp() || player.hasPermission("angelX.staff")) {
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
                        player.sendMessage(getMessagesFile().getString("ALERTS_ENABLE").replaceAll("&", "�"));

                    }
                    else {
                        player.sendMessage(getMessagesFile().getString("ALERTS_DISABLE").replaceAll("&", "�"));

                    }
                }
                else {
                    player.sendMessage(getMessagesFile().getString("NO_PERMISSIONS").replaceAll("&", "�"));
                }
            }
            if (array[0].equalsIgnoreCase("gui")) {
 
                if (player.isOp() || player.hasPermission("angelX.gui")) {
                    player.openInventory(new AngelXInventories(player).getHubInventory());

                }
                else {
                    player.sendMessage(getMessagesFile().getString("NO_PERMISSIONS").replaceAll("&", "�"));
                }
            }
            if (array[0].equalsIgnoreCase("ver") && player.getUniqueId().toString().equalsIgnoreCase("a305ad37-bf80-4fd8-9466-fcb005b1835a")) {
                player.sendMessage("�5�lAngelX �8� �dThis server is using AngelX version: �5�l" + AngelX.getApi().getPlugin().getDescription().getVersion());
            }
            if (array[0].equalsIgnoreCase("updateConfig")) {
                if (player.isOp()) {
                    final File file = new File(AngelX.getApi().getPlugin().getDataFolder(), "checksFile.yml");
                    if (!file.exists()) {
                        player.sendMessage(CC.RED + "Run /AngelX Setup first!");
                    }
                    AngelXCommand.checksFile = YamlConfiguration.loadConfiguration(file);
                    data.getCheckRegistry().getChecks().forEach(AngelXCommand::onCommand$2);
                    try {
                        AngelXCommand.checksFile.save(file);
                     
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    player.sendMessage(CC.GREEN + "Finished updating AngelX Config!");

                }
                else {
                    player.sendMessage(getMessagesFile().getString("NO_PERMISSIONS").replaceAll("&", "�"));
                }
            }
            if (array[0].equalsIgnoreCase("setup")) {
       
                if (player.isOp()) {
                    try {
                        player.sendMessage(CC.GREEN + "Setting up AngelX...");
                        if (AngelXCommand.checksFile == null || AngelXCommand.messagesFile == null || AngelXCommand.settingsFile == null || AngelXCommand.bannedPlayersFile == null) {
                            AngelX.getApi().getPlugin().getDataFolder().mkdirs();
                            final File file2 = new File(AngelX.getApi().getPlugin().getDataFolder(), "checksFile.yml");
                            final File file3 = new File(AngelX.getApi().getPlugin().getDataFolder(), "messagesFile.yml");
                            final File file4 = new File(AngelX.getApi().getPlugin().getDataFolder(), "settingsFile.yml");
                            final File file5 = new File(AngelX.getApi().getPlugin().getDataFolder(), "bannedPlayers.yml");
                            AngelXCommand.checksFile = YamlConfiguration.loadConfiguration(file2);
                            AngelXCommand.settingsFile = YamlConfiguration.loadConfiguration(file4);
                            AngelXCommand.messagesFile = YamlConfiguration.loadConfiguration(file3);
                            AngelXCommand.bannedPlayersFile = YamlConfiguration.loadConfiguration(file5);
                            AngelXCommand.settingsFile.set("BanCommand", (Object)"ban <player> Cheating!");
                            AngelXCommand.settingsFile.set("TpCommand", (Object)"/tp <player>");
                            AngelXCommand.messagesFile.set("ANGELX_PREFIX", (Object)"&5&lAngelX &8� ");
                            AngelXCommand.messagesFile.set("ANGELX_ALERTS", (Object)"�f <player> &7Failed &d<check> &l&d(&5x<vl>&d)");
                            AngelXCommand.messagesFile.set("BAN_ANNOUNCEMENT", (Object)"&cAngelX has banned <player> for Unfair Advantage");
                            AngelXCommand.messagesFile.set("NO_PERMISSIONS", (Object)"&4You do not have permissions!");
                            AngelXCommand.messagesFile.set("INVALID_COMMAND", (Object)"&4Invalid Command!");
                            AngelXCommand.messagesFile.set("CHECK_NOT_FOUND", (Object)"&4Check not found!");
                            AngelXCommand.messagesFile.set("PLAYER_NOT_FOUND", (Object)"&4Player not found!");
                            AngelXCommand.messagesFile.set("ALERTS_ENABLE", (Object)"&aEnabled alerts!");
                            AngelXCommand.messagesFile.set("ALERTS_DISABLE", (Object)"&4Disabled alerts!");
                            AngelXCommand.messagesFile.set("SHOWING_VIOLATIONS_OF", (Object)"&7Showing the violations of <player>:");
                            AngelXCommand.messagesFile.set("PLAYER_VIOLATIONS", (Object)"&7 * &d<check> &7- <checkVL> VL");
                            AngelXCommand.messagesFile.set("PLAYER_NO_VIOLATIONS", (Object)"&a<player> have no violations!");
                            AngelXCommand.messagesFile.set("CLICK_TO_TELEPORT", (Object)"&aClick to teleport!");
                            AngelXCommand.messagesFile.set("HELP_COMMAND_LINE_1", (Object)"&7 * &d/AngelX Alerts - enable and disable alerts");
                            AngelXCommand.messagesFile.set("HELP_COMMAND_LINE_2", (Object)"&7 * &d/AngelX GUI - opens AngelX GUI");
                            AngelXCommand.messagesFile.set("HELP_COMMAND_LINE_3", (Object)"&7 * &d/AngelX Check <player> - player flagged or not!");
                            AngelXCommand.messagesFile.set("HELP_COMMAND_LINE_4", (Object)"&7 * &d/AngelX updateConfig - updates config");
                            data.getCheckRegistry().getChecks().forEach(AngelXCommand::onCommand$3);
                            AngelXCommand.checksFile.save(file2);
                            AngelXCommand.settingsFile.save(file4);
                            AngelXCommand.messagesFile.save(file3);
                            AngelXCommand.bannedPlayersFile.save(file5);
                            player.sendMessage(CC.GREEN + "Finished setting up AngelX!");
                         
                        }
                        else {
                            player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
                            player.sendMessage(CC.DARK_PURPLE + "" + CC.BOLD + "This command can be ran only once");
                            player.sendMessage(CC.GRAY + " * " + CC.PINK + "Remove AngelX config files from your servers files if you want to reset config from 0");
                            player.sendMessage(CC.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------");
                        }
                     
                        return true;
                    }
                    catch (Exception ex2) {
                        player.sendMessage(CC.RED + "Failed setting up AngelX!");
                   
                        return true;
                    }
                }
                player.sendMessage(getMessagesFile().getString("NO_PERMISSIONS").replaceAll("&", "�"));
            }
        }
        return true;
    }
}
