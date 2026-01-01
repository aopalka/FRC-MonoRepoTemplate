package org.bobcatrobotics.Hardware.DistanceSensors;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Millimeters;

import org.bobcatrobotics.Util.CANDeviceDetails;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;
import com.playingwithfusion.TimeOfFlight;

/**
 * Real hardware implementation of a Time-of-Flight (TOF) distance sensor using the
 * Playing With Fusion TimeOfFlight sensor.
 * <p>
 * Provides methods to read distance in meters, check validity, and update sensor status.
 * Alerts when the sensor is disconnected.
 * </p>
 */
public class TofRangeIOReal implements DistanceSensorIO {

    /** Alert displayed when the sensor is unplugged or disconnected. */
    private final Alert sensorUnpluggedAlert;

    /** Device details, including ID and subsystem name. */
    private final CANDeviceDetails details;

    /** Underlying PWF TimeOfFlight sensor instance. */
    private final TimeOfFlight sensor;

    /** Last read distance in meters. */
    private double lastDistanceM = 0.0;

    /** Flag indicating whether the sensor reading is valid. */
    private boolean valid = false;

    /**
     * Constructs a new TOF range sensor interface.
     *
     * @param details CANDeviceDetails containing ID and subsystem information for the sensor
     */
    public TofRangeIOReal(CANDeviceDetails details) {
        this.details = details;
        sensor = new TimeOfFlight(details.id());
        sensorUnpluggedAlert =
                new Alert(details.subsystemName() + " TOF Range" + " Joystick unplugged!",
                        AlertType.kWarning);
    }

    /**
     * Updates the inputs structure with the current sensor readings.
     *
     * @param inputs the {@link DistanceSensorIO.DistanceSensorIOInputs} object to update
     */
    public void updateInputs(DistanceSensorIOInputs inputs) {
        inputs.distanceInMeters = getDistanceMeters();
        inputs.isValid = isValid();
        inputs.connected = inputs.isValid;
        sensorUnpluggedAlert.set(!inputs.isValid);
    }

    /**
     * Reads the current distance from the sensor in meters.
     *
     * @return distance in meters; returns last valid distance if a read fails
     */
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

    /**
     * Returns whether the sensor reading is currently valid.
     *
     * @return true if the last reading was successful; false otherwise
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns the status of the sensor.
     * <p>
     * For this sensor, status is equivalent to {@link #isValid()}.
     * </p>
     *
     * @return true if the sensor reading is valid; false otherwise
     */
    public boolean getStatus() {
        return isValid();
    }

    /**
     * Simulates setting the distance for testing purposes.
     * <p>
     * This implementation does nothing because it represents a real hardware sensor.
     * </p>
     *
     * @param distanceInMeters distance to simulate
     */
    public void simSetDistance(double distanceInMeters) {
        // No-op for real sensor
    }
}
