package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.Target;

@InjectionPoint.AtCode("LOAD")
public class BeforeLoadLocal extends ModifyVariableInjector.ContextualInjectionPoint {
   private final Type returnType;
   private final LocalVariableDiscriminator discriminator;
   private final int opcode;
   private final int ordinal;
   private boolean opcodeAfter;

   protected BeforeLoadLocal(InjectionPointData var1) {
      this(var1, 21, false);
   }

   protected BeforeLoadLocal(InjectionPointData var1, int var2, boolean var3) {
      super(var1.getContext());
      this.returnType = var1.getMethodReturnType();
      this.discriminator = var1.getLocalVariableDiscriminator();
      this.opcode = var1.getOpcode(this.returnType.getOpcode(var2));
      this.ordinal = var1.getOrdinal();
      this.opcodeAfter = var3;
   }

   boolean find(Target var1, Collection var2) {
      BeforeLoadLocal.SearchState var3 = new BeforeLoadLocal.SearchState(this.ordinal, this.discriminator.printLVT());
      ListIterator var4 = var1.method.instructions.iterator();

      while(true) {
         while(var4.hasNext()) {
            AbstractInsnNode var5 = (AbstractInsnNode)var4.next();
            int var6;
            if (var3.isPendingCheck()) {
               var6 = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), var1, var5);
               var3.check(var2, var5, var6);
            } else if (var5 instanceof VarInsnNode && var5.getOpcode() == this.opcode && (this.ordinal == -1 || !var3.success())) {
               var3.register((VarInsnNode)var5);
               if (this.opcodeAfter) {
                  var3.setPendingCheck();
               } else {
                  var6 = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), var1, var5);
                  var3.check(var2, var5, var6);
               }
            }
         }

         return var3.success();
      }
   }

   public boolean find(String var1, InsnList var2, Collection var3) {
      return super.find(var1, var2, var3);
   }

   static class SearchState {
      private final boolean print;
      private final int targetOrdinal;
      private int ordinal = 0;
      private boolean pendingCheck = false;
      private boolean found = false;
      private VarInsnNode varNode;

      SearchState(int var1, boolean var2) {
         this.targetOrdinal = var1;
         this.print = var2;
      }

      boolean success() {
         return this.found;
      }

      boolean isPendingCheck() {
         return this.pendingCheck;
      }

      void setPendingCheck() {
         this.pendingCheck = true;
      }

      void register(VarInsnNode var1) {
         this.varNode = var1;
      }

      void check(Collection var1, AbstractInsnNode var2, int var3) {
         this.pendingCheck = false;
         if (var3 == this.varNode.var || var3 <= -2 && this.print) {
            if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
               var1.add(var2);
               this.found = true;
            }

            ++this.ordinal;
            this.varNode = null;
         }
      }
   }
}
