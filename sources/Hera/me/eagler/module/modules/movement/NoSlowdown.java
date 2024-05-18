/*    */ package me.eagler.module.modules.movement;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class NoSlowdown
/*    */   extends Module
/*    */ {
/*    */   public NoSlowdown() {
/* 11 */     super("NoSlowdown", Category.Movement);
/*    */     
/* 13 */     this.settingManager.addSetting(new Setting("Gomme", this, false));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 20 */     if (this.settingManager.getSettingByName("Gomme").getBoolean())
/*    */     {
/* 22 */       if (this.mc.thePlayer.isUsingItem())
/*    */       {
/* 24 */         this.mc.thePlayer.setSprinting(false);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\movement\NoSlowdown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */