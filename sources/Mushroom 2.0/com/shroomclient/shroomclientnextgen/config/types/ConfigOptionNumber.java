package com.shroomclient.shroomclientnextgen.config.types;

import com.shroomclient.shroomclientnextgen.util.MathUtil;
import java.lang.reflect.Field;

public class ConfigOptionNumber<T> extends ConfigOption<T> {

    public ConfigOptionNumber(
        Field field,
        Object instance,
        com.shroomclient.shroomclientnextgen.config.ConfigOption ann
    ) {
        super(field, instance, ann);
    }

    public Class<?> getType() {
        return field.getType();
    }

    @Override
    public void set(T value) {
        if (value instanceof Integer) {
            int v = (int) value;
            Integer rV = Math.min(
                (int) ann.max(),
                Math.max((int) ann.min(), v)
            );
            internalSet((T) rV);
        } else if (value instanceof Long) {
            long v = (long) value;
            Long rV = Math.min((long) ann.max(), Math.max((long) ann.min(), v));
            internalSet((T) rV);
        } else if (value instanceof Float) {
            float v = (float) value;
            float rV = Math.min(
                (float) ann.max(),
                Math.max((float) ann.min(), v)
            );
            Float rrV = MathUtil.roundTo(rV, ann.precision());
            internalSet((T) rrV);
        } else if (value instanceof Double) {
            double v = (double) value;
            double rV = Math.min(ann.max(), Math.max(ann.min(), v));
            Double rrV = MathUtil.roundTo(rV, ann.precision());
            internalSet((T) rrV);
        }
    }

    public double getMinimum() {
        return ann.min();
    }

    public double getMaximum() {
        return ann.max();
    }
}
