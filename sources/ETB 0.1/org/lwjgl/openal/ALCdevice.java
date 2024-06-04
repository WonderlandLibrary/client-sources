package org.lwjgl.openal;

import java.util.HashMap;




















































public final class ALCdevice
{
  final long device;
  private boolean valid;
  private final HashMap<Long, ALCcontext> contexts = new HashMap();
  




  ALCdevice(long device)
  {
    this.device = device;
    valid = true;
  }
  


  public boolean equals(Object device)
  {
    if ((device instanceof ALCdevice)) {
      return device == this.device;
    }
    return super.equals(device);
  }
  




  void addContext(ALCcontext context)
  {
    synchronized (contexts) {
      contexts.put(Long.valueOf(context), context);
    }
  }
  




  void removeContext(ALCcontext context)
  {
    synchronized (contexts) {
      contexts.remove(Long.valueOf(context));
    }
  }
  


  void setInvalid()
  {
    valid = false;
    synchronized (contexts) {
      for (ALCcontext context : contexts.values())
        context.setInvalid();
    }
    contexts.clear();
  }
  


  public boolean isValid()
  {
    return valid;
  }
}
