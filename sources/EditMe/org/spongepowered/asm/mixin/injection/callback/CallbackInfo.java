package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;

public class CallbackInfo implements Cancellable {
   private final String name;
   private final boolean cancellable;
   private boolean cancelled;

   public CallbackInfo(String var1, boolean var2) {
      this.name = var1;
      this.cancellable = var2;
   }

   public String getId() {
      return this.name;
   }

   public String toString() {
      return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", this.getClass().getSimpleName(), this.name, this.cancellable);
   }

   public final boolean isCancellable() {
      return this.cancellable;
   }

   public final boolean isCancelled() {
      return this.cancelled;
   }

   public void cancel() throws CancellationException {
      if (!this.cancellable) {
         throw new CancellationException(String.format("The call %s is not cancellable.", this.name));
      } else {
         this.cancelled = true;
      }
   }

   static String getCallInfoClassName() {
      return CallbackInfo.class.getName();
   }

   public static String getCallInfoClassName(Type var0) {
      return (var0.equals(Type.VOID_TYPE) ? CallbackInfo.class.getName() : CallbackInfoReturnable.class.getName()).replace('.', '/');
   }

   static String getConstructorDescriptor(Type var0) {
      if (var0.equals(Type.VOID_TYPE)) {
         return getConstructorDescriptor();
      } else {
         return var0.getSort() != 10 && var0.getSort() != 9 ? String.format("(%sZ%s)V", "Ljava/lang/String;", var0.getDescriptor()) : String.format("(%sZ%s)V", "Ljava/lang/String;", "Ljava/lang/Object;");
      }
   }

   static String getConstructorDescriptor() {
      return String.format("(%sZ)V", "Ljava/lang/String;");
   }

   static String getIsCancelledMethodName() {
      return "isCancelled";
   }

   static String getIsCancelledMethodSig() {
      return "()Z";
   }
}
