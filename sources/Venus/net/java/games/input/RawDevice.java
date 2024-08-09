/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.DataQueue;
import net.java.games.input.RawDeviceInfo;
import net.java.games.input.RawInputEventQueue;
import net.java.games.input.RawKeyboardEvent;
import net.java.games.input.RawMouseEvent;

final class RawDevice {
    public static final int RI_MOUSE_LEFT_BUTTON_DOWN = 1;
    public static final int RI_MOUSE_LEFT_BUTTON_UP = 2;
    public static final int RI_MOUSE_RIGHT_BUTTON_DOWN = 4;
    public static final int RI_MOUSE_RIGHT_BUTTON_UP = 8;
    public static final int RI_MOUSE_MIDDLE_BUTTON_DOWN = 16;
    public static final int RI_MOUSE_MIDDLE_BUTTON_UP = 32;
    public static final int RI_MOUSE_BUTTON_1_DOWN = 1;
    public static final int RI_MOUSE_BUTTON_1_UP = 2;
    public static final int RI_MOUSE_BUTTON_2_DOWN = 4;
    public static final int RI_MOUSE_BUTTON_2_UP = 8;
    public static final int RI_MOUSE_BUTTON_3_DOWN = 16;
    public static final int RI_MOUSE_BUTTON_3_UP = 32;
    public static final int RI_MOUSE_BUTTON_4_DOWN = 64;
    public static final int RI_MOUSE_BUTTON_4_UP = 128;
    public static final int RI_MOUSE_BUTTON_5_DOWN = 256;
    public static final int RI_MOUSE_BUTTON_5_UP = 512;
    public static final int RI_MOUSE_WHEEL = 1024;
    public static final int MOUSE_MOVE_RELATIVE = 0;
    public static final int MOUSE_MOVE_ABSOLUTE = 1;
    public static final int MOUSE_VIRTUAL_DESKTOP = 2;
    public static final int MOUSE_ATTRIBUTES_CHANGED = 4;
    public static final int RIM_TYPEHID = 2;
    public static final int RIM_TYPEKEYBOARD = 1;
    public static final int RIM_TYPEMOUSE = 0;
    public static final int WM_KEYDOWN = 256;
    public static final int WM_KEYUP = 257;
    public static final int WM_SYSKEYDOWN = 260;
    public static final int WM_SYSKEYUP = 261;
    private final RawInputEventQueue queue;
    private final long handle;
    private final int type;
    private DataQueue keyboard_events;
    private DataQueue mouse_events;
    private DataQueue processed_keyboard_events;
    private DataQueue processed_mouse_events;
    private final boolean[] button_states = new boolean[5];
    private int wheel;
    private int relative_x;
    private int relative_y;
    private int last_x;
    private int last_y;
    private int event_relative_x;
    private int event_relative_y;
    private int event_last_x;
    private int event_last_y;
    private final boolean[] key_states = new boolean[255];
    static Class class$net$java$games$input$RawKeyboardEvent;
    static Class class$net$java$games$input$RawMouseEvent;

    public RawDevice(RawInputEventQueue rawInputEventQueue, long l, int n) {
        this.queue = rawInputEventQueue;
        this.handle = l;
        this.type = n;
        this.setBufferSize(32);
    }

    public final synchronized void addMouseEvent(long l, int n, int n2, int n3, long l2, long l3, long l4, long l5) {
        if (this.mouse_events.hasRemaining()) {
            RawMouseEvent rawMouseEvent = (RawMouseEvent)this.mouse_events.get();
            rawMouseEvent.set(l, n, n2, n3, l2, l3, l4, l5);
        }
    }

    public final synchronized void addKeyboardEvent(long l, int n, int n2, int n3, int n4, long l2) {
        if (this.keyboard_events.hasRemaining()) {
            RawKeyboardEvent rawKeyboardEvent = (RawKeyboardEvent)this.keyboard_events.get();
            rawKeyboardEvent.set(l, n, n2, n3, n4, l2);
        }
    }

    public final synchronized void pollMouse() {
        this.wheel = 0;
        this.relative_y = 0;
        this.relative_x = 0;
        this.mouse_events.flip();
        while (this.mouse_events.hasRemaining()) {
            RawMouseEvent rawMouseEvent = (RawMouseEvent)this.mouse_events.get();
            boolean bl = this.processMouseEvent(rawMouseEvent);
            if (!bl || !this.processed_mouse_events.hasRemaining()) continue;
            RawMouseEvent rawMouseEvent2 = (RawMouseEvent)this.processed_mouse_events.get();
            rawMouseEvent2.set(rawMouseEvent);
        }
        this.mouse_events.compact();
    }

    public final synchronized void pollKeyboard() {
        this.keyboard_events.flip();
        while (this.keyboard_events.hasRemaining()) {
            RawKeyboardEvent rawKeyboardEvent = (RawKeyboardEvent)this.keyboard_events.get();
            boolean bl = this.processKeyboardEvent(rawKeyboardEvent);
            if (!bl || !this.processed_keyboard_events.hasRemaining()) continue;
            RawKeyboardEvent rawKeyboardEvent2 = (RawKeyboardEvent)this.processed_keyboard_events.get();
            rawKeyboardEvent2.set(rawKeyboardEvent);
        }
        this.keyboard_events.compact();
    }

    private final boolean updateButtonState(int n, int n2, int n3, int n4) {
        if (n >= this.button_states.length) {
            return true;
        }
        if ((n2 & n3) != 0) {
            this.button_states[n] = true;
            return false;
        }
        if ((n2 & n4) != 0) {
            this.button_states[n] = false;
            return false;
        }
        return true;
    }

