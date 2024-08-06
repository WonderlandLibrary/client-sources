package com.shroomclient.shroomclientnextgen.config.types;

import java.lang.reflect.Field;
import lombok.Getter;

public abstract class ConfigOption<T> {

    @Getter
    protected final Field field;

    protected final com.shroomclient.shroomclientnextgen.config.ConfigOption ann;
    private final Object instance;

    public ConfigOption(
        Field field,
        Object instance,
        com.shroomclient.shroomclientnextgen.config.ConfigOption ann
    ) {
        this.field = field;
        this.instance = instance;
        this.ann = ann;
    }

    public T get() {
        try {
            return (T) field.get(instance);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to get field");
        }
    }

    protected void internalSet(T value) {
        try {
            field.set(instance, value);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Couldn't set field");
        }
    }

    public abstract void set(T value);

    public String getName() {
        return ann.name();
    }

    public String getDescription() {
        return ann.description();
    }

    public double getOrder() {
        return ann.order();
    }
}
