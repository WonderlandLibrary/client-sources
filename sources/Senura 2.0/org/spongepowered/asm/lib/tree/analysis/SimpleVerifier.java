/*     */ package org.spongepowered.asm.lib.tree.analysis;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleVerifier
/*     */   extends BasicVerifier
/*     */ {
/*     */   private final Type currentClass;
/*     */   private final Type currentSuperClass;
/*     */   private final List<Type> currentClassInterfaces;
/*     */   private final boolean isInterface;
/*  69 */   private ClassLoader loader = getClass().getClassLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleVerifier() {
/*  75 */     this(null, null, false);
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
/*     */   public SimpleVerifier(Type currentClass, Type currentSuperClass, boolean isInterface) {
/*  91 */     this(currentClass, currentSuperClass, null, isInterface);
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
/*     */   public SimpleVerifier(Type currentClass, Type currentSuperClass, List<Type> currentClassInterfaces, boolean isInterface) {
/* 110 */     this(327680, currentClass, currentSuperClass, currentClassInterfaces, isInterface);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleVerifier(int api, Type currentClass, Type currentSuperClass, List<Type> currentClassInterfaces, boolean isInterface) {
/* 117 */     super(api);
/* 118 */     this.currentClass = currentClass;
/* 119 */     this.currentSuperClass = currentSuperClass;
/* 120 */     this.currentClassInterfaces = currentClassInterfaces;
/* 121 */     this.isInterface = isInterface;
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
/*     */   public void setClassLoader(ClassLoader loader) {
/* 133 */     this.loader = loader;
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicValue newValue(Type type) {
/* 138 */     if (type == null) {
/* 139 */       return BasicValue.UNINITIALIZED_VALUE;
/*     */     }
/*     */     
/* 142 */     boolean isArray = (type.getSort() == 9);
/* 143 */     if (isArray) {
/* 144 */       switch (type.getElementType().getSort()) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/* 149 */           return new BasicValue(type);
/*     */       } 
/*     */     
/*     */     }
/* 153 */     BasicValue v = super.newValue(type);
/* 154 */     if (BasicValue.REFERENCE_VALUE.equals(v)) {
/* 155 */       if (isArray) {
/* 156 */         v = newValue(type.getElementType());
/* 157 */         String desc = v.getType().getDescriptor();
/* 158 */         for (int i = 0; i < type.getDimensions(); i++) {
/* 159 */           desc = '[' + desc;
/*     */         }
/* 161 */         v = new BasicValue(Type.getType(desc));
/*     */       } else {
/* 163 */         v = new BasicValue(type);
/*     */       } 
/*     */     }
/* 166 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isArrayValue(BasicValue value) {
/* 171 */     Type t = value.getType();
/* 172 */     return (t != null && ("Lnull;"
/* 173 */       .equals(t.getDescriptor()) || t.getSort() == 9));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicValue getElementValue(BasicValue objectArrayValue) throws AnalyzerException {
/* 179 */     Type arrayType = objectArrayValue.getType();
/* 180 */     if (arrayType != null) {
/* 181 */       if (arrayType.getSort() == 9)
/* 182 */         return newValue(Type.getType(arrayType.getDescriptor()
/* 183 */               .substring(1))); 
/* 184 */       if ("Lnull;".equals(arrayType.getDescriptor())) {
/* 185 */         return objectArrayValue;
/*     */       }
/*     */     } 
/* 188 */     throw new Error("Internal error");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSubTypeOf(BasicValue value, BasicValue expected) {
/* 194 */     Type expectedType = expected.getType();
/* 195 */     Type type = value.getType();
/* 196 */     switch (expectedType.getSort()) {
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/* 201 */         return type.equals(expectedType);
/*     */       case 9:
/*     */       case 10:
/* 204 */         if ("Lnull;".equals(type.getDescriptor()))
/* 205 */           return true; 
/* 206 */         if (type.getSort() == 10 || type
/* 207 */           .getSort() == 9) {
/* 208 */           return isAssignableFrom(expectedType, type);
/*     */         }
/* 210 */         return false;
/*     */     } 
/*     */     
/* 213 */     throw new Error("Internal error");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue merge(BasicValue v, BasicValue w) {
/* 219 */     if (!v.equals(w)) {
/* 220 */       Type t = v.getType();
/* 221 */       Type u = w.getType();
/* 222 */       if (t != null && (t
/* 223 */         .getSort() == 10 || t.getSort() == 9) && 
/* 224 */         u != null && (u
/* 225 */         .getSort() == 10 || u.getSort() == 9)) {
/* 226 */         if ("Lnull;".equals(t.getDescriptor())) {
/* 227 */           return w;
/*     */         }
/* 229 */         if ("Lnull;".equals(u.getDescriptor())) {
/* 230 */           return v;
/*     */         }
/* 232 */         if (isAssignableFrom(t, u)) {
/* 233 */           return v;
/*     */         }
/* 235 */         if (isAssignableFrom(u, t)) {
/* 236 */           return w;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         while (true) {
/* 243 */           if (t == null || isInterface(t)) {
/* 244 */             return BasicValue.REFERENCE_VALUE;
/*     */           }
/* 246 */           t = getSuperClass(t);
/* 247 */           if (isAssignableFrom(t, u)) {
/* 248 */             return newValue(t);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 253 */       return BasicValue.UNINITIALIZED_VALUE;
/*     */     } 
/* 255 */     return v;
/*     */   }
/*     */   
/*     */   protected boolean isInterface(Type t) {
/* 259 */     if (this.currentClass != null && t.equals(this.currentClass)) {
/* 260 */       return this.isInterface;
/*     */     }
/* 262 */     return getClass(t).isInterface();
/*     */   }
/*     */   
/*     */   protected Type getSuperClass(Type t) {
/* 266 */     if (this.currentClass != null && t.equals(this.currentClass)) {
/* 267 */       return this.currentSuperClass;
/*     */     }
/* 269 */     Class<?> c = getClass(t).getSuperclass();
/* 270 */     return (c == null) ? null : Type.getType(c);
/*     */   }
/*     */   
/*     */   protected boolean isAssignableFrom(Type t, Type u) {
/* 274 */     if (t.equals(u)) {
/* 275 */       return true;
/*     */     }
/* 277 */     if (this.currentClass != null && t.equals(this.currentClass)) {
/* 278 */       if (getSuperClass(u) == null) {
/* 279 */         return false;
/*     */       }
/* 281 */       if (this.isInterface) {
/* 282 */         return (u.getSort() == 10 || u
/* 283 */           .getSort() == 9);
/*     */       }
/* 285 */       return isAssignableFrom(t, getSuperClass(u));
/*     */     } 
/*     */     
/* 288 */     if (this.currentClass != null && u.equals(this.currentClass)) {
/* 289 */       if (isAssignableFrom(t, this.currentSuperClass)) {
/* 290 */         return true;
/*     */       }
/* 292 */       if (this.currentClassInterfaces != null) {
/* 293 */         for (int i = 0; i < this.currentClassInterfaces.size(); i++) {
/* 294 */           Type v = this.currentClassInterfaces.get(i);
/* 295 */           if (isAssignableFrom(t, v)) {
/* 296 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/* 300 */       return false;
/*     */     } 
/* 302 */     Class<?> tc = getClass(t);
/* 303 */     if (tc.isInterface()) {
/* 304 */       tc = Object.class;
/*     */     }
/* 306 */     return tc.isAssignableFrom(getClass(u));
/*     */   }
/*     */   
/*     */   protected Class<?> getClass(Type t) {
/*     */     try {
/* 311 */       if (t.getSort() == 9) {
/* 312 */         return Class.forName(t.getDescriptor().replace('/', '.'), false, this.loader);
/*     */       }
/*     */       
/* 315 */       return Class.forName(t.getClassName(), false, this.loader);
/* 316 */     } catch (ClassNotFoundException e) {
/* 317 */       throw new RuntimeException(e.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\SimpleVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */