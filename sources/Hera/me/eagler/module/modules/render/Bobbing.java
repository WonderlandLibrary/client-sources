/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class Bobbing
/*    */   extends Module {
/*    */   public Bobbing() {
/* 10 */     super("Bobbing", Category.Render);
/*    */     
/* 12 */     this.settingManager.addSetting(new Setting("Hardness", this, 2.0D, 1.0D, 10.0D, true));
/* 13 */     this.settingManager.addSetting(new Setting("NoBob", this, false));
/* 14 */     this.settingManager.addSetting(new Setting("NoHand", this, true));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 19 */     double hardness = this.settingManager.getSettingByName("Hardness").getValue();
/*    */     
/* 21 */     setExtraTag(hardness);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\Bobbing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */