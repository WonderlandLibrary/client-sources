/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;

final class LinuxJoystickButton
extends AbstractComponent {
    private float value;

    public LinuxJoystickButton(Component.Identifier identifier) {
        super(identifier.getName(), identifier);
    }

    public final boolean isRelative() {
        return true;
    }

    final void setValue(float f) {
        this.value = f;
    }

    protected final float poll() throws IOException {
        return this.value;
    }
}

