package org.bobcatrobotics.Commands;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.bobcatrobotics.Commands.ActionState;
import org.littletonrobotics.junction.Logger;

public class ActionFactory {
    public boolean hasTimeout = false;
    public boolean hasCondition = false;

    /**
     * An action that is designed to be scheduled once.
     * Great for triggering an method once. Powered by the {@link IntantCommand } command type from wpilib.
     * This should be used when you want the subsystem to run even when another action that uses it is running.
     * 
     * @param commandName
     * @param runBehaviour
     * @return Command that should be called and scheduled once
     */
    public Command singleAction(String commandName, Runnable runBehaviour) {
        return new InstantCommand(runBehaviour).withName(commandName);
    }

    /**
     * An action that is designed to be scheduled once.
     * Great for triggering an method once. Powered by the {@link runOnce } command type from wpilib.
     * This should be used when you want to require the subsystem so that if another subsystem action is called it will end the current action.
     * 
     * @param commandName
     * @param runBehaviour
     * @param subsystems
     * @return Command that should be called and scheduled once
     */
    public Command singleAction(String commandName, Runnable runBehaviour,
            Subsystem... subsystems) {
        return Commands.runOnce(runBehaviour, subsystems).withName(commandName);
    }
    /**
     * An action that is scheduled once and continuously executed.
     * Great for triggering an method once. Powered by the {@link RunCommand } command type from wpilib.
     * This should be used when you want the subsystem to run even when another action that uses it is running.
     * 
     * Note that this defines what this should execute when it is interrupted.
     * 
     * @param commandName
     * @param runBehaviour
     * @param onInterrupt
     * @return Command that should be called and scheduled once
     */
    public Command continuousAction(String commandName, Runnable runBehaviour,
            Runnable onInterrupt) {
        return new RunCommand(runBehaviour).withName(commandName).handleInterrupt(onInterrupt);
    }
    /**
     * An action that is scheduled once and continuously executed.
     * Great for triggering an method once. Powered by the {@link run } command type from wpilib.
     * This should be used when you want to require the subsystem so that if another subsystem action is called it will end the current action.
     * 
     * Note that this defines what this should execute when it is interrupted.
     * 
     * @param commandName
     * @param runBehaviour
     * @param onInterrupt
     * @param subsystems
     * @return Command that should be called and scheduled once
     */
    public Command continuousAction(String commandName, Runnable runBehaviour, Runnable onInterrupt,
            Subsystem... subsystems) {
        return new RunCommand(runBehaviour, subsystems).withName(commandName)
                .handleInterrupt(onInterrupt);
    }

    public Command addTimeout(Command action, double time) {
        hasTimeout = true;
        return action.withTimeout(time);
    }

    public Command addCondition(Command action, BooleanSupplier condition) {
        hasCondition = true;
        return action.until(condition);
    }

    public Command logCommand(Command action, String keyName){
        Command logInitialization = singleAction(action.getName(), ()-> {
            Logger.recordOutput(action.getSubsystem() + "/" + keyName, ActionState.Initialized);
        });
        Command logExecuting = singleAction(action.getName(), ()-> {
            Logger.recordOutput(action.getSubsystem() + "/" + keyName, ActionState.Executing);
        });
        Command logCommand = logExecuting.beforeStarting(logInitialization).finallyDo(()-> {
            Logger.recordOutput(action.getSubsystem() + "/" + keyName, ActionState.Finished);
        });
        action.alongWith(logCommand);
        return action;
    }
}
