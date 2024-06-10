package Snipeware.value.impl;

import java.awt.*;

import Snipeware.value.Value;

public class ColorValue extends Value<Integer> {

    public ColorValue(String label, int value) {
        super(label, value, ValueType.Color);
    }

    @Override
    public void setValue(String value) {

    }
    
    public Color getColor() {
        return new Color(getValue());
    }
}
