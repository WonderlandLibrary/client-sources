package org.spongepowered.asm.util.asm;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public class MixinVerifier extends SimpleVerifier {
   private Type currentClass;
   private Type currentSuperClass;
   private List currentClassInterfaces;
   private boolean isInterface;

   public MixinVerifier(Type var1, Type var2, List var3, boolean var4) {
      super(var1, var2, var3, var4);
      this.currentClass = var1;
      this.currentSuperClass = var2;
      this.currentClassInterfaces = var3;
      this.isInterface = var4;
   }

   protected boolean isInterface(Type var1) {
      return this.currentClass != null && var1.equals(this.currentClass) ? this.isInterface : ClassInfo.forType(var1).isInterface();
   }

   protected Type getSuperClass(Type var1) {
      if (this.currentClass != null && var1.equals(this.currentClass)) {
         return this.currentSuperClass;
      } else {
         ClassInfo var2 = ClassInfo.forType(var1).getSuperClass();
         return var2 == null ? null : Type.getType("L" + var2.getName() + ";");
      }
   }

   protected boolean isAssignableFrom(Type var1, Type var2) {
      if (var1.equals(var2)) {
         return true;
      } else if (this.currentClass != null && var1.equals(this.currentClass)) {
         if (this.getSuperClass(var2) == null) {
            return false;
         } else if (!this.isInterface) {
            return this.isAssignableFrom(var1, this.getSuperClass(var2));
         } else {
            return var2.getSort() == 10 || var2.getSort() == 9;
         }
      } else if (this.currentClass != null && var2.equals(this.currentClass)) {
         if (this.isAssignableFrom(var1, this.currentSuperClass)) {
            return true;
         } else {
            if (this.currentClassInterfaces != null) {
               for(int var5 = 0; var5 < this.currentClassInterfaces.size(); ++var5) {
                  Type var4 = (Type)this.currentClassInterfaces.get(var5);
                  if (this.isAssignableFrom(var1, var4)) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         ClassInfo var3 = ClassInfo.forType(var1);
         if (var3 == null) {
            return false;
         } else {
            if (var3.isInterface()) {
               var3 = ClassInfo.forName("java/lang/Object");
            }

            return ClassInfo.forType(var2).hasSuperClass(var3);
         }
      }
   }
}
