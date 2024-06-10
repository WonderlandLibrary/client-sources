/*    */ package nightmare.module.player;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class AutoTool
/*    */   extends Module
/*    */ {
/*    */   public AutoTool() {
/* 16 */     super("AutoTool", 0, Category.PLAYER);
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 21 */     if (mc.field_71462_r == null && Mouse.isButtonDown(0) && mc.field_71476_x != null) {
/* 22 */       BlockPos blockPos = mc.field_71476_x.func_178782_a();
/* 23 */       if (blockPos != null) {
/* 24 */         Block block = mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
/* 25 */         float strength = 1.0F;
/* 26 */         int bestToolSlot = -1;
/*    */         
/* 28 */         for (int i = 0; i < 9; i++) {
/* 29 */           ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 30 */           if (itemStack != null && itemStack.func_150997_a(block) > strength) {
/* 31 */             strength = itemStack.func_150997_a(block);
/* 32 */             bestToolSlot = i;
/*    */           } 
/*    */         } 
/*    */         
/* 36 */         if (bestToolSlot != -1)
/* 37 */           mc.field_71439_g.field_71071_by.field_70461_c = bestToolSlot; 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\player\AutoTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */