/*     */ package org.spongepowered.tools.obfuscation.struct;
/*     */ 
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.AnnotationValue;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Message
/*     */ {
/*     */   private Diagnostic.Kind kind;
/*     */   private CharSequence msg;
/*     */   private final Element element;
/*     */   private final AnnotationMirror annotation;
/*     */   private final AnnotationValue value;
/*     */   
/*     */   public Message(Diagnostic.Kind kind, CharSequence msg) {
/*  48 */     this(kind, msg, (Element)null, (AnnotationMirror)null, (AnnotationValue)null);
/*     */   }
/*     */   
/*     */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element) {
/*  52 */     this(kind, msg, element, (AnnotationMirror)null, (AnnotationValue)null);
/*     */   }
/*     */   
/*     */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationHandle annotation) {
/*  56 */     this(kind, msg, element, annotation.asMirror(), (AnnotationValue)null);
/*     */   }
/*     */   
/*     */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation) {
/*  60 */     this(kind, msg, element, annotation, (AnnotationValue)null);
/*     */   }
/*     */   
/*     */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationHandle annotation, AnnotationValue value) {
/*  64 */     this(kind, msg, element, annotation.asMirror(), value);
/*     */   }
/*     */   
/*     */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation, AnnotationValue value) {
/*  68 */     this.kind = kind;
/*  69 */     this.msg = msg;
/*  70 */     this.element = element;
/*  71 */     this.annotation = annotation;
/*  72 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message sendTo(Messager messager) {
/*  82 */     if (this.value != null) {
/*  83 */       messager.printMessage(this.kind, this.msg, this.element, this.annotation, this.value);
/*  84 */     } else if (this.annotation != null) {
/*  85 */       messager.printMessage(this.kind, this.msg, this.element, this.annotation);
/*  86 */     } else if (this.element != null) {
/*  87 */       messager.printMessage(this.kind, this.msg, this.element);
/*     */     } else {
/*  89 */       messager.printMessage(this.kind, this.msg);
/*     */     } 
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Diagnostic.Kind getKind() {
/*  98 */     return this.kind;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message setKind(Diagnostic.Kind kind) {
/* 108 */     this.kind = kind;
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence getMsg() {
/* 118 */     return this.msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message setMsg(CharSequence msg) {
/* 128 */     this.msg = msg;
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getElement() {
/* 136 */     return this.element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationMirror getAnnotation() {
/* 143 */     return this.annotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationValue getValue() {
/* 150 */     return this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\struct\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */