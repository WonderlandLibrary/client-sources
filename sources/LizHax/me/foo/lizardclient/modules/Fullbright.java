/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Fullbright
/*    */   extends Module
/*    */ {
/* 11 */   float preGammaSetting = this.mc.gameSettings.gammaSetting;
/*    */   
/*    */   public Fullbright() {
/* 14 */     super("Fullbright", Category.WORLD, 37);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 19 */     this.mc.gameSettings.gammaSetting = 1000.0F;
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 23 */     this.mc.gameSettings.gammaSetting = this.preGammaSetting;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\Fullbright.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */