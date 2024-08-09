package xyz.mathax.mathaxclient.utils.tooltip;

import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class BannerTooltipComponent implements MatHaxTooltipData, TooltipComponent {
    private final ItemStack banner;

    private final ModelPart bannerField;

    public BannerTooltipComponent(ItemStack banner) {
        this.banner = banner;
        this.bannerField = mc.getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");
    }

    @Override
    public TooltipComponent getComponent() {
        return this;
    }

    @Override
    public int getHeight() {
        return 32 * 5 - 2;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 16 * 5;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrixStack, ItemRenderer itemRenderer, int z) {
        DiffuseLighting.disableGuiDepthLighting();
        matrixStack.push();
        matrixStack.translate(x + 8, y + 8, z);

        matrixStack.push();
        matrixStack.translate(0.5, 16, 0);
        matrixStack.scale(6, -6, 1);
        matrixStack.scale(2, -2, -2);
        matrixStack.push();
        matrixStack.translate(2.5, 8.5, 0);
        matrixStack.scale(5, 5, 5);
        VertexConsumerProvider.Immediate immediate = mc.getBufferBuilders().getEntityVertexConsumers();
        this.bannerField.pitch = 0f;
        this.bannerField.pivotY = -32f;
        BannerBlockEntityRenderer.renderCanvas(matrixStack, immediate, 0xF000F0, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, BannerBlockEntity.getPatternsFromNbt(((BannerItem) this.banner.getItem()).getColor(), BannerBlockEntity.getPatternListNbt(this.banner)));
        matrixStack.pop();
        matrixStack.pop();
        immediate.draw();
        matrixStack.pop();
        DiffuseLighting.enableGuiDepthLighting();
    }
}
