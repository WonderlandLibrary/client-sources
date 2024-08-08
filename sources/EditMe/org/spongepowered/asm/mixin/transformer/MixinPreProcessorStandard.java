package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.Iterator;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

class MixinPreProcessorStandard {
   private static final Logger logger = LogManager.getLogger("mixin");
   protected final MixinInfo mixin;
   protected final MixinInfo.MixinClassNode classNode;
   protected final MixinEnvironment env;
   protected final Profiler profiler = MixinEnvironment.getProfiler();
   private final boolean verboseLogging;
   private final boolean strictUnique;
   private boolean prepared;
   private boolean attached;

   MixinPreProcessorStandard(MixinInfo var1, MixinInfo.MixinClassNode var2) {
      this.mixin = var1;
      this.classNode = var2;
      this.env = var1.getParent().getEnvironment();
      this.verboseLogging = this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
      this.strictUnique = this.env.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
   }

   final MixinPreProcessorStandard prepare() {
      if (this.prepared) {
         return this;
      } else {
         this.prepared = true;
         Profiler.Section var1 = this.profiler.begin("prepare");
         Iterator var2 = this.classNode.mixinMethods.iterator();

         while(var2.hasNext()) {
            MixinInfo.MixinMethodNode var3 = (MixinInfo.MixinMethodNode)var2.next();
            ClassInfo.Method var4 = this.mixin.getClassInfo().findMethod((MethodNode)var3);
            this.prepareMethod(var3, var4);
         }

         var2 = this.classNode.fields.iterator();

         while(var2.hasNext()) {
            FieldNode var5 = (FieldNode)var2.next();
            this.prepareField(var5);
         }

         var1.end();
         return this;
      }
   }

   protected void prepareMethod(MixinInfo.MixinMethodNode var1, ClassInfo.Method var2) {
      this.prepareShadow(var1, var2);
      this.prepareSoftImplements(var1, var2);
   }

   protected void prepareShadow(MixinInfo.MixinMethodNode var1, ClassInfo.Method var2) {
      AnnotationNode var3 = Annotations.getVisible((MethodNode)var1, Shadow.class);
      if (var3 != null) {
         String var4 = (String)Annotations.getValue(var3, "prefix", Shadow.class);
         if (var1.name.startsWith(var4)) {
            Annotations.setVisible((MethodNode)var1, MixinRenamed.class, "originalName", var1.name);
            String var5 = var1.name.substring(var4.length());
            var1.name = var2.renameTo(var5);
         }

      }
   }

   protected void prepareSoftImplements(MixinInfo.MixinMethodNode var1, ClassInfo.Method var2) {
      Iterator var3 = this.mixin.getSoftImplements().iterator();

      while(var3.hasNext()) {
         InterfaceInfo var4 = (InterfaceInfo)var3.next();
         if (var4.renameMethod(var1)) {
            var2.renameTo(var1.name);
         }
      }

   }

   protected void prepareField(FieldNode var1) {
   }

   final MixinPreProcessorStandard conform(TargetClassContext var1) {
      return this.conform(var1.getClassInfo());
   }

   final MixinPreProcessorStandard conform(ClassInfo var1) {
      Profiler.Section var2 = this.profiler.begin("conform");
      Iterator var3 = this.classNode.mixinMethods.iterator();

      while(var3.hasNext()) {
         MixinInfo.MixinMethodNode var4 = (MixinInfo.MixinMethodNode)var3.next();
         if (var4.isInjector()) {
            ClassInfo.Method var5 = this.mixin.getClassInfo().findMethod((MethodNode)var4, 10);
            this.conformInjector(var1, var4, var5);
         }
      }

      var2.end();
      return this;
   }

   private void conformInjector(ClassInfo var1, MixinInfo.MixinMethodNode var2, ClassInfo.Method var3) {
      MethodMapper var4 = var1.getMethodMapper();
      var4.remapHandlerMethod(this.mixin, var2, var3);
   }

   MixinTargetContext createContextFor(TargetClassContext var1) {
      MixinTargetContext var2 = new MixinTargetContext(this.mixin, this.classNode, var1);
      this.conform(var1);
      this.attach(var2);
      return var2;
   }

   final MixinPreProcessorStandard attach(MixinTargetContext var1) {
      if (this.attached) {
         throw new IllegalStateException("Preprocessor was already attached");
      } else {
         this.attached = true;
         Profiler.Section var2 = this.profiler.begin("attach");
         Profiler.Section var3 = this.profiler.begin("methods");
         this.attachMethods(var1);
         var3 = var3.next("fields");
         this.attachFields(var1);
         var3 = var3.next("transform");
         this.transform(var1);
         var3.end();
         var2.end();
         return this;
      }
   }

