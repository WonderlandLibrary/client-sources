/*
 * Decompiled with CFR 0_118.
 */
package shadersmod.client;

import optifine.Config;
import shadersmod.client.Property;

public class PropertyDefaultFastFancyOff
extends Property {
    public static final String[] PROPERTY_VALUES = new String[]{"default", "fast", "fancy", "off"};
    public static final String[] USER_VALUES = new String[]{"Default", "Fast", "Fancy", "OFF"};

    public PropertyDefaultFastFancyOff(String propertyName, String userName, int defaultValue) {
        super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
    }

    public boolean isDefault() {
        if (this.getValue() == 0) {
            return true;
        }
        return false;
    }

    public boolean isFast() {
        if (this.getValue() == 1) {
            return true;
        }
        return false;
    }

    public boolean isFancy() {
        if (this.getValue() == 2) {
            return true;
        }
        return false;
    }

    public boolean isOff() {
        if (this.getValue() == 3) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setPropertyValue(String propVal) {
        if (Config.equals(propVal, "none")) {
            propVal = "off";
        }
        return super.setPropertyValue(propVal);
    }
}

