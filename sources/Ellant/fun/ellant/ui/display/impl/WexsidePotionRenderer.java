package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WexsidePotionRenderer implements ElementRenderer {

    private final ResourceLocation Potion = new ResourceLocation("expensive/images/hud/potions.png");



    final Dragging dragging;


    float width;
    float height;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;

        ITextComponent name = GradientUtil.white("Potions");

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawShadow(posX, posY, 52, 16, 4, ColorUtils.rgba(32, 35, 90, 156));
        DisplayUtils.drawRoundedRect(posX, posY, 52, 16, 2, ColorUtils.rgba(0,0,0,128));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.montserrat.drawText(ms, "Potions", posX + 38 / 2, posY + padding - 0.5f, -1, 6f, 0.08f);
        DisplayUtils.drawImage(Potion, posX + 4, posY + 1, 13f, 13f, ColorUtils.getColor(0));

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        posY += 3f;

        for (EffectInstance ef : mc.player.getActivePotionEffects()) {
            int amp = ef.getAmplifier();

            String ampStr = "";

            if (amp >= 1 && amp <= 9) {
                ampStr = " " + I18n.format("enchantment.level." + (amp + 1));
            }
            String nameText = I18n.format(ef.getEffectName()) + ampStr;
            float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);

            String bindText = EffectUtils.getPotionDurationString(ef, 1);
            float bindWidth = Fonts.sfMedium.getWidth(bindText, fontSize);

            float localWidth = nameWidth + bindWidth + padding * 3;

            DisplayUtils.drawShadow(posX, posY - 1, nameWidth + 8, 10.0f, 4, ColorUtils.rgba(32, 35, 90, 156));
            DisplayUtils.drawRoundedRect(posX, posY - 1, nameWidth + 8, 10.0f, 2, ColorUtils.rgba(0, 0, 0, 140));
            Fonts.montserrat.drawText(ms, nameText, posX + padding, posY + 0.5f, ColorUtils.rgba(255, 255, 255, 255), 6, 0.07f);
            DisplayUtils.drawShadow(posX + nameWidth + 12, posY - 1, bindWidth + 3, 10, 4, ColorUtils.rgba(32, 35, 90, 156));
            DisplayUtils.drawRoundedRect(posX + nameWidth + 12, posY - 1, bindWidth + 3, 10, 2, ColorUtils.rgba(0,0,0,140));
            Fonts.montserrat.drawText(ms, bindText, posX + nameWidth + 14, posY + 0.5f, ColorUtils.getColor(0), 6, 0.07f);
            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += (fontSize + padding);
            localHeight += (fontSize + padding);
        }
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 80);
        height = localHeight + 2.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {

        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(20, 20, 30, 255));
    }
}
