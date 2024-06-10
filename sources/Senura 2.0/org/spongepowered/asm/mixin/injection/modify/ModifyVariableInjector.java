/*     */ package org.spongepowered.asm.mixin.injection.modify;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ public class ModifyVariableInjector
/*     */   extends Injector
/*     */ {
/*     */   private final LocalVariableDiscriminator discriminator;
/*     */   
/*     */   static class Context
/*     */     extends LocalVariableDiscriminator.Context
/*     */   {
/*  63 */     final InsnList insns = new InsnList();
/*     */     
/*     */     public Context(Type returnType, boolean argsOnly, Target target, AbstractInsnNode node) {
/*  66 */       super(returnType, argsOnly, target, node);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class ContextualInjectionPoint
/*     */     extends InjectionPoint
/*     */   {
/*     */     protected final IMixinContext context;
/*     */ 
/*     */     
/*     */     ContextualInjectionPoint(IMixinContext context) {
/*  79 */       this.context = context;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/*  84 */       throw new InvalidInjectionException(this.context, getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract boolean find(Target param1Target, Collection<AbstractInsnNode> param1Collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModifyVariableInjector(InjectionInfo info, LocalVariableDiscriminator discriminator) {
/* 101 */     super(info);
/* 102 */     this.discriminator = discriminator;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean findTargetNodes(MethodNode into, InjectionPoint injectionPoint, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 107 */     if (injectionPoint instanceof ContextualInjectionPoint) {
/* 108 */       Target target = this.info.getContext().getTargetMethod(into);
/* 109 */       return ((ContextualInjectionPoint)injectionPoint).find(target, nodes);
/*     */     } 
/* 111 */     return injectionPoint.find(into.desc, insns, nodes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/* 121 */     super.sanityCheck(target, injectionPoints);
/*     */     
/* 123 */     if (target.isStatic != this.isStatic) {
/* 124 */       throw new InvalidInjectionException(this.info, "'static' of variable modifier method does not match target in " + this);
/*     */     }
/*     */     
/* 127 */     int ordinal = this.discriminator.getOrdinal();
/* 128 */     if (ordinal < -1) {
/* 129 */       throw new InvalidInjectionException(this.info, "Invalid ordinal " + ordinal + " specified in " + this);
/*     */     }
/*     */     
/* 132 */     if (this.discriminator.getIndex() == 0 && !this.isStatic) {
/* 133 */       throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 142 */     if (node.isReplaced()) {
/* 143 */       throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
/*     */     }
/*     */     
/* 146 */     Context context = new Context(this.returnType, this.discriminator.isArgsOnly(), target, node.getCurrentTarget());
/*     */     
/* 148 */     if (this.discriminator.printLVT()) {
/* 149 */       printLocals(context);
/*     */     }
/*     */     
/* 152 */     String handlerDesc = Bytecode.getDescriptor(new Type[] { this.returnType }, this.returnType);
/* 153 */     if (!handlerDesc.equals(this.methodNode.desc)) {
/* 154 */       throw new InvalidInjectionException(this.info, "Variable modifier " + this + " has an invalid signature, expected " + handlerDesc + " but found " + this.methodNode.desc);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 159 */       int local = this.discriminator.findLocal(context);
/* 160 */       if (local > -1) {
/* 161 */         inject(context, local);
/*     */       }
/* 163 */     } catch (InvalidImplicitDiscriminatorException ex) {
/* 164 */       if (this.discriminator.printLVT()) {
/* 165 */         this.info.addCallbackInvocation(this.methodNode);
/*     */         return;
/*     */       } 
/* 168 */       throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, ex);
/*     */     } 
/*     */     
/* 171 */     target.insns.insertBefore(context.node, context.insns);
/* 172 */     target.addToStack(this.isStatic ? 1 : 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printLocals(Context context) {
/* 179 */     SignaturePrinter handlerSig = new SignaturePrinter(this.methodNode.name, this.returnType, this.methodArgs, new String[] { "var" });
/* 180 */     handlerSig.setModifiers(this.methodNode);
/*     */     
/* 182 */     (new PrettyPrinter())
/* 183 */       .kvWidth(20)
/* 184 */       .kv("Target Class", this.classNode.name.replace('/', '.'))
/* 185 */       .kv("Target Method", context.target.method.name)
/* 186 */       .kv("Callback Name", this.methodNode.name)
/* 187 */       .kv("Capture Type", SignaturePrinter.getTypeName(this.returnType, false))
/* 188 */       .kv("Instruction", "%s %s", new Object[] { context.node.getClass().getSimpleName(), Bytecode.getOpcodeName(context.node.getOpcode()) }).hr()
/* 189 */       .kv("Match mode", this.discriminator.isImplicit(context) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)")
/* 190 */       .kv("Match ordinal", (this.discriminator.getOrdinal() < 0) ? "any" : Integer.valueOf(this.discriminator.getOrdinal()))
/* 191 */       .kv("Match index", (this.discriminator.getIndex() < context.baseArgIndex) ? "any" : Integer.valueOf(this.discriminator.getIndex()))
/* 192 */       .kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any")
/* 193 */       .kv("Args only", Boolean.valueOf(this.discriminator.isArgsOnly())).hr()
/* 194 */       .add(context)
/* 195 */       .print(System.err);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void inject(Context context, int local) {
/* 205 */     if (!this.isStatic) {
/* 206 */       context.insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*     */     }
/*     */     
/* 209 */     context.insns.add((AbstractInsnNode)new VarInsnNode(this.returnType.getOpcode(21), local));
/* 210 */     invokeHandler(context.insns);
/* 211 */     context.insns.add((AbstractInsnNode)new VarInsnNode(this.returnType.getOpcode(54), local));
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\modify\ModifyVariableInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */