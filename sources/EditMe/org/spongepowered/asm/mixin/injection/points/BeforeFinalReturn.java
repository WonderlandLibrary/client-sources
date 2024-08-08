package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

@InjectionPoint.AtCode("TAIL")
public class BeforeFinalReturn extends InjectionPoint {
   private final IMixinContext context;

   public BeforeFinalReturn(InjectionPointData var1) {
      super(var1);
      this.context = var1.getContext();
   }

   public boolean find(String var1, InsnList var2, Collection var3) {
      AbstractInsnNode var4 = null;
      int var5 = Type.getReturnType(var1).getOpcode(172);
      ListIterator var6 = var2.iterator();

      while(var6.hasNext()) {
         AbstractInsnNode var7 = (AbstractInsnNode)var6.next();
         if (var7 instanceof InsnNode && var7.getOpcode() == var5) {
            var4 = var7;
         }
      }

      if (var4 == null) {
         throw new InvalidInjectionException(this.context, "TAIL could not locate a valid RETURN in the target method!");
      } else {
         var3.add(var4);
         return true;
      }
   }
}
