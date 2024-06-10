/*    */ package nightmare.module.world;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventTick;
/*    */ import nightmare.mixin.mixins.accessor.MinecraftAccessor;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class Timer
/*    */   extends Module
/*    */ {
/* 15 */   private MinecraftAccessor mcAccessor = (MinecraftAccessor)Minecraft.func_71410_x();
/*    */   
/*    */   public Timer() {
/* 18 */     super("Timer", 0, Category.WORLD);
/*    */     
/* 20 */     Nightmare.instance.settingsManager.rSetting(new Setting("OnlyGround", this, false));
/* 21 */     Nightmare.instance.settingsManager.rSetting(new Setting("Timer", this, 1.5D, 0.1D, 3.0D, false));
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onTick(EventTick event) {
/* 27 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "OnlyGround").getValBoolean() && !mc.field_71439_g.field_70122_E) {
/* 28 */       (this.mcAccessor.timer()).field_74278_d = 1.0F;
/*    */       
/*    */       return;
/*    */     } 
/* 32 */     if (!(mc.field_71462_r instanceof net.minecraft.client.gui.Gui)) {
/* 33 */       (this.mcAccessor.timer()).field_74278_d = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Timer").getValDouble();
/*    */     } else {
/* 35 */       (this.mcAccessor.timer()).field_74278_d = 1.0F;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 42 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/*    */       return;
/*    */     }
/*    */     
/* 46 */     (this.mcAccessor.timer()).field_74278_d = 1.0F;
/*    */     
/* 48 */     super.onDisable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\world\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */