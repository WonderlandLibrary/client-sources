package org.lwjgl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import sun.misc.Unsafe;
import sun.reflect.FieldAccessor;







































final class MemoryUtilSun
{
  private MemoryUtilSun() {}
  
  private static class AccessorUnsafe
    implements MemoryUtil.Accessor
  {
    private final Unsafe unsafe;
    private final long address;
    
    AccessorUnsafe()
    {
      try
      {
        unsafe = getUnsafeInstance();
        address = unsafe.objectFieldOffset(MemoryUtil.getAddressField());
      } catch (Exception e) {
        throw new UnsupportedOperationException(e);
      }
    }
    
    public long getAddress(Buffer buffer) {
      return unsafe.getLong(buffer, address);
    }
    
    private static Unsafe getUnsafeInstance() {
      Field[] fields = Unsafe.class.getDeclaredFields();
      








      for (Field field : fields) {
        if (field.getType().equals(Unsafe.class))
        {

          int modifiers = field.getModifiers();
          if ((Modifier.isStatic(modifiers)) && (Modifier.isFinal(modifiers)))
          {

            field.setAccessible(true);
            try {
              return (Unsafe)field.get(null);
            }
            catch (IllegalAccessException e) {}
          }
        }
      }
      
      throw new UnsupportedOperationException();
    }
  }
  
  private static class AccessorReflectFast implements MemoryUtil.Accessor
  {
    private final FieldAccessor addressAccessor;
    
    AccessorReflectFast()
    {
      Field address;
      try
      {
        address = MemoryUtil.getAddressField();
      } catch (NoSuchFieldException e) {
        throw new UnsupportedOperationException(e);
      }
      address.setAccessible(true);
      try
      {
        Method m = Field.class.getDeclaredMethod("acquireFieldAccessor", new Class[] { Boolean.TYPE });
        m.setAccessible(true);
        addressAccessor = ((FieldAccessor)m.invoke(address, new Object[] { Boolean.valueOf(true) }));
      } catch (Exception e) {
        throw new UnsupportedOperationException(e);
      }
    }
    
    public long getAddress(Buffer buffer) {
      return addressAccessor.getLong(buffer);
    }
  }
}
