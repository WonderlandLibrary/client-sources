package ninja.leaping.configurate.transformation;

import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import ninja.leaping.configurate.transformation.NodePath;

public final class ConfigurationTransformation$NodePath implements NodePath {
   Object[] arr;

   public Object get(int var1) {
      return this.arr[var1];
   }

   public int size() {
      return this.arr.length;
   }

   public Object[] getArray() {
      return Arrays.copyOf(this.arr, this.arr.length);
   }

   public Iterator iterator() {
      return Iterators.forArray(this.arr);
   }
}
