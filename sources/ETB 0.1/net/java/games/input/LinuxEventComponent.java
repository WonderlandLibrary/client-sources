package net.java.games.input;

import java.io.IOException;




























final class LinuxEventComponent
{
  private final LinuxEventDevice device;
  private final Component.Identifier identifier;
  private final Controller.Type button_trait;
  private final boolean is_relative;
  private final LinuxAxisDescriptor descriptor;
  private final int min;
  private final int max;
  private final int flat;
  
  public LinuxEventComponent(LinuxEventDevice device, Component.Identifier identifier, boolean is_relative, int native_type, int native_code)
    throws IOException
  {
    this.device = device;
    this.identifier = identifier;
    if (native_type == 1) {
      button_trait = LinuxNativeTypesMap.guessButtonTrait(native_code);
    } else
      button_trait = Controller.Type.UNKNOWN;
    this.is_relative = is_relative;
    descriptor = new LinuxAxisDescriptor();
    descriptor.set(native_type, native_code);
    if (native_type == 3) {
      LinuxAbsInfo abs_info = new LinuxAbsInfo();
      getAbsInfo(abs_info);
      min = abs_info.getMin();
      max = abs_info.getMax();
      flat = abs_info.getFlat();
    } else {
      min = Integer.MIN_VALUE;
      max = Integer.MAX_VALUE;
      flat = 0;
    }
  }
  
  public final LinuxEventDevice getDevice() {
    return device;
  }
  
  public final void getAbsInfo(LinuxAbsInfo abs_info) throws IOException {
    assert (descriptor.getType() == 3);
    device.getAbsInfo(descriptor.getCode(), abs_info);
  }
  
  public final Controller.Type getButtonTrait() {
    return button_trait;
  }
  
  public final Component.Identifier getIdentifier() {
    return identifier;
  }
  
  public final LinuxAxisDescriptor getDescriptor() {
    return descriptor;
  }
  
  public final boolean isRelative() {
    return is_relative;
  }
  
  public final boolean isAnalog() {
    return ((identifier instanceof Component.Identifier.Axis)) && (identifier != Component.Identifier.Axis.POV);
  }
  
  final float convertValue(float value) {
    if (((identifier instanceof Component.Identifier.Axis)) && (!is_relative))
    {
      if (min == max)
        return 0.0F;
      if (value > max) {
        value = max;
      } else if (value < min)
        value = min;
      return 2.0F * (value - min) / (max - min) - 1.0F;
    }
    return value;
  }
  
  final float getDeadZone()
  {
    return flat / (2.0F * (max - min));
  }
}
