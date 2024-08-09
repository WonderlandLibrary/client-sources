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
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.utils.text.GradientUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class PotionRenderer
implements ElementRenderer {
    private final Dragging dragging;
    private final ResourceLocation logo = new ResourceLocation("venusfr/images/hud/potions.png");
    private float iconSizeX = 10.0f;
    private float iconSizeY = 10.0f;
    private float width;
    private float height;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f = this.dragging.getX();
        float f2 = this.dragging.getY();
        float f3 = 6.5f;
        float f4 = 5.0f;
        StringTextComponent stringTextComponent = GradientUtil.gradient("Potions");
        String string = "Potions";
        float f5 = f2;
        this.drawStyledRect(f, f5, this.width, this.height, 5.0f);
        DisplayUtils.drawShadow(f, f2, this.width, this.height, 15, ColorUtils.rgba(21, 24, 40, 165));
        Scissor.push();
        Scissor.setFromComponentCoordinates(f, f2, this.width, this.height);
        Fonts.sfui.drawText(matrixStack, string, f + f4, f2 + f4 + 1.0f, ColorUtils.rgb(255, 255, 255), f3);
        f2 += f3 + f4 * 2.0f;
        float f6 = Fonts.sfMedium.getWidth(stringTextComponent, f3) + f4 * 2.0f;
        float f7 = f3 + f4 * 2.0f;
        for (EffectInstance effectInstance : PotionRenderer.mc.player.getActivePotionEffects()) {
            int n = effectInstance.getAmplifier();
            Object object = "";
            if (n >= 1 && n <= 9) {
                object = " " + I18n.format("enchantment.level." + (n + 1), new Object[0]);
            }
            String string2 = I18n.format(effectInstance.getEffectName(), new Object[0]) + (String)object;
            float f8 = Fonts.sfMedium.getWidth(string2, f3);
            String string3 = EffectUtils.getPotionDurationString(effectInstance, 1.0f);
            float f9 = Fonts.sfMedium.getWidth(string3, f3);
            float f10 = f8 + f9 + f4 * 3.0f;
            Fonts.sfMedium.drawText(matrixStack, string2, f + f4, f2, ColorUtils.rgba(210, 210, 210, 255), f3);
            Fonts.sfMedium.drawText(matrixStack, string3, f + this.width - f4 - f9, f2, ColorUtils.rgba(210, 210, 210, 255), f3);
            if (f10 > f6) {
                f6 = f10;
            }
            f2 += f3 + f4;
            f7 += f3 + f4;
        }
        this.width = Math.max(f6, 80.0f);
        this.height = f7 + 2.5f;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
        float f11 = f + this.width - this.iconSizeX - f4;
        DisplayUtils.drawImage(this.logo, f11, f5 + 3.7f, this.iconSizeX, this.iconSizeY, ColorUtils.getColor(1));
        Scissor.unset();
        Scissor.pop();
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5) {
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.setAlpha(ColorUtils.rgb(21, 24, 40), 90));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 24, 40, 90));
    }

    public PotionRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}

