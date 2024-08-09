package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Furious;
import im.expensive.events.EventDisplay;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.styles.Style;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import im.expensive.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PotionRenderer implements ElementRenderer {


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

        ITextComponent name = GradientUtil.gradient("Potions");

        Style style = Furious.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawRoundedRect(posX,posY - 0.5f,width - 9.8f,20,1,ColorUtils.setAlpha(ColorUtils.rgb(21,21,21), 220));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfui.drawCenteredText(ms, name, posX - 4 + width / 2, posY + padding + 0.5f, fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        //DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
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

            DisplayUtils.drawRoundedRect(posX,posY, 2 + nameWidth + 1,10,1,ColorUtils.setAlpha(ColorUtils.rgb(21,21,21), 220));
            DisplayUtils.drawRoundedRect(posX + nameWidth + 4.5f,posY,1 + bindWidth + 1,10,1,ColorUtils.setAlpha(ColorUtils.rgb(21,21,21), 220));
            Fonts.sfMedium.drawText(ms, nameText, posX + 0.5f, posY + 1.5f, ColorUtils.rgba(255, 255, 255, (int) (255)), fontSize);
            Fonts.sfMedium.drawText(ms, bindText, posX + nameWidth + 5, posY + 1.5f,ColorUtils.rgba(255, 255, 255, (int) (255)), fontSize);

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

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));
    }
}
