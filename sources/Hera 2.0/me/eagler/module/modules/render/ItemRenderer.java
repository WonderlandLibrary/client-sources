/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class ItemRenderer
/*    */   extends Module {
/*    */   public ItemRenderer() {
/* 10 */     super("ItemRenderer", Category.Render);
/*    */     
/* 12 */     this.settingManager.addSetting(new Setting("ItemHeight", this, 4.0D, 1.0D, 10.0D, true));
/* 13 */     this.settingManager.addSetting(new Setting("BHAnimation", this, true));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 18 */     double itemHeight = this.settingManager.getSettingByName("ItemHeight").getValue();
/*    */     
/* 20 */     setExtraTag(itemHeight);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\ItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */