package me.xtasy.anticheat.util.player;

import java.io.*;

public class StdUtils implements Serializable
{
    private double variance;
    private int windowCount;
    private final double[] elements;
    private int currentElement;

    public StdUtils(final int n) {
        this.elements = new double[n];
        this.variance = n * 2.5;
        int i = 0;
        while (i < this.elements.length) {
            this.elements[i] = n * 2.5 / n;
            ++i;

        }
    }
    
    public void add(double n) {
        n /= this.elements.length;
        this.variance -= this.elements[this.currentElement];
        this.variance += n;
        this.elements[this.currentElement] = n;
        this.currentElement = (this.currentElement + 1) % this.elements.length;
    }
    
    public double getStdDev(final double n) {
        final double sqrt = Math.sqrt(this.variance);
        if (sqrt >= n) {
            if (this.windowCount > 0) {
                this.windowCount = 0;
            }
            return n;
        }
        if (++this.windowCount > this.elements.length) {
            return sqrt;
        }
        return Double.NaN;
    }
}
