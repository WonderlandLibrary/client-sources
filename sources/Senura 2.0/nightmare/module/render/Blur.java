/*    */ package nightmare.module.render;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class Blur
/*    */   extends Module {
/*    */   public Blur() {
/* 11 */     super("Blur", 0, Category.RENDER);
/*    */     
/* 13 */     Nightmare.instance.settingsManager.rSetting(new Setting("ClickGUI", this, false));
/* 14 */     Nightmare.instance.settingsManager.rSetting(new Setting("Inventory", this, false));
/* 15 */     Nightmare.instance.settingsManager.rSetting(new Setting("Notification", this, false));
/* 16 */     Nightmare.instance.settingsManager.rSetting(new Setting("TargetHUD", this, false));
/* 17 */     Nightmare.instance.settingsManager.rSetting(new Setting("Radius", this, 20.0D, 1.0D, 80.0D, true));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\render\Blur.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */