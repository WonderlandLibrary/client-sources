/*    */ package nightmare.utils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class ScreenUtils
/*    */ {
/*    */   public static int getWidth() {
/*  8 */     return (Minecraft.func_71410_x()).field_71443_c / getScaleFactor();
/*    */   }
/*    */   
/*    */   public static int getHeight() {
/* 12 */     return (Minecraft.func_71410_x()).field_71440_d / getScaleFactor();
/*    */   }
/*    */   
/*    */   public static int getScaleFactor() {
/* 16 */     int scaleFactor = 1;
/* 17 */     boolean isUnicode = Minecraft.func_71410_x().func_152349_b();
/* 18 */     int guiScale = (Minecraft.func_71410_x()).field_71474_y.field_74335_Z;
/* 19 */     if (guiScale == 0) {
/* 20 */       guiScale = 1000;
/*    */     }
/*    */     
/* 23 */     while (scaleFactor < guiScale && (Minecraft.func_71410_x()).field_71443_c / (scaleFactor + 1) >= 320 && (Minecraft.func_71410_x()).field_71440_d / (scaleFactor + 1) >= 240) {
/* 24 */       scaleFactor++;
/*    */     }
/* 26 */     if (isUnicode && scaleFactor % 2 != 0 && scaleFactor != 1) {
/* 27 */       scaleFactor--;
/*    */     }
/* 29 */     return scaleFactor;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\ScreenUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */