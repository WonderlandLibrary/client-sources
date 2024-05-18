/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class Hurtcam
/*    */   extends Module {
/*    */   public Hurtcam() {
/* 10 */     super("Hurtcam", Category.Render);
/*    */     
/* 12 */     this.settingManager.addSetting(new Setting("NoHurtcam", this, true));
/* 13 */     this.settingManager.addSetting(new Setting("RedHurtcam", this, true));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\Hurtcam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */