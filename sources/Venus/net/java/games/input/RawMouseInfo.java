/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.DIIdentifierMap;
import net.java.games.input.RawDevice;
import net.java.games.input.RawDeviceInfo;
import net.java.games.input.RawMouse;
import net.java.games.input.Rumbler;
import net.java.games.input.SetupAPIDevice;

class RawMouseInfo
extends RawDeviceInfo {
    private final RawDevice device;
    private final int id;
    private final int num_buttons;
    private final int sample_rate;

    public RawMouseInfo(RawDevice rawDevice, int n, int n2, int n3) {
        this.device = rawDevice;
        this.id = n;
        this.num_buttons = n2;
        this.sample_rate = n3;
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
        if (this.num_buttons == 0) {
            return null;
        }
        Component[] componentArray = new Component[3 + this.num_buttons];
        int n = 0;
        componentArray[n++] = new RawMouse.Axis(rawDevice, Component.Identifier.Axis.X);
        componentArray[n++] = new RawMouse.Axis(rawDevice, Component.Identifier.Axis.Y);
        componentArray[n++] = new RawMouse.Axis(rawDevice, Component.Identifier.Axis.Z);
        for (int i = 0; i < this.num_buttons; ++i) {
            Component.Identifier.Button button = DIIdentifierMap.mapMouseButtonIdentifier(DIIdentifierMap.getButtonIdentifier(i));
            componentArray[n++] = new RawMouse.Button(rawDevice, button, i);
        }
        RawMouse rawMouse = new RawMouse(setupAPIDevice.getName(), rawDevice, componentArray, new Controller[0], new Rumbler[0]);
        return rawMouse;
    }
}

