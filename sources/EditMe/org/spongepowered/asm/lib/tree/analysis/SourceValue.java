package org.spongepowered.asm.lib.tree.analysis;

import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class SourceValue implements Value {
   public final int size;
   public final Set insns;

   public SourceValue(int var1) {
      this(var1, SmallSet.emptySet());
   }

   public SourceValue(int var1, AbstractInsnNode var2) {
      this.size = var1;
      this.insns = new SmallSet(var2, (Object)null);
   }

   public SourceValue(int var1, Set var2) {
      this.size = var1;
      this.insns = var2;
   }

   public int getSize() {
      return this.size;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof SourceValue)) {
         return false;
      } else {
         SourceValue var2 = (SourceValue)var1;
         return this.size == var2.size && this.insns.equals(var2.insns);
      }
   }

   public int hashCode() {
      return this.insns.hashCode();
   }
}
