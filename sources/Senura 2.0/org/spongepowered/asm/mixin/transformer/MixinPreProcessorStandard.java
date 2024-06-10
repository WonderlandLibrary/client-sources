/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Iterator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.Dynamic;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
/*     */ import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.perf.Profiler;
/*     */ import org.spongepowered.asm.util.throwables.SyntheticBridgeException;
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
/*     */ class MixinPreProcessorStandard
/*     */ {
/*     */   enum SpecialMethod
/*     */   {
/*  95 */     MERGE(true),
/*  96 */     OVERWRITE(true, Overwrite.class),
/*  97 */     SHADOW(false, Shadow.class),
/*  98 */     ACCESSOR(false, Accessor.class),
/*  99 */     INVOKER(false, Invoker.class);
/*     */     
/*     */     final boolean isOverwrite;
/*     */     
/*     */     final Class<? extends Annotation> annotation;
/*     */     
/*     */     final String description;
/*     */     
/*     */     SpecialMethod(boolean isOverwrite, Class<? extends Annotation> type) {
/* 108 */       this.isOverwrite = isOverwrite;
/* 109 */       this.annotation = type;
/* 110 */       this.description = "@" + Bytecode.getSimpleName(type);
/*     */     }
/*     */     
/*     */     SpecialMethod(boolean isOverwrite) {
/* 114 */       this.isOverwrite = isOverwrite;
/* 115 */       this.annotation = null;
/* 116 */       this.description = "overwrite";
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 121 */       return this.description;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MixinInfo mixin;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MixinInfo.MixinClassNode classNode;
/*     */ 
/*     */   
/*     */   protected final MixinEnvironment env;
/*     */ 
/*     */   
/* 143 */   protected final Profiler profiler = MixinEnvironment.getProfiler();
/*     */   private final boolean verboseLogging;
/*     */   private final boolean strictUnique;
/*     */   private boolean prepared;
/*     */   private boolean attached;
/*     */   
/*     */   MixinPreProcessorStandard(MixinInfo mixin, MixinInfo.MixinClassNode classNode) {
/* 150 */     this.mixin = mixin;
/* 151 */     this.classNode = classNode;
/* 152 */     this.env = mixin.getParent().getEnvironment();
/* 153 */     this.verboseLogging = this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
/* 154 */     this.strictUnique = this.env.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final MixinPreProcessorStandard prepare() {
/* 163 */     if (this.prepared) {
/* 164 */       return this;
/*     */     }
/*     */     
/* 167 */     this.prepared = true;
/*     */     
/* 169 */     Profiler.Section prepareTimer = this.profiler.begin("prepare");
/*     */     
/* 171 */     for (MixinInfo.MixinMethodNode mixinMethod : this.classNode.mixinMethods) {
/* 172 */       ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod);
/* 173 */       prepareMethod(mixinMethod, method);
/*     */     } 
/*     */     
/* 176 */     for (FieldNode mixinField : this.classNode.fields) {
/* 177 */       prepareField(mixinField);
/*     */     }
/*     */     
/* 180 */     prepareTimer.end();
/* 181 */     return this;
/*     */   }
/*     */   
/*     */   protected void prepareMethod(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 185 */     prepareShadow(mixinMethod, method);
/* 186 */     prepareSoftImplements(mixinMethod, method);
/*     */   }
/*     */   
/*     */   protected void prepareShadow(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 190 */     AnnotationNode shadowAnnotation = Annotations.getVisible(mixinMethod, Shadow.class);
/* 191 */     if (shadowAnnotation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 195 */     String prefix = (String)Annotations.getValue(shadowAnnotation, "prefix", Shadow.class);
/* 196 */     if (mixinMethod.name.startsWith(prefix)) {
/* 197 */       Annotations.setVisible(mixinMethod, MixinRenamed.class, new Object[] { "originalName", mixinMethod.name });
/* 198 */       String newName = mixinMethod.name.substring(prefix.length());
/* 199 */       mixinMethod.name = method.renameTo(newName);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void prepareSoftImplements(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 204 */     for (InterfaceInfo iface : this.mixin.getSoftImplements()) {
/* 205 */       if (iface.renameMethod(mixinMethod)) {
/* 206 */         method.renameTo(mixinMethod.name);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prepareField(FieldNode mixinField) {}
/*     */ 
/*     */   
/*     */   final MixinPreProcessorStandard conform(TargetClassContext target) {
/* 216 */     return conform(target.getClassInfo());
/*     */   }
/*     */   
/*     */   final MixinPreProcessorStandard conform(ClassInfo target) {
/* 220 */     Profiler.Section conformTimer = this.profiler.begin("conform");
/*     */     
/* 222 */     for (MixinInfo.MixinMethodNode mixinMethod : this.classNode.mixinMethods) {
/* 223 */       if (mixinMethod.isInjector()) {
/* 224 */         ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod, 10);
/* 225 */         conformInjector(target, mixinMethod, method);
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     conformTimer.end();
/* 230 */     return this;
/*     */   }
/*     */   
/*     */   private void conformInjector(ClassInfo targetClass, MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 234 */     MethodMapper methodMapper = targetClass.getMethodMapper();
/* 235 */     methodMapper.remapHandlerMethod(this.mixin, mixinMethod, method);
/*     */   }
/*     */   
/*     */   MixinTargetContext createContextFor(TargetClassContext target) {
/* 239 */     MixinTargetContext context = new MixinTargetContext(this.mixin, this.classNode, target);
/* 240 */     conform(target);
/* 241 */     attach(context);
/* 242 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final MixinPreProcessorStandard attach(MixinTargetContext context) {
/* 251 */     if (this.attached) {
/* 252 */       throw new IllegalStateException("Preprocessor was already attached");
/*     */     }
/*     */     
/* 255 */     this.attached = true;
/*     */     
/* 257 */     Profiler.Section attachTimer = this.profiler.begin("attach");
/*     */ 
/*     */     
/* 260 */     Profiler.Section timer = this.profiler.begin("methods");
/* 261 */     attachMethods(context);
/* 262 */     timer = timer.next("fields");
/* 263 */     attachFields(context);
/*     */ 
/*     */     
/* 266 */     timer = timer.next("transform");
/* 267 */     transform(context);
/* 268 */     timer.end();
/*     */     
/* 270 */     attachTimer.end();
/* 271 */     return this;
/*     */   }
/*     */   
/*     */   protected void attachMethods(MixinTargetContext context) {
/* 275 */     for (Iterator<MixinInfo.MixinMethodNode> iter = this.classNode.mixinMethods.iterator(); iter.hasNext(); ) {
/* 276 */       MixinInfo.MixinMethodNode mixinMethod = iter.next();
/*     */       
/* 278 */       if (!validateMethod(context, mixinMethod)) {
/* 279 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 283 */       if (attachInjectorMethod(context, mixinMethod)) {
/* 284 */         context.addMixinMethod(mixinMethod);
/*     */         
/*     */         continue;
/*     */       } 
/* 288 */       if (attachAccessorMethod(context, mixinMethod)) {
/* 289 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 293 */       if (attachShadowMethod(context, mixinMethod)) {
/* 294 */         context.addShadowMethod(mixinMethod);
/* 295 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 299 */       if (attachOverwriteMethod(context, mixinMethod)) {
/* 300 */         context.addMixinMethod(mixinMethod);
/*     */         
/*     */         continue;
/*     */       } 
/* 304 */       if (attachUniqueMethod(context, mixinMethod)) {
/* 305 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 309 */       attachMethod(context, mixinMethod);
/* 310 */       context.addMixinMethod(mixinMethod);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean validateMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 315 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean attachInjectorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 319 */     return mixinMethod.isInjector();
/*     */   }
/*     */   
/*     */   protected boolean attachAccessorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 323 */     return (attachAccessorMethod(context, mixinMethod, SpecialMethod.ACCESSOR) || 
/* 324 */       attachAccessorMethod(context, mixinMethod, SpecialMethod.INVOKER));
/*     */   }
/*     */   
/*     */   protected boolean attachAccessorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
/* 328 */     AnnotationNode annotation = mixinMethod.getVisibleAnnotation(type.annotation);
/* 329 */     if (annotation == null) {
/* 330 */       return false;
/*     */     }
/*     */     
/* 333 */     String description = type + " method " + mixinMethod.name;
/* 334 */     ClassInfo.Method method = getSpecialMethod(mixinMethod, type);
/* 335 */     if (MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8) && method.isStatic()) {
/* 336 */       if (this.mixin.getTargets().size() > 1) {
/* 337 */         throw new InvalidAccessorException(context, description + " in multi-target mixin is invalid. Mixin must have exactly 1 target.");
/*     */       }
/*     */       
/* 340 */       String uniqueName = context.getUniqueName(mixinMethod, true);
/* 341 */       logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique method {}{} to {} in {}", new Object[] { mixinMethod.name, mixinMethod.desc, uniqueName, this.mixin });
/*     */       
/* 343 */       mixinMethod.name = method.renameTo(uniqueName);
/*     */     } else {
/*     */       
/* 346 */       if (!method.isAbstract()) {
/* 347 */         throw new InvalidAccessorException(context, description + " is not abstract");
/*     */       }
/*     */       
/* 350 */       if (method.isStatic()) {
/* 351 */         throw new InvalidAccessorException(context, description + " cannot be static");
/*     */       }
/*     */     } 
/*     */     
/* 355 */     context.addAccessorMethod(mixinMethod, type.annotation);
/* 356 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean attachShadowMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 360 */     return attachSpecialMethod(context, mixinMethod, SpecialMethod.SHADOW);
/*     */   }
/*     */   
/*     */   protected boolean attachOverwriteMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 364 */     return attachSpecialMethod(context, mixinMethod, SpecialMethod.OVERWRITE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean attachSpecialMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
/* 369 */     AnnotationNode annotation = mixinMethod.getVisibleAnnotation(type.annotation);
/* 370 */     if (annotation == null) {
/* 371 */       return false;
/*     */     }
/*     */     
/* 374 */     if (type.isOverwrite) {
/* 375 */       checkMixinNotUnique(mixinMethod, type);
/*     */     }
/*     */     
/* 378 */     ClassInfo.Method method = getSpecialMethod(mixinMethod, type);
/* 379 */     MethodNode target = context.findMethod(mixinMethod, annotation);
/* 380 */     if (target == null) {
/* 381 */       if (type.isOverwrite) {
/* 382 */         return false;
/*     */       }
/* 384 */       target = context.findRemappedMethod(mixinMethod);
/* 385 */       if (target == null)
/* 386 */         throw new InvalidMixinException(this.mixin, 
/* 387 */             String.format("%s method %s in %s was not located in the target class %s. %s%s", new Object[] {
/* 388 */                 type, mixinMethod.name, this.mixin, context.getTarget(), context.getReferenceMapper().getStatus(), 
/* 389 */                 getDynamicInfo(mixinMethod)
/*     */               })); 
/* 391 */       mixinMethod.name = method.renameTo(target.name);
/*     */     } 
/*     */     
/* 394 */     if ("<init>".equals(target.name)) {
/* 395 */       throw new InvalidMixinException(this.mixin, String.format("Nice try! %s in %s cannot alias a constructor", new Object[] { mixinMethod.name, this.mixin }));
/*     */     }
/*     */     
/* 398 */     if (!Bytecode.compareFlags(mixinMethod, target, 8)) {
/* 399 */       throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of %s method %s in %s does not match the target", new Object[] { type, mixinMethod.name, this.mixin }));
/*     */     }
/*     */ 
/*     */     
/* 403 */     conformVisibility(context, mixinMethod, type, target);
/*     */     
/* 405 */     if (!target.name.equals(mixinMethod.name)) {
/* 406 */       if (type.isOverwrite && (target.access & 0x2) == 0) {
/* 407 */         throw new InvalidMixinException(this.mixin, "Non-private method cannot be aliased. Found " + target.name);
/*     */       }
/*     */       
/* 410 */       mixinMethod.name = method.renameTo(target.name);
/*     */     } 
/*     */     
/* 413 */     return true;
/*     */   }
/*     */   
/*     */   private void conformVisibility(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type, MethodNode target) {
/* 417 */     Bytecode.Visibility visTarget = Bytecode.getVisibility(target);
/* 418 */     Bytecode.Visibility visMethod = Bytecode.getVisibility(mixinMethod);
/* 419 */     if (visMethod.ordinal() >= visTarget.ordinal()) {
/* 420 */       if (visTarget == Bytecode.Visibility.PRIVATE && visMethod.ordinal() > Bytecode.Visibility.PRIVATE.ordinal()) {
/* 421 */         context.getTarget().addUpgradedMethod(target);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 426 */     String message = String.format("%s %s method %s in %s cannot reduce visibiliy of %s target method", new Object[] { visMethod, type, mixinMethod.name, this.mixin, visTarget });
/*     */ 
/*     */     
/* 429 */     if (type.isOverwrite && !this.mixin.getParent().conformOverwriteVisibility()) {
/* 430 */       throw new InvalidMixinException(this.mixin, message);
/*     */     }
/*     */     
/* 433 */     if (visMethod == Bytecode.Visibility.PRIVATE) {
/* 434 */       if (type.isOverwrite) {
/* 435 */         logger.warn("Static binding violation: {}, visibility will be upgraded.", new Object[] { message });
/*     */       }
/* 437 */       context.addUpgradedMethod(mixinMethod);
/* 438 */       Bytecode.setVisibility(mixinMethod, visTarget);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ClassInfo.Method getSpecialMethod(MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
/* 443 */     ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod, 10);
/* 444 */     checkMethodNotUnique(method, type);
/* 445 */     return method;
/*     */   }
/*     */   
/*     */   protected void checkMethodNotUnique(ClassInfo.Method method, SpecialMethod type) {
/* 449 */     if (method.isUnique()) {
/* 450 */       throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s cannot be @Unique", new Object[] { type, method.getName(), this.mixin }));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void checkMixinNotUnique(MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
/* 455 */     if (this.mixin.isUnique()) {
/* 456 */       throw new InvalidMixinException(this.mixin, String.format("%s method %s found in a @Unique mixin %s", new Object[] { type, mixinMethod.name, this.mixin }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean attachUniqueMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 462 */     ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod, 10);
/* 463 */     if (method == null || (!method.isUnique() && !this.mixin.isUnique() && !method.isSynthetic())) {
/* 464 */       return false;
/*     */     }
/*     */     
/* 467 */     if (method.isSynthetic()) {
/* 468 */       context.transformDescriptor(mixinMethod);
/* 469 */       method.remapTo(mixinMethod.desc);
/*     */     } 
/*     */     
/* 472 */     MethodNode target = context.findMethod(mixinMethod, (AnnotationNode)null);
/* 473 */     if (target == null) {
/* 474 */       return false;
/*     */     }
/*     */     
/* 477 */     String type = method.isSynthetic() ? "synthetic" : "@Unique";
/*     */     
/* 479 */     if (Bytecode.getVisibility(mixinMethod).ordinal() < Bytecode.Visibility.PUBLIC.ordinal()) {
/* 480 */       String uniqueName = context.getUniqueName(mixinMethod, false);
/* 481 */       logger.log(this.mixin.getLoggingLevel(), "Renaming {} method {}{} to {} in {}", new Object[] { type, mixinMethod.name, mixinMethod.desc, uniqueName, this.mixin });
/*     */       
/* 483 */       mixinMethod.name = method.renameTo(uniqueName);
/* 484 */       return false;
/*     */     } 
/*     */     
/* 487 */     if (this.strictUnique) {
/* 488 */       throw new InvalidMixinException(this.mixin, String.format("Method conflict, %s method %s in %s cannot overwrite %s%s in %s", new Object[] { type, mixinMethod.name, this.mixin, target.name, target.desc, context
/* 489 */               .getTarget() }));
/*     */     }
/*     */     
/* 492 */     AnnotationNode unique = Annotations.getVisible(mixinMethod, Unique.class);
/* 493 */     if (unique == null || !((Boolean)Annotations.getValue(unique, "silent", Boolean.FALSE)).booleanValue()) {
/* 494 */       if (Bytecode.hasFlag(mixinMethod, 64)) {
/*     */         
/*     */         try {
/* 497 */           Bytecode.compareBridgeMethods(target, mixinMethod);
/* 498 */           logger.debug("Discarding sythetic bridge method {} in {} because existing method in {} is compatible", new Object[] { type, mixinMethod.name, this.mixin, context
/* 499 */                 .getTarget() });
/* 500 */           return true;
/* 501 */         } catch (SyntheticBridgeException ex) {
/* 502 */           if (this.verboseLogging || this.env.getOption(MixinEnvironment.Option.DEBUG_VERIFY))
/*     */           {
/* 504 */             ex.printAnalysis(context, target, mixinMethod);
/*     */           }
/* 506 */           throw new InvalidMixinException(this.mixin, ex.getMessage());
/*     */         } 
/*     */       }
/*     */       
/* 510 */       logger.warn("Discarding {} public method {} in {} because it already exists in {}", new Object[] { type, mixinMethod.name, this.mixin, context
/* 511 */             .getTarget() });
/* 512 */       return true;
/*     */     } 
/*     */     
/* 515 */     context.addMixinMethod(mixinMethod);
/* 516 */     return true;
/*     */   }
/*     */   
/*     */   protected void attachMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 520 */     ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod);
/* 521 */     if (method == null) {
/*     */       return;
/*     */     }
/*     */     
/* 525 */     ClassInfo.Method parentMethod = this.mixin.getClassInfo().findMethodInHierarchy(mixinMethod, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
/* 526 */     if (parentMethod != null && parentMethod.isRenamed()) {
/* 527 */       mixinMethod.name = method.renameTo(parentMethod.getName());
/*     */     }
/*     */     
/* 530 */     MethodNode target = context.findMethod(mixinMethod, (AnnotationNode)null);
/* 531 */     if (target != null) {
/* 532 */       conformVisibility(context, mixinMethod, SpecialMethod.MERGE, target);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void attachFields(MixinTargetContext context) {
/* 537 */     for (Iterator<FieldNode> iter = this.classNode.fields.iterator(); iter.hasNext(); ) {
/* 538 */       FieldNode mixinField = iter.next();
/* 539 */       AnnotationNode shadow = Annotations.getVisible(mixinField, Shadow.class);
/* 540 */       boolean isShadow = (shadow != null);
/*     */       
/* 542 */       if (!validateField(context, mixinField, shadow)) {
/* 543 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 547 */       ClassInfo.Field field = this.mixin.getClassInfo().findField(mixinField);
/* 548 */       context.transformDescriptor(mixinField);
/* 549 */       field.remapTo(mixinField.desc);
/*     */       
/* 551 */       if (field.isUnique() && isShadow) {
/* 552 */         throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s cannot be @Unique", new Object[] { mixinField.name }));
/*     */       }
/*     */       
/* 555 */       FieldNode target = context.findField(mixinField, shadow);
/* 556 */       if (target == null) {
/* 557 */         if (shadow == null) {
/*     */           continue;
/*     */         }
/* 560 */         target = context.findRemappedField(mixinField);
/* 561 */         if (target == null)
/*     */         {
/* 563 */           throw new InvalidMixinException(this.mixin, String.format("Shadow field %s was not located in the target class %s. %s%s", new Object[] { mixinField.name, context
/* 564 */                   .getTarget(), context.getReferenceMapper().getStatus(), 
/* 565 */                   getDynamicInfo(mixinField) }));
/*     */         }
/* 567 */         mixinField.name = field.renameTo(target.name);
/*     */       } 
/*     */       
/* 570 */       if (!Bytecode.compareFlags(mixinField, target, 8)) {
/* 571 */         throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of @Shadow field %s in %s does not match the target", new Object[] { mixinField.name, this.mixin }));
/*     */       }
/*     */ 
/*     */       
/* 575 */       if (field.isUnique()) {
/* 576 */         if ((mixinField.access & 0x6) != 0) {
/* 577 */           String uniqueName = context.getUniqueName(mixinField);
/* 578 */           logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique field {}{} to {} in {}", new Object[] { mixinField.name, mixinField.desc, uniqueName, this.mixin });
/*     */           
/* 580 */           mixinField.name = field.renameTo(uniqueName);
/*     */           
/*     */           continue;
/*     */         } 
/* 584 */         if (this.strictUnique) {
/* 585 */           throw new InvalidMixinException(this.mixin, String.format("Field conflict, @Unique field %s in %s cannot overwrite %s%s in %s", new Object[] { mixinField.name, this.mixin, target.name, target.desc, context
/* 586 */                   .getTarget() }));
/*     */         }
/*     */         
/* 589 */         logger.warn("Discarding @Unique public field {} in {} because it already exists in {}. Note that declared FIELD INITIALISERS will NOT be removed!", new Object[] { mixinField.name, this.mixin, context
/* 590 */               .getTarget() });
/*     */         
/* 592 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 597 */       if (!target.desc.equals(mixinField.desc)) {
/* 598 */         throw new InvalidMixinException(this.mixin, String.format("The field %s in the target class has a conflicting signature", new Object[] { mixinField.name }));
/*     */       }
/*     */ 
/*     */       
/* 602 */       if (!target.name.equals(mixinField.name)) {
/* 603 */         if ((target.access & 0x2) == 0 && (target.access & 0x1000) == 0) {
/* 604 */           throw new InvalidMixinException(this.mixin, "Non-private field cannot be aliased. Found " + target.name);
/*     */         }
/*     */         
/* 607 */         mixinField.name = field.renameTo(target.name);
/*     */       } 
/*     */ 
/*     */       
/* 611 */       iter.remove();
/*     */       
/* 613 */       if (isShadow) {
/* 614 */         boolean isFinal = field.isDecoratedFinal();
/* 615 */         if (this.verboseLogging && Bytecode.hasFlag(target, 16) != isFinal) {
/* 616 */           String message = isFinal ? "@Shadow field {}::{} is decorated with @Final but target is not final" : "@Shadow target {}::{} is final but shadow is not decorated with @Final";
/*     */ 
/*     */           
/* 619 */           logger.warn(message, new Object[] { this.mixin, mixinField.name });
/*     */         } 
/*     */         
/* 622 */         context.addShadowField(mixinField, field);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean validateField(MixinTargetContext context, FieldNode field, AnnotationNode shadow) {
/* 629 */     if (Bytecode.hasFlag(field, 8) && 
/* 630 */       !Bytecode.hasFlag(field, 2) && 
/* 631 */       !Bytecode.hasFlag(field, 4096) && shadow == null)
/*     */     {
/* 633 */       throw new InvalidMixinException(context, String.format("Mixin %s contains non-private static field %s:%s", new Object[] { context, field.name, field.desc }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 638 */     String prefix = (String)Annotations.getValue(shadow, "prefix", Shadow.class);
/* 639 */     if (field.name.startsWith(prefix)) {
/* 640 */       throw new InvalidMixinException(context, String.format("@Shadow field %s.%s has a shadow prefix. This is not allowed.", new Object[] { context, field.name }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 645 */     if ("super$".equals(field.name)) {
/* 646 */       if (field.access != 2) {
/* 647 */         throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must be private and non-final", new Object[] { context, field.name }));
/*     */       }
/*     */       
/* 650 */       if (!field.desc.equals("L" + this.mixin.getClassRef() + ";"))
/* 651 */         throw new InvalidMixinException(this.mixin, 
/* 652 */             String.format("Imaginary super field %s.%s must have the same type as the parent mixin (%s)", new Object[] {
/* 653 */                 context, field.name, this.mixin.getClassName()
/*     */               })); 
/* 655 */       return false;
/*     */     } 
/*     */     
/* 658 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void transform(MixinTargetContext context) {
/* 666 */     for (MethodNode mixinMethod : this.classNode.methods) {
/* 667 */       for (Iterator<AbstractInsnNode> iter = mixinMethod.instructions.iterator(); iter.hasNext(); ) {
/* 668 */         AbstractInsnNode insn = iter.next();
/* 669 */         if (insn instanceof MethodInsnNode) {
/* 670 */           transformMethod((MethodInsnNode)insn); continue;
/* 671 */         }  if (insn instanceof FieldInsnNode) {
/* 672 */           transformField((FieldInsnNode)insn);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void transformMethod(MethodInsnNode methodNode) {
/* 679 */     Profiler.Section metaTimer = this.profiler.begin("meta");
/* 680 */     ClassInfo owner = ClassInfo.forName(methodNode.owner);
/* 681 */     if (owner == null) {
/* 682 */       throw new RuntimeException(new ClassNotFoundException(methodNode.owner.replace('/', '.')));
/*     */     }
/*     */     
/* 685 */     ClassInfo.Method method = owner.findMethodInHierarchy(methodNode, ClassInfo.SearchType.ALL_CLASSES, 2);
/* 686 */     metaTimer.end();
/*     */     
/* 688 */     if (method != null && method.isRenamed()) {
/* 689 */       methodNode.name = method.getName();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void transformField(FieldInsnNode fieldNode) {
/* 694 */     Profiler.Section metaTimer = this.profiler.begin("meta");
/* 695 */     ClassInfo owner = ClassInfo.forName(fieldNode.owner);
/* 696 */     if (owner == null) {
/* 697 */       throw new RuntimeException(new ClassNotFoundException(fieldNode.owner.replace('/', '.')));
/*     */     }
/*     */     
/* 700 */     ClassInfo.Field field = owner.findField(fieldNode, 2);
/* 701 */     metaTimer.end();
/*     */     
/* 703 */     if (field != null && field.isRenamed()) {
/* 704 */       fieldNode.name = field.getName();
/*     */     }
/*     */   }
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
/*     */   protected static String getDynamicInfo(MethodNode method) {
/* 718 */     return getDynamicInfo("Method", Annotations.getInvisible(method, Dynamic.class));
/*     */   }
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
/*     */   protected static String getDynamicInfo(FieldNode method) {
/* 731 */     return getDynamicInfo("Field", Annotations.getInvisible(method, Dynamic.class));
/*     */   }
/*     */   
/*     */   private static String getDynamicInfo(String targetType, AnnotationNode annotation) {
/* 735 */     String description = Strings.nullToEmpty((String)Annotations.getValue(annotation));
/* 736 */     Type upstream = (Type)Annotations.getValue(annotation, "mixin");
/* 737 */     if (upstream != null) {
/* 738 */       description = String.format("{%s} %s", new Object[] { upstream.getClassName(), description }).trim();
/*     */     }
/* 740 */     return (description.length() > 0) ? String.format(" %s is @Dynamic(%s)", new Object[] { targetType, description }) : "";
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MixinPreProcessorStandard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */