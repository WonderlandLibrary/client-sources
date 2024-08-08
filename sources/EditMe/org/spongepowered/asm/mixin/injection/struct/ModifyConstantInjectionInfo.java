package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class ModifyConstantInjectionInfo extends InjectionInfo {
   private static final String CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');

   public ModifyConstantInjectionInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      super(var1, var2, var3, "constant");
   }

   protected List readInjectionPoints(String var1) {
      Object var2 = super.readInjectionPoints(var1);
      if (((List)var2).isEmpty()) {
         AnnotationNode var3 = new AnnotationNode(CONSTANT_ANNOTATION_CLASS);
         var3.visit("log", Boolean.TRUE);
         var2 = ImmutableList.of(var3);
      }

      return (List)var2;
   }

   protected void parseInjectionPoints(List var1) {
      Type var2 = Type.getReturnType(this.method.desc);
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         AnnotationNode var4 = (AnnotationNode)var3.next();
         this.injectionPoints.add(new BeforeConstant(this.getContext(), var4, var2.getDescriptor()));
      }

   }

   protected Injector parseInjector(AnnotationNode var1) {
      return new ModifyConstantInjector(this);
   }

   protected String getDescription() {
      return "Constant modifier method";
   }

   public String getSliceId(String var1) {
      return Strings.nullToEmpty(var1);
   }
}
