package org.bobcatrobotics.Hardware.LedControllers;

public class LedStrip {
    public final int length;
    public final int startIndex;
    public final int endIndex;

    public LedStrip(int length, int start, int end) {
        this.length = length;
        if (start < 0 || end - start <= 0 || end > length || start > length) {
            throw new IllegalArgumentException("Check Parameters");
        }
        this.startIndex = start;
        this.endIndex = end;
    }
}
