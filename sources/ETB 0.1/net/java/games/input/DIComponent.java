package net.java.games.input;

import java.io.IOException;








































final class DIComponent
  extends AbstractComponent
{
  private final DIDeviceObject object;
  
  public DIComponent(Component.Identifier identifier, DIDeviceObject object)
  {
    super(object.getName(), identifier);
    this.object = object;
  }
  
  public final boolean isRelative() {
    return object.isRelative();
  }
  
  public final boolean isAnalog() {
    return object.isAnalog();
  }
  
  public final float getDeadZone() {
    return object.getDeadzone();
  }
  
  public final DIDeviceObject getDeviceObject() {
    return object;
  }
  
  protected final float poll() throws IOException {
    return DIControllers.poll(this, object);
  }
}
