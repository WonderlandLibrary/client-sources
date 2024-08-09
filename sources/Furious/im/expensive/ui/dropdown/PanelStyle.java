package im.expensive.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Furious;
import im.expensive.functions.api.Category;
import im.expensive.functions.impl.render.HUD;
import im.expensive.ui.styles.Style;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.*;
import im.expensive.utils.render.font.Fonts;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;

@Getter
public class PanelStyle extends Panel {
    private final ResourceLocation line = new ResourceLocation("expensive/images/gradline.png");
    public PanelStyle(Category category) {
        super(category);
        // TODO Auto-generated constructor stub
    }

    float max = 0;

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        float header = 55 / 2f;
        float headerFont = 9;
        setAnimatedScrool(MathUtil.fast(getAnimatedScrool(), getScroll(), 10));

        DisplayUtils.drawRoundedRect(x, y, width, header, new Vector4f(7, 0, 7, 0), ColorUtils.rgba(23, 23, 23, (int) (255 * 0.43)));
        drawPanel(x ,y,getWidth(),getHeight(),6);
        DisplayUtils.drawImage(line, x + 7, y + header - 5, 120, 1, HUD.getColor(1,0));

        Fonts.montserrat.drawCenteredText(stack, getCategory().name(), x + width / 2f,
                y + 1.5f+ header / 2f - Fonts.montserrat.getHeight(headerFont) / 2f - 1, ColorUtils.rgb(255, 255, 255),
                headerFont, 0.1f);
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

        boolean hovered = false;
        for (Style style : Furious.getInstance().getStyleManager().getStyleList()) {
            float x = this.x + 5;
            float y = this.y + header + offset * (57 / 2f) + getAnimatedScrool();
            if (MathUtil.isHovered(mouseX, mouseY, x + 5, y + 13, width - 10 - 10, 23 / 2f)) {
                hovered = true;
            }


           // DisplayUtils.drawRoundedRect(x + 0.5f, y + 0.5f, width - 10 - 1, 57 / 2f - 1, new Vector4f(7, 7, 7, 7), ColorUtils.rgba(17, 17, 17, (int) (255 * 0.33)));
            Stencil.initStencilToWrite();

           // DisplayUtils.drawRoundedRect(x + 0.5f, y + 0.5f, width - 10 - 1, 57 / 2f - 1, new Vector4f(7, 7, 7, 7), ColorUtils.rgba(17, 17, 17, (int) (255 * 0.33)));

           Stencil.readStencilBuffer(0);

            //  DisplayUtils.drawRoundedRect(x, y, width - 10, 57 / 2f, new Vector4f(7, 7, 7, 7), new Vector4i(ColorUtils.rgb(48, 53, 60), ColorUtils.rgb(0, 0, 0), ColorUtils.rgb(48, 53, 60), ColorUtils.rgb(0, 0, 0)));

            Stencil.uninitStencilBuffer();

            Fonts.montserrat.drawText(stack, style.getStyleName(), x + 5, y + 5, -1, 6f, 0.05f);

            y += 1;

            if (Furious.getInstance().getStyleManager().getCurrentStyle() == style) {
                DisplayUtils.drawShadow(x + 5, y + 13, width - 10 - 10, 23 / 2f, 15, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
            }
            DisplayUtils.drawRoundedRect(x + 5, y + 13, width - 10 - 10, 23 / 2f, new Vector4f(7, 7, 7, 7), new Vector4i(style.getFirstColor().getRGB(), style.getFirstColor().getRGB(), style.getSecondColor().getRGB(), style.getSecondColor().getRGB()));
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
        max = offset * Furious.getInstance().getStyleManager().getStyleList().size() * 2.2f;
    }

    @Override
    public void keyPressed(int key, int scanCode, int modifiers) {

    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int button) {
        float header = 55 / 2f;
        int offset = 0;
        for (Style style : Furious.getInstance().getStyleManager().getStyleList()) {
            float x = this.x + 5;
            float y = this.y + header + offset * (57 / 2f) + getAnimatedScrool();
            if (MathUtil.isHovered(mouseX, mouseY, x + 5, y + 13, width - 10 - 10, 23 / 2f)) {
                Furious.getInstance().getStyleManager().setCurrentStyle(style);
            }
            offset++;
        }

    }

    @Override
    public void mouseRelease(float mouseX, float mouseY, int button) {

    }
    private void drawPanel(     float x,
                                float y,
                                float width,
                                float height,
                                float radius) {
        DisplayUtils.drawRoundedRect(x - 1f, y - 1f, width + 1.5f, height + 1.5f, radius + 4f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x + 0.5f, y + 0.5f, width - 1.5f, height-1.5f, radius, ColorUtils.rgba(21, 21, 21, 255));
    }
}
