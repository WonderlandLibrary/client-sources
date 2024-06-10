/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LdcInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*     */ public abstract class Injector
/*     */ {
/*     */   public static final class TargetNode
/*     */   {
/*     */     final AbstractInsnNode insn;
/*  70 */     final Set<InjectionPoint> nominators = new HashSet<InjectionPoint>();
/*     */     
/*     */     TargetNode(AbstractInsnNode insn) {
/*  73 */       this.insn = insn;
/*     */     }
/*     */     
/*     */     public AbstractInsnNode getNode() {
/*  77 */       return this.insn;
/*     */     }
/*     */     
/*     */     public Set<InjectionPoint> getNominators() {
/*  81 */       return Collections.unmodifiableSet(this.nominators);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  86 */       if (obj == null || obj.getClass() != TargetNode.class) {
/*  87 */         return false;
/*     */       }
/*     */       
/*  90 */       return (((TargetNode)obj).insn == this.insn);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  95 */       return this.insn.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   protected static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InjectionInfo info;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ClassNode classNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MethodNode methodNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type[] methodArgs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type returnType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean isStatic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Injector(InjectionInfo info) {
/* 141 */     this(info.getClassNode(), info.getMethod());
/* 142 */     this.info = info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Injector(ClassNode classNode, MethodNode methodNode) {
/* 152 */     this.classNode = classNode;
/* 153 */     this.methodNode = methodNode;
/* 154 */     this.methodArgs = Type.getArgumentTypes(this.methodNode.desc);
/* 155 */     this.returnType = Type.getReturnType(this.methodNode.desc);
/* 156 */     this.isStatic = Bytecode.methodIsStatic(this.methodNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     return String.format("%s::%s", new Object[] { this.classNode.name, this.methodNode.name });
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
/*     */   public final List<InjectionNodes.InjectionNode> find(InjectorTarget injectorTarget, List<InjectionPoint> injectionPoints) {
/* 173 */     sanityCheck(injectorTarget.getTarget(), injectionPoints);
/*     */     
/* 175 */     List<InjectionNodes.InjectionNode> myNodes = new ArrayList<InjectionNodes.InjectionNode>();
/* 176 */     for (TargetNode node : findTargetNodes(injectorTarget, injectionPoints)) {
/* 177 */       addTargetNode(injectorTarget.getTarget(), myNodes, node.insn, node.nominators);
/*     */     }
/* 179 */     return myNodes;
/*     */   }
/*     */   
/*     */   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode node, Set<InjectionPoint> nominators) {
/* 183 */     myNodes.add(target.addInjectionNode(node));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inject(Target target, List<InjectionNodes.InjectionNode> nodes) {
/* 193 */     for (InjectionNodes.InjectionNode node : nodes) {
/* 194 */       if (node.isRemoved()) {
/* 195 */         if (this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 196 */           logger.warn("Target node for {} was removed by a previous injector in {}", new Object[] { this.info, target });
/*     */         }
/*     */         continue;
/*     */       } 
/* 200 */       inject(target, node);
/*     */     } 
/*     */     
/* 203 */     for (InjectionNodes.InjectionNode node : nodes) {
/* 204 */       postInject(target, node);
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
/*     */   private Collection<TargetNode> findTargetNodes(InjectorTarget injectorTarget, List<InjectionPoint> injectionPoints) {
/* 218 */     IMixinContext mixin = this.info.getContext();
/* 219 */     MethodNode method = injectorTarget.getMethod();
/* 220 */     Map<Integer, TargetNode> targetNodes = new TreeMap<Integer, TargetNode>();
/* 221 */     Collection<AbstractInsnNode> nodes = new ArrayList<AbstractInsnNode>(32);
/*     */     
/* 223 */     for (InjectionPoint injectionPoint : injectionPoints) {
/* 224 */       nodes.clear();
/*     */       
/* 226 */       if (injectorTarget.isMerged() && 
/* 227 */         !mixin.getClassName().equals(injectorTarget.getMergedBy()) && 
/* 228 */         !injectionPoint.checkPriority(injectorTarget.getMergedPriority(), mixin.getPriority())) {
/* 229 */         throw new InvalidInjectionException(this.info, String.format("%s on %s with priority %d cannot inject into %s merged by %s with priority %d", new Object[] { injectionPoint, this, 
/* 230 */                 Integer.valueOf(mixin.getPriority()), injectorTarget, injectorTarget
/* 231 */                 .getMergedBy(), Integer.valueOf(injectorTarget.getMergedPriority()) }));
/*     */       }
/*     */       
/* 234 */       if (findTargetNodes(method, injectionPoint, injectorTarget.getSlice(injectionPoint), nodes)) {
/* 235 */         for (AbstractInsnNode insn : nodes) {
/* 236 */           Integer key = Integer.valueOf(method.instructions.indexOf(insn));
/* 237 */           TargetNode targetNode = targetNodes.get(key);
/* 238 */           if (targetNode == null) {
/* 239 */             targetNode = new TargetNode(insn);
/* 240 */             targetNodes.put(key, targetNode);
/*     */           } 
/* 242 */           targetNode.nominators.add(injectionPoint);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 247 */     return targetNodes.values();
/*     */   }
/*     */   
/*     */   protected boolean findTargetNodes(MethodNode into, InjectionPoint injectionPoint, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 251 */     return injectionPoint.find(into.desc, insns, nodes);
/*     */   }
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/* 255 */     if (target.classNode != this.classNode) {
/* 256 */       throw new InvalidInjectionException(this.info, "Target class does not match injector class in " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void inject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postInject(Target target, InjectionNodes.InjectionNode node) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractInsnNode invokeHandler(InsnList insns) {
/* 273 */     return invokeHandler(insns, this.methodNode);
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
/*     */   protected AbstractInsnNode invokeHandler(InsnList insns, MethodNode handler) {
/* 285 */     boolean isPrivate = ((handler.access & 0x2) != 0);
/* 286 */     int invokeOpcode = this.isStatic ? 184 : (isPrivate ? 183 : 182);
/* 287 */     MethodInsnNode insn = new MethodInsnNode(invokeOpcode, this.classNode.name, handler.name, handler.desc, false);
/* 288 */     insns.add((AbstractInsnNode)insn);
/* 289 */     this.info.addCallbackInvocation(handler);
/* 290 */     return (AbstractInsnNode)insn;
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
/*     */   protected void throwException(InsnList insns, String exceptionType, String message) {
/* 302 */     insns.add((AbstractInsnNode)new TypeInsnNode(187, exceptionType));
/* 303 */     insns.add((AbstractInsnNode)new InsnNode(89));
/* 304 */     insns.add((AbstractInsnNode)new LdcInsnNode(message));
/* 305 */     insns.add((AbstractInsnNode)new MethodInsnNode(183, exceptionType, "<init>", "(Ljava/lang/String;)V", false));
/* 306 */     insns.add((AbstractInsnNode)new InsnNode(191));
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
/*     */   public static boolean canCoerce(Type from, Type to) {
/* 318 */     if (from.getSort() == 10 && to.getSort() == 10) {
/* 319 */       return canCoerce(ClassInfo.forType(from), ClassInfo.forType(to));
/*     */     }
/*     */     
/* 322 */     return canCoerce(from.getDescriptor(), to.getDescriptor());
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
/*     */   public static boolean canCoerce(String from, String to) {
/* 334 */     if (from.length() > 1 || to.length() > 1) {
/* 335 */       return false;
/*     */     }
/*     */     
/* 338 */     return canCoerce(from.charAt(0), to.charAt(0));
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
/*     */   public static boolean canCoerce(char from, char to) {
/* 350 */     return (to == 'I' && "IBSCZ".indexOf(from) > -1);
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
/*     */   private static boolean canCoerce(ClassInfo from, ClassInfo to) {
/* 363 */     return (from != null && to != null && (to == from || to.hasSuperClass(from)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\code\Injector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */