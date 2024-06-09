package net.minecraft.client.gui.inventory;

import io.netty.buffer.Unpooled;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;

public class GuiBeacon extends GuiContainer
{
  private static final org.apache.logging.log4j.Logger logger = ;
  private static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
  private IInventory tileBeacon;
  private ConfirmButton beaconConfirmButton;
  private boolean buttonsNotDrawn;
  private static final String __OBFID = "CL_00000739";
  
  public GuiBeacon(InventoryPlayer p_i45507_1_, IInventory p_i45507_2_)
  {
    super(new ContainerBeacon(p_i45507_1_, p_i45507_2_));
    tileBeacon = p_i45507_2_;
    xSize = 230;
    ySize = 219;
  }
  



  public void initGui()
  {
    super.initGui();
    buttonList.add(this.beaconConfirmButton = new ConfirmButton(-1, guiLeft + 164, guiTop + 107));
    buttonList.add(new CancelButton(-2, guiLeft + 190, guiTop + 107));
    buttonsNotDrawn = true;
    beaconConfirmButton.enabled = false;
  }
  



  public void updateScreen()
  {
    super.updateScreen();
    int var1 = tileBeacon.getField(0);
    int var2 = tileBeacon.getField(1);
    int var3 = tileBeacon.getField(2);
    
    if ((buttonsNotDrawn) && (var1 >= 0))
    {
      buttonsNotDrawn = false;
      





      for (int var4 = 0; var4 <= 2; var4++)
      {
        int var5 = net.minecraft.tileentity.TileEntityBeacon.effectsList[var4].length;
        int var6 = var5 * 22 + (var5 - 1) * 2;
        
        for (int var7 = 0; var7 < var5; var7++)
        {
          int var8 = effectsListid;
          PowerButton var9 = new PowerButton(var4 << 8 | var8, guiLeft + 76 + var7 * 24 - var6 / 2, guiTop + 22 + var4 * 25, var8, var4);
          buttonList.add(var9);
          
          if (var4 >= var1)
          {
            enabled = false;
          }
          else if (var8 == var2)
          {
            var9.func_146140_b(true);
          }
        }
      }
      
      byte var10 = 3;
      int var5 = net.minecraft.tileentity.TileEntityBeacon.effectsList[var10].length + 1;
      int var6 = var5 * 22 + (var5 - 1) * 2;
      
      for (int var7 = 0; var7 < var5 - 1; var7++)
      {
        int var8 = effectsListid;
        PowerButton var9 = new PowerButton(var10 << 8 | var8, guiLeft + 167 + var7 * 24 - var6 / 2, guiTop + 47, var8, var10);
        buttonList.add(var9);
        
        if (var10 >= var1)
        {
          enabled = false;
        }
        else if (var8 == var3)
        {
          var9.func_146140_b(true);
        }
      }
      
      if (var2 > 0)
      {
        PowerButton var11 = new PowerButton(var10 << 8 | var2, guiLeft + 167 + (var5 - 1) * 24 - var6 / 2, guiTop + 47, var2, var10);
        buttonList.add(var11);
        
        if (var10 >= var1)
        {
          enabled = false;
        }
        else if (var2 == var3)
        {
          var11.func_146140_b(true);
        }
      }
    }
    
    beaconConfirmButton.enabled = ((tileBeacon.getStackInSlot(0) != null) && (var2 > 0));
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    if (id == -2)
    {
      mc.displayGuiScreen(null);
    }
    else if (id == -1)
    {
      String var2 = "MC|Beacon";
      PacketBuffer var3 = new PacketBuffer(Unpooled.buffer());
      var3.writeInt(tileBeacon.getField(1));
      var3.writeInt(tileBeacon.getField(2));
      mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var3));
      mc.displayGuiScreen(null);
    }
    else if ((button instanceof PowerButton))
    {
      if (((PowerButton)button).func_146141_c())
      {
        return;
      }
      
      int var5 = id;
      int var6 = var5 & 0xFF;
      int var4 = var5 >> 8;
      
      if (var4 < 3)
      {
        tileBeacon.setField(1, var6);
      }
      else
      {
        tileBeacon.setField(2, var6);
      }
      
      buttonList.clear();
      initGui();
      updateScreen();
    }
  }
  



  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    RenderHelper.disableStandardItemLighting();
    drawCenteredString(fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
    drawCenteredString(fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
    Iterator var3 = buttonList.iterator();
    
    while (var3.hasNext())
    {
      GuiButton var4 = (GuiButton)var3.next();
      
      if (var4.isMouseOver())
      {
        var4.drawButtonForegroundLayer(mouseX - guiLeft, mouseY - guiTop);
        break;
      }
    }
    
    RenderHelper.enableGUIStandardItemLighting();
  }
  



  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(beaconGuiTextures);
    int var4 = (width - xSize) / 2;
    int var5 = (height - ySize) / 2;
    drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
    itemRender.zLevel = 100.0F;
    itemRender.func_180450_b(new ItemStack(Items.emerald), var4 + 42, var5 + 109);
    itemRender.func_180450_b(new ItemStack(Items.diamond), var4 + 42 + 22, var5 + 109);
    itemRender.func_180450_b(new ItemStack(Items.gold_ingot), var4 + 42 + 44, var5 + 109);
    itemRender.func_180450_b(new ItemStack(Items.iron_ingot), var4 + 42 + 66, var5 + 109);
    itemRender.zLevel = 0.0F;
  }
  
  static class Button extends GuiButton
  {
    private final ResourceLocation field_146145_o;
    private final int field_146144_p;
    private final int field_146143_q;
    private boolean field_146142_r;
    private static final String __OBFID = "CL_00000743";
    
    protected Button(int p_i1077_1_, int p_i1077_2_, int p_i1077_3_, ResourceLocation p_i1077_4_, int p_i1077_5_, int p_i1077_6_)
    {
      super(p_i1077_2_, p_i1077_3_, 22, 22, "");
      field_146145_o = p_i1077_4_;
      field_146144_p = p_i1077_5_;
      field_146143_q = p_i1077_6_;
    }
    
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
      if (visible)
      {
        mc.getTextureManager().bindTexture(GuiBeacon.beaconGuiTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        hovered = ((mouseX >= xPosition) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height));
        short var4 = 219;
        int var5 = 0;
        
        if (!enabled)
        {
          var5 += width * 2;
        }
        else if (field_146142_r)
        {
          var5 += width * 1;
        }
        else if (hovered)
        {
          var5 += width * 3;
        }
        
        drawTexturedModalRect(xPosition, yPosition, var5, var4, width, height);
        
        if (!GuiBeacon.beaconGuiTextures.equals(field_146145_o))
        {
          mc.getTextureManager().bindTexture(field_146145_o);
        }
        
        drawTexturedModalRect(xPosition + 2, yPosition + 2, field_146144_p, field_146143_q, 18, 18);
      }
    }
    
    public boolean func_146141_c()
    {
      return field_146142_r;
    }
    
    public void func_146140_b(boolean p_146140_1_)
    {
      field_146142_r = p_146140_1_;
    }
  }
  
  class CancelButton extends GuiBeacon.Button
  {
    private static final String __OBFID = "CL_00000740";
    
    public CancelButton(int p_i1074_2_, int p_i1074_3_, int p_i1074_4_)
    {
      super(p_i1074_3_, p_i1074_4_, GuiBeacon.beaconGuiTextures, 112, 220);
    }
    
    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
      drawCreativeTabHoveringText(I18n.format("gui.cancel", new Object[0]), mouseX, mouseY);
    }
  }
  
  class ConfirmButton extends GuiBeacon.Button
  {
    private static final String __OBFID = "CL_00000741";
    
    public ConfirmButton(int p_i1075_2_, int p_i1075_3_, int p_i1075_4_)
    {
      super(p_i1075_3_, p_i1075_4_, GuiBeacon.beaconGuiTextures, 90, 220);
    }
    
    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
      drawCreativeTabHoveringText(I18n.format("gui.done", new Object[0]), mouseX, mouseY);
    }
  }
  
  class PowerButton extends GuiBeacon.Button
  {
    private final int field_146149_p;
    private final int field_146148_q;
    private static final String __OBFID = "CL_00000742";
    
    public PowerButton(int p_i1076_2_, int p_i1076_3_, int p_i1076_4_, int p_i1076_5_, int p_i1076_6_)
    {
      super(p_i1076_3_, p_i1076_4_, GuiContainer.inventoryBackground, 0 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() / 8 * 18);
      field_146149_p = p_i1076_5_;
      field_146148_q = p_i1076_6_;
    }
    
    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
      String var3 = I18n.format(Potion.potionTypes[field_146149_p].getName(), new Object[0]);
      
      if ((field_146148_q >= 3) && (field_146149_p != regenerationid))
      {
        var3 = var3 + " II";
      }
      
      drawCreativeTabHoveringText(var3, mouseX, mouseY);
    }
  }
}
