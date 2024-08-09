/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.ui.ab.logic.ActivationLogic;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.venusfr;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ChestScreen
extends ContainerScreen<ChestContainer>
implements IHasContainer<ChestContainer>,
IMinecraft {
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private final int inventoryRows;
    private final ITextComponent title;
    Button button;

    public ChestScreen(ChestContainer chestContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(chestContainer, playerInventory, iTextComponent);
        this.title = iTextComponent;
        this.passEvents = false;
        int n = 222;
        int n2 = 114;
        this.inventoryRows = chestContainer.getNumRows();
        this.ySize = 114 + this.inventoryRows * 18;
        this.playerInventoryTitleY = this.ySize - 94;
    }

    @Override
    protected void init() {
        super.init();
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        if (venusfr.userData.getUid() < 2 && (this.title.getString().contains("\u0410\u0443\u043a\u0446\u0438\u043e\u043d\u044b") || this.title.getString().contains("\u041f\u043e\u0438\u0441\u043a"))) {
            this.button = new Button(this.width / 2 - 50, this.height / 2 - 150, 100, 20, new StringTextComponent("AutoBuy: " + venusfr.getInstance().getActivationLogic().getCurrentState()), ChestScreen::lambda$init$0);
            this.addButton(this.button);
        }
    }

    @Override
    public void closeScreen() {
        if (this.title.getString().contains("\u0410\u0443\u043a\u0446\u0438\u043e\u043d\u044b") || this.title.getString().contains("\u041f\u043e\u0438\u0441\u043a") && venusfr.userData.getUid() < 2) {
            venusfr.getInstance().getActivationLogic().setCurrentState(ActivationLogic.State.INACTIVE);
        }
        super.closeScreen();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.button != null && venusfr.userData.getUid() < 2) {
            this.button.setMessage(new StringTextComponent("AutoBuy: " + venusfr.getInstance().getActivationLogic().getCurrentState()));
        }
        this.renderBackground(matrixStack);
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int n3 = (this.width - this.xSize) / 2;
        int n4 = (this.height - this.ySize) / 2;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.blit(matrixStack, n3, n4 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

    private static void lambda$init$0(Button button) {
        venusfr.getInstance().getActivationLogic().toggleState();
        ChestScreen.mc.player.sendChatMessage("/balance");
    }
}

