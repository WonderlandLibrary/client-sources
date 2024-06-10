/*     */ package org.spongepowered.asm.util.throwables;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyntheticBridgeException
/*     */   extends MixinException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Problem problem;
/*     */   private final String name;
/*     */   private final String desc;
/*     */   private final int index;
/*     */   private final AbstractInsnNode a;
/*     */   private final AbstractInsnNode b;
/*     */   
/*     */   public enum Problem
/*     */   {
/*  58 */     BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"),
/*  59 */     BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"),
/*  60 */     BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s"),
/*  61 */     BAD_INVOKE_NAME("Conflicting synthetic bridge target method name in synthetic bridge method %1$s%2$s Existing:%6$s Incoming:%7$s"),
/*  62 */     BAD_INVOKE_DESC("Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s"),
/*  63 */     BAD_LENGTH("Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");
/*     */     
/*     */     private final String message;
/*     */     
/*     */     Problem(String message) {
/*  68 */       this.message = message;
/*     */     }
/*     */     
/*     */     String getMessage(String name, String desc, int index, AbstractInsnNode a, AbstractInsnNode b) {
/*  72 */       return String.format(this.message, new Object[] { name, desc, Integer.valueOf(index), Bytecode.getOpcodeName(a), Bytecode.getOpcodeName(a), 
/*  73 */             getInsnName(a), getInsnName(b), getInsnDesc(a), getInsnDesc(b) });
/*     */     }
/*     */     
/*     */     private static String getInsnName(AbstractInsnNode node) {
/*  77 */       return (node instanceof MethodInsnNode) ? ((MethodInsnNode)node).name : "";
/*     */     }
/*     */     
/*     */     private static String getInsnDesc(AbstractInsnNode node) {
/*  81 */       return (node instanceof MethodInsnNode) ? ((MethodInsnNode)node).desc : "";
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
/*     */ 
/*     */ 
/*     */   
/*     */   public SyntheticBridgeException(Problem problem, String name, String desc, int index, AbstractInsnNode a, AbstractInsnNode b) {
/* 108 */     super(problem.getMessage(name, desc, index, a, b));
/* 109 */     this.problem = problem;
/* 110 */     this.name = name;
/* 111 */     this.desc = desc;
/* 112 */     this.index = index;
/* 113 */     this.a = a;
/* 114 */     this.b = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printAnalysis(IMixinContext context, MethodNode mda, MethodNode mdb) {
/* 123 */     PrettyPrinter printer = new PrettyPrinter();
/* 124 */     printer.addWrapped(100, getMessage(), new Object[0]).hr();
/* 125 */     printer.add().kv("Method", this.name + this.desc).kv("Problem Type", this.problem).add().hr();
/* 126 */     String merged = (String)Annotations.getValue(Annotations.getVisible(mda, MixinMerged.class), "mixin");
/* 127 */     String owner = (merged != null) ? merged : context.getTargetClassRef().replace('/', '.');
/* 128 */     printMethod(printer.add("Existing method").add().kv("Owner", owner).add(), mda).hr();
/* 129 */     printMethod(printer.add("Incoming method").add().kv("Owner", context.getClassRef().replace('/', '.')).add(), mdb).hr();
/* 130 */     printProblem(printer, context, mda, mdb).print(System.err);
/*     */   }
/*     */   
/*     */   private PrettyPrinter printMethod(PrettyPrinter printer, MethodNode method) {
/* 134 */     int index = 0;
/* 135 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); index++) {
/* 136 */       printer.kv((index == this.index) ? ">>>>" : "", Bytecode.describeNode(iter.next()));
/*     */     }
/* 138 */     return printer.add(); } private PrettyPrinter printProblem(PrettyPrinter printer, IMixinContext context, MethodNode mda, MethodNode mdb) { ListIterator<AbstractInsnNode> ia, ib; Type[] argsa, argsb; int index; Type ta, tb;
/*     */     MethodInsnNode mdna, mdnb;
/*     */     Type arga[], argb[], rta, rtb;
/*     */     int i;
/* 142 */     Type target = Type.getObjectType(context.getTargetClassRef());
/*     */     
/* 144 */     printer.add("Analysis").add();
/* 145 */     switch (this.problem) {
/*     */       case BAD_INSN:
/* 147 */         printer.add("The bridge methods are not compatible because they contain incompatible opcodes");
/* 148 */         printer.add("at index " + this.index + ":").add();
/* 149 */         printer.kv("Existing opcode: %s", Bytecode.getOpcodeName(this.a));
/* 150 */         printer.kv("Incoming opcode: %s", Bytecode.getOpcodeName(this.b)).add();
/* 151 */         printer.add("This implies that the bridge methods are from different interfaces. This problem");
/* 152 */         printer.add("may not be resolvable without changing the base interfaces.").add();
/*     */         break;
/*     */       
/*     */       case BAD_LOAD:
/* 156 */         printer.add("The bridge methods are not compatible because they contain different variables at");
/* 157 */         printer.add("opcode index " + this.index + ".").add();
/*     */         
/* 159 */         ia = mda.instructions.iterator();
/* 160 */         ib = mdb.instructions.iterator();
/*     */         
/* 162 */         argsa = Type.getArgumentTypes(mda.desc);
/* 163 */         argsb = Type.getArgumentTypes(mdb.desc);
/* 164 */         for (index = 0; ia.hasNext() && ib.hasNext(); index++) {
/* 165 */           AbstractInsnNode na = ia.next();
/* 166 */           AbstractInsnNode nb = ib.next();
/* 167 */           if (na instanceof VarInsnNode && nb instanceof VarInsnNode) {
/* 168 */             VarInsnNode va = (VarInsnNode)na;
/* 169 */             VarInsnNode vb = (VarInsnNode)nb;
/*     */             
/* 171 */             Type type1 = (va.var > 0) ? argsa[va.var - 1] : target;
/* 172 */             Type type2 = (vb.var > 0) ? argsb[vb.var - 1] : target;
/* 173 */             printer.kv("Target " + index, "%8s %-2d %s", new Object[] { Bytecode.getOpcodeName((AbstractInsnNode)va), Integer.valueOf(va.var), type1 });
/* 174 */             printer.kv("Incoming " + index, "%8s %-2d %s", new Object[] { Bytecode.getOpcodeName((AbstractInsnNode)vb), Integer.valueOf(vb.var), type2 });
/*     */             
/* 176 */             if (type1.equals(type2)) {
/* 177 */               printer.kv("", "Types match: %s", new Object[] { type1 });
/* 178 */             } else if (type1.getSort() != type2.getSort()) {
/* 179 */               printer.kv("", "Types are incompatible");
/* 180 */             } else if (type1.getSort() == 10) {
/* 181 */               ClassInfo superClass = ClassInfo.getCommonSuperClassOrInterface(type1, type2);
/* 182 */               printer.kv("", "Common supertype: %s", new Object[] { superClass });
/*     */             } 
/*     */             
/* 185 */             printer.add();
/*     */           } 
/*     */         } 
/*     */         
/* 189 */         printer.add("Since this probably means that the methods come from different interfaces, you");
/* 190 */         printer.add("may have a \"multiple inheritance\" problem, it may not be possible to implement");
/* 191 */         printer.add("both root interfaces");
/*     */         break;
/*     */       
/*     */       case BAD_CAST:
/* 195 */         printer.add("Incompatible CHECKCAST encountered at opcode " + this.index + ", this could indicate that the bridge");
/* 196 */         printer.add("is casting down for contravariant generic types. It may be possible to coalesce the");
/* 197 */         printer.add("bridges by adjusting the types in the target method.").add();
/*     */         
/* 199 */         ta = Type.getObjectType(((TypeInsnNode)this.a).desc);
/* 200 */         tb = Type.getObjectType(((TypeInsnNode)this.b).desc);
/* 201 */         printer.kv("Target type", ta);
/* 202 */         printer.kv("Incoming type", tb);
/* 203 */         printer.kv("Common supertype", ClassInfo.getCommonSuperClassOrInterface(ta, tb)).add();
/*     */         break;
/*     */       
/*     */       case BAD_INVOKE_NAME:
/* 207 */         printer.add("Incompatible invocation targets in synthetic bridge. This is extremely unusual");
/* 208 */         printer.add("and implies that a remapping transformer has incorrectly remapped a method. This");
/* 209 */         printer.add("is an unrecoverable error.");
/*     */         break;
/*     */       
/*     */       case BAD_INVOKE_DESC:
/* 213 */         mdna = (MethodInsnNode)this.a;
/* 214 */         mdnb = (MethodInsnNode)this.b;
/*     */         
/* 216 */         arga = Type.getArgumentTypes(mdna.desc);
/* 217 */         argb = Type.getArgumentTypes(mdnb.desc);
/*     */         
/* 219 */         if (arga.length != argb.length) {
/* 220 */           int argCount = (Type.getArgumentTypes(mda.desc)).length;
/* 221 */           String winner = (arga.length == argCount) ? "The TARGET" : ((argb.length == argCount) ? " The INCOMING" : "NEITHER");
/*     */           
/* 223 */           printer.add("Mismatched invocation descriptors in synthetic bridge implies that a remapping");
/* 224 */           printer.add("transformer has incorrectly coalesced a bridge method with a conflicting name.");
/* 225 */           printer.add("Overlapping bridge methods should always have the same number of arguments, yet");
/* 226 */           printer.add("the target method has %d arguments, the incoming method has %d. This is an", new Object[] { Integer.valueOf(arga.length), Integer.valueOf(argb.length) });
/* 227 */           printer.add("unrecoverable error. %s method has the expected arg count of %d", new Object[] { winner, Integer.valueOf(argCount) });
/*     */           
/*     */           break;
/*     */         } 
/* 231 */         rta = Type.getReturnType(mdna.desc);
/* 232 */         rtb = Type.getReturnType(mdnb.desc);
/*     */         
/* 234 */         printer.add("Incompatible invocation descriptors in synthetic bridge implies that generified");
/* 235 */         printer.add("types are incompatible over one or more generic superclasses or interfaces. It may");
/* 236 */         printer.add("be possible to adjust the generic types on implemented members to rectify this");
/* 237 */         printer.add("problem by coalescing the appropriate generic types.").add();
/*     */         
/* 239 */         printTypeComparison(printer, "return type", rta, rtb);
/* 240 */         for (i = 0; i < arga.length; i++) {
/* 241 */           printTypeComparison(printer, "arg " + i, arga[i], argb[i]);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case BAD_LENGTH:
/* 247 */         printer.add("Mismatched bridge method length implies the bridge methods are incompatible");
/* 248 */         printer.add("and may originate from different superinterfaces. This is an unrecoverable");
/* 249 */         printer.add("error.").add();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     return printer; }
/*     */ 
/*     */   
/*     */   private PrettyPrinter printTypeComparison(PrettyPrinter printer, String index, Type tpa, Type tpb) {
/* 260 */     printer.kv("Target " + index, "%s", new Object[] { tpa });
/* 261 */     printer.kv("Incoming " + index, "%s", new Object[] { tpb });
/*     */     
/* 263 */     if (tpa.equals(tpb)) {
/* 264 */       printer.kv("Analysis", "Types match: %s", new Object[] { tpa });
/* 265 */     } else if (tpa.getSort() != tpb.getSort()) {
/* 266 */       printer.kv("Analysis", "Types are incompatible");
/* 267 */     } else if (tpa.getSort() == 10) {
/* 268 */       ClassInfo superClass = ClassInfo.getCommonSuperClassOrInterface(tpa, tpb);
/* 269 */       printer.kv("Analysis", "Common supertype: L%s;", new Object[] { superClass });
/*     */     } 
/*     */     
/* 272 */     return printer.add();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\throwables\SyntheticBridgeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */