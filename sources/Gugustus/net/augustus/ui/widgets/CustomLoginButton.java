package net.augustus.ui.widgets;

import java.awt.Color;
import net.minecraft.client.audio.SoundHandler;

public class CustomLoginButton extends CustomButton {
   public CustomLoginButton(int id, int x, int y, int width, int height, String message, Color color) {
      super(id, x, y, width, height, message, color);
   }

   @Override
   public void playPressSound(SoundHandler soundHandlerIn) {
   }
}
