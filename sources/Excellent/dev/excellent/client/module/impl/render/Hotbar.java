package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.RenderHotbarEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.render.RenderUtil;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.systems.RenderSystem;
import net.optifine.CustomItems;

@ModuleInfo(name = "Hotbar", description = "Отображает кастомный хотбар.", category = Category.RENDER)
public class Hotbar extends Module {
    private final Animation scrollAnimation = new Animation(Easing.LINEAR, 100);
    private final Listener<RenderHotbarEvent> onRender = event -> {
        if (mc.player == null) return;
        event.cancel();
        MatrixStack matrixStack = event.getMatrix();
        float partialTicks = event.getPartialTicks();
        PlayerEntity playerentity = mc.ingameGUI.getRenderViewPlayer();

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(IngameGui.WIDGETS_TEX_PATH);
        ItemStack itemstack = playerentity.getHeldItemOffhand();
        HandSide handside = playerentity.getPrimaryHand().opposite();
        int i = mc.ingameGUI.scaledWidth / 2;
        int j = mc.ingameGUI.getBlitOffset();

        mc.ingameGUI.setBlitOffset(-90);

        scrollAnimation.run(i - 91 + playerentity.inventory.currentItem * 20);

        RenderUtil.renderClientRect(matrixStack, i - 91, mc.ingameGUI.scaledHeight - 22, 182, 22, false, 0, 128);
        RenderUtil.renderClientRect(matrixStack, (float) scrollAnimation.getValue() + 1, mc.ingameGUI.scaledHeight - 21, 21, 20, false, 0, 128);

        if (!itemstack.isEmpty()) {
            if (handside == HandSide.LEFT) {
                RenderUtil.renderClientRect(matrixStack, i - 91 - 7 - 22, mc.ingameGUI.scaledHeight - 22, 22, 22, false, 0, 128);
            } else {
                RenderUtil.renderClientRect(matrixStack, i + 91 + 7, mc.ingameGUI.scaledHeight - 22, 22, 22, false, 0, 128);
            }
        }

        mc.ingameGUI.setBlitOffset(j);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        CustomItems.setRenderOffHand(false);

        for (int i1 = 0; i1 < 9; ++i1) {
            int j1 = i - 90 + i1 * 20 + 2;
            int k1 = mc.ingameGUI.scaledHeight - 16 - 3;
            mc.ingameGUI.renderHotbarItem(j1, k1, partialTicks, playerentity, playerentity.inventory.mainInventory.get(i1));
        }

        if (!itemstack.isEmpty()) {
            CustomItems.setRenderOffHand(true);
            int i2 = mc.ingameGUI.scaledHeight - 16 - 3;

            if (handside == HandSide.LEFT) {
                mc.ingameGUI.renderHotbarItem(i - 91 - 26, i2, partialTicks, playerentity, itemstack);
            } else {
                mc.ingameGUI.renderHotbarItem(i + 91 + 10, i2, partialTicks, playerentity, itemstack);
            }

            CustomItems.setRenderOffHand(false);
        }

        if (mc.gameSettings.attackIndicator == AttackIndicatorStatus.HOTBAR) {
            float f = mc.player.getCooledAttackStrength(0.0F);

            if (f < 1.0F) {
                int j2 = mc.ingameGUI.scaledHeight - 20;
                int k2 = i + 91 + 6;

                if (handside == HandSide.RIGHT) {
                    k2 = i - 91 - 22;
                }

                mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
                int l1 = (int) (f * 19.0F);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.ingameGUI.blit(matrixStack, k2, j2, 0, 94, 18, 18);
                mc.ingameGUI.blit(matrixStack, k2, j2 + 18 - l1, 18, 112 - l1, 18, l1);
            }
        }

        RenderSystem.disableRescaleNormal();
        RenderSystem.disableBlend();
    };

}
