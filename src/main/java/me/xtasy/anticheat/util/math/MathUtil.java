package me.xtasy.anticheat.util.math;

import org.bukkit.util.*;
import org.bukkit.util.Vector;

import java.math.*;
import java.util.*;

public class MathUtil
{
    public static final Random RANDOM;

    
    public static Vector getDirection(final float n, final float n2) {
        final Vector vector = new Vector();
        final float n3 = (float)Math.toRadians(n);
        final float n4 = (float)Math.toRadians(n2);
        vector.setY(-Math.sin(n4));
        final double cos = Math.cos(n4);
        vector.setX(-cos * Math.sin(n3));
        vector.setZ(cos * Math.cos(n3));
        return vector;
    }
    
    public static double hypot(final double... array) {
        return Math.sqrt(hypotSquared(array));
    }
    
    public static double average(final Number... array) {
        return average(Arrays.asList(array));
    }
    
    static {
        RANDOM = new Random();
    }
    
    public static float getDistanceBetweenAngles(final float n, final float n2) {
        float n4;
        final float n3 = n4 = n - n2;
        if (n3 < 0.0f) {
            n4 = -n3;
        }
        float n5 = n4 % 360.0f;
        if (n5 > 180.0f) {
            n5 = 360.0f - n5;
        }
        return n5;
    }
    
    public static int gcd(final int n, final int n2) {
        return BigInteger.valueOf(n).gcd(BigInteger.valueOf(n2)).intValue();
    }
    
    public static double getMagnitude(final double n, final double n2) {
        return Math.sqrt(n * n + n2 * n2);
    }
    
    public static double gcd(final double n, final double n2, final double n3) {
        double gcd;
        if (n3 <= n) {
            gcd = n2;

        }
        else {
            gcd = gcd(n, n3, n2 % n3);
        }
        return gcd;
    }
    
    public static double[] dequeTranslator(final Collection<? extends Number> collection) {
        return collection.stream().mapToDouble(Number::doubleValue).toArray();
    }
    
    public static int pingFormula(final long n) {
        return (int)Math.ceil(n / 2L / 50.0) + 2;
    }
    
    public static double getDistanceBetweenAngles360(final double n, final double n2) {
        double n4;
        final double n3 = n4 = n % 360.0 - n2 % 360.0;
        if (n3 < 0.0) {
            n4 = -n3;
        }
        final double n5 = n4;
        double min;
        final double n6 = min = Math.min(360.0 - n5, n5);
        if (n6 < 0.0) {
            min = -n6;
        }
        return min;
    }
    
    public static long gcd(final long n, final long n2, final long n3) {
        long gcd;
        if (n3 <= n) {
            gcd = n2;

        }
        else {
            gcd = gcd(n, n3, n2 % n3);
        }
        return gcd;
    }
    
    public static double angle(final Vector vector, final Vector vector2) {
        return Math.acos(Math.min(Math.max(vector.dot(vector2) / (vector.length() * vector2.length()), -1.0), 1.0));
    }
    
    public static double hypotSquared(final double... array) {
        double n = 0.0;
        final int length = array.length;
        int i = 0;
        while (i < length) {
            n += Math.pow(array[i], 2.0);
            ++i;

        }
        return n;
    }
    
    public static double round(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return BigDecimal.valueOf(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double average(final Iterable<? extends Number> iterable) {
        double n = 0.0;
        int n2 = 0;
        final Iterator<? extends Number> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            n += ((Number)iterator.next()).doubleValue();
            ++n2;

        }
        return n / n2;
    }
}
