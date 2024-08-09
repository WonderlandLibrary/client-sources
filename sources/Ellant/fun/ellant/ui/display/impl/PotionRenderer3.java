package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;

public class PotionRenderer3 implements ElementRenderer {
    private final Dragging dragging;
    private float width;
    private float height;

    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        float fontSize = 5.5F;
        float padding = 5.0F;
        ITextComponent name = GradientUtil.gradient("Potions", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        float topHeight = 15.0F;
        if (Ellant.getInstance().getFunctionRegistry().getHud().shadow.get()) {
            DisplayUtils.drawShadow(posX, posY, this.width, topHeight, 6, ColorUtils.getColor1(), ColorUtils.getColor2());
        }

        DisplayUtils.drawRoundedRect(posX, posY, this.width, topHeight, new Vector4f(4.0F, 4.0F, 4.0F, 4.0F), (new Color(12, 13, 13, 230)).getRGB());
        Fonts.sfbold.drawCenteredText(ms, name, posX + this.width / 2.0F, posY + topHeight / 2.0F - 3.5F, 7.0F);
        posY += fontSize + padding + 4.0F;
        float maxWidth = Fonts.sfbold.getWidth(name, fontSize) + padding * 2.0F;
        float localHeight = fontSize + padding;
        posY += 3.0F;

        for(Iterator var12 = mc.player.getActivePotionEffects().iterator(); var12.hasNext(); localHeight += fontSize + padding) {
            EffectInstance ef = (EffectInstance)var12.next();
            int amp = ef.getAmplifier();
            String ampStr = "";
            String var10000;
            if (amp >= 1 && amp <= 9) {
                var10000 = "enchantment.level." + (amp + 1);
                ampStr = " " + I18n.format(var10000, new Object[0]);
            }

            var10000 = I18n.format(ef.getEffectName(), new Object[0]);
            String nameText = var10000 + ampStr;
            float nameWidth = Fonts.sfbold.getWidth(nameText, fontSize);
            String bindText = EffectUtils.getPotionDurationString(ef, 1.0F);
            float bindWidth = Fonts.sfbold.getWidth(bindText, fontSize);
            float localWidth = nameWidth + bindWidth + padding * 3.0F;
            if (Ellant.getInstance().getFunctionRegistry().getHud().shadow.get()) {
                DisplayUtils.drawShadow(posX, posY, this.width, 15.0F, 6, ColorUtils.getColor1(), ColorUtils.getColor2());
            }

            DisplayUtils.drawRoundedRect(posX, posY, this.width, 15.0F, new Vector4f(4.0F, 4.0F, 4.0F, 4.0F), (new Color(12, 13, 12, 210)).getRGB());
            Fonts.sfbold.drawText(ms, nameText, posX + padding, posY + 4.6F, ColorUtils.rgba(210, 210, 210, 255), fontSize);
            Fonts.sfbold.drawText(ms, bindText, posX + this.width - padding - bindWidth, posY + 4.6F, ColorUtils.rgba(210, 210, 210, 255), fontSize);
            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += fontSize + padding + 6.0F;
        }

        this.width = Math.max(maxWidth, 80.0F);
        this.height = localHeight;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        int alpha = 150;
        Color blurColor = new Color(20, 20, 20, alpha);
        DisplayUtils.drawRoundedRect(x, y, width, height, radius + 3.0F, blurColor.getRGB());
        float posx2 = 0.0F;
        float posy2 = 0.0F;
        float width2 = 0.0F;
        float height2 = 0.0F;
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawShadow(posx2, posy2, width2, height2, 25, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
    }

    public PotionRenderer3(Dragging dragging) {
        this.dragging = dragging;
    }
}