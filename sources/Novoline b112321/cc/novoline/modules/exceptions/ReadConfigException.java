package cc.novoline.modules.exceptions;

import java.io.IOException;

public final class ReadConfigException extends IOException {
   public ReadConfigException() {
   }

   public ReadConfigException(String var1) {
      super(var1);
   }

   public ReadConfigException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public ReadConfigException(Throwable var1) {
      super(var1);
   }
}
