package org.bobcatrobotics.Controllers.Joysticks;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class GenericHIDJoystick implements JoystickBase{
    private final GenericHID controller;
    private final Alert joystickUnpluggedAlert;

    public GenericHIDJoystick(int port, String name){
        joystickUnpluggedAlert =
      new Alert(name + " Joystick unplugged!", AlertType.kWarning);
        controller = new GenericHID(port);
    }
    public Trigger getRawButton(int map){
        return switch (map) {
            default -> new Trigger(()->false);
        };
    }
    public double getStickX(){
        return controller.getRawAxis(1);
    }
    public double getStickY(){
        return controller.getRawAxis(2);
    }
    public double getStickZ(){
        return controller.getRawAxis(3);
    }
    public double getFineStickX(){
        return controller.getRawAxis(1);
    }
    public double getFineStickY(){
        return controller.getRawAxis(2);
    }
    public double getFineStickZ(){
        return controller.getRawAxis(2);
    }
    public void updateControllerAlerts() {
        joystickUnpluggedAlert.set(!controller.isConnected());
    }
}

