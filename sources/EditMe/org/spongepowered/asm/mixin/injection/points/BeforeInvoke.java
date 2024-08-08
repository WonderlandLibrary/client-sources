package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint.AtCode("INVOKE")
public class BeforeInvoke extends InjectionPoint {
   protected final MemberInfo target;
   protected final MemberInfo permissiveTarget;
   protected final int ordinal;
   protected final String className;
   private boolean log = false;
   private final Logger logger = LogManager.getLogger("mixin");

   public BeforeInvoke(InjectionPointData var1) {
      super(var1);
      this.target = var1.getTarget();
      this.ordinal = var1.getOrdinal();
      this.log = var1.get("log", false);
      this.className = this.getClassName();
      this.permissiveTarget = var1.getContext().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? this.target.transform((String)null) : null;
   }

   private String getClassName() {
      InjectionPoint.AtCode var1 = (InjectionPoint.AtCode)this.getClass().getAnnotation(InjectionPoint.AtCode.class);
      return String.format("@At(%s)", var1 != null ? var1.value() : this.getClass().getSimpleName().toUpperCase());
   }

   public BeforeInvoke setLogging(boolean var1) {
      this.log = var1;
      return this;
   }

   public boolean find(String var1, InsnList var2, Collection var3) {
      this.log("{} is searching for an injection point in method with descriptor {}", this.className, var1);
      return !this.find(var1, var2, var3, this.target) ? this.find(var1, var2, var3, this.permissiveTarget) : true;
   }

   protected boolean find(String var1, InsnList var2, Collection var3, MemberInfo var4) {
      if (var4 == null) {
         return false;
      } else {
         int var5 = 0;
         boolean var6 = false;

         AbstractInsnNode var8;
         for(ListIterator var7 = var2.iterator(); var7.hasNext(); this.inspectInsn(var1, var2, var8)) {
            var8 = (AbstractInsnNode)var7.next();
            if (this.matchesInsn(var8)) {
               MemberInfo var9 = new MemberInfo(var8);
               this.log("{} is considering insn {}", this.className, var9);
               if (var4.matches(var9.owner, var9.name, var9.desc)) {
                  this.log("{} > found a matching insn, checking preconditions...", this.className);
                  if (this.matchesInsn(var9, var5)) {
                     this.log("{} > > > found a matching insn at ordinal {}", this.className, var5);
                     var6 |= this.addInsn(var2, var3, var8);
                     if (this.ordinal == var5) {
                        break;
                     }
                  }

                  ++var5;
               }
            }
         }

         return var6;
      }
   }

   protected boolean addInsn(InsnList var1, Collection var2, AbstractInsnNode var3) {
      var2.add(var3);
      return true;
   }

   protected boolean matchesInsn(AbstractInsnNode var1) {
      return var1 instanceof MethodInsnNode;
   }

   protected void inspectInsn(String var1, InsnList var2, AbstractInsnNode var3) {
   }

   protected boolean matchesInsn(MemberInfo var1, int var2) {
      this.log("{} > > comparing target ordinal {} with current ordinal {}", this.className, this.ordinal, var2);
      return this.ordinal == -1 || this.ordinal == var2;
   }

   protected void log(String var1, Object... var2) {
      if (this.log) {
         this.logger.info(var1, var2);
      }

   }
}
