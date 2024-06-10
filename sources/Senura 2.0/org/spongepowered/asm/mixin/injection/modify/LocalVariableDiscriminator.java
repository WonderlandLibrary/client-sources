/*     */ package org.spongepowered.asm.mixin.injection.modify;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.LocalVariableNode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
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
/*     */ public class LocalVariableDiscriminator
/*     */ {
/*     */   private final boolean argsOnly;
/*     */   private final int ordinal;
/*     */   private final int index;
/*     */   private final Set<String> names;
/*     */   private final boolean print;
/*     */   
/*     */   public static class Context
/*     */     implements PrettyPrinter.IPrettyPrintable
/*     */   {
/*     */     final Target target;
/*     */     final Type returnType;
/*     */     final AbstractInsnNode node;
/*     */     final int baseArgIndex;
/*     */     final Local[] locals;
/*     */     private final boolean isStatic;
/*     */     
/*     */     public class Local
/*     */     {
/*  68 */       int ord = 0;
/*     */ 
/*     */ 
/*     */       
/*     */       String name;
/*     */ 
/*     */ 
/*     */       
/*     */       Type type;
/*     */ 
/*     */ 
/*     */       
/*     */       public Local(String name, Type type) {
/*  81 */         this.name = name;
/*  82 */         this.type = type;
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/*  87 */         return String.format("Local[ordinal=%d, name=%s, type=%s]", new Object[] { Integer.valueOf(this.ord), this.name, this.type });
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Context(Type returnType, boolean argsOnly, Target target, AbstractInsnNode node) {
/* 125 */       this.isStatic = Bytecode.methodIsStatic(target.method);
/* 126 */       this.returnType = returnType;
/* 127 */       this.target = target;
/* 128 */       this.node = node;
/* 129 */       this.baseArgIndex = this.isStatic ? 0 : 1;
/* 130 */       this.locals = initLocals(target, argsOnly, node);
/* 131 */       initOrdinals();
/*     */     }
/*     */     
/*     */     private Local[] initLocals(Target target, boolean argsOnly, AbstractInsnNode node) {
/* 135 */       if (!argsOnly) {
/* 136 */         LocalVariableNode[] locals = Locals.getLocalsAt(target.classNode, target.method, node);
/* 137 */         if (locals != null) {
/* 138 */           Local[] arrayOfLocal = new Local[locals.length];
/* 139 */           for (int l = 0; l < locals.length; l++) {
/* 140 */             if (locals[l] != null) {
/* 141 */               arrayOfLocal[l] = new Local((locals[l]).name, Type.getType((locals[l]).desc));
/*     */             }
/*     */           } 
/* 144 */           return arrayOfLocal;
/*     */         } 
/*     */       } 
/*     */       
/* 148 */       Local[] lvt = new Local[this.baseArgIndex + target.arguments.length];
/* 149 */       if (!this.isStatic) {
/* 150 */         lvt[0] = new Local("this", Type.getType(target.classNode.name));
/*     */       }
/* 152 */       for (int local = this.baseArgIndex; local < lvt.length; local++) {
/* 153 */         Type arg = target.arguments[local - this.baseArgIndex];
/* 154 */         lvt[local] = new Local("arg" + local, arg);
/*     */       } 
/* 156 */       return lvt;
/*     */     }
/*     */     
/*     */     private void initOrdinals() {
/* 160 */       Map<Type, Integer> ordinalMap = new HashMap<Type, Integer>();
/* 161 */       for (int l = 0; l < this.locals.length; l++) {
/* 162 */         Integer ordinal = Integer.valueOf(0);
/* 163 */         if (this.locals[l] != null) {
/* 164 */           ordinal = ordinalMap.get((this.locals[l]).type);
/* 165 */           ordinalMap.put((this.locals[l]).type, ordinal = Integer.valueOf((ordinal == null) ? 0 : (ordinal.intValue() + 1)));
/* 166 */           (this.locals[l]).ord = ordinal.intValue();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void print(PrettyPrinter printer) {
/* 173 */       printer.add("%5s  %7s  %30s  %-50s  %s", new Object[] { "INDEX", "ORDINAL", "TYPE", "NAME", "CANDIDATE" });
/* 174 */       for (int l = this.baseArgIndex; l < this.locals.length; l++) {
/* 175 */         Local local = this.locals[l];
/* 176 */         if (local != null) {
/* 177 */           Type localType = local.type;
/* 178 */           String localName = local.name;
/* 179 */           int ordinal = local.ord;
/* 180 */           String candidate = this.returnType.equals(localType) ? "YES" : "-";
/* 181 */           printer.add("[%3d]    [%3d]  %30s  %-50s  %s", new Object[] { Integer.valueOf(l), Integer.valueOf(ordinal), SignaturePrinter.getTypeName(localType, false), localName, candidate });
/* 182 */         } else if (l > 0) {
/* 183 */           Local prevLocal = this.locals[l - 1];
/* 184 */           boolean isTop = (prevLocal != null && prevLocal.type != null && prevLocal.type.getSize() > 1);
/* 185 */           printer.add("[%3d]           %30s", new Object[] { Integer.valueOf(l), isTop ? "<top>" : "-" });
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class Local
/*     */   {
/*     */     int ord = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     String name;
/*     */ 
/*     */ 
/*     */     
/*     */     Type type;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Local(String name, Type type) {
/*     */       this.name = name;
/*     */       this.type = type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*     */       return String.format("Local[ordinal=%d, name=%s, type=%s]", new Object[] { Integer.valueOf(this.ord), this.name, this.type });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalVariableDiscriminator(boolean argsOnly, int ordinal, int index, Set<String> names, boolean print) {
/* 226 */     this.argsOnly = argsOnly;
/* 227 */     this.ordinal = ordinal;
/* 228 */     this.index = index;
/* 229 */     this.names = Collections.unmodifiableSet(names);
/* 230 */     this.print = print;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArgsOnly() {
/* 238 */     return this.argsOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrdinal() {
/* 245 */     return this.ordinal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 252 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getNames() {
/* 259 */     return this.names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNames() {
/* 266 */     return !this.names.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean printLVT() {
/* 273 */     return this.print;
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
/*     */   protected boolean isImplicit(Context context) {
/* 286 */     return (this.ordinal < 0 && this.index < context.baseArgIndex && this.names.isEmpty());
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
/*     */   public int findLocal(Type returnType, boolean argsOnly, Target target, AbstractInsnNode node) {
/*     */     try {
/* 300 */       return findLocal(new Context(returnType, argsOnly, target, node));
/* 301 */     } catch (InvalidImplicitDiscriminatorException ex) {
/* 302 */       return -2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int findLocal(Context context) {
/* 313 */     if (isImplicit(context)) {
/* 314 */       return findImplicitLocal(context);
/*     */     }
/* 316 */     return findExplicitLocal(context);
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
/*     */   private int findImplicitLocal(Context context) {
/* 328 */     int found = 0;
/* 329 */     int count = 0;
/* 330 */     for (int index = context.baseArgIndex; index < context.locals.length; index++) {
/* 331 */       Context.Local local = context.locals[index];
/* 332 */       if (local != null && local.type.equals(context.returnType)) {
/*     */ 
/*     */         
/* 335 */         count++;
/* 336 */         found = index;
/*     */       } 
/*     */     } 
/* 339 */     if (count == 1) {
/* 340 */       return found;
/*     */     }
/*     */     
/* 343 */     throw new InvalidImplicitDiscriminatorException("Found " + count + " candidate variables but exactly 1 is required.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findExplicitLocal(Context context) {
/* 354 */     for (int index = context.baseArgIndex; index < context.locals.length; index++) {
/* 355 */       Context.Local local = context.locals[index];
/* 356 */       if (local != null && local.type.equals(context.returnType))
/*     */       {
/*     */         
/* 359 */         if (this.ordinal > -1) {
/* 360 */           if (this.ordinal == local.ord) {
/* 361 */             return index;
/*     */           
/*     */           }
/*     */         }
/* 365 */         else if (this.index >= context.baseArgIndex) {
/* 366 */           if (this.index == index) {
/* 367 */             return index;
/*     */           
/*     */           }
/*     */         }
/* 371 */         else if (this.names.contains(local.name)) {
/* 372 */           return index;
/*     */         } 
/*     */       }
/*     */     } 
/* 376 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocalVariableDiscriminator parse(AnnotationNode annotation) {
/* 386 */     boolean argsOnly = ((Boolean)Annotations.getValue(annotation, "argsOnly", Boolean.FALSE)).booleanValue();
/* 387 */     int ordinal = ((Integer)Annotations.getValue(annotation, "ordinal", Integer.valueOf(-1))).intValue();
/* 388 */     int index = ((Integer)Annotations.getValue(annotation, "index", Integer.valueOf(-1))).intValue();
/* 389 */     boolean print = ((Boolean)Annotations.getValue(annotation, "print", Boolean.FALSE)).booleanValue();
/*     */     
/* 391 */     Set<String> names = new HashSet<String>();
/* 392 */     List<String> namesList = (List<String>)Annotations.getValue(annotation, "name", null);
/* 393 */     if (namesList != null) {
/* 394 */       names.addAll(namesList);
/*     */     }
/*     */     
/* 397 */     return new LocalVariableDiscriminator(argsOnly, ordinal, index, names, print);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\modify\LocalVariableDiscriminator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */