/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ArmorRenderer
implements ElementRenderer {
    private final ResourceLocation logo = new ResourceLocation("Wiksi/images/watermark/armor.png");
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
        int n = "\u6fbf".length();
        int n2 = "\u6413".length();
        int n3 = "\u689b\u66f0\u5843".length();
        int n4 = "\u6d69\u5618".length();
        int n5 = "\u65c1\u701d\u5757".length();
        int n6 = "\u61fd".length();
        int n7 = "\u60a3".length();
        float f9 = f5 + f - f3 - f7;
        DisplayUtils.drawImage(this.logo, f9, f8 + 3.7f, f3, f4, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, "Armor", f5 + 5.0f, f6 + 5.0f, -1, 6.5f);
        int n8 = (int)f5 + 3 + 75;
        int n9 = (int)f6 + 15;
        int n10 = 0;
        for (ItemStack itemStack : minecraft.player.getArmorInventoryList()) {
            if (!itemStack.isEmpty()) {
                minecraft.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, n8, n9);
                float f10 = (float)itemStack.getDamage() * 100.0f / (float)itemStack.getMaxDamage();
                int n11 = (int)(255.0f * (f10 / 100.0f));
                int n12 = "\u545f\u5662\u6e43\u6a64\u5529".length();
                int n13 = "\u631d\u67f4\u6355".length();
                int n14 = 255 - n11;
                int n15 = "\u5943\u56e7".length();
                int n16 = "\u6cb0\u605c\u59aa".length();
                int n17 = "\u55ec\u548b\u5d17\u5469\u634e".length();
                int n18 = "\u56c0\u55e9\u59fa\u6b20\u4ea1".length();
                int n19 = "\u6fe7\u5188".length();
                int n20 = Math.round(45.0f * (100.0f - f10) / 100.0f);
                int n21 = "\u6f36\u5b0e\u50a2\u55ad\u538d".length();
                Fonts.sfui.drawCenteredText(matrixStack, 100 - itemStack.getDamage() * 100 / itemStack.getMaxDamage() + "%", n8 + 10, n9 + 20, -1, 6.5f);
            } else {
                Fonts.sfui.drawCenteredText(matrixStack, "-", n8 + 8, (float)n9 + 2.5f, ColorUtils.rgb(135, 136, 148), 9.0f);
            }
            n8 -= 25;
            ++n10;
        }
        this.dragging.setWidth(f);
        this.dragging.setHeight(f2);
    }

    public ArmorRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}

