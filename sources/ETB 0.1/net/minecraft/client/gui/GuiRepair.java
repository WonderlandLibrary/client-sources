package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class GuiRepair extends GuiContainer implements net.minecraft.inventory.ICrafting
{
  private static final ResourceLocation anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
  private ContainerRepair anvil;
  private GuiTextField nameField;
  private InventoryPlayer playerInventory;
  private static final String __OBFID = "CL_00000738";
  
  public GuiRepair(InventoryPlayer p_i45508_1_, World worldIn)
  {
    super(new ContainerRepair(p_i45508_1_, worldIn, getMinecraftthePlayer));
    playerInventory = p_i45508_1_;
    anvil = ((ContainerRepair)inventorySlots);
  }
  



  public void initGui()
  {
    super.initGui();
    Keyboard.enableRepeatEvents(true);
    int var1 = (width - xSize) / 2;
    int var2 = (height - ySize) / 2;
    nameField = new GuiTextField(0, fontRendererObj, var1 + 62, var2 + 24, 103, 12);
    nameField.setTextColor(-1);
    nameField.setDisabledTextColour(-1);
    nameField.setEnableBackgroundDrawing(false);
    nameField.setMaxStringLength(40);
    inventorySlots.removeCraftingFromCrafters(this);
    inventorySlots.onCraftGuiOpened(this);
  }
  



  public void onGuiClosed()
  {
    super.onGuiClosed();
    Keyboard.enableRepeatEvents(false);
    inventorySlots.removeCraftingFromCrafters(this);
  }
  



  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    GlStateManager.disableLighting();
    GlStateManager.disableBlend();
    fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60, 6, 4210752);
    
    if (anvil.maximumCost > 0)
    {
      int var3 = 8453920;
      boolean var4 = true;
      String var5 = I18n.format("container.repair.cost", new Object[] { Integer.valueOf(anvil.maximumCost) });
      
      if ((anvil.maximumCost >= 40) && (!mc.thePlayer.capabilities.isCreativeMode))
      {
        var5 = I18n.format("container.repair.expensive", new Object[0]);
        var3 = 16736352;
      }
      else if (!anvil.getSlot(2).getHasStack())
      {
        var4 = false;
      }
      else if (!anvil.getSlot(2).canTakeStack(playerInventory.player))
      {
        var3 = 16736352;
      }
      
      if (var4)
      {
        int var6 = 0xFF000000 | (var3 & 0xFCFCFC) >> 2 | var3 & 0xFF000000;
        int var7 = xSize - 8 - fontRendererObj.getStringWidth(var5);
        byte var8 = 67;
        
        if (fontRendererObj.getUnicodeFlag())
        {
          drawRect(var7 - 3, var8 - 2, xSize - 7, var8 + 10, -16777216);
          drawRect(var7 - 2, var8 - 1, xSize - 8, var8 + 9, -12895429);
        }
        else
        {
          fontRendererObj.drawString(var5, var7, var8 + 1, var6);
          fontRendererObj.drawString(var5, var7 + 1, var8, var6);
          fontRendererObj.drawString(var5, var7 + 1, var8 + 1, var6);
        }
        
        fontRendererObj.drawString(var5, var7, var8, var3);
      }
    }
    
    GlStateManager.enableLighting();
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    if (nameField.textboxKeyTyped(typedChar, keyCode))
    {
      renameItem();
    }
    else
    {
      super.keyTyped(typedChar, keyCode);
    }
  }
  
  private void renameItem()
  {
    String var1 = nameField.getText();
    Slot var2 = anvil.getSlot(0);
    
    if ((var2 != null) && (var2.getHasStack()) && (!var2.getStack().hasDisplayName()) && (var1.equals(var2.getStack().getDisplayName())))
    {
      var1 = "";
    }
    
    anvil.updateItemName(var1);
    mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|ItemName", new PacketBuffer(io.netty.buffer.Unpooled.buffer()).writeString(var1)));
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    nameField.mouseClicked(mouseX, mouseY, mouseButton);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    super.drawScreen(mouseX, mouseY, partialTicks);
    GlStateManager.disableLighting();
    GlStateManager.disableBlend();
    nameField.drawTextBox();
  }
  



  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(anvilResource);
    int var4 = (width - xSize) / 2;
    int var5 = (height - ySize) / 2;
    drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
    drawTexturedModalRect(var4 + 59, var5 + 20, 0, ySize + (anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
    
    if (((anvil.getSlot(0).getHasStack()) || (anvil.getSlot(1).getHasStack())) && (!anvil.getSlot(2).getHasStack()))
    {
      drawTexturedModalRect(var4 + 99, var5 + 45, xSize, 0, 28, 21);
    }
  }
  



  public void updateCraftingInventory(Container p_71110_1_, List p_71110_2_)
  {
    sendSlotContents(p_71110_1_, 0, p_71110_1_.getSlot(0).getStack());
  }
  




  public void sendSlotContents(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_)
  {
    if (p_71111_2_ == 0)
    {
      nameField.setText(p_71111_3_ == null ? "" : p_71111_3_.getDisplayName());
      nameField.setEnabled(p_71111_3_ != null);
      
      if (p_71111_3_ != null)
      {
        renameItem();
      }
    }
  }
  
  public void sendProgressBarUpdate(Container p_71112_1_, int p_71112_2_, int p_71112_3_) {}
  
  public void func_175173_a(Container p_175173_1_, IInventory p_175173_2_) {}
}
