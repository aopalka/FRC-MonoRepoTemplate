package frc.robot.lib.util;

import org.littletonrobotics.junction.networktables.LoggedNetworkString;

public class TunableString {
  private final String key;
  private boolean configUpdate = false;
  private LoggedNetworkString value;
  private String last;

  public TunableString(String key, String defaultVal) {
    value = new LoggedNetworkString(key, defaultVal);
    this.key = key;
    last = defaultVal;
  }

  public void periodic() {
    String current = value.get();
    if (current != last) {
      last = value.get();
      configUpdate = true;
    } else {
      configUpdate = false;
    }
  }

  public String get() {
    return value.get();
  }

  public boolean check() {
    return configUpdate;
  }
}