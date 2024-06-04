package net.java.games.input;

import java.io.IOException;








































final class OSXAbstractController
  extends AbstractController
{
  private final Controller.PortType port;
  private final OSXHIDQueue queue;
  private final Controller.Type type;
  
  protected OSXAbstractController(OSXHIDDevice device, OSXHIDQueue queue, Component[] components, Controller[] children, Rumbler[] rumblers, Controller.Type type)
  {
    super(device.getProductName(), components, children, rumblers);
    this.queue = queue;
    this.type = type;
    port = device.getPortType();
  }
  
  protected final boolean getNextDeviceEvent(Event event) throws IOException {
    return OSXControllers.getNextDeviceEvent(event, queue);
  }
  
  protected final void setDeviceEventQueueSize(int size) throws IOException {
    queue.setQueueDepth(size);
  }
  
  public Controller.Type getType() {
    return type;
  }
  
  public final Controller.PortType getPortType() {
    return port;
  }
}
