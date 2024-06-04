package net.java.games.input;

import java.io.IOException;








































final class OSXKeyboard
  extends Keyboard
{
  private final Controller.PortType port;
  private final OSXHIDQueue queue;
  
  protected OSXKeyboard(OSXHIDDevice device, OSXHIDQueue queue, Component[] components, Controller[] children, Rumbler[] rumblers)
  {
    super(device.getProductName(), components, children, rumblers);
    this.queue = queue;
    port = device.getPortType();
  }
  
  protected final boolean getNextDeviceEvent(Event event) throws IOException {
    return OSXControllers.getNextDeviceEvent(event, queue);
  }
  
  protected final void setDeviceEventQueueSize(int size) throws IOException {
    queue.setQueueDepth(size);
  }
  
  public final Controller.PortType getPortType() {
    return port;
  }
}
