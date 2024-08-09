package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.events.EventDisplay;
import im.expensive.functions.impl.render.HUD;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import im.expensive.utils.text.GradientUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;

public class AncientPotion1 implements ElementRenderer
{
    private final Dragging dragging;
    private float width;
    private float height;
    private float animation;
    final float iconSize = 10;

    public void render(final EventDisplay eventDisplay) {
        final MatrixStack ms = eventDisplay.getMatrixStack();
        final float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        final float fontSize = 6.5f;
        final float padding = 5.0f;
        //final ITextComponent name = GradientUtil.gradient("Potions");
        //DisplayUtils.drawRoundedRect(posX, posY + 1.0f, this.animation, 18.0f, 0, ColorUtils.rgba(0, 0, 0, 255));
        //DisplayUtils.drawShadow(posX, posY, 3, 14.3f, 7, ColorUtils.getColor(0));
        //DisplayUtils.drawRoundedRect(posX, posY + 1.2f, 2.5f, 18f, new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(90, 1.0F)));
        //Fonts.sfui.drawCenteredText(ms, name, posX + this.animation / 1.9f - 5 + 2, posY + padding + 1.5f, 6.5f);
        //DisplayUtils.drawRectVerticalW(posX + 20.0f, posY + 3.0f, width - 1, 14.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
        posY += fontSize + padding * 2.0f;
        float maxWidth = Fonts.sfui.getWidth("name", fontSize) + padding * 2.0f;
        float localHeight = fontSize + padding * 2.0f;
        posY += 3.0f;
        for (EffectInstance ef : PotionRenderer.mc.player.getActivePotionEffects()) {
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
            //DisplayUtils.drawRoundedRect(posX, posY - 2, this.animation, 14.0f, 0, ColorUtils.rgba(0, 0, 0, 255));
            //DisplayUtils.drawRoundedRect(posX, posY - 2f, 2.5f, 14f, new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(90, 1.0F)));
            Fonts.sfui.drawText(ms, nameText, posX + 4.0f + 1, posY + 2.0f, ColorUtils.rgba(255, 255, 255, 255), fontSize);
            Fonts.sfui.drawText(ms, bindText, posX + this.animation - 4.0f - bindWidth, posY + 2.0f, ColorUtils.rgba(255, 255, 255, 255), fontSize);
            //DisplayUtils.drawRectVerticalW(posX + 66.0f, posY + 2.0f, width - 1, 8.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }
            posY += 7.5f + padding;
            localHeight += fontSize + padding;
        }
        this.animation = MathUtil.lerp(this.animation, Math.max(maxWidth, 80.0f), 10.0f);
        this.height = localHeight + 2.5f;
        this.dragging.setWidth(this.animation);
        this.dragging.setHeight(this.height);
    }

    private void drawStyledRect(final float x, final float y, final float width, final float height, final float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1.0f, height + 1.0f, radius + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(255, 255, 255, 255));
    }

    public AncientPotion1(final Dragging dragging) {
        this.dragging = dragging;
    }
}
