package org.bobcatrobotics.Controllers.Joysticks;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class LogitechJoystick implements JoystickBase{
    private CommandJoystick controller;
    private final Alert joystickUnpluggedAlert;
    public LogitechJoystick(int port, String name){
        joystickUnpluggedAlert =
      new Alert(name + " Joystick unplugged!", AlertType.kWarning);
        controller = new CommandJoystick(port);
    }
    public Trigger getRawButton(int map){
        return switch (map) {
            case 1      -> controller.button(1);
            case 2        -> controller.button(2);
            case 6    -> controller.button(6);
            case 5     -> controller.button(5);
            case 4 -> controller.button(4);
            case 3  -> controller.button(3);
            case 7     -> controller.button(7);
            case 8     -> controller.button(8);
            case 9     -> controller.button(9);
            case 10    -> controller.button(10);
            case 11    -> controller.button(11);
            case 12    -> controller.button(12);
            default -> new Trigger(()->false);
        };
    }
    public double getStickX(){
        return controller.getRawAxis(0);
    }
    public double getStickY(){
        return controller.getRawAxis(1);
    }
    public double getStickZ(){
        return controller.getRawAxis(2);
    }
    public double getFineStickX(){
        return controller.getRawAxis(0);
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
