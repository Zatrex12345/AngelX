package me.xtasy.anticheat.util.license;

import org.bukkit.plugin.*;
import org.bukkit.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class LicenseSystem
{
    private String licenseKey;
    private String securityKey;
    private String validationServer;
    private boolean debug;
    private LogType logType;
    private Plugin plugin;

    
    public LicenseSystem setConsoleLog(final LogType logType) {
        this.logType = logType;
        return this;
    }
    
    public LicenseSystem(final String licenseKey, final String validationServer, final Plugin plugin) {
        this.logType = LogType.NORMAL;
        this.securityKey = "jJ6giJGAEhLCgij30lKga9tXX";
        this.debug = false;
        this.licenseKey = licenseKey;
        this.plugin = plugin;
        this.validationServer = validationServer;
    }
    
    public boolean isValidSimple() {
        boolean b;
        if (this.isValid() == ValidationType.VALID) {
            b = true;

        }
        else {
            b = false;
        }
        return b;
    }
    
    private void log(final int n, final String s) {
        if (this.logType == LogType.NONE || (this.logType == LogType.LOW && n == 0)) {
            return;
        }
        System.out.println(s);
    }
    
    public LicenseSystem setSecurityKey(final String securityKey) {
        this.securityKey = securityKey;
        return this;
    }
    
    private String toBinary(final String s) {
        final byte[] array = s.getBytes();
        final StringBuilder sb = new StringBuilder();
        final byte[] array2 = array;
        final int length = array2.length;
        int i = 0;
        while (i < length) {
            int n = array2[i];
            int j = 0;
            while (j < 8) {
                final StringBuilder sb2 = sb;
                int n2;
                if ((n & 0x80) == 0x0) {
                    n2 = 0;

                }
                else {
                    n2 = 1;
                }
                sb2.append(n2);
                n <<= 1;
                ++j;

            }
            ++i;

        }
        return sb.toString();
    }
      // REMOVE THIS SHIT

    private static String xor(final String s, final String s2) {
        String string = "";
        int n = 0;

            final int n2 = n;
            int n3;
            if (s.length() < s2.length()) {
                n3 = s.length();

            }
            else {
                n3 = s2.length();
            }
            if (n2 >= n3) {
                return string;
            }
            string += (Byte.valueOf("" + s.charAt(n)) ^ Byte.valueOf("" + s2.charAt(n)));
            ++n;


        return string;
    }

    public boolean register() {
        if (this.isValid() == ValidationType.VALID) {
            return true;
        }
        Bukkit.getScheduler().cancelTasks(this.plugin);
        Bukkit.getPluginManager().disablePlugin(this.plugin);
        Bukkit.shutdown();
        return false;
    }
    
    public ValidationType isValid() {
        final String binary = this.toBinary(UUID.randomUUID().toString());
        final String binary2 = this.toBinary(this.securityKey);
        final String binary3 = this.toBinary(this.licenseKey);
        try {
            final URL url = new URL(this.validationServer + "?v1=" + xor(binary, binary2) + "&v2=" + xor(binary, binary3) + "&pl=" + this.plugin.getName());
            if (this.debug) {
                System.out.println("RequestURL -> " + url.toString());
            }
            final Scanner scanner = new Scanner(url.openStream());
            if (scanner.hasNext()) {
                final String next = scanner.next();
                scanner.close();
                try {
                    return ValidationType.valueOf(next);
                }
                catch (IllegalArgumentException ex2) {
                    final String xor = xor(xor(next, binary3), binary2);
                    if (binary.substring(0, xor.length()).equals(xor)) {
                        return ValidationType.VALID;
                    }
                    return ValidationType.WRONG_RESPONSE;
                }
            }
            scanner.close();
            return ValidationType.PAGE_ERROR;
        }
        catch (IOException ex) {
            if (this.debug) {
                ex.printStackTrace();
            }
            return ValidationType.URL_ERROR;
        }
    }
    
    public LicenseSystem debug() {
        this.debug = true;
        return this;
    }
    
    public enum ValidationType
    {
        URL_ERROR, 
        WRONG_RESPONSE, 
        KEY_OUTDATED, 
        KEY_NOT_FOUND, 
        PAGE_ERROR, 
        INVALID_PLUGIN, 
        NOT_VALID_IP, 
        VALID;
        
        private static final ValidationType[] $VALUES;
        
        static {
            $VALUES = new ValidationType[] { ValidationType.WRONG_RESPONSE, ValidationType.PAGE_ERROR, ValidationType.URL_ERROR, ValidationType.KEY_OUTDATED, ValidationType.KEY_NOT_FOUND, ValidationType.NOT_VALID_IP, ValidationType.INVALID_PLUGIN, ValidationType.VALID };
        }
    }
    
    public enum LogType
    {
        NORMAL,
        NONE,
        LOW;

        private static final LogType[] $VALUES;
        

        
        static {
            $VALUES = new LogType[] { LogType.NORMAL, LogType.LOW, LogType.NONE };
        }
    }
}
