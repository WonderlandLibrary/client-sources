/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Nofall
/*    */   extends Module
/*    */ {
/*    */   public Nofall() {
/* 12 */     super("Nofall", Category.PLAYER, 48);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPreUpdate() {
/* 18 */     if (this.mc.player.fallDistance > 3.0F)
/* 19 */       this.mc.player.onGround = true; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\Nofall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */