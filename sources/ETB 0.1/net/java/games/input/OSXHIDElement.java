package net.java.games.input;

import java.io.IOException;










































final class OSXHIDElement
{
  private final OSXHIDDevice device;
  private final UsagePair usage_pair;
  private final long element_cookie;
  private final ElementType element_type;
  private final int min;
  private final int max;
  private final Component.Identifier identifier;
  private final boolean is_relative;
  
  public OSXHIDElement(OSXHIDDevice device, UsagePair usage_pair, long element_cookie, ElementType element_type, int min, int max, boolean is_relative)
  {
    this.device = device;
    this.usage_pair = usage_pair;
    this.element_cookie = element_cookie;
    this.element_type = element_type;
    this.min = min;
    this.max = max;
    identifier = computeIdentifier();
    this.is_relative = is_relative;
  }
  
  private final Component.Identifier computeIdentifier() {
    if (usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP)
      return ((GenericDesktopUsage)usage_pair.getUsage()).getIdentifier();
    if (usage_pair.getUsagePage() == UsagePage.BUTTON)
      return ((ButtonUsage)usage_pair.getUsage()).getIdentifier();
    if (usage_pair.getUsagePage() == UsagePage.KEYBOARD_OR_KEYPAD) {
      return ((KeyboardUsage)usage_pair.getUsage()).getIdentifier();
    }
    return null;
  }
  
  final Component.Identifier getIdentifier() {
    return identifier;
  }
  
  final long getCookie() {
    return element_cookie;
  }
  
  final ElementType getType() {
    return element_type;
  }
  
  final boolean isRelative() {
    return (is_relative) && ((identifier instanceof Component.Identifier.Axis));
  }
  
  final boolean isAnalog() {
    return ((identifier instanceof Component.Identifier.Axis)) && (identifier != Component.Identifier.Axis.POV);
  }
  
  private UsagePair getUsagePair() {
    return usage_pair;
  }
  
  final void getElementValue(OSXEvent event) throws IOException {
    device.getElementValue(element_cookie, event);
  }
  
  final float convertValue(float value) {
    if (identifier == Component.Identifier.Axis.POV) {
      switch ((int)value) {
      case 0: 
        return 0.25F;
      case 1: 
        return 0.375F;
      case 2: 
        return 0.5F;
      case 3: 
        return 0.625F;
      case 4: 
        return 0.75F;
      case 5: 
        return 0.875F;
      case 6: 
        return 1.0F;
      case 7: 
        return 0.125F;
      case 8: 
        return 0.0F;
      }
      return 0.0F;
    }
    if (((identifier instanceof Component.Identifier.Axis)) && (!is_relative)) {
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
}