    private final boolean processKeyboardEvent(RawKeyboardEvent rawKeyboardEvent) {
        int n = rawKeyboardEvent.getMessage();
        int n2 = rawKeyboardEvent.getVKey();
        if (n2 >= this.key_states.length) {
            return true;
        }
        if (n == 256 || n == 260) {
            this.key_states[n2] = true;
            return false;
        }
        if (n == 257 || n == 261) {
            this.key_states[n2] = false;
            return false;
        }
        return true;
    }

    public final boolean isKeyDown(int n) {
        return this.key_states[n];
    }

    private final boolean processMouseEvent(RawMouseEvent rawMouseEvent) {
        int n;
        int n2;
        boolean bl = false;
        int n3 = rawMouseEvent.getButtonFlags();
        bl = this.updateButtonState(0, n3, 1, 2) || bl;
        bl = this.updateButtonState(1, n3, 4, 8) || bl;
        bl = this.updateButtonState(2, n3, 16, 32) || bl;
        bl = this.updateButtonState(3, n3, 64, 128) || bl;
        boolean bl2 = bl = this.updateButtonState(4, n3, 256, 512) || bl;
        if ((rawMouseEvent.getFlags() & 1) != 0) {
            n2 = rawMouseEvent.getLastX() - this.last_x;
            n = rawMouseEvent.getLastY() - this.last_y;
            this.last_x = rawMouseEvent.getLastX();
            this.last_y = rawMouseEvent.getLastY();
        } else {
            n2 = rawMouseEvent.getLastX();
            n = rawMouseEvent.getLastY();
        }
        int n4 = 0;
        if ((n3 & 0x400) != 0) {
            n4 = rawMouseEvent.getWheelDelta();
        }
        this.relative_x += n2;
        this.relative_y += n;
        this.wheel += n4;
        bl = n2 != 0 || n != 0 || n4 != 0 || bl;
        return bl;
    }

    public final int getWheel() {
        return this.wheel;
    }

    public final int getEventRelativeX() {
        return this.event_relative_x;
    }

    public final int getEventRelativeY() {
        return this.event_relative_y;
    }

    public final int getRelativeX() {
        return this.relative_x;
    }

    public final int getRelativeY() {
        return this.relative_y;
    }

    public final synchronized boolean getNextKeyboardEvent(RawKeyboardEvent rawKeyboardEvent) {
        this.processed_keyboard_events.flip();
        if (!this.processed_keyboard_events.hasRemaining()) {
            this.processed_keyboard_events.compact();
            return true;
        }
        RawKeyboardEvent rawKeyboardEvent2 = (RawKeyboardEvent)this.processed_keyboard_events.get();
        rawKeyboardEvent.set(rawKeyboardEvent2);
        this.processed_keyboard_events.compact();
        return false;
    }

    public final synchronized boolean getNextMouseEvent(RawMouseEvent rawMouseEvent) {
        this.processed_mouse_events.flip();
        if (!this.processed_mouse_events.hasRemaining()) {
            this.processed_mouse_events.compact();
            return true;
        }
        RawMouseEvent rawMouseEvent2 = (RawMouseEvent)this.processed_mouse_events.get();
        if ((rawMouseEvent2.getFlags() & 1) != 0) {
            this.event_relative_x = rawMouseEvent2.getLastX() - this.event_last_x;
            this.event_relative_y = rawMouseEvent2.getLastY() - this.event_last_y;
            this.event_last_x = rawMouseEvent2.getLastX();
            this.event_last_y = rawMouseEvent2.getLastY();
        } else {
            this.event_relative_x = rawMouseEvent2.getLastX();
            this.event_relative_y = rawMouseEvent2.getLastY();
        }
        rawMouseEvent.set(rawMouseEvent2);
        this.processed_mouse_events.compact();
        return false;
    }

    public final boolean getButtonState(int n) {
        if (n >= this.button_states.length) {
            return true;
        }
        return this.button_states[n];
    }

    public final void setBufferSize(int n) {
        this.keyboard_events = new DataQueue(n, class$net$java$games$input$RawKeyboardEvent == null ? (class$net$java$games$input$RawKeyboardEvent = RawDevice.class$("net.java.games.input.RawKeyboardEvent")) : class$net$java$games$input$RawKeyboardEvent);
        this.mouse_events = new DataQueue(n, class$net$java$games$input$RawMouseEvent == null ? (class$net$java$games$input$RawMouseEvent = RawDevice.class$("net.java.games.input.RawMouseEvent")) : class$net$java$games$input$RawMouseEvent);
        this.processed_keyboard_events = new DataQueue(n, class$net$java$games$input$RawKeyboardEvent == null ? (class$net$java$games$input$RawKeyboardEvent = RawDevice.class$("net.java.games.input.RawKeyboardEvent")) : class$net$java$games$input$RawKeyboardEvent);
        this.processed_mouse_events = new DataQueue(n, class$net$java$games$input$RawMouseEvent == null ? (class$net$java$games$input$RawMouseEvent = RawDevice.class$("net.java.games.input.RawMouseEvent")) : class$net$java$games$input$RawMouseEvent);
    }

    public final int getType() {
        return this.type;
    }

    public final long getHandle() {
        return this.handle;
    }

    public final String getName() throws IOException {
        return RawDevice.nGetName(this.handle);
    }

    private static final native String nGetName(long var0) throws IOException;

    public final RawDeviceInfo getInfo() throws IOException {
        return RawDevice.nGetInfo(this, this.handle);
    }

    private static final native RawDeviceInfo nGetInfo(RawDevice var0, long var1) throws IOException;

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }
}

