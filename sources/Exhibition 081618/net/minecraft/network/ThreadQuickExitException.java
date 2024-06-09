package net.minecraft.network;

public final class ThreadQuickExitException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   public static final ThreadQuickExitException field_179886_a = new ThreadQuickExitException();

   private ThreadQuickExitException() {
      this.setStackTrace(new StackTraceElement[0]);
   }

   public synchronized Throwable fillInStackTrace() {
      this.setStackTrace(new StackTraceElement[0]);
      return this;
   }
}
