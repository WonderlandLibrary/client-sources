/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.collect.BiMap;
/*      */ import com.google.common.collect.HashBiMap;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Deque;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.spongepowered.asm.lib.Handle;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldNode;
/*      */ import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.LdcInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.LocalVariableNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.SoftOverride;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*      */ import org.spongepowered.asm.mixin.gen.AccessorInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*      */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*      */ import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
/*      */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*      */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*      */ import org.spongepowered.asm.mixin.struct.MemberRef;
/*      */ import org.spongepowered.asm.mixin.struct.SourceMap;
/*      */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
/*      */ import org.spongepowered.asm.obfuscation.RemapperChain;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.Bytecode;
/*      */ import org.spongepowered.asm.util.ClassSignature;
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
/*      */ 
/*      */ public class MixinTargetContext
/*      */   extends ClassContext
/*      */   implements IMixinContext
/*      */ {
/*   93 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MixinInfo mixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ClassNode classNode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final TargetClassContext targetClass;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String sessionId;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ClassInfo targetClassInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   private final BiMap<String, String> innerClasses = (BiMap<String, String>)HashBiMap.create();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   private final List<MethodNode> shadowMethods = new ArrayList<MethodNode>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  133 */   private final Map<FieldNode, ClassInfo.Field> shadowFields = new LinkedHashMap<FieldNode, ClassInfo.Field>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   private final List<MethodNode> mergedMethods = new ArrayList<MethodNode>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   private final InjectorGroupInfo.Map injectorGroups = new InjectorGroupInfo.Map();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   private final List<InjectionInfo> injectors = new ArrayList<InjectionInfo>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   private final List<AccessorInfo> accessors = new ArrayList<AccessorInfo>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean inheritsFromMixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean detachedSuper;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final SourceMap.File stratum;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  174 */   private int minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinTargetContext(MixinInfo mixin, ClassNode classNode, TargetClassContext context) {
/*  184 */     this.mixin = mixin;
/*  185 */     this.classNode = classNode;
/*  186 */     this.targetClass = context;
/*  187 */     this.targetClassInfo = ClassInfo.forName(getTarget().getClassRef());
/*  188 */     this.stratum = context.getSourceMap().addFile(this.classNode);
/*  189 */     this.inheritsFromMixin = (mixin.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy());
/*  190 */     this.detachedSuper = !this.classNode.superName.equals((getTarget().getClassNode()).superName);
/*  191 */     this.sessionId = context.getSessionId();
/*  192 */     requireVersion(classNode.version);
/*      */     
/*  194 */     InnerClassGenerator icg = (InnerClassGenerator)context.getExtensions().getGenerator(InnerClassGenerator.class);
/*  195 */     for (String innerClass : this.mixin.getInnerClasses()) {
/*  196 */       this.innerClasses.put(innerClass, icg.registerInnerClass(this.mixin, innerClass, this));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addShadowMethod(MethodNode method) {
/*  206 */     this.shadowMethods.add(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addShadowField(FieldNode fieldNode, ClassInfo.Field fieldInfo) {
/*  216 */     this.shadowFields.put(fieldNode, fieldInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addAccessorMethod(MethodNode method, Class<? extends Annotation> type) {
/*  226 */     this.accessors.add(AccessorInfo.of(this, method, type));
/*      */   }
/*      */   
/*      */   void addMixinMethod(MethodNode method) {
/*  230 */     Annotations.setVisible(method, MixinMerged.class, new Object[] { "mixin", getClassName() });
/*  231 */     getTarget().addMixinMethod(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void methodMerged(MethodNode method) {
/*  240 */     this.mergedMethods.add(method);
/*  241 */     this.targetClassInfo.addMethod(method);
/*  242 */     getTarget().methodMerged(method);
/*      */     
/*  244 */     Annotations.setVisible(method, MixinMerged.class, new Object[] { "mixin", 
/*  245 */           getClassName(), "priority", 
/*  246 */           Integer.valueOf(getPriority()), "sessionId", this.sessionId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  255 */     return this.mixin.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment getEnvironment() {
/*  264 */     return this.mixin.getParent().getEnvironment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOption(MixinEnvironment.Option option) {
/*  273 */     return getEnvironment().getOption(option);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassNode getClassNode() {
/*  283 */     return this.classNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassName() {
/*  293 */     return this.mixin.getClassName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassRef() {
/*  302 */     return this.mixin.getClassRef();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TargetClassContext getTarget() {
/*  311 */     return this.targetClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTargetClassRef() {
/*  322 */     return getTarget().getClassRef();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassNode getTargetClassNode() {
/*  331 */     return getTarget().getClassNode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo getTargetClassInfo() {
/*  340 */     return this.targetClassInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ClassInfo getClassInfo() {
/*  350 */     return this.mixin.getClassInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassSignature getSignature() {
/*  359 */     return getClassInfo().getSignature();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SourceMap.File getStratum() {
/*  368 */     return this.stratum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinRequiredClassVersion() {
/*  375 */     return this.minRequiredClassVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultRequiredInjections() {
/*  385 */     return this.mixin.getParent().getDefaultRequiredInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultInjectorGroup() {
/*  394 */     return this.mixin.getParent().getDefaultInjectorGroup();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxShiftByValue() {
/*  403 */     return this.mixin.getParent().getMaxShiftByValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InjectorGroupInfo.Map getInjectorGroups() {
/*  412 */     return this.injectorGroups;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireOverwriteAnnotations() {
/*  421 */     return this.mixin.getParent().requireOverwriteAnnotations();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo findRealType(ClassInfo mixin) {
/*  432 */     if (mixin == getClassInfo()) {
/*  433 */       return this.targetClassInfo;
/*      */     }
/*      */     
/*  436 */     ClassInfo type = this.targetClassInfo.findCorrespondingType(mixin);
/*  437 */     if (type == null) {
/*  438 */       throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + mixin + " in hierarchy of " + this.targetClassInfo);
/*      */     }
/*      */ 
/*      */     
/*  442 */     return type;
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
/*      */   public void transformMethod(MethodNode method) {
/*  454 */     validateMethod(method);
/*  455 */     transformDescriptor(method);
/*  456 */     transformLVT(method);
/*      */ 
/*      */     
/*  459 */     this.stratum.applyOffset(method);
/*      */     
/*  461 */     AbstractInsnNode lastInsn = null;
/*  462 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  463 */       AbstractInsnNode insn = iter.next();
/*      */       
/*  465 */       if (insn instanceof MethodInsnNode) {
/*  466 */         transformMethodRef(method, iter, (MemberRef)new MemberRef.Method((MethodInsnNode)insn));
/*  467 */       } else if (insn instanceof FieldInsnNode) {
/*  468 */         transformFieldRef(method, iter, (MemberRef)new MemberRef.Field((FieldInsnNode)insn));
/*  469 */         checkFinal(method, iter, (FieldInsnNode)insn);
/*  470 */       } else if (insn instanceof TypeInsnNode) {
/*  471 */         transformTypeNode(method, iter, (TypeInsnNode)insn, lastInsn);
/*  472 */       } else if (insn instanceof LdcInsnNode) {
/*  473 */         transformConstantNode(method, iter, (LdcInsnNode)insn);
/*  474 */       } else if (insn instanceof InvokeDynamicInsnNode) {
/*  475 */         transformInvokeDynamicNode(method, iter, (InvokeDynamicInsnNode)insn);
/*      */       } 
/*      */       
/*  478 */       lastInsn = insn;
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
/*      */   private void validateMethod(MethodNode method) {
/*  490 */     if (Annotations.getInvisible(method, SoftOverride.class) != null) {
/*  491 */       ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(method.name, method.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
/*      */       
/*  493 */       if (superMethod == null || !superMethod.isInjected()) {
/*  494 */         throw new InvalidMixinException(this, "Mixin method " + method.name + method.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + 
/*  495 */             getTarget().getClassName());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformLVT(MethodNode method) {
/*  506 */     if (method.localVariables == null) {
/*      */       return;
/*      */     }
/*      */     
/*  510 */     for (LocalVariableNode local : method.localVariables) {
/*  511 */       if (local == null || local.desc == null) {
/*      */         continue;
/*      */       }
/*      */       
/*  515 */       local.desc = transformSingleDescriptor(Type.getType(local.desc));
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
/*      */   
/*      */   private void transformMethodRef(MethodNode method, Iterator<AbstractInsnNode> iter, MemberRef methodRef) {
/*  528 */     transformDescriptor(methodRef);
/*      */     
/*  530 */     if (methodRef.getOwner().equals(getClassRef())) {
/*  531 */       methodRef.setOwner(getTarget().getClassRef());
/*  532 */       ClassInfo.Method md = getClassInfo().findMethod(methodRef.getName(), methodRef.getDesc(), 10);
/*  533 */       if (md != null && md.isRenamed() && md.getOriginalName().equals(methodRef.getName()) && md.isSynthetic()) {
/*  534 */         methodRef.setName(md.getName());
/*      */       }
/*  536 */       upgradeMethodRef(method, methodRef, md);
/*  537 */     } else if (this.innerClasses.containsKey(methodRef.getOwner())) {
/*  538 */       methodRef.setOwner((String)this.innerClasses.get(methodRef.getOwner()));
/*  539 */       methodRef.setDesc(transformMethodDescriptor(methodRef.getDesc()));
/*  540 */     } else if (this.detachedSuper || this.inheritsFromMixin) {
/*  541 */       if (methodRef.getOpcode() == 183) {
/*  542 */         updateStaticBinding(method, methodRef);
/*  543 */       } else if (methodRef.getOpcode() == 182 && ClassInfo.forName(methodRef.getOwner()).isMixin()) {
/*  544 */         updateDynamicBinding(method, methodRef);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformFieldRef(MethodNode method, Iterator<AbstractInsnNode> iter, MemberRef fieldRef) {
/*  561 */     if ("super$".equals(fieldRef.getName())) {
/*  562 */       if (fieldRef instanceof MemberRef.Field) {
/*  563 */         processImaginarySuper(method, ((MemberRef.Field)fieldRef).insn);
/*  564 */         iter.remove();
/*      */       } else {
/*  566 */         throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
/*      */       } 
/*      */     }
/*      */     
/*  570 */     transformDescriptor(fieldRef);
/*      */     
/*  572 */     if (fieldRef.getOwner().equals(getClassRef())) {
/*  573 */       fieldRef.setOwner(getTarget().getClassRef());
/*      */       
/*  575 */       ClassInfo.Field field = getClassInfo().findField(fieldRef.getName(), fieldRef.getDesc(), 10);
/*      */       
/*  577 */       if (field != null && field.isRenamed() && field.getOriginalName().equals(fieldRef.getName()) && field.isStatic()) {
/*  578 */         fieldRef.setName(field.getName());
/*      */       }
/*      */     } else {
/*  581 */       ClassInfo fieldOwner = ClassInfo.forName(fieldRef.getOwner());
/*  582 */       if (fieldOwner.isMixin()) {
/*  583 */         ClassInfo actualOwner = this.targetClassInfo.findCorrespondingType(fieldOwner);
/*  584 */         fieldRef.setOwner((actualOwner != null) ? actualOwner.getName() : getTarget().getClassRef());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkFinal(MethodNode method, Iterator<AbstractInsnNode> iter, FieldInsnNode fieldNode) {
/*  590 */     if (!fieldNode.owner.equals(getTarget().getClassRef())) {
/*      */       return;
/*      */     }
/*      */     
/*  594 */     int opcode = fieldNode.getOpcode();
/*  595 */     if (opcode == 180 || opcode == 178) {
/*      */       return;
/*      */     }
/*      */     
/*  599 */     for (Map.Entry<FieldNode, ClassInfo.Field> shadow : this.shadowFields.entrySet()) {
/*  600 */       FieldNode shadowFieldNode = shadow.getKey();
/*  601 */       if (!shadowFieldNode.desc.equals(fieldNode.desc) || !shadowFieldNode.name.equals(fieldNode.name)) {
/*      */         continue;
/*      */       }
/*  604 */       ClassInfo.Field shadowField = shadow.getValue();
/*  605 */       if (shadowField.isDecoratedFinal()) {
/*  606 */         if (shadowField.isDecoratedMutable()) {
/*  607 */           if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/*  608 */             logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[] { shadowField, this.mixin, method.name });
/*      */           }
/*      */         }
/*  611 */         else if ("<init>".equals(method.name) || "<clinit>".equals(method.name)) {
/*  612 */           logger.warn("@Final field {} in {} should be final", new Object[] { shadowField, this.mixin });
/*      */         } else {
/*  614 */           logger.error("Write access detected to @Final field {} in {}::{}", new Object[] { shadowField, this.mixin, method.name });
/*  615 */           if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
/*  616 */             throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + shadowField + " in " + this.mixin + "::" + method.name);
/*      */           }
/*      */         } 
/*      */       }
/*      */       return;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformTypeNode(MethodNode method, Iterator<AbstractInsnNode> iter, TypeInsnNode typeInsn, AbstractInsnNode lastNode) {
/*  637 */     if (typeInsn.getOpcode() == 192 && typeInsn.desc
/*  638 */       .equals(getTarget().getClassRef()) && lastNode
/*  639 */       .getOpcode() == 25 && ((VarInsnNode)lastNode).var == 0) {
/*      */       
/*  641 */       iter.remove();
/*      */       
/*      */       return;
/*      */     } 
/*  645 */     if (typeInsn.desc.equals(getClassRef())) {
/*  646 */       typeInsn.desc = getTarget().getClassRef();
/*      */     } else {
/*  648 */       String newName = (String)this.innerClasses.get(typeInsn.desc);
/*  649 */       if (newName != null) {
/*  650 */         typeInsn.desc = newName;
/*      */       }
/*      */     } 
/*      */     
/*  654 */     transformDescriptor(typeInsn);
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
/*      */   private void transformConstantNode(MethodNode method, Iterator<AbstractInsnNode> iter, LdcInsnNode ldcInsn) {
/*  666 */     ldcInsn.cst = transformConstant(method, iter, ldcInsn.cst);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformInvokeDynamicNode(MethodNode method, Iterator<AbstractInsnNode> iter, InvokeDynamicInsnNode dynInsn) {
/*  677 */     requireVersion(51);
/*  678 */     dynInsn.desc = transformMethodDescriptor(dynInsn.desc);
/*  679 */     dynInsn.bsm = transformHandle(method, iter, dynInsn.bsm);
/*  680 */     for (int i = 0; i < dynInsn.bsmArgs.length; i++) {
/*  681 */       dynInsn.bsmArgs[i] = transformConstant(method, iter, dynInsn.bsmArgs[i]);
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
/*      */   
/*      */   private Object transformConstant(MethodNode method, Iterator<AbstractInsnNode> iter, Object constant) {
/*  694 */     if (constant instanceof Type) {
/*  695 */       Type type = (Type)constant;
/*  696 */       String desc = transformDescriptor(type);
/*  697 */       if (!type.toString().equals(desc)) {
/*  698 */         return Type.getType(desc);
/*      */       }
/*  700 */       return constant;
/*  701 */     }  if (constant instanceof Handle) {
/*  702 */       return transformHandle(method, iter, (Handle)constant);
/*      */     }
/*  704 */     return constant;
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
/*      */   private Handle transformHandle(MethodNode method, Iterator<AbstractInsnNode> iter, Handle handle) {
/*  716 */     MemberRef.Handle memberRef = new MemberRef.Handle(handle);
/*  717 */     if (memberRef.isField()) {
/*  718 */       transformFieldRef(method, iter, (MemberRef)memberRef);
/*      */     } else {
/*  720 */       transformMethodRef(method, iter, (MemberRef)memberRef);
/*      */     } 
/*  722 */     return memberRef.getMethodHandle();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processImaginarySuper(MethodNode method, FieldInsnNode fieldInsn) {
/*  738 */     if (fieldInsn.getOpcode() != 180) {
/*  739 */       if ("<init>".equals(method.name)) {
/*  740 */         throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + fieldInsn.name + " must not specify an initialiser");
/*      */       }
/*      */ 
/*      */       
/*  744 */       throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(fieldInsn.getOpcode()) + " opcode in " + method.name + method.desc);
/*      */     } 
/*      */ 
/*      */     
/*  748 */     if ((method.access & 0x2) != 0 || (method.access & 0x8) != 0) {
/*  749 */       throw new InvalidMixinException(this, "Illegal imaginary super access: method " + method.name + method.desc + " is private or static");
/*      */     }
/*      */ 
/*      */     
/*  753 */     if (Annotations.getInvisible(method, SoftOverride.class) == null) {
/*  754 */       throw new InvalidMixinException(this, "Illegal imaginary super access: method " + method.name + method.desc + " is not decorated with @SoftOverride");
/*      */     }
/*      */ 
/*      */     
/*  758 */     for (Iterator<AbstractInsnNode> methodIter = method.instructions.iterator(method.instructions.indexOf((AbstractInsnNode)fieldInsn)); methodIter.hasNext(); ) {
/*  759 */       AbstractInsnNode insn = methodIter.next();
/*  760 */       if (insn instanceof MethodInsnNode) {
/*  761 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  762 */         if (methodNode.owner.equals(getClassRef()) && methodNode.name.equals(method.name) && methodNode.desc.equals(method.desc)) {
/*  763 */           methodNode.setOpcode(183);
/*  764 */           updateStaticBinding(method, (MemberRef)new MemberRef.Method(methodNode));
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  770 */     throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + method.name + method.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateStaticBinding(MethodNode method, MemberRef methodRef) {
/*  781 */     updateBinding(method, methodRef, ClassInfo.Traversal.SUPER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDynamicBinding(MethodNode method, MemberRef methodRef) {
/*  792 */     updateBinding(method, methodRef, ClassInfo.Traversal.ALL);
/*      */   }
/*      */   
/*      */   private void updateBinding(MethodNode method, MemberRef methodRef, ClassInfo.Traversal traversal) {
/*  796 */     if ("<init>".equals(method.name) || methodRef
/*  797 */       .getOwner().equals(getTarget().getClassRef()) || 
/*  798 */       getTarget().getClassRef().startsWith("<")) {
/*      */       return;
/*      */     }
/*      */     
/*  802 */     ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(methodRef.getName(), methodRef.getDesc(), traversal
/*  803 */         .getSearchType(), traversal);
/*  804 */     if (superMethod != null) {
/*  805 */       if (superMethod.getOwner().isMixin()) {
/*  806 */         throw new InvalidMixinException(this, "Invalid " + methodRef + " in " + this + " resolved " + superMethod.getOwner() + " but is mixin.");
/*      */       }
/*      */       
/*  809 */       methodRef.setOwner(superMethod.getImplementor().getName());
/*  810 */     } else if (ClassInfo.forName(methodRef.getOwner()).isMixin()) {
/*  811 */       throw new MixinTransformerError("Error resolving " + methodRef + " in " + this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformDescriptor(FieldNode field) {
/*  821 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  824 */     field.desc = transformSingleDescriptor(field.desc, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformDescriptor(MethodNode method) {
/*  833 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  836 */     method.desc = transformMethodDescriptor(method.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformDescriptor(MemberRef member) {
/*  846 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  849 */     if (member.isField()) {
/*  850 */       member.setDesc(transformSingleDescriptor(member.getDesc(), false));
/*      */     } else {
/*  852 */       member.setDesc(transformMethodDescriptor(member.getDesc()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformDescriptor(TypeInsnNode typeInsn) {
/*  862 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  865 */     typeInsn.desc = transformSingleDescriptor(typeInsn.desc, true);
/*      */   }
/*      */   
/*      */   private String transformDescriptor(Type type) {
/*  869 */     if (type.getSort() == 11) {
/*  870 */       return transformMethodDescriptor(type.getDescriptor());
/*      */     }
/*  872 */     return transformSingleDescriptor(type);
/*      */   }
/*      */   
/*      */   private String transformSingleDescriptor(Type type) {
/*  876 */     if (type.getSort() < 9) {
/*  877 */       return type.toString();
/*      */     }
/*      */     
/*  880 */     return transformSingleDescriptor(type.toString(), false);
/*      */   }
/*      */   
/*      */   private String transformSingleDescriptor(String desc, boolean isObject) {
/*  884 */     String type = desc;
/*  885 */     while (type.startsWith("[") || type.startsWith("L")) {
/*  886 */       if (type.startsWith("[")) {
/*  887 */         type = type.substring(1);
/*      */         continue;
/*      */       } 
/*  890 */       type = type.substring(1, type.indexOf(";"));
/*  891 */       isObject = true;
/*      */     } 
/*      */     
/*  894 */     if (!isObject) {
/*  895 */       return desc;
/*      */     }
/*      */     
/*  898 */     String innerClassName = (String)this.innerClasses.get(type);
/*  899 */     if (innerClassName != null) {
/*  900 */       return desc.replace(type, innerClassName);
/*      */     }
/*      */     
/*  903 */     if (this.innerClasses.inverse().containsKey(type)) {
/*  904 */       return desc;
/*      */     }
/*      */     
/*  907 */     ClassInfo typeInfo = ClassInfo.forName(type);
/*      */     
/*  909 */     if (!typeInfo.isMixin()) {
/*  910 */       return desc;
/*      */     }
/*      */     
/*  913 */     return desc.replace(type, findRealType(typeInfo).toString());
/*      */   }
/*      */   
/*      */   private String transformMethodDescriptor(String desc) {
/*  917 */     StringBuilder newDesc = new StringBuilder();
/*  918 */     newDesc.append('(');
/*  919 */     for (Type arg : Type.getArgumentTypes(desc)) {
/*  920 */       newDesc.append(transformSingleDescriptor(arg));
/*      */     }
/*  922 */     return newDesc.append(')').append(transformSingleDescriptor(Type.getReturnType(desc))).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Target getTargetMethod(MethodNode method) {
/*  933 */     return getTarget().getTargetMethod(method);
/*      */   }
/*      */   
/*      */   MethodNode findMethod(MethodNode method, AnnotationNode annotation) {
/*  937 */     Deque<String> aliases = new LinkedList<String>();
/*  938 */     aliases.add(method.name);
/*  939 */     if (annotation != null) {
/*  940 */       List<String> aka = (List<String>)Annotations.getValue(annotation, "aliases");
/*  941 */       if (aka != null) {
/*  942 */         aliases.addAll(aka);
/*      */       }
/*      */     } 
/*      */     
/*  946 */     return getTarget().findMethod(aliases, method.desc);
/*      */   }
/*      */   
/*      */   MethodNode findRemappedMethod(MethodNode method) {
/*  950 */     RemapperChain remapperChain = getEnvironment().getRemappers();
/*  951 */     String remappedName = remapperChain.mapMethodName(getTarget().getClassRef(), method.name, method.desc);
/*  952 */     if (remappedName.equals(method.name)) {
/*  953 */       return null;
/*      */     }
/*      */     
/*  956 */     Deque<String> aliases = new LinkedList<String>();
/*  957 */     aliases.add(remappedName);
/*      */     
/*  959 */     return getTarget().findAliasedMethod(aliases, method.desc);
/*      */   }
/*      */   
/*      */   FieldNode findField(FieldNode field, AnnotationNode shadow) {
/*  963 */     Deque<String> aliases = new LinkedList<String>();
/*  964 */     aliases.add(field.name);
/*  965 */     if (shadow != null) {
/*  966 */       List<String> aka = (List<String>)Annotations.getValue(shadow, "aliases");
/*  967 */       if (aka != null) {
/*  968 */         aliases.addAll(aka);
/*      */       }
/*      */     } 
/*      */     
/*  972 */     return getTarget().findAliasedField(aliases, field.desc);
/*      */   }
/*      */   
/*      */   FieldNode findRemappedField(FieldNode field) {
/*  976 */     RemapperChain remapperChain = getEnvironment().getRemappers();
/*  977 */     String remappedName = remapperChain.mapFieldName(getTarget().getClassRef(), field.name, field.desc);
/*  978 */     if (remappedName.equals(field.name)) {
/*  979 */       return null;
/*      */     }
/*      */     
/*  982 */     Deque<String> aliases = new LinkedList<String>();
/*  983 */     aliases.add(remappedName);
/*  984 */     return getTarget().findAliasedField(aliases, field.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void requireVersion(int version) {
/*  994 */     this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, version);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     if (version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
/* 1000 */       throw new InvalidMixinException(this, "Unsupported mixin class version " + version);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Extensions getExtensions() {
/* 1009 */     return this.targetClass.getExtensions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IMixinInfo getMixin() {
/* 1017 */     return this.mixin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinInfo getInfo() {
/* 1024 */     return this.mixin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPriority() {
/* 1034 */     return this.mixin.getPriority();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getInterfaces() {
/* 1043 */     return this.mixin.getInterfaces();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<MethodNode> getShadowMethods() {
/* 1052 */     return this.shadowMethods;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<MethodNode> getMethods() {
/* 1061 */     return this.classNode.methods;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Map.Entry<FieldNode, ClassInfo.Field>> getShadowFields() {
/* 1070 */     return this.shadowFields.entrySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<FieldNode> getFields() {
/* 1079 */     return this.classNode.fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Level getLoggingLevel() {
/* 1088 */     return this.mixin.getLoggingLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldSetSourceFile() {
/* 1098 */     return this.mixin.getParent().shouldSetSourceFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSourceFile() {
/* 1107 */     return this.classNode.sourceFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IReferenceMapper getReferenceMapper() {
/* 1116 */     return this.mixin.getParent().getReferenceMapper();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preApply(String transformedName, ClassNode targetClass) {
/* 1126 */     this.mixin.preApply(transformedName, targetClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postApply(String transformedName, ClassNode targetClass) {
/*      */     try {
/* 1137 */       this.injectorGroups.validateAll();
/* 1138 */     } catch (InjectionValidationException ex) {
/* 1139 */       InjectorGroupInfo group = ex.getGroup();
/* 1140 */       throw new InjectionError(
/* 1141 */           String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", new Object[] {
/* 1142 */               group, this.mixin, ex.getMessage()
/*      */             }));
/*      */     } 
/* 1145 */     this.mixin.postApply(transformedName, targetClass);
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
/*      */   
/*      */   public String getUniqueName(MethodNode method, boolean preservePrefix) {
/* 1158 */     return getTarget().getUniqueName(method, preservePrefix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUniqueName(FieldNode field) {
/* 1169 */     return getTarget().getUniqueName(field);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prepareInjections() {
/* 1177 */     this.injectors.clear();
/*      */     
/* 1179 */     for (MethodNode method : this.mergedMethods) {
/* 1180 */       InjectionInfo injectInfo = InjectionInfo.parse(this, method);
/* 1181 */       if (injectInfo == null) {
/*      */         continue;
/*      */       }
/*      */       
/* 1185 */       if (injectInfo.isValid()) {
/* 1186 */         injectInfo.prepare();
/* 1187 */         this.injectors.add(injectInfo);
/*      */       } 
/*      */       
/* 1190 */       method.visibleAnnotations.remove(injectInfo.getAnnotation());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyInjections() {
/* 1198 */     for (InjectionInfo injectInfo : this.injectors) {
/* 1199 */       injectInfo.inject();
/*      */     }
/*      */     
/* 1202 */     for (InjectionInfo injectInfo : this.injectors) {
/* 1203 */       injectInfo.postInject();
/*      */     }
/*      */     
/* 1206 */     this.injectors.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<MethodNode> generateAccessors() {
/* 1214 */     for (AccessorInfo accessor : this.accessors) {
/* 1215 */       accessor.locate();
/*      */     }
/*      */     
/* 1218 */     List<MethodNode> methods = new ArrayList<MethodNode>();
/*      */     
/* 1220 */     for (AccessorInfo accessor : this.accessors) {
/* 1221 */       MethodNode generated = accessor.generate();
/* 1222 */       getTarget().addMixinMethod(generated);
/* 1223 */       methods.add(generated);
/*      */     } 
/*      */     
/* 1226 */     return methods;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MixinTargetContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */