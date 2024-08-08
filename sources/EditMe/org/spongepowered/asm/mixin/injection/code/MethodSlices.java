package org.spongepowered.asm.mixin.injection.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;

public final class MethodSlices {
   private final InjectionInfo info;
   private final Map slices = new HashMap(4);

   private MethodSlices(InjectionInfo var1) {
      this.info = var1;
   }

   private void add(MethodSlice var1) {
      String var2 = this.info.getSliceId(var1.getId());
      if (this.slices.containsKey(var2)) {
         throw new InvalidSliceException(this.info, var1 + " has a duplicate id, '" + var2 + "' was already defined");
      } else {
         this.slices.put(var2, var1);
      }
   }

   public MethodSlice get(String var1) {
      return (MethodSlice)this.slices.get(var1);
   }

   public String toString() {
      return String.format("MethodSlices%s", this.slices.keySet());
   }

   public static MethodSlices parse(InjectionInfo var0) {
      MethodSlices var1 = new MethodSlices(var0);
      AnnotationNode var2 = var0.getAnnotation();
      if (var2 != null) {
         Iterator var3 = Annotations.getValue(var2, "slice", true).iterator();

         while(var3.hasNext()) {
            AnnotationNode var4 = (AnnotationNode)var3.next();
            MethodSlice var5 = MethodSlice.parse(var0, (AnnotationNode)var4);
            var1.add(var5);
         }
      }

      return var1;
   }
}
