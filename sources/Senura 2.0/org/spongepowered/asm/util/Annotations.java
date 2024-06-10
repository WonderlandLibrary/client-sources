/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Annotations
/*     */ {
/*     */   public static void setVisible(FieldNode field, Class<? extends Annotation> annotationClass, Object... value) {
/*  61 */     AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
/*  62 */     field.visibleAnnotations = add(field.visibleAnnotations, node);
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
/*     */   public static void setInvisible(FieldNode field, Class<? extends Annotation> annotationClass, Object... value) {
/*  74 */     AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
/*  75 */     field.invisibleAnnotations = add(field.invisibleAnnotations, node);
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
/*     */   public static void setVisible(MethodNode method, Class<? extends Annotation> annotationClass, Object... value) {
/*  87 */     AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
/*  88 */     method.visibleAnnotations = add(method.visibleAnnotations, node);
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
/*     */   public static void setInvisible(MethodNode method, Class<? extends Annotation> annotationClass, Object... value) {
/* 100 */     AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
/* 101 */     method.invisibleAnnotations = add(method.invisibleAnnotations, node);
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
/*     */   private static AnnotationNode createNode(String annotationType, Object... value) {
/* 113 */     AnnotationNode node = new AnnotationNode(annotationType);
/* 114 */     for (int pos = 0; pos < value.length - 1; pos += 2) {
/* 115 */       if (!(value[pos] instanceof String)) {
/* 116 */         throw new IllegalArgumentException("Annotation keys must be strings, found " + value[pos].getClass().getSimpleName() + " with " + value[pos]
/* 117 */             .toString() + " at index " + pos + " creating " + annotationType);
/*     */       }
/* 119 */       node.visit((String)value[pos], value[pos + 1]);
/*     */     } 
/* 121 */     return node;
/*     */   }
/*     */   
/*     */   private static List<AnnotationNode> add(List<AnnotationNode> annotations, AnnotationNode node) {
/* 125 */     if (annotations == null) {
/* 126 */       annotations = new ArrayList<AnnotationNode>(1);
/*     */     } else {
/* 128 */       annotations.remove(get(annotations, node.desc));
/*     */     } 
/* 130 */     annotations.add(node);
/* 131 */     return annotations;
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
/*     */   public static AnnotationNode getVisible(FieldNode field, Class<? extends Annotation> annotationClass) {
/* 143 */     return get(field.visibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getInvisible(FieldNode field, Class<? extends Annotation> annotationClass) {
/* 155 */     return get(field.invisibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getVisible(MethodNode method, Class<? extends Annotation> annotationClass) {
/* 167 */     return get(method.visibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getInvisible(MethodNode method, Class<? extends Annotation> annotationClass) {
/* 179 */     return get(method.invisibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getSingleVisible(MethodNode method, Class<? extends Annotation>... annotationClasses) {
/* 191 */     return getSingle(method.visibleAnnotations, annotationClasses);
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
/*     */   public static AnnotationNode getSingleInvisible(MethodNode method, Class<? extends Annotation>... annotationClasses) {
/* 203 */     return getSingle(method.invisibleAnnotations, annotationClasses);
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
/*     */   public static AnnotationNode getVisible(ClassNode classNode, Class<? extends Annotation> annotationClass) {
/* 215 */     return get(classNode.visibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getInvisible(ClassNode classNode, Class<? extends Annotation> annotationClass) {
/* 227 */     return get(classNode.invisibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getVisibleParameter(MethodNode method, Class<? extends Annotation> annotationClass, int paramIndex) {
/* 240 */     return getParameter((List<AnnotationNode>[])method.visibleParameterAnnotations, Type.getDescriptor(annotationClass), paramIndex);
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
/*     */   public static AnnotationNode getInvisibleParameter(MethodNode method, Class<? extends Annotation> annotationClass, int paramIndex) {
/* 253 */     return getParameter((List<AnnotationNode>[])method.invisibleParameterAnnotations, Type.getDescriptor(annotationClass), paramIndex);
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
/*     */   public static AnnotationNode getParameter(List<AnnotationNode>[] parameterAnnotations, String annotationType, int paramIndex) {
/* 266 */     if (parameterAnnotations == null || paramIndex < 0 || paramIndex >= parameterAnnotations.length) {
/* 267 */       return null;
/*     */     }
/*     */     
/* 270 */     return get(parameterAnnotations[paramIndex], annotationType);
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
/*     */   public static AnnotationNode get(List<AnnotationNode> annotations, String annotationType) {
/* 283 */     if (annotations == null) {
/* 284 */       return null;
/*     */     }
/*     */     
/* 287 */     for (AnnotationNode annotation : annotations) {
/* 288 */       if (annotationType.equals(annotation.desc)) {
/* 289 */         return annotation;
/*     */       }
/*     */     } 
/*     */     
/* 293 */     return null;
/*     */   }
/*     */   
/*     */   private static AnnotationNode getSingle(List<AnnotationNode> annotations, Class<? extends Annotation>[] annotationClasses) {
/* 297 */     List<AnnotationNode> nodes = new ArrayList<AnnotationNode>();
/* 298 */     for (Class<? extends Annotation> annotationClass : annotationClasses) {
/* 299 */       AnnotationNode annotation = get(annotations, Type.getDescriptor(annotationClass));
/* 300 */       if (annotation != null) {
/* 301 */         nodes.add(annotation);
/*     */       }
/*     */     } 
/*     */     
/* 305 */     int foundNodes = nodes.size();
/* 306 */     if (foundNodes > 1) {
/* 307 */       throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(nodes, new Function<AnnotationNode, String>() {
/*     */               public String apply(AnnotationNode input) {
/* 309 */                 return input.desc;
/*     */               }
/*     */             }));
/*     */     }
/*     */     
/* 314 */     return (foundNodes == 0) ? null : nodes.get(0);
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
/*     */   public static <T> T getValue(AnnotationNode annotation) {
/* 326 */     return getValue(annotation, "value");
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
/*     */   public static <T> T getValue(AnnotationNode annotation, String key, T defaultValue) {
/* 342 */     T returnValue = getValue(annotation, key);
/* 343 */     return (returnValue != null) ? returnValue : defaultValue;
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
/*     */   public static <T> T getValue(AnnotationNode annotation, String key, Class<?> annotationClass) {
/* 360 */     Preconditions.checkNotNull(annotationClass, "annotationClass cannot be null");
/* 361 */     T value = getValue(annotation, key);
/* 362 */     if (value == null) {
/*     */       try {
/* 364 */         value = (T)annotationClass.getDeclaredMethod(key, new Class[0]).getDefaultValue();
/* 365 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*     */     }
/*     */ 
/*     */     
/* 369 */     return value;
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
/*     */   public static <T> T getValue(AnnotationNode annotation, String key) {
/* 384 */     boolean getNextValue = false;
/*     */     
/* 386 */     if (annotation == null || annotation.values == null) {
/* 387 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 391 */     for (Object value : annotation.values) {
/* 392 */       if (getNextValue) {
/* 393 */         return (T)value;
/*     */       }
/* 395 */       if (value.equals(key)) {
/* 396 */         getNextValue = true;
/*     */       }
/*     */     } 
/*     */     
/* 400 */     return null;
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
/*     */   public static <T extends Enum<T>> T getValue(AnnotationNode annotation, String key, Class<T> enumClass, T defaultValue) {
/* 415 */     String[] value = getValue(annotation, key);
/* 416 */     if (value == null) {
/* 417 */       return defaultValue;
/*     */     }
/* 419 */     return toEnumValue(enumClass, value);
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
/*     */   public static <T> List<T> getValue(AnnotationNode annotation, String key, boolean notNull) {
/* 433 */     Object value = getValue(annotation, key);
/* 434 */     if (value instanceof List)
/* 435 */       return (List<T>)value; 
/* 436 */     if (value != null) {
/* 437 */       List<T> list = new ArrayList<T>();
/* 438 */       list.add((T)value);
/* 439 */       return list;
/*     */     } 
/* 441 */     return Collections.emptyList();
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
/*     */   public static <T extends Enum<T>> List<T> getValue(AnnotationNode annotation, String key, boolean notNull, Class<T> enumClass) {
/* 456 */     Object value = getValue(annotation, key);
/* 457 */     if (value instanceof List) {
/* 458 */       for (ListIterator<Object> iter = ((List<Object>)value).listIterator(); iter.hasNext();) {
/* 459 */         iter.set(toEnumValue(enumClass, (String[])iter.next()));
/*     */       }
/* 461 */       return (List<T>)value;
/* 462 */     }  if (value instanceof String[]) {
/* 463 */       List<T> list = new ArrayList<T>();
/* 464 */       list.add(toEnumValue(enumClass, (String[])value));
/* 465 */       return list;
/*     */     } 
/* 467 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   private static <T extends Enum<T>> T toEnumValue(Class<T> enumClass, String[] value) {
/* 471 */     if (!enumClass.getName().equals(Type.getType(value[0]).getClassName())) {
/* 472 */       throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
/*     */     }
/* 474 */     return Enum.valueOf(enumClass, value[1]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\Annotations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */