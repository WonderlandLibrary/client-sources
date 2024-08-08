package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("RETURN")
public class BeforeReturn extends InjectionPoint {
   private final int ordinal;

   public BeforeReturn(InjectionPointData var1) {
      super(var1);
      this.ordinal = var1.getOrdinal();
   }

   public boolean find(String var1, InsnList var2, Collection var3) {
      boolean var4 = false;
      int var5 = Type.getReturnType(var1).getOpcode(172);
      int var6 = 0;
      ListIterator var7 = var2.iterator();

      while(true) {
         AbstractInsnNode var8;
         do {
            do {
               if (!var7.hasNext()) {
                  return var4;
               }

               var8 = (AbstractInsnNode)var7.next();
            } while(!(var8 instanceof InsnNode));
         } while(var8.getOpcode() != var5);

         if (this.ordinal == -1 || this.ordinal == var6) {
            var3.add(var8);
            var4 = true;
         }

         ++var6;
      }
   }
}
