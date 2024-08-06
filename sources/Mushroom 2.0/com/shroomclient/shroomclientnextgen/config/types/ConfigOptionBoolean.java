package com.shroomclient.shroomclientnextgen.config.types;

import java.lang.reflect.Field;

public class ConfigOptionBoolean extends ConfigOption<Boolean> {

    public ConfigOptionBoolean(
        Field field,
        Object instance,
        com.shroomclient.shroomclientnextgen.config.ConfigOption ann
    ) {
        super(field, instance, ann);
    }

    @Override
    public void set(Boolean value) {
        internalSet(value);
    }
}
