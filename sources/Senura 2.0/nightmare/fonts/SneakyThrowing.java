/*    */ package nightmare.fonts;
/*    */ 
/*    */ public final class SneakyThrowing
/*    */ {
/*    */   public static RuntimeException sneakyThrow(Throwable throwable) {
/*  6 */     return sneakyThrow0(throwable);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T extends Throwable> T sneakyThrow0(Throwable throwable) throws T {
/* 11 */     throw (T)throwable;
/*    */   }
/*    */   
/*    */   private SneakyThrowing() {
/* 15 */     throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\fonts\SneakyThrowing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */