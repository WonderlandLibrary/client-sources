package net.java.games.input;

import java.io.IOException;








































class LinuxComponent
  extends AbstractComponent
{
  private final LinuxEventComponent component;
  
  public LinuxComponent(LinuxEventComponent component)
  {
    super(component.getIdentifier().getName(), component.getIdentifier());
    this.component = component;
  }
  
  public final boolean isRelative() {
    return component.isRelative();
  }
  
  public final boolean isAnalog() {
    return component.isAnalog();
  }
  
  protected float poll() throws IOException {
    return convertValue(LinuxControllers.poll(component), component.getDescriptor());
  }
  
  float convertValue(float value, LinuxAxisDescriptor descriptor) {
    return getComponent().convertValue(value);
  }
  
  public final float getDeadZone() {
    return component.getDeadZone();
  }
  
  public final LinuxEventComponent getComponent() {
    return component;
  }
}
