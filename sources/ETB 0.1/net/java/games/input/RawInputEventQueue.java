package net.java.games.input;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;






































final class RawInputEventQueue
{
  private final Object monitor;
  private List devices;
  
  RawInputEventQueue()
  {
    monitor = new Object();
  }
  
  public final void start(List devices) throws IOException
  {
    this.devices = devices;
    QueueThread queue = new QueueThread();
    synchronized (monitor) {
      queue.start();
      
      while (!queue.isInitialized()) {
        try {
          monitor.wait();
        } catch (InterruptedException e) {}
      }
    }
    if (queue.getException() != null)
      throw queue.getException();
  }
  
  private final RawDevice lookupDevice(long handle) {
    for (int i = 0; i < devices.size(); i++) {
      RawDevice device = (RawDevice)devices.get(i);
      if (device.getHandle() == handle)
        return device;
    }
    return null;
  }
  
  private final void addMouseEvent(long handle, long millis, int flags, int button_flags, int button_data, long raw_buttons, long last_x, long last_y, long extra_information)
  {
    RawDevice device = lookupDevice(handle);
    if (device == null)
      return;
    device.addMouseEvent(millis, flags, button_flags, button_data, raw_buttons, last_x, last_y, extra_information);
  }
  
  private final void addKeyboardEvent(long handle, long millis, int make_code, int flags, int vkey, int message, long extra_information) {
    RawDevice device = lookupDevice(handle);
    if (device == null)
      return;
    device.addKeyboardEvent(millis, make_code, flags, vkey, message, extra_information);
  }
  
  private final void poll(DummyWindow window) throws IOException {
    nPoll(window.getHwnd());
  }
  
  private final native void nPoll(long paramLong) throws IOException;
  
  private static final void registerDevices(DummyWindow window, RawDeviceInfo[] devices) throws IOException { nRegisterDevices(0, window.getHwnd(), devices); }
  
  private static final native void nRegisterDevices(int paramInt, long paramLong, RawDeviceInfo[] paramArrayOfRawDeviceInfo) throws IOException;
  
  private final class QueueThread extends Thread {
    private boolean initialized;
    private DummyWindow window;
    private IOException exception;
    
    public QueueThread() {
      setDaemon(true);
    }
    
    public final boolean isInitialized() {
      return initialized;
    }
    
    public final IOException getException() {
      return exception;
    }
    
    public final void run()
    {
      try {
        window = new DummyWindow();
      } catch (IOException e) {
        exception = e;
      }
      initialized = true;
      synchronized (monitor) {
        monitor.notify();
      }
      if (exception != null)
        return;
      Set active_infos = new HashSet();
      try {
        for (int i = 0; i < devices.size(); i++) {
          RawDevice device = (RawDevice)devices.get(i);
          active_infos.add(device.getInfo());
        }
        RawDeviceInfo[] active_infos_array = new RawDeviceInfo[active_infos.size()];
        active_infos.toArray(active_infos_array);
        try {
          RawInputEventQueue.registerDevices(window, active_infos_array);
          while (!isInterrupted()) {
            RawInputEventQueue.this.poll(window);
          }
        } finally {
          window.destroy();
        }
      } catch (IOException e) {
        exception = e;
      }
    }
  }
}
