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
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class PotionRenderer
implements ElementRenderer {
    private final Dragging dragging;
    private final ResourceLocation logo = new ResourceLocation("Wiksi/images/watermark/potions.png");
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
            int n2 = "\u508f\u4eb2\u52ba\u5e22\u6119".length();
            int n3 = "\u5eb5\u6f46\u6a00\u5a03\u5462".length();
            Fonts.sfMedium.drawText(matrixStack, string3, f + this.width - f4 - f9, f2, ColorUtils.rgba(210, 210, 210, 255), f3);
            if (f10 > f6) {
                f6 = f10;
            }
            f2 += f3 + f4;
            f7 += f3 + f4;
        }
        this.width = Math.max(f6, 80.0f);
        this.height = f7 + 2.5f;
        float posX = 3;
        float posY = 3;
        float padding = 3;
        float fontSize = 6.5f;
        float iconSize = 8;
        float localHeight = fontSize + padding * 2;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
        int n = "\u61ec\u53a0\u5158\u65d7\u62b9".length();
        int n4 = "\u585d".length();
        int n5 = "\u6917".length();
        int n6 = "\u6734\u5319".length();
        int n7 = "\u4fcb".length();
        float f11 = f + this.width - this.iconSizeX - f4;
        DisplayUtils.drawImage(this.logo, f11, f3 + 3.7f, f6, f7, ColorUtils.getColor(1));
        Scissor.unset();
        Scissor.pop();
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5) {
        int n = "\u513b\u682f".length();
        int n2 = "\u681c\u591f\u6f01\u5d39\u6ce8".length();
        int n3 = "\u5750\u60a1\u526f\u567d\u5475".length();
        int n4 = "\u6888\u599c\u64df".length();
        int n5 = "\u6c50\u54d0".length();
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.setAlpha(ColorUtils.rgb(21, 24, 40), 90));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 24, 40, 90));
    }

    public PotionRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}

