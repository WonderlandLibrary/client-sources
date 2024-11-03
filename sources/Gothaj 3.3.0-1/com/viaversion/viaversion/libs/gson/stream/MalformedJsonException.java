package com.viaversion.viaversion.libs.gson.stream;

import java.io.IOException;

public final class MalformedJsonException extends IOException {
   private static final long serialVersionUID = 1L;

   public MalformedJsonException(String msg) {
      super(msg);
   }

   public MalformedJsonException(String msg, Throwable throwable) {
      super(msg, throwable);
   }

   public MalformedJsonException(Throwable throwable) {
      super(throwable);
   }
}
