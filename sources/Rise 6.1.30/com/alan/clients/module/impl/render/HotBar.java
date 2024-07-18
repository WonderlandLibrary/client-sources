package com.alan.clients.module.impl.render;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.impl.DragValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;

import static com.alan.clients.layer.Layers.*;

@ModuleInfo(aliases = {"module.render.hotbar.name"}, description = "module.render.hotbar.description", category = Category.RENDER, autoEnabled = true, allowDisable = false)
public class HotBar extends Module {

    private final DragValue structure = new DragValue("", this, new Vector2d(200, 200), false, true);
    private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
    private final StopWatch stopwatch = new StopWatch();
    private float rPosX;
    private Interface interfaceModule;

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (mc.getRenderViewEntity() instanceof EntityPlayer) {
            final ScaledResolution sr = event.getScaledResolution();
            final EntityPlayer entityplayer = (EntityPlayer) mc.getRenderViewEntity();

            structure.position = new Vector2d((int) (sr.getScaledWidth() / 2.0F - 91), sr.getScaledHeight() - 21 - Client.INSTANCE.getThemeManager().getTheme().getPadding() - 18);
            structure.scale = new Vector2d(91 * 2, 22 + 18);

            if (interfaceModule == null) interfaceModule = getModule(Interface.class);

            getLayer(REGULAR, 1).add(() -> {
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                RenderHelper.enableGUIStandardItemLighting();

                renderHotBar(getTheme().getBackgroundShade());

                for (int j = 0; j < 9; ++j) {
                    final int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
                    final int l = sr.getScaledHeight() - 16 - 3;
                    renderHotBarItem(j, k, l - 4, event.getPartialTicks(), entityplayer);
                }

                RenderHelper.disableStandardItemLighting();
                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
            });

            getLayer(BLUR).add(() -> RenderUtil.roundedRectangle(structure.position.x, structure.position.y + 18, structure.scale.x, structure.scale.y - 18, 9, Color.BLACK));

            this.renderHotBarScroll(sr);

            getLayer(BLOOM).add(this::renderHotBarBloom);
        }
    };

    private void renderHotBar(final Color color) {
        if (mc.getRenderViewEntity() instanceof EntityPlayer) {
            switch (interfaceModule.getInformationType().getValue().getName()) {
                case "Rise":
                    RenderUtil.roundedRectangle(structure.position.x, structure.position.y + 18, structure.scale.x, structure.scale.y - 18, 9, color);
                    break;

                case "Traditional":
                    RenderUtil.color(Color.WHITE);
                    this.mc.getTextureManager().bindTexture(widgetsTexPath);

                    mc.ingameGUI.drawTexturedModalRect((float) (structure.position.x + 1), (int) (structure.position.y + 18), 0, 0, 182, 22);
                    break;
            }
        }
    }

    private void renderHotBarBloom() {
        if (mc.getRenderViewEntity() instanceof EntityPlayer) {
            RenderUtil.roundedRectangle(structure.position.x, structure.position.y + 18, structure.scale.x, structure.scale.y - 18, 10, getTheme().getDropShadow());
        }
    }

    private void renderHotBarScroll(final ScaledResolution scaledResolution) {
        if (scaledResolution == null || !(mc.getRenderViewEntity() instanceof EntityPlayer)) return;

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();

        final EntityPlayer entityplayer = mc.thePlayer;

        final int i = scaledResolution.getScaledWidth() / 2;

        rPosX = MathUtil.lerp(rPosX, i - 91 - 1 + entityplayer.inventory.currentItem * 20, 0.03f * stopwatch.getElapsedTime());
        stopwatch.reset();

        switch (interfaceModule.getInformationType().getValue().getName()) {
            case "Rise":
                getLayer(REGULAR).add(() -> {
                    RenderUtil.roundedRectangle(rPosX + 1, scaledResolution.getScaledHeight() - 22 - 4, 22, 22, 8, getTheme().getBackgroundShade());
                });
                break;

            case "Traditional":
                getLayer(REGULAR, 1).add(() -> {
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    RenderHelper.enableGUIStandardItemLighting();

                    this.mc.getTextureManager().bindTexture(widgetsTexPath);
                    RenderUtil.color(Color.WHITE);
                    mc.ingameGUI.drawTexturedModalRect(rPosX + 1, (int) (structure.position.y + 18 - 1), 0, 22, 24, 22);

                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableRescaleNormal();
                    GlStateManager.disableBlend();
                });
                break;
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
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
