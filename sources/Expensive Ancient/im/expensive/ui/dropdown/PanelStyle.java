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
    boolean opened = false;

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        float header = 55 / 2f;
        float headerFont = 9;
        setAnimatedScrool(MathUtil.fast(getAnimatedScrool(), getScroll(), 10));

        DisplayUtils.drawRoundedRect(x, y, width, header, new Vector4f(7, 0, 7, 0), ColorUtils.rgba(23, 23, 23, (int) (255 * 0.43)));

        DisplayUtils.drawRoundedRect(x, y, width, height, new Vector4f(7, 7, 7, 7), ColorUtils.rgba(17, 17, 17, (int) (255 * 0.65)));

        DisplayUtils.drawRectHorizontalW(x, y, width, header, ColorUtils.rgba(17, 17, 17, 64), ColorUtils.rgba(17, 17, 17, 0));

        DisplayUtils.drawRectVerticalW(x, y + header, width, 0.5f, ColorUtils.rgb(24, 24, 30), ColorUtils.rgb(32, 34, 40));
        Fonts.montserrat.drawCenteredText(stack, getCategory().name(), x + width / 2f, y + header / 2f - Fonts.montserrat.getHeight(headerFont) / 2f - 1, ColorUtils.rgb(161, 164, 177), headerFont, 0.1f);
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
        float testY = getY() + 65 / 2f + (height * halfAnimationValueRest);
        float testW = getWidth() * animationValue;
        float testH = height * animationValue;

        testX = testX * animationValue + ((Minecraft.getInstance().getMainWindow().getScaledWidth() - testW) * halfAnimationValueRest);
        Scissor.push();
        Scissor.setFromComponentCoordinates(testX, testY, testW, testH);
        int offset = 0;
        float panelHeight = 57 / 2;

        boolean hovered = false;
        for (Style style : Expensive.getInstance().getStyleManager().getStyleList()) {
            float x = this.x + 5;
            float y = this.y + header + 5 + offset * (panelHeight + 5) + getAnimatedScrool();
            if (MathUtil.isInRegion(mouseX, mouseY, x + 5, y + 13, width - 10 - 10, 23 / 2f)) {
                hovered = true;
            }

            DisplayUtils.drawRoundedRect(x + 0.5f, y + 0.5f, width - 10 - 1, panelHeight - 1, new Vector4f(7, 7, 7, 7), ColorUtils.rgba(17, 17, 17, (int) (255 * 0.33)));
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(x + 0.5f, y + 0.5f, width - 10 - 1, panelHeight - 1, new Vector4f(7, 7, 7, 7), ColorUtils.rgba(17, 17, 17, (int) (255 * 0.33)));
            Stencil.readStencilBuffer(0);
            DisplayUtils.drawRoundedRect(x, y, width - 10, panelHeight, new Vector4f(7, 7, 7, 7), new Vector4i(ColorUtils.rgb(48, 53, 60), ColorUtils.rgb(0, 0, 0), ColorUtils.rgb(48, 53, 60), ColorUtils.rgb(0, 0, 0)));
            Stencil.uninitStencilBuffer();

            Fonts.montserrat.drawText(stack, style.getStyleName(), x + 5, y + 5, -1, 6f, 0.05f);

            y += 1;

            if (Expensive.getInstance().getStyleManager().getCurrentStyle() == style) {
                DisplayUtils.drawShadow(x + 5, y + 13, width - 10 - 10, 23 / 2f, 12, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
            }
            DisplayUtils.drawRoundedRect(x + 5, y + 13, width - 10 - 10, 23 / 2f, new Vector4f(7, 7, 7, 7), new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB()));
            offset++;
        }
        if (MathUtil.isInRegion(mouseX, mouseY, x, y, width, height)) {
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
        float header = 55 / 2f;
        int offset = 0;
        for (Style style : Expensive.getInstance().getStyleManager().getStyleList()) {

            float x = this.x + 5;
            float y = this.y + header + 5 + offset * (57 / 2 + 5) + getAnimatedScrool();
            if (MathUtil.isInRegion(mouseX, mouseY, x + 5, y + 13, width - 10 - 10, 23 / 2f)) {
                if (style.getStyleName().equals("Кастомный") && button == 1) {
                    opened = !opened;
                    return;
                }
                Expensive.getInstance().getStyleManager().setCurrentStyle(style);
            }
            offset++;
        }
    }

    @Override
    public void mouseRelease(float mouseX, float mouseY, int button) {

    }

}
