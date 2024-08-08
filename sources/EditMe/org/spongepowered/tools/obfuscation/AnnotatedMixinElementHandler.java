package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.mirror.Visibility;

abstract class AnnotatedMixinElementHandler {
   protected final AnnotatedMixin mixin;
   protected final String classRef;
   protected final IMixinAnnotationProcessor ap;
   protected final IObfuscationManager obf;
   private IMappingConsumer mappings;

   AnnotatedMixinElementHandler(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      this.ap = var1;
      this.mixin = var2;
      this.classRef = var2.getClassRef();
      this.obf = var1.getObfuscationManager();
   }

   private IMappingConsumer getMappings() {
      if (this.mappings == null) {
         IMappingConsumer var1 = this.mixin.getMappings();
         if (var1 instanceof Mappings) {
            this.mappings = ((Mappings)var1).asUnique();
         } else {
            this.mappings = var1;
         }
      }

      return this.mappings;
   }

   protected final void addFieldMappings(String var1, String var2, ObfuscationData var3) {
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         ObfuscationType var5 = (ObfuscationType)var4.next();
         MappingField var6 = (MappingField)var3.get(var5);
         this.addFieldMapping(var5, var1, var6.getSimpleName(), var2, var6.getDesc());
      }

   }

   protected final void addFieldMapping(ObfuscationType var1, AnnotatedMixinElementHandler.ShadowElementName var2, String var3, String var4) {
      this.addFieldMapping(var1, var2.name(), var2.obfuscated(), var3, var4);
   }

   protected final void addFieldMapping(ObfuscationType var1, String var2, String var3, String var4, String var5) {
      MappingField var6 = new MappingField(this.classRef, var2, var4);
      MappingField var7 = new MappingField(this.classRef, var3, var5);
      this.getMappings().addFieldMapping(var1, var6, var7);
   }

   protected final void addMethodMappings(String var1, String var2, ObfuscationData var3) {
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         ObfuscationType var5 = (ObfuscationType)var4.next();
         MappingMethod var6 = (MappingMethod)var3.get(var5);
         this.addMethodMapping(var5, var1, var6.getSimpleName(), var2, var6.getDesc());
      }

   }

   protected final void addMethodMapping(ObfuscationType var1, AnnotatedMixinElementHandler.ShadowElementName var2, String var3, String var4) {
      this.addMethodMapping(var1, var2.name(), var2.obfuscated(), var3, var4);
   }

   protected final void addMethodMapping(ObfuscationType var1, String var2, String var3, String var4, String var5) {
      MappingMethod var6 = new MappingMethod(this.classRef, var2, var4);
      MappingMethod var7 = new MappingMethod(this.classRef, var3, var5);
      this.getMappings().addMethodMapping(var1, var6, var7);
   }

   protected final void checkConstraints(ExecutableElement var1, AnnotationHandle var2) {
      try {
         ConstraintParser.Constraint var3 = ConstraintParser.parse((String)var2.getValue("constraints"));

         try {
            var3.check(this.ap.getTokenProvider());
         } catch (ConstraintViolationException var5) {
            this.ap.printMessage(Kind.ERROR, var5.getMessage(), var1, var2.asMirror());
         }
      } catch (InvalidConstraintException var6) {
         this.ap.printMessage(Kind.WARNING, var6.getMessage(), var1, var2.asMirror());
      }

   }

   protected final void validateTarget(Element var1, AnnotationHandle var2, AnnotatedMixinElementHandler.AliasedElementName var3, String var4) {
      if (var1 instanceof ExecutableElement) {
         this.validateTargetMethod((ExecutableElement)var1, var2, var3, var4, false, false);
      } else if (var1 instanceof VariableElement) {
         this.validateTargetField((VariableElement)var1, var2, var3, var4);
      }

   }

   protected final void validateTargetMethod(ExecutableElement var1, AnnotationHandle var2, AnnotatedMixinElementHandler.AliasedElementName var3, String var4, boolean var5, boolean var6) {
      String var7 = TypeUtils.getJavaSignature((Element)var1);
      Iterator var8 = this.mixin.getTargets().iterator();

      while(true) {
         TypeHandle var9;
         do {
            if (!var8.hasNext()) {
               return;
            }

            var9 = (TypeHandle)var8.next();
         } while(var9.isImaginary());

         MethodHandle var10 = var9.findMethod(var1);
         if (var10 == null && var3.hasPrefix()) {
            var10 = var9.findMethod(var3.baseName(), var7);
         }

         if (var10 == null && var3.hasAliases()) {
            Iterator var11 = var3.getAliases().iterator();

            while(var11.hasNext()) {
               String var12 = (String)var11.next();
               if ((var10 = var9.findMethod(var12, var7)) != null) {
                  break;
               }
            }
         }

         if (var10 != null) {
            if (var5) {
               this.validateMethodVisibility(var1, var2, var4, var9, var10);
            }
         } else if (!var6) {
            this.printMessage(Kind.WARNING, "Cannot find target for " + var4 + " method in " + var9, var1, var2);
         }
      }
   }

   private void validateMethodVisibility(ExecutableElement var1, AnnotationHandle var2, String var3, TypeHandle var4, MethodHandle var5) {
      Visibility var6 = var5.getVisibility();
      if (var6 != null) {
         Visibility var7 = TypeUtils.getVisibility(var1);
         String var8 = "visibility of " + var6 + " method in " + var4;
         if (var6.ordinal() > var7.ordinal()) {
            this.printMessage(Kind.WARNING, var7 + " " + var3 + " method cannot reduce " + var8, var1, var2);
         } else if (var6 == Visibility.PRIVATE && var7.ordinal() > var6.ordinal()) {
            this.printMessage(Kind.WARNING, var7 + " " + var3 + " method will upgrade " + var8, var1, var2);
         }

      }
   }

   protected final void validateTargetField(VariableElement var1, AnnotationHandle var2, AnnotatedMixinElementHandler.AliasedElementName var3, String var4) {
      String var5 = var1.asType().toString();
      Iterator var6 = this.mixin.getTargets().iterator();

      while(true) {
         TypeHandle var7;
         FieldHandle var8;
         do {
            do {
               if (!var6.hasNext()) {
                  return;
               }

               var7 = (TypeHandle)var6.next();
            } while(var7.isImaginary());

            var8 = var7.findField(var1);
         } while(var8 != null);

         List var9 = var3.getAliases();
         Iterator var10 = var9.iterator();

         while(var10.hasNext()) {
            String var11 = (String)var10.next();
            if ((var8 = var7.findField(var11, var5)) != null) {
               break;
            }
         }

         if (var8 == null) {
            this.ap.printMessage(Kind.WARNING, "Cannot find target for " + var4 + " field in " + var7, var1, var2.asMirror());
         }
      }
   }

   protected final void validateReferencedTarget(ExecutableElement var1, AnnotationHandle var2, MemberInfo var3, String var4) {
      String var5 = var3.toDescriptor();
      Iterator var6 = this.mixin.getTargets().iterator();

      while(var6.hasNext()) {
         TypeHandle var7 = (TypeHandle)var6.next();
         if (!var7.isImaginary()) {
            MethodHandle var8 = var7.findMethod(var3.name, var5);
            if (var8 == null) {
               this.ap.printMessage(Kind.WARNING, "Cannot find target method for " + var4 + " in " + var7, var1, var2.asMirror());
            }
         }
      }

   }

   private void printMessage(Kind var1, String var2, Element var3, AnnotationHandle var4) {
      if (var4 == null) {
         this.ap.printMessage(var1, var2, var3);
      } else {
         this.ap.printMessage(var1, var2, var3, var4.asMirror());
      }

   }

   protected static ObfuscationData stripOwnerData(ObfuscationData var0) {
      ObfuscationData var1 = new ObfuscationData();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         ObfuscationType var3 = (ObfuscationType)var2.next();
         IMapping var4 = (IMapping)var0.get(var3);
         var1.put(var3, var4.move((String)null));
      }

      return var1;
   }

   protected static ObfuscationData stripDescriptors(ObfuscationData var0) {
      ObfuscationData var1 = new ObfuscationData();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         ObfuscationType var3 = (ObfuscationType)var2.next();
         IMapping var4 = (IMapping)var0.get(var3);
         var1.put(var3, var4.transform((String)null));
      }

      return var1;
   }

   static class ShadowElementName extends AnnotatedMixinElementHandler.AliasedElementName {
      private final boolean hasPrefix;
      private final String prefix;
      private final String baseName;
      private String obfuscated;

      ShadowElementName(Element var1, AnnotationHandle var2) {
         super(var1, var2);
         this.prefix = (String)var2.getValue("prefix", "shadow$");
         boolean var3 = false;
         String var4 = this.originalName;
         if (var4.startsWith(this.prefix)) {
            var3 = true;
            var4 = var4.substring(this.prefix.length());
         }

         this.hasPrefix = var3;
         this.obfuscated = this.baseName = var4;
      }

      public String toString() {
         return this.baseName;
      }

      public String baseName() {
         return this.baseName;
      }

      public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping var1) {
         this.obfuscated = var1.getName();
         return this;
      }

      public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String var1) {
         this.obfuscated = var1;
         return this;
      }

      public boolean hasPrefix() {
         return this.hasPrefix;
      }

      public String prefix() {
         return this.hasPrefix ? this.prefix : "";
      }

      public String name() {
         return this.prefix(this.baseName);
      }

      public String obfuscated() {
         return this.prefix(this.obfuscated);
      }

      public String prefix(String var1) {
         return this.hasPrefix ? this.prefix + var1 : var1;
      }
   }

   static class AliasedElementName {
      protected final String originalName;
      private final List aliases;
      private boolean caseSensitive;

      public AliasedElementName(Element var1, AnnotationHandle var2) {
         this.originalName = var1.getSimpleName().toString();
         this.aliases = var2.getList("aliases");
      }

      public AnnotatedMixinElementHandler.AliasedElementName setCaseSensitive(boolean var1) {
         this.caseSensitive = var1;
         return this;
      }

      public boolean isCaseSensitive() {
         return this.caseSensitive;
      }

      public boolean hasAliases() {
         return this.aliases.size() > 0;
      }

      public List getAliases() {
         return this.aliases;
      }

      public String elementName() {
         return this.originalName;
      }

      public String baseName() {
         return this.originalName;
      }

      public boolean hasPrefix() {
         return false;
      }
   }

   abstract static class AnnotatedElement {
      protected final Element element;
      protected final AnnotationHandle annotation;
      private final String desc;

      public AnnotatedElement(Element var1, AnnotationHandle var2) {
         this.element = var1;
         this.annotation = var2;
         this.desc = TypeUtils.getDescriptor(var1);
      }

      public Element getElement() {
         return this.element;
      }

      public AnnotationHandle getAnnotation() {
         return this.annotation;
      }

      public String getSimpleName() {
         return this.getElement().getSimpleName().toString();
      }

      public String getDesc() {
         return this.desc;
      }

      public final void printMessage(Messager var1, Kind var2, CharSequence var3) {
         var1.printMessage(var2, var3, this.element, this.annotation.asMirror());
      }
   }
}
