package net.java.games.input;

import java.io.IOException;








































final class DIKeyboard
  extends Keyboard
{
  private final IDirectInputDevice device;
  
  protected DIKeyboard(IDirectInputDevice device, Component[] components, Controller[] children, Rumbler[] rumblers)
  {
    super(device.getProductName(), components, children, rumblers);
    this.device = device;
  }
  
  protected final boolean getNextDeviceEvent(Event event) throws IOException {
    return DIControllers.getNextDeviceEvent(event, device);
  }
  
  public final void pollDevice() throws IOException {
    device.pollAll();
  }
  
  protected final void setDeviceEventQueueSize(int size) throws IOException {
    device.setBufferSize(size);
  }
}
