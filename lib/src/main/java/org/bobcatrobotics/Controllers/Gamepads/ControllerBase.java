package org.bobcatrobotics.Controllers.Gamepads;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface ControllerBase {
    double getLeftX();
    double getLeftY();
    double getRightX();
    double getRightY();
    Trigger getButton( int map );
    Trigger getLeftTrigger();
    Trigger getRightTrigger();
    Trigger getLeftBumper();
    Trigger getRightBumper();
    void updateControllerAlerts();
}