package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.exception.CancelException$1;

public class CancelException extends Exception {
   public static final CancelException CACHED = new CancelException$1("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these");

   public CancelException() {
   }

   public CancelException(String var1) {
      super(var1);
   }

   public CancelException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public CancelException(Throwable var1) {
      super(var1);
   }

   public CancelException(String var1, Throwable var2, boolean var3, boolean var4) {
      super(var1, var2, var3, var4);
   }

   public static CancelException generate() {
      return Via.b().c()?new CancelException():CACHED;
   }
}
