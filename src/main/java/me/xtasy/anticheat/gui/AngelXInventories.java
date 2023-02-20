package me.xtasy.anticheat.gui;

import java.text.*;
import org.bukkit.entity.*;
import me.xtasy.anticheat.util.server.*;
import me.xtasy.anticheat.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import java.util.*;
import java.util.function.*;
import me.xtasy.anticheat.data.*;
import org.bukkit.inventory.meta.*;
import me.xtasy.anticheat.check.*;

public class AngelXInventories
{
    private double tps;
    private Inventory hubInventory;
    private int size;
    private Inventory angelXChecksInventory;
    private ItemStack angelInfoBook;
    private ItemStack checksListEmrald;
    private DecimalFormat decFormat;
    private ItemStack alertsToggleGlow;

    public DecimalFormat getDecFormat() {
        return this.decFormat;
    }
    
    public Inventory getAngelXChecksInventory() {
        return this.angelXChecksInventory;
    }
    
    public Inventory getHubInventory() {
        return this.hubInventory;
    }
    
    public AngelXInventories(final Player player) {
        this.decFormat = new DecimalFormat("0.##");
        this.tps = ServerUtil.getTPS();
        try {
            this.loadHubInventory(player);

        }
        catch (Exception ex) {}
    }
    
    public ItemStack getAngelInfoBook() {
        return this.angelInfoBook;
    }
    
    public ItemStack getAlertsToggleGlow() {
        return this.alertsToggleGlow;
    }
    
    public ItemStack getChecksListEmrald() {
        return this.checksListEmrald;
    }
    
    public int getSize() {
        return this.size;
    }
    
    private void loadHubInventory(final Player player) {
        this.size = -2;
        final PlayerData data = AngelX.getApi().getPlayerDataManager().getData(player);
        this.hubInventory = Bukkit.createInventory((InventoryHolder)null, 27, "�5�lAngelX �7Menu");
        this.angelXChecksInventory = Bukkit.createInventory((InventoryHolder)null, 54, "�5�lAngelX �aChecks!");
        this.checksListEmrald = this.createItem(Material.EMERALD_BLOCK, 1, "�5�lAngelX �aChecks!");
        this.angelInfoBook = this.createItem(Material.BOOK, 1, "�5�lAngelX �7AntiCheat");
        final ItemMeta itemMeta = this.angelInfoBook.getItemMeta();
        final ArrayList<String> lore = new ArrayList<String>();
        final double n = this.tps * 5.0;
        lore.add(" ");
        lore.add("�7Server TPS � �a" + this.decFormat.format(this.tps) + " �7(�a" + this.decFormat.format(n) + "%�7)");
        lore.add("�7Online Players � �a" + Bukkit.getServer().getOnlinePlayers().size() + "�7/�c" + Bukkit.getServer().getMaxPlayers());
        lore.add("�7AngelX Version � �a" + AngelX.getApi().getPlugin().getDescription().getVersion());
        lore.add("�7Server Version � �a" + Bukkit.getServer().getVersion() + " �7(�a" + ServerUtil.getVersion() + "�7)");
        itemMeta.setLore((List)lore);
        this.angelInfoBook.setItemMeta(itemMeta);
        this.alertsToggleGlow = this.createItem(Material.GLOWSTONE_DUST, 1, "�7Alerts");
        this.hubInventory.setItem(10, this.checksListEmrald);
        this.hubInventory.setItem(13, this.angelInfoBook);
        this.hubInventory.setItem(16, this.alertsToggleGlow);
        data.getCheckRegistry().getChecks().forEach(this::loadHubInventory);
    }
    
    private ItemStack createItem(final Material material, final int n, final String displayName) {
        final ItemStack itemStack = new ItemStack(material, n);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public double getTps() {
        return this.tps;
    }
    
    private void loadHubInventory(final Check check) {
        ++this.size;
        this.angelXChecksInventory.setItem(this.size + 1, this.createItem(Material.STONE_SWORD, 1, check.getCheckName()));
    }
}
