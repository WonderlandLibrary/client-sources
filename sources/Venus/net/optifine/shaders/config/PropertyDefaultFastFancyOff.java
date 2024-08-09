/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import net.optifine.Config;
import net.optifine.shaders.config.Property;

public class PropertyDefaultFastFancyOff
extends Property {
    public static final String[] PROPERTY_VALUES = new String[]{"default", "fast", "fancy", "off"};
    public static final String[] USER_VALUES = new String[]{"Default", "Fast", "Fancy", "OFF"};

    public PropertyDefaultFastFancyOff(String string, String string2, int n) {
        super(string, PROPERTY_VALUES, string2, USER_VALUES, n);
    }

    public boolean isDefault() {
        return this.getValue() == 0;
    }

    public boolean isFast() {
        return this.getValue() == 1;
    }

    public boolean isFancy() {
        return this.getValue() == 2;
    }

    public boolean isOff() {
        return this.getValue() == 3;
    }

    @Override
    public boolean setPropertyValue(String string) {
        if (Config.equals(string, "none")) {
            string = "off";
        }
        return super.setPropertyValue(string);
    }
}

