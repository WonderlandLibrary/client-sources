package net.java.games.input;

import java.io.IOException;






































abstract class RawDeviceInfo
{
  RawDeviceInfo() {}
  
  public abstract Controller createControllerFromDevice(RawDevice paramRawDevice, SetupAPIDevice paramSetupAPIDevice)
    throws IOException;
  
  public abstract int getUsage();
  
  public abstract int getUsagePage();
  
  public abstract long getHandle();
  
  public final boolean equals(Object other)
  {
    if (!(other instanceof RawDeviceInfo))
      return false;
    RawDeviceInfo other_info = (RawDeviceInfo)other;
    return (other_info.getUsage() == getUsage()) && (other_info.getUsagePage() == getUsagePage());
  }
  
  public final int hashCode()
  {
    return getUsage() ^ getUsagePage();
  }
}
