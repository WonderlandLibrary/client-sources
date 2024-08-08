package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public void registerShadow(AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow var1) {
      this.validateTarget(var1.getElement(), var1.getAnnotation(), var1.getName(), "@Shadow");
      if (var1.shouldRemap()) {
         Iterator var2 = this.mixin.getTargets().iterator();

         while(var2.hasNext()) {
            TypeHandle var3 = (TypeHandle)var2.next();
            this.registerShadowForTarget(var1, var3);
         }

      }
   }

   private void registerShadowForTarget(AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow var1, TypeHandle var2) {
      ObfuscationData var3 = var1.getObfuscationData(this.obf.getDataProvider(), var2);
      if (var3.isEmpty()) {
         String var8 = this.mixin.isMultiTarget() ? " in target " + var2 : "";
         if (var2.isSimulated()) {
            var1.printMessage(this.ap, Kind.WARNING, "Unable to locate obfuscation mapping" + var8 + " for @Shadow " + var1);
         } else {
            var1.printMessage(this.ap, Kind.WARNING, "Unable to locate obfuscation mapping" + var8 + " for @Shadow " + var1);
         }

      } else {
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            ObfuscationType var5 = (ObfuscationType)var4.next();

            try {
               var1.addMapping(var5, (IMapping)var3.get(var5));
            } catch (Mappings.MappingConflictException var7) {
               var1.printMessage(this.ap, Kind.ERROR, "Mapping conflict for @Shadow " + var1 + ": " + var7.getNew().getSimpleName() + " for target " + var2 + " conflicts with existing mapping " + var7.getOld().getSimpleName());
            }
         }

      }
   }

   class AnnotatedElementShadowMethod extends AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow {
      final AnnotatedMixinElementHandlerShadow this$0;

      public AnnotatedElementShadowMethod(AnnotatedMixinElementHandlerShadow var1, ExecutableElement var2, AnnotationHandle var3, boolean var4) {
         super(var2, var3, var4, IMapping.Type.METHOD);
         this.this$0 = var1;
      }

      public MappingMethod getMapping(TypeHandle var1, String var2, String var3) {
         return var1.getMappingMethod(var2, var3);
      }

      public void addMapping(ObfuscationType var1, IMapping var2) {
         this.this$0.addMethodMapping(var1, this.setObfuscatedName(var2), this.getDesc(), var2.getDesc());
      }

      public IMapping getMapping(TypeHandle var1, String var2, String var3) {
         return this.getMapping(var1, var2, var3);
      }
   }

   class AnnotatedElementShadowField extends AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow {
      final AnnotatedMixinElementHandlerShadow this$0;

      public AnnotatedElementShadowField(AnnotatedMixinElementHandlerShadow var1, VariableElement var2, AnnotationHandle var3, boolean var4) {
         super(var2, var3, var4, IMapping.Type.FIELD);
         this.this$0 = var1;
      }

      public MappingField getMapping(TypeHandle var1, String var2, String var3) {
         return new MappingField(var1.getName(), var2, var3);
      }

      public void addMapping(ObfuscationType var1, IMapping var2) {
         this.this$0.addFieldMapping(var1, this.setObfuscatedName(var2), this.getDesc(), var2.getDesc());
      }

      public IMapping getMapping(TypeHandle var1, String var2, String var3) {
         return this.getMapping(var1, var2, var3);
      }
   }

   abstract static class AnnotatedElementShadow extends AnnotatedMixinElementHandler.AnnotatedElement {
      private final boolean shouldRemap;
      private final AnnotatedMixinElementHandler.ShadowElementName name;
      private final IMapping.Type type;

      protected AnnotatedElementShadow(Element var1, AnnotationHandle var2, boolean var3, IMapping.Type var4) {
         super(var1, var2);
         this.shouldRemap = var3;
         this.name = new AnnotatedMixinElementHandler.ShadowElementName(var1, var2);
         this.type = var4;
      }

      public boolean shouldRemap() {
         return this.shouldRemap;
      }

      public AnnotatedMixinElementHandler.ShadowElementName getName() {
         return this.name;
      }

      public IMapping.Type getElementType() {
         return this.type;
      }

      public String toString() {
         return this.getElementType().name().toLowerCase();
      }

      public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping var1) {
         return this.setObfuscatedName(var1.getSimpleName());
      }

      public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String var1) {
         return this.getName().setObfuscatedName(var1);
      }

      public ObfuscationData getObfuscationData(IObfuscationDataProvider var1, TypeHandle var2) {
         return var1.getObfEntry(this.getMapping(var2, this.getName().toString(), this.getDesc()));
      }

      public abstract IMapping getMapping(TypeHandle var1, String var2, String var3);

      public abstract void addMapping(ObfuscationType var1, IMapping var2);
   }
}
