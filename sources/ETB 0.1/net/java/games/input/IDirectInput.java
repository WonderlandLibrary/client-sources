package net.java.games.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;











































final class IDirectInput
{
  private final List devices = new ArrayList();
  private final long idirectinput_address;
  private final DummyWindow window;
  
  public IDirectInput(DummyWindow window) throws IOException {
    this.window = window;
    idirectinput_address = createIDirectInput();
    try {
      enumDevices();
    } catch (IOException e) {
      releaseDevices();
      release();
      throw e;
    }
  }
  
  private static final native long createIDirectInput() throws IOException;
  
  public final List getDevices() { return devices; }
  
  private final void enumDevices() throws IOException
  {
    nEnumDevices(idirectinput_address);
  }
  
  private final native void nEnumDevices(long paramLong) throws IOException;
  
  private final void addDevice(long address, byte[] instance_guid, byte[] product_guid, int dev_type, int dev_subtype, String instance_name, String product_name) throws IOException
  {
    try
    {
      IDirectInputDevice device = new IDirectInputDevice(window, address, instance_guid, product_guid, dev_type, dev_subtype, instance_name, product_name);
      devices.add(device);
    } catch (IOException e) {
      DirectInputEnvironmentPlugin.logln("Failed to initialize device " + product_name + " because of: " + e);
    }
  }
  
  public final void releaseDevices() {
    for (int i = 0; i < devices.size(); i++) {
      IDirectInputDevice device = (IDirectInputDevice)devices.get(i);
      device.release();
    }
  }
  
  public final void release() {
    nRelease(idirectinput_address);
  }
  
  private static final native void nRelease(long paramLong);
}
