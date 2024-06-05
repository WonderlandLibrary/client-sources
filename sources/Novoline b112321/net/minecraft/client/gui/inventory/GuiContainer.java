package net.minecraft.client.gui.inventory;

import com.google.common.collect.Sets;
import java.awt.Color;
import java.io.IOException;
import java.util.Set;
import net.AD;
import net.Ju;
import net.UW;
import net.aZU;
import net.asx;
import net.av6;
import net.avP;
import net.avU;
import net.ava;
import net.gZ;
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
   protected final Set dragSplittingSlots = Sets.newHashSet();
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
   private AD I;
   private AD Y;
   private AD J;
   private AD ae;
   aZU G = new aZU(1001, 4, 5, 120, 21, "Disable Killaura");
   aZU B = new aZU(1003, 4, 32, 120, 21, "Disable Auto Armor");
   aZU ad = new aZU(1002, 4, 59, 120, 21, "Disable Inventory Manager");
   aZU F = new aZU(1004, 4, 86, 120, 21, "Disable Chest Stealer");
   private final Color af = new Color(131, 45, 241);
   private final Color Q = new Color(30, 30, 30, 125);
   private final Color M = new Color(10, 10, 10, 125);

   public GuiContainer(Container var1) {
      this.inventorySlots = var1;
      this.ignoreMouseUp = true;
   }

   public Color a() {
      return gZ.g().m()?this.af:Ju.a(((ava)gZ.g().d().b(ava.class)).z(), 0.8D);
   }

   public void initGui() {
      super.initGui();
      this.mc.thePlayer.openContainer = this.inventorySlots;
      this.guiLeft = (this.width - this.xSize) / 2;
      this.guiTop = (this.height - this.ySize) / 2;
      this.buttonList.add(this.G);
      this.buttonList.add(this.B);
      this.buttonList.add(this.F);
      this.buttonList.add(this.ad);
      this.I = new AD(3.0F, 4.0F, 124.0F, 26.0F, this.a().getRGB());
      this.Y = new AD(3.0F, 31.0F, 124.0F, 53.0F, this.a().getRGB());
      this.J = new AD(3.0F, 58.0F, 124.0F, 80.0F, this.a().getRGB());
      this.ae = new AD(3.0F, 85.0F, 124.0F, 107.0F, this.a().getRGB());
   }

   private int a(Color var1, Color var2) {
      int var3 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * 1.0F);
      int var4 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * 1.0F);
      int var5 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * 1.0F);
      int var6 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * 1.0F);

      try {
         return (new Color(var3, var4, var5, var6)).getRGB();
      } catch (Exception var8) {
         return -1;
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      int var4 = this.guiLeft;
      int var5 = this.guiTop;
      this.drawGuiContainerBackgroundLayer(var3, var1, var2);
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      super.drawScreen(var1, var2, var3);
      RenderHelper.enableGUIStandardItemLighting();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)var4, (float)var5, 0.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableRescaleNormal();
      this.theSlot = null;
      short var6 = 240;
      short var7 = 240;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6, (float)var7);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      for(int var8 = 0; var8 < this.inventorySlots.inventorySlots.size(); ++var8) {
         Slot var9 = (Slot)this.inventorySlots.inventorySlots.get(var8);
         this.drawSlot(var9);
         if(this.isMouseOverSlot(var9, var1, var2) && var9.canBeHovered()) {
            this.theSlot = var9;
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int var10 = var9.xDisplayPosition;
            int var11 = var9.yDisplayPosition;
            GlStateManager.colorMask(true, true, true, false);
            this.a((float)var10, (float)var11, (float)(var10 + 16), (float)(var11 + 16), -2130706433, -2130706433);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
         }
      }

      RenderHelper.disableStandardItemLighting();
      this.drawGuiContainerForegroundLayer(var1, var2);
      RenderHelper.enableGUIStandardItemLighting();
      InventoryPlayer var15 = this.mc.thePlayer.bJ;
      ItemStack var16 = this.draggedStack == null?var15.getItemStack():this.draggedStack;
      byte var17 = 8;
      int var20 = this.draggedStack == null?8:16;
      String var12 = null;
      if(this.draggedStack != null && this.isRightMouseClick) {
         var16 = var16.copy();
         var16.stackSize = MathHelper.ceiling_float_int((float)var16.stackSize / 2.0F);
      } else if(this.dragSplitting && this.dragSplittingSlots.size() > 1) {
         var16 = var16.copy();
         var16.stackSize = this.dragSplittingRemnant;
         if(var16.stackSize == 0) {
            var12 = "" + EnumChatFormatting.YELLOW + "0";
         }
      }

      this.drawItemStack(var16, var1 - var4 - var17, var2 - var5 - var20, var12);
      if(this.returningStack != null) {
         float var18 = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
         if(var18 >= 1.0F) {
            var18 = 1.0F;
            this.returningStack = null;
         }

         var20 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
         int var22 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
         int var13 = this.touchUpX + (int)((float)var20 * var18);
         int var14 = this.touchUpY + (int)((float)var22 * var18);
         this.drawItemStack(this.returningStack, var13, var14, (String)null);
      }

      GlStateManager.popMatrix();
      if(var15.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
         ItemStack var19 = this.theSlot.getStack();
         this.renderToolTip(var19, var1, var2);
      }

      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
      this.G.drawButton(this.mc, var1, var2);
      this.G.b(this.a(this.G.a(var1, var2)?this.Q.brighter():this.Q, this.G.a(var1, var2)?this.M.brighter():this.M));
      this.G.c(this.G.a(var1, var2)?Color.WHITE.getRGB():Color.WHITE.darker().getRGB());
      this.B.drawButton(this.mc, var1, var2);
      this.B.b(this.a(this.B.a(var1, var2)?this.Q.brighter():this.Q, this.B.a(var1, var2)?this.M.brighter():this.M));
      this.B.c(this.B.a(var1, var2)?Color.WHITE.getRGB():Color.WHITE.darker().getRGB());
      this.F.drawButton(this.mc, var1, var2);
      this.F.b(this.a(this.F.a(var1, var2)?this.Q.brighter():this.Q, this.F.a(var1, var2)?this.M.brighter():this.M));
      this.F.c(this.F.a(var1, var2)?Color.WHITE.getRGB():Color.WHITE.darker().getRGB());
      this.ad.drawButton(this.mc, var1, var2);
      this.ad.b(this.a(this.ad.a(var1, var2)?this.Q.brighter():this.Q, this.ad.a(var1, var2)?this.M.brighter():this.M));
      this.ad.c(this.ad.a(var1, var2)?Color.WHITE.getRGB():Color.WHITE.darker().getRGB());
      this.I.a(this.a().getRGB());
      this.I.a();
      this.Y.a(this.a().getRGB());
      this.Y.a();
      this.J.a(this.a().getRGB());
      this.J.a();
      this.ae.a(this.a().getRGB());
      this.ae.a();
   }

   private void drawItemStack(ItemStack var1, int var2, int var3, String var4) {
      GlStateManager.translate(0.0F, 0.0F, 32.0F);
      this.zLevel = 200.0F;
      this.s.a = 200.0F;
      this.s.b(var1, (float)var2, (float)var3);
      this.s.a(this.fontRendererObj, var1, var2, var3 - (this.draggedStack == null?0:8), var4);
      this.zLevel = 0.0F;
      this.s.a = 0.0F;
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
   }

   protected abstract void drawGuiContainerBackgroundLayer(float var1, int var2, int var3);

   private void drawSlot(Slot var1) {
      int var2 = var1.xDisplayPosition;
      int var3 = var1.yDisplayPosition;
      ItemStack var4 = var1.getStack();
      boolean var5 = false;
      if(var1 == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick) {
         boolean var12 = true;
      } else {
         boolean var10000 = false;
      }

      ItemStack var7 = this.mc.thePlayer.bJ.getItemStack();
      String var8 = null;
      if(var1 == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick) {
         var4 = var4.copy();
         var4.stackSize /= 2;
      } else if(this.dragSplitting && this.dragSplittingSlots.contains(var1)) {
         if(this.dragSplittingSlots.size() == 1) {
            return;
         }

         if(Container.canAddItemToSlot(var1, var7, true) && this.inventorySlots.canDragIntoSlot(var1)) {
            var4 = var7.copy();
            var5 = true;
            Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, var4, var1.getStack() == null?0:var1.getStack().stackSize);
            if(var4.stackSize > var4.u()) {
               var8 = EnumChatFormatting.YELLOW + "" + var4.u();
               var4.stackSize = var4.u();
            }

            if(var4.stackSize > var1.getItemStackLimit(var4)) {
               var8 = EnumChatFormatting.YELLOW + "" + var1.getItemStackLimit(var4);
               var4.stackSize = var1.getItemStackLimit(var4);
            }
         } else {
            this.dragSplittingSlots.remove(var1);
            this.updateDragSplitting();
         }
      }

      this.zLevel = 100.0F;
      this.s.a = 100.0F;
      String var9 = var1.getSlotTexture();
      TextureAtlasSprite var10 = this.mc.getTextureMapBlocks().getAtlasSprite(var9);
      GlStateManager.disableLighting();
      this.mc.ab().a(TextureMap.locationBlocksTexture);
      this.drawTexturedModalRect(var2, var3, var10, 16, 16);
      GlStateManager.enableLighting();
      boolean var6 = true;
      drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
      GlStateManager.enableDepth();
      this.s.b(var4, (float)var2, (float)var3);
      this.s.a(this.fontRendererObj, var4, var2, var3, var8);
      this.s.a = 0.0F;
      this.zLevel = 0.0F;
   }

   private void updateDragSplitting() {
      ItemStack var1 = this.mc.thePlayer.bJ.getItemStack();
      if(this.dragSplitting) {
         this.dragSplittingRemnant = var1.stackSize;

         for(Slot var3 : this.dragSplittingSlots) {
            ItemStack var4 = var1.copy();
            int var5 = var3.getStack() == null?0:var3.getStack().stackSize;
            Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, var4, var5);
            if(var4.stackSize > var4.u()) {
               var4.stackSize = var4.u();
            }

            if(var4.stackSize > var3.getItemStackLimit(var4)) {
               var4.stackSize = var3.getItemStackLimit(var4);
            }

            this.dragSplittingRemnant -= var4.stackSize - var5;
         }
      }

   }

   private Slot getSlotAtPosition(int var1, int var2) {
      for(int var3 = 0; var3 < this.inventorySlots.inventorySlots.size(); ++var3) {
         Slot var4 = (Slot)this.inventorySlots.inventorySlots.get(var3);
         if(this.isMouseOverSlot(var4, var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      UW var4 = gZ.g().d();
      if(this.G.a(var1, var2)) {
         ((asx)var4.b(asx.class)).u();
         this.G.playPressSound(this.mc.getSoundHandler());
      } else if(this.B.a(var1, var2)) {
         ((avP)var4.b(avP.class)).u();
         this.B.playPressSound(this.mc.getSoundHandler());
      } else if(this.F.a(var1, var2)) {
         ((avU)var4.b(avU.class)).u();
         this.F.playPressSound(this.mc.getSoundHandler());
      } else if(this.ad.a(var1, var2)) {
         ((av6)var4.b(av6.class)).u();
         this.ad.playPressSound(this.mc.getSoundHandler());
      }

      boolean var14 = var3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
      Slot var5 = this.getSlotAtPosition(var1, var2);
      long var6 = Minecraft.getSystemTime();
      this.doubleClick = this.lastClickSlot == var5 && var6 - this.lastClickTime < 250L && this.lastClickButton == var3;
      this.ignoreMouseUp = false;
      if(var3 != 1) {
         ;
      }

      int var8 = this.guiLeft;
      int var9 = this.guiTop;
      if(var1 >= var8 && var2 >= var9 && var1 < var8 + this.xSize && var2 < var9 + this.ySize) {
         boolean var18 = false;
      } else {
         boolean var10000 = true;
      }

      int var11 = -1;
      var11 = var5.slotNumber;
      var11 = -999;
      if(this.mc.gameSettings.touchscreen && this.mc.thePlayer.bJ.getItemStack() == null) {
         this.mc.displayGuiScreen((GuiScreen)null);
      } else {
         if(var11 != -1) {
            if(this.mc.gameSettings.touchscreen) {
               if(var5.getHasStack()) {
                  this.clickedSlot = var5;
                  this.draggedStack = null;
                  this.isRightMouseClick = var3 == 1;
               } else {
                  this.clickedSlot = null;
               }
            } else if(!this.dragSplitting) {
               if(this.mc.thePlayer.bJ.getItemStack() == null) {
                  if(var3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                     this.handleMouseClick(var5, var11, var3, 3);
                  } else {
                     if(var11 == -999 || !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)) {
                        boolean var20 = false;
                     } else {
                        boolean var19 = true;
                     }

                     byte var13 = 0;
                     this.shiftClickedSlot = var5.getHasStack()?var5.getStack():null;
                     var13 = 1;
                     this.handleMouseClick(var5, var11, var3, var13);
                  }

                  this.ignoreMouseUp = true;
               } else {
                  this.dragSplitting = true;
                  this.dragSplittingButton = var3;
                  this.dragSplittingSlots.clear();
                  this.dragSplittingLimit = 0;
               }
            }
         }

         this.lastClickSlot = var5;
         this.lastClickTime = var6;
         this.lastClickButton = var3;
      }
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
      Slot var6 = this.getSlotAtPosition(var1, var2);
      ItemStack var7 = this.mc.thePlayer.bJ.getItemStack();
      if(this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
         if(var3 == 1) {
            if(this.draggedStack == null) {
               if(var6 != this.clickedSlot && this.clickedSlot.getStack() != null) {
                  this.draggedStack = this.clickedSlot.getStack().copy();
               }
            } else if(this.draggedStack.stackSize > 1 && Container.canAddItemToSlot(var6, this.draggedStack, false)) {
               long var8 = Minecraft.getSystemTime();
               if(this.currentDragTargetSlot == var6) {
                  if(var8 - this.dragItemDropDelay > 500L) {
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
      } else if(this.dragSplitting && var7.stackSize > this.dragSplittingSlots.size() && Container.canAddItemToSlot(var6, var7, true) && var6.isItemValid(var7) && this.inventorySlots.canDragIntoSlot(var6)) {
         this.dragSplittingSlots.add(var6);
         this.updateDragSplitting();
      }

   }

   protected void mouseReleased(int var1, int var2, int var3) {
      Slot var4 = this.getSlotAtPosition(var1, var2);
      int var5 = this.guiLeft;
      int var6 = this.guiTop;
      if(var1 >= var5 && var2 >= var6 && var1 < var5 + this.xSize && var2 < var6 + this.ySize) {
         boolean var16 = false;
      } else {
         boolean var10000 = true;
      }

      int var8 = -1;
      var8 = var4.slotNumber;
      var8 = -999;
      if(this.doubleClick && this.inventorySlots.canMergeSlot((ItemStack)null, var4)) {
         if(isShiftKeyDown()) {
            if(var4.inventory != null && this.shiftClickedSlot != null) {
               for(Slot var15 : this.inventorySlots.inventorySlots) {
                  if(var15.canTakeStack(this.mc.thePlayer) && var15.getHasStack() && var15.inventory == var4.inventory && Container.canAddItemToSlot(var15, this.shiftClickedSlot, true)) {
                     this.handleMouseClick(var15, var15.slotNumber, var3, 1);
                  }
               }
            }
         } else {
            this.handleMouseClick(var4, var8, var3, 6);
         }

         this.doubleClick = false;
         this.lastClickTime = 0L;
      } else {
         if(this.dragSplitting && this.dragSplittingButton != var3) {
            this.dragSplitting = false;
            this.dragSplittingSlots.clear();
            this.ignoreMouseUp = true;
            return;
         }

         if(this.ignoreMouseUp) {
            this.ignoreMouseUp = false;
            return;
         }

         if(this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if(var3 == 1) {
               if(this.draggedStack == null && var4 != this.clickedSlot) {
                  this.draggedStack = this.clickedSlot.getStack();
               }

               boolean var13 = Container.canAddItemToSlot(var4, this.draggedStack, false);
               if(var8 != -1 && this.draggedStack != null) {
                  this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, var3, 0);
                  this.handleMouseClick(var4, var8, 0, 0);
                  if(this.mc.thePlayer.bJ.getItemStack() != null) {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, var3, 0);
                     this.touchUpX = var1 - var5;
                     this.touchUpY = var2 - var6;
                     this.returningStackDestSlot = this.clickedSlot;
                     this.returningStack = this.draggedStack;
                     this.returningStackTime = Minecraft.getSystemTime();
                  } else {
                     this.returningStack = null;
                  }
               } else if(this.draggedStack != null) {
                  this.touchUpX = var1 - var5;
                  this.touchUpY = var2 - var6;
                  this.returningStackDestSlot = this.clickedSlot;
                  this.returningStack = this.draggedStack;
                  this.returningStackTime = Minecraft.getSystemTime();
               }

               this.draggedStack = null;
               this.clickedSlot = null;
            }
         } else if(this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
            this.handleMouseClick((Slot)null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);

            for(Slot var10 : this.dragSplittingSlots) {
               this.handleMouseClick(var10, var10.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
            }

            this.handleMouseClick((Slot)null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
         } else if(this.mc.thePlayer.bJ.getItemStack() != null) {
            if(var3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
               this.handleMouseClick(var4, var8, var3, 3);
            } else {
               if(var8 == -999 || !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)) {
                  boolean var18 = false;
               } else {
                  boolean var17 = true;
               }

               this.shiftClickedSlot = var4.getHasStack()?var4.getStack():null;
               this.handleMouseClick(var4, var8, var3, 1);
            }
         }
      }

      if(this.mc.thePlayer.bJ.getItemStack() == null) {
         this.lastClickTime = 0L;
      }

      this.dragSplitting = false;
   }

   private boolean isMouseOverSlot(Slot var1, int var2, int var3) {
      return this.isPointInRegion(var1.xDisplayPosition, var1.yDisplayPosition, 16, 16, var2, var3);
   }

   protected boolean isPointInRegion(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.guiLeft;
      int var8 = this.guiTop;
      var5 = var5 - var7;
      var6 = var6 - var8;
      return var5 >= var1 - 1 && var5 < var1 + var3 + 1 && var6 >= var2 - 1 && var6 < var2 + var4 + 1;
   }

   protected void handleMouseClick(Slot var1, int var2, int var3, int var4) {
      var2 = var1.slotNumber;
      this.mc.playerController.a(this.inventorySlots.windowId, var2, var3, var4, this.mc.thePlayer);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(var2 == 1 || var2 == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
         this.mc.thePlayer.closeScreen();
      }

      this.checkHotbarKeys(var2);
      if(this.theSlot != null && this.theSlot.getHasStack()) {
         if(var2 == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
            this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
         } else if(var2 == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
            this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, isCtrlKeyDown()?1:0, 4);
         }
      }

   }

   protected boolean checkHotbarKeys(int var1) {
      if(this.mc.thePlayer.bJ.getItemStack() == null && this.theSlot != null) {
         for(int var2 = 0; var2 < 9; ++var2) {
            if(var1 == this.mc.gameSettings.keyBindsHotbar[var2].getKeyCode()) {
               this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, var2, 2);
               return true;
            }
         }
      }

      return false;
   }

   public void onGuiClosed() {
      if(this.mc.thePlayer != null) {
         this.inventorySlots.onContainerClosed(this.mc.thePlayer);
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void updateScreen() {
      super.updateScreen();
      if(!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
         this.mc.thePlayer.closeScreen();
      }

   }

   private boolean a(int var1, int var2) {
      return var1 >= 5 && var1 <= 100 && var2 >= 100 && var2 <= 110;
   }

   private boolean b(int var1, int var2) {
      return var1 >= 5 && var1 <= 100 && var2 >= 115 && var2 <= 125;
   }

   private boolean d(int var1, int var2) {
      return var1 >= 5 && var1 <= 100 && var2 >= 130 && var2 <= 140;
   }

   private static IOException b(IOException var0) {
      return var0;
   }
}
