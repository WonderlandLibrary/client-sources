/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ArmorRenderer
implements ElementRenderer {
    private final ResourceLocation logo = new ResourceLocation("venusfr/images/hud/armor.png");
    private final Dragging dragging;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        Minecraft minecraft = Minecraft.getInstance();
        float f = 100.0f;
        float f2 = 45.0f;
        float f3 = 10.0f;
        float f4 = 10.0f;
        float f5 = this.dragging.getX();
        float f6 = this.dragging.getY();
        float f7 = 5.0f;
        float f8 = f6;
        DisplayUtils.drawShadow(f5, f6, f, f2 + 1.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f5, f6, f, f2, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        float f9 = f5 + f - f3 - f7;
        DisplayUtils.drawImage(this.logo, f9, f8 + 3.7f, f3, f4, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, "Armor", f5 + 5.0f, f6 + 5.0f, -1, 6.5f);
        int n = (int)f5 + 3 + 75;
        int n2 = (int)f6 + 15;
        int n3 = 0;
        for (ItemStack itemStack : minecraft.player.getArmorInventoryList()) {
            if (!itemStack.isEmpty()) {
                minecraft.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, n, n2);
                float f10 = (float)itemStack.getDamage() * 100.0f / (float)itemStack.getMaxDamage();
                int n4 = (int)(255.0f * (f10 / 100.0f));
                int n5 = 255 - n4;
                int n6 = Math.round(45.0f * (100.0f - f10) / 100.0f);
                Fonts.sfui.drawCenteredText(matrixStack, 100 - itemStack.getDamage() * 100 / itemStack.getMaxDamage() + "%", n + 10, n2 + 20, -1, 6.5f);
            } else {
                Fonts.sfui.drawCenteredText(matrixStack, "-", n + 8, (float)n2 + 2.5f, ColorUtils.rgb(135, 136, 148), 9.0f);
            }
            n -= 25;
            ++n3;
        }
        this.dragging.setWidth(f);
        this.dragging.setHeight(f2);
    }

    public ArmorRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}

