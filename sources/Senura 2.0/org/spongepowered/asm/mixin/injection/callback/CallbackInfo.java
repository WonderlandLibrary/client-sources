/*     */ package org.spongepowered.asm.mixin.injection.callback;
/*     */ 
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
/*     */ public class CallbackInfo
/*     */   implements Cancellable
/*     */ {
/*     */   private final String name;
/*     */   private final boolean cancellable;
/*     */   private boolean cancelled;
/*     */   
/*     */   public CallbackInfo(String name, boolean cancellable) {
/*  62 */     this.name = name;
/*  63 */     this.cancellable = cancellable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  74 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  82 */     return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", new Object[] { getClass().getSimpleName(), this.name, Boolean.valueOf(this.cancellable) });
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isCancellable() {
/*  87 */     return this.cancellable;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isCancelled() {
/*  92 */     return this.cancelled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() throws CancellationException {
/* 100 */     if (!this.cancellable) {
/* 101 */       throw new CancellationException(String.format("The call %s is not cancellable.", new Object[] { this.name }));
/*     */     }
/*     */     
/* 104 */     this.cancelled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String getCallInfoClassName() {
/* 110 */     return CallbackInfo.class.getName();
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
/*     */   public static String getCallInfoClassName(Type returnType) {
/* 122 */     return (returnType.equals(Type.VOID_TYPE) ? CallbackInfo.class.getName() : CallbackInfoReturnable.class.getName()).replace('.', '/');
/*     */   }
/*     */   
/*     */   static String getConstructorDescriptor(Type returnType) {
/* 126 */     if (returnType.equals(Type.VOID_TYPE)) {
/* 127 */       return getConstructorDescriptor();
/*     */     }
/*     */     
/* 130 */     if (returnType.getSort() == 10 || returnType.getSort() == 9) {
/* 131 */       return String.format("(%sZ%s)V", new Object[] { "Ljava/lang/String;", "Ljava/lang/Object;" });
/*     */     }
/*     */     
/* 134 */     return String.format("(%sZ%s)V", new Object[] { "Ljava/lang/String;", returnType.getDescriptor() });
/*     */   }
/*     */   
/*     */   static String getConstructorDescriptor() {
/* 138 */     return String.format("(%sZ)V", new Object[] { "Ljava/lang/String;" });
/*     */   }
/*     */   
/*     */   static String getIsCancelledMethodName() {
/* 142 */     return "isCancelled";
/*     */   }
/*     */   
/*     */   static String getIsCancelledMethodSig() {
/* 146 */     return "()Z";
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\callback\CallbackInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */