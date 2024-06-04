package org.lwjgl;


















public abstract class PointerWrapperAbstract
  implements PointerWrapper
{
  protected final long pointer;
  

















  protected PointerWrapperAbstract(long pointer)
  {
    this.pointer = pointer;
  }
  







  public boolean isValid()
  {
    return pointer != 0L;
  }
  




  public final void checkValid()
  {
    if ((LWJGLUtil.DEBUG) && (!isValid()))
      throw new IllegalStateException("This " + getClass().getSimpleName() + " pointer is not valid.");
  }
  
  public final long getPointer() {
    checkValid();
    return pointer;
  }
  
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PointerWrapperAbstract)) { return false;
    }
    PointerWrapperAbstract that = (PointerWrapperAbstract)o;
    
    if (pointer != pointer) { return false;
    }
    return true;
  }
  
  public int hashCode() {
    return (int)(pointer ^ pointer >>> 32);
  }
  
  public String toString() {
    return getClass().getSimpleName() + " pointer (0x" + Long.toHexString(pointer).toUpperCase() + ")";
  }
}
