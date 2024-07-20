/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class GuiRepair
extends GuiContainer
implements IContainerListener {
    private static final ResourceLocation ANVIL_RESOURCE = new ResourceLocation("textures/gui/container/anvil.png");
    private final ContainerRepair anvil;
    private GuiTextField nameField;
    private final InventoryPlayer playerInventory;

    public GuiRepair(InventoryPlayer inventoryIn, World worldIn) {
        Minecraft.getMinecraft();
        super(new ContainerRepair(inventoryIn, worldIn, Minecraft.player));
        this.playerInventory = inventoryIn;
        this.anvil = (ContainerRepair)this.inventorySlots;
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        int i = (width - this.xSize) / 2;
        int j = (height - this.ySize) / 2;
        this.nameField = new GuiTextField(0, this.fontRendererObj, i + 62, j + 24, 103, 12);
        this.nameField.setTextColor(-1);
        this.nameField.setDisabledTextColour(-1);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setMaxStringLength(35);
        this.inventorySlots.removeListener(this);
        this.inventorySlots.addListener(this);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.inventorySlots.removeListener(this);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        block8: {
            GlStateManager.disableLighting();
            GlStateManager.disableBlend();
            this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60.0f, 6.0, 0x404040);
            if (this.anvil.maximumCost <= 0) break block8;
            i = 8453920;
            flag = true;
            s = I18n.format("container.repair.cost", new Object[]{this.anvil.maximumCost});
            if (this.anvil.maximumCost < 40) ** GOTO lbl-1000
            if (!Minecraft.player.capabilities.isCreativeMode) {
                s = I18n.format("container.repair.expensive", new Object[0]);
                i = 0xFF6060;
            } else if (!this.anvil.getSlot(2).getHasStack()) {
                flag = false;
            } else if (!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {
                i = 0xFF6060;
            }
            if (flag) {
                j = -16777216 | (i & 0xFCFCFC) >> 2 | i & -16777216;
                k = this.xSize - 8 - this.fontRendererObj.getStringWidth(s);
                l = 67;
                if (this.fontRendererObj.getUnicodeFlag()) {
                    GuiRepair.drawRect(k - 3, 65.0, (double)(this.xSize - 7), 77.0, -16777216);
                    GuiRepair.drawRect(k - 2, 66.0, (double)(this.xSize - 8), 76.0, -12895429);
                } else {
                    this.fontRendererObj.drawString(s, k, 68.0, j);
                    this.fontRendererObj.drawString(s, k + 1, 67.0, j);
                    this.fontRendererObj.drawString(s, k + 1, 68.0, j);
                }
                this.fontRendererObj.drawString(s, k, 67.0, i);
            }
        }
        GlStateManager.enableLighting();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
            this.renameItem();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    private void renameItem() {
        String s = this.nameField.getText();
        Slot slot = this.anvil.getSlot(0);
        if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && s.equals(slot.getStack().getDisplayName())) {
            s = "";
        }
        this.anvil.updateItemName(s);
        Minecraft.player.connection.sendPacket(new CPacketCustomPayload("MC|ItemName", new PacketBuffer(Unpooled.buffer()).writeString(s)));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.nameField.drawTextBox();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(ANVIL_RESOURCE);
        int i = (width - this.xSize) / 2;
        int j = (height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(i + 59, j + 20, 0, this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
        if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack()) {
            this.drawTexturedModalRect(i + 99, j + 45, this.xSize, 0, 28, 21);
        }
    }

    @Override
    public void updateCraftingInventory(Container containerToSend, NonNullList<ItemStack> itemsList) {
        this.sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
    }

    @Override
    public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
        if (slotInd == 0) {
            this.nameField.setText(stack.func_190926_b() ? "" : stack.getDisplayName());
            this.nameField.setEnabled(!stack.func_190926_b());
            if (!stack.func_190926_b()) {
                this.renameItem();
            }
        }
    }

    @Override
    public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {
    }

    @Override
    public void sendAllWindowProperties(Container containerIn, IInventory inventory) {
    }
}

