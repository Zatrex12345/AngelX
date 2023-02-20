package me.xtasy.anticheat.util.message;

public class LogUtil
{
    public static void log(final String s) {
        System.out.println("[AngelX] " + s);
    }
    
    private LogUtil() {
        throw new IllegalStateException("Utility classes can not be initialised");
    }
}
