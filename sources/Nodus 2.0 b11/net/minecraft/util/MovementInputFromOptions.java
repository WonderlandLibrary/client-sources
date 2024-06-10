/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.settings.GameSettings;
/*  4:   */ import net.minecraft.client.settings.KeyBinding;
/*  5:   */ 
/*  6:   */ public class MovementInputFromOptions
/*  7:   */   extends MovementInput
/*  8:   */ {
/*  9:   */   private GameSettings gameSettings;
/* 10:   */   private static final String __OBFID = "CL_00000937";
/* 11:   */   
/* 12:   */   public MovementInputFromOptions(GameSettings par1GameSettings)
/* 13:   */   {
/* 14:12 */     this.gameSettings = par1GameSettings;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void updatePlayerMoveState()
/* 18:   */   {
/* 19:17 */     this.moveStrafe = 0.0F;
/* 20:18 */     this.moveForward = 0.0F;
/* 21:20 */     if (this.gameSettings.keyBindForward.getIsKeyPressed()) {
/* 22:22 */       this.moveForward += 1.0F;
/* 23:   */     }
/* 24:25 */     if (this.gameSettings.keyBindBack.getIsKeyPressed()) {
/* 25:27 */       this.moveForward -= 1.0F;
/* 26:   */     }
/* 27:30 */     if (this.gameSettings.keyBindLeft.getIsKeyPressed()) {
/* 28:32 */       this.moveStrafe += 1.0F;
/* 29:   */     }
/* 30:35 */     if (this.gameSettings.keyBindRight.getIsKeyPressed()) {
/* 31:37 */       this.moveStrafe -= 1.0F;
/* 32:   */     }
/* 33:40 */     this.jump = this.gameSettings.keyBindJump.getIsKeyPressed();
/* 34:41 */     this.sneak = this.gameSettings.keyBindSneak.getIsKeyPressed();
/* 35:43 */     if (this.sneak)
/* 36:   */     {
/* 37:45 */       this.moveStrafe = ((float)(this.moveStrafe * 0.3D));
/* 38:46 */       this.moveForward = ((float)(this.moveForward * 0.3D));
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MovementInputFromOptions
 * JD-Core Version:    0.7.0.1
 */