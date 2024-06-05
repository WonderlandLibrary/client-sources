package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class GuiRepair extends GuiContainer implements ICrafting {
   private ContainerRepair anvil;
   private GuiTextField nameField;
   private static final ResourceLocation anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
   private static final String __OBFID = "CL_00000738";
   private InventoryPlayer playerInventory;

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
      this.fontRendererObj.drawString(I18n.format("container.repair"), 60.0D, 6.0D, 4210752);
      if (this.anvil.maximumCost > 0) {
         int var3 = 8453920;
         boolean var4 = true;
         String var5 = I18n.format("container.repair.cost", this.anvil.maximumCost);
         if (this.anvil.maximumCost >= 40 && !this.mc.thePlayer.capabilities.isCreativeMode) {
            var5 = I18n.format("container.repair.expensive");
            var3 = 16736352;
         } else if (!this.anvil.getSlot(2).getHasStack()) {
            var4 = false;
         } else if (!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {
            var3 = 16736352;
         }

         if (var4) {
            int var6 = -16777216 | (var3 & 16579836) >> 2 | var3 & -16777216;
            int var7 = this.xSize - 8 - this.fontRendererObj.getStringWidth(var5);
            byte var8 = 67;
            if (this.fontRendererObj.getUnicodeFlag()) {
               drawRect((double)(var7 - 3), (double)(var8 - 2), (double)(this.xSize - 7), (double)(var8 + 10), -16777216);
               drawRect((double)(var7 - 2), (double)(var8 - 1), (double)(this.xSize - 8), (double)(var8 + 9), -12895429);
            } else {
               this.fontRendererObj.drawString(var5, (double)var7, (double)(var8 + 1), var6);
               this.fontRendererObj.drawString(var5, (double)(var7 + 1), (double)var8, var6);
               this.fontRendererObj.drawString(var5, (double)(var7 + 1), (double)(var8 + 1), var6);
            }

            this.fontRendererObj.drawString(var5, (double)var7, (double)var8, var3);
         }
      }

      GlStateManager.enableLighting();
   }

   public GuiRepair(InventoryPlayer var1, World var2) {
      super(new ContainerRepair(var1, var2, Minecraft.getMinecraft().thePlayer));
      this.playerInventory = var1;
      this.anvil = (ContainerRepair)this.inventorySlots;
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      this.nameField.mouseClicked(var1, var2, var3);
   }

   public void updateCraftingInventory(Container var1, List var2) {
      this.sendSlotContents(var1, 0, var1.getSlot(0).getStack());
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(anvilResource);
      int var4 = (this.width - this.xSize) / 2;
      int var5 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
      this.drawTexturedModalRect(var4 + 59, var5 + 20, 0, this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
      if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack()) {
         this.drawTexturedModalRect(var4 + 99, var5 + 45, this.xSize, 0, 28, 21);
      }

   }

   public void initGui() {
      super.initGui();
      Keyboard.enableRepeatEvents(true);
      int var1 = (this.width - this.xSize) / 2;
      int var2 = (this.height - this.ySize) / 2;
      this.nameField = new GuiTextField(0, this.fontRendererObj, var1 + 62, var2 + 24, 103, 12);
      this.nameField.setTextColor(-1);
      this.nameField.setDisabledTextColour(-1);
      this.nameField.setEnableBackgroundDrawing(false);
      this.nameField.setMaxStringLength(40);
      this.inventorySlots.removeCraftingFromCrafters(this);
      this.inventorySlots.onCraftGuiOpened(this);
   }

   public void func_175173_a(Container var1, IInventory var2) {
   }

   public void sendProgressBarUpdate(Container var1, int var2, int var3) {
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
      this.nameField.drawTextBox();
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (this.nameField.textboxKeyTyped(var1, var2)) {
         this.renameItem();
      } else {
         super.keyTyped(var1, var2);
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
      Keyboard.enableRepeatEvents(false);
      this.inventorySlots.removeCraftingFromCrafters(this);
   }

   public void sendSlotContents(Container var1, int var2, ItemStack var3) {
      if (var2 == 0) {
         this.nameField.setText(var3 == null ? "" : var3.getDisplayName());
         this.nameField.setEnabled(var3 != null);
         if (var3 != null) {
            this.renameItem();
         }
      }

   }

   private void renameItem() {
      String var1 = this.nameField.getText();
      Slot var2 = this.anvil.getSlot(0);
      if (var2 != null && var2.getHasStack() && !var2.getStack().hasDisplayName() && var1.equals(var2.getStack().getDisplayName())) {
         var1 = "";
      }

      this.anvil.updateItemName(var1);
      this.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|ItemName", (new PacketBuffer(Unpooled.buffer())).writeString(var1)));
   }
}
