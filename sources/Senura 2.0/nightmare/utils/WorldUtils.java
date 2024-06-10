/*    */ package nightmare.utils;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ public class WorldUtils
/*    */ {
/* 12 */   private static Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   public static List<EntityPlayer> getLivingPlayers() {
/* 15 */     return Arrays.asList((EntityPlayer[])mc.field_71441_e.field_72996_f
/* 16 */         .stream()
/* 17 */         .filter(entity -> entity instanceof EntityPlayer)
/* 18 */         .filter(entity -> (entity != mc.field_71439_g))
/* 19 */         .map(entity -> (EntityPlayer)entity)
/* 20 */         .toArray(x$0 -> new EntityPlayer[x$0]));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\WorldUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */