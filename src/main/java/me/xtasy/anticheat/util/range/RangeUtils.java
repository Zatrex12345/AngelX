package me.xtasy.anticheat.util.range;

import me.xtasy.anticheat.data.*;
import me.xtasy.anticheat.util.location.*;

public class RangeUtils
{
    public static double getRange(final PlayerData playerData, final CustomLocation customLocation, final CustomLocation customLocation2) {
        if (playerData.getPastPositions().size() == 0) {
            return 0.0;
        }
        return playerData.getPastPositions().stream().mapToDouble(RangeUtils::getRange).min().orElse(0.0);
    }



    private static double getRange(final CustomLocation customLocation) {
        double n2;
        final double n = n2 = customLocation.getX() - (customLocation.getX() - 0.3);
        if (n < 0.0) {
            n2 = -n;
        }
        double n4;
        final double n3 = n4 = customLocation.getX() - (customLocation.getX() + 0.3);
        if (n3 < 0.0) {
            n4 = -n3;
        }
        final double min = Math.min(n2, n4);
        double n6;
        final double n5 = n6 = customLocation.getX() - (customLocation.getX() - 0.3);
        if (n5 < 0.0) {
            n6 = -n5;
        }
        double n8;
        final double n7 = n8 = customLocation.getX() - (customLocation.getX() + 0.3);
        if (n7 < 0.0) {
            n8 = -n7;
        }
        final double min2 = Math.min(min, Math.min(n6, n8));
        double n10;
        final double n9 = n10 = customLocation.getZ() - (customLocation.getZ() - 0.3);
        if (n9 < 0.0) {
            n10 = -n9;
        }
        double n12;
        final double n11 = n12 = customLocation.getZ() - (customLocation.getZ() + 0.3);
        if (n11 < 0.0) {
            n12 = -n11;
        }
        final double min3 = Math.min(n10, n12);
        double n14;
        final double n13 = n14 = customLocation.getZ() - (customLocation.getZ() - 0.3);
        if (n13 < 0.0) {
            n14 = -n13;
        }
        double n16;
        final double n15 = n16 = customLocation.getZ() - (customLocation.getZ() + 0.3);
        if (n15 < 0.0) {
            n16 = -n15;
        }
        return Math.sqrt(Math.pow(min2, 2.0) + Math.pow(Math.min(min3, Math.min(n14, n16)), 2.0)) - 0.004;
    }
}
