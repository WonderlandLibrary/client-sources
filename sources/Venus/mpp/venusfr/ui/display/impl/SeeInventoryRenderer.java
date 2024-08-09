/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.math.Vector4i;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.utils.text.GradientUtil;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class SeeInventoryRenderer
implements ElementRenderer {
    private final ResourceLocation logo = new ResourceLocation("venusfr/images/hud/Inventory.png");
    private final int INVENTORY_ROWS = 3;
    private final int INVENTORY_COLUMNS = 9;
    private final int SLOT_SIZE = 18;
    private final int SLOT_PADDING = 2;
    private final Dragging dragging;
    private float width;
    private float height;
    private final Minecraft mc = Minecraft.getInstance();
    private float padding = 5.0f;
    private final float iconSize = 10.0f;

    @Override
    public void render(EventDisplay eventDisplay) {
        float f;
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f2 = this.dragging.getX();
        float f3 = this.dragging.getY();
        float f4 = 6.5f;
        float f5 = 5.0f;
        float f6 = 10.0f;
        float f7 = 10.0f;
        StringTextComponent stringTextComponent = GradientUtil.gradient("Inventory");
        Style style = venusfr.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawShadow(f2, f3, this.width, this.height, 10, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f2, f3, this.width, this.height, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        NonNullList<ItemStack> nonNullList = this.mc.player.inventory.mainInventory;
        float f8 = f2 + this.width - f6 - f5;
        DisplayUtils.drawImage(this.logo, f8, f3 + 3.7f, f6, f7, ColorUtils.getColor(1));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                f = f2 + 3.5f + (float)(j * 20);
                float f9 = f3 + this.height - 62.0f + (float)(i * 20);
                int n = j + (i + 1) * 9;
                ItemStack itemStack = nonNullList.get(n);
                float f10 = f + 2.7f;
                float f11 = f9 + 2.0f;
                float f12 = 0.8f;
                int n2 = (int)(16.0f * f12);
                int n3 = (int)(16.0f * f12);
                if (!itemStack.isEmpty()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scalef(f12, f12, f12);
                    this.mc.getItemRenderer().renderItemIntoGUI(itemStack, (int)(f10 / f12), (int)(f11 / f12));
                    GlStateManager.popMatrix();
                    int n4 = itemStack.getCount();
                    if (n4 > 1) {
                        String string = String.valueOf(n4);
                        float f13 = f10 + (n4 > 9 ? 8.5f : 11.0f);
                        float f14 = f11 + (n4 > 9 ? 10.0f : 10.0f);
                        float f15 = n4 > 9 ? 0.6f : 0.65f;
                        RenderSystem.pushMatrix();
                        RenderSystem.scalef(f15, f15, f15);
                        this.mc.fontRenderer.drawStringWithShadow(eventDisplay.getMatrixStack(), string, f13 / f15, f14 / f15, 0xFFFFFF);
                        RenderSystem.popMatrix();
                    }
                    if (!itemStack.isEmpty()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.scalef(f12, f12, f12);
                        this.mc.getItemRenderer().renderItemIntoGUI(itemStack, (int)(f10 / f12), (int)(f11 / f12));
                        GlStateManager.popMatrix();
                    }
                }
                DisplayUtils.drawRoundedRect(f, f9, 18.0f, 18.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
            }
        }
        Vector4i vector4i = new Vector4i(HUD.getColor(0, 1.0f), HUD.getColor(90, 1.0f), HUD.getColor(180, 1.0f), HUD.getColor(270, 1.0f));
        Scissor.push();
        Scissor.setFromComponentCoordinates(f2, f3, this.width, this.height);
        Fonts.sfui.drawText(matrixStack, "Inventory", f2 + 5.0f, f3 + 5.0f, -1, f4);
        float f16 = Fonts.sfMedium.getWidth("Inventory", f4) + f5 * 2.0f;
        f = f4 + f5 * 2.0f;
        DisplayUtils.drawRectHorizontalW(f2 + 0.5f, f3 += f4 + f5 * 2.0f, this.width - 1.0f, 2.5, 3, ColorUtils.rgba(0, 0, 0, 63));
        Scissor.unset();
        Scissor.pop();
        this.width = Math.max(f16, 185.0f);
        this.height = f + 65.0f;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5) {
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 21, 21, 255));
    }

    public SeeInventoryRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}

