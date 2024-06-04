package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class GuiChest extends GuiContainer
{
  private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
  
  private IInventory upperChestInventory;
  
  private IInventory lowerChestInventory;
  
  private int inventoryRows;
  
  private static final String __OBFID = "CL_00000749";
  
  public GuiChest(IInventory p_i46315_1_, IInventory p_i46315_2_)
  {
    super(new net.minecraft.inventory.ContainerChest(p_i46315_1_, p_i46315_2_, getMinecraftthePlayer));
    upperChestInventory = p_i46315_1_;
    lowerChestInventory = p_i46315_2_;
    allowUserInput = false;
    short var3 = 222;
    int var4 = var3 - 108;
    inventoryRows = (p_i46315_2_.getSizeInventory() / 9);
    ySize = (var4 + inventoryRows * 18);
  }
  



  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    fontRendererObj.drawString(lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
    fontRendererObj.drawString(upperChestInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
  }
  



  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(field_147017_u);
    int var4 = (width - xSize) / 2;
    int var5 = (height - ySize) / 2;
    drawTexturedModalRect(var4, var5, 0, 0, xSize, inventoryRows * 18 + 17);
    drawTexturedModalRect(var4, var5 + inventoryRows * 18 + 17, 0, 126, xSize, 96);
  }
}
