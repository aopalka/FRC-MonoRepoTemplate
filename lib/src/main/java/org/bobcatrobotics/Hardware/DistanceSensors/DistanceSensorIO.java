package org.bobcatrobotics.Hardware.DistanceSensors;

import org.littletonrobotics.junction.AutoLog;

public interface DistanceSensorIO {
    @AutoLog
    public static class DistanceSensorIOInputs{
        public double distanceInMeters = 0.00;
        public boolean isValid = false;
        public boolean connected = false;
    }
    public default void updateInputs(DistanceSensorIOInputs inputs) {}
    public default double getDistanceMeters() { return 0.00;}
    public default boolean isValid() {return false;}
    public default boolean getStatus() {return false;}
}
