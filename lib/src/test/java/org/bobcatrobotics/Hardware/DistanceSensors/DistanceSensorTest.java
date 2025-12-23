package org.bobcatrobotics.Hardware.DistanceSensors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.bobcatrobotics.Util.CANDeviceDetails;
import org.junit.jupiter.api.Test;
import edu.wpi.first.wpilibj.simulation.SimHooks;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class DistanceSensorTest {
    @Test
    void testFullConstructor() {
        DistanceSensor distanceSensor = new DistanceSensor(new DistanceSensorIOSim(new CANDeviceDetails(0)));
        assertEquals(false, distanceSensor.getStatus());
    }

    @Test
    void testDistanceSensor(){
        DistanceSensor distanceSensor = new DistanceSensor(new DistanceSensorIOSim(new CANDeviceDetails(0)));
        assertEquals(false, distanceSensor.getStatus());
        CommandScheduler scheduler = CommandScheduler.getInstance();
        // Run for 0.5 seconds → should still be running
        advanceTime(scheduler, 5);
        assertEquals(1, distanceSensor.getDistance());
    }
    
    private void advanceTime(CommandScheduler scheduler, double seconds) {
        final double step = 0.02; // 20ms like real robot
        int cycles = (int) Math.ceil(seconds / step);

        for (int i = 0; i < cycles; i++) {
            SimHooks.stepTiming(step);
            scheduler.run();
        }
    }
}
