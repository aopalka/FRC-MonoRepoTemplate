package org.bobcatrobotics.Controllers.Gamepads;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class LogitechController {
    CommandJoystick controller;
    private final Alert controllerUnpluggedAlert;
    public LogitechController(int port, String name){
        controllerUnpluggedAlert =
        new Alert(name + " Joystick unplugged!", AlertType.kWarning);
        controller = new CommandJoystick(port);
    }

    public Trigger getButton(int map) {
        return switch (map) {
            case 1 -> controller.button(1); // a
            case 2 -> controller.button(2); // b
            case 3 -> controller.button(3); // x
            case 4 -> controller.button(4); // y
            case 9 -> controller.button(9); // select
            case 10 -> controller.button(10); // start
            default -> new Trigger(() -> false);
        };
    }
    public Trigger getLeftTrigger(){
        return new Trigger(()-> false);
    }
    public Trigger getRightTrigger(){
        return new Trigger(()-> false);
    }
    public Trigger getLeftBumper(){
        return controller.button(5);
    }
    public Trigger getRightBumper(){
        return controller.button(6);
    }
    public double getLeftX(){
        return controller.getRawAxis(0);
    }
    public double getRightX(){
        return controller.getRawAxis(4);
    }
    public double getLeftY(){
        return -controller.getRawAxis(1);
    }
    public double getRightY(){
        return -controller.getRawAxis(5);
    }
    public void updateControllerAlerts() {
        controllerUnpluggedAlert.set(!controller.isConnected());
    }
}
