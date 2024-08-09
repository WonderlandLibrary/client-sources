/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.AbstractRepairScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CRenameItemPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AnvilScreen
extends AbstractRepairScreen<RepairContainer> {
    private static final ResourceLocation ANVIL_RESOURCE = new ResourceLocation("textures/gui/container/anvil.png");
    private static final ITextComponent field_243333_B = new TranslationTextComponent("container.repair.venusfr");
    private TextFieldWidget nameField;

    public AnvilScreen(RepairContainer repairContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(repairContainer, playerInventory, iTextComponent, ANVIL_RESOURCE);
        this.titleX = 60;
    }

    @Override
    public void tick() {
        super.tick();
        this.nameField.tick();
    }

    @Override
    protected void initFields() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        int n = (this.width - this.xSize) / 2;
        int n2 = (this.height - this.ySize) / 2;
        this.nameField = new TextFieldWidget(this.font, n + 62, n2 + 24, 103, 12, new TranslationTextComponent("container.repair"));
        this.nameField.setCanLoseFocus(true);
        this.nameField.setTextColor(-1);
        this.nameField.setDisabledTextColour(-1);
        this.nameField.setEnableBackgroundDrawing(true);
        this.nameField.setMaxStringLength(35);
        this.nameField.setResponder(this::renameItem);
        this.children.add(this.nameField);
        this.setFocusedDefault(this.nameField);
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.nameField.getText();
        this.init(minecraft, n, n2);
        this.nameField.setText(string);
    }

    @Override
    public void onClose() {
        super.onClose();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.player.closeScreen();
        }
        return !this.nameField.keyPressed(n, n2, n3) && !this.nameField.canWrite() ? super.keyPressed(n, n2, n3) : true;
    }

    private void renameItem(String string) {
        if (!string.isEmpty()) {
            String string2 = string;
            Slot slot = ((RepairContainer)this.container).getSlot(0);
            if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && string.equals(slot.getStack().getDisplayName().getString())) {
                string2 = "";
            }
            ((RepairContainer)this.container).updateItemName(string2);
            this.minecraft.player.connection.sendPacket(new CRenameItemPacket(string2));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int n, int n2) {
        RenderSystem.disableBlend();
        super.drawGuiContainerForegroundLayer(matrixStack, n, n2);
        int n3 = ((RepairContainer)this.container).getMaximumCost();
        if (n3 > 0) {
            ITextComponent iTextComponent;
            int n4 = 8453920;
            if (n3 >= 40 && !this.minecraft.player.abilities.isCreativeMode) {
                iTextComponent = field_243333_B;
                n4 = 0xFF6060;
            } else if (!((RepairContainer)this.container).getSlot(2).getHasStack()) {
                iTextComponent = null;
            } else {
                iTextComponent = new TranslationTextComponent("container.repair.cost", n3);
                if (!((RepairContainer)this.container).getSlot(2).canTakeStack(this.playerInventory.player)) {
                    n4 = 0xFF6060;
                }
            }
            if (iTextComponent != null) {
                int n5 = this.xSize - 8 - this.font.getStringPropertyWidth(iTextComponent) - 2;
                int n6 = 69;
                AnvilScreen.fill(matrixStack, n5 - 2, 67, this.xSize - 8, 79, 0x4F000000);
                this.font.func_243246_a(matrixStack, iTextComponent, n5, 69.0f, n4);
            }
        }
    }

    @Override
    public void renderNameField(MatrixStack matrixStack, int n, int n2, float f) {
        this.nameField.render(matrixStack, n, n2, f);
    }

    @Override
    public void sendSlotContents(Container container, int n, ItemStack itemStack) {
        if (n == 0) {
            this.nameField.setText(itemStack.isEmpty() ? "" : itemStack.getDisplayName().getString());
            this.nameField.setEnabled(!itemStack.isEmpty());
            this.setListener(this.nameField);
        }
    }
}

