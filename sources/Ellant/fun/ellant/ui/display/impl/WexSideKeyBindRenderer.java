package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.impl.render.SwingAnimation;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.client.KeyStorage;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.KawaseBlur;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WexSideKeyBindRenderer implements ElementRenderer {

    private final ResourceLocation bind = new ResourceLocation("expensive/images/hud/binds.png");

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
        float finalPosY = (float) posY;

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawShadow(posX, finalPosY, 60, 16, 4, ColorUtils.rgba(32, 35, 90, 156));
        DisplayUtils.drawRoundedRect(posX, finalPosY, 60, 16, 2, ColorUtils.rgba(0, 0, 0, 140));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.montserrat.drawText(ms, "Key Binds", posX + 45 / 2, posY + padding, -1, 6f, 0.07f);
        DisplayUtils.drawImage(bind, posX + 5, posY, 14, 14, ColorUtils.getColor(0));

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth("Key Binds", fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        posY += 3f;

        for (Function f : Ellant.getInstance().getFunctionRegistry().getFunctions()) {
            f.getAnimation().update();
            if (!(f.getAnimation().getValue() > 0) || f.getBind() == 0) continue;
            String nameText = f.getName();
            float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);

            String bindText = "[" + KeyStorage.getKey(f.getBind()) + "]";
            float bindWidth = Fonts.sfMedium.getWidth(bindText, fontSize);

            float localWidth = nameWidth + bindWidth + padding * 3;
            DisplayUtils.drawShadow(posX, posY - 1, nameWidth + 8, 10.0f, 4, ColorUtils.rgba(32, 35, 90, 156));
            DisplayUtils.drawRoundedRect(posX, posY - 1, nameWidth + 8, 10.0f, 2, ColorUtils.rgba(0, 0, 0, (int)(140 * f.getAnimation().getValue())));
            DisplayUtils.drawShadow(posX + nameWidth + 9, posY - 1, bindWidth + 3, 10, 4, ColorUtils.rgba(32, 35, 90, 156));
            DisplayUtils.drawRoundedRect(posX + nameWidth + 9, posY - 1, bindWidth + 3, 10, 2, ColorUtils.rgba(0,0,0,140));
            Fonts.montserrat.drawText(ms, nameText, posX - 0.5f + padding, posY + 0.5f, ColorUtils.rgba(255, 255, 255, (int) (255 * f.getAnimation().getValue())), 6, 0.07f);
            Fonts.montserrat.drawText(ms, bindText, posX + nameWidth + 11, posY + 0.5f, ColorUtils.getColor(0), 6, 0.07f);
            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += (float) ((fontSize + padding) * f.getAnimation().getValue());
            localHeight += (float) ((fontSize + padding) * f.getAnimation().getValue());
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
                                float radius, int alpha) {

        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(0, 0, 0, 128));
    }
}