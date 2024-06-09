package me.teus.eclipse.utils.interfaces;

import me.teus.eclipse.Client;
import me.teus.eclipse.utils.font.CFontRenderer;
import me.teus.eclipse.utils.font.FontLoaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public interface HUDStyle {
   public static ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
   public static Minecraft mc = Minecraft.getMinecraft();
   public static CFontRenderer fr = FontLoaders.quicksand21;
}
