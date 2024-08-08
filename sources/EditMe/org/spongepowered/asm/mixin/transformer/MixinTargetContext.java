package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;

public class MixinTargetContext extends ClassContext implements IMixinContext {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final MixinInfo mixin;
   private final ClassNode classNode;
   private final TargetClassContext targetClass;
   private final String sessionId;
   private final ClassInfo targetClassInfo;
   private final BiMap innerClasses = HashBiMap.create();
   private final List shadowMethods = new ArrayList();
   private final Map shadowFields = new LinkedHashMap();
   private final List mergedMethods = new ArrayList();
   private final InjectorGroupInfo.Map injectorGroups = new InjectorGroupInfo.Map();
   private final List injectors = new ArrayList();
   private final List accessors = new ArrayList();
   private final boolean inheritsFromMixin;
   private final boolean detachedSuper;
   private final SourceMap.File stratum;
   private int minRequiredClassVersion;

   MixinTargetContext(MixinInfo var1, ClassNode var2, TargetClassContext var3) {
      this.minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
      this.mixin = var1;
      this.classNode = var2;
      this.targetClass = var3;
      this.targetClassInfo = ClassInfo.forName(this.getTarget().getClassRef());
      this.stratum = var3.getSourceMap().addFile(this.classNode);
      this.inheritsFromMixin = var1.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy();
      this.detachedSuper = !this.classNode.superName.equals(this.getTarget().getClassNode().superName);
      this.sessionId = var3.getSessionId();
      this.requireVersion(var2.version);
      InnerClassGenerator var4 = (InnerClassGenerator)var3.getExtensions().getGenerator(InnerClassGenerator.class);
      Iterator var5 = this.mixin.getInnerClasses().iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         this.innerClasses.put(var6, var4.registerInnerClass(this.mixin, var6, this));
      }

   }

   void addShadowMethod(MethodNode var1) {
      this.shadowMethods.add(var1);
   }

   void addShadowField(FieldNode var1, ClassInfo.Field var2) {
      this.shadowFields.put(var1, var2);
   }

   void addAccessorMethod(MethodNode var1, Class var2) {
      this.accessors.add(AccessorInfo.of(this, var1, var2));
   }

   void addMixinMethod(MethodNode var1) {
      Annotations.setVisible(var1, MixinMerged.class, "mixin", this.getClassName());
      this.getTarget().addMixinMethod(var1);
   }

   void methodMerged(MethodNode var1) {
      this.mergedMethods.add(var1);
      this.targetClassInfo.addMethod(var1);
      this.getTarget().methodMerged(var1);
      Annotations.setVisible(var1, MixinMerged.class, "mixin", this.getClassName(), "priority", this.getPriority(), "sessionId", this.sessionId);
   }

   public String toString() {
      return this.mixin.toString();
   }

   public MixinEnvironment getEnvironment() {
      return this.mixin.getParent().getEnvironment();
   }

   public boolean getOption(MixinEnvironment.Option var1) {
      return this.getEnvironment().getOption(var1);
   }

   public ClassNode getClassNode() {
      return this.classNode;
   }

   public String getClassName() {
      return this.mixin.getClassName();
   }

   public String getClassRef() {
      return this.mixin.getClassRef();
   }

   public TargetClassContext getTarget() {
      return this.targetClass;
   }

   public String getTargetClassRef() {
      return this.getTarget().getClassRef();
   }

   public ClassNode getTargetClassNode() {
      return this.getTarget().getClassNode();
   }

   public ClassInfo getTargetClassInfo() {
      return this.targetClassInfo;
   }

   protected ClassInfo getClassInfo() {
      return this.mixin.getClassInfo();
   }

   public ClassSignature getSignature() {
      return this.getClassInfo().getSignature();
   }

   public SourceMap.File getStratum() {
      return this.stratum;
   }

   public int getMinRequiredClassVersion() {
      return this.minRequiredClassVersion;
   }

   public int getDefaultRequiredInjections() {
      return this.mixin.getParent().getDefaultRequiredInjections();
   }

   public String getDefaultInjectorGroup() {
      return this.mixin.getParent().getDefaultInjectorGroup();
   }

   public int getMaxShiftByValue() {
      return this.mixin.getParent().getMaxShiftByValue();
   }

   public InjectorGroupInfo.Map getInjectorGroups() {
      return this.injectorGroups;
   }

   public boolean requireOverwriteAnnotations() {
      return this.mixin.getParent().requireOverwriteAnnotations();
   }

   public ClassInfo findRealType(ClassInfo var1) {
      if (var1 == this.getClassInfo()) {
         return this.targetClassInfo;
      } else {
         ClassInfo var2 = this.targetClassInfo.findCorrespondingType(var1);
         if (var2 == null) {
            throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + var1 + " in hierarchy of " + this.targetClassInfo);
         } else {
            return var2;
         }
      }
   }

   public void transformMethod(MethodNode var1) {
      this.validateMethod(var1);
      this.transformDescriptor(var1);
      this.transformLVT(var1);
      this.stratum.applyOffset(var1);
      AbstractInsnNode var2 = null;

      AbstractInsnNode var4;
      for(ListIterator var3 = var1.instructions.iterator(); var3.hasNext(); var2 = var4) {
         var4 = (AbstractInsnNode)var3.next();
         if (var4 instanceof MethodInsnNode) {
            this.transformMethodRef(var1, var3, new MemberRef.Method((MethodInsnNode)var4));
         } else if (var4 instanceof FieldInsnNode) {
            this.transformFieldRef(var1, var3, new MemberRef.Field((FieldInsnNode)var4));
            this.checkFinal(var1, var3, (FieldInsnNode)var4);
         } else if (var4 instanceof TypeInsnNode) {
            this.transformTypeNode(var1, var3, (TypeInsnNode)var4, var2);
         } else if (var4 instanceof LdcInsnNode) {
            this.transformConstantNode(var1, var3, (LdcInsnNode)var4);
         } else if (var4 instanceof InvokeDynamicInsnNode) {
            this.transformInvokeDynamicNode(var1, var3, (InvokeDynamicInsnNode)var4);
         }
      }

   }

   private void validateMethod(MethodNode var1) {
      if (Annotations.getInvisible(var1, SoftOverride.class) != null) {
         ClassInfo.Method var2 = this.targetClassInfo.findMethodInHierarchy(var1.name, var1.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
         if (var2 == null || !var2.isInjected()) {
            throw new InvalidMixinException(this, "Mixin method " + var1.name + var1.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + this.getTarget().getClassName());
         }
      }

   }

   private void transformLVT(MethodNode var1) {
      if (var1.localVariables != null) {
         Iterator var2 = var1.localVariables.iterator();

         while(var2.hasNext()) {
            LocalVariableNode var3 = (LocalVariableNode)var2.next();
            if (var3 != null && var3.desc != null) {
               var3.desc = this.transformSingleDescriptor(Type.getType(var3.desc));
            }
         }

      }
   }

   private void transformMethodRef(MethodNode var1, Iterator var2, MemberRef var3) {
      this.transformDescriptor(var3);
      if (var3.getOwner().equals(this.getClassRef())) {
         var3.setOwner(this.getTarget().getClassRef());
         ClassInfo.Method var4 = this.getClassInfo().findMethod(var3.getName(), var3.getDesc(), 10);
         if (var4 != null && var4.isRenamed() && var4.getOriginalName().equals(var3.getName()) && var4.isSynthetic()) {
            var3.setName(var4.getName());
         }

         this.upgradeMethodRef(var1, var3, var4);
      } else if (this.innerClasses.containsKey(var3.getOwner())) {
         var3.setOwner((String)this.innerClasses.get(var3.getOwner()));
         var3.setDesc(this.transformMethodDescriptor(var3.getDesc()));
      } else if (this.detachedSuper || this.inheritsFromMixin) {
         if (var3.getOpcode() == 183) {
            this.updateStaticBinding(var1, var3);
         } else if (var3.getOpcode() == 182 && ClassInfo.forName(var3.getOwner()).isMixin()) {
            this.updateDynamicBinding(var1, var3);
         }
      }

   }

   private void transformFieldRef(MethodNode var1, Iterator var2, MemberRef var3) {
      if ("super$".equals(var3.getName())) {
         if (!(var3 instanceof MemberRef.Field)) {
            throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
         }

         this.processImaginarySuper(var1, ((MemberRef.Field)var3).insn);
         var2.remove();
      }

      this.transformDescriptor(var3);
      if (var3.getOwner().equals(this.getClassRef())) {
         var3.setOwner(this.getTarget().getClassRef());
         ClassInfo.Field var4 = this.getClassInfo().findField(var3.getName(), var3.getDesc(), 10);
         if (var4 != null && var4.isRenamed() && var4.getOriginalName().equals(var3.getName()) && var4.isStatic()) {
            var3.setName(var4.getName());
         }
      } else {
         ClassInfo var6 = ClassInfo.forName(var3.getOwner());
         if (var6.isMixin()) {
            ClassInfo var5 = this.targetClassInfo.findCorrespondingType(var6);
            var3.setOwner(var5 != null ? var5.getName() : this.getTarget().getClassRef());
         }
      }

   }

   private void checkFinal(MethodNode var1, Iterator var2, FieldInsnNode var3) {
      if (var3.owner.equals(this.getTarget().getClassRef())) {
         int var4 = var3.getOpcode();
         if (var4 != 180 && var4 != 178) {
            Iterator var5 = this.shadowFields.entrySet().iterator();

            Entry var6;
            FieldNode var7;
            do {
               if (!var5.hasNext()) {
                  return;
               }

               var6 = (Entry)var5.next();
               var7 = (FieldNode)var6.getKey();
            } while(!var7.desc.equals(var3.desc) || !var7.name.equals(var3.name));

            ClassInfo.Field var8 = (ClassInfo.Field)var6.getValue();
            if (var8.isDecoratedFinal()) {
               if (var8.isDecoratedMutable()) {
                  if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                     logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[]{var8, this.mixin, var1.name});
                  }
               } else if (!"<init>".equals(var1.name) && !"<clinit>".equals(var1.name)) {
                  logger.error("Write access detected to @Final field {} in {}::{}", new Object[]{var8, this.mixin, var1.name});
                  if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                     throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + var8 + " in " + this.mixin + "::" + var1.name);
                  }
               } else {
                  logger.warn("@Final field {} in {} should be final", new Object[]{var8, this.mixin});
               }
            }

         }
      }
   }

   private void transformTypeNode(MethodNode var1, Iterator var2, TypeInsnNode var3, AbstractInsnNode var4) {
      if (var3.getOpcode() == 192 && var3.desc.equals(this.getTarget().getClassRef()) && var4.getOpcode() == 25 && ((VarInsnNode)var4).var == 0) {
         var2.remove();
      } else {
         if (var3.desc.equals(this.getClassRef())) {
            var3.desc = this.getTarget().getClassRef();
         } else {
            String var5 = (String)this.innerClasses.get(var3.desc);
            if (var5 != null) {
               var3.desc = var5;
            }
         }

         this.transformDescriptor(var3);
      }
   }

   private void transformConstantNode(MethodNode var1, Iterator var2, LdcInsnNode var3) {
      var3.cst = this.transformConstant(var1, var2, var3.cst);
   }

   private void transformInvokeDynamicNode(MethodNode var1, Iterator var2, InvokeDynamicInsnNode var3) {
      this.requireVersion(51);
      var3.desc = this.transformMethodDescriptor(var3.desc);
      var3.bsm = this.transformHandle(var1, var2, var3.bsm);

      for(int var4 = 0; var4 < var3.bsmArgs.length; ++var4) {
         var3.bsmArgs[var4] = this.transformConstant(var1, var2, var3.bsmArgs[var4]);
      }

   }

   private Object transformConstant(MethodNode var1, Iterator var2, Object var3) {
      if (var3 instanceof Type) {
         Type var4 = (Type)var3;
         String var5 = this.transformDescriptor(var4);
         return !var4.toString().equals(var5) ? Type.getType(var5) : var3;
      } else {
         return var3 instanceof Handle ? this.transformHandle(var1, var2, (Handle)var3) : var3;
      }
   }

   private Handle transformHandle(MethodNode var1, Iterator var2, Handle var3) {
      MemberRef.Handle var4 = new MemberRef.Handle(var3);
      if (var4.isField()) {
         this.transformFieldRef(var1, var2, var4);
      } else {
         this.transformMethodRef(var1, var2, var4);
      }

      return var4.getMethodHandle();
   }

   private void processImaginarySuper(MethodNode var1, FieldInsnNode var2) {
      if (var2.getOpcode() != 180) {
         if ("<init>".equals(var1.name)) {
            throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + var2.name + " must not specify an initialiser");
         } else {
            throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(var2.getOpcode()) + " opcode in " + var1.name + var1.desc);
         }
      } else if ((var1.access & 2) == 0 && (var1.access & 8) == 0) {
         if (Annotations.getInvisible(var1, SoftOverride.class) == null) {
            throw new InvalidMixinException(this, "Illegal imaginary super access: method " + var1.name + var1.desc + " is not decorated with @SoftOverride");
         } else {
            ListIterator var3 = var1.instructions.iterator(var1.instructions.indexOf(var2));

            while(var3.hasNext()) {
               AbstractInsnNode var4 = (AbstractInsnNode)var3.next();
               if (var4 instanceof MethodInsnNode) {
                  MethodInsnNode var5 = (MethodInsnNode)var4;
                  if (var5.owner.equals(this.getClassRef()) && var5.name.equals(var1.name) && var5.desc.equals(var1.desc)) {
                     var5.setOpcode(183);
                     this.updateStaticBinding(var1, new MemberRef.Method(var5));
                     return;
                  }
               }
            }

            throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + var1.name + var1.desc);
         }
      } else {
         throw new InvalidMixinException(this, "Illegal imaginary super access: method " + var1.name + var1.desc + " is private or static");
      }
   }

   private void updateStaticBinding(MethodNode var1, MemberRef var2) {
      this.updateBinding(var1, var2, ClassInfo.Traversal.SUPER);
   }

   private void updateDynamicBinding(MethodNode var1, MemberRef var2) {
      this.updateBinding(var1, var2, ClassInfo.Traversal.ALL);
   }

   private void updateBinding(MethodNode var1, MemberRef var2, ClassInfo.Traversal var3) {
      if (!"<init>".equals(var1.name) && !var2.getOwner().equals(this.getTarget().getClassRef()) && !this.getTarget().getClassRef().startsWith("<")) {
         ClassInfo.Method var4 = this.targetClassInfo.findMethodInHierarchy(var2.getName(), var2.getDesc(), var3.getSearchType(), var3);
         if (var4 != null) {
            if (var4.getOwner().isMixin()) {
               throw new InvalidMixinException(this, "Invalid " + var2 + " in " + this + " resolved " + var4.getOwner() + " but is mixin.");
            }

            var2.setOwner(var4.getImplementor().getName());
         } else if (ClassInfo.forName(var2.getOwner()).isMixin()) {
            throw new MixinTransformerError("Error resolving " + var2 + " in " + this);
         }

      }
   }

   public void transformDescriptor(FieldNode var1) {
      if (this.inheritsFromMixin || this.innerClasses.size() != 0) {
         var1.desc = this.transformSingleDescriptor(var1.desc, false);
      }
   }

   public void transformDescriptor(MethodNode var1) {
      if (this.inheritsFromMixin || this.innerClasses.size() != 0) {
         var1.desc = this.transformMethodDescriptor(var1.desc);
      }
   }

   public void transformDescriptor(MemberRef var1) {
      if (this.inheritsFromMixin || this.innerClasses.size() != 0) {
         if (var1.isField()) {
            var1.setDesc(this.transformSingleDescriptor(var1.getDesc(), false));
         } else {
            var1.setDesc(this.transformMethodDescriptor(var1.getDesc()));
         }

      }
   }

   public void transformDescriptor(TypeInsnNode var1) {
      if (this.inheritsFromMixin || this.innerClasses.size() != 0) {
         var1.desc = this.transformSingleDescriptor(var1.desc, true);
      }
   }

   private String transformDescriptor(Type var1) {
      return var1.getSort() == 11 ? this.transformMethodDescriptor(var1.getDescriptor()) : this.transformSingleDescriptor(var1);
   }

   private String transformSingleDescriptor(Type var1) {
      return var1.getSort() < 9 ? var1.toString() : this.transformSingleDescriptor(var1.toString(), false);
   }

   private String transformSingleDescriptor(String var1, boolean var2) {
      String var3 = var1;

      while(var3.startsWith("[") || var3.startsWith("L")) {
         if (var3.startsWith("[")) {
            var3 = var3.substring(1);
         } else {
            var3 = var3.substring(1, var3.indexOf(";"));
            var2 = true;
         }
      }

      if (!var2) {
         return var1;
      } else {
         String var4 = (String)this.innerClasses.get(var3);
         if (var4 != null) {
            return var1.replace(var3, var4);
         } else if (this.innerClasses.inverse().containsKey(var3)) {
            return var1;
         } else {
            ClassInfo var5 = ClassInfo.forName(var3);
            if (!var5.isMixin()) {
               return var1;
            } else {
               return var1.replace(var3, this.findRealType(var5).toString());
            }
         }
      }
   }

   private String transformMethodDescriptor(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append('(');
      Type[] var3 = Type.getArgumentTypes(var1);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type var6 = var3[var5];
         var2.append(this.transformSingleDescriptor(var6));
      }

      return var2.append(')').append(this.transformSingleDescriptor(Type.getReturnType(var1))).toString();
   }

   public Target getTargetMethod(MethodNode var1) {
      return this.getTarget().getTargetMethod(var1);
   }

   MethodNode findMethod(MethodNode var1, AnnotationNode var2) {
      LinkedList var3 = new LinkedList();
      var3.add(var1.name);
      if (var2 != null) {
         List var4 = (List)Annotations.getValue(var2, "aliases");
         if (var4 != null) {
            var3.addAll(var4);
         }
      }

      return this.getTarget().findMethod(var3, var1.desc);
   }

   MethodNode findRemappedMethod(MethodNode var1) {
      RemapperChain var2 = this.getEnvironment().getRemappers();
      String var3 = var2.mapMethodName(this.getTarget().getClassRef(), var1.name, var1.desc);
      if (var3.equals(var1.name)) {
         return null;
      } else {
         LinkedList var4 = new LinkedList();
         var4.add(var3);
         return this.getTarget().findAliasedMethod(var4, var1.desc);
      }
   }

   FieldNode findField(FieldNode var1, AnnotationNode var2) {
      LinkedList var3 = new LinkedList();
      var3.add(var1.name);
      if (var2 != null) {
         List var4 = (List)Annotations.getValue(var2, "aliases");
         if (var4 != null) {
            var3.addAll(var4);
         }
      }

      return this.getTarget().findAliasedField(var3, var1.desc);
   }

   FieldNode findRemappedField(FieldNode var1) {
      RemapperChain var2 = this.getEnvironment().getRemappers();
      String var3 = var2.mapFieldName(this.getTarget().getClassRef(), var1.name, var1.desc);
      if (var3.equals(var1.name)) {
         return null;
      } else {
         LinkedList var4 = new LinkedList();
         var4.add(var3);
         return this.getTarget().findAliasedField(var4, var1.desc);
      }
   }

   protected void requireVersion(int var1) {
      this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, var1);
      if (var1 > MixinEnvironment.getCompatibilityLevel().classVersion()) {
         throw new InvalidMixinException(this, "Unsupported mixin class version " + var1);
      }
   }

   public Extensions getExtensions() {
      return this.targetClass.getExtensions();
   }

   public IMixinInfo getMixin() {
      return this.mixin;
   }

   MixinInfo getInfo() {
      return this.mixin;
   }

   public int getPriority() {
      return this.mixin.getPriority();
   }

   public Set getInterfaces() {
      return this.mixin.getInterfaces();
   }

   public Collection getShadowMethods() {
      return this.shadowMethods;
   }

   public List getMethods() {
      return this.classNode.methods;
   }

   public Set getShadowFields() {
      return this.shadowFields.entrySet();
   }

   public List getFields() {
      return this.classNode.fields;
   }

   public Level getLoggingLevel() {
      return this.mixin.getLoggingLevel();
   }

   public boolean shouldSetSourceFile() {
      return this.mixin.getParent().shouldSetSourceFile();
   }

   public String getSourceFile() {
      return this.classNode.sourceFile;
   }

   public IReferenceMapper getReferenceMapper() {
      return this.mixin.getParent().getReferenceMapper();
   }

   public void preApply(String var1, ClassNode var2) {
      this.mixin.preApply(var1, var2);
   }

   public void postApply(String var1, ClassNode var2) {
      try {
         this.injectorGroups.validateAll();
      } catch (InjectionValidationException var5) {
         InjectorGroupInfo var4 = var5.getGroup();
         throw new InjectionError(String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", var4, this.mixin, var5.getMessage()));
      }

      this.mixin.postApply(var1, var2);
   }

   public String getUniqueName(MethodNode var1, boolean var2) {
      return this.getTarget().getUniqueName(var1, var2);
   }

   public String getUniqueName(FieldNode var1) {
      return this.getTarget().getUniqueName(var1);
   }

   public void prepareInjections() {
      this.injectors.clear();
      Iterator var1 = this.mergedMethods.iterator();

      while(var1.hasNext()) {
         MethodNode var2 = (MethodNode)var1.next();
         InjectionInfo var3 = InjectionInfo.parse(this, var2);
         if (var3 != null) {
            if (var3.isValid()) {
               var3.prepare();
               this.injectors.add(var3);
            }

            var2.visibleAnnotations.remove(var3.getAnnotation());
         }
      }

   }

   public void applyInjections() {
      Iterator var1 = this.injectors.iterator();

      InjectionInfo var2;
      while(var1.hasNext()) {
         var2 = (InjectionInfo)var1.next();
         var2.inject();
      }

      var1 = this.injectors.iterator();

      while(var1.hasNext()) {
         var2 = (InjectionInfo)var1.next();
         var2.postInject();
      }

      this.injectors.clear();
   }

   public List generateAccessors() {
      Iterator var1 = this.accessors.iterator();

      while(var1.hasNext()) {
         AccessorInfo var2 = (AccessorInfo)var1.next();
         var2.locate();
      }

      ArrayList var5 = new ArrayList();
      Iterator var6 = this.accessors.iterator();

      while(var6.hasNext()) {
         AccessorInfo var3 = (AccessorInfo)var6.next();
         MethodNode var4 = var3.generate();
         this.getTarget().addMixinMethod(var4);
         var5.add(var4);
      }

      return var5;
   }
}
