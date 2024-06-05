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
import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.gui.inventory.GuiContainerCreative$ContainerCreative;
import net.minecraft.client.gui.inventory.GuiContainerCreative$CreativeSlot;
import net.minecraft.client.gui.inventory.GuiInventory;
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

   public GuiContainerCreative(EntityPlayer var1) {
      super(new GuiContainerCreative$ContainerCreative(var1));
      var1.openContainer = this.V;
      this.ab = 136;
      this.y = 195;
   }

   public void updateScreen() {
      if(!this.mc.at.h()) {
         this.mc.displayGuiScreen(new GuiInventory(this.mc.player));
      }

      this.updateActivePotionEffects();
   }

   protected void handleMouseClick(Slot var1, int var2, int var3, int var4) {
      this.field_147057_D = true;
      boolean var5 = var4 == 1;
      var4 = var2 == -999?4:var4;
      if(selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && var4 != 5) {
         InventoryPlayer var15 = this.mc.player.inventory;
         if(var15.getItemStack() != null) {
            this.mc.player.dropPlayerItemWithRandomChoice(var15.getItemStack(), true);
            this.mc.at.a(var15.getItemStack());
            var15.setItemStack((ItemStack)null);
            if(var3 == 1) {
               ItemStack var17 = var15.getItemStack().splitStack(1);
               this.mc.player.dropPlayerItemWithRandomChoice(var17, true);
               this.mc.at.a(var17);
               if(var15.getItemStack().stackSize == 0) {
                  var15.setItemStack((ItemStack)null);
               }
            }
         }
      } else if(var1 == this.field_147064_C) {
         for(int var6 = 0; var6 < this.mc.player.inventoryContainer.getInventory().size(); ++var6) {
            this.mc.at.a((ItemStack)null, var6);
         }
      } else if(selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
         if(var1 == this.field_147064_C) {
            this.mc.player.inventory.setItemStack((ItemStack)null);
         } else if(var4 == 4 && var1.getHasStack()) {
            ItemStack var11 = var1.decrStackSize(1);
            this.mc.player.dropPlayerItemWithRandomChoice(var11, true);
            this.mc.at.a(var11);
         } else if(var4 == 4 && this.mc.player.inventory.getItemStack() != null) {
            this.mc.player.dropPlayerItemWithRandomChoice(this.mc.player.inventory.getItemStack(), true);
            this.mc.at.a(this.mc.player.inventory.getItemStack());
            this.mc.player.inventory.setItemStack((ItemStack)null);
         } else {
            this.mc.player.inventoryContainer.slotClick(var2, var3, var4, this.mc.player);
            this.mc.player.inventoryContainer.detectAndSendChanges();
         }
      } else if(var4 != 5 && var1.inventory == field_147060_v) {
         InventoryPlayer var14 = this.mc.player.inventory;
         ItemStack var7 = var14.getItemStack();
         ItemStack var8 = var1.getStack();
         if(var4 == 2) {
            if(var3 < 9) {
               ItemStack var19 = var8.copy();
               var19.stackSize = var19.getMaxStackSize();
               this.mc.player.inventory.setInventorySlotContents(var3, var19);
               this.mc.player.inventoryContainer.detectAndSendChanges();
            }

            return;
         }

         if(var4 == 3) {
            if(var14.getItemStack() == null && var1.getHasStack()) {
               ItemStack var18 = var1.getStack().copy();
               var18.stackSize = var18.getMaxStackSize();
               var14.setItemStack(var18);
            }

            return;
         }

         if(var4 == 4) {
            ItemStack var9 = var8.copy();
            var9.stackSize = 1;
            this.mc.player.dropPlayerItemWithRandomChoice(var9, true);
            this.mc.at.a(var9);
            return;
         }

         if(var7.isItemEqual(var8)) {
            var7.stackSize = var7.getMaxStackSize();
         } else {
            var14.setItemStack(ItemStack.d(var8));
            var7 = var14.getItemStack();
            var7.stackSize = var7.getMaxStackSize();
         }
      } else {
         this.V.slotClick(var2, var3, var4, this.mc.player);
         if(Container.getDragEvent(var3) == 2) {
            for(int var12 = 0; var12 < 9; ++var12) {
               this.mc.at.a(this.V.getSlot(45 + var12).getStack(), 36 + var12);
            }
         } else {
            ItemStack var13 = this.V.getSlot(var1.slotNumber).getStack();
            this.mc.at.a(var13, var1.slotNumber - this.V.inventorySlots.size() + 9 + 36);
         }
      }

   }

   protected void updateActivePotionEffects() {
      int var1 = this.R;
      super.updateActivePotionEffects();
      if(this.searchField != null && this.R != var1) {
         this.searchField.xPosition = (float)(this.R + 82);
      }

   }

   public void initGui() {
      if(this.mc.at.h()) {
         super.initGui();
         this.buttonList.clear();
         Keyboard.enableRepeatEvents(true);
         this.searchField = new GuiTextField(0, this.fontRendererObj, this.R + 82, this.W + 6, 89, this.fontRendererObj.getHeight());
         this.searchField.setMaxStringLength(15);
         this.searchField.setEnableBackgroundDrawing(false);
         this.searchField.setVisible(false);
         this.searchField.setTextColor(16777215);
         int var1 = selectedTabIndex;
         selectedTabIndex = -1;
         this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[var1]);
         this.field_147059_E = new CreativeCrafting(this.mc);
         this.mc.player.inventoryContainer.onCraftGuiOpened(this.field_147059_E);
      } else {
         this.mc.displayGuiScreen(new GuiInventory(this.mc.player));
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
      if(this.mc.player != null && this.mc.player.inventory != null) {
         this.mc.player.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
      }

      Keyboard.enableRepeatEvents(false);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
         if(GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
            this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
         } else {
            super.keyTyped(var1, var2);
         }
      } else {
         if(this.field_147057_D) {
            this.field_147057_D = false;
            this.searchField.setText("");
         }

         if(!this.b(var2)) {
            if(this.searchField.textboxKeyTyped(var1, var2)) {
               this.updateCreativeSearch();
            } else {
               super.keyTyped(var1, var2);
            }
         }
      }

   }

   private void updateCreativeSearch() {
      GuiContainerCreative$ContainerCreative var1 = (GuiContainerCreative$ContainerCreative)this.V;
      var1.itemList.clear();

      for(Item var3 : Item.itemRegistry) {
         if(var3.getCreativeTab() != null) {
            var3.getSubItems(var3, (CreativeTabs)null, var1.itemList);
         }
      }

      for(Enchantment var5 : Enchantment.enchantmentsBookList) {
         if(var5.type != null) {
            Items.enchanted_book.getAll(var5, var1.itemList);
         }
      }

      Iterator var9 = var1.itemList.iterator();

      for(String var11 = this.searchField.getText().toLowerCase(); var9.hasNext(); var9.remove()) {
         ItemStack var12 = (ItemStack)var9.next();
         boolean var13 = false;

         for(String var7 : var12.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips)) {
            if(EnumChatFormatting.a(var7).toLowerCase().contains(var11)) {
               var13 = true;
               break;
            }
         }
      }

      this.currentScroll = 0.0F;
      var1.scrollTo(0.0F);
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      CreativeTabs var3 = CreativeTabs.creativeTabArray[selectedTabIndex];
      if(var3.drawInForegroundOfTab()) {
         GlStateManager.disableBlend();
         this.fontRendererObj.drawString(I18n.format(var3.getTranslatedTabLabel(), new Object[0]), 8.0F, 6.0F, 4210752);
      }

   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      int var4 = var1 - this.R;
      int var5 = var2 - this.W;

      for(CreativeTabs var9 : CreativeTabs.creativeTabArray) {
         if(this.func_147049_a(var9, var4, var5)) {
            return;
         }
      }

      super.mouseClicked(var1, var2, var3);
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      int var4 = var1 - this.R;
      int var5 = var2 - this.W;

      for(CreativeTabs var9 : CreativeTabs.creativeTabArray) {
         if(this.func_147049_a(var9, var4, var5)) {
            this.setCurrentCreativeTab(var9);
            return;
         }
      }

      super.mouseReleased(var1, var2, var3);
   }

   private boolean needsScrollBars() {
      return selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory() && ((GuiContainerCreative$ContainerCreative)this.V).func_148328_e();
   }

   private void setCurrentCreativeTab(CreativeTabs var1) {
      int var2 = selectedTabIndex;
      selectedTabIndex = var1.getTabIndex();
      GuiContainerCreative$ContainerCreative var3 = (GuiContainerCreative$ContainerCreative)this.V;
      this.X.clear();
      var3.itemList.clear();
      var1.displayAllReleventItems(var3.itemList);
      if(var1 == CreativeTabs.tabInventory) {
         Container var4 = this.mc.player.inventoryContainer;
         if(this.field_147063_B == null) {
            this.field_147063_B = var3.inventorySlots;
         }

         var3.inventorySlots = Lists.newArrayList();

         for(int var5 = 0; var5 < var4.inventorySlots.size(); ++var5) {
            GuiContainerCreative$CreativeSlot var6 = new GuiContainerCreative$CreativeSlot(this, (Slot)var4.inventorySlots.get(var5), var5);
            var3.inventorySlots.add(var6);
            if(var5 >= 5 && var5 < 9) {
               int var10 = var5 - 5;
               int var11 = var10 / 2;
               int var12 = var10 % 2;
               var6.xDisplayPosition = 9 + var11 * 54;
               var6.yDisplayPosition = 6 + var12 * 27;
            } else if(var5 < 5) {
               var6.yDisplayPosition = -2000;
               var6.xDisplayPosition = -2000;
            } else if(var5 < var4.inventorySlots.size()) {
               int var7 = var5 - 9;
               int var8 = var7 % 9;
               int var9 = var7 / 9;
               var6.xDisplayPosition = 9 + var8 * 18;
               if(var5 >= 36) {
                  var6.yDisplayPosition = 112;
               } else {
                  var6.yDisplayPosition = 54 + var9 * 18;
               }
            }
         }

         this.field_147064_C = new Slot(field_147060_v, 0, 173, 112);
         var3.inventorySlots.add(this.field_147064_C);
      } else if(var2 == CreativeTabs.tabInventory.getTabIndex()) {
         var3.inventorySlots = this.field_147063_B;
         this.field_147063_B = null;
      }

      if(this.searchField != null) {
         if(var1 == CreativeTabs.tabAllSearch) {
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

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if(this.needsScrollBars()) {
         int var2 = ((GuiContainerCreative$ContainerCreative)this.V).itemList.size() / 9 - 5;
         var1 = 1;
         var1 = -1;
         this.currentScroll = (float)((double)this.currentScroll - (double)var1 / (double)var2);
         this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
         ((GuiContainerCreative$ContainerCreative)this.V).scrollTo(this.currentScroll);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      boolean var4 = Mouse.isButtonDown(0);
      int var5 = this.R;
      int var6 = this.W;
      int var7 = var5 + 175;
      int var8 = var6 + 18;
      int var9 = var7 + 14;
      int var10 = var8 + 112;
      if(!this.wasClicking && var1 >= var7 && var2 >= var8 && var1 < var9 && var2 < var10) {
         this.isScrolling = this.needsScrollBars();
      }

      this.isScrolling = false;
      this.wasClicking = var4;
      if(this.isScrolling) {
         this.currentScroll = ((float)(var2 - var8) - 7.5F) / ((float)(var10 - var8) - 15.0F);
         this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
         ((GuiContainerCreative$ContainerCreative)this.V).scrollTo(this.currentScroll);
      }

      super.drawScreen(var1, var2, var3);

      for(CreativeTabs var14 : CreativeTabs.creativeTabArray) {
         if(this.renderCreativeInventoryHoveringText(var14, var1, var2)) {
            break;
         }
      }

      if(this.field_147064_C != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && this.a(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, var1, var2)) {
         this.drawCreativeTabHoveringText(I18n.format("inventory.binSlot", new Object[0]), var1, var2);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableLighting();
   }

   protected void renderToolTip(ItemStack var1, int var2, int var3) {
      if(selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex()) {
         List var4 = var1.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips);
         CreativeTabs var5 = var1.getItem().getCreativeTab();
         if(var1.getItem() == Items.enchanted_book) {
            Map var6 = EnchantmentHelper.getEnchantments(var1);
            if(var6.size() == 1) {
               Enchantment var7 = Enchantment.getEnchantmentById(((Integer)var6.keySet().iterator().next()).intValue());

               for(CreativeTabs var11 : CreativeTabs.creativeTabArray) {
                  if(var11.hasRelevantEnchantmentType(var7.type)) {
                     var5 = var11;
                     break;
                  }
               }
            }
         }

         var4.add(1, "" + EnumChatFormatting.BOLD + EnumChatFormatting.BLUE + I18n.format(var5.getTranslatedTabLabel(), new Object[0]));

         for(int var12 = 0; var12 < var4.size(); ++var12) {
            var4.set(var12, var1.getRarity().rarityColor + (String)var4.get(var12));
         }

         this.drawHoveringText(var4, var2, var3);
      } else {
         super.renderToolTip(var1, var2, var3);
      }

   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      RenderHelper.enableGUIStandardItemLighting();
      CreativeTabs var4 = CreativeTabs.creativeTabArray[selectedTabIndex];

      for(CreativeTabs var8 : CreativeTabs.creativeTabArray) {
         this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
         if(var8.getTabIndex() != selectedTabIndex) {
            this.func_147051_a(var8);
         }
      }

      this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var4.getBackgroundImageName()));
      this.drawTexturedModalRect(this.R, this.W, 0, 0, this.y, this.ab);
      this.searchField.drawTextBox();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int var9 = this.R + 175;
      int var10 = this.W + 18;
      int var11 = var10 + 112;
      this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
      if(var4.shouldHidePlayerInventory()) {
         this.drawTexturedModalRect(var9, var10 + (int)((float)(var11 - var10 - 17) * this.currentScroll), 232 + (this.needsScrollBars()?0:12), 0, 12, 15);
      }

      this.func_147051_a(var4);
      if(var4 == CreativeTabs.tabInventory) {
         GuiInventory.drawEntityOnScreen(this.R + 43, this.W + 45, 20, (float)(this.R + 43 - var2), (float)(this.W + 45 - 30 - var3), this.mc.player);
      }

   }

   protected boolean func_147049_a(CreativeTabs var1, int var2, int var3) {
      int var4 = var1.getTabColumn();
      int var5 = 28 * var4;
      int var6 = 0;
      if(var4 == 5) {
         var5 = this.y - 28 + 2;
      } else {
         var5 = var5 + var4;
      }

      if(var1.isTabInFirstRow()) {
         var6 = var6 - 32;
      } else {
         var6 = var6 + this.ab;
      }

      return var2 >= var5 && var2 <= var5 + 28 && var3 >= var6 && var3 <= var6 + 32;
   }

   protected boolean renderCreativeInventoryHoveringText(CreativeTabs var1, int var2, int var3) {
      int var4 = var1.getTabColumn();
      int var5 = 28 * var4;
      int var6 = 0;
      if(var4 == 5) {
         var5 = this.y - 28 + 2;
      } else {
         var5 = var5 + var4;
      }

      if(var1.isTabInFirstRow()) {
         var6 = var6 - 32;
      } else {
         var6 = var6 + this.ab;
      }

      if(this.a(var5 + 3, var6 + 3, 23, 27, var2, var3)) {
         this.drawCreativeTabHoveringText(I18n.format(var1.getTranslatedTabLabel(), new Object[0]), var2, var3);
         return true;
      } else {
         return false;
      }
   }

   protected void func_147051_a(CreativeTabs var1) {
      boolean var2 = var1.getTabIndex() == selectedTabIndex;
      boolean var3 = var1.isTabInFirstRow();
      int var4 = var1.getTabColumn();
      int var5 = var4 * 28;
      int var6 = 0;
      int var7 = this.R + 28 * var4;
      int var8 = this.W;
      byte var9 = 32;
      var6 = var6 + 32;
      if(var4 == 5) {
         var7 = this.R + this.y - 28;
      } else {
         var7 = var7 + var4;
      }

      var8 = var8 - 28;
      GlStateManager.disableLighting();
      this.drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
      this.zLevel = 100.0F;
      this.itemRender.zLevel = 100.0F;
      var7 = var7 + 6;
      var8 = var8 + 8 + 1;
      GlStateManager.enableLighting();
      GlStateManager.enableRescaleNormal();
      ItemStack var10 = var1.getIconItemStack();
      this.itemRender.renderItemAndEffectIntoGUI(var10, (float)var7, (float)var8);
      this.itemRender.renderItemOverlays(this.fontRendererObj, var10, var7, var8);
      GlStateManager.disableLighting();
      this.itemRender.zLevel = 0.0F;
      this.zLevel = 0.0F;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.id == 0) {
         this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.player.getStatFileWriter()));
      }

      if(var1.id == 1) {
         this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
      }

   }

   public int getSelectedTabIndex() {
      return selectedTabIndex;
   }

   static InventoryBasic access$100() {
      return field_147060_v;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
