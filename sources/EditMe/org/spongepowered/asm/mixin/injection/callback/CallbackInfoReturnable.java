package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;

public class CallbackInfoReturnable extends CallbackInfo {
   private Object returnValue;

   public CallbackInfoReturnable(String var1, boolean var2) {
      super(var1, var2);
      this.returnValue = null;
   }

   public CallbackInfoReturnable(String var1, boolean var2, Object var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, byte var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, char var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, double var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, float var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, int var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, long var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, short var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public CallbackInfoReturnable(String var1, boolean var2, boolean var3) {
      super(var1, var2);
      this.returnValue = var3;
   }

   public void setReturnValue(Object var1) throws CancellationException {
      super.cancel();
      this.returnValue = var1;
   }

   public Object getReturnValue() {
      return this.returnValue;
   }

   public byte getReturnValueB() {
      return this.returnValue == null ? 0 : (Byte)this.returnValue;
   }

   public char getReturnValueC() {
      return this.returnValue == null ? '\u0000' : (Character)this.returnValue;
   }

   public double getReturnValueD() {
      return this.returnValue == null ? 0.0D : (Double)this.returnValue;
   }

   public float getReturnValueF() {
      return this.returnValue == null ? 0.0F : (Float)this.returnValue;
   }

   public int getReturnValueI() {
      return this.returnValue == null ? 0 : (Integer)this.returnValue;
   }

   public long getReturnValueJ() {
      return this.returnValue == null ? 0L : (Long)this.returnValue;
   }

   public short getReturnValueS() {
      return this.returnValue == null ? 0 : (Short)this.returnValue;
   }

   public boolean getReturnValueZ() {
      return this.returnValue == null ? false : (Boolean)this.returnValue;
   }

   static String getReturnAccessor(Type var0) {
      return var0.getSort() != 10 && var0.getSort() != 9 ? String.format("getReturnValue%s", var0.getDescriptor()) : "getReturnValue";
   }

   static String getReturnDescriptor(Type var0) {
      return var0.getSort() != 10 && var0.getSort() != 9 ? String.format("()%s", var0.getDescriptor()) : String.format("()%s", "Ljava/lang/Object;");
   }
}
