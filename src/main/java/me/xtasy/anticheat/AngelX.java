package me.xtasy.anticheat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.*;
import me.xtasy.anticheat.api.*;

import java.util.logging.Logger;

    // ONLY for SKID NO BUILD THIS SHIT <>  I THINK ITS NOT WORK I GOT SKIPPED MANY METHODS
// im lazy to fix the colors xd

public class AngelX extends JavaPlugin
{
    private static AntiCheatAPI api;
    
    public void onEnable() {
        (AngelX.api = new AntiCheatAPI(this)).start();
        System.out.println(">>Welcome TO BEst AntiShit<<");
    }
    
    public static AntiCheatAPI getApi() {
        return AngelX.api;
    }
    
    public void onDisable() {
    }
}
