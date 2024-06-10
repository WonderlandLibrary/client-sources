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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CheckSignatureAdapter
/*     */   extends SignatureVisitor
/*     */ {
/*     */   public static final int CLASS_SIGNATURE = 0;
/*     */   public static final int METHOD_SIGNATURE = 1;
/*     */   public static final int TYPE_SIGNATURE = 2;
/*     */   private static final int EMPTY = 1;
/*     */   private static final int FORMAL = 2;
/*     */   private static final int BOUND = 4;
/*     */   private static final int SUPER = 8;
/*     */   private static final int PARAM = 16;
/*     */   private static final int RETURN = 32;
/*     */   private static final int SIMPLE_TYPE = 64;
/*     */   private static final int CLASS_TYPE = 128;
/*     */   private static final int END = 256;
/*     */   private final int type;
/*     */   private int state;
/*     */   private boolean canBeVoid;
/*     */   private final SignatureVisitor sv;
/*     */   
/*     */   public CheckSignatureAdapter(int type, SignatureVisitor sv) {
/* 116 */     this(327680, type, sv);
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
/*     */   protected CheckSignatureAdapter(int api, int type, SignatureVisitor sv) {
/* 135 */     super(api);
/* 136 */     this.type = type;
/* 137 */     this.state = 1;
/* 138 */     this.sv = sv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFormalTypeParameter(String name) {
/* 145 */     if (this.type == 2 || (this.state != 1 && this.state != 2 && this.state != 4))
/*     */     {
/* 147 */       throw new IllegalStateException();
/*     */     }
/* 149 */     CheckMethodAdapter.checkIdentifier(name, "formal type parameter");
/* 150 */     this.state = 2;
/* 151 */     if (this.sv != null) {
/* 152 */       this.sv.visitFormalTypeParameter(name);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitClassBound() {
/* 158 */     if (this.state != 2) {
/* 159 */       throw new IllegalStateException();
/*     */     }
/* 161 */     this.state = 4;
/* 162 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitClassBound();
/* 163 */     return new CheckSignatureAdapter(2, v);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterfaceBound() {
/* 168 */     if (this.state != 2 && this.state != 4) {
/* 169 */       throw new IllegalArgumentException();
/*     */     }
/* 171 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitInterfaceBound();
/* 172 */     return new CheckSignatureAdapter(2, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitSuperclass() {
/* 179 */     if (this.type != 0 || (this.state & 0x7) == 0) {
/* 180 */       throw new IllegalArgumentException();
/*     */     }
/* 182 */     this.state = 8;
/* 183 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitSuperclass();
/* 184 */     return new CheckSignatureAdapter(2, v);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterface() {
/* 189 */     if (this.state != 8) {
/* 190 */       throw new IllegalStateException();
/*     */     }
/* 192 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitInterface();
/* 193 */     return new CheckSignatureAdapter(2, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitParameterType() {
/* 200 */     if (this.type != 1 || (this.state & 0x17) == 0)
/*     */     {
/* 202 */       throw new IllegalArgumentException();
/*     */     }
/* 204 */     this.state = 16;
/* 205 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitParameterType();
/* 206 */     return new CheckSignatureAdapter(2, v);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitReturnType() {
/* 211 */     if (this.type != 1 || (this.state & 0x17) == 0)
/*     */     {
/* 213 */       throw new IllegalArgumentException();
/*     */     }
/* 215 */     this.state = 32;
/* 216 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitReturnType();
/* 217 */     CheckSignatureAdapter cv = new CheckSignatureAdapter(2, v);
/* 218 */     cv.canBeVoid = true;
/* 219 */     return cv;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitExceptionType() {
/* 224 */     if (this.state != 32) {
/* 225 */       throw new IllegalStateException();
/*     */     }
/* 227 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitExceptionType();
/* 228 */     return new CheckSignatureAdapter(2, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitBaseType(char descriptor) {
/* 235 */     if (this.type != 2 || this.state != 1) {
/* 236 */       throw new IllegalStateException();
/*     */     }
/* 238 */     if (descriptor == 'V') {
/* 239 */       if (!this.canBeVoid) {
/* 240 */         throw new IllegalArgumentException();
/*     */       }
/*     */     }
/* 243 */     else if ("ZCBSIFJD".indexOf(descriptor) == -1) {
/* 244 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 247 */     this.state = 64;
/* 248 */     if (this.sv != null) {
/* 249 */       this.sv.visitBaseType(descriptor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeVariable(String name) {
/* 255 */     if (this.type != 2 || this.state != 1) {
/* 256 */       throw new IllegalStateException();
/*     */     }
/* 258 */     CheckMethodAdapter.checkIdentifier(name, "type variable");
/* 259 */     this.state = 64;
/* 260 */     if (this.sv != null) {
/* 261 */       this.sv.visitTypeVariable(name);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitArrayType() {
/* 267 */     if (this.type != 2 || this.state != 1) {
/* 268 */       throw new IllegalStateException();
/*     */     }
/* 270 */     this.state = 64;
/* 271 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitArrayType();
/* 272 */     return new CheckSignatureAdapter(2, v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassType(String name) {
/* 277 */     if (this.type != 2 || this.state != 1) {
/* 278 */       throw new IllegalStateException();
/*     */     }
/* 280 */     CheckMethodAdapter.checkInternalName(name, "class name");
/* 281 */     this.state = 128;
/* 282 */     if (this.sv != null) {
/* 283 */       this.sv.visitClassType(name);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInnerClassType(String name) {
/* 289 */     if (this.state != 128) {
/* 290 */       throw new IllegalStateException();
/*     */     }
/* 292 */     CheckMethodAdapter.checkIdentifier(name, "inner class name");
/* 293 */     if (this.sv != null) {
/* 294 */       this.sv.visitInnerClassType(name);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeArgument() {
/* 300 */     if (this.state != 128) {
/* 301 */       throw new IllegalStateException();
/*     */     }
/* 303 */     if (this.sv != null) {
/* 304 */       this.sv.visitTypeArgument();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitTypeArgument(char wildcard) {
/* 310 */     if (this.state != 128) {
/* 311 */       throw new IllegalStateException();
/*     */     }
/* 313 */     if ("+-=".indexOf(wildcard) == -1) {
/* 314 */       throw new IllegalArgumentException();
/*     */     }
/* 316 */     SignatureVisitor v = (this.sv == null) ? null : this.sv.visitTypeArgument(wildcard);
/* 317 */     return new CheckSignatureAdapter(2, v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 322 */     if (this.state != 128) {
/* 323 */       throw new IllegalStateException();
/*     */     }
/* 325 */     this.state = 256;
/* 326 */     if (this.sv != null)
/* 327 */       this.sv.visitEnd(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\CheckSignatureAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */