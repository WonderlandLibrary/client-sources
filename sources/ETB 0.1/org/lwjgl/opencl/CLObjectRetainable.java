package org.lwjgl.opencl;


















abstract class CLObjectRetainable
  extends CLObject
{
  private int refCount;
  

















  protected CLObjectRetainable(long pointer)
  {
    super(pointer);
    
    if (super.isValid())
      refCount = 1;
  }
  
  public final int getReferenceCount() {
    return refCount;
  }
  
  public final boolean isValid() {
    return refCount > 0;
  }
  
  int retain() {
    checkValid();
    
    return ++refCount;
  }
  
  int release() {
    checkValid();
    
    return --refCount;
  }
}
