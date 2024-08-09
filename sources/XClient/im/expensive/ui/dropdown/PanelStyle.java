package im.expensive.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.functions.api.Category;
import im.expensive.ui.styles.Style;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.*;
import im.expensive.utils.render.font.Fonts;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;

@Getter
public class PanelStyle extends Panel {

    public PanelStyle(Category category) {
        super(category);
        // TODO Auto-generated constructor stub
    }

    float max = 0;

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        float header = 32 / 2f;
        float headerFont = 9;
        setAnimatedScrool(MathUtil.fast(getAnimatedScrool(), getScroll(), 5));

        DisplayUtils.drawRoundedRect(x, y, width, height , 3,
                ColorUtils.rgba(10, 10, 10, (int) (200)));
        DisplayUtils.drawRoundedRect(x + 3, y + 20, width - 6, height - 23, 3,
                ColorUtils.rgba(10, 10, 10, (int) (255)));

        Fonts.sfMedium.drawCenteredText(stack, getCategory().name(), x + width / 2f, y + header  - 4 - Fonts.sfMedium.getHeight(headerFont) / 2f - 1, ColorUtils.rgb(161, 164, 177), headerFont, 0.1f);
        drawOutline();
        if (max > height - header - 10) {
            setScroll(MathHelper.clamp(getScroll(), -max + height - header - 10, 0));
            setAnimatedScrool(MathHelper.clamp(getAnimatedScrool(), -max + height - header - 10, 0));
        } else {
            setScroll(0);
            setAnimatedScrool(0);
        }
        float animationValue = (float) DropDown.getAnimation().getValue() * DropDown.scale;

        float halfAnimationValueRest = (1 - animationValue) / 2f;
        float height = getHeight();
        float testX = getX() + (getWidth() * halfAnimationValueRest);
        float testY = getY() + 41 / 2f + (height * halfAnimationValueRest);
        float testW = getWidth() * animationValue;
        float testH = (height - 25f) * animationValue;

        testX = testX * animationValue + ((Minecraft.getInstance().getMainWindow().getScaledWidth() - testW) * halfAnimationValueRest);
        Scissor.push();
        Scissor.setFromComponentCoordinates(testX, testY, testW, testH);
        int offset = 0;

        boolean hovered = false;
        for (Style style : Expensive.getInstance().getStyleManager().getStyleList()) {
            float x = this.x + 5;
            float y = this.y + header - 1.5f+ offset * (32 / 2f + 5) + getAnimatedScrool();
            if (MathUtil.isHovered(mouseX, mouseY, x + 5, y + 13, width - 10 - 10, 32 / 2f)) {
                hovered = true;
            }



            Fonts.sfMedium.drawText(stack, style.getStyleName(), x + 34, y + 17, -1, 8f, 0.05f);

            y += 1;

            if (Expensive.getInstance().getStyleManager().getCurrentStyle() == style) {
                DisplayUtils.drawShadow(x + 5, y + 13, width -110, 13, 12, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
            }
            DisplayUtils.drawRoundedRect(x + 5, y + 13, width -110, 13, new Vector4f(6, 6, 6, 6), new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB()));
            offset++;
        }
        if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
            if (hovered) {

                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
            } else {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            }
        }
        Scissor.unset();
        Scissor.pop();
        max = offset * Expensive.getInstance().getStyleManager().getStyleList().size() * 2.2f;
    }

    @Override
    public void keyPressed(int key, int scanCode, int modifiers) {

    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int button) {
        float header = 32 / 2f;
        int offset = 0;
        for (Style style : Expensive.getInstance().getStyleManager().getStyleList()) {
            float x = this.x + 5;
            float y = this.y + header + 5 + offset * (32 / 2f + 5) + getAnimatedScrool();
            if (MathUtil.isHovered(mouseX, mouseY, x + 5, y + 5, width - 10 - 10, 32 / 2f)) {
                Expensive.getInstance().getStyleManager().setCurrentStyle(style);
            }
            offset++;
        }

    }

    @Override
    public void mouseRelease(float mouseX, float mouseY, int button) {

    }

}
