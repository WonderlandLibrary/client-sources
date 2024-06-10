/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Deque;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.spongepowered.asm.lib.Label;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.signature.SignatureReader;
/*      */ import org.spongepowered.asm.lib.signature.SignatureVisitor;
/*      */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldNode;
/*      */ import org.spongepowered.asm.lib.tree.LabelNode;
/*      */ import org.spongepowered.asm.lib.tree.LineNumberNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.mixin.Final;
/*      */ import org.spongepowered.asm.mixin.Intrinsic;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.Overwrite;
/*      */ import org.spongepowered.asm.mixin.injection.Inject;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyConstant;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*      */ import org.spongepowered.asm.mixin.injection.Redirect;
/*      */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.Bytecode;
/*      */ import org.spongepowered.asm.util.ConstraintParser;
/*      */ import org.spongepowered.asm.util.ITokenProvider;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
/*      */ import org.spongepowered.asm.util.throwables.ConstraintViolationException;
/*      */ import org.spongepowered.asm.util.throwables.InvalidConstraintException;
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
/*      */ class MixinApplicatorStandard
/*      */ {
/*   83 */   protected static final List<Class<? extends Annotation>> CONSTRAINED_ANNOTATIONS = (List<Class<? extends Annotation>>)ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
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
/*      */   enum ApplicatorPass
/*      */   {
/*  100 */     MAIN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  105 */     PREINJECT,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  110 */     INJECT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum InitialiserInjectionMode
/*      */   {
/*  121 */     DEFAULT,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  127 */     SAFE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class Range
/*      */   {
/*      */     final int start;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int end;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int marker;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Range(int start, int end, int marker) {
/*  157 */       this.start = start;
/*  158 */       this.end = end;
/*  159 */       this.marker = marker;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isValid() {
/*  169 */       return (this.start != 0 && this.end != 0 && this.end >= this.start);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean contains(int value) {
/*  179 */       return (value >= this.start && value <= this.end);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean excludes(int value) {
/*  188 */       return (value < this.start || value > this.end);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  196 */       return String.format("Range[%d-%d,%d,valid=%s)", new Object[] { Integer.valueOf(this.start), Integer.valueOf(this.end), Integer.valueOf(this.marker), Boolean.valueOf(isValid()) });
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
/*  209 */   protected static final int[] INITIALISER_OPCODE_BLACKLIST = new int[] { 177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  219 */   protected final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final TargetClassContext context;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String targetName;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ClassNode targetClass;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  239 */   protected final Profiler profiler = MixinEnvironment.getProfiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean mergeSignatures;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinApplicatorStandard(TargetClassContext context) {
/*  250 */     this.context = context;
/*  251 */     this.targetName = context.getClassName();
/*  252 */     this.targetClass = context.getClassNode();
/*      */     
/*  254 */     ExtensionClassExporter exporter = (ExtensionClassExporter)context.getExtensions().getExtension(ExtensionClassExporter.class);
/*  255 */     this
/*  256 */       .mergeSignatures = (exporter.isDecompilerActive() && MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void apply(SortedSet<MixinInfo> mixins) {
/*  263 */     List<MixinTargetContext> mixinContexts = new ArrayList<MixinTargetContext>();
/*      */     
/*  265 */     for (MixinInfo mixin : mixins) {
/*  266 */       this.logger.log(mixin.getLoggingLevel(), "Mixing {} from {} into {}", new Object[] { mixin.getName(), mixin.getParent(), this.targetName });
/*  267 */       mixinContexts.add(mixin.createContextFor(this.context));
/*      */     } 
/*      */     
/*  270 */     MixinTargetContext current = null;
/*      */     
/*      */     try {
/*  273 */       for (MixinTargetContext context : mixinContexts) {
/*  274 */         (current = context).preApply(this.targetName, this.targetClass);
/*      */       }
/*      */       
/*  277 */       for (ApplicatorPass pass : ApplicatorPass.values()) {
/*  278 */         Profiler.Section timer = this.profiler.begin(new String[] { "pass", pass.name().toLowerCase() });
/*  279 */         for (MixinTargetContext context : mixinContexts) {
/*  280 */           applyMixin(current = context, pass);
/*      */         }
/*  282 */         timer.end();
/*      */       } 
/*      */       
/*  285 */       for (MixinTargetContext context : mixinContexts) {
/*  286 */         (current = context).postApply(this.targetName, this.targetClass);
/*      */       }
/*  288 */     } catch (InvalidMixinException ex) {
/*  289 */       throw ex;
/*  290 */     } catch (Exception ex) {
/*  291 */       throw new InvalidMixinException(current, "Unexpecteded " + ex.getClass().getSimpleName() + " whilst applying the mixin class: " + ex
/*  292 */           .getMessage(), ex);
/*      */     } 
/*      */     
/*  295 */     applySourceMap(this.context);
/*  296 */     this.context.processDebugTasks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void applyMixin(MixinTargetContext mixin, ApplicatorPass pass) {
/*  305 */     switch (pass) {
/*      */       case MAIN:
/*  307 */         applySignature(mixin);
/*  308 */         applyInterfaces(mixin);
/*  309 */         applyAttributes(mixin);
/*  310 */         applyAnnotations(mixin);
/*  311 */         applyFields(mixin);
/*  312 */         applyMethods(mixin);
/*  313 */         applyInitialisers(mixin);
/*      */         return;
/*      */       
/*      */       case PREINJECT:
/*  317 */         prepareInjections(mixin);
/*      */         return;
/*      */       
/*      */       case INJECT:
/*  321 */         applyAccessors(mixin);
/*  322 */         applyInjections(mixin);
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  327 */     throw new IllegalStateException("Invalid pass specified " + pass);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applySignature(MixinTargetContext mixin) {
/*  332 */     if (this.mergeSignatures) {
/*  333 */       this.context.mergeSignature(mixin.getSignature());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInterfaces(MixinTargetContext mixin) {
/*  343 */     for (String interfaceName : mixin.getInterfaces()) {
/*  344 */       if (!this.targetClass.interfaces.contains(interfaceName)) {
/*  345 */         this.targetClass.interfaces.add(interfaceName);
/*  346 */         mixin.getTargetClassInfo().addInterface(interfaceName);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAttributes(MixinTargetContext mixin) {
/*  357 */     if (mixin.shouldSetSourceFile()) {
/*  358 */       this.targetClass.sourceFile = mixin.getSourceFile();
/*      */     }
/*  360 */     this.targetClass.version = Math.max(this.targetClass.version, mixin.getMinRequiredClassVersion());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAnnotations(MixinTargetContext mixin) {
/*  369 */     ClassNode sourceClass = mixin.getClassNode();
/*  370 */     Bytecode.mergeAnnotations(sourceClass, this.targetClass);
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
/*      */   protected void applyFields(MixinTargetContext mixin) {
/*  382 */     mergeShadowFields(mixin);
/*  383 */     mergeNewFields(mixin);
/*      */   }
/*      */   
/*      */   protected void mergeShadowFields(MixinTargetContext mixin) {
/*  387 */     for (Map.Entry<FieldNode, ClassInfo.Field> entry : mixin.getShadowFields()) {
/*  388 */       FieldNode shadow = entry.getKey();
/*  389 */       FieldNode target = findTargetField(shadow);
/*  390 */       if (target != null) {
/*  391 */         Bytecode.mergeAnnotations(shadow, target);
/*      */ 
/*      */         
/*  394 */         if (((ClassInfo.Field)entry.getValue()).isDecoratedMutable() && !Bytecode.hasFlag(target, 2)) {
/*  395 */           target.access &= 0xFFFFFFEF;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void mergeNewFields(MixinTargetContext mixin) {
/*  402 */     for (FieldNode field : mixin.getFields()) {
/*  403 */       FieldNode target = findTargetField(field);
/*  404 */       if (target == null) {
/*      */         
/*  406 */         this.targetClass.fields.add(field);
/*      */         
/*  408 */         if (field.signature != null) {
/*  409 */           if (this.mergeSignatures) {
/*  410 */             SignatureVisitor sv = mixin.getSignature().getRemapper();
/*  411 */             (new SignatureReader(field.signature)).accept(sv);
/*  412 */             field.signature = sv.toString(); continue;
/*      */           } 
/*  414 */           field.signature = null;
/*      */         } 
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
/*      */   protected void applyMethods(MixinTargetContext mixin) {
/*  427 */     for (MethodNode shadow : mixin.getShadowMethods()) {
/*  428 */       applyShadowMethod(mixin, shadow);
/*      */     }
/*      */     
/*  431 */     for (MethodNode mixinMethod : mixin.getMethods()) {
/*  432 */       applyNormalMethod(mixin, mixinMethod);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void applyShadowMethod(MixinTargetContext mixin, MethodNode shadow) {
/*  437 */     MethodNode target = findTargetMethod(shadow);
/*  438 */     if (target != null) {
/*  439 */       Bytecode.mergeAnnotations(shadow, target);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyNormalMethod(MixinTargetContext mixin, MethodNode mixinMethod) {
/*  445 */     mixin.transformMethod(mixinMethod);
/*      */     
/*  447 */     if (!mixinMethod.name.startsWith("<")) {
/*  448 */       checkMethodVisibility(mixin, mixinMethod);
/*  449 */       checkMethodConstraints(mixin, mixinMethod);
/*  450 */       mergeMethod(mixin, mixinMethod);
/*  451 */     } else if ("<clinit>".equals(mixinMethod.name)) {
/*      */       
/*  453 */       appendInsns(mixin, mixinMethod);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mergeMethod(MixinTargetContext mixin, MethodNode method) {
/*  464 */     boolean isOverwrite = (Annotations.getVisible(method, Overwrite.class) != null);
/*  465 */     MethodNode target = findTargetMethod(method);
/*      */     
/*  467 */     if (target != null) {
/*  468 */       if (isAlreadyMerged(mixin, method, isOverwrite, target)) {
/*      */         return;
/*      */       }
/*      */       
/*  472 */       AnnotationNode intrinsic = Annotations.getInvisible(method, Intrinsic.class);
/*  473 */       if (intrinsic != null) {
/*  474 */         if (mergeIntrinsic(mixin, method, isOverwrite, target, intrinsic)) {
/*  475 */           mixin.getTarget().methodMerged(method);
/*      */           return;
/*      */         } 
/*      */       } else {
/*  479 */         if (mixin.requireOverwriteAnnotations() && !isOverwrite) {
/*  480 */           throw new InvalidMixinException(mixin, 
/*  481 */               String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", new Object[] {
/*  482 */                   method.name, method.desc, mixin, mixin.getTarget().getClassName()
/*      */                 }));
/*      */         }
/*  485 */         this.targetClass.methods.remove(target);
/*      */       } 
/*  487 */     } else if (isOverwrite) {
/*  488 */       throw new InvalidMixinException(mixin, String.format("Overwrite target \"%s\" was not located in target class %s", new Object[] { method.name, mixin
/*  489 */               .getTargetClassRef() }));
/*      */     } 
/*      */     
/*  492 */     this.targetClass.methods.add(method);
/*  493 */     mixin.methodMerged(method);
/*      */     
/*  495 */     if (method.signature != null) {
/*  496 */       if (this.mergeSignatures) {
/*  497 */         SignatureVisitor sv = mixin.getSignature().getRemapper();
/*  498 */         (new SignatureReader(method.signature)).accept(sv);
/*  499 */         method.signature = sv.toString();
/*      */       } else {
/*  501 */         method.signature = null;
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
/*      */   protected boolean isAlreadyMerged(MixinTargetContext mixin, MethodNode method, boolean isOverwrite, MethodNode target) {
/*  518 */     AnnotationNode merged = Annotations.getVisible(target, MixinMerged.class);
/*  519 */     if (merged == null) {
/*  520 */       if (Annotations.getVisible(target, Final.class) != null) {
/*  521 */         this.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[] { method.name, mixin });
/*  522 */         return true;
/*      */       } 
/*  524 */       return false;
/*      */     } 
/*      */     
/*  527 */     String sessionId = (String)Annotations.getValue(merged, "sessionId");
/*      */     
/*  529 */     if (!this.context.getSessionId().equals(sessionId)) {
/*  530 */       throw new ClassFormatError("Invalid @MixinMerged annotation found in" + mixin + " at " + method.name + " in " + this.targetClass.name);
/*      */     }
/*      */     
/*  533 */     if (Bytecode.hasFlag(target, 4160) && 
/*  534 */       Bytecode.hasFlag(method, 4160)) {
/*  535 */       if (mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/*  536 */         this.logger.warn("Synthetic bridge method clash for {} in {}", new Object[] { method.name, mixin });
/*      */       }
/*  538 */       return true;
/*      */     } 
/*      */     
/*  541 */     String owner = (String)Annotations.getValue(merged, "mixin");
/*  542 */     int priority = ((Integer)Annotations.getValue(merged, "priority")).intValue();
/*      */     
/*  544 */     if (priority >= mixin.getPriority() && !owner.equals(mixin.getClassName())) {
/*  545 */       this.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[] { method.name, mixin, owner });
/*  546 */       return true;
/*      */     } 
/*      */     
/*  549 */     if (Annotations.getVisible(target, Final.class) != null) {
/*  550 */       this.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[] { method.name, mixin, owner });
/*  551 */       return true;
/*      */     } 
/*      */     
/*  554 */     return false;
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mergeIntrinsic(MixinTargetContext mixin, MethodNode method, boolean isOverwrite, MethodNode target, AnnotationNode intrinsic) {
/*  573 */     if (isOverwrite) {
/*  574 */       throw new InvalidMixinException(mixin, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + method.name + " in " + mixin);
/*      */     }
/*      */ 
/*      */     
/*  578 */     String methodName = method.name + method.desc;
/*  579 */     if (Bytecode.hasFlag(method, 8)) {
/*  580 */       throw new InvalidMixinException(mixin, "@Intrinsic method cannot be static, found " + methodName + " in " + mixin);
/*      */     }
/*      */     
/*  583 */     if (!Bytecode.hasFlag(method, 4096)) {
/*  584 */       AnnotationNode renamed = Annotations.getVisible(method, MixinRenamed.class);
/*  585 */       if (renamed == null || !((Boolean)Annotations.getValue(renamed, "isInterfaceMember", Boolean.FALSE)).booleanValue()) {
/*  586 */         throw new InvalidMixinException(mixin, "@Intrinsic method must be prefixed interface method, no rename encountered on " + methodName + " in " + mixin);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  591 */     if (!((Boolean)Annotations.getValue(intrinsic, "displace", Boolean.FALSE)).booleanValue()) {
/*  592 */       this.logger.log(mixin.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[] { methodName, mixin.getTargetClassRef() });
/*  593 */       return true;
/*      */     } 
/*      */     
/*  596 */     displaceIntrinsic(mixin, method, target);
/*  597 */     return false;
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
/*      */   protected void displaceIntrinsic(MixinTargetContext mixin, MethodNode method, MethodNode target) {
/*  610 */     String proxyName = "proxy+" + target.name;
/*      */     
/*  612 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  613 */       AbstractInsnNode insn = iter.next();
/*  614 */       if (insn instanceof MethodInsnNode && insn.getOpcode() != 184) {
/*  615 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  616 */         if (methodNode.owner.equals(this.targetClass.name) && methodNode.name.equals(target.name) && methodNode.desc.equals(target.desc)) {
/*  617 */           methodNode.name = proxyName;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  622 */     target.name = proxyName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void appendInsns(MixinTargetContext mixin, MethodNode method) {
/*  633 */     if (Type.getReturnType(method.desc) != Type.VOID_TYPE) {
/*  634 */       throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
/*      */     }
/*      */     
/*  637 */     MethodNode target = findTargetMethod(method);
/*      */     
/*  639 */     if (target != null) {
/*  640 */       AbstractInsnNode returnNode = Bytecode.findInsn(target, 177);
/*      */       
/*  642 */       if (returnNode != null) {
/*  643 */         Iterator<AbstractInsnNode> injectIter = method.instructions.iterator();
/*  644 */         while (injectIter.hasNext()) {
/*  645 */           AbstractInsnNode insn = injectIter.next();
/*  646 */           if (!(insn instanceof LineNumberNode) && insn.getOpcode() != 177) {
/*  647 */             target.instructions.insertBefore(returnNode, insn);
/*      */           }
/*      */         } 
/*      */         
/*  651 */         target.maxLocals = Math.max(target.maxLocals, method.maxLocals);
/*  652 */         target.maxStack = Math.max(target.maxStack, method.maxStack);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  658 */     this.targetClass.methods.add(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInitialisers(MixinTargetContext mixin) {
/*  669 */     MethodNode ctor = getConstructor(mixin);
/*  670 */     if (ctor == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  675 */     Deque<AbstractInsnNode> initialiser = getInitialiser(mixin, ctor);
/*  676 */     if (initialiser == null || initialiser.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  681 */     for (MethodNode method : this.targetClass.methods) {
/*  682 */       if ("<init>".equals(method.name)) {
/*  683 */         method.maxStack = Math.max(method.maxStack, ctor.maxStack);
/*  684 */         injectInitialiser(mixin, method, initialiser);
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
/*      */   protected MethodNode getConstructor(MixinTargetContext mixin) {
/*  696 */     MethodNode ctor = null;
/*      */     
/*  698 */     for (MethodNode mixinMethod : mixin.getMethods()) {
/*  699 */       if ("<init>".equals(mixinMethod.name) && Bytecode.methodHasLineNumbers(mixinMethod)) {
/*  700 */         if (ctor == null) {
/*  701 */           ctor = mixinMethod;
/*      */           continue;
/*      */         } 
/*  704 */         this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", new Object[] { mixin, ctor.desc }));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  709 */     return ctor;
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
/*      */   private Range getConstructorRange(MethodNode ctor) {
/*  721 */     boolean lineNumberIsValid = false;
/*  722 */     AbstractInsnNode endReturn = null;
/*      */     
/*  724 */     int line = 0, start = 0, end = 0, superIndex = -1;
/*  725 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(); iter.hasNext(); ) {
/*  726 */       AbstractInsnNode insn = iter.next();
/*  727 */       if (insn instanceof LineNumberNode) {
/*  728 */         line = ((LineNumberNode)insn).line;
/*  729 */         lineNumberIsValid = true; continue;
/*  730 */       }  if (insn instanceof MethodInsnNode) {
/*  731 */         if (insn.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)insn).name) && superIndex == -1) {
/*  732 */           superIndex = ctor.instructions.indexOf(insn);
/*  733 */           start = line;
/*      */         }  continue;
/*  735 */       }  if (insn.getOpcode() == 181) {
/*  736 */         lineNumberIsValid = false; continue;
/*  737 */       }  if (insn.getOpcode() == 177) {
/*  738 */         if (lineNumberIsValid) {
/*  739 */           end = line; continue;
/*      */         } 
/*  741 */         end = start;
/*  742 */         endReturn = insn;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  747 */     if (endReturn != null) {
/*  748 */       LabelNode label = new LabelNode(new Label());
/*  749 */       ctor.instructions.insertBefore(endReturn, (AbstractInsnNode)label);
/*  750 */       ctor.instructions.insertBefore(endReturn, (AbstractInsnNode)new LineNumberNode(start, label));
/*      */     } 
/*      */     
/*  753 */     return new Range(start, end, superIndex);
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
/*      */ 
/*      */   
/*      */   protected final Deque<AbstractInsnNode> getInitialiser(MixinTargetContext mixin, MethodNode ctor) {
/*  771 */     Range init = getConstructorRange(ctor);
/*  772 */     if (!init.isValid()) {
/*  773 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  777 */     int line = 0;
/*  778 */     Deque<AbstractInsnNode> initialiser = new ArrayDeque<AbstractInsnNode>();
/*  779 */     boolean gatherNodes = false;
/*  780 */     int trimAtOpcode = -1;
/*  781 */     LabelNode optionalInsn = null;
/*  782 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(init.marker); iter.hasNext(); ) {
/*  783 */       AbstractInsnNode insn = iter.next();
/*  784 */       if (insn instanceof LineNumberNode) {
/*  785 */         line = ((LineNumberNode)insn).line;
/*  786 */         AbstractInsnNode next = ctor.instructions.get(ctor.instructions.indexOf(insn) + 1);
/*  787 */         if (line == init.end && next.getOpcode() != 177) {
/*  788 */           gatherNodes = true;
/*  789 */           trimAtOpcode = 177; continue;
/*      */         } 
/*  791 */         gatherNodes = init.excludes(line);
/*  792 */         trimAtOpcode = -1; continue;
/*      */       } 
/*  794 */       if (gatherNodes) {
/*  795 */         if (optionalInsn != null) {
/*  796 */           initialiser.add(optionalInsn);
/*  797 */           optionalInsn = null;
/*      */         } 
/*      */         
/*  800 */         if (insn instanceof LabelNode) {
/*  801 */           optionalInsn = (LabelNode)insn; continue;
/*      */         } 
/*  803 */         int opcode = insn.getOpcode();
/*  804 */         if (opcode == trimAtOpcode) {
/*  805 */           trimAtOpcode = -1;
/*      */           continue;
/*      */         } 
/*  808 */         for (int ivalidOp : INITIALISER_OPCODE_BLACKLIST) {
/*  809 */           if (opcode == ivalidOp)
/*      */           {
/*      */             
/*  812 */             throw new InvalidMixinException(mixin, "Cannot handle " + Bytecode.getOpcodeName(opcode) + " opcode (0x" + 
/*  813 */                 Integer.toHexString(opcode).toUpperCase() + ") in class initialiser");
/*      */           }
/*      */         } 
/*      */         
/*  817 */         initialiser.add(insn);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  823 */     AbstractInsnNode last = initialiser.peekLast();
/*  824 */     if (last != null && 
/*  825 */       last.getOpcode() != 181) {
/*  826 */       throw new InvalidMixinException(mixin, "Could not parse initialiser, expected 0xB5, found 0x" + 
/*  827 */           Integer.toHexString(last.getOpcode()) + " in " + mixin);
/*      */     }
/*      */ 
/*      */     
/*  831 */     return initialiser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void injectInitialiser(MixinTargetContext mixin, MethodNode ctor, Deque<AbstractInsnNode> initialiser) {
/*  842 */     Map<LabelNode, LabelNode> labels = Bytecode.cloneLabels(ctor.instructions);
/*      */     
/*  844 */     AbstractInsnNode insn = findInitialiserInjectionPoint(mixin, ctor, initialiser);
/*  845 */     if (insn == null) {
/*  846 */       this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[] { ctor.desc });
/*      */       
/*      */       return;
/*      */     } 
/*  850 */     for (AbstractInsnNode node : initialiser) {
/*  851 */       if (node instanceof LabelNode) {
/*      */         continue;
/*      */       }
/*  854 */       if (node instanceof org.spongepowered.asm.lib.tree.JumpInsnNode) {
/*  855 */         throw new InvalidMixinException(mixin, "Unsupported JUMP opcode in initialiser in " + mixin);
/*      */       }
/*  857 */       AbstractInsnNode imACloneNow = node.clone(labels);
/*  858 */       ctor.instructions.insert(insn, imACloneNow);
/*  859 */       insn = imACloneNow;
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
/*      */   protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext mixin, MethodNode ctor, Deque<AbstractInsnNode> initialiser) {
/*  873 */     Set<String> initialisedFields = new HashSet<String>();
/*  874 */     for (AbstractInsnNode initialiserInsn : initialiser) {
/*  875 */       if (initialiserInsn.getOpcode() == 181) {
/*  876 */         initialisedFields.add(fieldKey((FieldInsnNode)initialiserInsn));
/*      */       }
/*      */     } 
/*      */     
/*  880 */     InitialiserInjectionMode mode = getInitialiserInjectionMode(mixin.getEnvironment());
/*  881 */     String targetName = mixin.getTargetClassInfo().getName();
/*  882 */     String targetSuperName = mixin.getTargetClassInfo().getSuperName();
/*  883 */     AbstractInsnNode targetInsn = null;
/*      */     
/*  885 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(); iter.hasNext(); ) {
/*  886 */       AbstractInsnNode insn = iter.next();
/*  887 */       if (insn.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)insn).name)) {
/*  888 */         String owner = ((MethodInsnNode)insn).owner;
/*  889 */         if (owner.equals(targetName) || owner.equals(targetSuperName)) {
/*  890 */           targetInsn = insn;
/*  891 */           if (mode == InitialiserInjectionMode.SAFE)
/*      */             break; 
/*      */         }  continue;
/*      */       } 
/*  895 */       if (insn.getOpcode() == 181 && mode == InitialiserInjectionMode.DEFAULT) {
/*  896 */         String key = fieldKey((FieldInsnNode)insn);
/*  897 */         if (initialisedFields.contains(key)) {
/*  898 */           targetInsn = insn;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  903 */     return targetInsn;
/*      */   }
/*      */   
/*      */   private InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment environment) {
/*  907 */     String strMode = environment.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
/*  908 */     if (strMode == null) {
/*  909 */       return InitialiserInjectionMode.DEFAULT;
/*      */     }
/*      */     try {
/*  912 */       return InitialiserInjectionMode.valueOf(strMode.toUpperCase());
/*  913 */     } catch (Exception ex) {
/*  914 */       this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[] { strMode });
/*  915 */       return InitialiserInjectionMode.DEFAULT;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String fieldKey(FieldInsnNode fieldNode) {
/*  920 */     return String.format("%s:%s", new Object[] { fieldNode.desc, fieldNode.name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void prepareInjections(MixinTargetContext mixin) {
/*  929 */     mixin.prepareInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInjections(MixinTargetContext mixin) {
/*  938 */     mixin.applyInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAccessors(MixinTargetContext mixin) {
/*  947 */     List<MethodNode> accessorMethods = mixin.generateAccessors();
/*  948 */     for (MethodNode method : accessorMethods) {
/*  949 */       if (!method.name.startsWith("<")) {
/*  950 */         mergeMethod(mixin, method);
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
/*      */   protected void checkMethodVisibility(MixinTargetContext mixin, MethodNode mixinMethod) {
/*  962 */     if (Bytecode.hasFlag(mixinMethod, 8) && 
/*  963 */       !Bytecode.hasFlag(mixinMethod, 2) && 
/*  964 */       !Bytecode.hasFlag(mixinMethod, 4096) && 
/*  965 */       Annotations.getVisible(mixinMethod, Overwrite.class) == null) {
/*  966 */       throw new InvalidMixinException(mixin, 
/*  967 */           String.format("Mixin %s contains non-private static method %s", new Object[] { mixin, mixinMethod }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void applySourceMap(TargetClassContext context) {
/*  972 */     this.targetClass.sourceDebug = context.getSourceMap().toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkMethodConstraints(MixinTargetContext mixin, MethodNode method) {
/*  982 */     for (Class<? extends Annotation> annotationType : CONSTRAINED_ANNOTATIONS) {
/*  983 */       AnnotationNode annotation = Annotations.getVisible(method, annotationType);
/*  984 */       if (annotation != null) {
/*  985 */         checkConstraints(mixin, method, annotation);
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
/*      */   protected final void checkConstraints(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/*      */     try {
/* 1000 */       ConstraintParser.Constraint constraint = ConstraintParser.parse(annotation);
/*      */       try {
/* 1002 */         constraint.check((ITokenProvider)mixin.getEnvironment());
/* 1003 */       } catch (ConstraintViolationException ex) {
/* 1004 */         String message = String.format("Constraint violation: %s on %s in %s", new Object[] { ex.getMessage(), method, mixin });
/* 1005 */         this.logger.warn(message);
/* 1006 */         if (!mixin.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
/* 1007 */           throw new InvalidMixinException(mixin, message, ex);
/*      */         }
/*      */       } 
/* 1010 */     } catch (InvalidConstraintException ex) {
/* 1011 */       throw new InvalidMixinException(mixin, ex.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final MethodNode findTargetMethod(MethodNode searchFor) {
/* 1022 */     for (MethodNode target : this.targetClass.methods) {
/* 1023 */       if (target.name.equals(searchFor.name) && target.desc.equals(searchFor.desc)) {
/* 1024 */         return target;
/*      */       }
/*      */     } 
/*      */     
/* 1028 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final FieldNode findTargetField(FieldNode searchFor) {
/* 1038 */     for (FieldNode target : this.targetClass.fields) {
/* 1039 */       if (target.name.equals(searchFor.name)) {
/* 1040 */         return target;
/*      */       }
/*      */     } 
/*      */     
/* 1044 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MixinApplicatorStandard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */