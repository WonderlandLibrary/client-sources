/*    */ package nightmare.module.render;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class MotionBlur
/*    */   extends Module {
/*    */   public MotionBlur() {
/* 11 */     super("MotionBlur", 0, Category.RENDER);
/*    */     
/* 13 */     Nightmare.instance.settingsManager.rSetting(new Setting("Amount", this, 0.5D, 0.1D, 0.85D, false));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\render\MotionBlur.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */