package org.bobcatrobotics.Hardware.LedControllers.Animations;

import java.util.function.BooleanSupplier;
import org.bobcatrobotics.Hardware.LedControllers.LedControllerIO;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Blink implements Animation {
    public boolean isFinished = false;
    LedControllerIO ledController;
    long startTimeMs = 0;
    private Color color;
    private long period;

    public Blink(LedControllerIO ledController, Color color, long period) {
        this.ledController = ledController;
        this.color = color;
        this.startTimeMs = System.currentTimeMillis();
        this.period = period;
    }

    /**
     * Starts or resumes the animation.
     * 
     * <p>
     * If the animation is not currently scheduled, this schedules it to the command scheduler. If
     * already running, this has no effect. For countdown animations, calling run() after completion
     * restarts the timer.
     */
    public void run() {
        if(isFinished){
            return;
        }
        long currentTimeMs = System.currentTimeMillis();
        ledController.setLEDs((int) (color.red), (int) (color.green ),
                (int) (color.blue ), 0, ledController.getStrip().startIndex, ledController.getStrip().endIndex);
        if( (currentTimeMs - startTimeMs) >= period ){
            isFinished = true;
        }
    }

    /**
     * Pauses the animation while maintaining the current LED state.
     * 
     * <p>
     * This cancels the underlying command but does not turn off the LEDs, leaving them in their
     * current state. Call run() to resume the animation.
     */
    public void stop() {
        ledController.setLEDs((int) (color.red), (int) (color.green), (int) (color.blue), 0,
                ledController.getStrip().startIndex, ledController.getStrip().endIndex);
    }

    /**
     * Stops the animation and turns off all LEDs in the strip.
     * 
     * <p>
     * This cancels the underlying command and sets all LEDs in the animation's strip segment to off
     * (0, 0, 0, 0). This provides a clean exit from the animation.
     */
    public void end() {
        ledController.setLEDs(0, 0, 0, 0, ledController.getStrip().startIndex, ledController.getStrip().endIndex);
        isFinished = true;
    }

    /**
     * Checks if the animation is currently running.
     * 
     * @return A boolean indicating if the animation is active
     */
    public boolean isRunning() {
        return isFinished;
    }

    public Command untill(BooleanSupplier condition) {
        return Commands.run(() -> run())
                .unless(condition).andThen(() -> end());
    }

    public Command withTimeout(double seconds) {
        return Commands.run(() -> run())
                .withTimeout(seconds).andThen(() -> end());
    }
}
