package cc.novoline.modules.exceptions;

public final class UnregisteredModuleException extends RuntimeException {
   public UnregisteredModuleException() {
   }

   public UnregisteredModuleException(String var1) {
      super(var1);
   }

   public UnregisteredModuleException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public UnregisteredModuleException(Throwable var1) {
      super(var1);
   }
}
