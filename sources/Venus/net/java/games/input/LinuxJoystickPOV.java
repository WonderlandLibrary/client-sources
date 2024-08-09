/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.Component;
import net.java.games.input.LinuxEnvironmentPlugin;
import net.java.games.input.LinuxJoystickAxis;

public class LinuxJoystickPOV
extends LinuxJoystickAxis {
    private LinuxJoystickAxis hatX;
    private LinuxJoystickAxis hatY;

    LinuxJoystickPOV(Component.Identifier.Axis axis, LinuxJoystickAxis linuxJoystickAxis, LinuxJoystickAxis linuxJoystickAxis2) {
        super(axis, false);
        this.hatX = linuxJoystickAxis;
        this.hatY = linuxJoystickAxis2;
    }

    protected LinuxJoystickAxis getXAxis() {
        return this.hatX;
    }

    protected LinuxJoystickAxis getYAxis() {
        return this.hatY;
    }

    protected void updateValue() {
        float f = this.hatX.getPollData();
        float f2 = this.hatY.getPollData();
        this.resetHasPolled();
        if (f == -1.0f && f2 == -1.0f) {
            this.setValue(0.125f);
        } else if (f == -1.0f && f2 == 0.0f) {
            this.setValue(1.0f);
        } else if (f == -1.0f && f2 == 1.0f) {
            this.setValue(0.875f);
        } else if (f == 0.0f && f2 == -1.0f) {
            this.setValue(0.25f);
        } else if (f == 0.0f && f2 == 0.0f) {
            this.setValue(0.0f);
        } else if (f == 0.0f && f2 == 1.0f) {
            this.setValue(0.75f);
        } else if (f == 1.0f && f2 == -1.0f) {
            this.setValue(0.375f);
        } else if (f == 1.0f && f2 == 0.0f) {
            this.setValue(0.5f);
        } else if (f == 1.0f && f2 == 1.0f) {
            this.setValue(0.625f);
        } else {
            LinuxEnvironmentPlugin.logln("Unknown values x = " + f + " | y = " + f2);
            this.setValue(0.0f);
        }
    }
}

