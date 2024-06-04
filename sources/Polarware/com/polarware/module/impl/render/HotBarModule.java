package com.polarware.module.impl.render;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.util.math.MathUtil;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.util.vector.Vector2d;
import com.polarware.value.impl.DragValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import util.time.StopWatch;

import java.awt.*;

@ModuleInfo(name = "module.render.hotbar.name", description = "module.render.hotbar.description", category = Category.RENDER, autoEnabled = true)
public class HotBarModule extends Module {

    private DragValue structure = new DragValue("", this, new Vector2d(200, 200), false, true);
    private float rPosX;
    private final StopWatch stopwatch = new StopWatch();
    private ScaledResolution scaledResolution;

    @EventLink()
    public final Listener<Render2DEvent> onPreMotionEvent = event -> {
        scaledResolution = event.getScaledResolution();

        NORMAL_POST_RENDER_RUNNABLES.add(() -> {
            this.renderHotBar(scaledResolution, new Color(0, 0, 0, 100));
        });

        NORMAL_POST_RENDER_RUNNABLES.add(() -> {
            this.renderHotBarItems(scaledResolution, event.getPartialTicks());
        });

        NORMAL_RENDER_RUNNABLES.add(this::renderHotBarScroll);

        NORMAL_BLUR_RUNNABLES.add(() -> {
            this.renderHotBar(scaledResolution, Color.BLACK);
        });

        NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
            this.renderHotBar(scaledResolution, Color.BLACK);
        });
    };

    private void renderHotBarItems(final ScaledResolution scaledResolution, final float partialTicks) {
        if (mc.getRenderViewEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer) mc.getRenderViewEntity();

            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();

            for (int index = 0; index < 9; ++index) {
                final int x = scaledResolution.getScaledWidth() / 2 - 90 + index * 20 + 2;
                final int y = scaledResolution.getScaledHeight() - 23;
                renderHotBarItem(index, x, y, partialTicks, entityplayer);
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    private void renderHotBar(final ScaledResolution scaledResolution, final Color color) {
        if (mc.getRenderViewEntity() instanceof EntityPlayer) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            structure.position = new Vector2d((int) (scaledResolution.getScaledWidth() / 2.0F - 91), (int) (scaledResolution.getScaledHeight() - 26));
            structure.scale = new Vector2d(182, 22);

            RenderUtil.rectangle(structure.position.x, structure.position.y, structure.scale.x, structure.scale.y, color);
        }
    }

    private void renderHotBarScroll() {
        if (scaledResolution == null || !(mc.getRenderViewEntity() instanceof EntityPlayer)) return;

        final EntityPlayer entityplayer = mc.thePlayer;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        final int i = scaledResolution.getScaledWidth() / 2;

        final InterfaceModule interfaceModule = Client.INSTANCE.getModuleManager().get(InterfaceModule.class);

        if (interfaceModule == null || interfaceModule.smoothHotBar.getValue()) {
            for (int time = 0; time < stopwatch.getElapsedTime(); ++time) {
                rPosX = MathUtil.lerp(rPosX, i - 91 - 1 + entityplayer.inventory.currentItem * 20, 0.055f);
            }

            stopwatch.reset();
        } else {
            rPosX = i - 91 - 1 + entityplayer.inventory.currentItem * 20;
        }

        RenderUtil.rectangle(rPosX + 1, scaledResolution.getScaledHeight() - 26, 22,
                22, ColorUtil.withAlpha(getTheme().getFirstColor(), 165));
    }

    private void renderHotBarItem(final int index, final int xPos, final int yPos, final float partialTicks, final EntityPlayer entityPlayer) {
        final ItemStack itemstack = entityPlayer.inventory.mainInventory[index];
        final RenderItem itemRenderer = mc.getRenderItem();

        if (itemstack != null) {
            final float f = (float) itemstack.animationsToGo - partialTicks;

            if (f > 0.0F) {
                GlStateManager.pushMatrix();
                final float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate((float) (xPos + 8), (float) (yPos + 12), 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float) (-(xPos + 8)), (float) (-(yPos + 12)), 0.0F);
            }

            itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);

            if (f > 0.0F) {
                GlStateManager.popMatrix();
            }

            itemRenderer.renderItemOverlays(mc.fontRendererObj, itemstack, xPos, yPos);
        }
    }
}
