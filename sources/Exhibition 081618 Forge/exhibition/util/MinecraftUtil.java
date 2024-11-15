package exhibition.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;

public interface MinecraftUtil {
   Minecraft mc = Minecraft.getMinecraft();
   EntityPlayerSP p = mc.thePlayer;
   WorldClient world = mc.theWorld;
   FontRenderer font = mc.fontRendererObj;
}
