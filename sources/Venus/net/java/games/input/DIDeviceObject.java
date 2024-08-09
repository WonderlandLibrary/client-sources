/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Component;
import net.java.games.input.IDirectInputDevice;

final class DIDeviceObject {
    private static final int WHEEL_SCALE = 120;
    private final IDirectInputDevice device;
    private final byte[] guid;
    private final int identifier;
    private final int type;
    private final int instance;
    private final int guid_type;
    private final int flags;
    private final String name;
    private final Component.Identifier id;
    private final int format_offset;
    private final long min;
    private final long max;
    private final int deadzone;
    private int last_poll_value;
    private int last_event_value;

    public DIDeviceObject(IDirectInputDevice iDirectInputDevice, Component.Identifier identifier, byte[] byArray, int n, int n2, int n3, int n4, int n5, String string, int n6) throws IOException {
        this.device = iDirectInputDevice;
        this.id = identifier;
        this.guid = byArray;
        this.identifier = n2;
        this.type = n3;
        this.instance = n4;
        this.guid_type = n;
        this.flags = n5;
        this.name = string;
        this.format_offset = n6;
        if (this.isAxis() && !this.isRelative()) {
            long[] lArray = iDirectInputDevice.getRangeProperty(n2);
            this.min = lArray[0];
            this.max = lArray[1];
            this.deadzone = iDirectInputDevice.getDeadzoneProperty(n2);
        } else {
            this.min = Integer.MIN_VALUE;
            this.max = Integer.MAX_VALUE;
            this.deadzone = 0;
        }
    }

    public final synchronized int getRelativePollValue(int n) {
        if (this.device.areAxesRelative()) {
            return n;
        }
        int n2 = n - this.last_poll_value;
        this.last_poll_value = n;
        return n2;
    }

    public final synchronized int getRelativeEventValue(int n) {
        if (this.device.areAxesRelative()) {
            return n;
        }
        int n2 = n - this.last_event_value;
        this.last_event_value = n;
        return n2;
    }

    public final int getGUIDType() {
        return this.guid_type;
    }

    public final int getFormatOffset() {
        return this.format_offset;
    }

    public final IDirectInputDevice getDevice() {
        return this.device;
    }

    public final int getDIIdentifier() {
        return this.identifier;
    }

    public final Component.Identifier getIdentifier() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final int getInstance() {
        return this.instance;
    }

    public final int getType() {
        return this.type;
    }

    public final byte[] getGUID() {
        return this.guid;
    }

    public final int getFlags() {
        return this.flags;
    }

    public final long getMin() {
        return this.min;
    }

    public final long getMax() {
        return this.max;
    }

    public final float getDeadzone() {
        return this.deadzone;
    }

    public final boolean isButton() {
        return (this.type & 0xC) != 0;
    }

    public final boolean isAxis() {
        return (this.type & 3) != 0;
    }

    public final boolean isRelative() {
        return this.isAxis() && (this.type & 1) != 0;
    }

    public final boolean isAnalog() {
        return this.isAxis() && this.id != Component.Identifier.Axis.POV;
    }

    public final float convertValue(float f) {
        if (this.getDevice().getType() == 18 && this.id == Component.Identifier.Axis.Z) {
            return f / 120.0f;
        }
        if (this.isButton()) {
            return ((int)f & 0x80) != 0 ? 1.0f : 0.0f;
        }
        if (this.id == Component.Identifier.Axis.POV) {
            int n = (int)f;
            if ((n & 0xFFFF) == 65535) {
                return 0.0f;
            }
            int n2 = 2250;
            if (n >= 0 && n < n2) {
                return 0.25f;
            }
            if (n < 3 * n2) {
                return 0.375f;
            }
            if (n < 5 * n2) {
                return 0.5f;
            }
            if (n < 7 * n2) {
                return 0.625f;
            }
            if (n < 9 * n2) {
                return 0.75f;
            }
            if (n < 11 * n2) {
                return 0.875f;
            }
            if (n < 13 * n2) {
                return 1.0f;
            }
            if (n < 15 * n2) {
                return 0.125f;
            }
            return 0.25f;
        }
        if (this.isAxis() && !this.isRelative()) {
            return 2.0f * (f - (float)this.min) / (float)(this.max - this.min) - 1.0f;
        }
        return f;
    }
}

