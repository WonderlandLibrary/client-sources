package xyz.mathax.mathaxclient.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class WItem extends WWidget {
    protected ItemStack itemStack;

    public WItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    protected void onCalculateSize() {
        double scale = theme.scale(32);

        width = scale;
        height = scale;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!itemStack.isEmpty()) {
            renderer.post(() -> {
                double scale = theme.scale(2);

                MatrixStack matrixStack = RenderSystem.getModelViewStack();

                matrixStack.push();
                matrixStack.scale((float) scale, (float) scale, 1);
                matrixStack.translate(x / scale, y / scale, 0);

                mc.getItemRenderer().renderGuiItemIcon(itemStack, 0, 0);

                matrixStack.pop();
            });
        }
    }

    public void set(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
