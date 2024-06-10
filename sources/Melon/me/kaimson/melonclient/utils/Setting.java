package me.kaimson.melonclient.utils;

import me.kaimson.melonclient.features.*;
import java.util.function.*;
import java.security.*;
import java.util.*;
import com.google.common.collect.*;

public class Setting
{
    private final Module module;
    private final String description;
    private final String key;
    private final List<Object> value;
    private Type type;
    private Consumer<Setting> consumer;
    
    public Setting(final Module module, final String description) {
        this(module, description, description.replace(" ", "").toUpperCase());
        this.module.settings.add(this);
    }
    
    public Setting onValueChanged(final Consumer<Setting> consumer) {
        this.consumer = consumer;
        return this;
    }
    
    public Setting setDefault(final int color, final int chromaSpeed) {
        this.type = Type.COLOR;
        this.replaceIndex(0, new ColorObject(color, chromaSpeed != 0, chromaSpeed));
        return this;
    }
    
    public Setting setDefault(final KeyBinding keyBinding) {
        this.type = Type.KEYBIND;
        this.replaceIndex(0, keyBinding);
        this.module.settings.add(this);
        return this;
    }
    
    public Setting setDefault(final String s) {
        this.replaceIndex(0, s);
        this.module.settings.add(this);
        return this;
    }
    
    public Setting setDefault(final Number n) {
        this.replaceIndex(0, n);
        this.module.settings.add(this);
        return this;
    }
    
    public Setting setDefault(final boolean b) {
        this.type = Type.CHECKBOX;
        this.replaceIndex(0, b);
        this.module.settings.add(this);
        return this;
    }
    
    public Setting setValue(final Object value) {
        this.replaceIndex(0, value);
        if (this.consumer != null) {
            this.consumer.accept(this);
        }
        return this;
    }
    
    public Object getObject() {
        return this.value.get(0);
    }
    
    public int getInt() {
        return this.value.get(0);
    }
    
    public float getFloat() {
        return this.value.get(0);
    }
    
    public boolean getBoolean() {
        return this.value.get(0);
    }
    
    public boolean hasValue() {
        return this.value.size() > 0;
    }
    
    public Setting setRange(final int min, final int max, final int step) {
        this.type = Type.INT_SLIDER;
        this.value.add(min);
        this.value.add(max);
        this.value.add(step);
        return this;
    }
    
    public Setting setRange(final float min, final float max, final float step) {
        this.type = Type.FLOAT_SLIDER;
        this.value.add(min);
        this.value.add(max);
        this.value.add(step);
        return this;
    }
    
    public Setting setRange(final String... display) {
        if (display.length == 0) {
            throw new InvalidParameterException("Supplied parameter has an insufficient length!");
        }
        this.type = Type.MODE;
        Collections.addAll(this.value, display);
        return this;
    }
    
    public float getRange(final int i) {
        return Float.parseFloat(String.valueOf(this.value.get(i + 1)));
    }
    
    public ColorObject getColorObject() {
        return this.value.get(0);
    }
    
    public int getColor() {
        final ColorObject color = this.getColorObject();
        if (color.isChroma() && color.getChromaSpeed() != 0) {
            return color.getChromaColor();
        }
        return color.getColor();
    }
    
    private void replaceIndex(final int index, final Object value) {
        if (this.value.size() > 0) {
            this.value.remove(index);
        }
        this.value.add(index, value);
    }
    
    public Setting(final Module module, final String description, final String key) {
        this.value = (List<Object>)Lists.newArrayList();
        this.module = module;
        this.description = description;
        this.key = key;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public List<Object> getValue() {
        return this.value;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        COLOR, 
        INT_SLIDER, 
        FLOAT_SLIDER, 
        CHECKBOX, 
        KEYBIND, 
        MODE, 
        DEFAULT;
    }
}
