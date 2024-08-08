/*    */ package me.foo.lizardclient.modules;
/*    */ 
/*    */ import me.foo.lizardclient.module.Category;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Fly
/*    */   extends Module
/*    */ {
/* 11 */   public static float speedMultiplyer = 1.0F;
/*    */   
/*    */   public Fly() {
/* 14 */     super("Fly", Category.MOVEMENT, 25);
/*    */   }
/*    */   
/*    */   public void onPreUpdate() {
/* 18 */     super.onPreUpdate();
/*    */     
/* 20 */     this.mc.player.capabilities.isFlying = true;
/*    */     
/* 22 */     if (this.mc.gameSettings.keyBindJump.isPressed()) {
/* 23 */       this.mc.player.motionY = 0.2D;
/*    */     }
/* 25 */     if (this.mc.gameSettings.keyBindSneak.isPressed()) {
/* 26 */       this.mc.player.motionY = -0.2D;
/*    */     }
/* 28 */     if (this.mc.player.movementInput.forwardKeyDown || this.mc.player.movementInput.backKeyDown) {
/* 29 */       this.mc.player.motionX *= speedMultiplyer;
/* 30 */       this.mc.player.motionZ *= speedMultiplyer;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 35 */     this.mc.player.capabilities.isFlying = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\modules\Fly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */