package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;

public class GuiMerchant extends GuiContainer
{
  private static final org.apache.logging.log4j.Logger logger = ;
  private static final ResourceLocation field_147038_v = new ResourceLocation("textures/gui/container/villager.png");
  private IMerchant field_147037_w;
  private MerchantButton field_147043_x;
  private MerchantButton field_147042_y;
  private int field_147041_z;
  private IChatComponent field_147040_A;
  private static final String __OBFID = "CL_00000762";
  
  public GuiMerchant(InventoryPlayer p_i45500_1_, IMerchant p_i45500_2_, World worldIn)
  {
    super(new ContainerMerchant(p_i45500_1_, p_i45500_2_, worldIn));
    field_147037_w = p_i45500_2_;
    field_147040_A = p_i45500_2_.getDisplayName();
  }
  



  public void initGui()
  {
    super.initGui();
    int var1 = (width - xSize) / 2;
    int var2 = (height - ySize) / 2;
    buttonList.add(this.field_147043_x = new MerchantButton(1, var1 + 120 + 27, var2 + 24 - 1, true));
    buttonList.add(this.field_147042_y = new MerchantButton(2, var1 + 36 - 19, var2 + 24 - 1, false));
    field_147043_x.enabled = false;
    field_147042_y.enabled = false;
  }
  



  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    String var3 = field_147040_A.getUnformattedText();
    fontRendererObj.drawString(var3, xSize / 2 - fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
    fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
  }
  



  public void updateScreen()
  {
    super.updateScreen();
    MerchantRecipeList var1 = field_147037_w.getRecipes(mc.thePlayer);
    
    if (var1 != null)
    {
      field_147043_x.enabled = (field_147041_z < var1.size() - 1);
      field_147042_y.enabled = (field_147041_z > 0);
    }
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    boolean var2 = false;
    
    if (button == field_147043_x)
    {
      field_147041_z += 1;
      MerchantRecipeList var3 = field_147037_w.getRecipes(mc.thePlayer);
      
      if ((var3 != null) && (field_147041_z >= var3.size()))
      {
        field_147041_z = (var3.size() - 1);
      }
      
      var2 = true;
    }
    else if (button == field_147042_y)
    {
      field_147041_z -= 1;
      
      if (field_147041_z < 0)
      {
        field_147041_z = 0;
      }
      
      var2 = true;
    }
    
    if (var2)
    {
      ((ContainerMerchant)inventorySlots).setCurrentRecipeIndex(field_147041_z);
      PacketBuffer var4 = new PacketBuffer(io.netty.buffer.Unpooled.buffer());
      var4.writeInt(field_147041_z);
      mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", var4));
    }
  }
  



  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(field_147038_v);
    int var4 = (width - xSize) / 2;
    int var5 = (height - ySize) / 2;
    drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
    MerchantRecipeList var6 = field_147037_w.getRecipes(mc.thePlayer);
    
    if ((var6 != null) && (!var6.isEmpty()))
    {
      int var7 = field_147041_z;
      
      if ((var7 < 0) || (var7 >= var6.size()))
      {
        return;
      }
      
      MerchantRecipe var8 = (MerchantRecipe)var6.get(var7);
      
      if (var8.isRecipeDisabled())
      {
        mc.getTextureManager().bindTexture(field_147038_v);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        drawTexturedModalRect(guiLeft + 83, guiTop + 21, 212, 0, 28, 21);
        drawTexturedModalRect(guiLeft + 83, guiTop + 51, 212, 0, 28, 21);
      }
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    super.drawScreen(mouseX, mouseY, partialTicks);
    MerchantRecipeList var4 = field_147037_w.getRecipes(mc.thePlayer);
    
    if ((var4 != null) && (!var4.isEmpty()))
    {
      int var5 = (width - xSize) / 2;
      int var6 = (height - ySize) / 2;
      int var7 = field_147041_z;
      MerchantRecipe var8 = (MerchantRecipe)var4.get(var7);
      ItemStack var9 = var8.getItemToBuy();
      ItemStack var10 = var8.getSecondItemToBuy();
      ItemStack var11 = var8.getItemToSell();
      GlStateManager.pushMatrix();
      RenderHelper.enableGUIStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableColorMaterial();
      GlStateManager.enableLighting();
      itemRender.zLevel = 100.0F;
      itemRender.func_180450_b(var9, var5 + 36, var6 + 24);
      itemRender.func_175030_a(fontRendererObj, var9, var5 + 36, var6 + 24);
      
      if (var10 != null)
      {
        itemRender.func_180450_b(var10, var5 + 62, var6 + 24);
        itemRender.func_175030_a(fontRendererObj, var10, var5 + 62, var6 + 24);
      }
      
      itemRender.func_180450_b(var11, var5 + 120, var6 + 24);
      itemRender.func_175030_a(fontRendererObj, var11, var5 + 120, var6 + 24);
      itemRender.zLevel = 0.0F;
      GlStateManager.disableLighting();
      
      if ((isPointInRegion(36, 24, 16, 16, mouseX, mouseY)) && (var9 != null))
      {
        renderToolTip(var9, mouseX, mouseY);
      }
      else if ((var10 != null) && (isPointInRegion(62, 24, 16, 16, mouseX, mouseY)) && (var10 != null))
      {
        renderToolTip(var10, mouseX, mouseY);
      }
      else if ((var11 != null) && (isPointInRegion(120, 24, 16, 16, mouseX, mouseY)) && (var11 != null))
      {
        renderToolTip(var11, mouseX, mouseY);
      }
      else if ((var8.isRecipeDisabled()) && ((isPointInRegion(83, 21, 28, 21, mouseX, mouseY)) || (isPointInRegion(83, 51, 28, 21, mouseX, mouseY))))
      {
        drawCreativeTabHoveringText(I18n.format("merchant.deprecated", new Object[0]), mouseX, mouseY);
      }
      
      GlStateManager.popMatrix();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
    }
  }
  
  public IMerchant getMerchant()
  {
    return field_147037_w;
  }
  
  static class MerchantButton extends GuiButton
  {
    private final boolean field_146157_o;
    private static final String __OBFID = "CL_00000763";
    
    public MerchantButton(int p_i1095_1_, int p_i1095_2_, int p_i1095_3_, boolean p_i1095_4_)
    {
      super(p_i1095_2_, p_i1095_3_, 12, 19, "");
      field_146157_o = p_i1095_4_;
    }
    
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
      if (visible)
      {
        mc.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        boolean var4 = (mouseX >= xPosition) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height);
        int var5 = 0;
        int var6 = 176;
        
        if (!enabled)
        {
          var6 += width * 2;
        }
        else if (var4)
        {
          var6 += width;
        }
        
        if (!field_146157_o)
        {
          var5 += height;
        }
        
        drawTexturedModalRect(xPosition, yPosition, var6, var5, width, height);
      }
    }
  }
}
