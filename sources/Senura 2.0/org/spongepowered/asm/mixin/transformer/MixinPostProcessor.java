/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.spongepowered.asm.lib.ClassReader;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.FieldVisitor;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
/*     */ import org.spongepowered.asm.transformers.MixinClassWriter;
/*     */ import org.spongepowered.asm.transformers.TreeTransformer;
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
/*     */ class MixinPostProcessor
/*     */   extends TreeTransformer
/*     */   implements MixinConfig.IListener
/*     */ {
/*  66 */   private final Set<String> syntheticInnerClasses = new HashSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private final Map<String, MixinInfo> accessorMixins = new HashMap<String, MixinInfo>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private final Set<String> loadable = new HashSet<String>();
/*     */ 
/*     */   
/*     */   public void onInit(MixinInfo mixin) {
/*  80 */     for (String innerClass : mixin.getSyntheticInnerClasses()) {
/*  81 */       registerSyntheticInner(innerClass.replace('/', '.'));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPrepare(MixinInfo mixin) {
/*  87 */     String className = mixin.getClassName();
/*     */     
/*  89 */     if (mixin.isLoadable()) {
/*  90 */       registerLoadable(className);
/*     */     }
/*     */     
/*  93 */     if (mixin.isAccessor()) {
/*  94 */       registerAccessor(mixin);
/*     */     }
/*     */   }
/*     */   
/*     */   void registerSyntheticInner(String className) {
/*  99 */     this.syntheticInnerClasses.add(className);
/*     */   }
/*     */   
/*     */   void registerLoadable(String className) {
/* 103 */     this.loadable.add(className);
/*     */   }
/*     */   
/*     */   void registerAccessor(MixinInfo mixin) {
/* 107 */     registerLoadable(mixin.getClassName());
/* 108 */     this.accessorMixins.put(mixin.getClassName(), mixin);
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
/*     */   boolean canTransform(String className) {
/* 120 */     return (this.syntheticInnerClasses.contains(className) || this.loadable.contains(className));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 128 */     return getClass().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDelegationExcluded() {
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] transformClassBytes(String name, String transformedName, byte[] bytes) {
/* 146 */     if (this.syntheticInnerClasses.contains(transformedName)) {
/* 147 */       return processSyntheticInner(bytes);
/*     */     }
/*     */     
/* 150 */     if (this.accessorMixins.containsKey(transformedName)) {
/* 151 */       MixinInfo mixin = this.accessorMixins.get(transformedName);
/* 152 */       return processAccessor(bytes, mixin);
/*     */     } 
/*     */     
/* 155 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] processSyntheticInner(byte[] bytes) {
/* 164 */     ClassReader cr = new ClassReader(bytes);
/* 165 */     MixinClassWriter mixinClassWriter = new MixinClassWriter(cr, 0);
/*     */     
/* 167 */     ClassVisitor visibilityVisitor = new ClassVisitor(327680, (ClassVisitor)mixinClassWriter)
/*     */       {
/*     */         public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
/*     */         {
/* 171 */           super.visit(version, access | 0x1, name, signature, superName, interfaces);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/* 177 */           if ((access & 0x6) == 0) {
/* 178 */             access |= 0x1;
/*     */           }
/* 180 */           return super.visitField(access, name, desc, signature, value);
/*     */         }
/*     */ 
/*     */         
/*     */         public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 185 */           if ((access & 0x6) == 0) {
/* 186 */             access |= 0x1;
/*     */           }
/* 188 */           return super.visitMethod(access, name, desc, signature, exceptions);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 193 */     cr.accept(visibilityVisitor, 8);
/* 194 */     return mixinClassWriter.toByteArray();
/*     */   }
/*     */   
/*     */   private byte[] processAccessor(byte[] bytes, MixinInfo mixin) {
/* 198 */     if (!MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8)) {
/* 199 */       return bytes;
/*     */     }
/*     */     
/* 202 */     boolean transformed = false;
/* 203 */     MixinInfo.MixinClassNode classNode = mixin.getClassNode(0);
/* 204 */     ClassInfo targetClass = mixin.getTargets().get(0);
/*     */     
/* 206 */     for (Iterator<MixinInfo.MixinMethodNode> iter = classNode.mixinMethods.iterator(); iter.hasNext(); ) {
/* 207 */       MixinInfo.MixinMethodNode methodNode = iter.next();
/* 208 */       if (!Bytecode.hasFlag(methodNode, 8)) {
/*     */         continue;
/*     */       }
/*     */       
/* 212 */       AnnotationNode accessor = methodNode.getVisibleAnnotation((Class)Accessor.class);
/* 213 */       AnnotationNode invoker = methodNode.getVisibleAnnotation((Class)Invoker.class);
/* 214 */       if (accessor != null || invoker != null) {
/* 215 */         ClassInfo.Method method = getAccessorMethod(mixin, methodNode, targetClass);
/* 216 */         createProxy(methodNode, targetClass, method);
/* 217 */         transformed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     if (transformed) {
/* 222 */       return writeClass(classNode);
/*     */     }
/*     */     
/* 225 */     return bytes;
/*     */   }
/*     */   
/*     */   private static ClassInfo.Method getAccessorMethod(MixinInfo mixin, MethodNode methodNode, ClassInfo targetClass) throws MixinTransformerError {
/* 229 */     ClassInfo.Method method = mixin.getClassInfo().findMethod(methodNode, 10);
/*     */ 
/*     */ 
/*     */     
/* 233 */     if (!method.isRenamed()) {
/* 234 */       throw new MixinTransformerError("Unexpected state: " + mixin + " loaded before " + targetClass + " was conformed");
/*     */     }
/*     */     
/* 237 */     return method;
/*     */   }
/*     */   
/*     */   private static void createProxy(MethodNode methodNode, ClassInfo targetClass, ClassInfo.Method method) {
/* 241 */     methodNode.instructions.clear();
/* 242 */     Type[] args = Type.getArgumentTypes(methodNode.desc);
/* 243 */     Type returnType = Type.getReturnType(methodNode.desc);
/* 244 */     Bytecode.loadArgs(args, methodNode.instructions, 0);
/* 245 */     methodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(184, targetClass.getName(), method.getName(), methodNode.desc, false));
/* 246 */     methodNode.instructions.add((AbstractInsnNode)new InsnNode(returnType.getOpcode(172)));
/* 247 */     methodNode.maxStack = Bytecode.getFirstNonArgLocalIndex(args, false);
/* 248 */     methodNode.maxLocals = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MixinPostProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */