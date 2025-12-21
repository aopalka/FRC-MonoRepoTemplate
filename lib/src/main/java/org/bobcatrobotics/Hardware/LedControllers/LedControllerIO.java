package org.bobcatrobotics.Hardware.LedControllers;

import org.littletonrobotics.junction.AutoLog;

public interface LedControllerIO{
    @AutoLog
    public static class LedControllerIOInputs {
        public int red = 0;
        public int green = 0;
        public int blue = 0;
        public int white = 0;
    
        public int startIdx = 0;
        public int endIdx = 0;
        public int count = 0;
    
        public boolean running = false;
        public String animation = "None";
        public double brightnessScale = 1.0;
    }
    public default void setLEDs(int r, int g, int b, int w, int startIdx, int endIdx) {}
    public default void updateInputs(LedControllerIOInputs inputs) {}
    public default  LedStrip getStrip(){
        return null;
    }
    public default int getCount(){
        return 0;
    }
}
