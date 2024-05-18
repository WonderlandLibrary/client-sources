package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class GuiScreenHorseInventory extends GuiContainer
{
  private static final ResourceLocation horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
  private IInventory field_147030_v;
  private IInventory field_147029_w;
  private EntityHorse field_147034_x;
  private float field_147033_y;
  private float field_147032_z;
  private static final String __OBFID = "CL_00000760";
  
  public GuiScreenHorseInventory(IInventory p_i1093_1_, IInventory p_i1093_2_, EntityHorse p_i1093_3_)
  {
    super(new net.minecraft.inventory.ContainerHorseInventory(p_i1093_1_, p_i1093_2_, p_i1093_3_, getMinecraftthePlayer));
    field_147030_v = p_i1093_1_;
    field_147029_w = p_i1093_2_;
    field_147034_x = p_i1093_3_;
    allowUserInput = false;
  }
  



  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    fontRendererObj.drawString(field_147029_w.getDisplayName().getUnformattedText(), 8, 6, 4210752);
    fontRendererObj.drawString(field_147030_v.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
  }
  



  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(horseGuiTextures);
    int var4 = (width - xSize) / 2;
    int var5 = (height - ySize) / 2;
    drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
    
    if (field_147034_x.isChested())
    {
      drawTexturedModalRect(var4 + 79, var5 + 17, 0, ySize, 90, 54);
    }
    
    if (field_147034_x.canWearArmor())
    {
      drawTexturedModalRect(var4 + 7, var5 + 35, 0, ySize + 54, 18, 18);
    }
    
    GuiInventory.drawEntityOnScreen(var4 + 51, var5 + 60, 17, var4 + 51 - field_147033_y, var5 + 75 - 50 - field_147032_z, field_147034_x);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    field_147033_y = mouseX;
    field_147032_z = mouseY;
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
