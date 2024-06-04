package org.lwjgl.util.mapped;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.MemoryUtil;














































public abstract class MappedObject
{
  static final boolean CHECKS = LWJGLUtil.getPrivilegedBoolean("org.lwjgl.util.mapped.Checks");
  




  public long baseAddress;
  




  public long viewAddress;
  




  ByteBuffer preventGC;
  




  public static int SIZEOF = -1;
  

  public int view;
  

  protected MappedObject() {}
  

  protected final long getViewAddress(int view)
  {
    throw new InternalError("type not registered");
  }
  
  public final void setViewAddress(long address) {
    if (CHECKS)
      checkAddress(address);
    viewAddress = address;
  }
  
  final void checkAddress(long address) {
    long base = MemoryUtil.getAddress0(preventGC);
    int offset = (int)(address - base);
    if ((address < base) || (preventGC.capacity() < offset + getSizeof()))
      throw new IndexOutOfBoundsException(Integer.toString(offset / getSizeof()));
  }
  
  final void checkRange(int bytes) {
    if (bytes < 0) {
      throw new IllegalArgumentException();
    }
    if (preventGC.capacity() < viewAddress - MemoryUtil.getAddress0(preventGC) + bytes) {
      throw new BufferOverflowException();
    }
  }
  





  public final int getAlign()
  {
    throw new InternalError("type not registered");
  }
  





  public final int getSizeof()
  {
    throw new InternalError("type not registered");
  }
  





  public final int capacity()
  {
    throw new InternalError("type not registered");
  }
  










  public static <T extends MappedObject> T map(ByteBuffer bb)
  {
    throw new InternalError("type not registered");
  }
  











  public static <T extends MappedObject> T map(long address, int capacity)
  {
    throw new InternalError("type not registered");
  }
  










  public static <T extends MappedObject> T malloc(int elementCount)
  {
    throw new InternalError("type not registered");
  }
  






  public final <T extends MappedObject> T dup()
  {
    throw new InternalError("type not registered");
  }
  




  public final <T extends MappedObject> T slice()
  {
    throw new InternalError("type not registered");
  }
  




  public final void runViewConstructor()
  {
    throw new InternalError("type not registered");
  }
  


  public final void next()
  {
    throw new InternalError("type not registered");
  }
  





  public final <T extends MappedObject> void copyTo(T target)
  {
    throw new InternalError("type not registered");
  }
  






  public final <T extends MappedObject> void copyRange(T target, int instances)
  {
    throw new InternalError("type not registered");
  }
  







  public static <T extends MappedObject> Iterable<T> foreach(T mapped)
  {
    return foreach(mapped, mapped.capacity());
  }
  







  public static <T extends MappedObject> Iterable<T> foreach(T mapped, int elementCount)
  {
    return new MappedForeach(mapped, elementCount);
  }
  

  public final <T extends MappedObject> T[] asArray()
  {
    throw new InternalError("type not registered");
  }
  




  public final ByteBuffer backingByteBuffer()
  {
    return preventGC;
  }
}
