package xyz.mathax.mathaxclient.utils.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.render.RenderUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class ContainerTooltipComponent implements TooltipComponent, MatHaxTooltipData {
    private static final Identifier TEXTURE_CONTAINER_BACKGROUND = new MatHaxIdentifier("textures/container.png");

    private final DefaultedList<ItemStack> items;

    private final Color color;

    public ContainerTooltipComponent(DefaultedList<ItemStack> items, Color color) {
        this.items = items;
        this.color = color;
    }

    @Override
    public TooltipComponent getComponent() {
        return this;
    }

    @Override
    public int getHeight() {
        return 67;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 176;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrixStack, ItemRenderer itemRenderer, int z) {
        // Background
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(color.r / 255f, color.g / 255f, color.b / 255f, color.a / 255f);
        RenderSystem.setShaderTexture(0, TEXTURE_CONTAINER_BACKGROUND);
        DrawableHelper.drawTexture(matrixStack, x, y, z, 0, 0, 176, 67, 176, 67);

        //Contents
        int row = 0;
        int i = 0;
        for (ItemStack itemStack : items) {
            RenderUtils.drawItem(itemStack, x + 8 + i * 18, y + 7 + row * 18, true);

            i++;
            if (i >= 9) {
                i = 0;
                row++;
            }
        }
    }
}
