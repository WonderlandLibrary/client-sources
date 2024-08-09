/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.Mouse;
import net.java.games.input.RawDevice;
import net.java.games.input.RawMouseEvent;
import net.java.games.input.Rumbler;

final class RawMouse
extends Mouse {
    private static final int EVENT_DONE = 1;
    private static final int EVENT_X = 2;
    private static final int EVENT_Y = 3;
    private static final int EVENT_Z = 4;
    private static final int EVENT_BUTTON_0 = 5;
    private static final int EVENT_BUTTON_1 = 6;
    private static final int EVENT_BUTTON_2 = 7;
    private static final int EVENT_BUTTON_3 = 8;
    private static final int EVENT_BUTTON_4 = 9;
    private final RawDevice device;
    private final RawMouseEvent current_event = new RawMouseEvent();
    private int event_state = 1;

    protected RawMouse(String string, RawDevice rawDevice, Component[] componentArray, Controller[] controllerArray, Rumbler[] rumblerArray) throws IOException {
        super(string, componentArray, controllerArray, rumblerArray);
        this.device = rawDevice;
    }

    public final void pollDevice() throws IOException {
        this.device.pollMouse();
    }

    private static final boolean makeButtonEvent(RawMouseEvent rawMouseEvent, Event event, Component component, int n, int n2) {
        if ((rawMouseEvent.getButtonFlags() & n) != 0) {
            event.set(component, 1.0f, rawMouseEvent.getNanos());
            return false;
        }
        if ((rawMouseEvent.getButtonFlags() & n2) != 0) {
            event.set(component, 0.0f, rawMouseEvent.getNanos());
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final synchronized boolean getNextDeviceEvent(Event event) throws IOException {
        block11: while (true) {
            switch (this.event_state) {
                case 1: {
                    if (!this.device.getNextMouseEvent(this.current_event)) {
                        return true;
                    }
                    this.event_state = 2;
                    continue block11;
                }
                case 2: {
                    int n = this.device.getEventRelativeX();
                    this.event_state = 3;
                    if (n == 0) continue block11;
                    event.set(this.getX(), n, this.current_event.getNanos());
                    return false;
                }
                case 3: {
                    int n = this.device.getEventRelativeY();
                    this.event_state = 4;
                    if (n == 0) continue block11;
                    event.set(this.getY(), n, this.current_event.getNanos());
                    return false;
                }
                case 4: {
                    int n = this.current_event.getWheelDelta();
                    this.event_state = 5;
                    if (n == 0) continue block11;
                    event.set(this.getWheel(), n, this.current_event.getNanos());
                    return false;
                }
                case 5: {
                    this.event_state = 6;
                    if (!RawMouse.makeButtonEvent(this.current_event, event, this.getPrimaryButton(), 1, 2)) continue block11;
                    return false;
                }
                case 6: {
                    this.event_state = 7;
                    if (!RawMouse.makeButtonEvent(this.current_event, event, this.getSecondaryButton(), 4, 8)) continue block11;
                    return false;
                }
                case 7: {
                    this.event_state = 8;
                    if (!RawMouse.makeButtonEvent(this.current_event, event, this.getTertiaryButton(), 16, 32)) continue block11;
                    return false;
                }
                case 8: {
                    this.event_state = 9;
                    if (!RawMouse.makeButtonEvent(this.current_event, event, this.getButton3(), 64, 128)) continue block11;
                    return false;
                }
                case 9: {
                    this.event_state = 1;
                    if (RawMouse.makeButtonEvent(this.current_event, event, this.getButton4(), 256, 512)) return false;
                    continue block11;
                }
            }
            break;
        }
        throw new RuntimeException("Unknown event state: " + this.event_state);
    }

    protected final void setDeviceEventQueueSize(int n) throws IOException {
        this.device.setBufferSize(n);
    }

    static final class Button
    extends AbstractComponent {
        private final RawDevice device;
        private final int button_id;

        public Button(RawDevice rawDevice, Component.Identifier.Button button, int n) {
            super(button.getName(), button);
            this.device = rawDevice;
            this.button_id = n;
        }

        protected final float poll() throws IOException {
            return this.device.getButtonState(this.button_id) ? 1.0f : 0.0f;
        }

        public final boolean isAnalog() {
            return true;
        }

        public final boolean isRelative() {
            return true;
        }
    }

    static final class Axis
    extends AbstractComponent {
        private final RawDevice device;

        public Axis(RawDevice rawDevice, Component.Identifier.Axis axis) {
            super(axis.getName(), axis);
            this.device = rawDevice;
        }

        public final boolean isRelative() {
            return false;
        }

        public final boolean isAnalog() {
            return false;
        }

        protected final float poll() throws IOException {
            if (this.getIdentifier() == Component.Identifier.Axis.X) {
                return this.device.getRelativeX();
            }
            if (this.getIdentifier() == Component.Identifier.Axis.Y) {
                return this.device.getRelativeY();
            }
            if (this.getIdentifier() == Component.Identifier.Axis.Z) {
                return this.device.getWheel();
            }
            throw new RuntimeException("Unknown raw axis: " + this.getIdentifier());
        }
    }
}

