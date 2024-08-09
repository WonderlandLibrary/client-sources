package fun.ellant.ui.display.impl;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.utils.math.MathUtil;
import net.minecraft.potion.EffectUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.EffectInstance;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.text.GradientUtil;
import fun.ellant.events.EventDisplay;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.ui.display.ElementRenderer;

public class PotionRenderer4 implements ElementRenderer
{
    private final Dragging dragging;
    private float width;
    private float height;
    private float animation;

    final ResourceLocation potion = new ResourceLocation("expensive/images/hud/potions.png");
    final float iconSize = 10;

    public void render(final EventDisplay eventDisplay) {
        final MatrixStack ms = eventDisplay.getMatrixStack();
        final float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        final float fontSize = 6.5f;
        final float padding = 5.0f;
        final ITextComponent name = GradientUtil.gradient("Potions", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        DisplayUtils.drawRoundedRect(posX, posY + 1.0f, this.animation, 18.0f, 4.0f, ColorUtils.rgba(0, 0, 0, 255));
        Fonts.sfui.drawCenteredText(ms, name, posX + this.animation / 1.9f - 5, posY + padding + 1.5f, 6.5f);
        DisplayUtils.drawImage(potion, posX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(205, 205, 205));
        DisplayUtils.drawRectVerticalW(posX + 20.0f, posY + 3.0f, width - 1, 14.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
        posY += fontSize + padding * 2.0f;
        float maxWidth = Fonts.sfui.getWidth(name, fontSize) + padding * 2.0f;
        float localHeight = fontSize + padding * 2.0f;
        posY += 3.0f;
        for (EffectInstance ef : PotionRenderer4.mc.player.getActivePotionEffects()) {
            final int amp = ef.getAmplifier();
            String ampStr = "";
            if (amp >= 1 && amp <= 9) {
                ampStr = " " + I18n.format("enchantment.level." + (amp + 1), new Object[0]);
            }
            final String nameText = I18n.format(ef.getEffectName(), new Object[0]) + ampStr;
            final float nameWidth = Fonts.sfui.getWidth(nameText, fontSize);
            final String bindText = EffectUtils.getPotionDurationString(ef, 1.0f);
            final float bindWidth = Fonts.sfui.getWidth(bindText, fontSize);
            final float localWidth = nameWidth + bindWidth + padding * 3.0f;
            DisplayUtils.drawRoundedRect(posX + padding, posY, this.animation - padding, 12.0f, 3.0f, ColorUtils.rgba(0, 0, 0, 255));
            Fonts.sfui.drawText(ms, nameText, posX + padding + 4.0f, posY + 2.0f, ColorUtils.rgba(230, 230, 230, 255), fontSize);
            Fonts.sfui.drawText(ms, bindText, posX + this.animation - 4.0f - bindWidth, posY + 2.0f, ColorUtils.rgba(255, 255, 255, 255), fontSize);
            DisplayUtils.drawRectVerticalW(posX + padding + 62.0f, posY + 2.0f, width - 1, 8.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }
            posY += 8.5f + padding;
            localHeight += fontSize + padding;
        }
        this.animation = MathUtil.lerp(this.animation, Math.max(maxWidth, 90.0f), 12.0f);
        this.height = localHeight + 4.5f;
        this.dragging.setWidth(this.animation);
        this.dragging.setHeight(this.height);
    }

    private void drawStyledRect(final float x, final float y, final float width, final float height, final float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1.0f, height + 1.0f, radius + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(255, 255, 255, 255));
    }

    public PotionRenderer4(final Dragging dragging) {
        this.dragging = dragging;
    }
}
