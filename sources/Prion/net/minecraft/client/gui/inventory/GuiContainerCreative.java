package net.minecraft.client.gui.inventory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiContainerCreative extends InventoryEffectRenderer
{
  private static final ResourceLocation creativeInventoryTabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
  private static InventoryBasic field_147060_v = new InventoryBasic("tmp", true, 45);
  

  private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
  
  private float currentScroll;
  
  private boolean isScrolling;
  
  private boolean wasClicking;
  
  private GuiTextField searchField;
  
  private List field_147063_B;
  
  private Slot field_147064_C;
  
  private boolean field_147057_D;
  
  private CreativeCrafting field_147059_E;
  private static final String __OBFID = "CL_00000752";
  
  public GuiContainerCreative(EntityPlayer p_i1088_1_)
  {
    super(new ContainerCreative(p_i1088_1_));
    openContainer = inventorySlots;
    allowUserInput = true;
    ySize = 136;
    xSize = 195;
  }
  



  public void updateScreen()
  {
    if (!mc.playerController.isInCreativeMode())
    {
      mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
    }
    
    func_175378_g();
  }
  



  protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType)
  {
    field_147057_D = true;
    boolean var5 = clickType == 1;
    clickType = (slotId == 64537) && (clickType == 0) ? 4 : clickType;
    


    if ((slotIn == null) && (selectedTabIndex != CreativeTabs.tabInventory.getTabIndex()) && (clickType != 5))
    {
      InventoryPlayer var11 = mc.thePlayer.inventory;
      
      if (var11.getItemStack() != null)
      {
        if (clickedButton == 0)
        {
          mc.thePlayer.dropPlayerItemWithRandomChoice(var11.getItemStack(), true);
          mc.playerController.sendPacketDropItem(var11.getItemStack());
          var11.setItemStack(null);
        }
        
        if (clickedButton == 1)
        {
          ItemStack var7 = var11.getItemStack().splitStack(1);
          mc.thePlayer.dropPlayerItemWithRandomChoice(var7, true);
          mc.playerController.sendPacketDropItem(var7);
          
          if (getItemStackstackSize == 0)
          {
            var11.setItemStack(null);
          }
          
        }
        
      }
      

    }
    else if ((slotIn == field_147064_C) && (var5))
    {
      for (int var10 = 0; var10 < mc.thePlayer.inventoryContainer.getInventory().size(); var10++)
      {
        mc.playerController.sendSlotPacket(null, var10);

      }
      


    }
    else if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex())
    {
      if (slotIn == field_147064_C)
      {
        mc.thePlayer.inventory.setItemStack(null);
      }
      else if ((clickType == 4) && (slotIn != null) && (slotIn.getHasStack()))
      {
        ItemStack var6 = slotIn.decrStackSize(clickedButton == 0 ? 1 : slotIn.getStack().getMaxStackSize());
        mc.thePlayer.dropPlayerItemWithRandomChoice(var6, true);
        mc.playerController.sendPacketDropItem(var6);
      }
      else if ((clickType == 4) && (mc.thePlayer.inventory.getItemStack() != null))
      {
        mc.thePlayer.dropPlayerItemWithRandomChoice(mc.thePlayer.inventory.getItemStack(), true);
        mc.playerController.sendPacketDropItem(mc.thePlayer.inventory.getItemStack());
        mc.thePlayer.inventory.setItemStack(null);
      }
      else
      {
        mc.thePlayer.inventoryContainer.slotClick(slotIn == null ? slotId : field_148332_b.slotNumber, clickedButton, clickType, mc.thePlayer);
        mc.thePlayer.inventoryContainer.detectAndSendChanges();
      }
    }
    else if ((clickType != 5) && (inventory == field_147060_v))
    {
      InventoryPlayer var11 = mc.thePlayer.inventory;
      ItemStack var7 = var11.getItemStack();
      ItemStack var8 = slotIn.getStack();
      

      if (clickType == 2)
      {
        if ((var8 != null) && (clickedButton >= 0) && (clickedButton < 9))
        {
          ItemStack var9 = var8.copy();
          stackSize = var9.getMaxStackSize();
          mc.thePlayer.inventory.setInventorySlotContents(clickedButton, var9);
          mc.thePlayer.inventoryContainer.detectAndSendChanges();
        }
        
        return;
      }
      
      if (clickType == 3)
      {
        if ((var11.getItemStack() == null) && (slotIn.getHasStack()))
        {
          ItemStack var9 = slotIn.getStack().copy();
          stackSize = var9.getMaxStackSize();
          var11.setItemStack(var9);
        }
        
        return;
      }
      
      if (clickType == 4)
      {
        if (var8 != null)
        {
          ItemStack var9 = var8.copy();
          stackSize = (clickedButton == 0 ? 1 : var9.getMaxStackSize());
          mc.thePlayer.dropPlayerItemWithRandomChoice(var9, true);
          mc.playerController.sendPacketDropItem(var9);
        }
        
        return;
      }
      
      if ((var7 != null) && (var8 != null) && (var7.isItemEqual(var8)))
      {
        if (clickedButton == 0)
        {
          if (var5)
          {
            stackSize = var7.getMaxStackSize();
          }
          else if (stackSize < var7.getMaxStackSize())
          {
            stackSize += 1;
          }
        }
        else if (stackSize <= 1)
        {
          var11.setItemStack(null);
        }
        else
        {
          stackSize -= 1;
        }
      }
      else if ((var8 != null) && (var7 == null))
      {
        var11.setItemStack(ItemStack.copyItemStack(var8));
        var7 = var11.getItemStack();
        
        if (var5)
        {
          stackSize = var7.getMaxStackSize();
        }
      }
      else
      {
        var11.setItemStack(null);
      }
    }
    else
    {
      inventorySlots.slotClick(slotIn == null ? slotId : slotNumber, clickedButton, clickType, mc.thePlayer);
      
      if (Container.getDragEvent(clickedButton) == 2)
      {
        for (int var10 = 0; var10 < 9; var10++)
        {
          mc.playerController.sendSlotPacket(inventorySlots.getSlot(45 + var10).getStack(), 36 + var10);
        }
      }
      else if (slotIn != null)
      {
        ItemStack var6 = inventorySlots.getSlot(slotNumber).getStack();
        mc.playerController.sendSlotPacket(var6, slotNumber - inventorySlots.inventorySlots.size() + 9 + 36);
      }
    }
  }
  





  public void initGui()
  {
    if (mc.playerController.isInCreativeMode())
    {
      super.initGui();
      buttonList.clear();
      org.lwjgl.input.Keyboard.enableRepeatEvents(true);
      searchField = new GuiTextField(0, fontRendererObj, guiLeft + 82, guiTop + 6, 89, fontRendererObj.FONT_HEIGHT);
      searchField.setMaxStringLength(15);
      searchField.setEnableBackgroundDrawing(false);
      searchField.setVisible(false);
      searchField.setTextColor(16777215);
      int var1 = selectedTabIndex;
      selectedTabIndex = -1;
      setCurrentCreativeTab(CreativeTabs.creativeTabArray[var1]);
      field_147059_E = new CreativeCrafting(mc);
      mc.thePlayer.inventoryContainer.onCraftGuiOpened(field_147059_E);
    }
    else
    {
      mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
    }
  }
  



  public void onGuiClosed()
  {
    super.onGuiClosed();
    
    if ((mc.thePlayer != null) && (mc.thePlayer.inventory != null))
    {
      mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(field_147059_E);
    }
    
    org.lwjgl.input.Keyboard.enableRepeatEvents(false);
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    if (selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex())
    {
      if (GameSettings.isKeyDown(mc.gameSettings.keyBindChat))
      {
        setCurrentCreativeTab(CreativeTabs.tabAllSearch);
      }
      else
      {
        super.keyTyped(typedChar, keyCode);
      }
    }
    else
    {
      if (field_147057_D)
      {
        field_147057_D = false;
        searchField.setText("");
      }
      
      if (!checkHotbarKeys(keyCode))
      {
        if (searchField.textboxKeyTyped(typedChar, keyCode))
        {
          updateCreativeSearch();
        }
        else
        {
          super.keyTyped(typedChar, keyCode);
        }
      }
    }
  }
  
  private void updateCreativeSearch()
  {
    ContainerCreative var1 = (ContainerCreative)inventorySlots;
    itemList.clear();
    Iterator var2 = Item.itemRegistry.iterator();
    
    while (var2.hasNext())
    {
      Item var3 = (Item)var2.next();
      
      if ((var3 != null) && (var3.getCreativeTab() != null))
      {
        var3.getSubItems(var3, null, itemList);
      }
    }
    
    Enchantment[] var8 = Enchantment.enchantmentsList;
    int var9 = var8.length;
    
    for (int var4 = 0; var4 < var9; var4++)
    {
      Enchantment var5 = var8[var4];
      
      if ((var5 != null) && (type != null))
      {
        Items.enchanted_book.func_92113_a(var5, itemList);
      }
    }
    
    var2 = itemList.iterator();
    String var10 = searchField.getText().toLowerCase();
    
    while (var2.hasNext())
    {
      ItemStack var11 = (ItemStack)var2.next();
      boolean var12 = false;
      Iterator var6 = var11.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips).iterator();
      


      while (var6.hasNext())
      {
        String var7 = (String)var6.next();
        
        if (EnumChatFormatting.getTextWithoutFormattingCodes(var7).toLowerCase().contains(var10))
        {



          var12 = true;
        }
      }
      if (!var12)
      {
        var2.remove();
      }
    }
    



    currentScroll = 0.0F;
    var1.scrollTo(0.0F);
  }
  



  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    CreativeTabs var3 = CreativeTabs.creativeTabArray[selectedTabIndex];
    
    if (var3.drawInForegroundOfTab())
    {
      GlStateManager.disableBlend();
      fontRendererObj.drawString(I18n.format(var3.getTranslatedTabLabel(), new Object[0]), 8, 6, 4210752);
    }
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    if (mouseButton == 0)
    {
      int var4 = mouseX - guiLeft;
      int var5 = mouseY - guiTop;
      CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
      int var7 = var6.length;
      
      for (int var8 = 0; var8 < var7; var8++)
      {
        CreativeTabs var9 = var6[var8];
        
        if (func_147049_a(var9, var4, var5))
        {
          return;
        }
      }
    }
    
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  



  protected void mouseReleased(int mouseX, int mouseY, int state)
  {
    if (state == 0)
    {
      int var4 = mouseX - guiLeft;
      int var5 = mouseY - guiTop;
      CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
      int var7 = var6.length;
      
      for (int var8 = 0; var8 < var7; var8++)
      {
        CreativeTabs var9 = var6[var8];
        
        if (func_147049_a(var9, var4, var5))
        {
          setCurrentCreativeTab(var9);
          return;
        }
      }
    }
    
    super.mouseReleased(mouseX, mouseY, state);
  }
  



  private boolean needsScrollBars()
  {
    return (selectedTabIndex != CreativeTabs.tabInventory.getTabIndex()) && (CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory()) && (((ContainerCreative)inventorySlots).func_148328_e());
  }
  
  private void setCurrentCreativeTab(CreativeTabs p_147050_1_)
  {
    int var2 = selectedTabIndex;
    selectedTabIndex = p_147050_1_.getTabIndex();
    ContainerCreative var3 = (ContainerCreative)inventorySlots;
    dragSplittingSlots.clear();
    itemList.clear();
    p_147050_1_.displayAllReleventItems(itemList);
    
    if (p_147050_1_ == CreativeTabs.tabInventory)
    {
      Container var4 = mc.thePlayer.inventoryContainer;
      
      if (field_147063_B == null)
      {
        field_147063_B = inventorySlots;
      }
      
      inventorySlots = com.google.common.collect.Lists.newArrayList();
      
      for (int var5 = 0; var5 < inventorySlots.size(); var5++)
      {
        CreativeSlot var6 = new CreativeSlot((Slot)inventorySlots.get(var5), var5);
        inventorySlots.add(var6);
        



        if ((var5 >= 5) && (var5 < 9))
        {
          int var7 = var5 - 5;
          int var8 = var7 / 2;
          int var9 = var7 % 2;
          xDisplayPosition = (9 + var8 * 54);
          yDisplayPosition = (6 + var9 * 27);
        }
        else if ((var5 >= 0) && (var5 < 5))
        {
          yDisplayPosition = 63536;
          xDisplayPosition = 63536;
        }
        else if (var5 < inventorySlots.size())
        {
          int var7 = var5 - 9;
          int var8 = var7 % 9;
          int var9 = var7 / 9;
          xDisplayPosition = (9 + var8 * 18);
          
          if (var5 >= 36)
          {
            yDisplayPosition = 112;
          }
          else
          {
            yDisplayPosition = (54 + var9 * 18);
          }
        }
      }
      
      field_147064_C = new Slot(field_147060_v, 0, 173, 112);
      inventorySlots.add(field_147064_C);
    }
    else if (var2 == CreativeTabs.tabInventory.getTabIndex())
    {
      inventorySlots = field_147063_B;
      field_147063_B = null;
    }
    
    if (searchField != null)
    {
      if (p_147050_1_ == CreativeTabs.tabAllSearch)
      {
        searchField.setVisible(true);
        searchField.setCanLoseFocus(false);
        searchField.setFocused(true);
        searchField.setText("");
        updateCreativeSearch();
      }
      else
      {
        searchField.setVisible(false);
        searchField.setCanLoseFocus(true);
        searchField.setFocused(false);
      }
    }
    
    currentScroll = 0.0F;
    var3.scrollTo(0.0F);
  }
  


  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
    int var1 = Mouse.getEventDWheel();
    
    if ((var1 != 0) && (needsScrollBars()))
    {
      int var2 = inventorySlots).itemList.size() / 9 - 5 + 1;
      
      if (var1 > 0)
      {
        var1 = 1;
      }
      
      if (var1 < 0)
      {
        var1 = -1;
      }
      
      currentScroll = ((float)(currentScroll - var1 / var2));
      currentScroll = MathHelper.clamp_float(currentScroll, 0.0F, 1.0F);
      ((ContainerCreative)inventorySlots).scrollTo(currentScroll);
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    boolean var4 = Mouse.isButtonDown(0);
    int var5 = guiLeft;
    int var6 = guiTop;
    int var7 = var5 + 175;
    int var8 = var6 + 18;
    int var9 = var7 + 14;
    int var10 = var8 + 112;
    
    if ((!wasClicking) && (var4) && (mouseX >= var7) && (mouseY >= var8) && (mouseX < var9) && (mouseY < var10))
    {
      isScrolling = needsScrollBars();
    }
    
    if (!var4)
    {
      isScrolling = false;
    }
    
    wasClicking = var4;
    
    if (isScrolling)
    {
      currentScroll = ((mouseY - var8 - 7.5F) / (var10 - var8 - 15.0F));
      currentScroll = MathHelper.clamp_float(currentScroll, 0.0F, 1.0F);
      ((ContainerCreative)inventorySlots).scrollTo(currentScroll);
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
    CreativeTabs[] var11 = CreativeTabs.creativeTabArray;
    int var12 = var11.length;
    
    for (int var13 = 0; var13 < var12; var13++)
    {
      CreativeTabs var14 = var11[var13];
      
      if (renderCreativeInventoryHoveringText(var14, mouseX, mouseY)) {
        break;
      }
    }
    

    if ((field_147064_C != null) && (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) && (isPointInRegion(field_147064_C.xDisplayPosition, field_147064_C.yDisplayPosition, 16, 16, mouseX, mouseY)))
    {
      drawCreativeTabHoveringText(I18n.format("inventory.binSlot", new Object[0]), mouseX, mouseY);
    }
    
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.disableLighting();
  }
  
  protected void renderToolTip(ItemStack itemIn, int x, int y)
  {
    if (selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex())
    {
      List var4 = itemIn.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);
      CreativeTabs var5 = itemIn.getItem().getCreativeTab();
      
      if ((var5 == null) && (itemIn.getItem() == Items.enchanted_book))
      {
        Map var6 = net.minecraft.enchantment.EnchantmentHelper.getEnchantments(itemIn);
        
        if (var6.size() == 1)
        {
          Enchantment var7 = Enchantment.func_180306_c(((Integer)var6.keySet().iterator().next()).intValue());
          CreativeTabs[] var8 = CreativeTabs.creativeTabArray;
          int var9 = var8.length;
          
          for (int var10 = 0; var10 < var9; var10++)
          {
            CreativeTabs var11 = var8[var10];
            
            if (var11.hasRelevantEnchantmentType(type))
            {
              var5 = var11;
              break;
            }
          }
        }
      }
      
      if (var5 != null)
      {
        var4.add(1, EnumChatFormatting.BOLD + EnumChatFormatting.BLUE + I18n.format(var5.getTranslatedTabLabel(), new Object[0]));
      }
      
      for (int var12 = 0; var12 < var4.size(); var12++)
      {
        if (var12 == 0)
        {
          var4.set(var12, getRarityrarityColor + (String)var4.get(var12));
        }
        else
        {
          var4.set(var12, EnumChatFormatting.GRAY + (String)var4.get(var12));
        }
      }
      
      drawHoveringText(var4, x, y);
    }
    else
    {
      super.renderToolTip(itemIn, x, y);
    }
  }
  



  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
    CreativeTabs var4 = CreativeTabs.creativeTabArray[selectedTabIndex];
    CreativeTabs[] var5 = CreativeTabs.creativeTabArray;
    int var6 = var5.length;
    

    for (int var7 = 0; var7 < var6; var7++)
    {
      CreativeTabs var8 = var5[var7];
      mc.getTextureManager().bindTexture(creativeInventoryTabs);
      
      if (var8.getTabIndex() != selectedTabIndex)
      {
        func_147051_a(var8);
      }
    }
    
    mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var4.getBackgroundImageName()));
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    searchField.drawTextBox();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    int var9 = guiLeft + 175;
    var6 = guiTop + 18;
    var7 = var6 + 112;
    mc.getTextureManager().bindTexture(creativeInventoryTabs);
    
    if (var4.shouldHidePlayerInventory())
    {
      drawTexturedModalRect(var9, var6 + (int)((var7 - var6 - 17) * currentScroll), 'è' + (needsScrollBars() ? 0 : 12), 0, 12, 15);
    }
    
    func_147051_a(var4);
    
    if (var4 == CreativeTabs.tabInventory)
    {
      GuiInventory.drawEntityOnScreen(guiLeft + 43, guiTop + 45, 20, guiLeft + 43 - mouseX, guiTop + 45 - 30 - mouseY, mc.thePlayer);
    }
  }
  
  protected boolean func_147049_a(CreativeTabs p_147049_1_, int p_147049_2_, int p_147049_3_)
  {
    int var4 = p_147049_1_.getTabColumn();
    int var5 = 28 * var4;
    byte var6 = 0;
    
    if (var4 == 5)
    {
      var5 = xSize - 28 + 2;
    }
    else if (var4 > 0)
    {
      var5 += var4;
    }
    
    int var7;
    int var7;
    if (p_147049_1_.isTabInFirstRow())
    {
      var7 = var6 - 32;
    }
    else
    {
      var7 = var6 + ySize;
    }
    
    return (p_147049_2_ >= var5) && (p_147049_2_ <= var5 + 28) && (p_147049_3_ >= var7) && (p_147049_3_ <= var7 + 32);
  }
  




  protected boolean renderCreativeInventoryHoveringText(CreativeTabs p_147052_1_, int p_147052_2_, int p_147052_3_)
  {
    int var4 = p_147052_1_.getTabColumn();
    int var5 = 28 * var4;
    byte var6 = 0;
    
    if (var4 == 5)
    {
      var5 = xSize - 28 + 2;
    }
    else if (var4 > 0)
    {
      var5 += var4;
    }
    
    int var7;
    int var7;
    if (p_147052_1_.isTabInFirstRow())
    {
      var7 = var6 - 32;
    }
    else
    {
      var7 = var6 + ySize;
    }
    
    if (isPointInRegion(var5 + 3, var7 + 3, 23, 27, p_147052_2_, p_147052_3_))
    {
      drawCreativeTabHoveringText(I18n.format(p_147052_1_.getTranslatedTabLabel(), new Object[0]), p_147052_2_, p_147052_3_);
      return true;
    }
    

    return false;
  }
  

  protected void func_147051_a(CreativeTabs p_147051_1_)
  {
    boolean var2 = p_147051_1_.getTabIndex() == selectedTabIndex;
    boolean var3 = p_147051_1_.isTabInFirstRow();
    int var4 = p_147051_1_.getTabColumn();
    int var5 = var4 * 28;
    int var6 = 0;
    int var7 = guiLeft + 28 * var4;
    int var8 = guiTop;
    byte var9 = 32;
    
    if (var2)
    {
      var6 += 32;
    }
    
    if (var4 == 5)
    {
      var7 = guiLeft + xSize - 28;
    }
    else if (var4 > 0)
    {
      var7 += var4;
    }
    
    if (var3)
    {
      var8 -= 28;
    }
    else
    {
      var6 += 64;
      var8 += ySize - 4;
    }
    
    GlStateManager.disableLighting();
    drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
    zLevel = 100.0F;
    itemRender.zLevel = 100.0F;
    var7 += 6;
    var8 += 8 + (var3 ? 1 : -1);
    GlStateManager.enableLighting();
    GlStateManager.enableRescaleNormal();
    ItemStack var10 = p_147051_1_.getIconItemStack();
    itemRender.func_180450_b(var10, var7, var8);
    itemRender.func_175030_a(fontRendererObj, var10, var7, var8);
    GlStateManager.disableLighting();
    itemRender.zLevel = 0.0F;
    zLevel = 0.0F;
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (id == 0)
    {
      mc.displayGuiScreen(new net.minecraft.client.gui.achievement.GuiAchievements(this, mc.thePlayer.getStatFileWriter()));
    }
    
    if (id == 1)
    {
      mc.displayGuiScreen(new net.minecraft.client.gui.achievement.GuiStats(this, mc.thePlayer.getStatFileWriter()));
    }
  }
  
  public int func_147056_g()
  {
    return selectedTabIndex;
  }
  
  static class ContainerCreative extends Container
  {
    public List itemList = com.google.common.collect.Lists.newArrayList();
    private static final String __OBFID = "CL_00000753";
    
    public ContainerCreative(EntityPlayer p_i1086_1_)
    {
      InventoryPlayer var2 = inventory;
      

      for (int var3 = 0; var3 < 5; var3++)
      {
        for (int var4 = 0; var4 < 9; var4++)
        {
          addSlotToContainer(new Slot(GuiContainerCreative.field_147060_v, var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
        }
      }
      
      for (var3 = 0; var3 < 9; var3++)
      {
        addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
      }
      
      scrollTo(0.0F);
    }
    
    public boolean canInteractWith(EntityPlayer playerIn)
    {
      return true;
    }
    
    public void scrollTo(float p_148329_1_)
    {
      int var2 = (itemList.size() + 8) / 9 - 5;
      int var3 = (int)(p_148329_1_ * var2 + 0.5D);
      
      if (var3 < 0)
      {
        var3 = 0;
      }
      
      for (int var4 = 0; var4 < 5; var4++)
      {
        for (int var5 = 0; var5 < 9; var5++)
        {
          int var6 = var5 + (var4 + var3) * 9;
          
          if ((var6 >= 0) && (var6 < itemList.size()))
          {
            GuiContainerCreative.field_147060_v.setInventorySlotContents(var5 + var4 * 9, (ItemStack)itemList.get(var6));
          }
          else
          {
            GuiContainerCreative.field_147060_v.setInventorySlotContents(var5 + var4 * 9, null);
          }
        }
      }
    }
    
    public boolean func_148328_e()
    {
      return itemList.size() > 45;
    }
    
    protected void retrySlotClick(int p_75133_1_, int p_75133_2_, boolean p_75133_3_, EntityPlayer p_75133_4_) {}
    
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
      if ((index >= inventorySlots.size() - 9) && (index < inventorySlots.size()))
      {
        Slot var3 = (Slot)inventorySlots.get(index);
        
        if ((var3 != null) && (var3.getHasStack()))
        {
          var3.putStack(null);
        }
      }
      
      return null;
    }
    
    public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_)
    {
      return yDisplayPosition > 90;
    }
    
    public boolean canDragIntoSlot(Slot p_94531_1_)
    {
      return ((inventory instanceof InventoryPlayer)) || ((yDisplayPosition > 90) && (xDisplayPosition <= 162));
    }
  }
  
  class CreativeSlot extends Slot
  {
    private final Slot field_148332_b;
    private static final String __OBFID = "CL_00000754";
    
    public CreativeSlot(Slot p_i46313_2_, int p_i46313_3_)
    {
      super(p_i46313_3_, 0, 0);
      field_148332_b = p_i46313_2_;
    }
    
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
      field_148332_b.onPickupFromSlot(playerIn, stack);
    }
    
    public boolean isItemValid(ItemStack stack)
    {
      return field_148332_b.isItemValid(stack);
    }
    
    public ItemStack getStack()
    {
      return field_148332_b.getStack();
    }
    
    public boolean getHasStack()
    {
      return field_148332_b.getHasStack();
    }
    
    public void putStack(ItemStack p_75215_1_)
    {
      field_148332_b.putStack(p_75215_1_);
    }
    
    public void onSlotChanged()
    {
      field_148332_b.onSlotChanged();
    }
    
    public int getSlotStackLimit()
    {
      return field_148332_b.getSlotStackLimit();
    }
    
    public int func_178170_b(ItemStack p_178170_1_)
    {
      return field_148332_b.func_178170_b(p_178170_1_);
    }
    
    public String func_178171_c()
    {
      return field_148332_b.func_178171_c();
    }
    
    public ItemStack decrStackSize(int p_75209_1_)
    {
      return field_148332_b.decrStackSize(p_75209_1_);
    }
    
    public boolean isHere(IInventory p_75217_1_, int p_75217_2_)
    {
      return field_148332_b.isHere(p_75217_1_, p_75217_2_);
    }
  }
}
