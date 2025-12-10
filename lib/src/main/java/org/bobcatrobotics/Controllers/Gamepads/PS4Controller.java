package org.bobcatrobotics.Controllers.Gamepads;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class PS4Controller implements ControllerBase{
    private final edu.wpi.first.wpilibj.PS4Controller controller;

    private final Alert controllerUnpluggedAlert;
    public PS4Controller(int port, String name){
        controllerUnpluggedAlert =
        new Alert(name + " Joystick unplugged!", AlertType.kWarning);
        controller = new edu.wpi.first.wpilibj.PS4Controller(port);
    }
    public Trigger getButton(int map){
        Trigger tmp;
        switch(map){
            case 0:
                tmp = new Trigger(()->controller.getCircleButton());
            default:
                tmp = new Trigger(()->false);
        }
        return tmp;
    }
    public Trigger getLeftTrigger(){
        return new Trigger(()->controller.getL2Button());
    }
    public Trigger getRightTrigger(){
        return new Trigger(()->controller.getR2Button());
    }
    public Trigger getLeftBumper(){
        return new Trigger(()->controller.getL1Button());
    }
    public Trigger getRightBumper(){
        return new Trigger(()->controller.getR1Button());
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
    public Trigger getPovUp(){
        int pov = controller.getPOV();
        if(pov == 0){
            return new Trigger(()-> true);
        }
        return new Trigger(()-> false);
    }
    public Trigger getPovDown(){
        int pov = controller.getPOV();
        if(pov == 180){
            return new Trigger(()-> true);
        }
        return new Trigger(()-> false);
    }
    public Trigger getPovLeft(){
        int pov = controller.getPOV();
        if(pov == 90){
            return new Trigger(()-> true);
        }
        return new Trigger(()-> false);
    }
    public Trigger getPovRight(){
        int pov = controller.getPOV();
        if(pov == 270){
            return new Trigger(()-> true);
        }
        return new Trigger(()-> false);
    }
}
