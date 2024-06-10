/*     */ package org.spongepowered.tools.obfuscation.struct;
/*     */ 
/*     */ import javax.annotation.processing.Messager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InjectorRemap
/*     */ {
/*     */   private final boolean remap;
/*     */   private Message message;
/*     */   private int remappedCount;
/*     */   
/*     */   public InjectorRemap(boolean remap) {
/*  62 */     this.remap = remap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRemap() {
/*  69 */     return this.remap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyRemapped() {
/*  77 */     this.remappedCount++;
/*  78 */     clearMessage();
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
/*     */   public void addMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationHandle annotation) {
/*  91 */     this.message = new Message(kind, msg, element, annotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearMessage() {
/*  98 */     this.message = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispatchPendingMessages(Messager messager) {
/* 108 */     if (this.remappedCount == 0 && this.message != null)
/* 109 */       this.message.sendTo(messager); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\struct\InjectorRemap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */