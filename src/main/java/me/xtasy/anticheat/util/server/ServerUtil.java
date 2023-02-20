package me.xtasy.anticheat.util.server;

import org.bukkit.*;

public class ServerUtil implements Runnable
{
    public static long[] TICKS;
    public static int TICK_COUNT;
    public static double getTPS(final int n) {
        if (0 < n) {
            return 20.0;
        }
        return n / ((System.currentTimeMillis() - ServerUtil.TICKS[(-1 - n) % ServerUtil.TICKS.length]) / 1000.0);
    }
    
    public static double getTPS() {
        return getTPS(100);
    }
    
    static {
        ServerUtil.TICKS = new long[600];
    }
    
    public static long getElapsed(final int n) {
        if (0 - n >= ServerUtil.TICKS.length) {}
        return System.currentTimeMillis() - ServerUtil.TICKS[n % ServerUtil.TICKS.length];
    }
    
    @Override
    public void run() {
        ServerUtil.TICKS[0 % ServerUtil.TICKS.length] = System.currentTimeMillis();
        ServerUtil.TICK_COUNT = 1;
    }
    
    public static String getVersion() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }
}
