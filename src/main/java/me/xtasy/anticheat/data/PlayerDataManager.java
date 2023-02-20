package me.xtasy.anticheat.data;

import me.xtasy.anticheat.interf.*;
import java.util.*;
import org.bukkit.entity.*;
import java.util.function.*;
import java.util.concurrent.*;

public class PlayerDataManager implements Startable
{
    private final Map<Player, PlayerData> playerDataMap;
    
    public PlayerData getData(final Player player) {
        return this.playerDataMap.computeIfAbsent(player, PlayerData::new);
    }
    
    @Override
    public void start() {
    }
    
    public void destroyData(final Player player) {
        this.playerDataMap.remove(player);
    }
    
    public PlayerDataManager() {
        this.playerDataMap = new ConcurrentHashMap<Player, PlayerData>();
    }
}
