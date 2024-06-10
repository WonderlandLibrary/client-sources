/*    */ package nightmare.module.render;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class Chams
/*    */   extends Module {
/*    */   public Chams() {
/* 11 */     super("Chams", 0, Category.RENDER);
/*    */     
/* 13 */     Nightmare.instance.settingsManager.rSetting(new Setting("Player", this, false));
/* 14 */     Nightmare.instance.settingsManager.rSetting(new Setting("Chest", this, false));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\render\Chams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */