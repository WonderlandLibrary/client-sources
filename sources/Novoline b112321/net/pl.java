package net;

import net.shadersmod.client.EnumShaderOption;
import net.shadersmod.client.GuiButtonEnumShaderOption;

public class pl {
   public static EnumShaderOption a(GuiButtonEnumShaderOption var0) {
      return var0.getEnumShaderOption();
   }

   public static void b(GuiButtonEnumShaderOption var0) {
      var0.updateButtonText();
   }
}
