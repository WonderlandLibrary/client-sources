package net.minecraft.client.gui.inventory;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public abstract class GuiContainer extends GuiScreen
{
  protected static final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
  

  protected int xSize = 176;
  

  protected int ySize = 166;
  

  public Container inventorySlots;
  

  protected int guiLeft;
  

  protected int guiTop;
  

  private Slot theSlot;
  

  private Slot clickedSlot;
  
  private boolean isRightMouseClick;
  
  private ItemStack draggedStack;
  
  private int touchUpX;
  
  private int touchUpY;
  
  private Slot returningStackDestSlot;
  
  private long returningStackTime;
  
  private ItemStack returningStack;
  
  private Slot currentDragTargetSlot;
  
  private long dragItemDropDelay;
  
  protected final Set dragSplittingSlots = com.google.common.collect.Sets.newHashSet();
  protected boolean dragSplitting;
  private int dragSplittingLimit;
  private int dragSplittingButton;
  private boolean ignoreMouseUp;
  private int dragSplittingRemnant;
  private long lastClickTime;
  private Slot lastClickSlot;
  private int lastClickButton;
  private boolean doubleClick;
  private ItemStack shiftClickedSlot;
  private static final String __OBFID = "CL_00000737";
  
  public GuiContainer(Container p_i1072_1_)
  {
    inventorySlots = p_i1072_1_;
    ignoreMouseUp = true;
  }
  



  public void initGui()
  {
    super.initGui();
    mc.thePlayer.openContainer = inventorySlots;
    guiLeft = ((width - xSize) / 2);
    guiTop = ((height - ySize) / 2);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    int var4 = guiLeft;
    int var5 = guiTop;
    drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    GlStateManager.disableRescaleNormal();
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableLighting();
    GlStateManager.disableDepth();
    super.drawScreen(mouseX, mouseY, partialTicks);
    RenderHelper.enableGUIStandardItemLighting();
    GlStateManager.pushMatrix();
    GlStateManager.translate(var4, var5, 0.0F);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.enableRescaleNormal();
    theSlot = null;
    short var6 = 240;
    short var7 = 240;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    

    for (int var8 = 0; var8 < inventorySlots.inventorySlots.size(); var8++)
    {
      Slot var9 = (Slot)inventorySlots.inventorySlots.get(var8);
      drawSlot(var9);
      
      if ((isMouseOverSlot(var9, mouseX, mouseY)) && (var9.canBeHovered()))
      {
        theSlot = var9;
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        int var10 = xDisplayPosition;
        int var11 = yDisplayPosition;
        GlStateManager.colorMask(true, true, true, false);
        drawGradientRect(var10, var11, var10 + 16, var11 + 16, -2130706433, -2130706433);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
      }
    }
    
    RenderHelper.disableStandardItemLighting();
    drawGuiContainerForegroundLayer(mouseX, mouseY);
    RenderHelper.enableGUIStandardItemLighting();
    InventoryPlayer var15 = mc.thePlayer.inventory;
    ItemStack var16 = draggedStack == null ? var15.getItemStack() : draggedStack;
    
    if (var16 != null)
    {
      byte var17 = 8;
      int var11 = draggedStack == null ? 8 : 16;
      String var12 = null;
      
      if ((draggedStack != null) && (isRightMouseClick))
      {
        var16 = var16.copy();
        stackSize = net.minecraft.util.MathHelper.ceiling_float_int(stackSize / 2.0F);
      }
      else if ((dragSplitting) && (dragSplittingSlots.size() > 1))
      {
        var16 = var16.copy();
        stackSize = dragSplittingRemnant;
        
        if (stackSize == 0)
        {
          var12 = EnumChatFormatting.YELLOW + "0";
        }
      }
      
      drawItemStack(var16, mouseX - var4 - var17, mouseY - var5 - var11, var12);
    }
    
    if (returningStack != null)
    {
      float var18 = (float)(Minecraft.getSystemTime() - returningStackTime) / 100.0F;
      
      if (var18 >= 1.0F)
      {
        var18 = 1.0F;
        returningStack = null;
      }
      
      int var11 = returningStackDestSlot.xDisplayPosition - touchUpX;
      int var20 = returningStackDestSlot.yDisplayPosition - touchUpY;
      int var13 = touchUpX + (int)(var11 * var18);
      int var14 = touchUpY + (int)(var20 * var18);
      drawItemStack(returningStack, var13, var14, null);
    }
    
    GlStateManager.popMatrix();
    
    if ((var15.getItemStack() == null) && (theSlot != null) && (theSlot.getHasStack()))
    {
      ItemStack var19 = theSlot.getStack();
      renderToolTip(var19, mouseX, mouseY);
    }
    
    GlStateManager.enableLighting();
    GlStateManager.enableDepth();
    RenderHelper.enableStandardItemLighting();
  }
  



  private void drawItemStack(ItemStack stack, int x, int y, String altText)
  {
    GlStateManager.translate(0.0F, 0.0F, 32.0F);
    zLevel = 200.0F;
    itemRender.zLevel = 200.0F;
    itemRender.func_180450_b(stack, x, y);
    itemRender.func_180453_a(fontRendererObj, stack, x, y - (draggedStack == null ? 0 : 8), altText);
    zLevel = 0.0F;
    itemRender.zLevel = 0.0F;
  }
  


  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {}
  


  protected abstract void drawGuiContainerBackgroundLayer(float paramFloat, int paramInt1, int paramInt2);
  


  private void drawSlot(Slot slotIn)
  {
    int var2 = xDisplayPosition;
    int var3 = yDisplayPosition;
    ItemStack var4 = slotIn.getStack();
    boolean var5 = false;
    boolean var6 = (slotIn == clickedSlot) && (draggedStack != null) && (!isRightMouseClick);
    ItemStack var7 = mc.thePlayer.inventory.getItemStack();
    String var8 = null;
    
    if ((slotIn == clickedSlot) && (draggedStack != null) && (isRightMouseClick) && (var4 != null))
    {
      var4 = var4.copy();
      stackSize /= 2;
    }
    else if ((dragSplitting) && (dragSplittingSlots.contains(slotIn)) && (var7 != null))
    {
      if (dragSplittingSlots.size() == 1)
      {
        return;
      }
      
      if ((Container.canAddItemToSlot(slotIn, var7, true)) && (inventorySlots.canDragIntoSlot(slotIn)))
      {
        var4 = var7.copy();
        var5 = true;
        Container.computeStackSize(dragSplittingSlots, dragSplittingLimit, var4, slotIn.getStack() == null ? 0 : getStackstackSize);
        
        if (stackSize > var4.getMaxStackSize())
        {
          var8 = EnumChatFormatting.YELLOW + var4.getMaxStackSize();
          stackSize = var4.getMaxStackSize();
        }
        
        if (stackSize > slotIn.func_178170_b(var4))
        {
          var8 = EnumChatFormatting.YELLOW + slotIn.func_178170_b(var4);
          stackSize = slotIn.func_178170_b(var4);
        }
      }
      else
      {
        dragSplittingSlots.remove(slotIn);
        updateDragSplitting();
      }
    }
    
    zLevel = 100.0F;
    itemRender.zLevel = 100.0F;
    
    if (var4 == null)
    {
      String var9 = slotIn.func_178171_c();
      
      if (var9 != null)
      {
        net.minecraft.client.renderer.texture.TextureAtlasSprite var10 = mc.getTextureMapBlocks().getAtlasSprite(var9);
        GlStateManager.disableLighting();
        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        func_175175_a(var2, var3, var10, 16, 16);
        GlStateManager.enableLighting();
        var6 = true;
      }
    }
    
    if (!var6)
    {
      if (var5)
      {
        drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
      }
      
      GlStateManager.enableDepth();
      itemRender.func_180450_b(var4, var2, var3);
      itemRender.func_180453_a(fontRendererObj, var4, var2, var3, var8);
    }
    
    itemRender.zLevel = 0.0F;
    zLevel = 0.0F;
  }
  
  private void updateDragSplitting()
  {
    ItemStack var1 = mc.thePlayer.inventory.getItemStack();
    
    if ((var1 != null) && (dragSplitting))
    {
      dragSplittingRemnant = stackSize;
      
      ItemStack var4;
      int var5;
      for (Iterator var2 = dragSplittingSlots.iterator(); var2.hasNext(); dragSplittingRemnant -= stackSize - var5)
      {
        Slot var3 = (Slot)var2.next();
        var4 = var1.copy();
        var5 = var3.getStack() == null ? 0 : getStackstackSize;
        Container.computeStackSize(dragSplittingSlots, dragSplittingLimit, var4, var5);
        
        if (stackSize > var4.getMaxStackSize())
        {
          stackSize = var4.getMaxStackSize();
        }
        
        if (stackSize > var3.func_178170_b(var4))
        {
          stackSize = var3.func_178170_b(var4);
        }
      }
    }
  }
  



  private Slot getSlotAtPosition(int x, int y)
  {
    for (int var3 = 0; var3 < inventorySlots.inventorySlots.size(); var3++)
    {
      Slot var4 = (Slot)inventorySlots.inventorySlots.get(var3);
      
      if (isMouseOverSlot(var4, x, y))
      {
        return var4;
      }
    }
    
    return null;
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws java.io.IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    boolean var4 = mouseButton == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
    Slot var5 = getSlotAtPosition(mouseX, mouseY);
    long var6 = Minecraft.getSystemTime();
    doubleClick = ((lastClickSlot == var5) && (var6 - lastClickTime < 250L) && (lastClickButton == mouseButton));
    ignoreMouseUp = false;
    
    if ((mouseButton == 0) || (mouseButton == 1) || (var4))
    {
      int var8 = guiLeft;
      int var9 = guiTop;
      boolean var10 = (mouseX < var8) || (mouseY < var9) || (mouseX >= var8 + xSize) || (mouseY >= var9 + ySize);
      int var11 = -1;
      
      if (var5 != null)
      {
        var11 = slotNumber;
      }
      
      if (var10)
      {
        var11 = 64537;
      }
      
      if ((mc.gameSettings.touchscreen) && (var10) && (mc.thePlayer.inventory.getItemStack() == null))
      {
        mc.displayGuiScreen(null);
        return;
      }
      
      if (var11 != -1)
      {
        if (mc.gameSettings.touchscreen)
        {
          if ((var5 != null) && (var5.getHasStack()))
          {
            clickedSlot = var5;
            draggedStack = null;
            isRightMouseClick = (mouseButton == 1);
          }
          else
          {
            clickedSlot = null;
          }
        }
        else if (!dragSplitting)
        {
          if (mc.thePlayer.inventory.getItemStack() == null)
          {
            if (mouseButton == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100)
            {
              handleMouseClick(var5, var11, mouseButton, 3);
            }
            else
            {
              boolean var12 = (var11 != 64537) && ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54)));
              byte var13 = 0;
              
              if (var12)
              {
                shiftClickedSlot = ((var5 != null) && (var5.getHasStack()) ? var5.getStack() : null);
                var13 = 1;
              }
              else if (var11 == 64537)
              {
                var13 = 4;
              }
              
              handleMouseClick(var5, var11, mouseButton, var13);
            }
            
            ignoreMouseUp = true;
          }
          else
          {
            dragSplitting = true;
            dragSplittingButton = mouseButton;
            dragSplittingSlots.clear();
            
            if (mouseButton == 0)
            {
              dragSplittingLimit = 0;
            }
            else if (mouseButton == 1)
            {
              dragSplittingLimit = 1;
            }
            else if (mouseButton == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100)
            {
              dragSplittingLimit = 2;
            }
          }
        }
      }
    }
    
    lastClickSlot = var5;
    lastClickTime = var6;
    lastClickButton = mouseButton;
  }
  




  protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
  {
    Slot var6 = getSlotAtPosition(mouseX, mouseY);
    ItemStack var7 = mc.thePlayer.inventory.getItemStack();
    
    if ((clickedSlot != null) && (mc.gameSettings.touchscreen))
    {
      if ((clickedMouseButton == 0) || (clickedMouseButton == 1))
      {
        if (draggedStack == null)
        {
          if (var6 != clickedSlot)
          {
            draggedStack = clickedSlot.getStack().copy();
          }
        }
        else if ((draggedStack.stackSize > 1) && (var6 != null) && (Container.canAddItemToSlot(var6, draggedStack, false)))
        {
          long var8 = Minecraft.getSystemTime();
          
          if (currentDragTargetSlot == var6)
          {
            if (var8 - dragItemDropDelay > 500L)
            {
              handleMouseClick(clickedSlot, clickedSlot.slotNumber, 0, 0);
              handleMouseClick(var6, slotNumber, 1, 0);
              handleMouseClick(clickedSlot, clickedSlot.slotNumber, 0, 0);
              dragItemDropDelay = (var8 + 750L);
              draggedStack.stackSize -= 1;
            }
          }
          else
          {
            currentDragTargetSlot = var6;
            dragItemDropDelay = var8;
          }
        }
      }
    }
    else if ((dragSplitting) && (var6 != null) && (var7 != null) && (stackSize > dragSplittingSlots.size()) && (Container.canAddItemToSlot(var6, var7, true)) && (var6.isItemValid(var7)) && (inventorySlots.canDragIntoSlot(var6)))
    {
      dragSplittingSlots.add(var6);
      updateDragSplitting();
    }
  }
  



  protected void mouseReleased(int mouseX, int mouseY, int state)
  {
    Slot var4 = getSlotAtPosition(mouseX, mouseY);
    int var5 = guiLeft;
    int var6 = guiTop;
    boolean var7 = (mouseX < var5) || (mouseY < var6) || (mouseX >= var5 + xSize) || (mouseY >= var6 + ySize);
    int var8 = -1;
    
    if (var4 != null)
    {
      var8 = slotNumber;
    }
    
    if (var7)
    {
      var8 = 64537;
    }
    



    if ((doubleClick) && (var4 != null) && (state == 0) && (inventorySlots.func_94530_a(null, var4)))
    {
      if (isShiftKeyDown())
      {
        if ((var4 != null) && (inventory != null) && (shiftClickedSlot != null))
        {
          Iterator var11 = inventorySlots.inventorySlots.iterator();
          
          while (var11.hasNext())
          {
            Slot var10 = (Slot)var11.next();
            
            if ((var10 != null) && (var10.canTakeStack(mc.thePlayer)) && (var10.getHasStack()) && (inventory == inventory) && (Container.canAddItemToSlot(var10, shiftClickedSlot, true)))
            {
              handleMouseClick(var10, slotNumber, state, 1);
            }
            
          }
        }
      }
      else {
        handleMouseClick(var4, var8, state, 6);
      }
      
      doubleClick = false;
      lastClickTime = 0L;
    }
    else
    {
      if ((dragSplitting) && (dragSplittingButton != state))
      {
        dragSplitting = false;
        dragSplittingSlots.clear();
        ignoreMouseUp = true;
        return;
      }
      
      if (ignoreMouseUp)
      {
        ignoreMouseUp = false;
        return;
      }
      


      if ((clickedSlot != null) && (mc.gameSettings.touchscreen))
      {
        if ((state == 0) || (state == 1))
        {
          if ((draggedStack == null) && (var4 != clickedSlot))
          {
            draggedStack = clickedSlot.getStack();
          }
          
          boolean var9 = Container.canAddItemToSlot(var4, draggedStack, false);
          
          if ((var8 != -1) && (draggedStack != null) && (var9))
          {
            handleMouseClick(clickedSlot, clickedSlot.slotNumber, state, 0);
            handleMouseClick(var4, var8, 0, 0);
            
            if (mc.thePlayer.inventory.getItemStack() != null)
            {
              handleMouseClick(clickedSlot, clickedSlot.slotNumber, state, 0);
              touchUpX = (mouseX - var5);
              touchUpY = (mouseY - var6);
              returningStackDestSlot = clickedSlot;
              returningStack = draggedStack;
              returningStackTime = Minecraft.getSystemTime();
            }
            else
            {
              returningStack = null;
            }
          }
          else if (draggedStack != null)
          {
            touchUpX = (mouseX - var5);
            touchUpY = (mouseY - var6);
            returningStackDestSlot = clickedSlot;
            returningStack = draggedStack;
            returningStackTime = Minecraft.getSystemTime();
          }
          
          draggedStack = null;
          clickedSlot = null;
        }
      }
      else if ((dragSplitting) && (!dragSplittingSlots.isEmpty()))
      {
        handleMouseClick(null, 64537, Container.func_94534_d(0, dragSplittingLimit), 5);
        Iterator var11 = dragSplittingSlots.iterator();
        
        while (var11.hasNext())
        {
          Slot var10 = (Slot)var11.next();
          handleMouseClick(var10, slotNumber, Container.func_94534_d(1, dragSplittingLimit), 5);
        }
        
        handleMouseClick(null, 64537, Container.func_94534_d(2, dragSplittingLimit), 5);
      }
      else if (mc.thePlayer.inventory.getItemStack() != null)
      {
        if (state == mc.gameSettings.keyBindPickBlock.getKeyCode() + 100)
        {
          handleMouseClick(var4, var8, state, 3);
        }
        else
        {
          boolean var9 = (var8 != 64537) && ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54)));
          
          if (var9)
          {
            shiftClickedSlot = ((var4 != null) && (var4.getHasStack()) ? var4.getStack() : null);
          }
          
          handleMouseClick(var4, var8, state, var9 ? 1 : 0);
        }
      }
    }
    
    if (mc.thePlayer.inventory.getItemStack() == null)
    {
      lastClickTime = 0L;
    }
    
    dragSplitting = false;
  }
  



  private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY)
  {
    return isPointInRegion(xDisplayPosition, yDisplayPosition, 16, 16, mouseX, mouseY);
  }
  




  protected boolean isPointInRegion(int left, int top, int right, int bottom, int pointX, int pointY)
  {
    int var7 = guiLeft;
    int var8 = guiTop;
    pointX -= var7;
    pointY -= var8;
    return (pointX >= left - 1) && (pointX < left + right + 1) && (pointY >= top - 1) && (pointY < top + bottom + 1);
  }
  



  protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType)
  {
    if (slotIn != null)
    {
      slotId = slotNumber;
    }
    
    mc.playerController.windowClick(inventorySlots.windowId, slotId, clickedButton, clickType, mc.thePlayer);
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws java.io.IOException
  {
    if ((keyCode == 1) || (keyCode == mc.gameSettings.keyBindInventory.getKeyCode()))
    {
      mc.thePlayer.closeScreen();
    }
    
    checkHotbarKeys(keyCode);
    
    if ((theSlot != null) && (theSlot.getHasStack()))
    {
      if (keyCode == mc.gameSettings.keyBindPickBlock.getKeyCode())
      {
        handleMouseClick(theSlot, theSlot.slotNumber, 0, 3);
      }
      else if (keyCode == mc.gameSettings.keyBindDrop.getKeyCode())
      {
        handleMouseClick(theSlot, theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
      }
    }
  }
  




  protected boolean checkHotbarKeys(int keyCode)
  {
    if ((mc.thePlayer.inventory.getItemStack() == null) && (theSlot != null))
    {
      for (int var2 = 0; var2 < 9; var2++)
      {
        if (keyCode == mc.gameSettings.keyBindsHotbar[var2].getKeyCode())
        {
          handleMouseClick(theSlot, theSlot.slotNumber, var2, 2);
          return true;
        }
      }
    }
    
    return false;
  }
  



  public void onGuiClosed()
  {
    if (mc.thePlayer != null)
    {
      inventorySlots.onContainerClosed(mc.thePlayer);
    }
  }
  



  public boolean doesGuiPauseGame()
  {
    return false;
  }
  



  public void updateScreen()
  {
    super.updateScreen();
    
    if ((!mc.thePlayer.isEntityAlive()) || (mc.thePlayer.isDead))
    {
      mc.thePlayer.closeScreen();
    }
  }
}
