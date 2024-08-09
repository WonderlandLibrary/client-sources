package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.math.Vector4i;
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
public class PotionRenderer2 implements ElementRenderer {


    final Dragging dragging;


    float width;
    float height;
    final ResourceLocation logo = new ResourceLocation("expensive/images/hud/potions.png");
    final float iconSize = 10;
    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 3;

        String name = ("Potions");

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawShadow(posX, posY, width - 20, height, 10, ColorUtils.rgba(21, 21, 21, 255));
        drawStyledRect(posX, posY, width, height, 4);
        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        DisplayUtils.drawImage(logo, posX + padding, posY + padding, iconSize, iconSize, colors);
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfui.drawCenteredText(ms, "Potions", posX + width / 2 - 2, posY + padding + 1f, ColorUtils.rgb(150, 150, 150), 7);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 6, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
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

            Fonts.sfMedium.drawText(ms, nameText, posX + padding - 2, posY, ColorUtils.rgba(255, 255, 255, 255), 6);
            Fonts.sfMedium.drawText(ms, bindText, posX + width  - 22, posY, ColorUtils.rgba(210, 210, 210, 255), 6);

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





        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width - 5, height + 1, radius + 0.5f, ColorUtils.rgba(0, 0, 0, 255)); // outline
        DisplayUtils.drawRoundedRect(x, y, width - 5, height, radius, ColorUtils.rgba(0, 0, 0, 255));

    }
}