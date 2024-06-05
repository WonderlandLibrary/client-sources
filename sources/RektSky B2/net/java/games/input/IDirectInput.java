package net.java.games.input;

import java.util.*;
import java.io.*;

final class IDirectInput
{
    private final List<IDirectInputDevice> devices;
    private final long idirectinput_address;
    private final DummyWindow window;
    
    public IDirectInput(final DummyWindow window) throws IOException {
        this.devices = new ArrayList<IDirectInputDevice>();
        this.window = window;
        this.idirectinput_address = createIDirectInput();
        try {
            this.enumDevices();
        }
        catch (IOException e) {
            this.releaseDevices();
            this.release();
            throw e;
        }
    }
    
    private static final native long createIDirectInput() throws IOException;
    
    public final List<IDirectInputDevice> getDevices() {
        return this.devices;
    }
    
    private final void enumDevices() throws IOException {
        this.nEnumDevices(this.idirectinput_address);
    }
    
    private final native void nEnumDevices(final long p0) throws IOException;
    
    private final void addDevice(final long address, final byte[] instance_guid, final byte[] product_guid, final int dev_type, final int dev_subtype, final String instance_name, final String product_name) throws IOException {
        try {
            final IDirectInputDevice device = new IDirectInputDevice(this.window, address, instance_guid, product_guid, dev_type, dev_subtype, instance_name, product_name);
            this.devices.add(device);
        }
        catch (IOException e) {
            ControllerEnvironment.log("Failed to initialize device " + product_name + " because of: " + e);
        }
    }
    
    public final void releaseDevices() {
        for (int i = 0; i < this.devices.size(); ++i) {
            final IDirectInputDevice device = this.devices.get(i);
            device.release();
        }
    }
    
    public final void release() {
        nRelease(this.idirectinput_address);
    }
    
    private static final native void nRelease(final long p0);
}
