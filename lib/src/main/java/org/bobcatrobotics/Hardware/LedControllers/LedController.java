package org.bobcatrobotics.Hardware.LedControllers;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class LedController extends SubsystemBase {
    private final LedControllerIO io;
    private final LedControllerIOInputsAutoLogged inputs =
            new LedControllerIOInputsAutoLogged();

    public LedController(LedControllerIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Led Controller", inputs);
    }

    public void setLEDs(int r, int g, int b, int w, int startIdx, int endIdx) {
        inputs.animation = "Solid";
        inputs.running = true;
        inputs.brightnessScale = 1.0;
        io.setLEDs(r, g, b, w, startIdx, endIdx - startIdx);
    }

    public void setAnimation(){

    }

    public void off(int startIdx, int endIdx) {
        inputs.animation = "Off";
        inputs.running = false;
        io.setLEDs(0, 0, 0,0, startIdx, endIdx - startIdx);
    }

    public int getCount(){
        return io.getCount();
    }
}
