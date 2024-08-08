package org.spongepowered.asm.mixin.gen;

import java.util.ArrayList;
import org.spongepowered.asm.lib.tree.MethodNode;

public abstract class AccessorGenerator {
   protected final AccessorInfo info;

   public AccessorGenerator(AccessorInfo var1) {
      this.info = var1;
   }

   protected final MethodNode createMethod(int var1, int var2) {
      MethodNode var3 = this.info.getMethod();
      MethodNode var4 = new MethodNode(327680, var3.access & -1025 | 4096, var3.name, var3.desc, (String)null, (String[])null);
      var4.visibleAnnotations = new ArrayList();
      var4.visibleAnnotations.add(this.info.getAnnotation());
      var4.maxLocals = var1;
      var4.maxStack = var2;
      return var4;
   }

   public abstract MethodNode generate();
}
