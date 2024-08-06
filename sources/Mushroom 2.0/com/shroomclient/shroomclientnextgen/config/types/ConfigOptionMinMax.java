package com.shroomclient.shroomclientnextgen.config.types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ConfigOptionMinMax extends ConfigOption {

    public final ConfigOptionNumber<?> min;
    public final ConfigOptionNumber<?> max;

    public ConfigOptionMinMax(
        ConfigOptionNumber<?> min,
        ConfigOptionNumber<?> max,
        String name,
        String description,
        double order,
        Field field
    ) {
        super(
            field,
            null,
            new com.shroomclient.shroomclientnextgen.config.ConfigOption() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }

                @Override
                public String name() {
                    return name;
                }

                @Override
                public String description() {
                    return description;
                }

                @Override
                public double min() {
                    return 0;
                }

                @Override
                public double max() {
                    return 0;
                }

                @Override
                public int precision() {
                    return 0;
                }

                @Override
                public double order() {
                    return order;
                }
            }
        );
        this.min = min;
        this.max = max;
    }

    @Override
    public void set(Object value) {
        throw new RuntimeException("Not implemented");
    }
}
