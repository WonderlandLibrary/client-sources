package net.java.games.input;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
































final class LinuxJoystickDevice
  implements LinuxDevice
{
  public static final int JS_EVENT_BUTTON = 1;
  public static final int JS_EVENT_AXIS = 2;
  public static final int JS_EVENT_INIT = 128;
  public static final int AXIS_MAX_VALUE = 32767;
  private final long fd;
  private final String name;
  private final LinuxJoystickEvent joystick_event = new LinuxJoystickEvent();
  private final Event event = new Event();
  private final LinuxJoystickButton[] buttons;
  private final LinuxJoystickAxis[] axes;
  private final Map povXs = new HashMap();
  private final Map povYs = new HashMap();
  
  private final byte[] axisMap;
  
  private final char[] buttonMap;
  
  private EventQueue event_queue;
  private boolean closed;
  
  public LinuxJoystickDevice(String filename)
    throws IOException
  {
    fd = nOpen(filename);
    try {
      name = getDeviceName();
      setBufferSize(32);
      buttons = new LinuxJoystickButton[getNumDeviceButtons()];
      axes = new LinuxJoystickAxis[getNumDeviceAxes()];
      axisMap = getDeviceAxisMap();
      buttonMap = getDeviceButtonMap();
    } catch (IOException e) {
      close();
      throw e;
    }
  }
  
  private static final native long nOpen(String paramString) throws IOException;
  
  public final synchronized void setBufferSize(int size) { event_queue = new EventQueue(size); }
  
  private final void processEvent(LinuxJoystickEvent joystick_event)
  {
    int index = joystick_event.getNumber();
    
    int type = joystick_event.getType() & 0xFF7F;
    switch (type) {
    case 1: 
      if (index < getNumButtons()) {
        LinuxJoystickButton button = buttons[index];
        if (button != null) {
          float value = joystick_event.getValue();
          button.setValue(value);
          event.set(button, value, joystick_event.getNanos());
          break;
        }
      }
      return;
    case 2: 
      if (index < getNumAxes()) {
        LinuxJoystickAxis axis = axes[index];
        if (axis != null) {
          float value = joystick_event.getValue() / 32767.0F;
          axis.setValue(value);
          if (povXs.containsKey(new Integer(index))) {
            LinuxJoystickPOV pov = (LinuxJoystickPOV)povXs.get(new Integer(index));
            pov.updateValue();
            event.set(pov, pov.getPollData(), joystick_event.getNanos());
            break; } if (povYs.containsKey(new Integer(index))) {
            LinuxJoystickPOV pov = (LinuxJoystickPOV)povYs.get(new Integer(index));
            pov.updateValue();
            event.set(pov, pov.getPollData(), joystick_event.getNanos());
            break; }
          event.set(axis, value, joystick_event.getNanos());
          
          break;
        }
      }
      return;
    
    default: 
      return;
    }
    if (!event_queue.isFull()) {
      event_queue.add(event);
    }
  }
  
  public final void registerAxis(int index, LinuxJoystickAxis axis) {
    axes[index] = axis;
  }
  
  public final void registerButton(int index, LinuxJoystickButton button) {
    buttons[index] = button;
  }
  
  public final void registerPOV(LinuxJoystickPOV pov)
  {
    LinuxJoystickAxis xAxis = pov.getYAxis();
    LinuxJoystickAxis yAxis = pov.getXAxis();
    

    for (int xIndex = 0; xIndex < axes.length; xIndex++) {
      if (axes[xIndex] == xAxis) {
        break;
      }
    }
    for (int yIndex = 0; yIndex < axes.length; yIndex++) {
      if (axes[yIndex] == yAxis) {
        break;
      }
    }
    povXs.put(new Integer(xIndex), pov);
    povYs.put(new Integer(yIndex), pov);
  }
  
  public final synchronized boolean getNextEvent(Event event) throws IOException {
    return event_queue.getNextEvent(event);
  }
  
  public final synchronized void poll() throws IOException {
    checkClosed();
    while (getNextDeviceEvent(joystick_event)) {
      processEvent(joystick_event);
    }
  }
  
  private final boolean getNextDeviceEvent(LinuxJoystickEvent joystick_event) throws IOException {
    return nGetNextEvent(fd, joystick_event);
  }
  
  private static final native boolean nGetNextEvent(long paramLong, LinuxJoystickEvent paramLinuxJoystickEvent) throws IOException;
  
  public final int getNumAxes() { return axes.length; }
  
  public final int getNumButtons()
  {
    return buttons.length;
  }
  
  public final byte[] getAxisMap() {
    return axisMap;
  }
  
  public final char[] getButtonMap() {
    return buttonMap;
  }
  
  private final int getNumDeviceButtons() throws IOException {
    return nGetNumButtons(fd);
  }
  
  private static final native int nGetNumButtons(long paramLong) throws IOException;
  
  private final int getNumDeviceAxes() throws IOException { return nGetNumAxes(fd); }
  
  private static final native int nGetNumAxes(long paramLong) throws IOException;
  
  private final byte[] getDeviceAxisMap() throws IOException {
    return nGetAxisMap(fd);
  }
  
  private static final native byte[] nGetAxisMap(long paramLong) throws IOException;
  
  private final char[] getDeviceButtonMap() throws IOException { return nGetButtonMap(fd); }
  
  private static final native char[] nGetButtonMap(long paramLong) throws IOException;
  
  private final int getVersion() throws IOException {
    return nGetVersion(fd);
  }
  
  private static final native int nGetVersion(long paramLong) throws IOException;
  
  public final String getName() { return name; }
  


  private final String getDeviceName() throws IOException { return nGetName(fd); }
  
  private static final native String nGetName(long paramLong) throws IOException;
  
  public final synchronized void close() throws IOException {
    if (!closed) {
      closed = true;
      nClose(fd);
    }
  }
  
  private static final native void nClose(long paramLong) throws IOException;
  
  private final void checkClosed() throws IOException { if (closed)
      throw new IOException("Device is closed");
  }
  
  protected void finalize() throws IOException {
    close();
  }
}
