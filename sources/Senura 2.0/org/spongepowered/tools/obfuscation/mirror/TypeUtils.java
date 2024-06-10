/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import javax.annotation.processing.ProcessingEnvironment;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.Modifier;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.ArrayType;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.lang.model.type.TypeVariable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TypeUtils
/*     */ {
/*     */   private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
/*     */   private static final String OBJECT_SIG = "java.lang.Object";
/*     */   private static final String OBJECT_REF = "java/lang/Object";
/*     */   
/*     */   public static PackageElement getPackage(TypeMirror type) {
/*  67 */     if (!(type instanceof DeclaredType)) {
/*  68 */       return null;
/*     */     }
/*  70 */     return getPackage((TypeElement)((DeclaredType)type).asElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PackageElement getPackage(TypeElement type) {
/*  79 */     Element parent = type.getEnclosingElement();
/*  80 */     while (parent != null && !(parent instanceof PackageElement)) {
/*  81 */       parent = parent.getEnclosingElement();
/*     */     }
/*  83 */     return (PackageElement)parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getElementType(Element element) {
/*  94 */     if (element instanceof TypeElement)
/*  95 */       return "TypeElement"; 
/*  96 */     if (element instanceof ExecutableElement)
/*  97 */       return "ExecutableElement"; 
/*  98 */     if (element instanceof VariableElement)
/*  99 */       return "VariableElement"; 
/* 100 */     if (element instanceof PackageElement)
/* 101 */       return "PackageElement"; 
/* 102 */     if (element instanceof javax.lang.model.element.TypeParameterElement) {
/* 103 */       return "TypeParameterElement";
/*     */     }
/*     */     
/* 106 */     return element.getClass().getSimpleName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String stripGenerics(String type) {
/* 116 */     StringBuilder sb = new StringBuilder();
/* 117 */     for (int pos = 0, depth = 0; pos < type.length(); pos++) {
/* 118 */       char c = type.charAt(pos);
/* 119 */       if (c == '<') {
/* 120 */         depth++;
/*     */       }
/* 122 */       if (depth == 0) {
/* 123 */         sb.append(c);
/* 124 */       } else if (c == '>') {
/* 125 */         depth--;
/*     */       } 
/*     */     } 
/* 128 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(VariableElement field) {
/* 138 */     return (field != null) ? field.getSimpleName().toString() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(ExecutableElement method) {
/* 148 */     return (method != null) ? method.getSimpleName().toString() : null;
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
/*     */   public static String getJavaSignature(Element element) {
/* 161 */     if (element instanceof ExecutableElement) {
/* 162 */       ExecutableElement method = (ExecutableElement)element;
/* 163 */       StringBuilder desc = (new StringBuilder()).append("(");
/* 164 */       boolean extra = false;
/* 165 */       for (VariableElement arg : method.getParameters()) {
/* 166 */         if (extra) {
/* 167 */           desc.append(',');
/*     */         }
/* 169 */         desc.append(getTypeName(arg.asType()));
/* 170 */         extra = true;
/*     */       } 
/* 172 */       desc.append(')').append(getTypeName(method.getReturnType()));
/* 173 */       return desc.toString();
/*     */     } 
/* 175 */     return getTypeName(element.asType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJavaSignature(String descriptor) {
/* 185 */     return (new SignaturePrinter("", descriptor)).setFullyQualified(true).toDescriptor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTypeName(TypeMirror type) {
/* 195 */     switch (type.getKind()) { case PUBLIC:
/* 196 */         return getTypeName(((ArrayType)type).getComponentType()) + "[]";
/* 197 */       case PROTECTED: return getTypeName((DeclaredType)type);
/* 198 */       case PRIVATE: return getTypeName(getUpperBound(type));
/* 199 */       case null: return "java.lang.Object"; }
/* 200 */      return type.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTypeName(DeclaredType type) {
/* 211 */     if (type == null) {
/* 212 */       return "java.lang.Object";
/*     */     }
/* 214 */     return getInternalName((TypeElement)type.asElement()).replace('/', '.');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(Element element) {
/* 224 */     if (element instanceof ExecutableElement)
/* 225 */       return getDescriptor((ExecutableElement)element); 
/* 226 */     if (element instanceof VariableElement) {
/* 227 */       return getInternalName((VariableElement)element);
/*     */     }
/*     */     
/* 230 */     return getInternalName(element.asType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(ExecutableElement method) {
/* 240 */     if (method == null) {
/* 241 */       return null;
/*     */     }
/*     */     
/* 244 */     StringBuilder signature = new StringBuilder();
/*     */     
/* 246 */     for (VariableElement var : method.getParameters()) {
/* 247 */       signature.append(getInternalName(var));
/*     */     }
/*     */     
/* 250 */     String returnType = getInternalName(method.getReturnType());
/* 251 */     return String.format("(%s)%s", new Object[] { signature, returnType });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInternalName(VariableElement field) {
/* 261 */     if (field == null) {
/* 262 */       return null;
/*     */     }
/* 264 */     return getInternalName(field.asType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInternalName(TypeMirror type) {
/* 274 */     switch (type.getKind()) { case PUBLIC:
/* 275 */         return "[" + getInternalName(((ArrayType)type).getComponentType());
/* 276 */       case PROTECTED: return "L" + getInternalName((DeclaredType)type) + ";";
/* 277 */       case PRIVATE: return "L" + getInternalName(getUpperBound(type)) + ";";
/* 278 */       case null: return "Z";
/* 279 */       case null: return "B";
/* 280 */       case null: return "C";
/* 281 */       case null: return "D";
/* 282 */       case null: return "F";
/* 283 */       case null: return "I";
/* 284 */       case null: return "J";
/* 285 */       case null: return "S";
/* 286 */       case null: return "V";
/*     */       case null:
/* 288 */         return "Ljava/lang/Object;"; }
/*     */ 
/*     */ 
/*     */     
/* 292 */     throw new IllegalArgumentException("Unable to parse type symbol " + type + " with " + type.getKind() + " to equivalent bytecode type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInternalName(DeclaredType type) {
/* 302 */     if (type == null) {
/* 303 */       return "java/lang/Object";
/*     */     }
/* 305 */     return getInternalName((TypeElement)type.asElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInternalName(TypeElement element) {
/* 315 */     if (element == null) {
/* 316 */       return null;
/*     */     }
/* 318 */     StringBuilder reference = new StringBuilder();
/* 319 */     reference.append(element.getSimpleName());
/* 320 */     Element parent = element.getEnclosingElement();
/* 321 */     while (parent != null) {
/* 322 */       if (parent instanceof TypeElement) {
/* 323 */         reference.insert(0, "$").insert(0, parent.getSimpleName());
/* 324 */       } else if (parent instanceof PackageElement) {
/* 325 */         reference.insert(0, "/").insert(0, ((PackageElement)parent).getQualifiedName().toString().replace('.', '/'));
/*     */       } 
/* 327 */       parent = parent.getEnclosingElement();
/*     */     } 
/* 329 */     return reference.toString();
/*     */   }
/*     */   
/*     */   private static DeclaredType getUpperBound(TypeMirror type) {
/*     */     try {
/* 334 */       return getUpperBound0(type, 5);
/* 335 */     } catch (IllegalStateException ex) {
/* 336 */       throw new IllegalArgumentException("Type symbol \"" + type + "\" is too complex", ex);
/* 337 */     } catch (IllegalArgumentException ex) {
/* 338 */       throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + type, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static DeclaredType getUpperBound0(TypeMirror type, int depth) {
/* 343 */     if (depth == 0) {
/* 344 */       throw new IllegalStateException("Generic symbol \"" + type + "\" is too complex, exceeded " + '\005' + " iterations attempting to determine upper bound");
/*     */     }
/*     */     
/* 347 */     if (type instanceof DeclaredType) {
/* 348 */       return (DeclaredType)type;
/*     */     }
/* 350 */     if (type instanceof TypeVariable) {
/*     */       try {
/* 352 */         TypeMirror upper = ((TypeVariable)type).getUpperBound();
/* 353 */         return getUpperBound0(upper, --depth);
/* 354 */       } catch (IllegalStateException ex) {
/* 355 */         throw ex;
/* 356 */       } catch (IllegalArgumentException ex) {
/* 357 */         throw ex;
/* 358 */       } catch (Exception ex) {
/* 359 */         throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + type);
/*     */       } 
/*     */     }
/* 362 */     return null;
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
/*     */   public static boolean isAssignable(ProcessingEnvironment processingEnv, TypeMirror targetType, TypeMirror superClass) {
/* 374 */     boolean assignable = processingEnv.getTypeUtils().isAssignable(targetType, superClass);
/* 375 */     if (!assignable && targetType instanceof DeclaredType && superClass instanceof DeclaredType) {
/* 376 */       TypeMirror rawTargetType = toRawType(processingEnv, (DeclaredType)targetType);
/* 377 */       TypeMirror rawSuperType = toRawType(processingEnv, (DeclaredType)superClass);
/* 378 */       return processingEnv.getTypeUtils().isAssignable(rawTargetType, rawSuperType);
/*     */     } 
/*     */     
/* 381 */     return assignable;
/*     */   }
/*     */   
/*     */   private static TypeMirror toRawType(ProcessingEnvironment processingEnv, DeclaredType targetType) {
/* 385 */     return processingEnv.getElementUtils().getTypeElement(((TypeElement)targetType.asElement()).getQualifiedName()).asType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Visibility getVisibility(Element element) {
/* 395 */     if (element == null) {
/* 396 */       return null;
/*     */     }
/*     */     
/* 399 */     for (Modifier modifier : element.getModifiers()) {
/* 400 */       switch (modifier) { case PUBLIC:
/* 401 */           return Visibility.PUBLIC;
/* 402 */         case PROTECTED: return Visibility.PROTECTED;
/* 403 */         case PRIVATE: return Visibility.PRIVATE; }
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 408 */     return Visibility.PACKAGE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mirror\TypeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */