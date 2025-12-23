package org.bobcatrobotics.Hardware.DistanceSensors;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DistanceSensor extends SubsystemBase {
    private final DistanceSensorIO io;
    private final DistanceSensorIOInputsAutoLogged inputs = new DistanceSensorIOInputsAutoLogged();

    public DistanceSensor( DistanceSensorIO io){
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Distance Sensor", inputs);
    }

    public double getDistance(){
        return io.getDistanceMeters();
    }

    public boolean getStatus(){
        return io.getStatus();
    }

}
