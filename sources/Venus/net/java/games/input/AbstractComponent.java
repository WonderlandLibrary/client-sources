/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Component;
import net.java.games.input.ControllerEnvironment;

public abstract class AbstractComponent
implements Component {
    private final String name;
    private final Component.Identifier id;
    private boolean has_polled;
    private float value;
    private float event_value;

    protected AbstractComponent(String string, Component.Identifier identifier) {
        this.name = string;
        this.id = identifier;
    }

    public Component.Identifier getIdentifier() {
        return this.id;
    }

    public boolean isAnalog() {
        return true;
    }

    public float getDeadZone() {
        return 0.0f;
    }

    public final float getPollData() {
        if (!this.has_polled && !this.isRelative()) {
            this.has_polled = true;
            try {
                this.setPollData(this.poll());
            } catch (IOException iOException) {
                ControllerEnvironment.log("Failed to poll component: " + iOException);
            }
        }
        return this.value;
    }

    final void resetHasPolled() {
        this.has_polled = false;
    }

    final void setPollData(float f) {
        this.value = f;
    }

    final float getEventValue() {
        return this.event_value;
    }

    final void setEventValue(float f) {
        this.event_value = f;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    protected abstract float poll() throws IOException;
}

