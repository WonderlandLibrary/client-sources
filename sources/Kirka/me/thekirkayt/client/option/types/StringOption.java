/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.option.types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;

public class StringOption
extends Option<String> {
    public StringOption(String id, String name, String value, Module module) {
        super(id, name, value, module);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        for (Field field : this.getModule().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Option.Op.class) || !field.getName().equalsIgnoreCase(this.getId())) continue;
            try {
                if (!field.getType().isAssignableFrom(String.class)) continue;
                field.set(this.getModule(), value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

