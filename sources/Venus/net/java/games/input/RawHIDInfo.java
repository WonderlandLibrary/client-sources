/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Controller;
import net.java.games.input.RawDevice;
import net.java.games.input.RawDeviceInfo;
import net.java.games.input.SetupAPIDevice;

class RawHIDInfo
extends RawDeviceInfo {
    private final RawDevice device;
    private final int vendor_id;
    private final int product_id;
    private final int version;
    private final int page;
    private final int usage;

    public RawHIDInfo(RawDevice rawDevice, int n, int n2, int n3, int n4, int n5) {
        this.device = rawDevice;
        this.vendor_id = n;
        this.product_id = n2;
        this.version = n3;
        this.page = n4;
        this.usage = n5;
    }

    public final int getUsage() {
        return this.usage;
    }

    public final int getUsagePage() {
        return this.page;
    }

    public final long getHandle() {
        return this.device.getHandle();
    }

    public final Controller createControllerFromDevice(RawDevice rawDevice, SetupAPIDevice setupAPIDevice) throws IOException {
        return null;
    }
}

