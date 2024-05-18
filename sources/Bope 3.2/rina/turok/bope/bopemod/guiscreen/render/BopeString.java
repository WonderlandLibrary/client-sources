package rina.turok.bope.bopemod.guiscreen.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class BopeString extends FontRenderer {
   Minecraft mc = Minecraft.getMinecraft();

   public BopeString(boolean use) {
      super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), use);
   }
}
