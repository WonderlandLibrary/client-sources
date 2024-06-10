/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Functions;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.io.IOException;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.spongepowered.asm.lib.ClassReader;
/*      */ import org.spongepowered.asm.lib.ClassVisitor;
/*      */ import org.spongepowered.asm.lib.MethodVisitor;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldNode;
/*      */ import org.spongepowered.asm.lib.tree.InnerClassNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.mixin.Implements;
/*      */ import org.spongepowered.asm.mixin.Mixin;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.Overwrite;
/*      */ import org.spongepowered.asm.mixin.Pseudo;
/*      */ import org.spongepowered.asm.mixin.Shadow;
/*      */ import org.spongepowered.asm.mixin.Unique;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*      */ import org.spongepowered.asm.mixin.injection.Surrogate;
/*      */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTargetAlreadyLoadedException;
/*      */ import org.spongepowered.asm.service.IMixinService;
/*      */ import org.spongepowered.asm.service.MixinService;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.Bytecode;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class MixinInfo
/*      */   implements Comparable<MixinInfo>, IMixinInfo
/*      */ {
/*      */   class MixinMethodNode
/*      */     extends MethodNode
/*      */   {
/*      */     private final String originalName;
/*      */     
/*      */     public MixinMethodNode(int access, String name, String desc, String signature, String[] exceptions) {
/*   90 */       super(327680, access, name, desc, signature, exceptions);
/*   91 */       this.originalName = name;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*   96 */       return String.format("%s%s", new Object[] { this.originalName, this.desc });
/*      */     }
/*      */     
/*      */     public String getOriginalName() {
/*  100 */       return this.originalName;
/*      */     }
/*      */     
/*      */     public boolean isInjector() {
/*  104 */       return (getInjectorAnnotation() != null || isSurrogate());
/*      */     }
/*      */     
/*      */     public boolean isSurrogate() {
/*  108 */       return (getVisibleAnnotation((Class)Surrogate.class) != null);
/*      */     }
/*      */     
/*      */     public boolean isSynthetic() {
/*  112 */       return Bytecode.hasFlag(this, 4096);
/*      */     }
/*      */     
/*      */     public AnnotationNode getVisibleAnnotation(Class<? extends Annotation> annotationClass) {
/*  116 */       return Annotations.getVisible(this, annotationClass);
/*      */     }
/*      */     
/*      */     public AnnotationNode getInjectorAnnotation() {
/*  120 */       return InjectionInfo.getInjectorAnnotation(MixinInfo.this, this);
/*      */     }
/*      */     
/*      */     public IMixinInfo getOwner() {
/*  124 */       return MixinInfo.this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class MixinClassNode
/*      */     extends ClassNode
/*      */   {
/*      */     public final List<MixinInfo.MixinMethodNode> mixinMethods;
/*      */ 
/*      */     
/*      */     public MixinClassNode(MixinInfo mixin) {
/*  137 */       this(327680);
/*      */     }
/*      */ 
/*      */     
/*      */     public MixinClassNode(int api) {
/*  142 */       super(api);
/*  143 */       this.mixinMethods = this.methods;
/*      */     }
/*      */     
/*      */     public MixinInfo getMixin() {
/*  147 */       return MixinInfo.this;
/*      */     }
/*      */ 
/*      */     
/*      */     public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/*  152 */       MethodNode method = new MixinInfo.MixinMethodNode(access, name, desc, signature, exceptions);
/*  153 */       this.methods.add(method);
/*  154 */       return (MethodVisitor)method;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class State
/*      */   {
/*      */     private byte[] mixinBytes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final ClassInfo classInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean detachedSuper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean unique;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  189 */     protected final Set<String> interfaces = new HashSet<String>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  194 */     protected final List<InterfaceInfo> softImplements = new ArrayList<InterfaceInfo>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  199 */     protected final Set<String> syntheticInnerClasses = new HashSet<String>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  204 */     protected final Set<String> innerClasses = new HashSet<String>();
/*      */ 
/*      */ 
/*      */     
/*      */     protected MixinInfo.MixinClassNode classNode;
/*      */ 
/*      */ 
/*      */     
/*      */     State(byte[] mixinBytes) {
/*  213 */       this(mixinBytes, null);
/*      */     }
/*      */     
/*      */     State(byte[] mixinBytes, ClassInfo classInfo) {
/*  217 */       this.mixinBytes = mixinBytes;
/*  218 */       connect();
/*  219 */       this.classInfo = (classInfo != null) ? classInfo : ClassInfo.fromClassNode(getClassNode());
/*      */     }
/*      */     
/*      */     private void connect() {
/*  223 */       this.classNode = createClassNode(0);
/*      */     }
/*      */     
/*      */     private void complete() {
/*  227 */       this.classNode = null;
/*      */     }
/*      */     
/*      */     ClassInfo getClassInfo() {
/*  231 */       return this.classInfo;
/*      */     }
/*      */     
/*      */     byte[] getClassBytes() {
/*  235 */       return this.mixinBytes;
/*      */     }
/*      */     
/*      */     MixinInfo.MixinClassNode getClassNode() {
/*  239 */       return this.classNode;
/*      */     }
/*      */     
/*      */     boolean isDetachedSuper() {
/*  243 */       return this.detachedSuper;
/*      */     }
/*      */     
/*      */     boolean isUnique() {
/*  247 */       return this.unique;
/*      */     }
/*      */     
/*      */     List<? extends InterfaceInfo> getSoftImplements() {
/*  251 */       return this.softImplements;
/*      */     }
/*      */     
/*      */     Set<String> getSyntheticInnerClasses() {
/*  255 */       return this.syntheticInnerClasses;
/*      */     }
/*      */     
/*      */     Set<String> getInnerClasses() {
/*  259 */       return this.innerClasses;
/*      */     }
/*      */     
/*      */     Set<String> getInterfaces() {
/*  263 */       return this.interfaces;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MixinInfo.MixinClassNode createClassNode(int flags) {
/*  273 */       MixinInfo.MixinClassNode classNode = new MixinInfo.MixinClassNode(MixinInfo.this);
/*  274 */       ClassReader classReader = new ClassReader(this.mixinBytes);
/*  275 */       classReader.accept((ClassVisitor)classNode, flags);
/*  276 */       return classNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void validate(MixinInfo.SubType type, List<ClassInfo> targetClasses) {
/*  286 */       MixinPreProcessorStandard preProcessor = type.createPreProcessor(getClassNode()).prepare();
/*  287 */       for (ClassInfo target : targetClasses) {
/*  288 */         preProcessor.conform(target);
/*      */       }
/*      */       
/*  291 */       type.validate(this, targetClasses);
/*      */       
/*  293 */       this.detachedSuper = type.isDetachedSuper();
/*  294 */       this.unique = (Annotations.getVisible(getClassNode(), Unique.class) != null);
/*      */ 
/*      */       
/*  297 */       validateInner();
/*  298 */       validateClassVersion();
/*  299 */       validateRemappables(targetClasses);
/*      */ 
/*      */       
/*  302 */       readImplementations(type);
/*  303 */       readInnerClasses();
/*      */ 
/*      */       
/*  306 */       validateChanges(type, targetClasses);
/*      */ 
/*      */       
/*  309 */       complete();
/*      */     }
/*      */ 
/*      */     
/*      */     private void validateInner() {
/*  314 */       if (!this.classInfo.isProbablyStatic()) {
/*  315 */         throw new InvalidMixinException(MixinInfo.this, "Inner class mixin must be declared static");
/*      */       }
/*      */     }
/*      */     
/*      */     private void validateClassVersion() {
/*  320 */       if (this.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
/*  321 */         String helpText = ".";
/*  322 */         for (MixinEnvironment.CompatibilityLevel level : MixinEnvironment.CompatibilityLevel.values()) {
/*  323 */           if (level.classVersion() >= this.classNode.version) {
/*  324 */             helpText = String.format(". Mixin requires compatibility level %s or above.", new Object[] { level.name() });
/*      */           }
/*      */         } 
/*      */         
/*  328 */         throw new InvalidMixinException(MixinInfo.this, "Unsupported mixin class version " + this.classNode.version + helpText);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void validateRemappables(List<ClassInfo> targetClasses) {
/*  334 */       if (targetClasses.size() > 1) {
/*  335 */         for (FieldNode field : this.classNode.fields) {
/*  336 */           validateRemappable(Shadow.class, field.name, Annotations.getVisible(field, Shadow.class));
/*      */         }
/*      */         
/*  339 */         for (MethodNode method : this.classNode.methods) {
/*  340 */           validateRemappable(Shadow.class, method.name, Annotations.getVisible(method, Shadow.class));
/*  341 */           AnnotationNode overwrite = Annotations.getVisible(method, Overwrite.class);
/*  342 */           if (overwrite != null && ((method.access & 0x8) == 0 || (method.access & 0x1) == 0)) {
/*  343 */             throw new InvalidMixinException(MixinInfo.this, "Found @Overwrite annotation on " + method.name + " in " + MixinInfo.this);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void validateRemappable(Class<Shadow> annotationClass, String name, AnnotationNode annotation) {
/*  350 */       if (annotation != null && ((Boolean)Annotations.getValue(annotation, "remap", Boolean.TRUE)).booleanValue()) {
/*  351 */         throw new InvalidMixinException(MixinInfo.this, "Found a remappable @" + annotationClass.getSimpleName() + " annotation on " + name + " in " + this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void readImplementations(MixinInfo.SubType type) {
/*  360 */       this.interfaces.addAll(this.classNode.interfaces);
/*  361 */       this.interfaces.addAll(type.getInterfaces());
/*      */       
/*  363 */       AnnotationNode implementsAnnotation = Annotations.getInvisible(this.classNode, Implements.class);
/*  364 */       if (implementsAnnotation == null) {
/*      */         return;
/*      */       }
/*      */       
/*  368 */       List<AnnotationNode> interfaces = (List<AnnotationNode>)Annotations.getValue(implementsAnnotation);
/*  369 */       if (interfaces == null) {
/*      */         return;
/*      */       }
/*      */       
/*  373 */       for (AnnotationNode interfaceNode : interfaces) {
/*  374 */         InterfaceInfo interfaceInfo = InterfaceInfo.fromAnnotation(MixinInfo.this, interfaceNode);
/*  375 */         this.softImplements.add(interfaceInfo);
/*  376 */         this.interfaces.add(interfaceInfo.getInternalName());
/*      */         
/*  378 */         if (!(this instanceof MixinInfo.Reloaded)) {
/*  379 */           this.classInfo.addInterface(interfaceInfo.getInternalName());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void readInnerClasses() {
/*  390 */       for (InnerClassNode inner : this.classNode.innerClasses) {
/*  391 */         ClassInfo innerClass = ClassInfo.forName(inner.name);
/*  392 */         if ((inner.outerName != null && inner.outerName.equals(this.classInfo.getName())) || inner.name
/*  393 */           .startsWith(this.classNode.name + "$")) {
/*  394 */           if (innerClass.isProbablyStatic() && innerClass.isSynthetic()) {
/*  395 */             this.syntheticInnerClasses.add(inner.name); continue;
/*      */           } 
/*  397 */           this.innerClasses.add(inner.name);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void validateChanges(MixinInfo.SubType type, List<ClassInfo> targetClasses) {
/*  404 */       type.createPreProcessor(this.classNode).prepare();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class Reloaded
/*      */     extends State
/*      */   {
/*      */     private final MixinInfo.State previous;
/*      */ 
/*      */ 
/*      */     
/*      */     Reloaded(MixinInfo.State previous, byte[] mixinBytes) {
/*  419 */       super(mixinBytes, previous.getClassInfo());
/*  420 */       this.previous = previous;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void validateChanges(MixinInfo.SubType type, List<ClassInfo> targetClasses) {
/*  429 */       if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
/*  430 */         throw new MixinReloadException(MixinInfo.this, "Cannot change inner classes");
/*      */       }
/*  432 */       if (!this.interfaces.equals(this.previous.interfaces)) {
/*  433 */         throw new MixinReloadException(MixinInfo.this, "Cannot change interfaces");
/*      */       }
/*  435 */       if (!(new HashSet(this.softImplements)).equals(new HashSet<InterfaceInfo>(this.previous.softImplements))) {
/*  436 */         throw new MixinReloadException(MixinInfo.this, "Cannot change soft interfaces");
/*      */       }
/*  438 */       List<ClassInfo> targets = MixinInfo.this.readTargetClasses(this.classNode, true);
/*  439 */       if (!(new HashSet(targets)).equals(new HashSet<ClassInfo>(targetClasses))) {
/*  440 */         throw new MixinReloadException(MixinInfo.this, "Cannot change target classes");
/*      */       }
/*  442 */       int priority = MixinInfo.this.readPriority(this.classNode);
/*  443 */       if (priority != MixinInfo.this.getPriority()) {
/*  444 */         throw new MixinReloadException(MixinInfo.this, "Cannot change mixin priority");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class SubType
/*      */   {
/*      */     protected final MixinInfo mixin;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final String annotationType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final boolean targetMustBeInterface;
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean detached;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SubType(MixinInfo info, String annotationType, boolean targetMustBeInterface) {
/*  475 */       this.mixin = info;
/*  476 */       this.annotationType = annotationType;
/*  477 */       this.targetMustBeInterface = targetMustBeInterface;
/*      */     }
/*      */     
/*      */     Collection<String> getInterfaces() {
/*  481 */       return Collections.emptyList();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isDetachedSuper() {
/*  491 */       return this.detached;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isLoadable() {
/*  501 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void validateTarget(String targetName, ClassInfo targetInfo) {
/*  511 */       boolean targetIsInterface = targetInfo.isInterface();
/*  512 */       if (targetIsInterface != this.targetMustBeInterface) {
/*  513 */         String not = targetIsInterface ? "" : "not ";
/*  514 */         throw new InvalidMixinException(this.mixin, this.annotationType + " target type mismatch: " + targetName + " is " + not + "an interface in " + this);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     abstract void validate(MixinInfo.State param1State, List<ClassInfo> param1List);
/*      */ 
/*      */     
/*      */     abstract MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode param1MixinClassNode);
/*      */ 
/*      */     
/*      */     static class Standard
/*      */       extends SubType
/*      */     {
/*      */       Standard(MixinInfo info) {
/*  529 */         super(info, "@Mixin", false);
/*      */       }
/*      */ 
/*      */       
/*      */       void validate(MixinInfo.State state, List<ClassInfo> targetClasses) {
/*  534 */         ClassNode classNode = state.getClassNode();
/*      */         
/*  536 */         for (ClassInfo targetClass : targetClasses) {
/*  537 */           if (classNode.superName.equals(targetClass.getSuperName())) {
/*      */             continue;
/*      */           }
/*      */           
/*  541 */           if (!targetClass.hasSuperClass(classNode.superName, ClassInfo.Traversal.SUPER)) {
/*  542 */             ClassInfo superClass = ClassInfo.forName(classNode.superName);
/*  543 */             if (superClass.isMixin())
/*      */             {
/*  545 */               for (ClassInfo superTarget : superClass.getTargets()) {
/*  546 */                 if (targetClasses.contains(superTarget)) {
/*  547 */                   throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + superTarget
/*  548 */                       .getClassName() + " as its superclass " + superClass
/*  549 */                       .getClassName());
/*      */                 }
/*      */               } 
/*      */             }
/*      */             
/*  554 */             throw new InvalidMixinException(this.mixin, "Super class '" + classNode.superName.replace('/', '.') + "' of " + this.mixin
/*  555 */                 .getName() + " was not found in the hierarchy of target class '" + targetClass + "'");
/*      */           } 
/*      */           
/*  558 */           this.detached = true;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode classNode) {
/*  564 */         return new MixinPreProcessorStandard(this.mixin, classNode);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static class Interface
/*      */       extends SubType
/*      */     {
/*      */       Interface(MixinInfo info) {
/*  574 */         super(info, "@Mixin", true);
/*      */       }
/*      */ 
/*      */       
/*      */       void validate(MixinInfo.State state, List<ClassInfo> targetClasses) {
/*  579 */         if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
/*  580 */           throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
/*      */         }
/*      */         
/*  583 */         ClassNode classNode = state.getClassNode();
/*      */         
/*  585 */         if (!"java/lang/Object".equals(classNode.superName)) {
/*  586 */           throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName
/*  587 */               .replace('/', '.'));
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode classNode) {
/*  593 */         return new MixinPreProcessorInterface(this.mixin, classNode);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static class Accessor
/*      */       extends SubType
/*      */     {
/*  603 */       private final Collection<String> interfaces = new ArrayList<String>();
/*      */       
/*      */       Accessor(MixinInfo info) {
/*  606 */         super(info, "@Mixin", false);
/*  607 */         this.interfaces.add(info.getClassRef());
/*      */       }
/*      */ 
/*      */       
/*      */       boolean isLoadable() {
/*  612 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       Collection<String> getInterfaces() {
/*  617 */         return this.interfaces;
/*      */       }
/*      */ 
/*      */       
/*      */       void validateTarget(String targetName, ClassInfo targetInfo) {
/*  622 */         boolean targetIsInterface = targetInfo.isInterface();
/*  623 */         if (targetIsInterface && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
/*  624 */           throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       void validate(MixinInfo.State state, List<ClassInfo> targetClasses) {
/*  630 */         ClassNode classNode = state.getClassNode();
/*      */         
/*  632 */         if (!"java/lang/Object".equals(classNode.superName)) {
/*  633 */           throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName
/*  634 */               .replace('/', '.'));
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode classNode) {
/*  640 */         return new MixinPreProcessorAccessor(this.mixin, classNode);
/*      */       }
/*      */     }
/*      */     
/*      */     static SubType getTypeFor(MixinInfo mixin) { int i;
/*  645 */       if (!mixin.getClassInfo().isInterface()) {
/*  646 */         return new Standard(mixin);
/*      */       }
/*      */       
/*  649 */       boolean containsNonAccessorMethod = false;
/*  650 */       for (ClassInfo.Method method : mixin.getClassInfo().getMethods()) {
/*  651 */         i = containsNonAccessorMethod | (!method.isAccessor() ? 1 : 0);
/*      */       }
/*      */       
/*  654 */       if (i != 0)
/*      */       {
/*  656 */         return new Interface(mixin);
/*      */       }
/*      */ 
/*      */       
/*  660 */       return new Accessor(mixin); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  668 */   private static final IMixinService classLoaderUtil = MixinService.getService();
/*      */   static class Accessor extends SubType {
/*      */     private final Collection<String> interfaces = new ArrayList<String>();
/*      */     Accessor(MixinInfo info) { super(info, "@Mixin", false); this.interfaces.add(info.getClassRef()); }
/*      */     boolean isLoadable() { return true; }
/*      */     Collection<String> getInterfaces() { return this.interfaces; }
/*  674 */     void validateTarget(String targetName, ClassInfo targetInfo) { boolean targetIsInterface = targetInfo.isInterface(); if (targetIsInterface && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");  } void validate(MixinInfo.State state, List<ClassInfo> targetClasses) { ClassNode classNode = state.getClassNode(); if (!"java/lang/Object".equals(classNode.superName)) throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName.replace('/', '.'));  } MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode classNode) { return new MixinPreProcessorAccessor(this.mixin, classNode); } } static int mixinOrder = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  679 */   private final transient Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  684 */   private final transient Profiler profiler = MixinEnvironment.getProfiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient MixinConfig parent;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String className;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int priority;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean virtual;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final List<ClassInfo> targetClasses;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final List<String> targetClassNames;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  724 */   private final transient int order = mixinOrder++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient IMixinService service;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient IMixinConfigPlugin plugin;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient MixinEnvironment.Phase phase;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient ClassInfo info;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient SubType type;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient boolean strict;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient State pendingState;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient State state;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinInfo(IMixinService service, MixinConfig parent, String name, boolean runTransformers, IMixinConfigPlugin plugin, boolean suppressPlugin) {
/*  778 */     this.service = service;
/*  779 */     this.parent = parent;
/*  780 */     this.name = name;
/*  781 */     this.className = parent.getMixinPackage() + name;
/*  782 */     this.plugin = plugin;
/*  783 */     this.phase = parent.getEnvironment().getPhase();
/*  784 */     this.strict = parent.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);
/*      */ 
/*      */     
/*      */     try {
/*  788 */       byte[] mixinBytes = loadMixinClass(this.className, runTransformers);
/*  789 */       this.pendingState = new State(mixinBytes);
/*  790 */       this.info = this.pendingState.getClassInfo();
/*  791 */       this.type = SubType.getTypeFor(this);
/*  792 */     } catch (InvalidMixinException ex) {
/*  793 */       throw ex;
/*  794 */     } catch (Exception ex) {
/*  795 */       throw new InvalidMixinException(this, ex);
/*      */     } 
/*      */     
/*  798 */     if (!this.type.isLoadable())
/*      */     {
/*      */ 
/*      */       
/*  802 */       classLoaderUtil.registerInvalidClass(this.className);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  807 */       this.priority = readPriority(this.pendingState.getClassNode());
/*  808 */       this.virtual = readPseudo(this.pendingState.getClassNode());
/*  809 */       this.targetClasses = readTargetClasses(this.pendingState.getClassNode(), suppressPlugin);
/*  810 */       this.targetClassNames = Collections.unmodifiableList(Lists.transform(this.targetClasses, Functions.toStringFunction()));
/*  811 */     } catch (InvalidMixinException ex) {
/*  812 */       throw ex;
/*  813 */     } catch (Exception ex) {
/*  814 */       throw new InvalidMixinException(this, ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void validate() {
/*  822 */     if (this.pendingState == null) {
/*  823 */       throw new IllegalStateException("No pending validation state for " + this);
/*      */     }
/*      */     
/*      */     try {
/*  827 */       this.pendingState.validate(this.type, this.targetClasses);
/*  828 */       this.state = this.pendingState;
/*      */     } finally {
/*  830 */       this.pendingState = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<ClassInfo> readTargetClasses(MixinClassNode classNode, boolean suppressPlugin) {
/*  842 */     if (classNode == null) {
/*  843 */       return Collections.emptyList();
/*      */     }
/*      */     
/*  846 */     AnnotationNode mixin = Annotations.getInvisible(classNode, Mixin.class);
/*  847 */     if (mixin == null) {
/*  848 */       throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", new Object[] { this.className }));
/*      */     }
/*      */     
/*  851 */     List<ClassInfo> targets = new ArrayList<ClassInfo>();
/*  852 */     List<Type> publicTargets = (List<Type>)Annotations.getValue(mixin, "value");
/*  853 */     List<String> privateTargets = (List<String>)Annotations.getValue(mixin, "targets");
/*      */     
/*  855 */     if (publicTargets != null) {
/*  856 */       readTargets(targets, Lists.transform(publicTargets, new Function<Type, String>()
/*      */             {
/*      */               public String apply(Type input) {
/*  859 */                 return input.getClassName();
/*      */               }
/*      */             }), suppressPlugin, false);
/*      */     }
/*      */     
/*  864 */     if (privateTargets != null) {
/*  865 */       readTargets(targets, Lists.transform(privateTargets, new Function<String, String>()
/*      */             {
/*      */               public String apply(String input) {
/*  868 */                 return MixinInfo.this.getParent().remapClassName(MixinInfo.this.getClassRef(), input);
/*      */               }
/*      */             }), suppressPlugin, true);
/*      */     }
/*      */     
/*  873 */     return targets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readTargets(Collection<ClassInfo> outTargets, Collection<String> inTargets, boolean suppressPlugin, boolean checkPublic) {
/*  880 */     for (String targetRef : inTargets) {
/*  881 */       String targetName = targetRef.replace('/', '.');
/*  882 */       if (classLoaderUtil.isClassLoaded(targetName) && !isReloading()) {
/*  883 */         String message = String.format("Critical problem: %s target %s was already transformed.", new Object[] { this, targetName });
/*  884 */         if (this.parent.isRequired()) {
/*  885 */           throw new MixinTargetAlreadyLoadedException(this, message, targetName);
/*      */         }
/*  887 */         this.logger.error(message);
/*      */       } 
/*      */       
/*  890 */       if (shouldApplyMixin(suppressPlugin, targetName)) {
/*  891 */         ClassInfo targetInfo = getTarget(targetName, checkPublic);
/*  892 */         if (targetInfo != null && !outTargets.contains(targetInfo)) {
/*  893 */           outTargets.add(targetInfo);
/*  894 */           targetInfo.addMixin(this);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean shouldApplyMixin(boolean suppressPlugin, String targetName) {
/*  901 */     Profiler.Section pluginTimer = this.profiler.begin("plugin");
/*  902 */     boolean result = (this.plugin == null || suppressPlugin || this.plugin.shouldApplyMixin(targetName, this.className));
/*  903 */     pluginTimer.end();
/*  904 */     return result;
/*      */   }
/*      */   
/*      */   private ClassInfo getTarget(String targetName, boolean checkPublic) throws InvalidMixinException {
/*  908 */     ClassInfo targetInfo = ClassInfo.forName(targetName);
/*  909 */     if (targetInfo == null) {
/*  910 */       if (isVirtual()) {
/*  911 */         this.logger.debug("Skipping virtual target {} for {}", new Object[] { targetName, this });
/*      */       } else {
/*  913 */         handleTargetError(String.format("@Mixin target %s was not found %s", new Object[] { targetName, this }));
/*      */       } 
/*  915 */       return null;
/*      */     } 
/*  917 */     this.type.validateTarget(targetName, targetInfo);
/*  918 */     if (checkPublic && targetInfo.isPublic() && !isVirtual()) {
/*  919 */       handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", new Object[] { targetName, this }));
/*      */     }
/*  921 */     return targetInfo;
/*      */   }
/*      */   
/*      */   private void handleTargetError(String message) {
/*  925 */     if (this.strict) {
/*  926 */       this.logger.error(message);
/*  927 */       throw new InvalidMixinException(this, message);
/*      */     } 
/*  929 */     this.logger.warn(message);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int readPriority(ClassNode classNode) {
/*  939 */     if (classNode == null) {
/*  940 */       return this.parent.getDefaultMixinPriority();
/*      */     }
/*      */     
/*  943 */     AnnotationNode mixin = Annotations.getInvisible(classNode, Mixin.class);
/*  944 */     if (mixin == null) {
/*  945 */       throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", new Object[] { this.className }));
/*      */     }
/*      */     
/*  948 */     Integer priority = (Integer)Annotations.getValue(mixin, "priority");
/*  949 */     return (priority == null) ? this.parent.getDefaultMixinPriority() : priority.intValue();
/*      */   }
/*      */   
/*      */   protected boolean readPseudo(ClassNode classNode) {
/*  953 */     return (Annotations.getInvisible(classNode, Pseudo.class) != null);
/*      */   }
/*      */   
/*      */   private boolean isReloading() {
/*  957 */     return this.pendingState instanceof Reloaded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private State getState() {
/*  965 */     return (this.state != null) ? this.state : this.pendingState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ClassInfo getClassInfo() {
/*  972 */     return this.info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IMixinConfig getConfig() {
/*  980 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinConfig getParent() {
/*  987 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPriority() {
/*  995 */     return this.priority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1003 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassName() {
/* 1011 */     return this.className;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassRef() {
/* 1019 */     return getClassInfo().getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getClassBytes() {
/* 1027 */     return getState().getClassBytes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDetachedSuper() {
/* 1036 */     return getState().isDetachedSuper();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUnique() {
/* 1043 */     return getState().isUnique();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVirtual() {
/* 1050 */     return this.virtual;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAccessor() {
/* 1057 */     return this.type instanceof SubType.Accessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLoadable() {
/* 1064 */     return this.type.isLoadable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Level getLoggingLevel() {
/* 1071 */     return this.parent.getLoggingLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment.Phase getPhase() {
/* 1079 */     return this.phase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinClassNode getClassNode(int flags) {
/* 1087 */     return getState().createClassNode(flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getTargetClasses() {
/* 1095 */     return this.targetClassNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<InterfaceInfo> getSoftImplements() {
/* 1102 */     return Collections.unmodifiableList(getState().getSoftImplements());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<String> getSyntheticInnerClasses() {
/* 1109 */     return Collections.unmodifiableSet(getState().getSyntheticInnerClasses());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<String> getInnerClasses() {
/* 1116 */     return Collections.unmodifiableSet(getState().getInnerClasses());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<ClassInfo> getTargets() {
/* 1123 */     return Collections.unmodifiableList(this.targetClasses);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<String> getInterfaces() {
/* 1132 */     return getState().getInterfaces();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinTargetContext createContextFor(TargetClassContext target) {
/* 1142 */     MixinClassNode classNode = getClassNode(8);
/* 1143 */     Profiler.Section preTimer = this.profiler.begin("pre");
/* 1144 */     MixinTargetContext preProcessor = this.type.createPreProcessor(classNode).prepare().createContextFor(target);
/* 1145 */     preTimer.end();
/* 1146 */     return preProcessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] loadMixinClass(String mixinClassName, boolean runTransformers) throws ClassNotFoundException {
/* 1158 */     byte[] mixinBytes = null;
/*      */     
/*      */     try {
/* 1161 */       if (runTransformers) {
/* 1162 */         String restrictions = this.service.getClassRestrictions(mixinClassName);
/* 1163 */         if (restrictions.length() > 0) {
/* 1164 */           this.logger.error("Classloader restrictions [{}] encountered loading {}, name: {}", new Object[] { restrictions, this, mixinClassName });
/*      */         }
/*      */       } 
/* 1167 */       mixinBytes = this.service.getBytecodeProvider().getClassBytes(mixinClassName, runTransformers);
/*      */     }
/* 1169 */     catch (ClassNotFoundException ex) {
/* 1170 */       throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", new Object[] { mixinClassName }));
/* 1171 */     } catch (IOException ex) {
/* 1172 */       this.logger.warn("Failed to load mixin {}, the specified mixin will not be applied", new Object[] { mixinClassName });
/* 1173 */       throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", ex);
/*      */     } 
/*      */     
/* 1176 */     return mixinBytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void reloadMixin(byte[] mixinBytes) {
/* 1185 */     if (this.pendingState != null) {
/* 1186 */       throw new IllegalStateException("Cannot reload mixin while it is initialising");
/*      */     }
/* 1188 */     this.pendingState = new Reloaded(this.state, mixinBytes);
/* 1189 */     validate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(MixinInfo other) {
/* 1197 */     if (other == null) {
/* 1198 */       return 0;
/*      */     }
/* 1200 */     if (other.priority == this.priority) {
/* 1201 */       return this.order - other.order;
/*      */     }
/* 1203 */     return this.priority - other.priority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preApply(String transformedName, ClassNode targetClass) {
/* 1210 */     if (this.plugin != null) {
/* 1211 */       Profiler.Section pluginTimer = this.profiler.begin("plugin");
/* 1212 */       this.plugin.preApply(transformedName, targetClass, this.className, this);
/* 1213 */       pluginTimer.end();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postApply(String transformedName, ClassNode targetClass) {
/* 1221 */     if (this.plugin != null) {
/* 1222 */       Profiler.Section pluginTimer = this.profiler.begin("plugin");
/* 1223 */       this.plugin.postApply(transformedName, targetClass, this.className, this);
/* 1224 */       pluginTimer.end();
/*      */     } 
/*      */     
/* 1227 */     this.parent.postApply(transformedName, targetClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1235 */     return String.format("%s:%s", new Object[] { this.parent.getName(), this.name });
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MixinInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */