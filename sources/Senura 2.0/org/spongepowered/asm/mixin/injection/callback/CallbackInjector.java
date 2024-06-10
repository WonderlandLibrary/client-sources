/*     */ package org.spongepowered.asm.mixin.injection.callback;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.JumpInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LabelNode;
/*     */ import org.spongepowered.asm.lib.tree.LdcInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LocalVariableNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.Coerce;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.Surrogate;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.Locals;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
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
/*     */ public class CallbackInjector
/*     */   extends Injector
/*     */ {
/*     */   private final boolean cancellable;
/*     */   private final LocalCapture localCapture;
/*     */   private final String identifier;
/*     */   
/*     */   private class Callback
/*     */     extends InsnList
/*     */   {
/*     */     private final MethodNode handler;
/*     */     private final AbstractInsnNode head;
/*     */     final Target target;
/*     */     final InjectionNodes.InjectionNode node;
/*     */     final LocalVariableNode[] locals;
/*     */     final Type[] localTypes;
/*     */     final int frameSize;
/*     */     final int extraArgs;
/*     */     final boolean canCaptureLocals;
/*     */     final boolean isAtReturn;
/*     */     final String desc;
/*     */     final String descl;
/*     */     final String[] argNames;
/*     */     int ctor;
/*     */     int invoke;
/* 157 */     private int marshalVar = -1;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean captureArgs = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Callback(MethodNode handler, Target target, InjectionNodes.InjectionNode node, LocalVariableNode[] locals, boolean captureLocals) {
/* 167 */       this.handler = handler;
/* 168 */       this.target = target;
/* 169 */       this.head = target.insns.getFirst();
/* 170 */       this.node = node;
/* 171 */       this.locals = locals;
/* 172 */       this.localTypes = (locals != null) ? new Type[locals.length] : null;
/* 173 */       this.frameSize = Bytecode.getFirstNonArgLocalIndex(target.arguments, !CallbackInjector.this.isStatic());
/* 174 */       List<String> argNames = null;
/*     */       
/* 176 */       if (locals != null) {
/* 177 */         int baseArgIndex = CallbackInjector.this.isStatic() ? 0 : 1;
/* 178 */         argNames = new ArrayList<String>();
/* 179 */         for (int l = 0; l <= locals.length; l++) {
/* 180 */           if (l == this.frameSize) {
/* 181 */             argNames.add((target.returnType == Type.VOID_TYPE) ? "ci" : "cir");
/*     */           }
/* 183 */           if (l < locals.length && locals[l] != null) {
/* 184 */             this.localTypes[l] = Type.getType((locals[l]).desc);
/* 185 */             if (l >= baseArgIndex) {
/* 186 */               argNames.add(CallbackInjector.meltSnowman(l, (locals[l]).name));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 193 */       this.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(this.handler) - this.frameSize + 1);
/* 194 */       this.argNames = (argNames != null) ? argNames.<String>toArray(new String[argNames.size()]) : null;
/* 195 */       this.canCaptureLocals = (captureLocals && locals != null && locals.length > this.frameSize);
/* 196 */       this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode && isValueReturnOpcode(this.node.getCurrentTarget().getOpcode()));
/* 197 */       this.desc = target.getCallbackDescriptor(this.localTypes, target.arguments);
/* 198 */       this.descl = target.getCallbackDescriptor(true, this.localTypes, target.arguments, this.frameSize, this.extraArgs);
/*     */ 
/*     */       
/* 201 */       this.invoke = target.arguments.length + (this.canCaptureLocals ? (this.localTypes.length - this.frameSize) : 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isValueReturnOpcode(int opcode) {
/* 212 */       return (opcode >= 172 && opcode < 177);
/*     */     }
/*     */     
/*     */     String getDescriptor() {
/* 216 */       return this.canCaptureLocals ? this.descl : this.desc;
/*     */     }
/*     */     
/*     */     String getDescriptorWithAllLocals() {
/* 220 */       return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
/*     */     }
/*     */     
/*     */     String getCallbackInfoConstructorDescriptor() {
/* 224 */       return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void add(AbstractInsnNode insn, boolean ctorStack, boolean invokeStack) {
/* 236 */       add(insn, ctorStack, invokeStack, false);
/*     */     }
/*     */     
/*     */     void add(AbstractInsnNode insn, boolean ctorStack, boolean invokeStack, boolean head) {
/* 240 */       if (head) {
/* 241 */         this.target.insns.insertBefore(this.head, insn);
/*     */       } else {
/* 243 */         add(insn);
/*     */       } 
/* 245 */       this.ctor += ctorStack ? 1 : 0;
/* 246 */       this.invoke += invokeStack ? 1 : 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void inject() {
/* 254 */       this.target.insertBefore(this.node, this);
/* 255 */       this.target.addToStack(Math.max(this.invoke, this.ctor));
/*     */     }
/*     */     
/*     */     boolean checkDescriptor(String desc) {
/* 259 */       if (getDescriptor().equals(desc)) {
/* 260 */         return true;
/*     */       }
/*     */       
/* 263 */       if (this.target.getSimpleCallbackDescriptor().equals(desc) && !this.canCaptureLocals) {
/* 264 */         this.captureArgs = false;
/* 265 */         return true;
/*     */       } 
/*     */       
/* 268 */       Type[] inTypes = Type.getArgumentTypes(desc);
/* 269 */       Type[] myTypes = Type.getArgumentTypes(this.descl);
/*     */       
/* 271 */       if (inTypes.length != myTypes.length) {
/* 272 */         return false;
/*     */       }
/*     */       
/* 275 */       for (int arg = 0; arg < myTypes.length; arg++) {
/* 276 */         Type type = inTypes[arg];
/* 277 */         if (!type.equals(myTypes[arg])) {
/*     */ 
/*     */ 
/*     */           
/* 281 */           if (type.getSort() == 9) {
/* 282 */             return false;
/*     */           }
/*     */           
/* 285 */           if (Annotations.getInvisibleParameter(this.handler, Coerce.class, arg) == null) {
/* 286 */             return false;
/*     */           }
/*     */           
/* 289 */           if (!Injector.canCoerce(inTypes[arg], myTypes[arg]))
/*     */           {
/*     */ 
/*     */             
/* 293 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 298 */       return true;
/*     */     }
/*     */     
/*     */     boolean captureArgs() {
/* 302 */       return this.captureArgs;
/*     */     }
/*     */     
/*     */     int marshalVar() {
/* 306 */       if (this.marshalVar < 0) {
/* 307 */         this.marshalVar = this.target.allocateLocal();
/*     */       }
/*     */       
/* 310 */       return this.marshalVar;
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
/* 333 */   private final Map<Integer, String> ids = new HashMap<Integer, String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 339 */   private int totalInjections = 0;
/* 340 */   private int callbackInfoVar = -1;
/*     */ 
/*     */   
/*     */   private String lastId;
/*     */ 
/*     */   
/*     */   private String lastDesc;
/*     */   
/*     */   private Target lastTarget;
/*     */   
/*     */   private String callbackInfoClass;
/*     */ 
/*     */   
/*     */   public CallbackInjector(InjectionInfo info, boolean cancellable, LocalCapture localCapture, String identifier) {
/* 354 */     super(info);
/* 355 */     this.cancellable = cancellable;
/* 356 */     this.localCapture = localCapture;
/* 357 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/* 367 */     super.sanityCheck(target, injectionPoints);
/*     */     
/* 369 */     if (target.isStatic != this.isStatic) {
/* 370 */       throw new InvalidInjectionException(this.info, "'static' modifier of callback method does not match target in " + this);
/*     */     }
/*     */     
/* 373 */     if ("<init>".equals(target.method.name)) {
/* 374 */       for (InjectionPoint injectionPoint : injectionPoints) {
/* 375 */         if (!injectionPoint.getClass().equals(BeforeReturn.class)) {
/* 376 */           throw new InvalidInjectionException(this.info, "Found injection point type " + injectionPoint.getClass().getSimpleName() + " targetting a ctor in " + this + ". Only RETURN allowed for a ctor target");
/*     */         }
/*     */       } 
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
/*     */   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode node, Set<InjectionPoint> nominators) {
/* 390 */     InjectionNodes.InjectionNode injectionNode = target.addInjectionNode(node);
/*     */     
/* 392 */     for (InjectionPoint ip : nominators) {
/* 393 */       String id = ip.getId();
/* 394 */       if (Strings.isNullOrEmpty(id)) {
/*     */         continue;
/*     */       }
/*     */       
/* 398 */       String existingId = this.ids.get(Integer.valueOf(injectionNode.getId()));
/* 399 */       if (existingId != null && !existingId.equals(id)) {
/* 400 */         Injector.logger.warn("Conflicting id for {} insn in {}, found id {} on {}, previously defined as {}", new Object[] { Bytecode.getOpcodeName(node), target
/* 401 */               .toString(), id, this.info, existingId });
/*     */         
/*     */         break;
/*     */       } 
/* 405 */       this.ids.put(Integer.valueOf(injectionNode.getId()), id);
/*     */     } 
/*     */     
/* 408 */     myNodes.add(injectionNode);
/* 409 */     this.totalInjections++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 419 */     LocalVariableNode[] locals = null;
/*     */     
/* 421 */     if (this.localCapture.isCaptureLocals() || this.localCapture.isPrintLocals()) {
/* 422 */       locals = Locals.getLocalsAt(this.classNode, target.method, node.getCurrentTarget());
/*     */     }
/*     */     
/* 425 */     inject(new Callback(this.methodNode, target, node, locals, this.localCapture.isCaptureLocals()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void inject(Callback callback) {
/* 434 */     if (this.localCapture.isPrintLocals()) {
/* 435 */       printLocals(callback);
/* 436 */       this.info.addCallbackInvocation(this.methodNode);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 443 */     MethodNode callbackMethod = this.methodNode;
/*     */     
/* 445 */     if (!callback.checkDescriptor(this.methodNode.desc)) {
/* 446 */       if (this.info.getTargets().size() > 1) {
/*     */         return;
/*     */       }
/*     */       
/* 450 */       if (callback.canCaptureLocals) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 456 */         MethodNode surrogateHandler = Bytecode.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
/* 457 */         if (surrogateHandler != null && Annotations.getVisible(surrogateHandler, Surrogate.class) != null) {
/*     */           
/* 459 */           callbackMethod = surrogateHandler;
/*     */         } else {
/*     */           
/* 462 */           String message = generateBadLVTMessage(callback);
/*     */           
/* 464 */           switch (this.localCapture) {
/*     */             case CAPTURE_FAILEXCEPTION:
/* 466 */               Injector.logger.error("Injection error: {}", new Object[] { message });
/* 467 */               callbackMethod = generateErrorMethod(callback, "org/spongepowered/asm/mixin/injection/throwables/InjectionError", message);
/*     */               break;
/*     */             
/*     */             case CAPTURE_FAILSOFT:
/* 471 */               Injector.logger.warn("Injection warning: {}", new Object[] { message });
/*     */               return;
/*     */             default:
/* 474 */               Injector.logger.error("Critical injection failure: {}", new Object[] { message });
/* 475 */               throw new InjectionError(message);
/*     */           } 
/*     */         
/*     */         } 
/*     */       } else {
/* 480 */         String returnableSig = this.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;");
/*     */ 
/*     */ 
/*     */         
/* 484 */         if (callback.checkDescriptor(returnableSig))
/*     */         {
/*     */           
/* 487 */           throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! CallbackInfoReturnable is required!");
/*     */         }
/*     */         
/* 490 */         MethodNode surrogateHandler = Bytecode.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
/* 491 */         if (surrogateHandler != null && Annotations.getVisible(surrogateHandler, Surrogate.class) != null) {
/*     */           
/* 493 */           callbackMethod = surrogateHandler;
/*     */         } else {
/* 495 */           throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! Expected " + callback.getDescriptor() + " but found " + this.methodNode.desc);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 501 */     dupReturnValue(callback);
/* 502 */     if (this.cancellable || this.totalInjections > 1) {
/* 503 */       createCallbackInfo(callback, true);
/*     */     }
/* 505 */     invokeCallback(callback, callbackMethod);
/* 506 */     injectCancellationCode(callback);
/*     */     
/* 508 */     callback.inject();
/* 509 */     this.info.notifyInjected(callback.target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String generateBadLVTMessage(Callback callback) {
/* 519 */     int position = callback.target.indexOf(callback.node);
/* 520 */     List<String> expected = summariseLocals(this.methodNode.desc, callback.target.arguments.length + 1);
/* 521 */     List<String> found = summariseLocals(callback.getDescriptorWithAllLocals(), callback.frameSize);
/* 522 */     return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", new Object[] { callback.target, 
/* 523 */           Integer.valueOf(position), this, expected, found });
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
/*     */   private MethodNode generateErrorMethod(Callback callback, String errorClass, String message) {
/* 535 */     MethodNode method = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", callback.getDescriptor());
/* 536 */     method.maxLocals = Bytecode.getFirstNonArgLocalIndex(Type.getArgumentTypes(callback.getDescriptor()), !this.isStatic);
/* 537 */     method.maxStack = 3;
/* 538 */     InsnList insns = method.instructions;
/* 539 */     insns.add((AbstractInsnNode)new TypeInsnNode(187, errorClass));
/* 540 */     insns.add((AbstractInsnNode)new InsnNode(89));
/* 541 */     insns.add((AbstractInsnNode)new LdcInsnNode(message));
/* 542 */     insns.add((AbstractInsnNode)new MethodInsnNode(183, errorClass, "<init>", "(Ljava/lang/String;)V", false));
/* 543 */     insns.add((AbstractInsnNode)new InsnNode(191));
/* 544 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printLocals(Callback callback) {
/* 553 */     Type[] args = Type.getArgumentTypes(callback.getDescriptorWithAllLocals());
/* 554 */     SignaturePrinter methodSig = new SignaturePrinter(callback.target.method, callback.argNames);
/* 555 */     SignaturePrinter handlerSig = new SignaturePrinter(this.methodNode.name, callback.target.returnType, args, callback.argNames);
/* 556 */     handlerSig.setModifiers(this.methodNode);
/*     */     
/* 558 */     PrettyPrinter printer = new PrettyPrinter();
/* 559 */     printer.kv("Target Class", this.classNode.name.replace('/', '.'));
/* 560 */     printer.kv("Target Method", methodSig);
/* 561 */     printer.kv("Target Max LOCALS", Integer.valueOf(callback.target.getMaxLocals()));
/* 562 */     printer.kv("Initial Frame Size", Integer.valueOf(callback.frameSize));
/* 563 */     printer.kv("Callback Name", this.methodNode.name);
/* 564 */     printer.kv("Instruction", "%s %s", new Object[] { callback.node.getClass().getSimpleName(), 
/* 565 */           Bytecode.getOpcodeName(callback.node.getCurrentTarget().getOpcode()) });
/* 566 */     printer.hr();
/* 567 */     if (callback.locals.length > callback.frameSize) {
/* 568 */       printer.add("  %s  %20s  %s", new Object[] { "LOCAL", "TYPE", "NAME" });
/* 569 */       for (int l = 0; l < callback.locals.length; l++) {
/* 570 */         String marker = (l == callback.frameSize) ? ">" : " ";
/* 571 */         if (callback.locals[l] != null) {
/* 572 */           printer.add("%s [%3d]  %20s  %-50s %s", new Object[] { marker, Integer.valueOf(l), SignaturePrinter.getTypeName(callback.localTypes[l], false), 
/* 573 */                 meltSnowman(l, (callback.locals[l]).name), (l >= callback.frameSize) ? "<capture>" : "" });
/*     */         } else {
/* 575 */           boolean isTop = (l > 0 && callback.localTypes[l - 1] != null && callback.localTypes[l - 1].getSize() > 1);
/* 576 */           printer.add("%s [%3d]  %20s", new Object[] { marker, Integer.valueOf(l), isTop ? "<top>" : "-" });
/*     */         } 
/*     */       } 
/* 579 */       printer.hr();
/*     */     } 
/* 581 */     printer.add().add("/**").add(" * Expected callback signature").add(" * /");
/* 582 */     printer.add("%s {", new Object[] { handlerSig });
/* 583 */     printer.add("    // Method body").add("}").add().print(System.err);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createCallbackInfo(Callback callback, boolean store) {
/* 592 */     if (callback.target != this.lastTarget) {
/* 593 */       this.lastId = null;
/* 594 */       this.lastDesc = null;
/*     */     } 
/* 596 */     this.lastTarget = callback.target;
/*     */     
/* 598 */     String id = getIdentifier(callback);
/* 599 */     String desc = callback.getCallbackInfoConstructorDescriptor();
/*     */ 
/*     */     
/* 602 */     if (id.equals(this.lastId) && desc.equals(this.lastDesc) && !callback.isAtReturn && !this.cancellable) {
/*     */       return;
/*     */     }
/*     */     
/* 606 */     instanceCallbackInfo(callback, id, desc, store);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadOrCreateCallbackInfo(Callback callback) {
/* 613 */     if (this.cancellable || this.totalInjections > 1) {
/* 614 */       callback.add((AbstractInsnNode)new VarInsnNode(25, this.callbackInfoVar), false, true);
/*     */     } else {
/* 616 */       createCallbackInfo(callback, false);
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
/*     */   private void dupReturnValue(Callback callback) {
/* 629 */     if (!callback.isAtReturn) {
/*     */       return;
/*     */     }
/*     */     
/* 633 */     callback.add((AbstractInsnNode)new InsnNode(89));
/* 634 */     callback.add((AbstractInsnNode)new VarInsnNode(callback.target.returnType.getOpcode(54), callback.marshalVar()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void instanceCallbackInfo(Callback callback, String id, String desc, boolean store) {
/* 645 */     this.lastId = id;
/* 646 */     this.lastDesc = desc;
/* 647 */     this.callbackInfoVar = callback.marshalVar();
/* 648 */     this.callbackInfoClass = callback.target.getCallbackInfoClass();
/*     */ 
/*     */ 
/*     */     
/* 652 */     boolean head = (store && this.totalInjections > 1 && !callback.isAtReturn && !this.cancellable);
/*     */     
/* 654 */     callback.add((AbstractInsnNode)new TypeInsnNode(187, this.callbackInfoClass), true, !store, head);
/* 655 */     callback.add((AbstractInsnNode)new InsnNode(89), true, true, head);
/* 656 */     callback.add((AbstractInsnNode)new LdcInsnNode(id), true, !store, head);
/* 657 */     callback.add((AbstractInsnNode)new InsnNode(this.cancellable ? 4 : 3), true, !store, head);
/*     */     
/* 659 */     if (callback.isAtReturn) {
/* 660 */       callback.add((AbstractInsnNode)new VarInsnNode(callback.target.returnType.getOpcode(21), callback.marshalVar()), true, !store);
/* 661 */       callback.add((AbstractInsnNode)new MethodInsnNode(183, this.callbackInfoClass, "<init>", desc, false));
/*     */     } else {
/*     */       
/* 664 */       callback.add((AbstractInsnNode)new MethodInsnNode(183, this.callbackInfoClass, "<init>", desc, false), false, false, head);
/*     */     } 
/*     */ 
/*     */     
/* 668 */     if (store) {
/* 669 */       callback.target.addLocalVariable(this.callbackInfoVar, "callbackInfo" + this.callbackInfoVar, "L" + this.callbackInfoClass + ";");
/* 670 */       callback.add((AbstractInsnNode)new VarInsnNode(58, this.callbackInfoVar), false, false, head);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void invokeCallback(Callback callback, MethodNode callbackMethod) {
/* 679 */     if (!this.isStatic) {
/* 680 */       callback.add((AbstractInsnNode)new VarInsnNode(25, 0), false, true);
/*     */     }
/*     */ 
/*     */     
/* 684 */     if (callback.captureArgs()) {
/* 685 */       Bytecode.loadArgs(callback.target.arguments, callback, this.isStatic ? 0 : 1, -1);
/*     */     }
/*     */ 
/*     */     
/* 689 */     loadOrCreateCallbackInfo(callback);
/*     */ 
/*     */     
/* 692 */     if (callback.canCaptureLocals) {
/* 693 */       Locals.loadLocals(callback.localTypes, callback, callback.frameSize, callback.extraArgs);
/*     */     }
/*     */ 
/*     */     
/* 697 */     invokeHandler(callback, callbackMethod);
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
/*     */   private String getIdentifier(Callback callback) {
/* 709 */     String baseId = Strings.isNullOrEmpty(this.identifier) ? callback.target.method.name : this.identifier;
/* 710 */     String locationId = this.ids.get(Integer.valueOf(callback.node.getId()));
/* 711 */     return baseId + (Strings.isNullOrEmpty(locationId) ? "" : (":" + locationId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectCancellationCode(Callback callback) {
/* 720 */     if (!this.cancellable) {
/*     */       return;
/*     */     }
/*     */     
/* 724 */     callback.add((AbstractInsnNode)new VarInsnNode(25, this.callbackInfoVar));
/* 725 */     callback.add((AbstractInsnNode)new MethodInsnNode(182, this.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), 
/* 726 */           CallbackInfo.getIsCancelledMethodSig(), false));
/*     */     
/* 728 */     LabelNode notCancelled = new LabelNode();
/* 729 */     callback.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
/*     */ 
/*     */ 
/*     */     
/* 733 */     injectReturnCode(callback);
/*     */     
/* 735 */     callback.add((AbstractInsnNode)notCancelled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectReturnCode(Callback callback) {
/* 744 */     if (callback.target.returnType.equals(Type.VOID_TYPE)) {
/*     */       
/* 746 */       callback.add((AbstractInsnNode)new InsnNode(177));
/*     */     }
/*     */     else {
/*     */       
/* 750 */       callback.add((AbstractInsnNode)new VarInsnNode(25, callback.marshalVar()));
/* 751 */       String accessor = CallbackInfoReturnable.getReturnAccessor(callback.target.returnType);
/* 752 */       String descriptor = CallbackInfoReturnable.getReturnDescriptor(callback.target.returnType);
/* 753 */       callback.add((AbstractInsnNode)new MethodInsnNode(182, this.callbackInfoClass, accessor, descriptor, false));
/* 754 */       if (callback.target.returnType.getSort() == 10) {
/* 755 */         callback.add((AbstractInsnNode)new TypeInsnNode(192, callback.target.returnType.getInternalName()));
/*     */       }
/* 757 */       callback.add((AbstractInsnNode)new InsnNode(callback.target.returnType.getOpcode(172)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isStatic() {
/* 767 */     return this.isStatic;
/*     */   }
/*     */   
/*     */   private static List<String> summariseLocals(String desc, int pos) {
/* 771 */     return summariseLocals(Type.getArgumentTypes(desc), pos);
/*     */   }
/*     */   
/*     */   private static List<String> summariseLocals(Type[] locals, int pos) {
/* 775 */     List<String> list = new ArrayList<String>();
/* 776 */     if (locals != null) {
/* 777 */       for (; pos < locals.length; pos++) {
/* 778 */         if (locals[pos] != null) {
/* 779 */           list.add(locals[pos].toString());
/*     */         }
/*     */       } 
/*     */     }
/* 783 */     return list;
/*     */   }
/*     */   
/*     */   static String meltSnowman(int index, String varName) {
/* 787 */     return (varName != null && '☃' == varName.charAt(0)) ? ("var" + index) : varName;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\callback\CallbackInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */