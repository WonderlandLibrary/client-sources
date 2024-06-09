package me.hexxed.mercury.overlay.components;

import me.hexxed.mercury.overlay.OverlayComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import org.lwjgl.opengl.GL11;

public class ARMORHUDComponent extends OverlayComponent
{
  public ARMORHUDComponent(int x, int y, boolean chat, String xy)
  {
    super("ArmorHud", x, y, chat, xy);
  }
  
  public void renderComponent()
  {
    ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int width = sr.getScaledWidth();
    int height = sr.getScaledHeight();
    net.minecraft.client.gui.FontRenderer fr = mc.fontRendererObj;
    if (mc.playerController.isNotCreative()) {
      int x = 15;
      GL11.glPushMatrix();
      RenderHelper.enableGUIStandardItemLighting();
      for (int index = 3; index >= 0; index--) {
        net.minecraft.item.ItemStack stack = mc.thePlayer.inventory.armorInventory[index];
        if (stack != null) {
          mc.getRenderItem().func_180450_b(stack, sr.getScaledWidth() / 2 + x, sr.getScaledHeight() - (mc.thePlayer.isInsideOfMaterial(net.minecraft.block.material.Material.water) ? 65 : 55));
          mc.getRenderItem().func_175030_a(mc.fontRendererObj, stack, sr.getScaledWidth() / 2 + x, sr.getScaledHeight() - (mc.thePlayer.isInsideOfMaterial(net.minecraft.block.material.Material.water) ? 65 : 55));
          x += 18;
        }
      }
      RenderHelper.disableStandardItemLighting();
      GL11.glPopMatrix();
    }
  }
}
