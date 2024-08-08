package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

class MixinApplicatorStandard {
   protected static final List CONSTRAINED_ANNOTATIONS = ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
   protected static final int[] INITIALISER_OPCODE_BLACKLIST = new int[]{177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86};
   protected final Logger logger = LogManager.getLogger("mixin");
   protected final TargetClassContext context;
   protected final String targetName;
   protected final ClassNode targetClass;
   protected final Profiler profiler = MixinEnvironment.getProfiler();

   MixinApplicatorStandard(TargetClassContext var1) {
      this.context = var1;
      this.targetName = var1.getClassName();
      this.targetClass = var1.getClassNode();
   }

   void apply(SortedSet var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         MixinInfo var4 = (MixinInfo)var3.next();
         this.logger.log(var4.getLoggingLevel(), "Mixing {} from {} into {}", new Object[]{var4.getName(), var4.getParent(), this.targetName});
         var2.add(var4.createContextFor(this.context));
      }

      var3 = null;

      try {
         Iterator var13 = var2.iterator();

         MixinTargetContext var5;
         while(var13.hasNext()) {
            var5 = (MixinTargetContext)var13.next();
            var5.preApply(this.targetName, this.targetClass);
         }

         MixinApplicatorStandard.ApplicatorPass[] var14 = MixinApplicatorStandard.ApplicatorPass.values();
         int var15 = var14.length;

         for(int var6 = 0; var6 < var15; ++var6) {
            MixinApplicatorStandard.ApplicatorPass var7 = var14[var6];
            Profiler.Section var8 = this.profiler.begin("pass", var7.name().toLowerCase());
            Iterator var9 = var2.iterator();

            while(var9.hasNext()) {
               MixinTargetContext var10 = (MixinTargetContext)var9.next();
               this.applyMixin(var10, var7);
            }

            var8.end();
         }

         var13 = var2.iterator();

         while(var13.hasNext()) {
            var5 = (MixinTargetContext)var13.next();
            var5.postApply(this.targetName, this.targetClass);
         }
      } catch (InvalidMixinException var11) {
         throw var11;
      } catch (Exception var12) {
         throw new InvalidMixinException(var3, "Unexpecteded " + var12.getClass().getSimpleName() + " whilst applying the mixin class: " + var12.getMessage(), var12);
      }

