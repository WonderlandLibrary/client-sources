/*    */ package nightmare.module.world;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class TimeChanger
/*    */   extends Module {
/*    */   public TimeChanger() {
/* 11 */     super("TimeChanger", 0, Category.WORLD);
/*    */     
/* 13 */     Nightmare.instance.settingsManager.rSetting(new Setting("Time", this, 0.0D, 0.0D, 15000.0D, true));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\world\TimeChanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */