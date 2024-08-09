/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;

class LinuxJoystickAxis
extends AbstractComponent {
    private float value;
    private boolean analog;

    public LinuxJoystickAxis(Component.Identifier.Axis axis) {
        this(axis, true);
    }

    public LinuxJoystickAxis(Component.Identifier.Axis axis, boolean bl) {
        super(axis.getName(), axis);
        this.analog = bl;
    }

    public final boolean isRelative() {
        return true;
    }

    public final boolean isAnalog() {
        return this.analog;
    }

    final void setValue(float f) {
        this.value = f;
        this.resetHasPolled();
    }

    protected final float poll() throws IOException {
        return this.value;
    }
}

