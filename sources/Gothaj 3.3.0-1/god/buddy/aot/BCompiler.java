package god.buddy.aot;

public @interface BCompiler {
   BCompiler.AOT aot();

   public static enum AOT {
      AGGRESSIVE,
      NORMAL;
   }
}
