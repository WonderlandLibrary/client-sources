package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class GuiDispenser extends GuiContainer
{
  private static final ResourceLocation dispenserGuiTextures = new ResourceLocation("textures/gui/container/dispenser.png");
  private final InventoryPlayer field_175376_w;
  public IInventory field_175377_u;
  private static final String __OBFID = "CL_00000765";
  
  public GuiDispenser(InventoryPlayer p_i45503_1_, IInventory p_i45503_2_)
  {
    super(new net.minecraft.inventory.ContainerDispenser(p_i45503_1_, p_i45503_2_));
    field_175376_w = p_i45503_1_;
    field_175377_u = p_i45503_2_;
  }
  



  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    String var3 = field_175377_u.getDisplayName().getUnformattedText();
    fontRendererObj.drawString(var3, xSize / 2 - fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
    fontRendererObj.drawString(field_175376_w.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
  }
  



  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(dispenserGuiTextures);
    int var4 = (width - xSize) / 2;
    int var5 = (height - ySize) / 2;
    drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
  }
}
