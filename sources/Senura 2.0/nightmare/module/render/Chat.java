/*    */ package nightmare.module.render;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class Chat
/*    */   extends Module {
/*    */   public Chat() {
/* 11 */     super("Chat", 0, Category.RENDER);
/*    */     
/* 13 */     Nightmare.instance.settingsManager.rSetting(new Setting("SmoothChat", this, false));
/* 14 */     Nightmare.instance.settingsManager.rSetting(new Setting("ClearChat", this, false));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\render\Chat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */