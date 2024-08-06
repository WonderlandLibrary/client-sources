package com.shroomclient.shroomclientnextgen.config.types;

import java.lang.reflect.Field;
import lombok.Getter;

public class ConfigOptionEnum<T> extends ConfigOption<T> {

    public ConfigOptionEnum(
        Field field,
        Object instance,
        com.shroomclient.shroomclientnextgen.config.ConfigOption ann
    ) {
        super(field, instance, ann);
    }

    @Override
    public void set(T value) {
        internalSet(value);
    }

    public EnumOption<T> getOption() {
        return new EnumOption<>(get(), field);
    }

    public static class EnumOption<T> {

        private final @Getter String name;
        private final @Getter T value;
        private final Field field;

        public EnumOption(T value, Field field) {
            name = value.toString();
            this.value = value;
            this.field = field;
        }

        public int getIndex() {
            T[] c = (T[]) field.getType().getEnumConstants();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == value) {
                    return i;
                }
            }
            throw new RuntimeException("Couldn't find index");
        }

        public EnumOption<T> next() {
            int idx = getIndex();
            T[] c = (T[]) field.getType().getEnumConstants();
            if (idx == c.length - 1) {
                // loop around!!!
                return new EnumOption<>(c[0], field);
            } else {
                return new EnumOption<>(c[idx + 1], field);
            }
        }

        public EnumOption<T> setInt(int i) {
            T[] c = (T[]) field.getType().getEnumConstants();
            if (i > c.length - 1) {
                return new EnumOption<>(c[0], field);
            }
            return new EnumOption<>(c[i], field);
        }

        public EnumOption<T> previous() {
            int idx = getIndex();
            T[] c = (T[]) field.getType().getEnumConstants();
            if (idx - 1 < 0) {
                // we back
                return new EnumOption<>(c[c.length - 1], field);
            } else {
                return new EnumOption<>(c[idx - 1], field);
            }
        }
    }
}
