/*     */ package org.spongepowered.asm.lib.util;
/*     */ 
/*     */ import org.spongepowered.asm.lib.signature.SignatureVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TraceSignatureVisitor
/*     */   extends SignatureVisitor
/*     */ {
/*     */   private final StringBuilder declaration;
/*     */   private boolean isInterface;
/*     */   private boolean seenFormalParameter;
/*     */   private boolean seenInterfaceBound;
/*     */   private boolean seenParameter;
/*     */   private boolean seenInterface;
/*     */   private StringBuilder returnType;
/*     */   private StringBuilder exceptions;
/*     */   private int argumentStack;
/*     */   private int arrayStack;
/*  75 */   private String separator = "";
/*     */   
/*     */   public TraceSignatureVisitor(int access) {
/*  78 */     super(327680);
/*  79 */     this.isInterface = ((access & 0x200) != 0);
/*  80 */     this.declaration = new StringBuilder();
/*     */   }
/*     */   
/*     */   private TraceSignatureVisitor(StringBuilder buf) {
/*  84 */     super(327680);
/*  85 */     this.declaration = buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitFormalTypeParameter(String name) {
/*  90 */     this.declaration.append(this.seenFormalParameter ? ", " : "<").append(name);
/*  91 */     this.seenFormalParameter = true;
/*  92 */     this.seenInterfaceBound = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitClassBound() {
/*  97 */     this.separator = " extends ";
/*  98 */     startType();
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterfaceBound() {
/* 104 */     this.separator = this.seenInterfaceBound ? ", " : " extends ";
/* 105 */     this.seenInterfaceBound = true;
/* 106 */     startType();
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitSuperclass() {
/* 112 */     endFormals();
/* 113 */     this.separator = " extends ";
/* 114 */     startType();
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterface() {
/* 120 */     this.separator = this.seenInterface ? ", " : (this.isInterface ? " extends " : " implements ");
/*     */     
/* 122 */     this.seenInterface = true;
/* 123 */     startType();
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitParameterType() {
/* 129 */     endFormals();
/* 130 */     if (this.seenParameter) {
/* 131 */       this.declaration.append(", ");
/*     */     } else {
/* 133 */       this.seenParameter = true;
/* 134 */       this.declaration.append('(');
/*     */     } 
/* 136 */     startType();
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitReturnType() {
/* 142 */     endFormals();
/* 143 */     if (this.seenParameter) {
/* 144 */       this.seenParameter = false;
/*     */     } else {
/* 146 */       this.declaration.append('(');
/*     */     } 
/* 148 */     this.declaration.append(')');
/* 149 */     this.returnType = new StringBuilder();
/* 150 */     return new TraceSignatureVisitor(this.returnType);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitExceptionType() {
/* 155 */     if (this.exceptions == null) {
/* 156 */       this.exceptions = new StringBuilder();
/*     */     } else {
/* 158 */       this.exceptions.append(", ");
/*     */     } 
/*     */     
/* 161 */     return new TraceSignatureVisitor(this.exceptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitBaseType(char descriptor) {
/* 166 */     switch (descriptor) {
/*     */       case 'V':
/* 168 */         this.declaration.append("void");
/*     */         break;
/*     */       case 'B':
/* 171 */         this.declaration.append("byte");
/*     */         break;
/*     */       case 'J':
/* 174 */         this.declaration.append("long");
/*     */         break;
/*     */       case 'Z':
/* 177 */         this.declaration.append("boolean");
/*     */         break;
/*     */       case 'I':
/* 180 */         this.declaration.append("int");
/*     */         break;
/*     */       case 'S':
/* 183 */         this.declaration.append("short");
/*     */         break;
/*     */       case 'C':
/* 186 */         this.declaration.append("char");
/*     */         break;
/*     */       case 'F':
/* 189 */         this.declaration.append("float");
/*     */         break;
/*     */       
/*     */       default:
/* 193 */         this.declaration.append("double");
/*     */         break;
/*     */     } 
/* 196 */     endType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeVariable(String name) {
/* 201 */     this.declaration.append(name);
/* 202 */     endType();
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitArrayType() {
/* 207 */     startType();
/* 208 */     this.arrayStack |= 0x1;
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassType(String name) {
/* 214 */     if ("java/lang/Object".equals(name)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       boolean needObjectClass = (this.argumentStack % 2 != 0 || this.seenParameter);
/* 221 */       if (needObjectClass) {
/* 222 */         this.declaration.append(this.separator).append(name.replace('/', '.'));
/*     */       }
/*     */     } else {
/* 225 */       this.declaration.append(this.separator).append(name.replace('/', '.'));
/*     */     } 
/* 227 */     this.separator = "";
/* 228 */     this.argumentStack *= 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInnerClassType(String name) {
/* 233 */     if (this.argumentStack % 2 != 0) {
/* 234 */       this.declaration.append('>');
/*     */     }
/* 236 */     this.argumentStack /= 2;
/* 237 */     this.declaration.append('.');
/* 238 */     this.declaration.append(this.separator).append(name.replace('/', '.'));
/* 239 */     this.separator = "";
/* 240 */     this.argumentStack *= 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeArgument() {
/* 245 */     if (this.argumentStack % 2 == 0) {
/* 246 */       this.argumentStack++;
/* 247 */       this.declaration.append('<');
/*     */     } else {
/* 249 */       this.declaration.append(", ");
/*     */     } 
/* 251 */     this.declaration.append('?');
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitTypeArgument(char tag) {
/* 256 */     if (this.argumentStack % 2 == 0) {
/* 257 */       this.argumentStack++;
/* 258 */       this.declaration.append('<');
/*     */     } else {
/* 260 */       this.declaration.append(", ");
/*     */     } 
/*     */     
/* 263 */     if (tag == '+') {
/* 264 */       this.declaration.append("? extends ");
/* 265 */     } else if (tag == '-') {
/* 266 */       this.declaration.append("? super ");
/*     */     } 
/*     */     
/* 269 */     startType();
/* 270 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 275 */     if (this.argumentStack % 2 != 0) {
/* 276 */       this.declaration.append('>');
/*     */     }
/* 278 */     this.argumentStack /= 2;
/* 279 */     endType();
/*     */   }
/*     */   
/*     */   public String getDeclaration() {
/* 283 */     return this.declaration.toString();
/*     */   }
/*     */   
/*     */   public String getReturnType() {
/* 287 */     return (this.returnType == null) ? null : this.returnType.toString();
/*     */   }
/*     */   
/*     */   public String getExceptions() {
/* 291 */     return (this.exceptions == null) ? null : this.exceptions.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void endFormals() {
/* 297 */     if (this.seenFormalParameter) {
/* 298 */       this.declaration.append('>');
/* 299 */       this.seenFormalParameter = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startType() {
/* 304 */     this.arrayStack *= 2;
/*     */   }
/*     */   
/*     */   private void endType() {
/* 308 */     if (this.arrayStack % 2 == 0) {
/* 309 */       this.arrayStack /= 2;
/*     */     } else {
/* 311 */       while (this.arrayStack % 2 != 0) {
/* 312 */         this.arrayStack /= 2;
/* 313 */         this.declaration.append("[]");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\TraceSignatureVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */