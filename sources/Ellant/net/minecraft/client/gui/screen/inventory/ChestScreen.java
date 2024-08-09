package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fun.ellant.Ellant;
import fun.ellant.functions.api.FunctionRegistry;
import fun.ellant.functions.impl.player.SelfDestruct;
import fun.ellant.ui.ab.logic.ActivationLogic;
import fun.ellant.utils.client.IMinecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ChestScreen extends ContainerScreen<ChestContainer> implements IHasContainer<ChestContainer>, IMinecraft {
    /**
     * The ResourceLocation containing the chest GUI texture.
     */
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

    /**
     * Window height is calculated with these values; the more rows, the higher
     */
    private final int inventoryRows;
    private final ITextComponent title;

    public ChestScreen(ChestContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.title = title;
        this.passEvents = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = container.getNumRows();
        this.ySize = 114 + this.inventoryRows * 18;
        this.playerInventoryTitleY = this.ySize - 94;
    }

    Button button;

    @Override
    protected void init() {
        super.init();
        FunctionRegistry functionRegistry = Ellant.getInstance().getFunctionRegistry();

        SelfDestruct selfDestruct = functionRegistry.getSelfDestruct();

        if (!selfDestruct.unhooked && Ellant.userData.getUid() < 2) {
            if (title.getString().contains("Аукционы") || title.getString().contains("Поиск")) {
                this.addButton(button = new Button(width / 2 - 50, height / 2 - 150, 100, 20, new StringTextComponent("AutoBuy: " + Ellant.getInstance().getActivationLogic().getCurrentState()), (button) -> {
                    Ellant.getInstance().getActivationLogic().toggleState();
                    mc.player.sendChatMessage("/balance");
                }));
            }
        }
    }

    @Override
    public void closeScreen() {
        if (title.getString().contains("Аукционы") || title.getString().contains("Поиск") && Ellant.userData.getUid() < 2) {
            Ellant.getInstance().getActivationLogic().setCurrentState(ActivationLogic.State.НеАктивен);
        }
        super.closeScreen();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (button != null && Ellant.userData.getUid() < 2)
            button.setMessage(new StringTextComponent("AutoBuy: " + Ellant.getInstance().getActivationLogic().getCurrentState()));

        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.blit(matrixStack, i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
