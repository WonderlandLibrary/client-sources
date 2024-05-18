/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class MovementInputFromOptions
/*    */   extends MovementInput
/*    */ {
/*    */   private final GameSettings gameSettings;
/*    */   
/*    */   public MovementInputFromOptions(GameSettings gameSettingsIn) {
/* 11 */     this.gameSettings = gameSettingsIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updatePlayerMoveState() {
/* 16 */     this.moveStrafe = 0.0F;
/* 17 */     this.moveForward = 0.0F;
/*    */     
/* 19 */     if (this.gameSettings.keyBindForward.isKeyDown())
/*    */     {
/* 21 */       this.moveForward++;
/*    */     }
/*    */     
/* 24 */     if (this.gameSettings.keyBindBack.isKeyDown())
/*    */     {
/* 26 */       this.moveForward--;
/*    */     }
/*    */     
/* 29 */     if (this.gameSettings.keyBindLeft.isKeyDown())
/*    */     {
/* 31 */       this.moveStrafe++;
/*    */     }
/*    */     
/* 34 */     if (this.gameSettings.keyBindRight.isKeyDown())
/*    */     {
/* 36 */       this.moveStrafe--;
/*    */     }
/*    */     
/* 39 */     this.jump = this.gameSettings.keyBindJump.isKeyDown();
/* 40 */     this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
/*    */     
/* 42 */     if (this.sneak) {
/*    */       
/* 44 */       this.moveStrafe = (float)(this.moveStrafe * 0.3D);
/* 45 */       this.moveForward = (float)(this.moveForward * 0.3D);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\MovementInputFromOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */