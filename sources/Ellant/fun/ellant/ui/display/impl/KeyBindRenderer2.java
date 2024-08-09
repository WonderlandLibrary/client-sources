package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.client.KeyStorage;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class KeyBindRenderer2 implements ElementRenderer {

    final Dragging dragging;
    float width;
    float height;
    final ResourceLocation imagePath = new ResourceLocation("expensive/images/hud/binds.png");

    final float iconSize = 10;
    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;

        String name = "Hot-Keys";

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawShadow(posX, posY, width, height, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        drawStyledRect(posX, posY, width, height, 4);
        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        DisplayUtils.drawImage(imagePath, posX + padding - 1, posY + padding, iconSize, iconSize, colors);
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfui.drawCenteredText(ms, ITextComponent.getTextComponentOrEmpty(name), posX + width / 2, posY + padding + 0.5f, fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        posY += 3f;

        for (Function f : Ellant.getInstance().getFunctionRegistry().getFunctions()) {
            f.getAnimation().update();
            if (!(f.getAnimation().getValue() > 0) || f.getBind() == 0) continue;
            String nameText = f.getName();
            float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);

            String bindText = "[" + KeyStorage.getKey(f.getBind()) + "]";
            float bindWidth = Fonts.sfMedium.getWidth(bindText, fontSize);

            float localWidth = nameWidth + bindWidth + padding * 3;

            Fonts.sfMedium.drawText(ms, nameText, posX + padding, posY + 0.5f, ColorUtils.rgba(210, 210, 210, (int) (255 * f.getAnimation().getValue())), fontSize);
            Fonts.sfMedium.drawText(ms, bindText, posX + width - padding - bindWidth, posY + 0.5f, ColorUtils.rgba(210, 210, 210, (int) (255 * f.getAnimation().getValue())), fontSize);

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
                                float radius) {

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width - 5, height + 1, radius + 0.5f, ColorUtils.rgba(0, 0, 0, 255)); // outline
        DisplayUtils.drawRoundedRect(x, y, width - 5, height, radius, ColorUtils.rgba(0, 0, 0, 255));
    }

    public boolean isEmpty() {
        for (Function f : Ellant.getInstance().getFunctionRegistry().getFunctions()) {
            if (f.getAnimation().getValue() > 0 && f.getBind() != 0) {
                return false;
            }
        }
        return true;
    }

}
