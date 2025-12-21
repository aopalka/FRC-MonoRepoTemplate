package org.bobcatrobotics.Hardware.LedControllers.Animations;

import java.util.function.BooleanSupplier;
import org.bobcatrobotics.Hardware.LedControllers.LedStrip;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Interface for LED animations that can be controlled through lifecycle methods.
 * 
 * <p>
 * All animations implement this interface to provide consistent control over starting, pausing, and
 * stopping animations. Animations are backed by WPILib Commands that are scheduled to the command
 * scheduler.
 */
public interface Animation {
    /**
     * Starts or resumes the animation.
     * 
     * <p>
     * If the animation is not currently scheduled, this schedules it to the command scheduler. If
     * already running, this has no effect. For countdown animations, calling run() after completion
     * restarts the timer.
     */
    void run();

    /**
     * Pauses the animation while maintaining the current LED state.
     * 
     * <p>
     * This cancels the underlying command but does not turn off the LEDs, leaving them in their
     * current state. Call run() to resume the animation.
     */
    void stop();

    /**
     * Stops the animation and turns off all LEDs in the strip.
     * 
     * <p>
     * This cancels the underlying command and sets all LEDs in the animation's strip segment to off
     * (0, 0, 0, 0). This provides a clean exit from the animation.
     */
    void end();

    /**
     * Checks if the animation is currently running.
     * 
     * @return A boolean indicating if the animation is active
     */
    public default boolean isRunning(){return false;}

    public default Command untill(BooleanSupplier condition) {
        return null;
    }

    public default Command withTimeout(double seconds) {
        return null;
    }
}
