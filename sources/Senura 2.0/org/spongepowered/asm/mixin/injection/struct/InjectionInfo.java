/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.Dynamic;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyConstant;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.code.ISliceContext;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
/*     */ import org.spongepowered.asm.mixin.injection.code.MethodSlice;
/*     */ import org.spongepowered.asm.mixin.injection.code.MethodSlices;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*     */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ public abstract class InjectionInfo
/*     */   extends SpecialMethodInfo
/*     */   implements ISliceContext
/*     */ {
/*     */   protected final boolean isStatic;
/*  85 */   protected final Deque<MethodNode> targets = new ArrayDeque<MethodNode>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MethodSlices slices;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String atKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   protected final List<InjectionPoint> injectionPoints = new ArrayList<InjectionPoint>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   protected final Map<Target, List<InjectionNodes.InjectionNode>> targetNodes = new LinkedHashMap<Target, List<InjectionNodes.InjectionNode>>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Injector injector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InjectorGroupInfo group;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   private final List<MethodNode> injectedMethods = new ArrayList<MethodNode>(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private int expectedCallbackCount = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   private int requiredCallbackCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private int maxCallbackCount = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   private int injectedCallbackCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 151 */     this(mixin, method, annotation, "at");
/*     */   }
/*     */   
/*     */   protected InjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
/* 155 */     super(mixin, method, annotation);
/* 156 */     this.isStatic = Bytecode.methodIsStatic(method);
/* 157 */     this.slices = MethodSlices.parse(this);
/* 158 */     this.atKey = atKey;
/* 159 */     readAnnotation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readAnnotation() {
/* 166 */     if (this.annotation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     String type = "@" + Bytecode.getSimpleName(this.annotation);
/* 171 */     List<AnnotationNode> injectionPoints = readInjectionPoints(type);
/* 172 */     findMethods(parseTargets(type), type);
/* 173 */     parseInjectionPoints(injectionPoints);
/* 174 */     parseRequirements();
/* 175 */     this.injector = parseInjector(this.annotation);
/*     */   }
/*     */   
/*     */   protected Set<MemberInfo> parseTargets(String type) {
/* 179 */     List<String> methods = Annotations.getValue(this.annotation, "method", false);
/* 180 */     if (methods == null) {
/* 181 */       throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", new Object[] { type, this.method.name }));
/*     */     }
/*     */     
/* 184 */     Set<MemberInfo> members = new LinkedHashSet<MemberInfo>();
/* 185 */     for (String method : methods) {
/*     */       try {
/* 187 */         MemberInfo targetMember = MemberInfo.parseAndValidate(method, (IMixinContext)this.mixin);
/* 188 */         if (targetMember.owner != null && !targetMember.owner.equals(this.mixin.getTargetClassRef())) {
/* 189 */           throw new InvalidInjectionException(this, 
/* 190 */               String.format("%s annotation on %s specifies a target class '%s', which is not supported", new Object[] { type, this.method.name, targetMember.owner }));
/*     */         }
/*     */         
/* 193 */         members.add(targetMember);
/* 194 */       } catch (InvalidMemberDescriptorException ex) {
/* 195 */         throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", new Object[] { type, this.method.name, method, this.mixin
/* 196 */                 .getReferenceMapper().getStatus() }));
/*     */       } 
/*     */     } 
/* 199 */     return members;
/*     */   }
/*     */   
/*     */   protected List<AnnotationNode> readInjectionPoints(String type) {
/* 203 */     List<AnnotationNode> ats = Annotations.getValue(this.annotation, this.atKey, false);
/* 204 */     if (ats == null) {
/* 205 */       throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", new Object[] { type, this.method.name, this.atKey }));
/*     */     }
/*     */     
/* 208 */     return ats;
/*     */   }
/*     */   
/*     */   protected void parseInjectionPoints(List<AnnotationNode> ats) {
/* 212 */     this.injectionPoints.addAll(InjectionPoint.parse((IMixinContext)this.mixin, this.method, this.annotation, ats));
/*     */   }
/*     */   
/*     */   protected void parseRequirements() {
/* 216 */     this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
/*     */     
/* 218 */     Integer expect = (Integer)Annotations.getValue(this.annotation, "expect");
/* 219 */     if (expect != null) {
/* 220 */       this.expectedCallbackCount = expect.intValue();
/*     */     }
/*     */     
/* 223 */     Integer require = (Integer)Annotations.getValue(this.annotation, "require");
/* 224 */     if (require != null && require.intValue() > -1) {
/* 225 */       this.requiredCallbackCount = require.intValue();
/* 226 */     } else if (this.group.isDefault()) {
/* 227 */       this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
/*     */     } 
/*     */     
/* 230 */     Integer allow = (Integer)Annotations.getValue(this.annotation, "allow");
/* 231 */     if (allow != null) {
/* 232 */       this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), allow.intValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Injector parseInjector(AnnotationNode paramAnnotationNode);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 246 */     return (this.targets.size() > 0 && this.injectionPoints.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 253 */     this.targetNodes.clear();
/* 254 */     for (MethodNode targetMethod : this.targets) {
/* 255 */       Target target = this.mixin.getTargetMethod(targetMethod);
/* 256 */       InjectorTarget injectorTarget = new InjectorTarget(this, target);
/* 257 */       this.targetNodes.put(target, this.injector.find(injectorTarget, this.injectionPoints));
/* 258 */       injectorTarget.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inject() {
/* 266 */     for (Map.Entry<Target, List<InjectionNodes.InjectionNode>> entry : this.targetNodes.entrySet()) {
/* 267 */       this.injector.inject(entry.getKey(), entry.getValue());
/*     */     }
/* 269 */     this.targets.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postInject() {
/* 276 */     for (MethodNode method : this.injectedMethods) {
/* 277 */       this.classNode.methods.add(method);
/*     */     }
/*     */     
/* 280 */     String description = getDescription();
/* 281 */     String refMapStatus = this.mixin.getReferenceMapper().getStatus();
/* 282 */     String dynamicInfo = getDynamicInfo();
/* 283 */     if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount)
/* 284 */       throw new InvalidInjectionException(this, 
/* 285 */           String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. %s%s", new Object[] {
/* 286 */               description, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.expectedCallbackCount), Integer.valueOf(this.injectedCallbackCount), refMapStatus, dynamicInfo
/*     */             })); 
/* 288 */     if (this.injectedCallbackCount < this.requiredCallbackCount)
/* 289 */       throw new InjectionError(
/* 290 */           String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. %s%s", new Object[] {
/* 291 */               description, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.requiredCallbackCount), refMapStatus, dynamicInfo
/*     */             })); 
/* 293 */     if (this.injectedCallbackCount > this.maxCallbackCount) {
/* 294 */       throw new InjectionError(
/* 295 */           String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", new Object[] {
/* 296 */               description, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.maxCallbackCount), dynamicInfo
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyInjected(Target target) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDescription() {
/* 312 */     return "Callback method";
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 317 */     return describeInjector((IMixinContext)this.mixin, this.annotation, this.method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<MethodNode> getTargets() {
/* 326 */     return this.targets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodSlice getSlice(String id) {
/* 334 */     return this.slices.get(getSliceId(id));
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
/*     */   public String getSliceId(String id) {
/* 346 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInjectedCallbackCount() {
/* 355 */     return this.injectedCallbackCount;
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
/*     */   public MethodNode addMethod(int access, String name, String desc) {
/* 368 */     MethodNode method = new MethodNode(327680, access | 0x1000, name, desc, null, null);
/* 369 */     this.injectedMethods.add(method);
/* 370 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCallbackInvocation(MethodNode handler) {
/* 379 */     this.injectedCallbackCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findMethods(Set<MemberInfo> searchFor, String type) {
/* 389 */     this.targets.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     int passes = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
/*     */     
/* 397 */     for (MemberInfo member : searchFor) {
/* 398 */       for (int count = 0, pass = 0; pass < passes && count < 1; pass++) {
/* 399 */         int ordinal = 0;
/* 400 */         for (MethodNode target : this.classNode.methods) {
/* 401 */           if (member.matches(target.name, target.desc, ordinal)) {
/* 402 */             boolean isMixinMethod = (Annotations.getVisible(target, MixinMerged.class) != null);
/* 403 */             if (member.matchAll && (Bytecode.methodIsStatic(target) != this.isStatic || target == this.method || isMixinMethod)) {
/*     */               continue;
/*     */             }
/*     */             
/* 407 */             checkTarget(target);
/* 408 */             this.targets.add(target);
/* 409 */             ordinal++;
/* 410 */             count++;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 415 */         member = member.transform(null);
/*     */       } 
/*     */     } 
/*     */     
/* 419 */     if (this.targets.size() == 0)
/* 420 */       throw new InvalidInjectionException(this, 
/* 421 */           String.format("%s annotation on %s could not find any targets matching %s in the target class %s. %s%s", new Object[] {
/* 422 */               type, this.method.name, namesOf(searchFor), this.mixin.getTarget(), this.mixin
/* 423 */               .getReferenceMapper().getStatus(), getDynamicInfo()
/*     */             })); 
/*     */   }
/*     */   
/*     */   private void checkTarget(MethodNode target) {
/* 428 */     AnnotationNode merged = Annotations.getVisible(target, MixinMerged.class);
/* 429 */     if (merged == null) {
/*     */       return;
/*     */     }
/*     */     
/* 433 */     if (Annotations.getVisible(target, Final.class) != null) {
/* 434 */       throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", new Object[] { this, this.classNode.name, target.name, target.desc, 
/* 435 */               Annotations.getValue(merged, "mixin") }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDynamicInfo() {
/* 446 */     AnnotationNode annotation = Annotations.getInvisible(this.method, Dynamic.class);
/* 447 */     String description = Strings.nullToEmpty((String)Annotations.getValue(annotation));
/* 448 */     Type upstream = (Type)Annotations.getValue(annotation, "mixin");
/* 449 */     if (upstream != null) {
/* 450 */       description = String.format("{%s} %s", new Object[] { upstream.getClassName(), description }).trim();
/*     */     }
/* 452 */     return (description.length() > 0) ? String.format(" Method is @Dynamic(%s)", new Object[] { description }) : "";
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
/*     */   public static InjectionInfo parse(MixinTargetContext mixin, MethodNode method) {
/* 465 */     AnnotationNode annotation = getInjectorAnnotation(mixin.getMixin(), method);
/*     */     
/* 467 */     if (annotation == null) {
/* 468 */       return null;
/*     */     }
/*     */     
/* 471 */     if (annotation.desc.endsWith(Inject.class.getSimpleName() + ";"))
/* 472 */       return new CallbackInjectionInfo(mixin, method, annotation); 
/* 473 */     if (annotation.desc.endsWith(ModifyArg.class.getSimpleName() + ";"))
/* 474 */       return new ModifyArgInjectionInfo(mixin, method, annotation); 
/* 475 */     if (annotation.desc.endsWith(ModifyArgs.class.getSimpleName() + ";"))
/* 476 */       return new ModifyArgsInjectionInfo(mixin, method, annotation); 
/* 477 */     if (annotation.desc.endsWith(Redirect.class.getSimpleName() + ";"))
/* 478 */       return new RedirectInjectionInfo(mixin, method, annotation); 
/* 479 */     if (annotation.desc.endsWith(ModifyVariable.class.getSimpleName() + ";"))
/* 480 */       return new ModifyVariableInjectionInfo(mixin, method, annotation); 
/* 481 */     if (annotation.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
/* 482 */       return new ModifyConstantInjectionInfo(mixin, method, annotation);
/*     */     }
/*     */     
/* 485 */     return null;
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
/*     */   
/*     */   public static AnnotationNode getInjectorAnnotation(IMixinInfo mixin, MethodNode method) {
/* 499 */     AnnotationNode annotation = null;
/*     */     try {
/* 501 */       annotation = Annotations.getSingleVisible(method, new Class[] { Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 509 */     catch (IllegalArgumentException ex) {
/* 510 */       throw new InvalidMixinException(mixin, String.format("Error parsing annotations on %s in %s: %s", new Object[] { method.name, mixin.getClassName(), ex
/* 511 */               .getMessage() }));
/*     */     } 
/* 513 */     return annotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInjectorPrefix(AnnotationNode annotation) {
/* 523 */     if (annotation != null) {
/* 524 */       if (annotation.desc.endsWith(ModifyArg.class.getSimpleName() + ";"))
/* 525 */         return "modify"; 
/* 526 */       if (annotation.desc.endsWith(ModifyArgs.class.getSimpleName() + ";"))
/* 527 */         return "args"; 
/* 528 */       if (annotation.desc.endsWith(Redirect.class.getSimpleName() + ";"))
/* 529 */         return "redirect"; 
/* 530 */       if (annotation.desc.endsWith(ModifyVariable.class.getSimpleName() + ";"))
/* 531 */         return "localvar"; 
/* 532 */       if (annotation.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
/* 533 */         return "constant";
/*     */       }
/*     */     } 
/* 536 */     return "handler";
/*     */   }
/*     */   
/*     */   static String describeInjector(IMixinContext mixin, AnnotationNode annotation, MethodNode method) {
/* 540 */     return String.format("%s->@%s::%s%s", new Object[] { mixin.toString(), Bytecode.getSimpleName(annotation), method.name, method.desc });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String namesOf(Collection<MemberInfo> members) {
/* 550 */     int index = 0, count = members.size();
/* 551 */     StringBuilder sb = new StringBuilder();
/* 552 */     for (MemberInfo member : members) {
/* 553 */       if (index > 0) {
/* 554 */         if (index == count - 1) {
/* 555 */           sb.append(" or ");
/*     */         } else {
/* 557 */           sb.append(", ");
/*     */         } 
/*     */       }
/* 560 */       sb.append('\'').append(member.name).append('\'');
/* 561 */       index++;
/*     */     } 
/* 563 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\InjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */