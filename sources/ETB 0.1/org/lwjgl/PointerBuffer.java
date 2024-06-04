package org.lwjgl;

import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;


























public class PointerBuffer
  implements Comparable
{
  private static final boolean is64Bit;
  protected final ByteBuffer pointers;
  protected final Buffer view;
  protected final IntBuffer view32;
  protected final LongBuffer view64;
  
  static
  {
    boolean is64 = false;
    try {
      Method m = Class.forName("org.lwjgl.Sys").getDeclaredMethod("is64Bit", (Class[])null);
      is64 = ((Boolean)m.invoke(null, (Object[])null)).booleanValue();
    }
    catch (Throwable t) {}finally
    {
      is64Bit = is64;
    }
  }
  










  public PointerBuffer(int capacity)
  {
    this(BufferUtils.createByteBuffer(capacity * getPointerSize()));
  }
  






  public PointerBuffer(ByteBuffer source)
  {
    if (LWJGLUtil.CHECKS) {
      checkSource(source);
    }
    pointers = source.slice().order(source.order());
    
    if (is64Bit) {
      view32 = null;
      view = (this.view64 = pointers.asLongBuffer());
    } else {
      view = (this.view32 = pointers.asIntBuffer());
      view64 = null;
    }
  }
  
  private static void checkSource(ByteBuffer source) {
    if (!source.isDirect()) {
      throw new IllegalArgumentException("The source buffer is not direct.");
    }
    int alignment = is64Bit ? 8 : 4;
    if (((MemoryUtil.getAddress0(source) + source.position()) % alignment != 0L) || (source.remaining() % alignment != 0)) {
      throw new IllegalArgumentException("The source buffer is not aligned to " + alignment + " bytes.");
    }
  }
  



  public ByteBuffer getBuffer()
  {
    return pointers;
  }
  
  public static boolean is64Bit()
  {
    return is64Bit;
  }
  




  public static int getPointerSize()
  {
    return is64Bit ? 8 : 4;
  }
  




  public final int capacity()
  {
    return view.capacity();
  }
  




  public final int position()
  {
    return view.position();
  }
  




  public final int positionByte()
  {
    return position() * getPointerSize();
  }
  










  public final PointerBuffer position(int newPosition)
  {
    view.position(newPosition);
    return this;
  }
  




  public final int limit()
  {
    return view.limit();
  }
  











  public final PointerBuffer limit(int newLimit)
  {
    view.limit(newLimit);
    return this;
  }
  




  public final PointerBuffer mark()
  {
    view.mark();
    return this;
  }
  









  public final PointerBuffer reset()
  {
    view.reset();
    return this;
  }
  
















  public final PointerBuffer clear()
  {
    view.clear();
    return this;
  }
  




















  public final PointerBuffer flip()
  {
    view.flip();
    return this;
  }
  














  public final PointerBuffer rewind()
  {
    view.rewind();
    return this;
  }
  





  public final int remaining()
  {
    return view.remaining();
  }
  





  public final int remainingByte()
  {
    return remaining() * getPointerSize();
  }
  






  public final boolean hasRemaining()
  {
    return view.hasRemaining();
  }
  











  public static PointerBuffer allocateDirect(int capacity)
  {
    return new PointerBuffer(capacity);
  }
  







  protected PointerBuffer newInstance(ByteBuffer source)
  {
    return new PointerBuffer(source);
  }
  
















  public PointerBuffer slice()
  {
    int pointerSize = getPointerSize();
    
    pointers.position(view.position() * pointerSize);
    pointers.limit(view.limit() * pointerSize);
    
    try
    {
      return newInstance(pointers);
    } finally {
      pointers.clear();
    }
  }
  














  public PointerBuffer duplicate()
  {
    PointerBuffer buffer = newInstance(pointers);
    
    buffer.position(view.position());
    buffer.limit(view.limit());
    
    return buffer;
  }
  

















  public PointerBuffer asReadOnlyBuffer()
  {
    PointerBuffer buffer = new PointerBufferR(pointers);
    
    buffer.position(view.position());
    buffer.limit(view.limit());
    
    return buffer;
  }
  
  public boolean isReadOnly() {
    return false;
  }
  







  public long get()
  {
    if (is64Bit) {
      return view64.get();
    }
    return view32.get() & 0xFFFFFFFF;
  }
  












  public PointerBuffer put(long l)
  {
    if (is64Bit) {
      view64.put(l);
    } else
      view32.put((int)l);
    return this;
  }
  




  public PointerBuffer put(PointerWrapper pointer)
  {
    return put(pointer.getPointer());
  }
  





  public static void put(ByteBuffer target, long l)
  {
    if (is64Bit) {
      target.putLong(l);
    } else {
      target.putInt((int)l);
    }
  }
  









  public long get(int index)
  {
    if (is64Bit) {
      return view64.get(index);
    }
    return view32.get(index) & 0xFFFFFFFF;
  }
  














  public PointerBuffer put(int index, long l)
  {
    if (is64Bit) {
      view64.put(index, l);
    } else
      view32.put(index, (int)l);
    return this;
  }
  




  public PointerBuffer put(int index, PointerWrapper pointer)
  {
    return put(index, pointer.getPointer());
  }
  






  public static void put(ByteBuffer target, int index, long l)
  {
    if (is64Bit) {
      target.putLong(index, l);
    } else {
      target.putInt(index, (int)l);
    }
  }
  









































  public PointerBuffer get(long[] dst, int offset, int length)
  {
    if (is64Bit) {
      view64.get(dst, offset, length);
    } else {
      checkBounds(offset, length, dst.length);
      if (length > view32.remaining())
        throw new BufferUnderflowException();
      int end = offset + length;
      for (int i = offset; i < end; i++) {
        dst[i] = (view32.get() & 0xFFFFFFFF);
      }
    }
    return this;
  }
  














  public PointerBuffer get(long[] dst)
  {
    return get(dst, 0, dst.length);
  }
  


































  public PointerBuffer put(PointerBuffer src)
  {
    if (is64Bit) {
      view64.put(view64);
    } else
      view32.put(view32);
    return this;
  }
  







































  public PointerBuffer put(long[] src, int offset, int length)
  {
    if (is64Bit) {
      view64.put(src, offset, length);
    } else {
      checkBounds(offset, length, src.length);
      if (length > view32.remaining())
        throw new BufferOverflowException();
      int end = offset + length;
      for (int i = offset; i < end; i++) {
        view32.put((int)src[i]);
      }
    }
    return this;
  }
  















  public final PointerBuffer put(long[] src)
  {
    return put(src, 0, src.length);
  }
  





















  public PointerBuffer compact()
  {
    if (is64Bit) {
      view64.compact();
    } else
      view32.compact();
    return this;
  }
  











  public ByteOrder order()
  {
    if (is64Bit) {
      return view64.order();
    }
    return view32.order();
  }
  




  public String toString()
  {
    StringBuilder sb = new StringBuilder(48);
    sb.append(getClass().getName());
    sb.append("[pos=");
    sb.append(position());
    sb.append(" lim=");
    sb.append(limit());
    sb.append(" cap=");
    sb.append(capacity());
    sb.append("]");
    return sb.toString();
  }
  












  public int hashCode()
  {
    int h = 1;
    int p = position();
    for (int i = limit() - 1; i >= p; i--)
      h = 31 * h + (int)get(i);
    return h;
  }
  
























  public boolean equals(Object ob)
  {
    if (!(ob instanceof PointerBuffer))
      return false;
    PointerBuffer that = (PointerBuffer)ob;
    if (remaining() != that.remaining())
      return false;
    int p = position();
    int i = limit() - 1; for (int j = that.limit() - 1; i >= p; j--) {
      long v1 = get(i);
      long v2 = that.get(j);
      if (v1 != v2) {
        return false;
      }
      i--;
    }
    




    return true;
  }
  











  public int compareTo(Object o)
  {
    PointerBuffer that = (PointerBuffer)o;
    int n = position() + Math.min(remaining(), that.remaining());
    int i = position(); for (int j = that.position(); i < n; j++) {
      long v1 = get(i);
      long v2 = that.get(j);
      if (v1 != v2)
      {
        if (v1 < v2)
          return -1;
        return 1;
      }
      i++;
    }
    






    return remaining() - that.remaining();
  }
  
  private static void checkBounds(int off, int len, int size) {
    if ((off | len | off + len | size - (off + len)) < 0) {
      throw new IndexOutOfBoundsException();
    }
  }
  


  private static final class PointerBufferR
    extends PointerBuffer
  {
    PointerBufferR(ByteBuffer source)
    {
      super();
    }
    
    public boolean isReadOnly() {
      return true;
    }
    
    protected PointerBuffer newInstance(ByteBuffer source) {
      return new PointerBufferR(source);
    }
    
    public PointerBuffer asReadOnlyBuffer() {
      return duplicate();
    }
    
    public PointerBuffer put(long l) {
      throw new ReadOnlyBufferException();
    }
    
    public PointerBuffer put(int index, long l) {
      throw new ReadOnlyBufferException();
    }
    
    public PointerBuffer put(PointerBuffer src) {
      throw new ReadOnlyBufferException();
    }
    
    public PointerBuffer put(long[] src, int offset, int length) {
      throw new ReadOnlyBufferException();
    }
    
    public PointerBuffer compact() {
      throw new ReadOnlyBufferException();
    }
  }
}
