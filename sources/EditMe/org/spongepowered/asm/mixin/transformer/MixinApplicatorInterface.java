package org.spongepowered.asm.mixin.transformer;

import java.util.Iterator;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;

class MixinApplicatorInterface extends MixinApplicatorStandard {
   MixinApplicatorInterface(TargetClassContext var1) {
      super(var1);
   }

   protected void applyInterfaces(MixinTargetContext var1) {
      Iterator var2 = var1.getInterfaces().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         if (!this.targetClass.name.equals(var3) && !this.targetClass.interfaces.contains(var3)) {
            this.targetClass.interfaces.add(var3);
            var1.getTargetClassInfo().addInterface(var3);
         }
      }

   }

   protected void applyFields(MixinTargetContext var1) {
      Iterator var2 = var1.getShadowFields().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         FieldNode var4 = (FieldNode)var3.getKey();
         this.logger.error("Ignoring redundant @Shadow field {}:{} in {}", new Object[]{var4.name, var4.desc, var1});
      }

      this.mergeNewFields(var1);
   }

   protected void applyInitialisers(MixinTargetContext var1) {
   }

   protected void prepareInjections(MixinTargetContext var1) {
      Iterator var2 = this.targetClass.methods.iterator();

      while(var2.hasNext()) {
         MethodNode var3 = (MethodNode)var2.next();

         try {
            InjectionInfo var4 = InjectionInfo.parse(var1, var3);
            if (var4 != null) {
               throw new InvalidInterfaceMixinException(var1, var4 + " is not supported on interface mixin method " + var3.name);
            }
         } catch (InvalidInjectionException var6) {
            String var5 = var6.getInjectionInfo() != null ? var6.getInjectionInfo().toString() : "Injection";
            throw new InvalidInterfaceMixinException(var1, var5 + " is not supported in interface mixin");
         }
      }

   }

   protected void applyInjections(MixinTargetContext var1) {
   }
}
