package org.bobcatrobotics.Hardware.LedControllers.Animations;

import java.util.function.BooleanSupplier;
import org.bobcatrobotics.Hardware.LedControllers.LedControllerIO;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Breathe implements Animation {
    public boolean isFinished = false;
    private LedControllerIO ledController;
    private double frequency;
    private double dimmness;
    private double phaseShift;
    private Color color;

    public Breathe(LedControllerIO ledController, Color color) {
        this.ledController = ledController;
        this.color = color;
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
        if (isFinished) {
            return;
        }
        long currentTimeMs = System.currentTimeMillis();
        double periodMs = 1000.0 / frequency;
        double timeInPeriod = currentTimeMs % periodMs;
        double phase = (timeInPeriod / periodMs) * 2 * Math.PI + phaseShift;
        double brightness = (Math.sin(phase) + 1) / 2;
        double scale = dimmness + (brightness * (1.0 - dimmness));
        ledController.setLEDs((int) (color.red * scale), (int) (color.green * scale),
                (int) (color.blue * scale), 0, ledController.getStrip().startIndex, ledController.getStrip().endIndex);
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
