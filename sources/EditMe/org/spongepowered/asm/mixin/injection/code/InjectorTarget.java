package org.spongepowered.asm.mixin.injection.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.Target;

public class InjectorTarget {
   private final ISliceContext context;
   private final Map cache = new HashMap();
   private final Target target;

   public InjectorTarget(ISliceContext var1, Target var2) {
      this.context = var1;
      this.target = var2;
   }

   public Target getTarget() {
      return this.target;
   }

   public MethodNode getMethod() {
      return this.target.method;
   }

   public InsnList getSlice(String var1) {
      ReadOnlyInsnList var2 = (ReadOnlyInsnList)this.cache.get(var1);
      if (var2 == null) {
         MethodSlice var3 = this.context.getSlice(var1);
         if (var3 != null) {
            var2 = var3.getSlice(this.target.method);
         } else {
            var2 = new ReadOnlyInsnList(this.target.method.instructions);
         }

         this.cache.put(var1, var2);
      }

      return var2;
   }

   public InsnList getSlice(InjectionPoint var1) {
      return this.getSlice(var1.getSlice());
   }

   public void dispose() {
      Iterator var1 = this.cache.values().iterator();

      while(var1.hasNext()) {
         ReadOnlyInsnList var2 = (ReadOnlyInsnList)var1.next();
         var2.dispose();
      }

      this.cache.clear();
   }
}