   protected void attachMethods(MixinTargetContext var1) {
      Iterator var2 = this.classNode.mixinMethods.iterator();

      while(var2.hasNext()) {
         MixinInfo.MixinMethodNode var3 = (MixinInfo.MixinMethodNode)var2.next();
         if (!this.validateMethod(var1, var3)) {
            var2.remove();
         } else if (this.attachInjectorMethod(var1, var3)) {
            var1.addMixinMethod(var3);
         } else if (this.attachAccessorMethod(var1, var3)) {
            var2.remove();
         } else if (this.attachShadowMethod(var1, var3)) {
            var1.addShadowMethod(var3);
            var2.remove();
         } else if (this.attachOverwriteMethod(var1, var3)) {
            var1.addMixinMethod(var3);
         } else if (this.attachUniqueMethod(var1, var3)) {
            var2.remove();
         } else {
            this.attachMethod(var1, var3);
            var1.addMixinMethod(var3);
         }
      }

   }

   protected boolean validateMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2) {
      return true;
   }

   protected boolean attachInjectorMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2) {
      return var2.isInjector();
   }

   protected boolean attachAccessorMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2) {
      return this.attachAccessorMethod(var1, var2, MixinPreProcessorStandard.SpecialMethod.ACCESSOR) || this.attachAccessorMethod(var1, var2, MixinPreProcessorStandard.SpecialMethod.INVOKER);
   }

   protected boolean attachAccessorMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2, MixinPreProcessorStandard.SpecialMethod var3) {
      AnnotationNode var4 = var2.getVisibleAnnotation(var3.annotation);
      if (var4 == null) {
         return false;
      } else {
         String var5 = var3 + " method " + var2.name;
         ClassInfo.Method var6 = this.getSpecialMethod(var2, var3);
         if (MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8) && var6.isStatic()) {
            if (this.mixin.getTargets().size() > 1) {
               throw new InvalidAccessorException(var1, var5 + " in multi-target mixin is invalid. Mixin must have exactly 1 target.");
            }

            String var7 = var1.getUniqueName(var2, true);
            logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique method {}{} to {} in {}", new Object[]{var2.name, var2.desc, var7, this.mixin});
            var2.name = var6.renameTo(var7);
         } else {
            if (!var6.isAbstract()) {
               throw new InvalidAccessorException(var1, var5 + " is not abstract");
            }

            if (var6.isStatic()) {
               throw new InvalidAccessorException(var1, var5 + " cannot be static");
            }
         }

         var1.addAccessorMethod(var2, var3.annotation);
         return true;
      }
   }

   protected boolean attachShadowMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2) {
      return this.attachSpecialMethod(var1, var2, MixinPreProcessorStandard.SpecialMethod.SHADOW);
   }

   protected boolean attachOverwriteMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2) {
      return this.attachSpecialMethod(var1, var2, MixinPreProcessorStandard.SpecialMethod.OVERWRITE);
   }

   protected boolean attachSpecialMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2, MixinPreProcessorStandard.SpecialMethod var3) {
      AnnotationNode var4 = var2.getVisibleAnnotation(var3.annotation);
      if (var4 == null) {
         return false;
      } else {
         if (var3.isOverwrite) {
            this.checkMixinNotUnique(var2, var3);
         }

         ClassInfo.Method var5 = this.getSpecialMethod(var2, var3);
         MethodNode var6 = var1.findMethod(var2, var4);
         if (var6 == null) {
            if (var3.isOverwrite) {
               return false;
            }

            var6 = var1.findRemappedMethod(var2);
            if (var6 == null) {
               throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s was not located in the target class %s. %s%s", var3, var2.name, this.mixin, var1.getTarget(), var1.getReferenceMapper().getStatus(), getDynamicInfo((MethodNode)var2)));
            }

            var2.name = var5.renameTo(var6.name);
         }

         if ("<init>".equals(var6.name)) {
            throw new InvalidMixinException(this.mixin, String.format("Nice try! %s in %s cannot alias a constructor", var2.name, this.mixin));
         } else if (!Bytecode.compareFlags((MethodNode)var2, (MethodNode)var6, 8)) {
            throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of %s method %s in %s does not match the target", var3, var2.name, this.mixin));
         } else {
            this.conformVisibility(var1, var2, var3, var6);
            if (!var6.name.equals(var2.name)) {
               if (var3.isOverwrite && (var6.access & 2) == 0) {
                  throw new InvalidMixinException(this.mixin, "Non-private method cannot be aliased. Found " + var6.name);
               }

               var2.name = var5.renameTo(var6.name);
            }

            return true;
         }
      }
   }

   private void conformVisibility(MixinTargetContext var1, MixinInfo.MixinMethodNode var2, MixinPreProcessorStandard.SpecialMethod var3, MethodNode var4) {
      Bytecode.Visibility var5 = Bytecode.getVisibility(var4);
      Bytecode.Visibility var6 = Bytecode.getVisibility((MethodNode)var2);
      if (var6.ordinal() >= var5.ordinal()) {
         if (var5 == Bytecode.Visibility.PRIVATE && var6.ordinal() > Bytecode.Visibility.PRIVATE.ordinal()) {
            var1.getTarget().addUpgradedMethod(var4);
         }

      } else {
         String var7 = String.format("%s %s method %s in %s cannot reduce visibiliy of %s target method", var6, var3, var2.name, this.mixin, var5);
         if (var3.isOverwrite && !this.mixin.getParent().conformOverwriteVisibility()) {
            throw new InvalidMixinException(this.mixin, var7);
         } else {
            if (var6 == Bytecode.Visibility.PRIVATE) {
               if (var3.isOverwrite) {
                  logger.warn("Static binding violation: {}, visibility will be upgraded.", new Object[]{var7});
               }

               var1.addUpgradedMethod(var2);
               Bytecode.setVisibility((MethodNode)var2, var5);
            }

         }
      }
   }

   protected ClassInfo.Method getSpecialMethod(MixinInfo.MixinMethodNode var1, MixinPreProcessorStandard.SpecialMethod var2) {
      ClassInfo.Method var3 = this.mixin.getClassInfo().findMethod((MethodNode)var1, 10);
      this.checkMethodNotUnique(var3, var2);
      return var3;
   }

   protected void checkMethodNotUnique(ClassInfo.Method var1, MixinPreProcessorStandard.SpecialMethod var2) {
      if (var1.isUnique()) {
         throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s cannot be @Unique", var2, var1.getName(), this.mixin));
      }
   }

   protected void checkMixinNotUnique(MixinInfo.MixinMethodNode var1, MixinPreProcessorStandard.SpecialMethod var2) {
      if (this.mixin.isUnique()) {
         throw new InvalidMixinException(this.mixin, String.format("%s method %s found in a @Unique mixin %s", var2, var1.name, this.mixin));
      }
   }

   protected boolean attachUniqueMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2) {
      ClassInfo.Method var3 = this.mixin.getClassInfo().findMethod((MethodNode)var2, 10);
      if (var3 == null || !var3.isUnique() && !this.mixin.isUnique() && !var3.isSynthetic()) {
         return false;
      } else {
         if (var3.isSynthetic()) {
            var1.transformDescriptor((MethodNode)var2);
            var3.remapTo(var2.desc);
         }

         MethodNode var4 = var1.findMethod(var2, (AnnotationNode)null);
         if (var4 == null) {
            return false;
         } else {
            String var5 = var3.isSynthetic() ? "synthetic" : "@Unique";
            if (Bytecode.getVisibility((MethodNode)var2).ordinal() < Bytecode.Visibility.PUBLIC.ordinal()) {
               String var9 = var1.getUniqueName(var2, false);
               logger.log(this.mixin.getLoggingLevel(), "Renaming {} method {}{} to {} in {}", new Object[]{var5, var2.name, var2.desc, var9, this.mixin});
               var2.name = var3.renameTo(var9);
               return false;
            } else if (this.strictUnique) {
               throw new InvalidMixinException(this.mixin, String.format("Method conflict, %s method %s in %s cannot overwrite %s%s in %s", var5, var2.name, this.mixin, var4.name, var4.desc, var1.getTarget()));
            } else {
               AnnotationNode var6 = Annotations.getVisible((MethodNode)var2, Unique.class);
               if (var6 != null && (Boolean)Annotations.getValue(var6, "silent", (Object)Boolean.FALSE)) {
                  var1.addMixinMethod(var2);
                  return true;
               } else if (Bytecode.hasFlag((MethodNode)var2, 64)) {
                  try {
                     Bytecode.compareBridgeMethods(var4, var2);
                     logger.debug("Discarding sythetic bridge method {} in {} because existing method in {} is compatible", new Object[]{var5, var2.name, this.mixin, var1.getTarget()});
                     return true;
                  } catch (SyntheticBridgeException var8) {
                     if (this.verboseLogging || this.env.getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                        var8.printAnalysis(var1, var4, var2);
                     }

                     throw new InvalidMixinException(this.mixin, var8.getMessage());
                  }
               } else {
                  logger.warn("Discarding {} public method {} in {} because it already exists in {}", new Object[]{var5, var2.name, this.mixin, var1.getTarget()});
                  return true;
               }
            }
         }
      }
   }

   protected void attachMethod(MixinTargetContext var1, MixinInfo.MixinMethodNode var2) {
      ClassInfo.Method var3 = this.mixin.getClassInfo().findMethod((MethodNode)var2);
      if (var3 != null) {
         ClassInfo.Method var4 = this.mixin.getClassInfo().findMethodInHierarchy((MethodNode)var2, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
         if (var4 != null && var4.isRenamed()) {
            var2.name = var3.renameTo(var4.getName());
         }

         MethodNode var5 = var1.findMethod(var2, (AnnotationNode)null);
         if (var5 != null) {
            this.conformVisibility(var1, var2, MixinPreProcessorStandard.SpecialMethod.MERGE, var5);
         }

      }
   }

   protected void attachFields(MixinTargetContext var1) {
      Iterator var2 = this.classNode.fields.iterator();

      while(true) {
         while(var2.hasNext()) {
            FieldNode var3 = (FieldNode)var2.next();
            AnnotationNode var4 = Annotations.getVisible(var3, Shadow.class);
            boolean var5 = var4 != null;
            if (!this.validateField(var1, var3, var4)) {
               var2.remove();
            } else {
               ClassInfo.Field var6 = this.mixin.getClassInfo().findField(var3);
               var1.transformDescriptor(var3);
               var6.remapTo(var3.desc);
               if (var6.isUnique() && var5) {
                  throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s cannot be @Unique", var3.name));
               }

               FieldNode var7 = var1.findField(var3, var4);
               if (var7 == null) {
                  if (var4 == null) {
                     continue;
                  }

                  var7 = var1.findRemappedField(var3);
                  if (var7 == null) {
                     throw new InvalidMixinException(this.mixin, String.format("Shadow field %s was not located in the target class %s. %s%s", var3.name, var1.getTarget(), var1.getReferenceMapper().getStatus(), getDynamicInfo(var3)));
                  }

                  var3.name = var6.renameTo(var7.name);
               }

               if (!Bytecode.compareFlags((FieldNode)var3, (FieldNode)var7, 8)) {
                  throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of @Shadow field %s in %s does not match the target", var3.name, this.mixin));
               }

               if (var6.isUnique()) {
                  if ((var3.access & 6) != 0) {
                     String var8 = var1.getUniqueName(var3);
                     logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique field {}{} to {} in {}", new Object[]{var3.name, var3.desc, var8, this.mixin});
                     var3.name = var6.renameTo(var8);
                  } else {
                     if (this.strictUnique) {
                        throw new InvalidMixinException(this.mixin, String.format("Field conflict, @Unique field %s in %s cannot overwrite %s%s in %s", var3.name, this.mixin, var7.name, var7.desc, var1.getTarget()));
                     }

                     logger.warn("Discarding @Unique public field {} in {} because it already exists in {}. Note that declared FIELD INITIALISERS will NOT be removed!", new Object[]{var3.name, this.mixin, var1.getTarget()});
                     var2.remove();
                  }
               } else {
                  if (!var7.desc.equals(var3.desc)) {
                     throw new InvalidMixinException(this.mixin, String.format("The field %s in the target class has a conflicting signature", var3.name));
                  }

                  if (!var7.name.equals(var3.name)) {
                     if ((var7.access & 2) == 0 && (var7.access & 4096) == 0) {
                        throw new InvalidMixinException(this.mixin, "Non-private field cannot be aliased. Found " + var7.name);
                     }

                     var3.name = var6.renameTo(var7.name);
                  }

                  var2.remove();
                  if (var5) {
                     boolean var10 = var6.isDecoratedFinal();
                     if (this.verboseLogging && Bytecode.hasFlag((FieldNode)var7, 16) != var10) {
                        String var9 = var10 ? "@Shadow field {}::{} is decorated with @Final but target is not final" : "@Shadow target {}::{} is final but shadow is not decorated with @Final";
                        logger.warn(var9, new Object[]{this.mixin, var3.name});
                     }

                     var1.addShadowField(var3, var6);
                  }
               }
            }
         }

         return;
      }
   }

   protected boolean validateField(MixinTargetContext var1, FieldNode var2, AnnotationNode var3) {
      if (Bytecode.hasFlag((FieldNode)var2, 8) && !Bytecode.hasFlag((FieldNode)var2, 2) && !Bytecode.hasFlag((FieldNode)var2, 4096) && var3 == null) {
         throw new InvalidMixinException(var1, String.format("Mixin %s contains non-private static field %s:%s", var1, var2.name, var2.desc));
      } else {
         String var4 = (String)Annotations.getValue(var3, "prefix", Shadow.class);
         if (var2.name.startsWith(var4)) {
            throw new InvalidMixinException(var1, String.format("@Shadow field %s.%s has a shadow prefix. This is not allowed.", var1, var2.name));
         } else if ("super$".equals(var2.name)) {
            if (var2.access != 2) {
               throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must be private and non-final", var1, var2.name));
            } else if (!var2.desc.equals("L" + this.mixin.getClassRef() + ";")) {
               throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must have the same type as the parent mixin (%s)", var1, var2.name, this.mixin.getClassName()));
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   protected void transform(MixinTargetContext var1) {
      Iterator var2 = this.classNode.methods.iterator();

      while(var2.hasNext()) {
         MethodNode var3 = (MethodNode)var2.next();
         ListIterator var4 = var3.instructions.iterator();

         while(var4.hasNext()) {
            AbstractInsnNode var5 = (AbstractInsnNode)var4.next();
            if (var5 instanceof MethodInsnNode) {
               this.transformMethod((MethodInsnNode)var5);
            } else if (var5 instanceof FieldInsnNode) {
               this.transformField((FieldInsnNode)var5);
            }
         }
      }

   }

   protected void transformMethod(MethodInsnNode var1) {
      Profiler.Section var2 = this.profiler.begin("meta");
      ClassInfo var3 = ClassInfo.forName(var1.owner);
      if (var3 == null) {
         throw new RuntimeException(new ClassNotFoundException(var1.owner.replace('/', '.')));
      } else {
         ClassInfo.Method var4 = var3.findMethodInHierarchy((MethodInsnNode)var1, ClassInfo.SearchType.ALL_CLASSES, 2);
         var2.end();
         if (var4 != null && var4.isRenamed()) {
            var1.name = var4.getName();
         }

      }
   }

   protected void transformField(FieldInsnNode var1) {
      Profiler.Section var2 = this.profiler.begin("meta");
      ClassInfo var3 = ClassInfo.forName(var1.owner);
      if (var3 == null) {
         throw new RuntimeException(new ClassNotFoundException(var1.owner.replace('/', '.')));
      } else {
         ClassInfo.Field var4 = var3.findField(var1, 2);
         var2.end();
         if (var4 != null && var4.isRenamed()) {
            var1.name = var4.getName();
         }

      }
   }

   protected static String getDynamicInfo(MethodNode var0) {
      return getDynamicInfo("Method", Annotations.getInvisible(var0, Dynamic.class));
   }

   protected static String getDynamicInfo(FieldNode var0) {
      return getDynamicInfo("Field", Annotations.getInvisible(var0, Dynamic.class));
   }

   private static String getDynamicInfo(String var0, AnnotationNode var1) {
      String var2 = Strings.nullToEmpty((String)Annotations.getValue(var1));
      Type var3 = (Type)Annotations.getValue(var1, "mixin");
      if (var3 != null) {
         var2 = String.format("{%s} %s", var3.getClassName(), var2).trim();
      }

      return var2.length() > 0 ? String.format(" %s is @Dynamic(%s)", var0, var2) : "";
   }

   static enum SpecialMethod {
      MERGE(true),
      OVERWRITE(true, Overwrite.class),
      SHADOW(false, Shadow.class),
      ACCESSOR(false, Accessor.class),
      INVOKER(false, Invoker.class);

      final boolean isOverwrite;
      final Class annotation;
      final String description;
      private static final MixinPreProcessorStandard.SpecialMethod[] $VALUES = new MixinPreProcessorStandard.SpecialMethod[]{MERGE, OVERWRITE, SHADOW, ACCESSOR, INVOKER};

      private SpecialMethod(boolean var3, Class var4) {
         this.isOverwrite = var3;
         this.annotation = var4;
         this.description = "@" + Bytecode.getSimpleName(var4);
      }

      private SpecialMethod(boolean var3) {
         this.isOverwrite = var3;
         this.annotation = null;
         this.description = "overwrite";
      }

      public String toString() {
         return this.description;
      }
   }
}
