/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.option.types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;

public class NumberOption
extends Option<Number> {
    private double increment;
    private double min;
    private double max;

    public NumberOption(String id, String name, Number value, Module module) {
        super(id, name, value, module);
    }

    @Override
    public void setValue(String value) {
        for (Field field : this.getModule().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Option.Op.class) || !field.getName().equalsIgnoreCase(this.getId())) continue;
            try {
                if (field.getType().isAssignableFrom(Float.TYPE)) {
                    super.setValue(Float.valueOf(Float.parseFloat(value)));
                    field.setFloat(this.getModule(), Float.parseFloat(value));
                    break;
                }
                if (field.getType().isAssignableFrom(Double.TYPE)) {
                    super.setValue(Double.parseDouble(value));
                    field.setDouble(this.getModule(), Double.parseDouble(value));
                    break;
                }
                if (field.getType().isAssignableFrom(Long.TYPE)) {
                    super.setValue(Math.round(Double.parseDouble(value)));
                    field.setLong(this.getModule(), Math.round(Double.parseDouble(value)));
                    break;
                }
                if (field.getType().isAssignableFrom(Integer.TYPE)) {
                    super.setValue((int)Math.round(Double.parseDouble(value)));
                    field.setInt(this.getModule(), (int)Math.round(Double.parseDouble(value)));
                    break;
                }
                if (field.getType().isAssignableFrom(Short.TYPE)) {
                    super.setValue((short)Math.round(Double.parseDouble(value)));
                    field.setShort(this.getModule(), (short)Math.round(Double.parseDouble(value)));
                    break;
                }
                if (!field.getType().isAssignableFrom(Byte.TYPE)) break;
                super.setValue((byte)Math.round(Double.parseDouble(value)));
                field.setByte(this.getModule(), (byte)Math.round(Double.parseDouble(value)));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }

    @Override
    public void setValue(Number value) {
        super.setValue(value);
        for (Field field : this.getModule().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Option.Op.class) || !field.getName().equalsIgnoreCase(this.getId())) continue;
            try {
                if (field.getType().isAssignableFrom(Float.TYPE)) {
                    field.setFloat(this.getModule(), value.floatValue());
                    break;
                }
                if (field.getType().isAssignableFrom(Double.TYPE)) {
                    field.setDouble(this.getModule(), value.doubleValue());
                    break;
                }
                if (field.getType().isAssignableFrom(Long.TYPE)) {
                    field.setLong(this.getModule(), value.longValue());
                    break;
                }
                if (field.getType().isAssignableFrom(Integer.TYPE)) {
                    field.setLong(this.getModule(), value.intValue());
                    break;
                }
                if (field.getType().isAssignableFrom(Short.TYPE)) {
                    field.setLong(this.getModule(), value.shortValue());
                    break;
                }
                if (!field.getType().isAssignableFrom(Byte.TYPE)) break;
                field.setLong(this.getModule(), value.byteValue());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getIncrement() {
        return this.increment;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public void increment() {
        this.setValue(Math.min(((Number)this.getValue()).doubleValue() + this.increment, this.max));
    }

    public void deincrement() {
        this.setValue(Math.max(((Number)this.getValue()).doubleValue() - this.increment, this.min));
    }
}

