package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;

public class MovementInputFromOptions extends MovementInput {
   private final GameSettings gameSettings;

   public MovementInputFromOptions(GameSettings var1) {
      this.gameSettings = var1;
   }

   public void updatePlayerMoveState() {
      this.setMoveStrafe(0.0F);
      this.setMoveForward(0.0F);
      if(this.gameSettings.keyBindForward.isKeyDown()) {
         this.setMoveForward(this.getMoveForward() + 1.0F);
      }

      if(this.gameSettings.keyBindBack.isKeyDown()) {
         this.setMoveForward(this.getMoveForward() - 1.0F);
      }

      if(this.gameSettings.keyBindLeft.isKeyDown()) {
         this.setMoveStrafe(this.getMoveStrafe() + 1.0F);
      }

      if(this.gameSettings.keyBindRight.isKeyDown()) {
         this.setMoveStrafe(this.getMoveStrafe() - 1.0F);
      }

      this.a(this.gameSettings.keyBindJump.isKeyDown());
      this.setSneak(this.gameSettings.keyBindSneak.isKeyDown());
      if(this.sneak()) {
         this.setMoveStrafe(this.getMoveStrafe() * 0.3F);
         this.setMoveForward(this.getMoveForward() * 0.3F);
      }

   }
}
