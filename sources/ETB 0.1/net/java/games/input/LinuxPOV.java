package net.java.games.input;

import java.io.IOException;









































final class LinuxPOV
  extends LinuxComponent
{
  private final LinuxEventComponent component_x;
  private final LinuxEventComponent component_y;
  private float last_x;
  private float last_y;
  
  public LinuxPOV(LinuxEventComponent component_x, LinuxEventComponent component_y)
  {
    super(component_x);
    this.component_x = component_x;
    this.component_y = component_y;
  }
  
  protected final float poll() throws IOException {
    last_x = LinuxControllers.poll(component_x);
    last_y = LinuxControllers.poll(component_y);
    return convertValue(0.0F, null);
  }
  
  public float convertValue(float value, LinuxAxisDescriptor descriptor) {
    if (descriptor == component_x.getDescriptor())
      last_x = value;
    if (descriptor == component_y.getDescriptor()) {
      last_y = value;
    }
    if ((last_x == -1.0F) && (last_y == -1.0F))
      return 0.125F;
    if ((last_x == -1.0F) && (last_y == 0.0F))
      return 1.0F;
    if ((last_x == -1.0F) && (last_y == 1.0F))
      return 0.875F;
    if ((last_x == 0.0F) && (last_y == -1.0F))
      return 0.25F;
    if ((last_x == 0.0F) && (last_y == 0.0F))
      return 0.0F;
    if ((last_x == 0.0F) && (last_y == 1.0F))
      return 0.75F;
    if ((last_x == 1.0F) && (last_y == -1.0F))
      return 0.375F;
    if ((last_x == 1.0F) && (last_y == 0.0F))
      return 0.5F;
    if ((last_x == 1.0F) && (last_y == 1.0F)) {
      return 0.625F;
    }
    LinuxEnvironmentPlugin.logln("Unknown values x = " + last_x + " | y = " + last_y);
    return 0.0F;
  }
}
