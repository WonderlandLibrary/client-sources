/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class RawMouseEvent {
    private static final int WHEEL_SCALE = 120;
    private long millis;
    private int flags;
    private int button_flags;
    private int button_data;
    private long raw_buttons;
    private long last_x;
    private long last_y;
    private long extra_information;

    RawMouseEvent() {
    }

    public final void set(long l, int n, int n2, int n3, long l2, long l3, long l4, long l5) {
        this.millis = l;
        this.flags = n;
        this.button_flags = n2;
        this.button_data = n3;
        this.raw_buttons = l2;
        this.last_x = l3;
        this.last_y = l4;
        this.extra_information = l5;
    }

    public final void set(RawMouseEvent rawMouseEvent) {
        this.set(rawMouseEvent.millis, rawMouseEvent.flags, rawMouseEvent.button_flags, rawMouseEvent.button_data, rawMouseEvent.raw_buttons, rawMouseEvent.last_x, rawMouseEvent.last_y, rawMouseEvent.extra_information);
    }

    public final int getWheelDelta() {
        return this.button_data / 120;
    }

    private final int getButtonData() {
        return this.button_data;
    }

    public final int getFlags() {
        return this.flags;
    }

    public final int getButtonFlags() {
        return this.button_flags;
    }

    public final int getLastX() {
        return (int)this.last_x;
    }

    public final int getLastY() {
        return (int)this.last_y;
    }

    public final long getRawButtons() {
        return this.raw_buttons;
    }

    public final long getNanos() {
        return this.millis * 1000000L;
    }
}

