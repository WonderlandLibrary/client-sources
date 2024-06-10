/*    */ package nightmare.module.movement;
/*    */ 
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FastBridge
/*    */   extends Module
/*    */ {
/*    */   public FastBridge() {
/* 21 */     super("FastBridge", 0, Category.MOVEMENT);
/*    */     
/* 23 */     Nightmare.instance.settingsManager.rSetting(new Setting("SmartCheck", this, false));
/* 24 */     Nightmare.instance.settingsManager.rSetting(new Setting("HeldItem", this, false));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 29 */     ItemStack i = mc.field_71439_g.func_71045_bC();
/* 30 */     BlockPos bP = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 0.5D, mc.field_71439_g.field_70161_v);
/*    */     
/* 32 */     if (isToggled()) {
/*    */       
/* 34 */       if (!(mc.field_71462_r instanceof net.minecraft.client.gui.Gui)) {
/* 35 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), Keyboard.isKeyDown(mc.field_71474_y.field_74311_E.func_151463_i()));
/*    */       }
/*    */       
/* 38 */       if (Nightmare.instance.settingsManager.getSettingByName(this, "HeldItem").getValBoolean() && 
/* 39 */         i == null && !Keyboard.isKeyDown(mc.field_71474_y.field_74311_E.func_151463_i())) {
/* 40 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
/*    */       }
/*    */ 
/*    */       
/* 44 */       if (Nightmare.instance.settingsManager.getSettingByName(this, "SmartCheck").getValBoolean()) {
/* 45 */         if ((!mc.field_71439_g.field_70122_E && !Keyboard.isKeyDown(mc.field_71474_y.field_74311_E.func_151463_i())) || (Keyboard.isKeyDown(mc.field_71474_y.field_74351_w.func_151463_i()) && !Keyboard.isKeyDown(mc.field_71474_y.field_74311_E.func_151463_i()))) {
/* 46 */           KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
/*    */         }
/* 48 */         if (mc.field_71439_g.func_70051_ag() || Keyboard.isKeyDown(mc.field_71474_y.field_74351_w.func_151463_i())) {
/*    */           return;
/*    */         }
/*    */       } 
/*    */       
/* 53 */       if ((!Nightmare.instance.settingsManager.getSettingByName(this, "HeldItem").getValBoolean() || (i != null && i.func_77973_b() instanceof net.minecraft.item.ItemBlock)) && 
/* 54 */         mc.field_71439_g.field_70122_E) {
/*    */         
/* 56 */         if (!Keyboard.isKeyDown(mc.field_71474_y.field_74311_E.func_151463_i())) {
/* 57 */           KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
/*    */         }
/*    */         
/* 60 */         if (mc.field_71441_e.func_180495_p(bP).func_177230_c() == Blocks.field_150350_a)
/* 61 */           KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\movement\FastBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */