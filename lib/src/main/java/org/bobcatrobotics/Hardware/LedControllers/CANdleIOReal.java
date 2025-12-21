package org.bobcatrobotics.Hardware.LedControllers;

import com.ctre.phoenix6.configs.CANdleConfiguration;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;
import com.ctre.phoenix6.signals.StripTypeValue;

public class CANdleIOReal implements LedControllerIO{
    private final CANdle candle;
    public LedStrip strip;

    private int r, g, b, w, start, end;

    public CANdleIOReal(int canId, LedStrip strip , StripTypeValue stripType) {
        candle = new CANdle(canId);
        this.strip = strip;

        CANdleConfiguration cfg = new CANdleConfiguration();
        cfg.LED.StripType = stripType;
        candle.getConfigurator().apply(cfg);
    }

    public void setLEDs(int r, int g, int b, int w, int startIdx, int endIdx) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.w = w;
        this.start = startIdx;
        this.end = endIdx;
        SolidColor color = new SolidColor(startIdx, endIdx - startIdx);
        RGBWColor rgbwColor = new RGBWColor(r, g, b, w);
        color.withColor(rgbwColor);
        candle.setControl(color);
    }

    public void updateInputs(LedControllerIOInputs inputs) {
        inputs.red = r;
        inputs.green = g;
        inputs.blue = b;
        inputs.white = w;
        inputs.startIdx = start;
        inputs.endIdx = end;
        inputs.count = end - start;
    }

    public LedStrip getStrip(){
        return strip;
    }
    public int getCount(){
        return strip.length;
    }
}
