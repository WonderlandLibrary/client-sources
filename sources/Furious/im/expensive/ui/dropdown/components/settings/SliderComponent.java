package im.expensive.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.functions.impl.render.HUD;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.ui.dropdown.impl.Component;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.Cursors;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

/**
 * SliderComponent
 */
public class SliderComponent extends Component {

    private final SliderSetting setting;

    public SliderComponent(SliderSetting setting) {
        this.setting = setting;
        this.setHeight(18);
    }

    private float anim;
    private boolean drag;
    private boolean hovered = false;

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        super.render(stack, mouseX, mouseY);
        Fonts.montserrat.drawText(stack, setting.getName(), getX() + 5, getY() + 4.5f / 2f + 1,
                ColorUtils.rgb(255, 255, 255), 5.5f, 0.05f);
        Fonts.montserrat.drawText(stack, String.valueOf(setting.get()), getX() + getWidth() - 5 - Fonts.montserrat.getWidth(String.valueOf(setting.get()), 5.5f), getY() + 4.5f / 2f + 1,
                ColorUtils.rgb(255, 255, 255), 5.5f, 0.05f);

        DisplayUtils.drawRoundedRect(getX() + 5, getY() + 11, getWidth() - 10, 2, 0.6f, HUD.getColor(0));
        anim = MathUtil.fast(anim, (getWidth() - 10) * (setting.get() - setting.min) / (setting.max - setting.min), 20);
        float sliderWidth = anim;
        DisplayUtils.drawRoundedRect(getX() + 5, getY() + 11, sliderWidth, 2, 0.6f, ColorUtils.rgb(128, 132, 150));
        DisplayUtils.drawCircle(getX() + 5 + sliderWidth, getY() + 12, 5, ColorUtils.rgb(255, 255, 255));
        DisplayUtils.drawShadowCircle(getX() + 5 + sliderWidth, getY() + 12, 6, ColorUtils.rgba(128, 132, 150, 64));
        if (drag) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR));
            setting.set((float) MathHelper.clamp(MathUtil.round((mouseX - getX() - 5) / (getWidth() - 10) * (setting.max - setting.min) + setting.min, setting.increment), setting.min, setting.max));
        }
        if (isHovered(mouseX, mouseY)) {
            if (MathUtil.isHovered(mouseX, mouseY, getX() + 5, getY() + 10, getWidth() - 10, 3)) {
                if (!hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.RESIZEH);
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
        // TODO Auto-generated method stub
        if (MathUtil.isHovered(mouseX, mouseY, getX() + 5, getY() + 10, getWidth() - 10, 3)) {
            drag = true;
        }
        super.mouseClick(mouseX, mouseY, mouse);
    }

    @Override
    public void mouseRelease(float mouseX, float mouseY, int mouse) {
        // TODO Auto-generated method stub
        drag = false;
        super.mouseRelease(mouseX, mouseY, mouse);
    }

    @Override
    public boolean isVisible() {
        return setting.visible.get();
    }

}