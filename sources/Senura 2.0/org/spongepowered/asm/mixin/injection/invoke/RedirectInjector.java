/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ObjectArrays;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.JumpInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LabelNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.Coerce;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeNew;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
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
/*     */ public class RedirectInjector
/*     */   extends InvokeInjector
/*     */ {
/*     */   private static final String KEY_NOMINATORS = "nominators";
/*     */   private static final String KEY_FUZZ = "fuzz";
/*     */   private static final String KEY_OPCODE = "opcode";
/*     */   protected Meta meta;
/*     */   
/*     */   class Meta
/*     */   {
/*     */     public static final String KEY = "redirector";
/*     */     final int priority;
/*     */     final boolean isFinal;
/*     */     final String name;
/*     */     final String desc;
/*     */     
/*     */     public Meta(int priority, boolean isFinal, String name, String desc) {
/* 110 */       this.priority = priority;
/* 111 */       this.isFinal = isFinal;
/* 112 */       this.name = name;
/* 113 */       this.desc = desc;
/*     */     }
/*     */     
/*     */     RedirectInjector getOwner() {
/* 117 */       return RedirectInjector.this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ConstructorRedirectData
/*     */   {
/*     */     public static final String KEY = "ctor";
/*     */ 
/*     */     
/*     */     public boolean wildcard = false;
/*     */ 
/*     */     
/* 131 */     public int injected = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   static class RedirectedInvoke
/*     */   {
/*     */     final Target target;
/*     */     
/*     */     final MethodInsnNode node;
/*     */     
/*     */     final Type returnType;
/*     */     
/*     */     final Type[] args;
/*     */     
/*     */     final Type[] locals;
/*     */     boolean captureTargetArgs = false;
/*     */     
/*     */     RedirectedInvoke(Target target, MethodInsnNode node) {
/* 149 */       this.target = target;
/* 150 */       this.node = node;
/* 151 */       this.returnType = Type.getReturnType(node.desc);
/* 152 */       this.args = Type.getArgumentTypes(node.desc);
/* 153 */       this
/*     */         
/* 155 */         .locals = (node.getOpcode() == 184) ? this.args : (Type[])ObjectArrays.concat(Type.getType("L" + node.owner + ";"), (Object[])this.args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 161 */   private Map<BeforeNew, ConstructorRedirectData> ctorRedirectors = new HashMap<BeforeNew, ConstructorRedirectData>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RedirectInjector(InjectionInfo info) {
/* 167 */     this(info, "@Redirect");
/*     */   }
/*     */   
/*     */   protected RedirectInjector(InjectionInfo info, String annotationType) {
/* 171 */     super(info, annotationType);
/*     */     
/* 173 */     int priority = info.getContext().getPriority();
/* 174 */     boolean isFinal = (Annotations.getVisible(this.methodNode, Final.class) != null);
/* 175 */     this.meta = new Meta(priority, isFinal, this.info.toString(), this.methodNode.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkTarget(Target target) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode insn, Set<InjectionPoint> nominators) {
/* 189 */     InjectionNodes.InjectionNode node = target.getInjectionNode(insn);
/* 190 */     ConstructorRedirectData ctorData = null;
/* 191 */     int fuzz = 8;
/* 192 */     int opcode = 0;
/*     */     
/* 194 */     if (node != null) {
/* 195 */       Meta other = (Meta)node.getDecoration("redirector");
/*     */       
/* 197 */       if (other != null && other.getOwner() != this) {
/* 198 */         if (other.priority >= this.meta.priority) {
/* 199 */           Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, 
/* 200 */                 Integer.valueOf(this.meta.priority), other.name, Integer.valueOf(other.priority) }); return;
/*     */         } 
/* 202 */         if (other.isFinal) {
/* 203 */           throw new InvalidInjectionException(this.info, String.format("%s conflict: %s failed because target was already remapped by %s", new Object[] { this.annotationType, this, other.name }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 209 */     for (InjectionPoint ip : nominators) {
/* 210 */       if (ip instanceof BeforeNew) {
/* 211 */         ctorData = getCtorRedirect((BeforeNew)ip);
/* 212 */         ctorData.wildcard = !((BeforeNew)ip).hasDescriptor(); continue;
/* 213 */       }  if (ip instanceof BeforeFieldAccess) {
/* 214 */         BeforeFieldAccess bfa = (BeforeFieldAccess)ip;
/* 215 */         fuzz = bfa.getFuzzFactor();
/* 216 */         opcode = bfa.getArrayOpcode();
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     InjectionNodes.InjectionNode targetNode = target.addInjectionNode(insn);
/* 221 */     targetNode.decorate("redirector", this.meta);
/* 222 */     targetNode.decorate("nominators", nominators);
/* 223 */     if (insn instanceof TypeInsnNode && insn.getOpcode() == 187) {
/* 224 */       targetNode.decorate("ctor", ctorData);
/*     */     } else {
/* 226 */       targetNode.decorate("fuzz", Integer.valueOf(fuzz));
/* 227 */       targetNode.decorate("opcode", Integer.valueOf(opcode));
/*     */     } 
/* 229 */     myNodes.add(targetNode);
/*     */   }
/*     */   
/*     */   private ConstructorRedirectData getCtorRedirect(BeforeNew ip) {
/* 233 */     ConstructorRedirectData ctorRedirect = this.ctorRedirectors.get(ip);
/* 234 */     if (ctorRedirect == null) {
/* 235 */       ctorRedirect = new ConstructorRedirectData();
/* 236 */       this.ctorRedirectors.put(ip, ctorRedirect);
/*     */     } 
/* 238 */     return ctorRedirect;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 243 */     if (!preInject(node)) {
/*     */       return;
/*     */     }
/*     */     
/* 247 */     if (node.isReplaced()) {
/* 248 */       throw new UnsupportedOperationException("Redirector target failure for " + this.info);
/*     */     }
/*     */     
/* 251 */     if (node.getCurrentTarget() instanceof MethodInsnNode) {
/* 252 */       checkTargetForNode(target, node);
/* 253 */       injectAtInvoke(target, node);
/*     */       
/*     */       return;
/*     */     } 
/* 257 */     if (node.getCurrentTarget() instanceof FieldInsnNode) {
/* 258 */       checkTargetForNode(target, node);
/* 259 */       injectAtFieldAccess(target, node);
/*     */       
/*     */       return;
/*     */     } 
/* 263 */     if (node.getCurrentTarget() instanceof TypeInsnNode && node.getCurrentTarget().getOpcode() == 187) {
/* 264 */       if (!this.isStatic && target.isStatic) {
/* 265 */         throw new InvalidInjectionException(this.info, String.format("non-static callback method %s has a static target which is not supported", new Object[] { this }));
/*     */       }
/*     */       
/* 268 */       injectAtConstructor(target, node);
/*     */       
/*     */       return;
/*     */     } 
/* 272 */     throw new InvalidInjectionException(this.info, String.format("%s annotation on is targetting an invalid insn in %s in %s", new Object[] { this.annotationType, target, this }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean preInject(InjectionNodes.InjectionNode node) {
/* 277 */     Meta other = (Meta)node.getDecoration("redirector");
/* 278 */     if (other.getOwner() != this) {
/* 279 */       Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, 
/* 280 */             Integer.valueOf(this.meta.priority), other.name, Integer.valueOf(other.priority) });
/* 281 */       return false;
/*     */     } 
/* 283 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postInject(Target target, InjectionNodes.InjectionNode node) {
/* 288 */     super.postInject(target, node);
/* 289 */     if (node.getOriginalTarget() instanceof TypeInsnNode && node.getOriginalTarget().getOpcode() == 187) {
/* 290 */       ConstructorRedirectData meta = (ConstructorRedirectData)node.getDecoration("ctor");
/* 291 */       if (meta.wildcard && meta.injected == 0) {
/* 292 */         throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", new Object[] { this.annotationType, target }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
/* 302 */     RedirectedInvoke invoke = new RedirectedInvoke(target, (MethodInsnNode)node.getCurrentTarget());
/*     */     
/* 304 */     validateParams(invoke);
/*     */     
/* 306 */     InsnList insns = new InsnList();
/* 307 */     int extraLocals = Bytecode.getArgsSize(invoke.locals) + 1;
/* 308 */     int extraStack = 1;
/* 309 */     int[] argMap = storeArgs(target, invoke.locals, insns, 0);
/* 310 */     if (invoke.captureTargetArgs) {
/* 311 */       int argSize = Bytecode.getArgsSize(target.arguments);
/* 312 */       extraLocals += argSize;
/* 313 */       extraStack += argSize;
/* 314 */       argMap = Ints.concat(new int[][] { argMap, target.getArgIndices() });
/*     */     } 
/* 316 */     AbstractInsnNode insn = invokeHandlerWithArgs(this.methodArgs, insns, argMap);
/* 317 */     target.replaceNode((AbstractInsnNode)invoke.node, insn, insns);
/* 318 */     target.addToLocals(extraLocals);
/* 319 */     target.addToStack(extraStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateParams(RedirectedInvoke invoke) {
/* 330 */     int argc = this.methodArgs.length;
/*     */     
/* 332 */     String description = String.format("%s handler method %s", new Object[] { this.annotationType, this });
/* 333 */     if (!invoke.returnType.equals(this.returnType)) {
/* 334 */       throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Expected return type %s found %s", new Object[] { description, this.returnType, invoke.returnType }));
/*     */     }
/*     */ 
/*     */     
/* 338 */     for (int index = 0; index < argc; index++) {
/* 339 */       Type toType = null;
/* 340 */       if (index >= this.methodArgs.length) {
/* 341 */         throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Not enough arguments found for capture of target method args, expected %d but found %d", new Object[] { description, 
/*     */                 
/* 343 */                 Integer.valueOf(argc), Integer.valueOf(this.methodArgs.length) }));
/*     */       }
/*     */       
/* 346 */       Type fromType = this.methodArgs[index];
/*     */       
/* 348 */       if (index < invoke.locals.length) {
/* 349 */         toType = invoke.locals[index];
/*     */       } else {
/* 351 */         invoke.captureTargetArgs = true;
/* 352 */         argc = Math.max(argc, invoke.locals.length + invoke.target.arguments.length);
/* 353 */         int arg = index - invoke.locals.length;
/* 354 */         if (arg >= invoke.target.arguments.length) {
/* 355 */           throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected additional target argument with type %s at index %d", new Object[] { description, fromType, 
/*     */                   
/* 357 */                   Integer.valueOf(index) }));
/*     */         }
/* 359 */         toType = invoke.target.arguments[arg];
/*     */       } 
/*     */       
/* 362 */       AnnotationNode coerce = Annotations.getInvisibleParameter(this.methodNode, Coerce.class, index);
/*     */       
/* 364 */       if (fromType.equals(toType)) {
/* 365 */         if (coerce != null && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 366 */           Injector.logger.warn("Redundant @Coerce on {} argument {}, {} is identical to {}", new Object[] { description, Integer.valueOf(index), toType, fromType });
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 372 */         boolean canCoerce = Injector.canCoerce(fromType, toType);
/* 373 */         if (coerce == null) {
/* 374 */           throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected argument type %s at index %d, expected %s", new Object[] { description, fromType, 
/*     */                   
/* 376 */                   Integer.valueOf(index), toType }));
/*     */         }
/*     */         
/* 379 */         if (!canCoerce) {
/* 380 */           throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Cannot @Coerce argument type %s at index %d to %s", new Object[] { description, toType, 
/*     */                   
/* 382 */                   Integer.valueOf(index), fromType }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtFieldAccess(Target target, InjectionNodes.InjectionNode node) {
/* 391 */     FieldInsnNode fieldNode = (FieldInsnNode)node.getCurrentTarget();
/* 392 */     int opCode = fieldNode.getOpcode();
/* 393 */     Type ownerType = Type.getType("L" + fieldNode.owner + ";");
/* 394 */     Type fieldType = Type.getType(fieldNode.desc);
/*     */     
/* 396 */     int targetDimensions = (fieldType.getSort() == 9) ? fieldType.getDimensions() : 0;
/* 397 */     int handlerDimensions = (this.returnType.getSort() == 9) ? this.returnType.getDimensions() : 0;
/*     */     
/* 399 */     if (handlerDimensions > targetDimensions)
/* 400 */       throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this); 
/* 401 */     if (handlerDimensions == 0 && targetDimensions > 0) {
/* 402 */       int fuzz = ((Integer)node.getDecoration("fuzz")).intValue();
/* 403 */       int opcode = ((Integer)node.getDecoration("opcode")).intValue();
/* 404 */       injectAtArrayField(target, fieldNode, opCode, ownerType, fieldType, fuzz, opcode);
/*     */     } else {
/* 406 */       injectAtScalarField(target, fieldNode, opCode, ownerType, fieldType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtArrayField(Target target, FieldInsnNode fieldNode, int opCode, Type ownerType, Type fieldType, int fuzz, int opcode) {
/* 414 */     Type elementType = fieldType.getElementType();
/* 415 */     if (opCode != 178 && opCode != 180)
/* 416 */       throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for array access %s", new Object[] {
/* 417 */               Bytecode.getOpcodeName(opCode), this.info })); 
/* 418 */     if (this.returnType.getSort() != 0) {
/* 419 */       if (opcode != 190) {
/* 420 */         opcode = elementType.getOpcode(46);
/*     */       }
/* 422 */       AbstractInsnNode varNode = BeforeFieldAccess.findArrayNode(target.insns, fieldNode, opcode, fuzz);
/* 423 */       injectAtGetArray(target, fieldNode, varNode, ownerType, fieldType);
/*     */     } else {
/* 425 */       AbstractInsnNode varNode = BeforeFieldAccess.findArrayNode(target.insns, fieldNode, elementType.getOpcode(79), fuzz);
/* 426 */       injectAtSetArray(target, fieldNode, varNode, ownerType, fieldType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtGetArray(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, Type ownerType, Type fieldType) {
/* 434 */     String handlerDesc = getGetArrayHandlerDescriptor(varNode, this.returnType, fieldType);
/* 435 */     boolean withArgs = checkDescriptor(handlerDesc, target, "array getter");
/* 436 */     injectArrayRedirect(target, fieldNode, varNode, withArgs, "array getter");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtSetArray(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, Type ownerType, Type fieldType) {
/* 443 */     String handlerDesc = Bytecode.generateDescriptor(null, (Object[])getArrayArgs(fieldType, 1, new Type[] { fieldType.getElementType() }));
/* 444 */     boolean withArgs = checkDescriptor(handlerDesc, target, "array setter");
/* 445 */     injectArrayRedirect(target, fieldNode, varNode, withArgs, "array setter");
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void injectArrayRedirect(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, boolean withArgs, String type) {
/* 462 */     if (varNode == null) {
/* 463 */       String advice = "";
/* 464 */       throw new InvalidInjectionException(this.info, String.format("Array element %s on %s could not locate a matching %s instruction in %s. %s", new Object[] { this.annotationType, this, type, target, advice }));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 469 */     if (!this.isStatic) {
/* 470 */       target.insns.insertBefore((AbstractInsnNode)fieldNode, (AbstractInsnNode)new VarInsnNode(25, 0));
/* 471 */       target.addToStack(1);
/*     */     } 
/*     */     
/* 474 */     InsnList invokeInsns = new InsnList();
/* 475 */     if (withArgs) {
/* 476 */       pushArgs(target.arguments, invokeInsns, target.getArgIndices(), 0, target.arguments.length);
/* 477 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/* 479 */     target.replaceNode(varNode, invokeHandler(invokeInsns), invokeInsns);
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
/*     */   public void injectAtScalarField(Target target, FieldInsnNode fieldNode, int opCode, Type ownerType, Type fieldType) {
/* 492 */     AbstractInsnNode invoke = null;
/* 493 */     InsnList insns = new InsnList();
/* 494 */     if (opCode == 178 || opCode == 180) {
/* 495 */       invoke = injectAtGetField(insns, target, fieldNode, (opCode == 178), ownerType, fieldType);
/* 496 */     } else if (opCode == 179 || opCode == 181) {
/* 497 */       invoke = injectAtPutField(insns, target, fieldNode, (opCode == 179), ownerType, fieldType);
/*     */     } else {
/* 499 */       throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for %s", new Object[] { Bytecode.getOpcodeName(opCode), this.info }));
/*     */     } 
/*     */     
/* 502 */     target.replaceNode((AbstractInsnNode)fieldNode, invoke, insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractInsnNode injectAtGetField(InsnList insns, Target target, FieldInsnNode node, boolean staticField, Type owner, Type fieldType) {
/* 512 */     String handlerDesc = staticField ? Bytecode.generateDescriptor(fieldType, new Object[0]) : Bytecode.generateDescriptor(fieldType, new Object[] { owner });
/* 513 */     boolean withArgs = checkDescriptor(handlerDesc, target, "getter");
/*     */     
/* 515 */     if (!this.isStatic) {
/* 516 */       insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 517 */       if (!staticField) {
/* 518 */         insns.add((AbstractInsnNode)new InsnNode(95));
/*     */       }
/*     */     } 
/*     */     
/* 522 */     if (withArgs) {
/* 523 */       pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
/* 524 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/*     */     
/* 527 */     target.addToStack(this.isStatic ? 0 : 1);
/* 528 */     return invokeHandler(insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractInsnNode injectAtPutField(InsnList insns, Target target, FieldInsnNode node, boolean staticField, Type owner, Type fieldType) {
/* 538 */     String handlerDesc = staticField ? Bytecode.generateDescriptor(null, new Object[] { fieldType }) : Bytecode.generateDescriptor(null, new Object[] { owner, fieldType });
/* 539 */     boolean withArgs = checkDescriptor(handlerDesc, target, "setter");
/*     */     
/* 541 */     if (!this.isStatic) {
/* 542 */       if (staticField) {
/* 543 */         insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 544 */         insns.add((AbstractInsnNode)new InsnNode(95));
/*     */       } else {
/* 546 */         int marshallVar = target.allocateLocals(fieldType.getSize());
/* 547 */         insns.add((AbstractInsnNode)new VarInsnNode(fieldType.getOpcode(54), marshallVar));
/* 548 */         insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 549 */         insns.add((AbstractInsnNode)new InsnNode(95));
/* 550 */         insns.add((AbstractInsnNode)new VarInsnNode(fieldType.getOpcode(21), marshallVar));
/*     */       } 
/*     */     }
/*     */     
/* 554 */     if (withArgs) {
/* 555 */       pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
/* 556 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/*     */     
/* 559 */     target.addToStack((!this.isStatic && !staticField) ? 1 : 0);
/* 560 */     return invokeHandler(insns);
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
/*     */   protected boolean checkDescriptor(String desc, Target target, String type) {
/* 574 */     if (this.methodNode.desc.equals(desc)) {
/* 575 */       return false;
/*     */     }
/*     */     
/* 578 */     int pos = desc.indexOf(')');
/* 579 */     String alternateDesc = String.format("%s%s%s", new Object[] { desc.substring(0, pos), Joiner.on("").join((Object[])target.arguments), desc.substring(pos) });
/* 580 */     if (this.methodNode.desc.equals(alternateDesc)) {
/* 581 */       return true;
/*     */     }
/*     */     
/* 584 */     throw new InvalidInjectionException(this.info, String.format("%s method %s %s has an invalid signature. Expected %s but found %s", new Object[] { this.annotationType, type, this, desc, this.methodNode.desc }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void injectAtConstructor(Target target, InjectionNodes.InjectionNode node) {
/* 589 */     ConstructorRedirectData meta = (ConstructorRedirectData)node.getDecoration("ctor");
/*     */     
/* 591 */     if (meta == null)
/*     */     {
/* 593 */       throw new InvalidInjectionException(this.info, String.format("%s ctor redirector has no metadata, the injector failed a preprocessing phase", new Object[] { this.annotationType }));
/*     */     }
/*     */ 
/*     */     
/* 597 */     TypeInsnNode newNode = (TypeInsnNode)node.getCurrentTarget();
/* 598 */     AbstractInsnNode dupNode = target.get(target.indexOf((AbstractInsnNode)newNode) + 1);
/* 599 */     MethodInsnNode initNode = target.findInitNodeFor(newNode);
/*     */     
/* 601 */     if (initNode == null) {
/* 602 */       if (!meta.wildcard) {
/* 603 */         throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", new Object[] { this.annotationType, target }));
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 609 */     boolean isAssigned = (dupNode.getOpcode() == 89);
/* 610 */     String desc = initNode.desc.replace(")V", ")L" + newNode.desc + ";");
/* 611 */     boolean withArgs = false;
/*     */     try {
/* 613 */       withArgs = checkDescriptor(desc, target, "constructor");
/* 614 */     } catch (InvalidInjectionException ex) {
/* 615 */       if (!meta.wildcard) {
/* 616 */         throw ex;
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 621 */     if (isAssigned) {
/* 622 */       target.removeNode(dupNode);
/*     */     }
/*     */     
/* 625 */     if (this.isStatic) {
/* 626 */       target.removeNode((AbstractInsnNode)newNode);
/*     */     } else {
/* 628 */       target.replaceNode((AbstractInsnNode)newNode, (AbstractInsnNode)new VarInsnNode(25, 0));
/*     */     } 
/*     */     
/* 631 */     InsnList insns = new InsnList();
/* 632 */     if (withArgs) {
/* 633 */       pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
/* 634 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/*     */     
/* 637 */     invokeHandler(insns);
/*     */     
/* 639 */     if (isAssigned) {
/*     */ 
/*     */ 
/*     */       
/* 643 */       LabelNode nullCheckSucceeded = new LabelNode();
/* 644 */       insns.add((AbstractInsnNode)new InsnNode(89));
/* 645 */       insns.add((AbstractInsnNode)new JumpInsnNode(199, nullCheckSucceeded));
/* 646 */       throwException(insns, "java/lang/NullPointerException", String.format("%s constructor handler %s returned null for %s", new Object[] { this.annotationType, this, newNode.desc
/* 647 */               .replace('/', '.') }));
/* 648 */       insns.add((AbstractInsnNode)nullCheckSucceeded);
/* 649 */       target.addToStack(1);
/*     */     } else {
/*     */       
/* 652 */       insns.add((AbstractInsnNode)new InsnNode(87));
/*     */     } 
/*     */     
/* 655 */     target.replaceNode((AbstractInsnNode)initNode, insns);
/* 656 */     meta.injected++;
/*     */   }
/*     */   
/*     */   private static String getGetArrayHandlerDescriptor(AbstractInsnNode varNode, Type returnType, Type fieldType) {
/* 660 */     if (varNode != null && varNode.getOpcode() == 190) {
/* 661 */       return Bytecode.generateDescriptor(Type.INT_TYPE, (Object[])getArrayArgs(fieldType, 0, new Type[0]));
/*     */     }
/* 663 */     return Bytecode.generateDescriptor(returnType, (Object[])getArrayArgs(fieldType, 1, new Type[0]));
/*     */   }
/*     */   
/*     */   private static Type[] getArrayArgs(Type fieldType, int extraDimensions, Type... extra) {
/* 667 */     int dimensions = fieldType.getDimensions() + extraDimensions;
/* 668 */     Type[] args = new Type[dimensions + extra.length];
/* 669 */     for (int i = 0; i < args.length; i++) {
/* 670 */       args[i] = (i == 0) ? fieldType : ((i < dimensions) ? Type.INT_TYPE : extra[dimensions - i]);
/*     */     }
/* 672 */     return args;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\invoke\RedirectInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */