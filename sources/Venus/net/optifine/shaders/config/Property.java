/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.Properties;
import net.optifine.Config;
import org.apache.commons.lang3.ArrayUtils;

public class Property {
    private int defaultValue = 0;
    private String propertyName = null;
    private String[] propertyValues = null;
    private String userName = null;
    private String[] userValues = null;
    private int value = 0;

    public Property(String string, String[] stringArray, String string2, String[] stringArray2, int n) {
        this.propertyName = string;
        this.propertyValues = stringArray;
        this.userName = string2;
        this.userValues = stringArray2;
        this.defaultValue = n;
        if (stringArray.length != stringArray2.length) {
            throw new IllegalArgumentException("Property and user values have different lengths: " + stringArray.length + " != " + stringArray2.length);
        }
        if (n < 0 || n >= stringArray.length) {
            throw new IllegalArgumentException("Invalid default value: " + n);
        }
        this.value = n;
    }

    public boolean setPropertyValue(String string) {
        if (string == null) {
            this.value = this.defaultValue;
            return true;
        }
        this.value = ArrayUtils.indexOf(this.propertyValues, string);
        if (this.value >= 0 && this.value < this.propertyValues.length) {
            return false;
        }
        this.value = this.defaultValue;
        return true;
    }

    public void nextValue(boolean bl) {
        int n = 0;
        int n2 = this.propertyValues.length - 1;
        this.value = Config.limit(this.value, n, n2);
        if (bl) {
            ++this.value;
            if (this.value > n2) {
                this.value = n;
            }
        } else {
            --this.value;
            if (this.value < n) {
                this.value = n2;
            }
        }
    }

    public void setValue(int n) {
        this.value = n;
        if (this.value < 0 || this.value >= this.propertyValues.length) {
            this.value = this.defaultValue;
        }
    }

    public int getValue() {
        return this.value;
    }

    public String getUserValue() {
        return this.userValues[this.value];
    }

    public String getPropertyValue() {
        return this.propertyValues[this.value];
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void resetValue() {
        this.value = this.defaultValue;
    }

    public boolean loadFrom(Properties properties) {
        this.resetValue();
        if (properties == null) {
            return true;
        }
        String string = properties.getProperty(this.propertyName);
        return string == null ? false : this.setPropertyValue(string);
    }

    public void saveTo(Properties properties) {
        if (properties != null) {
            properties.setProperty(this.getPropertyName(), this.getPropertyValue());
        }
    }

    public String toString() {
        return this.propertyName + "=" + this.getPropertyValue() + " [" + Config.arrayToString(this.propertyValues) + "], value: " + this.value;
    }
}

