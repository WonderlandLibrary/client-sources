/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.Mouse;
import net.java.games.input.Rumbler;

final class AWTMouse
extends Mouse
implements AWTEventListener {
    private static final int EVENT_X = 1;
    private static final int EVENT_Y = 2;
    private static final int EVENT_BUTTON = 4;
    private final List awt_events = new ArrayList();
    private final List processed_awt_events = new ArrayList();
    private int event_state = 1;

    protected AWTMouse() {
        super("AWTMouse", AWTMouse.createComponents(), new Controller[0], new Rumbler[0]);
        Toolkit.getDefaultToolkit().addAWTEventListener(this, 131120L);
    }

    private static final Component[] createComponents() {
        return new Component[]{new Axis(Component.Identifier.Axis.X), new Axis(Component.Identifier.Axis.Y), new Axis(Component.Identifier.Axis.Z), new Button(Component.Identifier.Button.LEFT), new Button(Component.Identifier.Button.MIDDLE), new Button(Component.Identifier.Button.RIGHT)};
    }

    private final void processButtons(int n, float f) {
        Button button = this.getButton(n);
        if (button != null) {
            button.setValue(f);
        }
    }

    private final Button getButton(int n) {
        switch (n) {
            case 1: {
                return (Button)this.getLeft();
            }
            case 2: {
                return (Button)this.getMiddle();
            }
            case 3: {
                return (Button)this.getRight();
            }
        }
        return null;
    }

    private final void processEvent(AWTEvent aWTEvent) throws IOException {
        if (aWTEvent instanceof MouseWheelEvent) {
            MouseWheelEvent mouseWheelEvent = (MouseWheelEvent)aWTEvent;
            Axis axis = (Axis)this.getWheel();
            axis.setValue(axis.poll() + (float)mouseWheelEvent.getWheelRotation());
        } else if (aWTEvent instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent)aWTEvent;
            Axis axis = (Axis)this.getX();
            Axis axis2 = (Axis)this.getY();
            axis.setValue(mouseEvent.getX());
            axis2.setValue(mouseEvent.getY());
            switch (mouseEvent.getID()) {
                case 501: {
                    this.processButtons(mouseEvent.getButton(), 1.0f);
                    break;
                }
                case 502: {
                    this.processButtons(mouseEvent.getButton(), 0.0f);
                    break;
                }
            }
        }
    }

    public final synchronized void pollDevice() throws IOException {
        Axis axis = (Axis)this.getWheel();
        axis.setValue(0.0f);
        for (int i = 0; i < this.awt_events.size(); ++i) {
            AWTEvent aWTEvent = (AWTEvent)this.awt_events.get(i);
            this.processEvent(aWTEvent);
            this.processed_awt_events.add(aWTEvent);
        }
        this.awt_events.clear();
    }

    protected final synchronized boolean getNextDeviceEvent(Event event) throws IOException {
        block9: while (true) {
            long l;
            MouseEvent mouseEvent;
            if (this.processed_awt_events.isEmpty()) {
                return true;
            }
            AWTEvent aWTEvent = (AWTEvent)this.processed_awt_events.get(0);
            if (aWTEvent instanceof MouseWheelEvent) {
                mouseEvent = (MouseWheelEvent)aWTEvent;
                l = mouseEvent.getWhen() * 1000000L;
                event.set(this.getWheel(), ((MouseWheelEvent)mouseEvent).getWheelRotation(), l);
                this.processed_awt_events.remove(0);
                continue;
            }
            if (!(aWTEvent instanceof MouseEvent)) continue;
            mouseEvent = (MouseEvent)aWTEvent;
            l = mouseEvent.getWhen() * 1000000L;
            switch (this.event_state) {
                case 1: {
                    this.event_state = 2;
                    event.set(this.getX(), mouseEvent.getX(), l);
                    return false;
                }
                case 2: {
                    this.event_state = 4;
                    event.set(this.getY(), mouseEvent.getY(), l);
                    return false;
                }
                case 4: {
                    this.processed_awt_events.remove(0);
                    this.event_state = 1;
                    Button button = this.getButton(mouseEvent.getButton());
                    if (button == null) continue block9;
                    switch (mouseEvent.getID()) {
                        case 501: {
                            event.set(button, 1.0f, l);
                            return false;
                        }
                        case 502: {
                            event.set(button, 0.0f, l);
                            return false;
                        }
                    }
                    continue block9;
                }
            }
            break;
        }
        throw new RuntimeException("Unknown event state: " + this.event_state);
    }

    public final synchronized void eventDispatched(AWTEvent aWTEvent) {
        this.awt_events.add(aWTEvent);
    }

    static final class Button
    extends AbstractComponent {
        private float value;

        public Button(Component.Identifier.Button button) {
            super(button.getName(), button);
        }

        protected final void setValue(float f) {
            this.value = f;
        }

        protected final float poll() throws IOException {
            return this.value;
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
        private float value;

        public Axis(Component.Identifier.Axis axis) {
            super(axis.getName(), axis);
        }

        public final boolean isRelative() {
            return true;
        }

        public final boolean isAnalog() {
            return false;
        }

        protected final void setValue(float f) {
            this.value = f;
        }

        protected final float poll() throws IOException {
            return this.value;
        }
    }
}

