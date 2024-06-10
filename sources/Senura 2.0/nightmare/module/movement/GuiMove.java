/*    */ package nightmare.module.movement;
/*    */ 
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ public class GuiMove
/*    */   extends Module
/*    */ {
/*    */   public GuiMove() {
/* 17 */     super("GuiMove", 0, Category.MOVEMENT);
/*    */     
/* 19 */     Nightmare.instance.settingsManager.rSetting(new Setting("Sneak", this, false));
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 25 */     if (mc.field_71462_r != null && !(mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat)) {
/* 26 */       KeyBinding.func_74510_a(mc.field_71474_y.field_74351_w.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74351_w.func_151463_i()));
/* 27 */       KeyBinding.func_74510_a(mc.field_71474_y.field_74368_y.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74368_y.func_151463_i()));
/* 28 */       KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74366_z.func_151463_i()));
/* 29 */       KeyBinding.func_74510_a(mc.field_71474_y.field_74370_x.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74370_x.func_151463_i()));
/* 30 */       KeyBinding.func_74510_a(mc.field_71474_y.field_74314_A.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74314_A.func_151463_i()));
/*    */       
/* 32 */       if (Nightmare.instance.settingsManager.getSettingByName(this, "Sneak").getValBoolean())
/* 33 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74311_E.func_151463_i())); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\movement\GuiMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */