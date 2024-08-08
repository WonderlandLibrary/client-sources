package org.spongepowered.tools.obfuscation;

import com.google.common.base.Strings;
import java.util.Iterator;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class AnnotatedMixinElementHandlerAccessor extends AnnotatedMixinElementHandler implements IMixinContext {
   public AnnotatedMixinElementHandlerAccessor(IMixinAnnotationProcessor var1, AnnotatedMixin var2) {
      super(var1, var2);
   }

   public ReferenceMapper getReferenceMapper() {
      return null;
   }

   public String getClassRef() {
      return this.mixin.getClassRef();
   }

   public String getTargetClassRef() {
      throw new UnsupportedOperationException("Target class not available at compile time");
   }

   public IMixinInfo getMixin() {
      throw new UnsupportedOperationException("MixinInfo not available at compile time");
   }

   public Extensions getExtensions() {
      throw new UnsupportedOperationException("Mixin Extensions not available at compile time");
   }

   public boolean getOption(MixinEnvironment.Option var1) {
      throw new UnsupportedOperationException("Options not available at compile time");
   }

   public int getPriority() {
      throw new UnsupportedOperationException("Priority not available at compile time");
   }

   public Target getTargetMethod(MethodNode var1) {
      throw new UnsupportedOperationException("Target not available at compile time");
   }

   public void registerAccessor(AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor var1) {
      if (var1.getAccessorType() == null) {
         var1.printMessage(this.ap, Kind.WARNING, "Unsupported accessor type");
      } else {
         String var2 = this.getAccessorTargetName(var1);
         if (var2 == null) {
            var1.printMessage(this.ap, Kind.WARNING, "Cannot inflect accessor target name");
         } else {
            var1.setTargetName(var2);
            Iterator var3 = this.mixin.getTargets().iterator();

            while(var3.hasNext()) {
               TypeHandle var4 = (TypeHandle)var3.next();
               if (var1.getAccessorType() == AccessorInfo.AccessorType.METHOD_PROXY) {
                  this.registerInvokerForTarget((AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker)var1, var4);
               } else {
                  this.registerAccessorForTarget(var1, var4);
               }
            }

         }
      }
   }

   private void registerAccessorForTarget(AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor var1, TypeHandle var2) {
      FieldHandle var3 = var2.findField(var1.getTargetName(), var1.getTargetTypeName(), false);
      if (var3 == null) {
         if (!var2.isImaginary()) {
            var1.printMessage(this.ap, Kind.ERROR, "Could not locate @Accessor target " + var1 + " in target " + var2);
            return;
         }

         var3 = new FieldHandle(var2.getName(), var1.getTargetName(), var1.getDesc());
      }

      if (var1.shouldRemap()) {
         ObfuscationData var4 = this.obf.getDataProvider().getObfField(var3.asMapping(false).move(var2.getName()));
         if (var4.isEmpty()) {
            String var5 = this.mixin.isMultiTarget() ? " in target " + var2 : "";
            var1.printMessage(this.ap, Kind.WARNING, "Unable to locate obfuscation mapping" + var5 + " for @Accessor target " + var1);
         } else {
            var4 = AnnotatedMixinElementHandler.stripOwnerData(var4);

            try {
               this.obf.getReferenceManager().addFieldMapping(this.mixin.getClassRef(), var1.getTargetName(), var1.getContext(), var4);
            } catch (ReferenceManager.ReferenceConflictException var6) {
               var1.printMessage(this.ap, Kind.ERROR, "Mapping conflict for @Accessor target " + var1 + ": " + var6.getNew() + " for target " + var2 + " conflicts with existing mapping " + var6.getOld());
            }

         }
      }
   }

   private void registerInvokerForTarget(AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker var1, TypeHandle var2) {
      MethodHandle var3 = var2.findMethod(var1.getTargetName(), var1.getTargetTypeName(), false);
      if (var3 == null) {
         if (!var2.isImaginary()) {
            var1.printMessage(this.ap, Kind.ERROR, "Could not locate @Invoker target " + var1 + " in target " + var2);
            return;
         }

         var3 = new MethodHandle(var2, var1.getTargetName(), var1.getDesc());
      }

      if (var1.shouldRemap()) {
         ObfuscationData var4 = this.obf.getDataProvider().getObfMethod(var3.asMapping(false).move(var2.getName()));
         if (var4.isEmpty()) {
            String var5 = this.mixin.isMultiTarget() ? " in target " + var2 : "";
            var1.printMessage(this.ap, Kind.WARNING, "Unable to locate obfuscation mapping" + var5 + " for @Accessor target " + var1);
         } else {
            var4 = AnnotatedMixinElementHandler.stripOwnerData(var4);

            try {
               this.obf.getReferenceManager().addMethodMapping(this.mixin.getClassRef(), var1.getTargetName(), var1.getContext(), var4);
            } catch (ReferenceManager.ReferenceConflictException var6) {
               var1.printMessage(this.ap, Kind.ERROR, "Mapping conflict for @Invoker target " + var1 + ": " + var6.getNew() + " for target " + var2 + " conflicts with existing mapping " + var6.getOld());
            }

         }
      }
   }

   private String getAccessorTargetName(AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor var1) {
      String var2 = var1.getAnnotationValue();
      return Strings.isNullOrEmpty(var2) ? this.inflectAccessorTarget(var1) : var2;
   }

   private String inflectAccessorTarget(AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor var1) {
      return AccessorInfo.inflectTarget(var1.getSimpleName(), var1.getAccessorType(), "", this, false);
   }

   public IReferenceMapper getReferenceMapper() {
      return this.getReferenceMapper();
   }

   static class AnnotatedElementInvoker extends AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor {
      public AnnotatedElementInvoker(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
         super(var1, var2, var3);
      }

      public String getAccessorDesc() {
         return TypeUtils.getDescriptor((ExecutableElement)this.getElement());
      }

      public AccessorInfo.AccessorType getAccessorType() {
         return AccessorInfo.AccessorType.METHOD_PROXY;
      }

      public String getTargetTypeName() {
         return TypeUtils.getJavaSignature(this.getElement());
      }
   }

   static class AnnotatedElementAccessor extends AnnotatedMixinElementHandler.AnnotatedElement {
      private final boolean shouldRemap;
      private final TypeMirror returnType;
      private String targetName;

      public AnnotatedElementAccessor(ExecutableElement var1, AnnotationHandle var2, boolean var3) {
         super(var1, var2);
         this.shouldRemap = var3;
         this.returnType = ((ExecutableElement)this.getElement()).getReturnType();
      }

      public boolean shouldRemap() {
         return this.shouldRemap;
      }

      public String getAnnotationValue() {
         return (String)this.getAnnotation().getValue();
      }

      public TypeMirror getTargetType() {
         switch(this.getAccessorType()) {
         case FIELD_GETTER:
            return this.returnType;
         case FIELD_SETTER:
            return ((VariableElement)((ExecutableElement)this.getElement()).getParameters().get(0)).asType();
         default:
            return null;
         }
      }

      public String getTargetTypeName() {
         return TypeUtils.getTypeName(this.getTargetType());
      }

      public String getAccessorDesc() {
         return TypeUtils.getInternalName(this.getTargetType());
      }

      public MemberInfo getContext() {
         return new MemberInfo(this.getTargetName(), (String)null, this.getAccessorDesc());
      }

      public AccessorInfo.AccessorType getAccessorType() {
         return this.returnType.getKind() == TypeKind.VOID ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
      }

      public void setTargetName(String var1) {
         this.targetName = var1;
      }

      public String getTargetName() {
         return this.targetName;
      }

      public String toString() {
         return this.targetName != null ? this.targetName : "<invalid>";
      }
   }
}
