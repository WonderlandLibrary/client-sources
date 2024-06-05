package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {
   private static final String __OBFID = "CL_00000937";
   private final GameSettings gameSettings;

   public void updatePlayerMoveState() {
      moveStrafe = 0.0F;
      moveForward = 0.0F;
      if (this.gameSettings.keyBindForward.getIsKeyPressed()) {
         ++moveForward;
      }

      if (this.gameSettings.keyBindBack.getIsKeyPressed()) {
         --moveForward;
      }

      if (this.gameSettings.keyBindLeft.getIsKeyPressed()) {
         ++moveStrafe;
      }

      if (this.gameSettings.keyBindRight.getIsKeyPressed()) {
         --moveStrafe;
      }

      this.jump = this.gameSettings.keyBindJump.getIsKeyPressed();
      this.sneak = this.gameSettings.keyBindSneak.getIsKeyPressed();
      if (this.sneak) {
         moveStrafe = (float)((double)moveStrafe * 0.3D);
         moveForward = (float)((double)moveForward * 0.3D);
      }

   }

   public MovementInputFromOptions(GameSettings var1) {
      this.gameSettings = var1;
   }
}
