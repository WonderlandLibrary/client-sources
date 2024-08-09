/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.AWTKeyMap;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.Keyboard;
import net.java.games.input.Rumbler;

final class AWTKeyboard
extends Keyboard
implements AWTEventListener {
    private final List awt_events = new ArrayList();
    private Event[] processed_events;
    private int processed_events_index;
    static Class class$java$awt$event$KeyEvent;

    protected AWTKeyboard() {
        super("AWTKeyboard", AWTKeyboard.createComponents(), new Controller[0], new Rumbler[0]);
        Toolkit.getDefaultToolkit().addAWTEventListener(this, 8L);
        this.resizeEventQueue(32);
    }

    private static final Component[] createComponents() {
        ArrayList<Key> arrayList = new ArrayList<Key>();
        Field[] fieldArray = (class$java$awt$event$KeyEvent == null ? (class$java$awt$event$KeyEvent = AWTKeyboard.class$("java.awt.event.KeyEvent")) : class$java$awt$event$KeyEvent).getFields();
        for (int i = 0; i < fieldArray.length; ++i) {
            Field field = fieldArray[i];
            try {
                int n;
                Component.Identifier.Key key;
                if (!Modifier.isStatic(field.getModifiers()) || field.getType() != Integer.TYPE || !field.getName().startsWith("VK_") || (key = AWTKeyMap.mapKeyCode(n = field.getInt(null))) == Component.Identifier.Key.UNKNOWN) continue;
                arrayList.add(new Key(key));
                continue;
            } catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException(illegalAccessException);
            }
        }
        arrayList.add(new Key(Component.Identifier.Key.RCONTROL));
        arrayList.add(new Key(Component.Identifier.Key.LCONTROL));
        arrayList.add(new Key(Component.Identifier.Key.RSHIFT));
        arrayList.add(new Key(Component.Identifier.Key.LSHIFT));
        arrayList.add(new Key(Component.Identifier.Key.RALT));
        arrayList.add(new Key(Component.Identifier.Key.LALT));
        arrayList.add(new Key(Component.Identifier.Key.NUMPADENTER));
        arrayList.add(new Key(Component.Identifier.Key.RETURN));
        arrayList.add(new Key(Component.Identifier.Key.NUMPADCOMMA));
        arrayList.add(new Key(Component.Identifier.Key.COMMA));
        return arrayList.toArray(new Component[0]);
    }

    private final void resizeEventQueue(int n) {
        this.processed_events = new Event[n];
        for (int i = 0; i < this.processed_events.length; ++i) {
            this.processed_events[i] = new Event();
        }
        this.processed_events_index = 0;
    }

    protected final void setDeviceEventQueueSize(int n) throws IOException {
        this.resizeEventQueue(n);
    }

    public final synchronized void eventDispatched(AWTEvent aWTEvent) {
        if (aWTEvent instanceof KeyEvent) {
            this.awt_events.add(aWTEvent);
        }
    }

    public final synchronized void pollDevice() throws IOException {
        for (int i = 0; i < this.awt_events.size(); ++i) {
            KeyEvent keyEvent = (KeyEvent)this.awt_events.get(i);
            this.processEvent(keyEvent);
        }
        this.awt_events.clear();
    }

    private final void processEvent(KeyEvent keyEvent) {
        KeyEvent keyEvent2;
        Component.Identifier.Key key = AWTKeyMap.map(keyEvent);
        if (key == null) {
            return;
        }
        Key key2 = (Key)this.getComponent(key);
        if (key2 == null) {
            return;
        }
        long l = keyEvent.getWhen() * 1000000L;
        if (keyEvent.getID() == 401) {
            this.addEvent(key2, 1.0f, l);
        } else if (keyEvent.getID() == 402 && ((keyEvent2 = (KeyEvent)Toolkit.getDefaultToolkit().getSystemEventQueue().peekEvent(401)) == null || keyEvent2.getWhen() != keyEvent.getWhen())) {
            this.addEvent(key2, 0.0f, l);
        }
    }

    private final void addEvent(Key key, float f, long l) {
        key.setValue(f);
        if (this.processed_events_index < this.processed_events.length) {
            this.processed_events[this.processed_events_index++].set(key, f, l);
        }
    }

    protected final synchronized boolean getNextDeviceEvent(Event event) throws IOException {
        if (this.processed_events_index == 0) {
            return true;
        }
        --this.processed_events_index;
        event.set(this.processed_events[0]);
        Event event2 = this.processed_events[0];
        this.processed_events[0] = this.processed_events[this.processed_events_index];
        this.processed_events[this.processed_events_index] = event2;
        return false;
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }

    private static final class Key
    extends AbstractComponent {
        private float value;

        public Key(Component.Identifier.Key key) {
            super(key.getName(), key);
        }

        public final void setValue(float f) {
            this.value = f;
        }

        protected final float poll() {
            return this.value;
        }

        public final boolean isAnalog() {
            return true;
        }

        public final boolean isRelative() {
            return true;
        }
    }
}

