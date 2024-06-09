/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class DamageIndicator
/*    */   extends Module
/*    */ {
/*    */   public DamageIndicator() {
/* 12 */     super("DMGIndicator", Category.Render);
/*    */     
/* 14 */     ArrayList<String> options = new ArrayList<String>();
/*    */     
/* 16 */     options.add("Side");
/* 17 */     options.add("Center");
/*    */     
/* 19 */     this.settingManager.addSetting(new Setting("DIMode", this, "Center", options));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 26 */     String mode = this.settingManager.getSettingByName("DIMode").getMode();
/*    */     
/* 28 */     setExtraTag(mode);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\DamageIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */