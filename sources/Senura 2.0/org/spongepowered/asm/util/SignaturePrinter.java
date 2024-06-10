/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.LocalVariableNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignaturePrinter
/*     */ {
/*     */   private final String name;
/*     */   private final Type returnType;
/*     */   private final Type[] argTypes;
/*     */   private final String[] argNames;
/*  64 */   private String modifiers = "private void";
/*     */ 
/*     */   
/*     */   private boolean fullyQualified;
/*     */ 
/*     */ 
/*     */   
/*     */   public SignaturePrinter(MethodNode method) {
/*  72 */     this(method.name, Type.VOID_TYPE, Type.getArgumentTypes(method.desc));
/*  73 */     setModifiers(method);
/*     */   }
/*     */   
/*     */   public SignaturePrinter(MethodNode method, String[] argNames) {
/*  77 */     this(method.name, Type.VOID_TYPE, Type.getArgumentTypes(method.desc), argNames);
/*  78 */     setModifiers(method);
/*     */   }
/*     */   
/*     */   public SignaturePrinter(MemberInfo member) {
/*  82 */     this(member.name, member.desc);
/*     */   }
/*     */   
/*     */   public SignaturePrinter(String name, String desc) {
/*  86 */     this(name, Type.getReturnType(desc), Type.getArgumentTypes(desc));
/*     */   }
/*     */   
/*     */   public SignaturePrinter(String name, Type returnType, Type[] args) {
/*  90 */     this.name = name;
/*  91 */     this.returnType = returnType;
/*  92 */     this.argTypes = new Type[args.length];
/*  93 */     this.argNames = new String[args.length];
/*  94 */     for (int l = 0, v = 0; l < args.length; l++) {
/*  95 */       if (args[l] != null) {
/*  96 */         this.argTypes[l] = args[l];
/*  97 */         this.argNames[l] = "var" + v++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public SignaturePrinter(String name, Type returnType, LocalVariableNode[] args) {
/* 103 */     this.name = name;
/* 104 */     this.returnType = returnType;
/* 105 */     this.argTypes = new Type[args.length];
/* 106 */     this.argNames = new String[args.length];
/* 107 */     for (int l = 0; l < args.length; l++) {
/* 108 */       if (args[l] != null) {
/* 109 */         this.argTypes[l] = Type.getType((args[l]).desc);
/* 110 */         this.argNames[l] = (args[l]).name;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public SignaturePrinter(String name, Type returnType, Type[] argTypes, String[] argNames) {
/* 116 */     this.name = name;
/* 117 */     this.returnType = returnType;
/* 118 */     this.argTypes = argTypes;
/* 119 */     this.argNames = argNames;
/* 120 */     if (this.argTypes.length > this.argNames.length) {
/* 121 */       throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", new Object[] {
/* 122 */               Integer.valueOf(this.argNames.length), Integer.valueOf(this.argTypes.length)
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedArgs() {
/* 130 */     return appendArgs(new StringBuilder(), true, true).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReturnType() {
/* 137 */     return getTypeName(this.returnType, false, this.fullyQualified);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModifiers(MethodNode method) {
/* 146 */     String returnType = getTypeName(Type.getReturnType(method.desc), false, this.fullyQualified);
/* 147 */     if ((method.access & 0x1) != 0) {
/* 148 */       setModifiers("public " + returnType);
/* 149 */     } else if ((method.access & 0x4) != 0) {
/* 150 */       setModifiers("protected " + returnType);
/* 151 */     } else if ((method.access & 0x2) != 0) {
/* 152 */       setModifiers("private " + returnType);
/*     */     } else {
/* 154 */       setModifiers(returnType);
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
/*     */   public SignaturePrinter setModifiers(String modifiers) {
/* 167 */     this.modifiers = modifiers.replace("${returnType}", getReturnType());
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignaturePrinter setFullyQualified(boolean fullyQualified) {
/* 179 */     this.fullyQualified = fullyQualified;
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyQualified() {
/* 188 */     return this.fullyQualified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 196 */     return appendArgs((new StringBuilder()).append(this.modifiers).append(" ").append(this.name), false, true).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toDescriptor() {
/* 203 */     StringBuilder args = appendArgs(new StringBuilder(), true, false);
/* 204 */     return args.append(getTypeName(this.returnType, false, this.fullyQualified)).toString();
/*     */   }
/*     */   
/*     */   private StringBuilder appendArgs(StringBuilder sb, boolean typesOnly, boolean pretty) {
/* 208 */     sb.append('(');
/* 209 */     for (int var = 0; var < this.argTypes.length; var++) {
/* 210 */       if (this.argTypes[var] != null) {
/*     */         
/* 212 */         if (var > 0) {
/* 213 */           sb.append(',');
/* 214 */           if (pretty) {
/* 215 */             sb.append(' ');
/*     */           }
/*     */         } 
/*     */         try {
/* 219 */           String name = typesOnly ? null : (Strings.isNullOrEmpty(this.argNames[var]) ? ("unnamed" + var) : this.argNames[var]);
/* 220 */           appendType(sb, this.argTypes[var], name);
/* 221 */         } catch (Exception ex) {
/*     */           
/* 223 */           throw new RuntimeException(ex);
/*     */         } 
/*     */       } 
/* 226 */     }  return sb.append(")");
/*     */   }
/*     */   
/*     */   private StringBuilder appendType(StringBuilder sb, Type type, String name) {
/* 230 */     switch (type.getSort()) {
/*     */       case 9:
/* 232 */         return appendArraySuffix(appendType(sb, type.getElementType(), name), type);
/*     */       case 10:
/* 234 */         return appendType(sb, type.getClassName(), name);
/*     */     } 
/* 236 */     sb.append(getTypeName(type, false, this.fullyQualified));
/* 237 */     if (name != null) {
/* 238 */       sb.append(' ').append(name);
/*     */     }
/* 240 */     return sb;
/*     */   }
/*     */ 
/*     */   
/*     */   private StringBuilder appendType(StringBuilder sb, String typeName, String name) {
/* 245 */     if (!this.fullyQualified) {
/* 246 */       typeName = typeName.substring(typeName.lastIndexOf('.') + 1);
/*     */     }
/* 248 */     sb.append(typeName);
/* 249 */     if (typeName.endsWith("CallbackInfoReturnable")) {
/* 250 */       sb.append('<').append(getTypeName(this.returnType, true, this.fullyQualified)).append('>');
/*     */     }
/* 252 */     if (name != null) {
/* 253 */       sb.append(' ').append(name);
/*     */     }
/* 255 */     return sb;
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
/*     */   public static String getTypeName(Type type, boolean box) {
/* 267 */     return getTypeName(type, box, false);
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
/*     */   public static String getTypeName(Type type, boolean box, boolean fullyQualified) {
/*     */     String typeName;
/* 280 */     switch (type.getSort()) { case 0:
/* 281 */         return box ? "Void" : "void";
/* 282 */       case 1: return box ? "Boolean" : "boolean";
/* 283 */       case 2: return box ? "Character" : "char";
/* 284 */       case 3: return box ? "Byte" : "byte";
/* 285 */       case 4: return box ? "Short" : "short";
/* 286 */       case 5: return box ? "Integer" : "int";
/* 287 */       case 6: return box ? "Float" : "float";
/* 288 */       case 7: return box ? "Long" : "long";
/* 289 */       case 8: return box ? "Double" : "double";
/* 290 */       case 9: return getTypeName(type.getElementType(), box, fullyQualified) + arraySuffix(type);
/*     */       case 10:
/* 292 */         typeName = type.getClassName();
/* 293 */         if (!fullyQualified) {
/* 294 */           typeName = typeName.substring(typeName.lastIndexOf('.') + 1);
/*     */         }
/* 296 */         return typeName; }
/*     */     
/* 298 */     return "Object";
/*     */   }
/*     */ 
/*     */   
/*     */   private static String arraySuffix(Type type) {
/* 303 */     return Strings.repeat("[]", type.getDimensions());
/*     */   }
/*     */ 
/*     */   
/*     */   private static StringBuilder appendArraySuffix(StringBuilder sb, Type type) {
/* 308 */     for (int i = 0; i < type.getDimensions(); i++) {
/* 309 */       sb.append("[]");
/*     */     }
/* 311 */     return sb;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\SignaturePrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */