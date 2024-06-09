/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.valuesystem;

import us.amerikan.valuesystem.Value;

public class ModeValue
extends Value<String> {
    private String[] possibleValues;

    public ModeValue(String valueName, String valueObject, String ... possibleValues) {
        super(valueName, valueObject);
        this.possibleValues = possibleValues;
    }

    public boolean setMode(String name) {
        for (String possibleValue : this.possibleValues) {
            if (!possibleValue.equalsIgnoreCase(name)) continue;
            this.setObject(possibleValue);
            return true;
        }
        return false;
    }

    public String getMode(String name) {
        for (String possibleValue : this.possibleValues) {
            if (!possibleValue.equalsIgnoreCase(name)) continue;
            return possibleValue;
        }
        return null;
    }

    public String[] getPossibleValues() {
        return this.possibleValues;
    }
}

