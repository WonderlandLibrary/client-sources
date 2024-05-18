/*    */ package me.eagler.module.modules.fun;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class BigItems
/*    */   extends Module {
/*    */   public BigItems() {
/* 10 */     super("BigItems", Category.Fun);
/*    */     
/* 12 */     this.settingManager.addSetting(new Setting("Holding", this, true));
/* 13 */     this.settingManager.addSetting(new Setting("Size", this, 2.0D, 1.0D, 10.0D, true));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\fun\BigItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */