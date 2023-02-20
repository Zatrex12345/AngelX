package me.xtasy.anticheat.util.config;

import org.bukkit.configuration.file.*;
import me.xtasy.anticheat.*;
import java.io.*;

public class ConfigManger
{
    public static YamlConfiguration ChecksFile;
    
    public static void makeConfig() throws IOException {
        final File file = new File(AngelX.getApi().getPlugin().getDataFolder(), "checksFile.yml");
        ConfigManger.ChecksFile = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            file.createNewFile();
            ConfigManger.ChecksFile.save(file);
        }
    }
}
