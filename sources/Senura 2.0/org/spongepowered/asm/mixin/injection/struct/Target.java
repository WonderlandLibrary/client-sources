/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Label;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.LabelNode;
/*     */ import org.spongepowered.asm.lib.tree.LocalVariableNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Target
/*     */   implements Comparable<Target>, Iterable<AbstractInsnNode>
/*     */ {
/*     */   public final ClassNode classNode;
/*     */   public final MethodNode method;
/*     */   public final InsnList insns;
/*     */   public final boolean isStatic;
/*     */   public final boolean isCtor;
/*     */   public final Type[] arguments;
/*     */   public final Type returnType;
/*     */   private final int maxStack;
/*     */   private final int maxLocals;
/* 102 */   private final InjectionNodes injectionNodes = new InjectionNodes();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String callbackInfoClass;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String callbackDescriptor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] argIndices;
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Integer> argMapVars;
/*     */ 
/*     */ 
/*     */   
/*     */   private LabelNode start;
/*     */ 
/*     */ 
/*     */   
/*     */   private LabelNode end;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target(ClassNode classNode, MethodNode method) {
/* 135 */     this.classNode = classNode;
/* 136 */     this.method = method;
/* 137 */     this.insns = method.instructions;
/* 138 */     this.isStatic = Bytecode.methodIsStatic(method);
/* 139 */     this.isCtor = method.name.equals("<init>");
/* 140 */     this.arguments = Type.getArgumentTypes(method.desc);
/*     */     
/* 142 */     this.returnType = Type.getReturnType(method.desc);
/* 143 */     this.maxStack = method.maxStack;
/* 144 */     this.maxLocals = method.maxLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionNodes.InjectionNode addInjectionNode(AbstractInsnNode node) {
/* 155 */     return this.injectionNodes.add(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionNodes.InjectionNode getInjectionNode(AbstractInsnNode node) {
/* 166 */     return this.injectionNodes.get(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLocals() {
/* 175 */     return this.maxLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStack() {
/* 184 */     return this.maxStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentMaxLocals() {
/* 193 */     return this.method.maxLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentMaxStack() {
/* 202 */     return this.method.maxStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int allocateLocal() {
/* 211 */     return allocateLocals(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int allocateLocals(int locals) {
/* 222 */     int nextLocal = this.method.maxLocals;
/* 223 */     this.method.maxLocals += locals;
/* 224 */     return nextLocal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToLocals(int locals) {
/* 233 */     setMaxLocals(this.maxLocals + locals);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxLocals(int maxLocals) {
/* 243 */     if (maxLocals > this.method.maxLocals) {
/* 244 */       this.method.maxLocals = maxLocals;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToStack(int stack) {
/* 254 */     setMaxStack(this.maxStack + stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxStack(int maxStack) {
/* 264 */     if (maxStack > this.method.maxStack) {
/* 265 */       this.method.maxStack = maxStack;
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
/*     */   public int[] generateArgMap(Type[] args, int start) {
/* 280 */     if (this.argMapVars == null) {
/* 281 */       this.argMapVars = new ArrayList<Integer>();
/*     */     }
/*     */     
/* 284 */     int[] argMap = new int[args.length];
/* 285 */     for (int arg = start, index = 0; arg < args.length; arg++) {
/* 286 */       int size = args[arg].getSize();
/* 287 */       argMap[arg] = allocateArgMapLocal(index, size);
/* 288 */       index += size;
/*     */     } 
/* 290 */     return argMap;
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
/*     */   private int allocateArgMapLocal(int index, int size) {
/* 306 */     if (index >= this.argMapVars.size()) {
/* 307 */       int base = allocateLocals(size);
/* 308 */       for (int offset = 0; offset < size; offset++) {
/* 309 */         this.argMapVars.add(Integer.valueOf(base + offset));
/*     */       }
/* 311 */       return base;
/*     */     } 
/*     */     
/* 314 */     int local = ((Integer)this.argMapVars.get(index)).intValue();
/*     */ 
/*     */     
/* 317 */     if (size > 1 && index + size > this.argMapVars.size()) {
/* 318 */       int nextLocal = allocateLocals(1);
/* 319 */       if (nextLocal == local + 1) {
/*     */         
/* 321 */         this.argMapVars.add(Integer.valueOf(nextLocal));
/* 322 */         return local;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 327 */       this.argMapVars.set(index, Integer.valueOf(nextLocal));
/* 328 */       this.argMapVars.add(Integer.valueOf(allocateLocals(1)));
/* 329 */       return nextLocal;
/*     */     } 
/*     */     
/* 332 */     return local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getArgIndices() {
/* 341 */     if (this.argIndices == null) {
/* 342 */       this.argIndices = calcArgIndices(this.isStatic ? 0 : 1);
/*     */     }
/* 344 */     return this.argIndices;
/*     */   }
/*     */   
/*     */   private int[] calcArgIndices(int local) {
/* 348 */     int[] argIndices = new int[this.arguments.length];
/* 349 */     for (int arg = 0; arg < this.arguments.length; arg++) {
/* 350 */       argIndices[arg] = local;
/* 351 */       local += this.arguments[arg].getSize();
/*     */     } 
/* 353 */     return argIndices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCallbackInfoClass() {
/* 363 */     if (this.callbackInfoClass == null) {
/* 364 */       this.callbackInfoClass = CallbackInfo.getCallInfoClassName(this.returnType);
/*     */     }
/* 366 */     return this.callbackInfoClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSimpleCallbackDescriptor() {
/* 375 */     return String.format("(L%s;)V", new Object[] { getCallbackInfoClass() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCallbackDescriptor(Type[] locals, Type[] argumentTypes) {
/* 386 */     return getCallbackDescriptor(false, locals, argumentTypes, 0, 32767);
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
/*     */   public String getCallbackDescriptor(boolean captureLocals, Type[] locals, Type[] argumentTypes, int startIndex, int extra) {
/* 400 */     if (this.callbackDescriptor == null) {
/* 401 */       this.callbackDescriptor = String.format("(%sL%s;)V", new Object[] { this.method.desc.substring(1, this.method.desc.indexOf(')')), 
/* 402 */             getCallbackInfoClass() });
/*     */     }
/*     */     
/* 405 */     if (!captureLocals || locals == null) {
/* 406 */       return this.callbackDescriptor;
/*     */     }
/*     */     
/* 409 */     StringBuilder descriptor = new StringBuilder(this.callbackDescriptor.substring(0, this.callbackDescriptor.indexOf(')')));
/* 410 */     for (int l = startIndex; l < locals.length && extra > 0; l++) {
/* 411 */       if (locals[l] != null) {
/* 412 */         descriptor.append(locals[l].getDescriptor());
/* 413 */         extra--;
/*     */       } 
/*     */     } 
/*     */     
/* 417 */     return descriptor.append(")V").toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 422 */     return String.format("%s::%s%s", new Object[] { this.classNode.name, this.method.name, this.method.desc });
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Target o) {
/* 427 */     if (o == null) {
/* 428 */       return Integer.MAX_VALUE;
/*     */     }
/* 430 */     return toString().compareTo(o.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(InjectionNodes.InjectionNode node) {
/* 440 */     return this.insns.indexOf(node.getCurrentTarget());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(AbstractInsnNode insn) {
/* 450 */     return this.insns.indexOf(insn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode get(int index) {
/* 460 */     return this.insns.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<AbstractInsnNode> iterator() {
/* 468 */     return this.insns.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodInsnNode findInitNodeFor(TypeInsnNode newNode) {
/* 479 */     int start = indexOf((AbstractInsnNode)newNode);
/* 480 */     for (Iterator<AbstractInsnNode> iter = this.insns.iterator(start); iter.hasNext(); ) {
/* 481 */       AbstractInsnNode insn = iter.next();
/* 482 */       if (insn instanceof MethodInsnNode && insn.getOpcode() == 183) {
/* 483 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/* 484 */         if ("<init>".equals(methodNode.name) && methodNode.owner.equals(newNode.desc)) {
/* 485 */           return methodNode;
/*     */         }
/*     */       } 
/*     */     } 
/* 489 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodInsnNode findSuperInitNode() {
/* 500 */     if (!this.isCtor) {
/* 501 */       return null;
/*     */     }
/*     */     
/* 504 */     return Bytecode.findSuperInit(this.method, ClassInfo.forName(this.classNode.name).getSuperName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertBefore(InjectionNodes.InjectionNode location, InsnList insns) {
/* 514 */     this.insns.insertBefore(location.getCurrentTarget(), insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertBefore(AbstractInsnNode location, InsnList insns) {
/* 524 */     this.insns.insertBefore(location, insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceNode(AbstractInsnNode location, AbstractInsnNode insn) {
/* 535 */     this.insns.insertBefore(location, insn);
/* 536 */     this.insns.remove(location);
/* 537 */     this.injectionNodes.replace(location, insn);
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
/*     */   public void replaceNode(AbstractInsnNode location, AbstractInsnNode champion, InsnList insns) {
/* 549 */     this.insns.insertBefore(location, insns);
/* 550 */     this.insns.remove(location);
/* 551 */     this.injectionNodes.replace(location, champion);
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
/*     */   public void wrapNode(AbstractInsnNode location, AbstractInsnNode champion, InsnList before, InsnList after) {
/* 564 */     this.insns.insertBefore(location, before);
/* 565 */     this.insns.insert(location, after);
/* 566 */     this.injectionNodes.replace(location, champion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceNode(AbstractInsnNode location, InsnList insns) {
/* 577 */     this.insns.insertBefore(location, insns);
/* 578 */     removeNode(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNode(AbstractInsnNode insn) {
/* 588 */     this.insns.remove(insn);
/* 589 */     this.injectionNodes.remove(insn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLocalVariable(int index, String name, String desc) {
/* 600 */     if (this.start == null) {
/* 601 */       this.start = new LabelNode(new Label());
/* 602 */       this.end = new LabelNode(new Label());
/* 603 */       this.insns.insert((AbstractInsnNode)this.start);
/* 604 */       this.insns.add((AbstractInsnNode)this.end);
/*     */     } 
/* 606 */     addLocalVariable(index, name, desc, this.start, this.end);
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
/*     */   private void addLocalVariable(int index, String name, String desc, LabelNode start, LabelNode end) {
/* 619 */     if (this.method.localVariables == null) {
/* 620 */       this.method.localVariables = new ArrayList();
/*     */     }
/* 622 */     this.method.localVariables.add(new LocalVariableNode(name, desc, null, start, end, index));
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\Target.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */