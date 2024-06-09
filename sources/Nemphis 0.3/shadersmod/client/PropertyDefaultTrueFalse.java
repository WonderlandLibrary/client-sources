/*
 * Decompiled with CFR 0_118.
 */
package shadersmod.client;

import shadersmod.client.Property;

public class PropertyDefaultTrueFalse
extends Property {
    public static final String[] PROPERTY_VALUES = new String[]{"default", "true", "false"};
    public static final String[] USER_VALUES = new String[]{"Default", "ON", "OFF"};

    public PropertyDefaultTrueFalse(String propertyName, String userName, int defaultValue) {
        super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
    }

    public boolean isDefault() {
        if (this.getValue() == 0) {
            return true;
        }
        return false;
    }

    public boolean isTrue() {
        if (this.getValue() == 1) {
            return true;
        }
        return false;
    }

    public boolean isFalse() {
        if (this.getValue() == 2) {
            return true;
        }
        return false;
    }
}

