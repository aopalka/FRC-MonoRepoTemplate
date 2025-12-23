package org.bobcatrobotics.Hardware.DistanceSensors;

import org.bobcatrobotics.Util.CANDeviceDetails;
import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;

public class DistanceSensorIOSim implements DistanceSensorIO {
    private final Alert sensorUnpluggedAlert;
    private final CANDeviceDetails details;
    private final SimDevice simDevice;
    private final SimDouble simDistanceMeters;
    private final SimBoolean simValid;
    private double lastDistanceM = 0.0;
    private boolean valid = false;

    public DistanceSensorIOSim(CANDeviceDetails details) {
        this.details = details;
        sensorUnpluggedAlert =
                new Alert(details.subsystemName() + " TOF Range" + " Joystick unplugged!",
                        AlertType.kWarning);
        simDevice = SimDevice.create("DistanceSensor", details.id());
        if (simDevice != null) {
            simDistanceMeters =
                    simDevice.createDouble("DistanceMeters", SimDevice.Direction.kInput, 0.0);

            simValid = simDevice.createBoolean("Valid", SimDevice.Direction.kInput, true);
        } else {
            // Simulation disabled (e.g. real robot)
            simDistanceMeters = null;
            simValid = null;
        }
    }

    public void updateInputs(DistanceSensorIOInputs inputs) {
        if (simDevice == null) {
            inputs.isValid = false;
            inputs.distanceInMeters = simDistanceMeters.get();
            inputs.connected = false;
            return;
        }
        inputs.isValid = simValid.get();
        inputs.distanceInMeters = getDistanceMeters();
        inputs.connected = true;
        sensorUnpluggedAlert.set(!inputs.isValid);
    }

    public double getDistanceMeters() {
        try {
            valid = true;
            lastDistanceM = simDistanceMeters.get();
        } catch (Exception ex) {
            DriverStation.reportWarning("PWF TOF read failed: " + ex, false);
            valid = false;
        }
        return lastDistanceM;
    }

    public boolean isValid() {
        return valid;
    }

    /* ---------------- Optional helpers for tests/physics ---------------- */
    public void setDistanceMeters(double meters) {
        if (simDistanceMeters != null) {
            simDistanceMeters.set(meters);
        }
    }

    public void setValid(boolean valid) {
        if (simValid != null) {
            simValid.set(valid);
        }
    }
}
