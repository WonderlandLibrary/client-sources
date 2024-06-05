package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.exception.CancelDecoderException$1;
import io.netty.handler.codec.DecoderException;
import net.aAu;

public class CancelDecoderException extends DecoderException implements aAu {
   public static final CancelDecoderException CACHED = new CancelDecoderException$1("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these");

   public CancelDecoderException() {
   }

   public CancelDecoderException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public CancelDecoderException(String var1) {
      super(var1);
   }

   public CancelDecoderException(Throwable var1) {
      super(var1);
   }

   public static CancelDecoderException generate(Throwable var0) {
      return Via.b().c()?new CancelDecoderException(var0):CACHED;
   }

   private static CancelDecoderException a(CancelDecoderException var0) {
      return var0;
   }
}
