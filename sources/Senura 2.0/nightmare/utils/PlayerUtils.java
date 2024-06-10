/*    */ package nightmare.utils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class PlayerUtils {
/*  7 */   private static Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   public static void shiftClick(int slot) {
/* 10 */     mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, 1, (EntityPlayer)mc.field_71439_g);
/*    */   }
/*    */   
/*    */   public static void drop(int slot) {
/* 14 */     mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, 1, 4, (EntityPlayer)mc.field_71439_g);
/*    */   }
/*    */   
/*    */   public static void swap(int slot, int hSlot) {
/* 18 */     mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, hSlot, 2, (EntityPlayer)mc.field_71439_g);
/*    */   }
/*    */   
/*    */   public static boolean isMoving() {
/* 22 */     return (mc.field_71439_g.field_70701_bs != 0.0F || mc.field_71439_g.field_70702_br != 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\PlayerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */