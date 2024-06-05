package net.shadersmod.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.shadersmod.client.EnumShaderOption;
import net.shadersmod.client.GuiButtonEnumShaderOption$1;
import net.shadersmod.client.GuiShaders;
import net.shadersmod.client.Shaders;

public class GuiButtonEnumShaderOption extends GuiButton {
   private EnumShaderOption enumShaderOption;

   public GuiButtonEnumShaderOption(EnumShaderOption var1, int var2, int var3, int var4, int var5) {
      super(var1.ordinal(), var2, var3, var4, var5, getButtonText(var1));
      this.enumShaderOption = var1;
   }

   public EnumShaderOption getEnumShaderOption() {
      return this.enumShaderOption;
   }

   private static String getButtonText(EnumShaderOption var0) {
      String var1 = I18n.format(var0.getResourceKey(), new Object[0]) + ": ";
      switch(GuiButtonEnumShaderOption$1.$SwitchMap$net$shadersmod$client$EnumShaderOption[var0.ordinal()]) {
      case 1:
         return var1 + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
      case 2:
         return var1 + GuiShaders.toStringOnOff(Shaders.configNormalMap);
      case 3:
         return var1 + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
      case 4:
         return var1 + GuiShaders.toStringQuality(Shaders.configRenderResMul);
      case 5:
         return var1 + GuiShaders.toStringQuality(Shaders.configShadowResMul);
      case 6:
         return var1 + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
      case 7:
         return var1 + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
      case 8:
         return var1 + Shaders.configOldHandLight.getUserValue();
      case 9:
         return var1 + Shaders.configOldLighting.getUserValue();
      case 10:
         return var1 + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
      case 11:
         return var1 + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
      default:
         return var1 + Shaders.getEnumShaderOption(var0);
      }
   }

   public void updateButtonText() {
      this.displayString = getButtonText(this.enumShaderOption);
   }
}
