package org.spongepowered.asm.mixin.injection.points;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint.AtCode("NEW")
public class BeforeNew extends InjectionPoint {
   private final String target;
   private final String desc;
   private final int ordinal;

   public BeforeNew(InjectionPointData var1) {
      super(var1);
      this.ordinal = var1.getOrdinal();
      String var2 = Strings.emptyToNull(var1.get("class", var1.get("target", "")).replace('.', '/'));
      MemberInfo var3 = MemberInfo.parseAndValidate(var2, var1.getContext());
      this.target = var3.toCtorType();
      this.desc = var3.toCtorDesc();
   }

   public boolean hasDescriptor() {
      return this.desc != null;
   }

   public boolean find(String var1, InsnList var2, Collection var3) {
      boolean var4 = false;
      int var5 = 0;
      ArrayList var6 = new ArrayList();
      Object var7 = this.desc != null ? var6 : var3;
      ListIterator var8 = var2.iterator();

      while(true) {
         AbstractInsnNode var9;
         do {
            do {
               do {
                  if (!var8.hasNext()) {
                     if (this.desc != null) {
                        Iterator var11 = var6.iterator();

                        while(var11.hasNext()) {
                           TypeInsnNode var10 = (TypeInsnNode)var11.next();
                           if (this.findCtor(var2, var10)) {
                              var3.add(var10);
                              var4 = true;
                           }
                        }
                     }

                     return var4;
                  }

                  var9 = (AbstractInsnNode)var8.next();
               } while(!(var9 instanceof TypeInsnNode));
            } while(var9.getOpcode() != 187);
         } while(!this.matchesOwner((TypeInsnNode)var9));

         if (this.ordinal == -1 || this.ordinal == var5) {
            ((Collection)var7).add(var9);
            var4 = this.desc == null;
         }

         ++var5;
      }
   }

   protected boolean findCtor(InsnList var1, TypeInsnNode var2) {
      int var3 = var1.indexOf(var2);
      ListIterator var4 = var1.iterator(var3);

      while(var4.hasNext()) {
         AbstractInsnNode var5 = (AbstractInsnNode)var4.next();
         if (var5 instanceof MethodInsnNode && var5.getOpcode() == 183) {
            MethodInsnNode var6 = (MethodInsnNode)var5;
            if ("<init>".equals(var6.name) && var6.owner.equals(var2.desc) && var6.desc.equals(this.desc)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean matchesOwner(TypeInsnNode var1) {
      return this.target == null || this.target.equals(var1.desc);
   }
}
