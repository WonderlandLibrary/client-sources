/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuraESP
/*    */   extends Module
/*    */ {
/*    */   public AuraESP() {
/* 16 */     super("AuraESP", Category.Render);
/*    */     
/* 18 */     this.settingManager.addSetting(new Setting("Width", this, 0.6D, 0.1D, 2.0D, false));
/* 19 */     this.settingManager.addSetting(new Setting("Height", this, 0.2D, 0.1D, 2.0D, false));
/*    */   }
/*    */   
/*    */   public void onRender() {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\AuraESP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */