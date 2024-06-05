package net.minecraft.client.gui.inventory;

import com.google.common.collect.Sets;
import hawk.Client;
import hawk.events.listeners.EventOpenChest;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public abstract class GuiContainer extends GuiScreen {
   protected int guiTop;
   private int touchUpX;
   private Slot currentDragTargetSlot;
   protected int guiLeft;
   private Slot theSlot;
   private boolean isRightMouseClick;
   private ItemStack draggedStack;
   private int dragSplittingButton;
   private boolean ignoreMouseUp;
   private int dragSplittingRemnant;
   private ItemStack shiftClickedSlot;
   private long lastClickTime;
   private long returningStackTime;
   private int touchUpY;
   protected final Set dragSplittingSlots = Sets.newHashSet();
   protected boolean dragSplitting;
   protected int xSize = 176;
   public Container inventorySlots;
   private int dragSplittingLimit;
   private ItemStack returningStack;
   private long dragItemDropDelay;
   protected static final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
   private Slot clickedSlot;
   private Slot lastClickSlot;
   private int lastClickButton;
   protected int ySize = 166;
   private boolean doubleClick;
   private static final String __OBFID = "CL_00000737";
   private Slot returningStackDestSlot;

   protected void mouseReleased(int var1, int var2, int var3) {
      Slot var4 = this.getSlotAtPosition(var1, var2);
      int var5 = this.guiLeft;
      int var6 = this.guiTop;
      boolean var7 = var1 < var5 || var2 < var6 || var1 >= var5 + this.xSize || var2 >= var6 + this.ySize;
      int var8 = -1;
      if (var4 != null) {
         var8 = var4.slotNumber;
      }

      if (var7) {
         var8 = -999;
      }

      Slot var9;
      Iterator var10;
      if (this.doubleClick && var4 != null && var3 == 0 && this.inventorySlots.func_94530_a((ItemStack)null, var4)) {
         if (isShiftKeyDown()) {
            if (var4 != null && var4.inventory != null && this.shiftClickedSlot != null) {
               var10 = this.inventorySlots.inventorySlots.iterator();

               while(var10.hasNext()) {
                  var9 = (Slot)var10.next();
                  if (var9 != null && var9.canTakeStack(this.mc.thePlayer) && var9.getHasStack() && var9.inventory == var4.inventory && Container.canAddItemToSlot(var9, this.shiftClickedSlot, true)) {
                     this.handleMouseClick(var9, var9.slotNumber, var3, 1);
                  }
               }
            }
         } else {
            this.handleMouseClick(var4, var8, var3, 6);
         }

         this.doubleClick = false;
         this.lastClickTime = 0L;
      } else {
         if (this.dragSplitting && this.dragSplittingButton != var3) {
            this.dragSplitting = false;
            this.dragSplittingSlots.clear();
            this.ignoreMouseUp = true;
            return;
         }

         if (this.ignoreMouseUp) {
            this.ignoreMouseUp = false;
            return;
         }

         boolean var11;
         if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (var3 == 0 || var3 == 1) {
               if (this.draggedStack == null && var4 != this.clickedSlot) {
                  this.draggedStack = this.clickedSlot.getStack();
               }

               var11 = Container.canAddItemToSlot(var4, this.draggedStack, false);
               if (var8 != -1 && this.draggedStack != null && var11) {
                  this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, var3, 0);
                  this.handleMouseClick(var4, var8, 0, 0);
                  if (this.mc.thePlayer.inventory.getItemStack() != null) {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, var3, 0);
                     this.touchUpX = var1 - var5;
                     this.touchUpY = var2 - var6;
                     this.returningStackDestSlot = this.clickedSlot;
                     this.returningStack = this.draggedStack;
                     this.returningStackTime = Minecraft.getSystemTime();
                  } else {
                     this.returningStack = null;
                  }
               } else if (this.draggedStack != null) {
                  this.touchUpX = var1 - var5;
                  this.touchUpY = var2 - var6;
                  this.returningStackDestSlot = this.clickedSlot;
                  this.returningStack = this.draggedStack;
                  this.returningStackTime = Minecraft.getSystemTime();
               }

               this.draggedStack = null;
               this.clickedSlot = null;
            }
         } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
            this.handleMouseClick((Slot)null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);
            var10 = this.dragSplittingSlots.iterator();

            while(var10.hasNext()) {
               var9 = (Slot)var10.next();
               this.handleMouseClick(var9, var9.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
            }

            this.handleMouseClick((Slot)null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
         } else if (this.mc.thePlayer.inventory.getItemStack() != null) {
            if (var3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
               this.handleMouseClick(var4, var8, var3, 3);
            } else {
               var11 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
               if (var11) {
                  this.shiftClickedSlot = var4 != null && var4.getHasStack() ? var4.getStack() : null;
               }

               this.handleMouseClick(var4, var8, var3, var11 ? 1 : 0);
            }
         }
      }

      if (this.mc.thePlayer.inventory.getItemStack() == null) {
         this.lastClickTime = 0L;
      }

      this.dragSplitting = false;
   }

   protected void handleMouseClick(Slot var1, int var2, int var3, int var4) {
      if (var1 != null) {
         var2 = var1.slotNumber;
      }

      this.mc.playerController.windowClick(this.inventorySlots.windowId, var2, var3, var4, this.mc.thePlayer);
   }

   private void drawItemStack(ItemStack var1, int var2, int var3, String var4) {
      GlStateManager.translate(0.0F, 0.0F, 32.0F);
      this.zLevel = 200.0F;
      this.itemRender.zLevel = 200.0F;
      this.itemRender.func_180450_b(var1, var2, var3);
      this.itemRender.func_180453_a(this.fontRendererObj, var1, var2, var3 - (this.draggedStack == null ? 0 : 8), var4);
      this.zLevel = 0.0F;
      this.itemRender.zLevel = 0.0F;
   }

   protected abstract void drawGuiContainerBackgroundLayer(float var1, int var2, int var3);

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
   }

   public void initGui() {
      super.initGui();
      this.mc.thePlayer.openContainer = this.inventorySlots;
      this.guiLeft = (this.width - this.xSize) / 2;
      this.guiTop = (this.height - this.ySize) / 2;
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      boolean var4 = var3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
      Slot var5 = this.getSlotAtPosition(var1, var2);
      long var6 = Minecraft.getSystemTime();
      this.doubleClick = this.lastClickSlot == var5 && var6 - this.lastClickTime < 250L && this.lastClickButton == var3;
      this.ignoreMouseUp = false;
      if (var3 == 0 || var3 == 1 || var4) {
         int var8 = this.guiLeft;
         int var9 = this.guiTop;
         boolean var10 = var1 < var8 || var2 < var9 || var1 >= var8 + this.xSize || var2 >= var9 + this.ySize;
         int var11 = -1;
         if (var5 != null) {
            var11 = var5.slotNumber;
         }

         if (var10) {
            var11 = -999;
         }

         if (this.mc.gameSettings.touchscreen && var10 && this.mc.thePlayer.inventory.getItemStack() == null) {
            this.mc.displayGuiScreen((GuiScreen)null);
            return;
         }

         if (var11 != -1) {
            if (this.mc.gameSettings.touchscreen) {
               if (var5 != null && var5.getHasStack()) {
                  this.clickedSlot = var5;
                  this.draggedStack = null;
                  this.isRightMouseClick = var3 == 1;
               } else {
                  this.clickedSlot = null;
               }
            } else if (!this.dragSplitting) {
               if (this.mc.thePlayer.inventory.getItemStack() == null) {
                  if (var3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                     this.handleMouseClick(var5, var11, var3, 3);
                  } else {
                     boolean var12 = var11 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                     byte var13 = 0;
                     if (var12) {
                        this.shiftClickedSlot = var5 != null && var5.getHasStack() ? var5.getStack() : null;
                        var13 = 1;
                     } else if (var11 == -999) {
                        var13 = 4;
                     }

                     this.handleMouseClick(var5, var11, var3, var13);
                  }

                  this.ignoreMouseUp = true;
               } else {
                  this.dragSplitting = true;
                  this.dragSplittingButton = var3;
                  this.dragSplittingSlots.clear();
                  if (var3 == 0) {
                     this.dragSplittingLimit = 0;
                  } else if (var3 == 1) {
                     this.dragSplittingLimit = 1;
                  } else if (var3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                     this.dragSplittingLimit = 2;
                  }
               }
            }
         }
      }

      this.lastClickSlot = var5;
      this.lastClickTime = var6;
      this.lastClickButton = var3;
   }

   private Slot getSlotAtPosition(int var1, int var2) {
      for(int var3 = 0; var3 < this.inventorySlots.inventorySlots.size(); ++var3) {
         Slot var4 = (Slot)this.inventorySlots.inventorySlots.get(var3);
         if (this.isMouseOverSlot(var4, var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   protected boolean isPointInRegion(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.guiLeft;
      int var8 = this.guiTop;
      var5 -= var7;
      var6 -= var8;
      return var5 >= var1 - 1 && var5 < var1 + var3 + 1 && var6 >= var2 - 1 && var6 < var2 + var4 + 1;
   }

   public void onGuiClosed() {
      if (this.mc.thePlayer != null) {
         this.inventorySlots.onContainerClosed(this.mc.thePlayer);
      }

   }

   private void updateDragSplitting() {
      ItemStack var1 = this.mc.thePlayer.inventory.getItemStack();
      if (var1 != null && this.dragSplitting) {
         this.dragSplittingRemnant = var1.stackSize;

         ItemStack var2;
         int var3;
         for(Iterator var4 = this.dragSplittingSlots.iterator(); var4.hasNext(); this.dragSplittingRemnant -= var2.stackSize - var3) {
            Slot var5 = (Slot)var4.next();
            var2 = var1.copy();
            var3 = var5.getStack() == null ? 0 : var5.getStack().stackSize;
            Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, var2, var3);
            if (var2.stackSize > var2.getMaxStackSize()) {
               var2.stackSize = var2.getMaxStackSize();
            }

            if (var2.stackSize > var5.func_178170_b(var2)) {
               var2.stackSize = var5.func_178170_b(var2);
            }
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      EventOpenChest var4 = new EventOpenChest();
      Client.onEvent(var4);
      if (!var4.isCancelled()) {
         this.drawDefaultBackground();
         int var5 = this.guiLeft;
         int var6 = this.guiTop;
         this.drawGuiContainerBackgroundLayer(var3, var1, var2);
         GlStateManager.disableRescaleNormal();
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         super.drawScreen(var1, var2, var3);
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)var5, (float)var6, 0.0F);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableRescaleNormal();
         this.theSlot = null;
         short var7 = 240;
         short var8 = 240;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var7 / 1.0F, (float)var8 / 1.0F);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

         int var9;
         for(int var10 = 0; var10 < this.inventorySlots.inventorySlots.size(); ++var10) {
            Slot var11 = (Slot)this.inventorySlots.inventorySlots.get(var10);
            this.drawSlot(var11);
            if (this.isMouseOverSlot(var11, var1, var2) && var11.canBeHovered()) {
               this.theSlot = var11;
               GlStateManager.disableLighting();
               GlStateManager.disableDepth();
               int var12 = var11.xDisplayPosition;
               var9 = var11.yDisplayPosition;
               GlStateManager.colorMask(true, true, true, false);
               this.drawGradientRect(var12, var9, var12 + 16, var9 + 16, -2130706433, -2130706433);
               GlStateManager.colorMask(true, true, true, true);
               GlStateManager.enableLighting();
               GlStateManager.enableDepth();
            }
         }

         RenderHelper.disableStandardItemLighting();
         this.drawGuiContainerForegroundLayer(var1, var2);
         RenderHelper.enableGUIStandardItemLighting();
         InventoryPlayer var16 = this.mc.thePlayer.inventory;
         ItemStack var17 = this.draggedStack == null ? var16.getItemStack() : this.draggedStack;
         if (var17 != null) {
            byte var18 = 8;
            var9 = this.draggedStack == null ? 8 : 16;
            String var13 = null;
            if (this.draggedStack != null && this.isRightMouseClick) {
               var17 = var17.copy();
               var17.stackSize = MathHelper.ceiling_float_int((float)var17.stackSize / 2.0F);
            } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
               var17 = var17.copy();
               var17.stackSize = this.dragSplittingRemnant;
               if (var17.stackSize == 0) {
                  var13 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.YELLOW).append("0"));
               }
            }

            this.drawItemStack(var17, var1 - var5 - var18, var2 - var6 - var9, var13);
         }

         if (this.returningStack != null) {
            float var19 = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
            if (var19 >= 1.0F) {
               var19 = 1.0F;
               this.returningStack = null;
            }

            var9 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
            int var21 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
            int var14 = this.touchUpX + (int)((float)var9 * var19);
            int var15 = this.touchUpY + (int)((float)var21 * var19);
            this.drawItemStack(this.returningStack, var14, var15, (String)null);
         }

         GlStateManager.popMatrix();
         if (var16.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            ItemStack var20 = this.theSlot.getStack();
            this.renderToolTip(var20, var1, var2);
         }

         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
         RenderHelper.enableStandardItemLighting();
      }
   }

   protected boolean checkHotbarKeys(int var1) {
      if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
         for(int var2 = 0; var2 < 9; ++var2) {
            if (var1 == this.mc.gameSettings.keyBindsHotbar[var2].getKeyCode()) {
               this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, var2, 2);
               return true;
            }
         }
      }

      return false;
   }

   public GuiContainer(Container var1) {
      this.inventorySlots = var1;
      this.ignoreMouseUp = true;
   }

   private boolean isMouseOverSlot(Slot var1, int var2, int var3) {
      return this.isPointInRegion(var1.xDisplayPosition, var1.yDisplayPosition, 16, 16, var2, var3);
   }

   public void updateScreen() {
      super.updateScreen();
      if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
         this.mc.thePlayer.closeScreen();
      }

   }

   private void drawSlot(Slot var1) {
      int var2 = var1.xDisplayPosition;
      int var3 = var1.yDisplayPosition;
      ItemStack var4 = var1.getStack();
      boolean var5 = false;
      boolean var6 = var1 == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
      ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
      String var8 = null;
      if (var1 == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && var4 != null) {
         var4 = var4.copy();
         var4.stackSize /= 2;
      } else if (this.dragSplitting && this.dragSplittingSlots.contains(var1) && var7 != null) {
         if (this.dragSplittingSlots.size() == 1) {
            return;
         }

         if (Container.canAddItemToSlot(var1, var7, true) && this.inventorySlots.canDragIntoSlot(var1)) {
            var4 = var7.copy();
            var5 = true;
            Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, var4, var1.getStack() == null ? 0 : var1.getStack().stackSize);
            if (var4.stackSize > var4.getMaxStackSize()) {
               var8 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.YELLOW).append(var4.getMaxStackSize()));
               var4.stackSize = var4.getMaxStackSize();
            }

            if (var4.stackSize > var1.func_178170_b(var4)) {
               var8 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.YELLOW).append(var1.func_178170_b(var4)));
               var4.stackSize = var1.func_178170_b(var4);
            }
         } else {
            this.dragSplittingSlots.remove(var1);
            this.updateDragSplitting();
         }
      }

      this.zLevel = 100.0F;
      this.itemRender.zLevel = 100.0F;
      if (var4 == null) {
         String var9 = var1.func_178171_c();
         if (var9 != null) {
            TextureAtlasSprite var10 = this.mc.getTextureMapBlocks().getAtlasSprite(var9);
            GlStateManager.disableLighting();
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            this.func_175175_a(var2, var3, var10, 16, 16);
            GlStateManager.enableLighting();
            var6 = true;
         }
      }

      if (!var6) {
         if (var5) {
            drawRect((double)var2, (double)var3, (double)(var2 + 16), (double)(var3 + 16), -2130706433);
         }

         GlStateManager.enableDepth();
         this.itemRender.func_180450_b(var4, var2, var3);
         this.itemRender.func_180453_a(this.fontRendererObj, var4, var2, var3, var8);
      }

      this.itemRender.zLevel = 0.0F;
      this.zLevel = 0.0F;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1 || var2 == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
         this.mc.thePlayer.closeScreen();
      }

      this.checkHotbarKeys(var2);
      if (this.theSlot != null && this.theSlot.getHasStack()) {
         if (var2 == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
            this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
         } else if (var2 == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
            this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
         }
      }

   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      Slot var6 = this.getSlotAtPosition(var1, var2);
      ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
      if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
         if (var3 == 0 || var3 == 1) {
            if (this.draggedStack == null) {
               if (var6 != this.clickedSlot) {
                  this.draggedStack = this.clickedSlot.getStack().copy();
               }
            } else if (this.draggedStack.stackSize > 1 && var6 != null && Container.canAddItemToSlot(var6, this.draggedStack, false)) {
               long var8 = Minecraft.getSystemTime();
               if (this.currentDragTargetSlot == var6) {
                  if (var8 - this.dragItemDropDelay > 500L) {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                     this.handleMouseClick(var6, var6.slotNumber, 1, 0);
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                     this.dragItemDropDelay = var8 + 750L;
                     --this.draggedStack.stackSize;
                  }
               } else {
                  this.currentDragTargetSlot = var6;
                  this.dragItemDropDelay = var8;
               }
            }
         }
      } else if (this.dragSplitting && var6 != null && var7 != null && var7.stackSize > this.dragSplittingSlots.size() && Container.canAddItemToSlot(var6, var7, true) && var6.isItemValid(var7) && this.inventorySlots.canDragIntoSlot(var6)) {
         this.dragSplittingSlots.add(var6);
         this.updateDragSplitting();
      }

   }
}
