package org.bobcatrobotics.Controllers.Gamepads;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class XboxController  implements ControllerBase{
    private final CommandXboxController controller;
    private final Alert controllerUnpluggedAlert;
    public XboxController(int port, String name){
        controllerUnpluggedAlert =
        new Alert(name + " Joystick unplugged!", AlertType.kWarning);
        controller = new CommandXboxController(port);
    }
    public Trigger getButton(int map){
        return controller.button(map);
    }
    public Trigger getLeftTrigger(){
        return controller.leftTrigger(0.1);
    }
    public Trigger getRightTrigger(){
        return controller.rightTrigger(0.1);
    }
    public Trigger getLeftBumper(){
        return controller.leftBumper();
    }
    public Trigger getRightBumper(){
        return controller.rightBumper();
    }
    public double getLeftX(){
        return controller.getLeftX();
    }
    public double getRightX(){
        return controller.getRightX();
    }
    public double getLeftY(){
        return controller.getLeftY();
    }
    public double getRightY(){
        return controller.getRightY();
    }
    public void updateControllerAlerts() {
        controllerUnpluggedAlert.set(!controller.isConnected());
    }
}
