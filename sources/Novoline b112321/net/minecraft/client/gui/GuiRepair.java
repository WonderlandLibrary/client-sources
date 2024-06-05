package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import net.aHz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
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

public class GuiRepair extends aHz implements ICrafting {
   private static final ResourceLocation anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
   private ContainerRepair anvil;
   private GuiTextField nameField;
   private InventoryPlayer playerInventory;

   public GuiRepair(InventoryPlayer var1, World var2) {
      super(new ContainerRepair(var1, var2, Minecraft.getInstance().player));
      this.playerInventory = var1;
      this.anvil = (ContainerRepair)this.V;
   }

   public void initGui() {
      super.initGui();
      Keyboard.enableRepeatEvents(true);
      int var1 = (this.width - this.y) / 2;
      int var2 = (this.height - this.ab) / 2;
      this.nameField = new GuiTextField(0, this.fontRendererObj, var1 + 62, var2 + 24, 103, 12);
      this.nameField.setTextColor(-1);
      this.nameField.setDisabledTextColour(-1);
      this.nameField.setEnableBackgroundDrawing(false);
      this.nameField.setMaxStringLength(30);
      this.V.removeCraftingFromCrafters(this);
      this.V.onCraftGuiOpened(this);
   }

   public void onGuiClosed() {
      super.onGuiClosed();
      Keyboard.enableRepeatEvents(false);
      this.V.removeCraftingFromCrafters(this);
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
      this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60.0F, 6.0F, 4210752);
      if(this.anvil.maximumCost > 0) {
         int var3 = 8453920;
         boolean var4 = true;
         String var5 = I18n.format("container.repair.cost", new Object[]{Integer.valueOf(this.anvil.maximumCost)});
         if(this.anvil.maximumCost >= 40 && !this.mc.player.abilities.isCreative()) {
            var5 = I18n.format("container.repair.expensive", new Object[0]);
            var3 = 16736352;
         } else if(!this.anvil.getSlot(2).getHasStack()) {
            var4 = false;
         } else if(!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {
            var3 = 16736352;
         }

         int var6 = -16777216 | (var3 & 16579836) >> 2 | var3 & -16777216;
         int var7 = this.y - 8 - this.fontRendererObj.d(var5);
         byte var8 = 67;
         if(this.fontRendererObj.getUnicodeFlag()) {
            drawRect(var7 - 3, var8 - 2, this.y - 7, var8 + 10, -16777216);
            drawRect(var7 - 2, var8 - 1, this.y - 8, var8 + 9, -12895429);
         } else {
            this.fontRendererObj.drawString(var5, (float)var7, (float)(var8 + 1), var6);
            this.fontRendererObj.drawString(var5, (float)(var7 + 1), (float)var8, var6);
            this.fontRendererObj.drawString(var5, (float)(var7 + 1), (float)(var8 + 1), var6);
         }

         this.fontRendererObj.drawString(var5, (float)var7, (float)var8, var3);
      }

      GlStateManager.enableLighting();
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(this.nameField.textboxKeyTyped(var1, var2)) {
         this.renameItem();
      } else {
         super.keyTyped(var1, var2);
      }

   }

   private void renameItem() {
      String var1 = this.nameField.getText();
      Slot var2 = this.anvil.getSlot(0);
      if(var2.getHasStack() && !var2.getStack().hasDisplayName() && var1.equals(var2.getStack().getDisplayName())) {
         var1 = "";
      }

      this.anvil.updateItemName(var1);
      this.mc.player.connection.b(new C17PacketCustomPayload("MC|ItemName", (new PacketBuffer(Unpooled.buffer())).writeString(var1)));
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      this.nameField.mouseClicked(var1, var2, var3);
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
      this.nameField.drawTextBox();
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(anvilResource);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
      this.drawTexturedModalRect(var4 + 59, var5 + 20, 0, this.ab + (this.anvil.getSlot(0).getHasStack()?0:16), 110, 16);
      if((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack()) {
         this.drawTexturedModalRect(var4 + 99, var5 + 45, this.y, 0, 28, 21);
      }

   }

   public void updateCraftingInventory(Container var1, List var2) {
      this.sendSlotContents(var1, 0, var1.getSlot(0).getStack());
   }

   public void sendSlotContents(Container var1, int var2, ItemStack var3) {
      this.nameField.setText("");
      this.nameField.setEnabled(true);
      this.renameItem();
   }

   public void sendProgressBarUpdate(Container var1, int var2, int var3) {
   }

   public void func_175173_a(Container var1, IInventory var2) {
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
