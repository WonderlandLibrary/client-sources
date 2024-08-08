package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class InvokerInfo extends AccessorInfo {
   public InvokerInfo(MixinTargetContext var1, MethodNode var2) {
      super(var1, var2, Invoker.class);
   }

   protected AccessorInfo.AccessorType initType() {
      return AccessorInfo.AccessorType.METHOD_PROXY;
   }

   protected Type initTargetFieldType() {
      return null;
   }

   protected MemberInfo initTarget() {
      return new MemberInfo(this.getTargetName(), (String)null, this.method.desc);
   }

   public void locate() {
      this.targetMethod = this.findTargetMethod();
   }

   private MethodNode findTargetMethod() {
      return (MethodNode)this.findTarget(this.classNode.methods);
   }
}
