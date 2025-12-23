package org.bobcatrobotics.Hardware.DistanceSensors;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Millimeters;
import org.bobcatrobotics.Util.CANDeviceDetails;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;
import com.playingwithfusion.TimeOfFlight;

public class TofRangeIOReal implements DistanceSensorIO {
    private final Alert sensorUnpluggedAlert;
    private final CANDeviceDetails details;
    private final TimeOfFlight sensor;
    private double lastDistanceM = 0.0;
    private boolean valid = false;

    public TofRangeIOReal(CANDeviceDetails details) {
        this.details = details;
        sensor = new TimeOfFlight(details.id());
        sensorUnpluggedAlert =
                new Alert(details.subsystemName() + " TOF Range" + " Joystick unplugged!",
                        AlertType.kWarning);
    }

    public void updateInputs(DistanceSensorIOInputs inputs) {
        inputs.distanceInMeters = getDistanceMeters();
        inputs.isValid = isValid();
        inputs.connected = inputs.isValid;
        sensorUnpluggedAlert.set(!inputs.isValid);
    }

    public double getDistanceMeters() {
        try {
            valid = true;
            lastDistanceM = Millimeters.of(sensor.getRange()).in(Meters);
        } catch (Exception ex) {
            DriverStation.reportWarning("PWF TOF read failed: " + ex, false);
            valid = false;
        }
        return lastDistanceM;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean getStatus(){
        return isValid();
    }
}
