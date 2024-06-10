/*    */ package nightmare.module.movement;
/*    */ 
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ 
/*    */ public class Sprint
/*    */   extends Module {
/*    */   public Sprint() {
/* 12 */     super("Sprint", 0, Category.MOVEMENT);
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 17 */     KeyBinding.func_74510_a(mc.field_71474_y.field_151444_V.func_151463_i(), true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 22 */     super.onDisable();
/* 23 */     KeyBinding.func_74510_a(mc.field_71474_y.field_151444_V.func_151463_i(), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\movement\Sprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */