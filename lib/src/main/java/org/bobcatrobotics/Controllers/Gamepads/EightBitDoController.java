package org.bobcatrobotics.Controllers.Gamepads;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

public class EightBitDoController  implements ControllerBase{
    CommandJoystick controller;
    private final Alert controllerUnpluggedAlert;
    public EightBitDoController(int port, String name){
        controllerUnpluggedAlert =
        new Alert(name + " Joystick unplugged!", AlertType.kWarning);
        controller = new CommandJoystick(port);
    }
    public Trigger getButton(int map){
        return switch (map) {
            case 2  -> controller.button(2);  // a
            case 1  -> controller.button(1);  // b
            case 4  -> controller.button(4);  // x
            case 3  -> controller.button(3);  // y
            case 7  -> controller.button(7);  // select
            case 8  -> controller.button(8);  // start
            default -> new Trigger(()-> false);
        };
    }
    public Trigger getLeftTrigger(){
        return controller.button(9);
    }
    public Trigger getRightTrigger(){
        return controller.button(10);
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
        return -controller.getRawAxis(3);
    }
    public void updateControllerAlerts() {
        controllerUnpluggedAlert.set(!controller.isConnected());
    }
    public Trigger getPovUp(){
        return controller.povUp();
    }
    public Trigger getPovDown(){
        return controller.povDown();
    }
    public Trigger getPovLeft(){
        return controller.povLeft();
    }
    public Trigger getPovRight(){
        return controller.povRight();
    }
}
