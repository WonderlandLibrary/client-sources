package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint.AtCode("INVOKE_STRING")
public class BeforeStringInvoke extends BeforeInvoke {
   private static final String STRING_VOID_SIG = "(Ljava/lang/String;)V";
   private final String ldcValue;
   private boolean foundLdc;

   public BeforeStringInvoke(InjectionPointData var1) {
      super(var1);
      this.ldcValue = var1.get("ldc", (String)null);
      if (this.ldcValue == null) {
         throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires named argument \"ldc\" to specify the desired target");
      } else if (!"(Ljava/lang/String;)V".equals(this.target.desc)) {
         throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires target method with with signature " + "(Ljava/lang/String;)V");
      }
   }

   public boolean find(String var1, InsnList var2, Collection var3) {
      this.foundLdc = false;
      return super.find(var1, var2, var3);
   }

   protected void inspectInsn(String var1, InsnList var2, AbstractInsnNode var3) {
      if (var3 instanceof LdcInsnNode) {
         LdcInsnNode var4 = (LdcInsnNode)var3;
         if (var4.cst instanceof String && this.ldcValue.equals(var4.cst)) {
            this.log("{} > found a matching LDC with value {}", new Object[]{this.className, var4.cst});
            this.foundLdc = true;
            return;
         }
      }

      this.foundLdc = false;
   }

   protected boolean matchesInsn(MemberInfo var1, int var2) {
      this.log("{} > > found LDC \"{}\" = {}", new Object[]{this.className, this.ldcValue, this.foundLdc});
      return this.foundLdc && super.matchesInsn(var1, var2);
   }
}
