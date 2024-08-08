/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Bhop
/*    */   extends Module
/*    */ {
/* 12 */   public static float speedMultiplyer = 1.2F;
/*    */   
/*    */   public Bhop() {
/* 15 */     super("Bhop", Category.MOVEMENT, 22);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPreUpdate() {
/* 21 */     super.onPreUpdate();
/*    */     
/* 23 */     if (this.mc.player.movementInput.moveStrafe != 0.0F && 
/* 24 */       this.mc.player.onGround) {
/* 25 */       this.mc.player.jump();
/* 26 */       this.mc.player.motionX *= speedMultiplyer;
/* 27 */       this.mc.player.motionZ *= speedMultiplyer;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPostUpdate() {
/* 33 */     super.onPostUpdate();
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\Bhop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */