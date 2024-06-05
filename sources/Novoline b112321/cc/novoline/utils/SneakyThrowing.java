package cc.novoline.utils;

public final class SneakyThrowing {
   public static RuntimeException sneakyThrow(Throwable var0) {
      return (RuntimeException)sneakyThrow0(var0);
   }

   private static Throwable sneakyThrow0(Throwable var0) throws Throwable {
      throw var0;
   }

   private SneakyThrowing() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
