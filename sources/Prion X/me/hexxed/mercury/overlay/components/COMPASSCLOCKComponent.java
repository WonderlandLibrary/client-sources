package me.hexxed.mercury.overlay.components;

import me.hexxed.mercury.overlay.OverlayComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;

public class COMPASSCLOCKComponent extends OverlayComponent
{
  public COMPASSCLOCKComponent(int x, int y, boolean chat, String xy)
  {
    super("CompassClock", x, y, chat, xy);
  }
  
  public void renderComponent()
  {
    int x = getX();
    int y = getY();
    mc.getRenderItem().func_180450_b(new net.minecraft.item.ItemStack(net.minecraft.item.Item.getByNameOrId("compass")), x, y);
    
    mc.getRenderItem().func_180450_b(new net.minecraft.item.ItemStack(net.minecraft.item.Item.getByNameOrId("clock")), x + 15, y);
  }
}
