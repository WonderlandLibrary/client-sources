/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.util.Deque;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.Debug;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.struct.SourceMap;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.ClassSignature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TargetClassContext
/*     */   extends ClassContext
/*     */   implements ITargetClassContext
/*     */ {
/*  60 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MixinEnvironment env;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Extensions extensions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String sessionId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String className;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassNode classNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassInfo classInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SourceMap sourceMap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassSignature signature;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SortedSet<MixinInfo> mixins;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private final Map<String, Target> targetMethods = new HashMap<String, Target>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private final Set<MethodNode> mixinMethods = new HashSet<MethodNode>();
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextUniqueMethodIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextUniqueFieldIndex;
/*     */ 
/*     */   
/*     */   private boolean applied;
/*     */ 
/*     */   
/*     */   private boolean forceExport;
/*     */ 
/*     */ 
/*     */   
/*     */   TargetClassContext(MixinEnvironment env, Extensions extensions, String sessionId, String name, ClassNode classNode, SortedSet<MixinInfo> mixins) {
/* 137 */     this.env = env;
/* 138 */     this.extensions = extensions;
/* 139 */     this.sessionId = sessionId;
/* 140 */     this.className = name;
/* 141 */     this.classNode = classNode;
/* 142 */     this.classInfo = ClassInfo.fromClassNode(classNode);
/* 143 */     this.signature = this.classInfo.getSignature();
/* 144 */     this.mixins = mixins;
/* 145 */     this.sourceMap = new SourceMap(classNode.sourceFile);
/* 146 */     this.sourceMap.addFile(this.classNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 151 */     return this.className;
/*     */   }
/*     */   
/*     */   boolean isApplied() {
/* 155 */     return this.applied;
/*     */   }
/*     */   
/*     */   boolean isExportForced() {
/* 159 */     return this.forceExport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Extensions getExtensions() {
/* 166 */     return this.extensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getSessionId() {
/* 173 */     return this.sessionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getClassRef() {
/* 181 */     return this.classNode.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getClassName() {
/* 188 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassNode getClassNode() {
/* 196 */     return this.classNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<MethodNode> getMethods() {
/* 203 */     return this.classNode.methods;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<FieldNode> getFields() {
/* 210 */     return this.classNode.fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassInfo getClassInfo() {
/* 218 */     return this.classInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SortedSet<MixinInfo> getMixins() {
/* 225 */     return this.mixins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SourceMap getSourceMap() {
/* 232 */     return this.sourceMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void mergeSignature(ClassSignature signature) {
/* 241 */     this.signature.merge(signature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addMixinMethod(MethodNode method) {
/* 250 */     this.mixinMethods.add(method);
/*     */   }
/*     */   
/*     */   void methodMerged(MethodNode method) {
/* 254 */     if (!this.mixinMethods.remove(method)) {
/* 255 */       logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[] { method.name, method.desc, this });
/*     */     }
/*     */   }
/*     */   
/*     */   MethodNode findMethod(Deque<String> aliases, String desc) {
/* 260 */     return findAliasedMethod(aliases, desc, true);
/*     */   }
/*     */   
/*     */   MethodNode findAliasedMethod(Deque<String> aliases, String desc) {
/* 264 */     return findAliasedMethod(aliases, desc, false);
/*     */   }
/*     */   
/*     */   private MethodNode findAliasedMethod(Deque<String> aliases, String desc, boolean includeMixinMethods) {
/* 268 */     String alias = aliases.poll();
/* 269 */     if (alias == null) {
/* 270 */       return null;
/*     */     }
/*     */     
/* 273 */     for (MethodNode target : this.classNode.methods) {
/* 274 */       if (target.name.equals(alias) && target.desc.equals(desc)) {
/* 275 */         return target;
/*     */       }
/*     */     } 
/*     */     
/* 279 */     if (includeMixinMethods) {
/* 280 */       for (MethodNode target : this.mixinMethods) {
/* 281 */         if (target.name.equals(alias) && target.desc.equals(desc)) {
/* 282 */           return target;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 287 */     return findAliasedMethod(aliases, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FieldNode findAliasedField(Deque<String> aliases, String desc) {
/* 298 */     String alias = aliases.poll();
/* 299 */     if (alias == null) {
/* 300 */       return null;
/*     */     }
/*     */     
/* 303 */     for (FieldNode target : this.classNode.fields) {
/* 304 */       if (target.name.equals(alias) && target.desc.equals(desc)) {
/* 305 */         return target;
/*     */       }
/*     */     } 
/*     */     
/* 309 */     return findAliasedField(aliases, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Target getTargetMethod(MethodNode method) {
/* 319 */     if (!this.classNode.methods.contains(method)) {
/* 320 */       throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
/*     */     }
/*     */     
/* 323 */     String targetName = method.name + method.desc;
/* 324 */     Target target = this.targetMethods.get(targetName);
/* 325 */     if (target == null) {
/* 326 */       target = new Target(this.classNode, method);
/* 327 */       this.targetMethods.put(targetName, target);
/*     */     } 
/* 329 */     return target;
/*     */   }
/*     */   
/*     */   String getUniqueName(MethodNode method, boolean preservePrefix) {
/* 333 */     String uniqueIndex = Integer.toHexString(this.nextUniqueMethodIndex++);
/* 334 */     String pattern = preservePrefix ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s";
/* 335 */     return String.format(pattern, new Object[] { this.sessionId.substring(30), method.name, uniqueIndex });
/*     */   }
/*     */   
/*     */   String getUniqueName(FieldNode field) {
/* 339 */     String uniqueIndex = Integer.toHexString(this.nextUniqueFieldIndex++);
/* 340 */     return String.format("fd%s$%s$%s", new Object[] { this.sessionId.substring(30), field.name, uniqueIndex });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void applyMixins() {
/* 347 */     if (this.applied) {
/* 348 */       throw new IllegalStateException("Mixins already applied to target class " + this.className);
/*     */     }
/* 350 */     this.applied = true;
/*     */     
/* 352 */     MixinApplicatorStandard applicator = createApplicator();
/* 353 */     applicator.apply(this.mixins);
/* 354 */     applySignature();
/* 355 */     upgradeMethods();
/* 356 */     checkMerges();
/*     */   }
/*     */   
/*     */   private MixinApplicatorStandard createApplicator() {
/* 360 */     if (this.classInfo.isInterface()) {
/* 361 */       return new MixinApplicatorInterface(this);
/*     */     }
/* 363 */     return new MixinApplicatorStandard(this);
/*     */   }
/*     */   
/*     */   private void applySignature() {
/* 367 */     (getClassNode()).signature = this.signature.toString();
/*     */   }
/*     */   
/*     */   private void checkMerges() {
/* 371 */     for (MethodNode method : this.mixinMethods) {
/* 372 */       if (!method.name.startsWith("<")) {
/* 373 */         logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[] { method.name, method.desc, this });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void processDebugTasks() {
/* 382 */     if (!this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/*     */       return;
/*     */     }
/*     */     
/* 386 */     AnnotationNode classDebugAnnotation = Annotations.getVisible(this.classNode, Debug.class);
/* 387 */     if (classDebugAnnotation != null) {
/* 388 */       this.forceExport = Boolean.TRUE.equals(Annotations.getValue(classDebugAnnotation, "export"));
/* 389 */       if (Boolean.TRUE.equals(Annotations.getValue(classDebugAnnotation, "print"))) {
/* 390 */         Bytecode.textify(this.classNode, System.err);
/*     */       }
/*     */     } 
/*     */     
/* 394 */     for (MethodNode method : this.classNode.methods) {
/* 395 */       AnnotationNode methodDebugAnnotation = Annotations.getVisible(method, Debug.class);
/* 396 */       if (methodDebugAnnotation != null && Boolean.TRUE.equals(Annotations.getValue(methodDebugAnnotation, "print")))
/* 397 */         Bytecode.textify(method, System.err); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\TargetClassContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */