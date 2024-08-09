package mathax.client.utils.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import mathax.client.utils.render.RenderUtils;
import mathax.client.utils.render.color.Color;
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
    private static final Identifier TEXTURE_CONTAINER_BACKGROUND = new Identifier("mathax", "textures/container/container.png");

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
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z) {

        // Background
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(color.r / 255f, color.g / 255f, color.b / 255f, color.a / 255f);
        RenderSystem.setShaderTexture(0, TEXTURE_CONTAINER_BACKGROUND);
        DrawableHelper.drawTexture(matrices, x, y, z, 0, 0, 176, 67, 176, 67);

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
