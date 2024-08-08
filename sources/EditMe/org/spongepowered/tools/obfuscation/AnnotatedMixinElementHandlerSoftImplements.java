package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class AnnotatedMixinElementHandlerSoftImplements extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerSoftImplements(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public void process(AnnotationHandle var1) {
      if (this.mixin.remap()) {
         List var2 = var1.getAnnotationList("value");
         if (var2.size() < 1) {
            this.ap.printMessage(Kind.WARNING, "Empty @Implements annotation", this.mixin.getMixin(), var1.asMirror());
         } else {
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
               AnnotationHandle var4 = (AnnotationHandle)var3.next();
               Interface.Remap var5 = (Interface.Remap)var4.getValue("remap", Interface.Remap.ALL);
               if (var5 != Interface.Remap.NONE) {
                  try {
                     TypeHandle var6 = new TypeHandle((DeclaredType)var4.getValue("iface"));
                     String var7 = (String)var4.getValue("prefix");
                     this.processSoftImplements(var5, var6, var7);
                  } catch (Exception var8) {
                     this.ap.printMessage(Kind.ERROR, "Unexpected error: " + var8.getClass().getName() + ": " + var8.getMessage(), this.mixin.getMixin(), var4.asMirror());
                  }
               }
            }

         }
      }
   }

   private void processSoftImplements(Interface.Remap var1, TypeHandle var2, String var3) {
      Iterator var4 = var2.getEnclosedElements(ElementKind.METHOD).iterator();

      while(var4.hasNext()) {
         ExecutableElement var5 = (ExecutableElement)var4.next();
         this.processMethod(var1, var2, var3, var5);
      }

      var4 = var2.getInterfaces().iterator();

      while(var4.hasNext()) {
         TypeHandle var6 = (TypeHandle)var4.next();
         this.processSoftImplements(var1, var6, var3);
      }

   }

   private void processMethod(Interface.Remap var1, TypeHandle var2, String var3, ExecutableElement var4) {
      String var5 = var4.getSimpleName().toString();
      String var6 = TypeUtils.getJavaSignature((Element)var4);
      String var7 = TypeUtils.getDescriptor(var4);
      MethodHandle var8;
      if (var1 != Interface.Remap.ONLY_PREFIXED) {
         var8 = this.mixin.getHandle().findMethod(var5, var6);
         if (var8 != null) {
            this.addInterfaceMethodMapping(var1, var2, (String)null, var8, var5, var7);
         }
      }

      if (var3 != null) {
         var8 = this.mixin.getHandle().findMethod(var3 + var5, var6);
         if (var8 != null) {
            this.addInterfaceMethodMapping(var1, var2, var3, var8, var5, var7);
         }
      }

   }

   private void addInterfaceMethodMapping(Interface.Remap var1, TypeHandle var2, String var3, MethodHandle var4, String var5, String var6) {
      MappingMethod var7 = new MappingMethod(var2.getName(), var5, var6);
      ObfuscationData var8 = this.obf.getDataProvider().getObfMethod(var7);
      if (var8.isEmpty()) {
         if (var1.forceRemap()) {
            this.ap.printMessage(Kind.ERROR, "No obfuscation mapping for soft-implementing method", var4.getElement());
         }

      } else {
         this.addMethodMappings(var4.getName(), var6, this.applyPrefix(var8, var3));
      }
   }

   private ObfuscationData applyPrefix(ObfuscationData var1, String var2) {
      if (var2 == null) {
         return var1;
      } else {
         ObfuscationData var3 = new ObfuscationData();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            ObfuscationType var5 = (ObfuscationType)var4.next();
            MappingMethod var6 = (MappingMethod)var1.get(var5);
            var3.put(var5, var6.addPrefix(var2));
         }

         return var3;
      }
   }
}
