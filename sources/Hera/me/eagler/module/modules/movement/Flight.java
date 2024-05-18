/*    */ package me.eagler.module.modules.movement;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class Flight
/*    */   extends Module {
/*    */   public Flight() {
/* 10 */     super("Flight", Category.Movement);
/*    */     
/* 12 */     this.settingManager.addSetting(new Setting("Speed", this, 2.0D, 1.0D, 10.0D, true));
/* 13 */     this.settingManager.addSetting(new Setting("Y-Speed", this, 1.0D, 1.0D, 10.0D, true));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 18 */     if (this.mc.theWorld != null)
/*    */     {
/* 20 */       this.mc.thePlayer.capabilities.isFlying = false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 28 */     float speed = (float)this.settingManager.getSettingByName("Speed").getValue();
/*    */     
/* 30 */     float yspeed = (float)this.settingManager.getSettingByName("Speed").getValue() / 4.0F;
/*    */     
/* 32 */     setExtraTag(speed);
/*    */     
/* 34 */     float realspeed = speed / 2.0F;
/*    */     
/* 36 */     this.mc.thePlayer.capabilities.isFlying = true;
/*    */     
/* 38 */     Speed.setSpeed(realspeed);
/*    */     
/* 40 */     if (this.mc.gameSettings.keyBindJump.pressed)
/*    */     {
/* 42 */       this.mc.thePlayer.motionY = yspeed;
/*    */     }
/*    */ 
/*    */     
/* 46 */     if (this.mc.gameSettings.keyBindSneak.pressed)
/*    */     {
/* 48 */       this.mc.thePlayer.motionY = -yspeed;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\movement\Flight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */