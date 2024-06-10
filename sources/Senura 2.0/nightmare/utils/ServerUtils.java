/*    */ package nightmare.utils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class ServerUtils
/*    */ {
/*  7 */   private static Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   public static boolean isOnServer() {
/* 10 */     if (mc.func_147104_D() != null) {
/* 11 */       return true;
/*    */     }
/* 13 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean isOnHypixel() {
/* 17 */     if (isOnServer() && (mc.func_147104_D()).field_78845_b.contains("hypixel")) {
/* 18 */       return true;
/*    */     }
/* 20 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean isOnSyuu() {
/* 24 */     if (isOnServer() && (mc.func_147104_D()).field_78845_b.contains("syuu")) {
/* 25 */       return true;
/*    */     }
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\ServerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */