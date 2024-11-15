package exhibition.management;

import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public abstract class AbstractManager {
   private final Class typeClass;
   protected Object[] array;

   public AbstractManager(Class typeClass, int size) {
      this.typeClass = typeClass;
      this.reset(size);
   }

   public abstract void setup();

   public void set(Object item, int index) {
      this.array[index] = item;
   }

   public Object get(int index) {
      return this.array[index];
   }

   public Object get(Class clazz) {
      Object[] var2 = this.array;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object e = var2[var4];
         if (e.getClass().equals(clazz)) {
            return e;
         }
      }

      return null;
   }

   public void add(Object e) {
      this.array = Arrays.copyOf(this.array, this.array.length + 1);
      this.array[this.array.length - 1] = e;
   }

   public void remove(Object e) {
      this.array = ArrayUtils.removeElement(this.array, e);
   }

   public void reset(int size) {
      this.array = (Object[])((Object[])Array.newInstance(this.typeClass, size));
   }

   public Object[] getArray() {
      return this.array;
   }
}
