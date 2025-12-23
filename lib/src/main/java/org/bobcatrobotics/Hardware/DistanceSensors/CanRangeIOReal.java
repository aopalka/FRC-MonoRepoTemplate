package org.bobcatrobotics.Hardware.DistanceSensors;

import org.bobcatrobotics.Util.CANDeviceDetails;
import com.ctre.phoenix6.hardware.CANrange;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;

public class CanRangeIOReal implements DistanceSensorIO {
    private final Alert sensorUnpluggedAlert;
    private final CANDeviceDetails details;
    private final CANrange sensor;
    private double lastDistanceM = 0.0;
    private boolean valid = false;

    public CanRangeIOReal(CANDeviceDetails details) {
        this.details = details;
        sensor = new CANrange(details.id(), details.bus());
        sensorUnpluggedAlert =
                new Alert(details.subsystemName() + " Can Range" + " Joystick unplugged!",
                        AlertType.kWarning);
    }

    public void updateInputs(DistanceSensorIOInputs inputs) {
        inputs.distanceInMeters = getDistanceMeters();
        inputs.isValid = isValid();
        inputs.connected = sensor.isConnected();
        sensorUnpluggedAlert.set(!inputs.connected);
    }

    public double getDistanceMeters() {
        try {
            valid = true;
            lastDistanceM = sensor.getDistance().getValueAsDouble();
        } catch (Exception ex) {
            valid = false;
        }
        return lastDistanceM;
    }

    public boolean isValid() {
        return valid;
    }
    public boolean getStatus(){
        return sensor.isConnected();
    }
}
