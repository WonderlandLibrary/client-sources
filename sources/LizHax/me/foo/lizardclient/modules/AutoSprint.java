/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ 
/*    */ 
/*    */ public class AutoSprint
/*    */   extends Module
/*    */ {
/*    */   public AutoSprint() {
/* 11 */     super("AutoSprint", Category.MOVEMENT, 50);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPreUpdate() {
/* 17 */     super.onPreUpdate();
/*    */     
/* 19 */     if (this.mc.player.movementInput.forwardKeyDown)
/* 20 */       this.mc.player.setSprinting(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\AutoSprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */