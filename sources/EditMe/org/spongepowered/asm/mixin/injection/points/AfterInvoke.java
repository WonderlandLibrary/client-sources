package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("INVOKE_ASSIGN")
public class AfterInvoke extends BeforeInvoke {
   public AfterInvoke(InjectionPointData var1) {
      super(var1);
   }

   protected boolean addInsn(InsnList var1, Collection var2, AbstractInsnNode var3) {
      MethodInsnNode var4 = (MethodInsnNode)var3;
      if (Type.getReturnType(var4.desc) == Type.VOID_TYPE) {
         return false;
      } else {
         var3 = InjectionPoint.nextNode(var1, var3);
         if (var3 instanceof VarInsnNode && var3.getOpcode() >= 54) {
            var3 = InjectionPoint.nextNode(var1, var3);
         }

         var2.add(var3);
         return true;
      }
   }
}
