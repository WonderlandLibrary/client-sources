/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.DirectInputEnvironmentPlugin;
import net.java.games.input.DummyWindow;
import net.java.games.input.IDirectInputDevice;

final class IDirectInput {
    private final List devices = new ArrayList();
    private final long idirectinput_address;
    private final DummyWindow window;

    public IDirectInput(DummyWindow dummyWindow) throws IOException {
        this.window = dummyWindow;
        this.idirectinput_address = IDirectInput.createIDirectInput();
        try {
            this.enumDevices();
        } catch (IOException iOException) {
            this.releaseDevices();
            this.release();
            throw iOException;
        }
    }

    private static final native long createIDirectInput() throws IOException;

    public final List getDevices() {
        return this.devices;
    }

    private final void enumDevices() throws IOException {
        this.nEnumDevices(this.idirectinput_address);
    }

    private final native void nEnumDevices(long var1) throws IOException;

    private final void addDevice(long l, byte[] byArray, byte[] byArray2, int n, int n2, String string, String string2) throws IOException {
        try {
            IDirectInputDevice iDirectInputDevice = new IDirectInputDevice(this.window, l, byArray, byArray2, n, n2, string, string2);
            this.devices.add(iDirectInputDevice);
        } catch (IOException iOException) {
            DirectInputEnvironmentPlugin.logln("Failed to initialize device " + string2 + " because of: " + iOException);
        }
    }

    public final void releaseDevices() {
        for (int i = 0; i < this.devices.size(); ++i) {
            IDirectInputDevice iDirectInputDevice = (IDirectInputDevice)this.devices.get(i);
            iDirectInputDevice.release();
        }
    }

    public final void release() {
        IDirectInput.nRelease(this.idirectinput_address);
    }

    private static final native void nRelease(long var0);
}

