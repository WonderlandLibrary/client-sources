package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
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
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiContainerCreative extends InventoryEffectRenderer {
   private static final String __OBFID = "CL_00000752";
   private boolean field_147057_D;
   private static InventoryBasic field_147060_v = new InventoryBasic("tmp", true, 45);
   private boolean wasClicking;
   private static int selectedTabIndex;
   private boolean isScrolling;
   private float currentScroll;
   private Slot field_147064_C;
   private List field_147063_B;
   private GuiTextField searchField;
   private CreativeCrafting field_147059_E;
   private static final ResourceLocation creativeInventoryTabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      CreativeTabs var3 = CreativeTabs.creativeTabArray[selectedTabIndex];
      if (var3.drawInForegroundOfTab()) {
         GlStateManager.disableBlend();
         this.fontRendererObj.drawString(I18n.format(var3.getTranslatedTabLabel()), 8.0D, 6.0D, 4210752);
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
      if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null) {
         this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
      }

      Keyboard.enableRepeatEvents(false);
   }

   private void updateCreativeSearch() {
      GuiContainerCreative.ContainerCreative var1 = (GuiContainerCreative.ContainerCreative)this.inventorySlots;
      var1.itemList.clear();
      Iterator var2 = Item.itemRegistry.iterator();

      while(var2.hasNext()) {
         Item var3 = (Item)var2.next();
         if (var3 != null && var3.getCreativeTab() != null) {
            var3.getSubItems(var3, (CreativeTabs)null, var1.itemList);
         }
      }

      Enchantment[] var10 = Enchantment.enchantmentsList;
      int var4 = var10.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Enchantment var6 = var10[var5];
         if (var6 != null && var6.type != null) {
            Items.enchanted_book.func_92113_a(var6, var1.itemList);
         }
      }

      var2 = var1.itemList.iterator();
      String var11 = this.searchField.getText().toLowerCase();

      while(var2.hasNext()) {
         ItemStack var12 = (ItemStack)var2.next();
         boolean var7 = false;
         Iterator var8 = var12.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();

         while(var8.hasNext()) {
            String var9 = (String)var8.next();
            if (EnumChatFormatting.getTextWithoutFormattingCodes(var9).toLowerCase().contains(var11)) {
               var7 = true;
               break;
            }
         }

         if (!var7) {
            var2.remove();
         }
      }

      this.currentScroll = 0.0F;
      var1.scrollTo(0.0F);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
         if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
            this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
         } else {
            super.keyTyped(var1, var2);
         }
      } else {
         if (this.field_147057_D) {
            this.field_147057_D = false;
            this.searchField.setText("");
         }

         if (!this.checkHotbarKeys(var2)) {
            if (this.searchField.textboxKeyTyped(var1, var2)) {
               this.updateCreativeSearch();
            } else {
               super.keyTyped(var1, var2);
            }
         }
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.id == 0) {
         this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
      }

      if (var1.id == 1) {
         this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
      }

   }

   protected void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0) {
         int var4 = var1 - this.guiLeft;
         int var5 = var2 - this.guiTop;
         CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            CreativeTabs var9 = var6[var8];
            if (this.func_147049_a(var9, var4, var5)) {
               this.setCurrentCreativeTab(var9);
               return;
            }
         }
      }

      super.mouseReleased(var1, var2, var3);
   }

   protected void renderToolTip(ItemStack var1, int var2, int var3) {
      if (selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex()) {
         List var4 = var1.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
         CreativeTabs var5 = var1.getItem().getCreativeTab();
         if (var5 == null && var1.getItem() == Items.enchanted_book) {
            Map var6 = EnchantmentHelper.getEnchantments(var1);
            if (var6.size() == 1) {
               Enchantment var7 = Enchantment.func_180306_c((Integer)var6.keySet().iterator().next());
               CreativeTabs[] var8 = CreativeTabs.creativeTabArray;
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  CreativeTabs var11 = var8[var10];
                  if (var11.hasRelevantEnchantmentType(var7.type)) {
                     var5 = var11;
                     break;
                  }
               }
            }
         }

         if (var5 != null) {
            var4.add(1, String.valueOf((new StringBuilder()).append(EnumChatFormatting.BOLD).append(EnumChatFormatting.BLUE).append(I18n.format(var5.getTranslatedTabLabel()))));
         }

         for(int var12 = 0; var12 < var4.size(); ++var12) {
            if (var12 == 0) {
               var4.set(var12, String.valueOf((new StringBuilder()).append(var1.getRarity().rarityColor).append((String)var4.get(var12))));
            } else {
               var4.set(var12, String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append((String)var4.get(var12))));
            }
         }

         this.drawHoveringText(var4, var2, var3);
      } else {
         super.renderToolTip(var1, var2, var3);
      }

   }

   public int func_147056_g() {
      return selectedTabIndex;
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         int var4 = var1 - this.guiLeft;
         int var5 = var2 - this.guiTop;
         CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            CreativeTabs var9 = var6[var8];
            if (this.func_147049_a(var9, var4, var5)) {
               return;
            }
         }
      }

      super.mouseClicked(var1, var2, var3);
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0 && this.needsScrollBars()) {
         int var2 = ((GuiContainerCreative.ContainerCreative)this.inventorySlots).itemList.size() / 9 - 5 + 1;
         if (var1 > 0) {
            var1 = 1;
         }

         if (var1 < 0) {
            var1 = -1;
         }

         this.currentScroll = (float)((double)this.currentScroll - (double)var1 / (double)var2);
         this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
         ((GuiContainerCreative.ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
      }

   }

   protected void handleMouseClick(Slot var1, int var2, int var3, int var4) {
      this.field_147057_D = true;
      boolean var5 = var4 == 1;
      var4 = var2 == -999 && var4 == 0 ? 4 : var4;
      ItemStack var6;
      InventoryPlayer var7;
      if (var1 == null && selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && var4 != 5) {
         var7 = this.mc.thePlayer.inventory;
         if (var7.getItemStack() != null) {
            if (var3 == 0) {
               this.mc.thePlayer.dropPlayerItemWithRandomChoice(var7.getItemStack(), true);
               this.mc.playerController.sendPacketDropItem(var7.getItemStack());
               var7.setItemStack((ItemStack)null);
            }

            if (var3 == 1) {
               var6 = var7.getItemStack().splitStack(1);
               this.mc.thePlayer.dropPlayerItemWithRandomChoice(var6, true);
               this.mc.playerController.sendPacketDropItem(var6);
               if (var7.getItemStack().stackSize == 0) {
                  var7.setItemStack((ItemStack)null);
               }
            }
         }
      } else {
         int var8;
         if (var1 == this.field_147064_C && var5) {
            for(var8 = 0; var8 < this.mc.thePlayer.inventoryContainer.getInventory().size(); ++var8) {
               this.mc.playerController.sendSlotPacket((ItemStack)null, var8);
            }
         } else {
            ItemStack var9;
            if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
               if (var1 == this.field_147064_C) {
                  this.mc.thePlayer.inventory.setItemStack((ItemStack)null);
               } else if (var4 == 4 && var1 != null && var1.getHasStack()) {
                  var9 = var1.decrStackSize(var3 == 0 ? 1 : var1.getStack().getMaxStackSize());
                  this.mc.thePlayer.dropPlayerItemWithRandomChoice(var9, true);
                  this.mc.playerController.sendPacketDropItem(var9);
               } else if (var4 == 4 && this.mc.thePlayer.inventory.getItemStack() != null) {
                  this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), true);
                  this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
                  this.mc.thePlayer.inventory.setItemStack((ItemStack)null);
               } else {
                  this.mc.thePlayer.inventoryContainer.slotClick(var1 == null ? var2 : GuiContainerCreative.CreativeSlot.access$0((GuiContainerCreative.CreativeSlot)var1).slotNumber, var3, var4, this.mc.thePlayer);
                  this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
               }
            } else if (var4 != 5 && var1.inventory == field_147060_v) {
               var7 = this.mc.thePlayer.inventory;
               var6 = var7.getItemStack();
               ItemStack var10 = var1.getStack();
               ItemStack var11;
               if (var4 == 2) {
                  if (var10 != null && var3 >= 0 && var3 < 9) {
                     var11 = var10.copy();
                     var11.stackSize = var11.getMaxStackSize();
                     this.mc.thePlayer.inventory.setInventorySlotContents(var3, var11);
                     this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
                  }

                  return;
               }

               if (var4 == 3) {
                  if (var7.getItemStack() == null && var1.getHasStack()) {
                     var11 = var1.getStack().copy();
                     var11.stackSize = var11.getMaxStackSize();
                     var7.setItemStack(var11);
                  }

                  return;
               }

               if (var4 == 4) {
                  if (var10 != null) {
                     var11 = var10.copy();
                     var11.stackSize = var3 == 0 ? 1 : var11.getMaxStackSize();
                     this.mc.thePlayer.dropPlayerItemWithRandomChoice(var11, true);
                     this.mc.playerController.sendPacketDropItem(var11);
                  }

                  return;
               }

               if (var6 != null && var10 != null && var6.isItemEqual(var10)) {
                  if (var3 == 0) {
                     if (var5) {
                        var6.stackSize = var6.getMaxStackSize();
                     } else if (var6.stackSize < var6.getMaxStackSize()) {
                        ++var6.stackSize;
                     }
                  } else if (var6.stackSize <= 1) {
                     var7.setItemStack((ItemStack)null);
                  } else {
                     --var6.stackSize;
                  }
               } else if (var10 != null && var6 == null) {
                  var7.setItemStack(ItemStack.copyItemStack(var10));
                  var6 = var7.getItemStack();
                  if (var5) {
                     var6.stackSize = var6.getMaxStackSize();
                  }
               } else {
                  var7.setItemStack((ItemStack)null);
               }
            } else {
               this.inventorySlots.slotClick(var1 == null ? var2 : var1.slotNumber, var3, var4, this.mc.thePlayer);
               if (Container.getDragEvent(var3) == 2) {
                  for(var8 = 0; var8 < 9; ++var8) {
                     this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45 + var8).getStack(), 36 + var8);
                  }
               } else if (var1 != null) {
                  var9 = this.inventorySlots.getSlot(var1.slotNumber).getStack();
                  this.mc.playerController.sendSlotPacket(var9, var1.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
               }
            }
         }
      }

   }

   static {
      selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
   }

   public void initGui() {
      if (this.mc.playerController.isInCreativeMode()) {
         super.initGui();
         this.buttonList.clear();
         Keyboard.enableRepeatEvents(true);
         this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT);
         this.searchField.setMaxStringLength(15);
         this.searchField.setEnableBackgroundDrawing(false);
         this.searchField.setVisible(false);
         this.searchField.setTextColor(16777215);
         int var1 = selectedTabIndex;
         selectedTabIndex = -1;
         this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[var1]);
         this.field_147059_E = new CreativeCrafting(this.mc);
         this.mc.thePlayer.inventoryContainer.onCraftGuiOpened(this.field_147059_E);
      } else {
         this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
      }

   }

   public void updateScreen() {
      if (!this.mc.playerController.isInCreativeMode()) {
         this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
      }

      this.func_175378_g();
   }

   private void setCurrentCreativeTab(CreativeTabs var1) {
      int var2 = selectedTabIndex;
      selectedTabIndex = var1.getTabIndex();
      GuiContainerCreative.ContainerCreative var3 = (GuiContainerCreative.ContainerCreative)this.inventorySlots;
      this.dragSplittingSlots.clear();
      var3.itemList.clear();
      var1.displayAllReleventItems(var3.itemList);
      if (var1 == CreativeTabs.tabInventory) {
         Container var4 = this.mc.thePlayer.inventoryContainer;
         if (this.field_147063_B == null) {
            this.field_147063_B = var3.inventorySlots;
         }

         var3.inventorySlots = Lists.newArrayList();

         for(int var5 = 0; var5 < var4.inventorySlots.size(); ++var5) {
            GuiContainerCreative.CreativeSlot var6 = new GuiContainerCreative.CreativeSlot(this, (Slot)var4.inventorySlots.get(var5), var5);
            var3.inventorySlots.add(var6);
            int var7;
            int var8;
            int var9;
            if (var5 >= 5 && var5 < 9) {
               var7 = var5 - 5;
               var8 = var7 / 2;
               var9 = var7 % 2;
               var6.xDisplayPosition = 9 + var8 * 54;
               var6.yDisplayPosition = 6 + var9 * 27;
            } else if (var5 >= 0 && var5 < 5) {
               var6.yDisplayPosition = -2000;
               var6.xDisplayPosition = -2000;
            } else if (var5 < var4.inventorySlots.size()) {
               var7 = var5 - 9;
               var8 = var7 % 9;
               var9 = var7 / 9;
               var6.xDisplayPosition = 9 + var8 * 18;
               if (var5 >= 36) {
                  var6.yDisplayPosition = 112;
               } else {
                  var6.yDisplayPosition = 54 + var9 * 18;
               }
            }
         }

         this.field_147064_C = new Slot(field_147060_v, 0, 173, 112);
         var3.inventorySlots.add(this.field_147064_C);
      } else if (var2 == CreativeTabs.tabInventory.getTabIndex()) {
         var3.inventorySlots = this.field_147063_B;
         this.field_147063_B = null;
      }

      if (this.searchField != null) {
         if (var1 == CreativeTabs.tabAllSearch) {
            this.searchField.setVisible(true);
            this.searchField.setCanLoseFocus(false);
            this.searchField.setFocused(true);
            this.searchField.setText("");
            this.updateCreativeSearch();
         } else {
            this.searchField.setVisible(false);
            this.searchField.setCanLoseFocus(true);
            this.searchField.setFocused(false);
         }
      }

      this.currentScroll = 0.0F;
      var3.scrollTo(0.0F);
   }

   protected boolean renderCreativeInventoryHoveringText(CreativeTabs var1, int var2, int var3) {
      int var4 = var1.getTabColumn();
      int var5 = 28 * var4;
      byte var6 = 0;
      if (var4 == 5) {
         var5 = this.xSize - 28 + 2;
      } else if (var4 > 0) {
         var5 += var4;
      }

      int var7;
      if (var1.isTabInFirstRow()) {
         var7 = var6 - 32;
      } else {
         var7 = var6 + this.ySize;
      }

      if (this.isPointInRegion(var5 + 3, var7 + 3, 23, 27, var2, var3)) {
         this.drawCreativeTabHoveringText(I18n.format(var1.getTranslatedTabLabel()), var2, var3);
         return true;
      } else {
         return false;
      }
   }

   static InventoryBasic access$0() {
      return field_147060_v;
   }

   protected boolean func_147049_a(CreativeTabs var1, int var2, int var3) {
      int var4 = var1.getTabColumn();
      int var5 = 28 * var4;
      byte var6 = 0;
      if (var4 == 5) {
         var5 = this.xSize - 28 + 2;
      } else if (var4 > 0) {
         var5 += var4;
      }

      int var7;
      if (var1.isTabInFirstRow()) {
         var7 = var6 - 32;
      } else {
         var7 = var6 + this.ySize;
      }

      return var2 >= var5 && var2 <= var5 + 28 && var3 >= var7 && var3 <= var7 + 32;
   }

   private boolean needsScrollBars() {
      return selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory() && ((GuiContainerCreative.ContainerCreative)this.inventorySlots).func_148328_e();
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      RenderHelper.enableGUIStandardItemLighting();
      CreativeTabs var4 = CreativeTabs.creativeTabArray[selectedTabIndex];
      CreativeTabs[] var5 = CreativeTabs.creativeTabArray;
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         CreativeTabs var8 = var5[var7];
         this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
         if (var8.getTabIndex() != selectedTabIndex) {
            this.func_147051_a(var8);
         }
      }

      this.mc.getTextureManager().bindTexture(new ResourceLocation(String.valueOf((new StringBuilder("textures/gui/container/creative_inventory/tab_")).append(var4.getBackgroundImageName()))));
      this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      this.searchField.drawTextBox();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int var9 = this.guiLeft + 175;
      var6 = this.guiTop + 18;
      var7 = var6 + 112;
      this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
      if (var4.shouldHidePlayerInventory()) {
         this.drawTexturedModalRect(var9, var6 + (int)((float)(var7 - var6 - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
      }

      this.func_147051_a(var4);
      if (var4 == CreativeTabs.tabInventory) {
         GuiInventory.drawEntityOnScreen(this.guiLeft + 43, this.guiTop + 45, 20, (float)(this.guiLeft + 43 - var2), (float)(this.guiTop + 45 - 30 - var3), this.mc.thePlayer);
      }

   }

   protected void func_147051_a(CreativeTabs var1) {
      boolean var2 = var1.getTabIndex() == selectedTabIndex;
      boolean var3 = var1.isTabInFirstRow();
      int var4 = var1.getTabColumn();
      int var5 = var4 * 28;
      int var6 = 0;
      int var7 = this.guiLeft + 28 * var4;
      int var8 = this.guiTop;
      byte var9 = 32;
      if (var2) {
         var6 += 32;
      }

      if (var4 == 5) {
         var7 = this.guiLeft + this.xSize - 28;
      } else if (var4 > 0) {
         var7 += var4;
      }

      if (var3) {
         var8 -= 28;
      } else {
         var6 += 64;
         var8 += this.ySize - 4;
      }

      GlStateManager.disableLighting();
      this.drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
      this.zLevel = 100.0F;
      this.itemRender.zLevel = 100.0F;
      var7 += 6;
      var8 += 8 + (var3 ? 1 : -1);
      GlStateManager.enableLighting();
      GlStateManager.enableRescaleNormal();
      ItemStack var10 = var1.getIconItemStack();
      this.itemRender.func_180450_b(var10, var7, var8);
      this.itemRender.func_175030_a(this.fontRendererObj, var10, var7, var8);
      GlStateManager.disableLighting();
      this.itemRender.zLevel = 0.0F;
      this.zLevel = 0.0F;
   }

   public void drawScreen(int var1, int var2, float var3) {
      boolean var4 = Mouse.isButtonDown(0);
      int var5 = this.guiLeft;
      int var6 = this.guiTop;
      int var7 = var5 + 175;
      int var8 = var6 + 18;
      int var9 = var7 + 14;
      int var10 = var8 + 112;
      if (!this.wasClicking && var4 && var1 >= var7 && var2 >= var8 && var1 < var9 && var2 < var10) {
         this.isScrolling = this.needsScrollBars();
      }

      if (!var4) {
         this.isScrolling = false;
      }

      this.wasClicking = var4;
      if (this.isScrolling) {
         this.currentScroll = ((float)(var2 - var8) - 7.5F) / ((float)(var10 - var8) - 15.0F);
         this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
         ((GuiContainerCreative.ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
      }

      super.drawScreen(var1, var2, var3);
      CreativeTabs[] var11 = CreativeTabs.creativeTabArray;
      int var12 = var11.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         CreativeTabs var14 = var11[var13];
         if (this.renderCreativeInventoryHoveringText(var14, var1, var2)) {
            break;
         }
      }

      if (this.field_147064_C != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && this.isPointInRegion(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, var1, var2)) {
         this.drawCreativeTabHoveringText(I18n.format("inventory.binSlot"), var1, var2);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableLighting();
   }

   public GuiContainerCreative(EntityPlayer var1) {
      super(new GuiContainerCreative.ContainerCreative(var1));
      var1.openContainer = this.inventorySlots;
      this.allowUserInput = true;
      this.ySize = 136;
      this.xSize = 195;
   }

   static class ContainerCreative extends Container {
      private static final String __OBFID = "CL_00000753";
      public List itemList = Lists.newArrayList();

      public boolean func_94530_a(ItemStack var1, Slot var2) {
         return var2.yDisplayPosition > 90;
      }

      public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
         if (var2 >= this.inventorySlots.size() - 9 && var2 < this.inventorySlots.size()) {
            Slot var3 = (Slot)this.inventorySlots.get(var2);
            if (var3 != null && var3.getHasStack()) {
               var3.putStack((ItemStack)null);
            }
         }

         return null;
      }

      protected void retrySlotClick(int var1, int var2, boolean var3, EntityPlayer var4) {
      }

      public ContainerCreative(EntityPlayer var1) {
         InventoryPlayer var2 = var1.inventory;

         int var3;
         for(var3 = 0; var3 < 5; ++var3) {
            for(int var4 = 0; var4 < 9; ++var4) {
               this.addSlotToContainer(new Slot(GuiContainerCreative.access$0(), var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
            }
         }

         for(var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
         }

         this.scrollTo(0.0F);
      }

      public boolean canDragIntoSlot(Slot var1) {
         return var1.inventory instanceof InventoryPlayer || var1.yDisplayPosition > 90 && var1.xDisplayPosition <= 162;
      }

      public void scrollTo(float var1) {
         int var2 = (this.itemList.size() + 8) / 9 - 5;
         int var3 = (int)((double)(var1 * (float)var2) + 0.5D);
         if (var3 < 0) {
            var3 = 0;
         }

         for(int var4 = 0; var4 < 5; ++var4) {
            for(int var5 = 0; var5 < 9; ++var5) {
               int var6 = var5 + (var4 + var3) * 9;
               if (var6 >= 0 && var6 < this.itemList.size()) {
                  GuiContainerCreative.access$0().setInventorySlotContents(var5 + var4 * 9, (ItemStack)this.itemList.get(var6));
               } else {
                  GuiContainerCreative.access$0().setInventorySlotContents(var5 + var4 * 9, (ItemStack)null);
               }
            }
         }

      }

      public boolean func_148328_e() {
         return this.itemList.size() > 45;
      }

      public boolean canInteractWith(EntityPlayer var1) {
         return true;
      }
   }

   class CreativeSlot extends Slot {
      private static final String __OBFID = "CL_00000754";
      final GuiContainerCreative this$0;
      private final Slot field_148332_b;

      public boolean getHasStack() {
         return this.field_148332_b.getHasStack();
      }

      public ItemStack decrStackSize(int var1) {
         return this.field_148332_b.decrStackSize(var1);
      }

      public ItemStack getStack() {
         return this.field_148332_b.getStack();
      }

      public boolean isHere(IInventory var1, int var2) {
         return this.field_148332_b.isHere(var1, var2);
      }

      public void onPickupFromSlot(EntityPlayer var1, ItemStack var2) {
         this.field_148332_b.onPickupFromSlot(var1, var2);
      }

      public CreativeSlot(GuiContainerCreative var1, Slot var2, int var3) {
         super(var2.inventory, var3, 0, 0);
         this.this$0 = var1;
         this.field_148332_b = var2;
      }

      public void putStack(ItemStack var1) {
         this.field_148332_b.putStack(var1);
      }

      public String func_178171_c() {
         return this.field_148332_b.func_178171_c();
      }

      public void onSlotChanged() {
         this.field_148332_b.onSlotChanged();
      }

      static Slot access$0(GuiContainerCreative.CreativeSlot var0) {
         return var0.field_148332_b;
      }

      public boolean isItemValid(ItemStack var1) {
         return this.field_148332_b.isItemValid(var1);
      }

      public int getSlotStackLimit() {
         return this.field_148332_b.getSlotStackLimit();
      }

      public int func_178170_b(ItemStack var1) {
         return this.field_148332_b.func_178170_b(var1);
      }
   }
}
