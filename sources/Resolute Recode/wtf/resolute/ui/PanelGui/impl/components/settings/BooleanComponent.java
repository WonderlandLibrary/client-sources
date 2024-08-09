package wtf.resolute.ui.PanelGui.impl.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.ui.PanelGui.impl.Component;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.Cursors;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.awt.*;

/**
 * BooleanComponent
 */
public class BooleanComponent extends Component {

    private final BooleanSetting setting;

    public BooleanComponent(BooleanSetting setting) {
        this.setting = setting;
        setHeight(16);
        animation = animation.animate(setting.get() ? 1 : 0, 2.0, Easings.CIRC_OUT); // Increased duration to 2 seconds
        alphaAnimation = alphaAnimation.animate(setting.get() ? 255 : 0, 2.0, Easings.CIRC_OUT); // Increased duration to 2 seconds
    }

    private Animation animation = new Animation();
    private Animation alphaAnimation = new Animation();
    private float width, height;
    private boolean hovered = false;

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        super.render(stack, mouseX, mouseY);
        animation.update();
        alphaAnimation.update();
        Fonted.msMedium[15].drawString(stack, setting.getName(), getX() + 5, getY() + 6.5f / 2f + 2, ColorUtils.rgb(160, 163, 175));

        width = 12;
        height = 12;
        float checkboxX = getX() + getWidth() - width - 7;
        float checkboxY = getY() + getHeight() / 2f - height / 2f;

        DisplayUtils.drawShadow(checkboxX, checkboxY, width, height, 10,  ColorUtils.rgb(10, 10, 12));
        DisplayUtils.drawRoundedRect(checkboxX, checkboxY, width, height, 2, new Color(49, 49, 49, 250).getRGB());
        if (setting.get()) {
            int alpha = (int) alphaAnimation.getValue();
            final ResourceLocation logo = new ResourceLocation("resolute/images/checkmark.png");
            DisplayUtils.drawImage(logo, checkboxX + width / 2f - 9, checkboxY + height / 2f - Fonts.montserrat.getHeight(10) / 2f - 6, 20, 20,new Color(255, 255, 255,alpha).getRGB());
        }

        if (isHovered(mouseX, mouseY)) {
            if (MathUtil.isHovered(mouseX, mouseY, checkboxX, checkboxY, width, height)) {
                if (!hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
                    hovered = true;
                }
            } else {
                if (hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                    hovered = false;
                }
            }
        }
    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int mouse) {
        float checkboxX = getX() + getWidth() - width - 7;
        float checkboxY = getY() + getHeight() / 2f - height / 2f;

        if (MathUtil.isHovered(mouseX, mouseY, checkboxX, checkboxY, width, height)) {
            setting.set(!setting.get());
            animation = animation.animate(setting.get() ? 1 : 0, 2.0, Easings.CIRC_OUT); // Increased duration to 2 seconds
            alphaAnimation = alphaAnimation.animate(setting.get() ? 255 : 0, 2.0, Easings.CIRC_OUT); // Increased duration to 2 seconds
        }
        super.mouseClick(mouseX, mouseY, mouse);
    }

    @Override
    public boolean isVisible() {
        return setting.visible.get();
    }
}
