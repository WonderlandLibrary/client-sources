/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Controller;
import net.java.games.input.RawDevice;
import net.java.games.input.RawDeviceInfo;
import net.java.games.input.RawKeyboard;
import net.java.games.input.Rumbler;
import net.java.games.input.SetupAPIDevice;

class RawKeyboardInfo
extends RawDeviceInfo {
    private final RawDevice device;
    private final int type;
    private final int sub_type;
    private final int keyboard_mode;
    private final int num_function_keys;
    private final int num_indicators;
    private final int num_keys_total;

    public RawKeyboardInfo(RawDevice rawDevice, int n, int n2, int n3, int n4, int n5, int n6) {
        this.device = rawDevice;
        this.type = n;
        this.sub_type = n2;
        this.keyboard_mode = n3;
        this.num_function_keys = n4;
        this.num_indicators = n5;
        this.num_keys_total = n6;
    }

    public final int getUsage() {
        return 1;
    }

    public final int getUsagePage() {
        return 0;
    }

    public final long getHandle() {
        return this.device.getHandle();
    }

    public final Controller createControllerFromDevice(RawDevice rawDevice, SetupAPIDevice setupAPIDevice) throws IOException {
        return new RawKeyboard(setupAPIDevice.getName(), rawDevice, new Controller[0], new Rumbler[0]);
    }
}