      this.applySourceMap(this.context);
      this.context.processDebugTasks();
   }

   protected final void applyMixin(MixinTargetContext var1, MixinApplicatorStandard.ApplicatorPass var2) {
      switch(var2) {
      case MAIN:
         this.applySignature(var1);
         this.applyInterfaces(var1);
         this.applyAttributes(var1);
         this.applyAnnotations(var1);
         this.applyFields(var1);
         this.applyMethods(var1);
         this.applyInitialisers(var1);
         break;
      case PREINJECT:
         this.prepareInjections(var1);
         break;
      case INJECT:
         this.applyAccessors(var1);
         this.applyInjections(var1);
         break;
      default:
         throw new IllegalStateException("Invalid pass specified " + var2);
      }

   }

   protected void applySignature(MixinTargetContext var1) {
      this.context.mergeSignature(var1.getSignature());
   }

   protected void applyInterfaces(MixinTargetContext var1) {
      Iterator var2 = var1.getInterfaces().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         if (!this.targetClass.interfaces.contains(var3)) {
            this.targetClass.interfaces.add(var3);
            var1.getTargetClassInfo().addInterface(var3);
         }
      }

   }

   protected void applyAttributes(MixinTargetContext var1) {
      if (var1.shouldSetSourceFile()) {
         this.targetClass.sourceFile = var1.getSourceFile();
      }

      this.targetClass.version = Math.max(this.targetClass.version, var1.getMinRequiredClassVersion());
   }

   protected void applyAnnotations(MixinTargetContext var1) {
      ClassNode var2 = var1.getClassNode();
      Bytecode.mergeAnnotations(var2, this.targetClass);
   }

   protected void applyFields(MixinTargetContext var1) {
      this.mergeShadowFields(var1);
      this.mergeNewFields(var1);
   }

   protected void mergeShadowFields(MixinTargetContext var1) {
      Iterator var2 = var1.getShadowFields().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         FieldNode var4 = (FieldNode)var3.getKey();
         FieldNode var5 = this.findTargetField(var4);
         if (var5 != null) {
            Bytecode.mergeAnnotations(var4, var5);
            if (((ClassInfo.Field)var3.getValue()).isDecoratedMutable() && !Bytecode.hasFlag((FieldNode)var5, 2)) {
               var5.access &= -17;
            }
         }
      }

   }

   protected void mergeNewFields(MixinTargetContext var1) {
      Iterator var2 = var1.getFields().iterator();

      while(var2.hasNext()) {
         FieldNode var3 = (FieldNode)var2.next();
         FieldNode var4 = this.findTargetField(var3);
         if (var4 == null) {
            this.targetClass.fields.add(var3);
         }
      }

   }

   protected void applyMethods(MixinTargetContext var1) {
      Iterator var2 = var1.getShadowMethods().iterator();

      MethodNode var3;
      while(var2.hasNext()) {
         var3 = (MethodNode)var2.next();
         this.applyShadowMethod(var1, var3);
      }

      var2 = var1.getMethods().iterator();

      while(var2.hasNext()) {
         var3 = (MethodNode)var2.next();
         this.applyNormalMethod(var1, var3);
      }

   }

   protected void applyShadowMethod(MixinTargetContext var1, MethodNode var2) {
      MethodNode var3 = this.findTargetMethod(var2);
      if (var3 != null) {
         Bytecode.mergeAnnotations(var2, var3);
      }

   }

   protected void applyNormalMethod(MixinTargetContext var1, MethodNode var2) {
      var1.transformMethod(var2);
      if (!var2.name.startsWith("<")) {
         this.checkMethodVisibility(var1, var2);
         this.checkMethodConstraints(var1, var2);
         this.mergeMethod(var1, var2);
      } else if ("<clinit>".equals(var2.name)) {
         this.appendInsns(var1, var2);
      }

   }

   protected void mergeMethod(MixinTargetContext var1, MethodNode var2) {
      boolean var3 = Annotations.getVisible(var2, Overwrite.class) != null;
      MethodNode var4 = this.findTargetMethod(var2);
      if (var4 != null) {
         if (this.isAlreadyMerged(var1, var2, var3, var4)) {
            return;
         }

         AnnotationNode var5 = Annotations.getInvisible(var2, Intrinsic.class);
         if (var5 != null) {
            if (this.mergeIntrinsic(var1, var2, var3, var4, var5)) {
               var1.getTarget().methodMerged(var2);
               return;
            }
         } else {
            if (var1.requireOverwriteAnnotations() && !var3) {
               throw new InvalidMixinException(var1, String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", var2.name, var2.desc, var1, var1.getTarget().getClassName()));
            }

            this.targetClass.methods.remove(var4);
         }
      } else if (var3) {
         throw new InvalidMixinException(var1, String.format("Overwrite target \"%s\" was not located in target class %s", var2.name, var1.getTargetClassRef()));
      }

      this.targetClass.methods.add(var2);
      var1.methodMerged(var2);
      if (var2.signature != null) {
         SignatureVisitor var6 = var1.getSignature().getRemapper();
         (new SignatureReader(var2.signature)).accept(var6);
         var2.signature = var6.toString();
      }

   }

   protected boolean isAlreadyMerged(MixinTargetContext var1, MethodNode var2, boolean var3, MethodNode var4) {
      AnnotationNode var5 = Annotations.getVisible(var4, MixinMerged.class);
      if (var5 == null) {
         if (Annotations.getVisible(var4, Final.class) != null) {
            this.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[]{var2.name, var1});
            return true;
         } else {
            return false;
         }
      } else {
         String var6 = (String)Annotations.getValue(var5, "sessionId");
         if (!this.context.getSessionId().equals(var6)) {
            throw new ClassFormatError("Invalid @MixinMerged annotation found in" + var1 + " at " + var2.name + " in " + this.targetClass.name);
         } else if (Bytecode.hasFlag((MethodNode)var4, 4160) && Bytecode.hasFlag((MethodNode)var2, 4160)) {
            if (var1.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
               this.logger.warn("Synthetic bridge method clash for {} in {}", new Object[]{var2.name, var1});
            }

            return true;
         } else {
            String var7 = (String)Annotations.getValue(var5, "mixin");
            int var8 = (Integer)Annotations.getValue(var5, "priority");
            if (var8 >= var1.getPriority() && !var7.equals(var1.getClassName())) {
               this.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[]{var2.name, var1, var7});
               return true;
            } else if (Annotations.getVisible(var4, Final.class) != null) {
               this.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[]{var2.name, var1, var7});
               return true;
            } else {
               return false;
            }
         }
      }
   }

   protected boolean mergeIntrinsic(MixinTargetContext var1, MethodNode var2, boolean var3, MethodNode var4, AnnotationNode var5) {
      if (var3) {
         throw new InvalidMixinException(var1, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + var2.name + " in " + var1);
      } else {
         String var6 = var2.name + var2.desc;
         if (Bytecode.hasFlag((MethodNode)var2, 8)) {
            throw new InvalidMixinException(var1, "@Intrinsic method cannot be static, found " + var6 + " in " + var1);
         } else {
            if (!Bytecode.hasFlag((MethodNode)var2, 4096)) {
               AnnotationNode var7 = Annotations.getVisible(var2, MixinRenamed.class);
               if (var7 == null || !(Boolean)Annotations.getValue(var7, "isInterfaceMember", (Object)Boolean.FALSE)) {
                  throw new InvalidMixinException(var1, "@Intrinsic method must be prefixed interface method, no rename encountered on " + var6 + " in " + var1);
               }
            }

            if (!(Boolean)Annotations.getValue(var5, "displace", (Object)Boolean.FALSE)) {
               this.logger.log(var1.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[]{var6, var1.getTargetClassRef()});
               return true;
            } else {
               this.displaceIntrinsic(var1, var2, var4);
               return false;
            }
         }
      }
   }

   protected void displaceIntrinsic(MixinTargetContext var1, MethodNode var2, MethodNode var3) {
      String var4 = "proxy+" + var3.name;
      ListIterator var5 = var2.instructions.iterator();

      while(var5.hasNext()) {
         AbstractInsnNode var6 = (AbstractInsnNode)var5.next();
         if (var6 instanceof MethodInsnNode && var6.getOpcode() != 184) {
            MethodInsnNode var7 = (MethodInsnNode)var6;
            if (var7.owner.equals(this.targetClass.name) && var7.name.equals(var3.name) && var7.desc.equals(var3.desc)) {
               var7.name = var4;
            }
         }
      }

      var3.name = var4;
   }

   protected final void appendInsns(MixinTargetContext var1, MethodNode var2) {
      if (Type.getReturnType(var2.desc) != Type.VOID_TYPE) {
         throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
      } else {
         MethodNode var3 = this.findTargetMethod(var2);
         if (var3 == null) {
            this.targetClass.methods.add(var2);
         } else {
            AbstractInsnNode var4 = Bytecode.findInsn(var3, 177);
            if (var4 != null) {
               ListIterator var5 = var2.instructions.iterator();

               while(var5.hasNext()) {
                  AbstractInsnNode var6 = (AbstractInsnNode)var5.next();
                  if (!(var6 instanceof LineNumberNode) && var6.getOpcode() != 177) {
                     var3.instructions.insertBefore(var4, var6);
                  }
               }

               var3.maxLocals = Math.max(var3.maxLocals, var2.maxLocals);
               var3.maxStack = Math.max(var3.maxStack, var2.maxStack);
            }

         }
      }
   }

   protected void applyInitialisers(MixinTargetContext var1) {
      MethodNode var2 = this.getConstructor(var1);
      if (var2 != null) {
         Deque var3 = this.getInitialiser(var1, var2);
         if (var3 != null && var3.size() != 0) {
            Iterator var4 = this.targetClass.methods.iterator();

            while(var4.hasNext()) {
               MethodNode var5 = (MethodNode)var4.next();
               if ("<init>".equals(var5.name)) {
                  var5.maxStack = Math.max(var5.maxStack, var2.maxStack);
                  this.injectInitialiser(var1, var5, var3);
               }
            }

         }
      }
   }

   protected MethodNode getConstructor(MixinTargetContext var1) {
      MethodNode var2 = null;
      Iterator var3 = var1.getMethods().iterator();

      while(var3.hasNext()) {
         MethodNode var4 = (MethodNode)var3.next();
         if ("<init>".equals(var4.name) && Bytecode.methodHasLineNumbers(var4)) {
            if (var2 == null) {
               var2 = var4;
            } else {
               this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", var1, var2.desc));
            }
         }
      }

      return var2;
   }

   private MixinApplicatorStandard.Range getConstructorRange(MethodNode var1) {
      boolean var2 = false;
      AbstractInsnNode var3 = null;
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      int var7 = -1;
      ListIterator var8 = var1.instructions.iterator();

      while(var8.hasNext()) {
         AbstractInsnNode var9 = (AbstractInsnNode)var8.next();
         if (var9 instanceof LineNumberNode) {
            var4 = ((LineNumberNode)var9).line;
            var2 = true;
         } else if (var9 instanceof MethodInsnNode) {
            if (var9.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)var9).name) && var7 == -1) {
               var7 = var1.instructions.indexOf(var9);
               var5 = var4;
            }
         } else if (var9.getOpcode() == 181) {
            var2 = false;
         } else if (var9.getOpcode() == 177) {
            if (var2) {
               var6 = var4;
            } else {
               var6 = var5;
               var3 = var9;
            }
         }
      }

      if (var3 != null) {
         LabelNode var10 = new LabelNode(new Label());
         var1.instructions.insertBefore(var3, (AbstractInsnNode)var10);
         var1.instructions.insertBefore(var3, (AbstractInsnNode)(new LineNumberNode(var5, var10)));
      }

      return new MixinApplicatorStandard.Range(this, var5, var6, var7);
   }

   protected final Deque getInitialiser(MixinTargetContext var1, MethodNode var2) {
      MixinApplicatorStandard.Range var3 = this.getConstructorRange(var2);
      if (!var3.isValid()) {
         return null;
      } else {
         boolean var4 = false;
         ArrayDeque var5 = new ArrayDeque();
         boolean var6 = false;
         short var7 = -1;
         LabelNode var8 = null;
         ListIterator var9 = var2.instructions.iterator(var3.marker);

         while(true) {
            while(true) {
               while(var9.hasNext()) {
                  AbstractInsnNode var10 = (AbstractInsnNode)var9.next();
                  if (var10 instanceof LineNumberNode) {
                     int var16 = ((LineNumberNode)var10).line;
                     AbstractInsnNode var18 = var2.instructions.get(var2.instructions.indexOf(var10) + 1);
                     if (var16 == var3.end && var18.getOpcode() != 177) {
                        var6 = true;
                        var7 = 177;
                     } else {
                        var6 = var3.excludes(var16);
                        var7 = -1;
                     }
                  } else if (var6) {
                     if (var8 != null) {
                        var5.add(var8);
                        var8 = null;
                     }

                     if (var10 instanceof LabelNode) {
                        var8 = (LabelNode)var10;
                     } else {
                        int var11 = var10.getOpcode();
                        if (var11 == var7) {
                           var7 = -1;
                        } else {
                           int[] var12 = INITIALISER_OPCODE_BLACKLIST;
                           int var13 = var12.length;

                           for(int var14 = 0; var14 < var13; ++var14) {
                              int var15 = var12[var14];
                              if (var11 == var15) {
                                 throw new InvalidMixinException(var1, "Cannot handle " + Bytecode.getOpcodeName(var11) + " opcode (0x" + Integer.toHexString(var11).toUpperCase() + ") in class initialiser");
                              }
                           }

                           var5.add(var10);
                        }
                     }
                  }
               }

               AbstractInsnNode var17 = (AbstractInsnNode)var5.peekLast();
               if (var17 != null && var17.getOpcode() != 181) {
                  throw new InvalidMixinException(var1, "Could not parse initialiser, expected 0xB5, found 0x" + Integer.toHexString(var17.getOpcode()) + " in " + var1);
               }

               return var5;
            }
         }
      }
   }

   protected final void injectInitialiser(MixinTargetContext var1, MethodNode var2, Deque var3) {
      Map var4 = Bytecode.cloneLabels(var2.instructions);
      AbstractInsnNode var5 = this.findInitialiserInjectionPoint(var1, var2, var3);
      if (var5 == null) {
         this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[]{var2.desc});
      } else {
         Iterator var6 = var3.iterator();

         while(var6.hasNext()) {
            AbstractInsnNode var7 = (AbstractInsnNode)var6.next();
            if (!(var7 instanceof LabelNode)) {
               if (var7 instanceof JumpInsnNode) {
                  throw new InvalidMixinException(var1, "Unsupported JUMP opcode in initialiser in " + var1);
               }

               AbstractInsnNode var8 = var7.clone(var4);
               var2.instructions.insert(var5, var8);
               var5 = var8;
            }
         }

      }
   }

   protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext var1, MethodNode var2, Deque var3) {
      HashSet var4 = new HashSet();
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         AbstractInsnNode var6 = (AbstractInsnNode)var5.next();
         if (var6.getOpcode() == 181) {
            var4.add(fieldKey((FieldInsnNode)var6));
         }
      }

      MixinApplicatorStandard.InitialiserInjectionMode var12 = this.getInitialiserInjectionMode(var1.getEnvironment());
      String var13 = var1.getTargetClassInfo().getName();
      String var7 = var1.getTargetClassInfo().getSuperName();
      AbstractInsnNode var8 = null;
      ListIterator var9 = var2.instructions.iterator();

      while(var9.hasNext()) {
         AbstractInsnNode var10 = (AbstractInsnNode)var9.next();
         String var11;
         if (var10.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)var10).name)) {
            var11 = ((MethodInsnNode)var10).owner;
            if (var11.equals(var13) || var11.equals(var7)) {
               var8 = var10;
               if (var12 == MixinApplicatorStandard.InitialiserInjectionMode.SAFE) {
                  break;
               }
            }
         } else if (var10.getOpcode() == 181 && var12 == MixinApplicatorStandard.InitialiserInjectionMode.DEFAULT) {
            var11 = fieldKey((FieldInsnNode)var10);
            if (var4.contains(var11)) {
               var8 = var10;
            }
         }
      }

      return var8;
   }

   private MixinApplicatorStandard.InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment var1) {
      String var2 = var1.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
      if (var2 == null) {
         return MixinApplicatorStandard.InitialiserInjectionMode.DEFAULT;
      } else {
         try {
            return MixinApplicatorStandard.InitialiserInjectionMode.valueOf(var2.toUpperCase());
         } catch (Exception var4) {
            this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[]{var2});
            return MixinApplicatorStandard.InitialiserInjectionMode.DEFAULT;
         }
      }
   }

   private static String fieldKey(FieldInsnNode var0) {
      return String.format("%s:%s", var0.desc, var0.name);
   }

   protected void prepareInjections(MixinTargetContext var1) {
      var1.prepareInjections();
   }

   protected void applyInjections(MixinTargetContext var1) {
      var1.applyInjections();
   }

   protected void applyAccessors(MixinTargetContext var1) {
      List var2 = var1.generateAccessors();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         MethodNode var4 = (MethodNode)var3.next();
         if (!var4.name.startsWith("<")) {
            this.mergeMethod(var1, var4);
         }
      }

   }

   protected void checkMethodVisibility(MixinTargetContext var1, MethodNode var2) {
      if (Bytecode.hasFlag((MethodNode)var2, 8) && !Bytecode.hasFlag((MethodNode)var2, 2) && !Bytecode.hasFlag((MethodNode)var2, 4096) && Annotations.getVisible(var2, Overwrite.class) == null) {
         throw new InvalidMixinException(var1, String.format("Mixin %s contains non-private static method %s", var1, var2));
      }
   }

   protected void applySourceMap(TargetClassContext var1) {
      this.targetClass.sourceDebug = var1.getSourceMap().toString();
   }

   protected void checkMethodConstraints(MixinTargetContext var1, MethodNode var2) {
      Iterator var3 = CONSTRAINED_ANNOTATIONS.iterator();

      while(var3.hasNext()) {
         Class var4 = (Class)var3.next();
         AnnotationNode var5 = Annotations.getVisible(var2, var4);
         if (var5 != null) {
            this.checkConstraints(var1, var2, var5);
         }
      }

   }

   protected final void checkConstraints(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      try {
         ConstraintParser.Constraint var4 = ConstraintParser.parse(var3);

         try {
            var4.check(var1.getEnvironment());
         } catch (ConstraintViolationException var7) {
            String var6 = String.format("Constraint violation: %s on %s in %s", var7.getMessage(), var2, var1);
            this.logger.warn(var6);
            if (!var1.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
               throw new InvalidMixinException(var1, var6, var7);
            }
         }

      } catch (InvalidConstraintException var8) {
         throw new InvalidMixinException(var1, var8.getMessage());
      }
   }

   protected final MethodNode findTargetMethod(MethodNode var1) {
      Iterator var2 = this.targetClass.methods.iterator();

      MethodNode var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (MethodNode)var2.next();
      } while(!var3.name.equals(var1.name) || !var3.desc.equals(var1.desc));

      return var3;
   }

   protected final FieldNode findTargetField(FieldNode var1) {
      Iterator var2 = this.targetClass.fields.iterator();

      FieldNode var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (FieldNode)var2.next();
      } while(!var3.name.equals(var1.name));

      return var3;
   }

   class Range {
      final int start;
      final int end;
      final int marker;
      final MixinApplicatorStandard this$0;

      Range(MixinApplicatorStandard var1, int var2, int var3, int var4) {
         this.this$0 = var1;
         this.start = var2;
         this.end = var3;
         this.marker = var4;
      }

      boolean isValid() {
         return this.start != 0 && this.end != 0 && this.end >= this.start;
      }

      boolean contains(int var1) {
         return var1 >= this.start && var1 <= this.end;
      }

      boolean excludes(int var1) {
         return var1 < this.start || var1 > this.end;
      }

      public String toString() {
         return String.format("Range[%d-%d,%d,valid=%s)", this.start, this.end, this.marker, this.isValid());
      }
   }

   static enum InitialiserInjectionMode {
      DEFAULT,
      SAFE;

      private static final MixinApplicatorStandard.InitialiserInjectionMode[] $VALUES = new MixinApplicatorStandard.InitialiserInjectionMode[]{DEFAULT, SAFE};
   }

   static enum ApplicatorPass {
      MAIN,
      PREINJECT,
      INJECT;

      private static final MixinApplicatorStandard.ApplicatorPass[] $VALUES = new MixinApplicatorStandard.ApplicatorPass[]{MAIN, PREINJECT, INJECT};
   }
}
