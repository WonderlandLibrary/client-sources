package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixinElementHandlerInjector extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerInjector(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public void registerInjector(AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector var1) {
      if (this.mixin.isInterface()) {
         this.ap.printMessage(Kind.ERROR, "Injector in interface is unsupported", var1.getElement());
      }

      Iterator var2 = var1.getAnnotation().getList("method").iterator();

      while(true) {
         String var3;
         MemberInfo var4;
         do {
            do {
               if (!var2.hasNext()) {
                  return;
               }

               var3 = (String)var2.next();
               var4 = MemberInfo.parse(var3);
            } while(var4.name == null);

            try {
               var4.validate();
            } catch (InvalidMemberDescriptorException var7) {
               var1.printMessage(this.ap, Kind.ERROR, var7.getMessage());
            }

            if (var4.desc != null) {
               this.validateReferencedTarget((ExecutableElement)var1.getElement(), var1.getAnnotation(), var4, var1.toString());
            }
         } while(!var1.shouldRemap());

         Iterator var5 = this.mixin.getTargets().iterator();

         while(var5.hasNext()) {
            TypeHandle var6 = (TypeHandle)var5.next();
            if (!this.registerInjector(var1, var3, var4, var6)) {
               break;
            }
         }
      }
   }

   private boolean registerInjector(AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector var1, String var2, MemberInfo var3, TypeHandle var4) {
      String var5 = var4.findDescriptor(var3);
      if (var5 == null) {
         Kind var15 = this.mixin.isMultiTarget() ? Kind.ERROR : Kind.WARNING;
         if (var4.isSimulated()) {
            var1.printMessage(this.ap, Kind.NOTE, var1 + " target '" + var2 + "' in @Pseudo mixin will not be obfuscated");
         } else if (var4.isImaginary()) {
            var1.printMessage(this.ap, var15, var1 + " target requires method signature because enclosing type information for " + var4 + " is unavailable");
         } else if (!var3.isInitialiser()) {
            var1.printMessage(this.ap, var15, "Unable to determine signature for " + var1 + " target method");
         }

         return true;
      } else {
         String var6 = var1 + " target " + var3.name;
         MappingMethod var7 = var4.getMappingMethod(var3.name, var5);
         ObfuscationData var8 = this.obf.getDataProvider().getObfMethod(var7);
         if (var8.isEmpty()) {
            if (!var4.isSimulated()) {
               if (var3.isClassInitialiser()) {
                  return true;
               }

               Kind var16 = var3.isConstructor() ? Kind.WARNING : Kind.ERROR;
               var1.addMessage(var16, "No obfuscation mapping for " + var6, var1.getElement(), var1.getAnnotation());
               return false;
            }

            var8 = this.obf.getDataProvider().getRemappedMethod(var7);
         }

         IReferenceManager var9 = this.obf.getReferenceManager();

         try {
            if (var3.owner == null && this.mixin.isMultiTarget() || var4.isSimulated()) {
               var8 = AnnotatedMixinElementHandler.stripOwnerData(var8);
            }

            var9.addMethodMapping(this.classRef, var2, var8);
         } catch (ReferenceManager.ReferenceConflictException var14) {
            String var11 = this.mixin.isMultiTarget() ? "Multi-target" : "Target";
            if (var1.hasCoerceArgument() && var3.owner == null && var3.desc == null) {
               MemberInfo var12 = MemberInfo.parse(var14.getOld());
               MemberInfo var13 = MemberInfo.parse(var14.getNew());
               if (var12.name.equals(var13.name)) {
                  var8 = AnnotatedMixinElementHandler.stripDescriptors(var8);
                  var9.setAllowConflicts(true);
                  var9.addMethodMapping(this.classRef, var2, var8);
                  var9.setAllowConflicts(false);
                  var1.printMessage(this.ap, Kind.WARNING, "Coerced " + var11 + " reference has conflicting descriptors for " + var6 + ": Storing bare references " + var8.values() + " in refMap");
                  return true;
               }
            }

            var1.printMessage(this.ap, Kind.ERROR, var11 + " reference conflict for " + var6 + ": " + var2 + " -> " + var14.getNew() + " previously defined as " + var14.getOld());
         }

         return true;
      }
   }

   public void registerInjectionPoint(AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint var1, String var2) {
      if (this.mixin.isInterface()) {
         this.ap.printMessage(Kind.ERROR, "Injector in interface is unsupported", var1.getElement());
      }

      if (var1.shouldRemap()) {
         String var3 = InjectionPointData.parseType((String)var1.getAt().getValue("value"));
         String var4 = (String)var1.getAt().getValue("target");
         if ("NEW".equals(var3)) {
            this.remapNewTarget(String.format(var2, var3 + ".<target>"), var4, var1);
            this.remapNewTarget(String.format(var2, var3 + ".args[class]"), var1.getAtArg("class"), var1);
         } else {
            this.remapReference(String.format(var2, var3 + ".<target>"), var4, var1);
         }

      }
   }

   protected final void remapNewTarget(String var1, String var2, AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint var3) {
      if (var2 != null) {
         MemberInfo var4 = MemberInfo.parse(var2);
         String var5 = var4.toCtorType();
         if (var5 != null) {
            String var6 = var4.toCtorDesc();
            MappingMethod var7 = new MappingMethod(var5, ".", var6 != null ? var6 : "()V");
            ObfuscationData var8 = this.obf.getDataProvider().getRemappedMethod(var7);
            if (var8.isEmpty()) {
               this.ap.printMessage(Kind.WARNING, "Cannot find class mapping for " + var1 + " '" + var5 + "'", var3.getElement(), var3.getAnnotation().asMirror());
               return;
            }

            ObfuscationData var9 = new ObfuscationData();
            Iterator var10 = var8.iterator();

            while(var10.hasNext()) {
               ObfuscationType var11 = (ObfuscationType)var10.next();
               MappingMethod var12 = (MappingMethod)var8.get(var11);
               if (var6 == null) {
                  var9.put(var11, var12.getOwner());
               } else {
                  var9.put(var11, var12.getDesc().replace(")V", ")L" + var12.getOwner() + ";"));
               }
            }

            this.obf.getReferenceManager().addClassMapping(this.classRef, var2, var9);
         }

         var3.notifyRemapped();
      }
   }

   protected final void remapReference(String var1, String var2, AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint var3) {
      if (var2 != null) {
         AnnotationMirror var4 = (this.ap.getCompilerEnvironment() == IMixinAnnotationProcessor.CompilerEnvironment.JDT ? var3.getAt() : var3.getAnnotation()).asMirror();
         MemberInfo var5 = MemberInfo.parse(var2);
         if (!var5.isFullyQualified()) {
            String var9 = var5.owner == null ? (var5.desc == null ? "owner and signature" : "owner") : "signature";
            this.ap.printMessage(Kind.ERROR, var1 + " is not fully qualified, missing " + var9, var3.getElement(), var4);
         } else {
            try {
               var5.validate();
            } catch (InvalidMemberDescriptorException var7) {
               this.ap.printMessage(Kind.ERROR, var7.getMessage(), var3.getElement(), var4);
            }

            try {
               ObfuscationData var6;
               if (var5.isField()) {
                  var6 = this.obf.getDataProvider().getObfFieldRecursive(var5);
                  if (var6.isEmpty()) {
                     this.ap.printMessage(Kind.WARNING, "Cannot find field mapping for " + var1 + " '" + var2 + "'", var3.getElement(), var4);
                     return;
                  }

                  this.obf.getReferenceManager().addFieldMapping(this.classRef, var2, var5, var6);
               } else {
                  var6 = this.obf.getDataProvider().getObfMethodRecursive(var5);
                  if (var6.isEmpty() && (var5.owner == null || !var5.owner.startsWith("java/lang/"))) {
                     this.ap.printMessage(Kind.WARNING, "Cannot find method mapping for " + var1 + " '" + var2 + "'", var3.getElement(), var4);
                     return;
                  }

                  this.obf.getReferenceManager().addMethodMapping(this.classRef, var2, var5, var6);
               }
            } catch (ReferenceManager.ReferenceConflictException var8) {
               this.ap.printMessage(Kind.ERROR, "Unexpected reference conflict for " + var1 + ": " + var2 + " -> " + var8.getNew() + " previously defined as " + var8.getOld(), var3.getElement(), var4);
               return;
            }

            var3.notifyRemapped();
         }
      }
   }

   static class AnnotatedElementInjectionPoint extends AnnotatedMixinElementHandler.AnnotatedElement {
      private final AnnotationHandle at;
      private Map args;
      private final InjectorRemap state;

      public AnnotatedElementInjectionPoint(ExecutableElement var1, AnnotationHandle var2, AnnotationHandle var3, InjectorRemap var4) {
         super(var1, var2);
         this.at = var3;
         this.state = var4;
      }

      public boolean shouldRemap() {
         return this.at.getBoolean("remap", this.state.shouldRemap());
      }

      public AnnotationHandle getAt() {
         return this.at;
      }

      public String getAtArg(String var1) {
         if (this.args == null) {
            this.args = new HashMap();
            Iterator var2 = this.at.getList("args").iterator();

            while(var2.hasNext()) {
               String var3 = (String)var2.next();
               if (var3 != null) {
                  int var4 = var3.indexOf(61);
                  if (var4 > -1) {
                     this.args.put(var3.substring(0, var4), var3.substring(var4 + 1));
                  } else {
                     this.args.put(var3, "");
                  }
               }
            }
         }

         return (String)this.args.get(var1);
      }

      public void notifyRemapped() {
         this.state.notifyRemapped();
      }
   }

   static class AnnotatedElementInjector extends AnnotatedMixinElementHandler.AnnotatedElement {
      private final InjectorRemap state;

      public AnnotatedElementInjector(ExecutableElement var1, AnnotationHandle var2, InjectorRemap var3) {
         super(var1, var2);
         this.state = var3;
      }

      public boolean shouldRemap() {
         return this.state.shouldRemap();
      }

      public boolean hasCoerceArgument() {
         if (!this.annotation.toString().equals("@Inject")) {
            return false;
         } else {
            Iterator var1 = ((ExecutableElement)this.element).getParameters().iterator();
            if (var1.hasNext()) {
               VariableElement var2 = (VariableElement)var1.next();
               return AnnotationHandle.of(var2, Coerce.class).exists();
            } else {
               return false;
            }
         }
      }

      public void addMessage(Kind var1, CharSequence var2, Element var3, AnnotationHandle var4) {
         this.state.addMessage(var1, var2, var3, var4);
      }

      public String toString() {
         return this.getAnnotation().toString();
      }
   }
}
