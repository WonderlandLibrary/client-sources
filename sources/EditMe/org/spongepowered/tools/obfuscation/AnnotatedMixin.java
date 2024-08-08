package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixin {
   private final AnnotationHandle annotation;
   private final Messager messager;
   private final ITypeHandleProvider typeProvider;
   private final IObfuscationManager obf;
   private final IMappingConsumer mappings;
   private final TypeElement mixin;
   private final List methods;
   private final TypeHandle handle;
   private final List targets = new ArrayList();
   private final TypeHandle primaryTarget;
   private final String classRef;
   private final boolean remap;
   private final boolean virtual;
   private final AnnotatedMixinElementHandlerOverwrite overwrites;
   private final AnnotatedMixinElementHandlerShadow shadows;
   private final AnnotatedMixinElementHandlerInjector injectors;
   private final AnnotatedMixinElementHandlerAccessor accessors;
   private final AnnotatedMixinElementHandlerSoftImplements softImplements;
   private boolean validated = false;

   public AnnotatedMixin(IMixinAnnotationProcessor var1, TypeElement var2) {
      this.typeProvider = var1.getTypeProvider();
      this.obf = var1.getObfuscationManager();
      this.mappings = this.obf.createMappingConsumer();
      this.messager = var1;
      this.mixin = var2;
      this.handle = new TypeHandle(var2);
      this.methods = new ArrayList(this.handle.getEnclosedElements(ElementKind.METHOD));
      this.virtual = this.handle.getAnnotation(Pseudo.class).exists();
      this.annotation = this.handle.getAnnotation(Mixin.class);
      this.classRef = TypeUtils.getInternalName(var2);
      this.primaryTarget = this.initTargets();
      this.remap = this.annotation.getBoolean("remap", true) && this.targets.size() > 0;
      this.overwrites = new AnnotatedMixinElementHandlerOverwrite(var1, this);
      this.shadows = new AnnotatedMixinElementHandlerShadow(var1, this);
      this.injectors = new AnnotatedMixinElementHandlerInjector(var1, this);
      this.accessors = new AnnotatedMixinElementHandlerAccessor(var1, this);
      this.softImplements = new AnnotatedMixinElementHandlerSoftImplements(var1, this);
   }

   AnnotatedMixin runValidators(IMixinValidator.ValidationPass var1, Collection var2) {
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         IMixinValidator var4 = (IMixinValidator)var3.next();
         if (!var4.validate(var1, this.mixin, this.annotation, this.targets)) {
            break;
         }
      }

      if (var1 == IMixinValidator.ValidationPass.FINAL && !this.validated) {
         this.validated = true;
         this.runFinalValidation();
      }

      return this;
   }

   private TypeHandle initTargets() {
      TypeHandle var1 = null;

      Iterator var2;
      TypeHandle var4;
      try {
         var2 = this.annotation.getList().iterator();

         while(var2.hasNext()) {
            TypeMirror var3 = (TypeMirror)var2.next();
            var4 = new TypeHandle((DeclaredType)var3);
            if (!this.targets.contains(var4)) {
               this.addTarget(var4);
               if (var1 == null) {
                  var1 = var4;
               }
            }
         }
      } catch (Exception var6) {
         this.printMessage(Kind.WARNING, "Error processing public targets: " + var6.getClass().getName() + ": " + var6.getMessage(), this);
      }

      try {
         var2 = this.annotation.getList("targets").iterator();

         while(var2.hasNext()) {
            String var7 = (String)var2.next();
            var4 = this.typeProvider.getTypeHandle(var7);
            if (!this.targets.contains(var4)) {
               if (this.virtual) {
                  var4 = this.typeProvider.getSimulatedHandle(var7, this.mixin.asType());
               } else {
                  if (var4 == null) {
                     this.printMessage(Kind.ERROR, "Mixin target " + var7 + " could not be found", this);
                     return null;
                  }

                  if (var4.isPublic()) {
                     this.printMessage(Kind.WARNING, "Mixin target " + var7 + " is public and must be specified in value", this);
                     return null;
                  }
               }

               this.addSoftTarget(var4, var7);
               if (var1 == null) {
                  var1 = var4;
               }
            }
         }
      } catch (Exception var5) {
         this.printMessage(Kind.WARNING, "Error processing private targets: " + var5.getClass().getName() + ": " + var5.getMessage(), this);
      }

      if (var1 == null) {
         this.printMessage(Kind.ERROR, "Mixin has no targets", this);
      }

      return var1;
   }

   private void printMessage(Kind var1, CharSequence var2, AnnotatedMixin var3) {
      this.messager.printMessage(var1, var2, this.mixin, this.annotation.asMirror());
   }

   private void addSoftTarget(TypeHandle var1, String var2) {
      ObfuscationData var3 = this.obf.getDataProvider().getObfClass(var1);
      if (!var3.isEmpty()) {
         this.obf.getReferenceManager().addClassMapping(this.classRef, var2, var3);
      }

      this.addTarget(var1);
   }

   private void addTarget(TypeHandle var1) {
      this.targets.add(var1);
   }

   public String toString() {
      return this.mixin.getSimpleName().toString();
   }

   public AnnotationHandle getAnnotation() {
      return this.annotation;
   }

   public TypeElement getMixin() {
      return this.mixin;
   }

   public TypeHandle getHandle() {
      return this.handle;
   }

   public String getClassRef() {
      return this.classRef;
   }

   public boolean isInterface() {
      return this.mixin.getKind() == ElementKind.INTERFACE;
   }

   /** @deprecated */
   @Deprecated
   public TypeHandle getPrimaryTarget() {
      return this.primaryTarget;
   }

   public List getTargets() {
      return this.targets;
   }

   public boolean isMultiTarget() {
      return this.targets.size() > 1;
   }

   public boolean remap() {
      return this.remap;
   }

   public IMappingConsumer getMappings() {
      return this.mappings;
   }

   private void runFinalValidation() {
      Iterator var1 = this.methods.iterator();

      while(var1.hasNext()) {
         ExecutableElement var2 = (ExecutableElement)var1.next();
         this.overwrites.registerMerge(var2);
      }

   }

   public void registerOverwrite(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
      this.methods.remove(var1);
      this.overwrites.registerOverwrite(new AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite(var1, var2, var3));
   }

   public void registerShadow(VariableElement var1, AnnotationHandle var2, boolean var3) {
      this.shadows.registerShadow(this.shadows.new AnnotatedElementShadowField(var1, var2, var3));
   }

   public void registerShadow(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
      this.methods.remove(var1);
      this.shadows.registerShadow(this.shadows.new AnnotatedElementShadowMethod(var1, var2, var3));
   }

   public void registerInjector(ExecutableElement var1, AnnotationHandle var2, InjectorRemap var3) {
      this.methods.remove(var1);
      this.injectors.registerInjector(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector(var1, var2, var3));
      List var4 = var2.getAnnotationList("at");
      Iterator var5 = var4.iterator();

      while(var5.hasNext()) {
         AnnotationHandle var6 = (AnnotationHandle)var5.next();
         this.registerInjectionPoint(var1, var2, var6, var3, "@At(%s)");
      }

      List var11 = var2.getAnnotationList("slice");
      Iterator var12 = var11.iterator();

      while(var12.hasNext()) {
         AnnotationHandle var7 = (AnnotationHandle)var12.next();
         String var8 = (String)var7.getValue("id", "");
         AnnotationHandle var9 = var7.getAnnotation("from");
         if (var9 != null) {
            this.registerInjectionPoint(var1, var2, var9, var3, "@Slice[" + var8 + "](from=@At(%s))");
         }

         AnnotationHandle var10 = var7.getAnnotation("to");
         if (var10 != null) {
            this.registerInjectionPoint(var1, var2, var10, var3, "@Slice[" + var8 + "](to=@At(%s))");
         }
      }

   }

   public void registerInjectionPoint(ExecutableElement var1, AnnotationHandle var2, AnnotationHandle var3, InjectorRemap var4, String var5) {
      this.injectors.registerInjectionPoint(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint(var1, var2, var3, var4), var5);
   }

   public void registerAccessor(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
      this.methods.remove(var1);
      this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor(var1, var2, var3));
   }

   public void registerInvoker(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
      this.methods.remove(var1);
      this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker(var1, var2, var3));
   }

   public void registerSoftImplements(AnnotationHandle var1) {
      this.softImplements.process(var1);
   }
}
