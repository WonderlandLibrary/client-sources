/*    */ package nightmare.module.misc;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class FPSBoost
/*    */   extends Module {
/*    */   public FPSBoost() {
/* 11 */     super("FPSBoost", 0, Category.MISC);
/*    */     
/* 13 */     Nightmare.instance.settingsManager.rSetting(new Setting("HideArmorStand", this, false));
/* 14 */     Nightmare.instance.settingsManager.rSetting(new Setting("HideBat", this, false));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\misc\FPSBoost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */