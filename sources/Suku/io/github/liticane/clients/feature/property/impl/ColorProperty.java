package io.github.liticane.clients.feature.property.impl;

import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.Property;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.function.Supplier;

@Getter
@Setter
public class ColorProperty extends Property {
    private float hue = 0;
    private float saturation = 1;
    private float brightness = 1;

    public ColorProperty(String name, Module module, Color color) {
        this.name = name;
        this.setColor(color);

        module.getProperties().add(this);
    }

    public ColorProperty(String name, Module module, Color color, Supplier<Boolean> shown) {
        this.name = name;
        this.visible = shown;
        this.setColor(color);

        module.getProperties().add(this);
    }

    public Color getColor() {
        return Color.getHSBColor(hue, saturation, brightness);
    }

    public void setColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
    }

    public void setColor(float hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

}