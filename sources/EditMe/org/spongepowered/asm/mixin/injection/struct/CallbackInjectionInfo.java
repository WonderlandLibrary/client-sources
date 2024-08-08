package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInjector;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

public class CallbackInjectionInfo extends InjectionInfo {
   protected CallbackInjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      super(var1, var2, var3);
   }

   protected Injector parseInjector(AnnotationNode var1) {
      boolean var2 = (Boolean)Annotations.getValue(var1, "cancellable", (Object)Boolean.FALSE);
      LocalCapture var3 = (LocalCapture)Annotations.getValue(var1, "locals", LocalCapture.class, LocalCapture.NO_CAPTURE);
      String var4 = (String)Annotations.getValue(var1, "id", (Object)"");
      return new CallbackInjector(this, var2, var3, var4);
   }

   public String getSliceId(String var1) {
      return Strings.nullToEmpty(var1);
   }
}
