package im.expensive.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.ui.dropdown.impl.Component;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.Cursors;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class MultiBoxComponent extends Component {

    final ModeListSetting setting;

    float width = 0;
    float heightPadding = 0;

    public MultiBoxComponent(ModeListSetting setting) {
        this.setting = setting;
        setHeight(22);
    }

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        super.render(stack, mouseX, mouseY);
        Fonts.montserrat.drawText(stack, setting.getName(), getX() + 5, getY() + 2, ColorUtils.rgb(46, 47, 51), 5.5f,
                0.05f);
        DisplayUtils.drawShadow(getX() + 5, getY() + 9, width + 5, 10 + heightPadding, 10, ColorUtils.rgb(10, 10, 12));

        DisplayUtils.drawRoundedRect(getX() + 5, getY() + 9, width + 5, 10 + heightPadding, 2, ColorUtils.rgb(10, 10, 12));

        float offset = 0;
        float heightoff = 0;
        boolean plused = false;
        boolean anyHovered = false;
        for (BooleanSetting text : setting.get()) {
            float off = Fonts.montserrat.getWidth(text.getName(), 5.5f, 0.05f) + 2;
            if (offset + off >= (getWidth() - 10)) {
                offset = 0;
                heightoff += 8;
                plused = true;
            }
            if (MathUtil.isHovered(mouseX, mouseY, getX() + 8 + offset, getY() + 11.5f + heightoff,
                    Fonts.montserrat.getWidth(text.getName(), 5.5f, 0.05f), Fonts.montserrat.getHeight(5.5f) + 1)) {
                anyHovered = true;
            }
            if (text.get()) {
                Fonts.montserrat.drawText(stack, text.getName(), getX() + 8 + offset, getY() + 11.5f + heightoff,
                        ColorUtils.rgb(114, 118, 134),
                        5.5f, 0.07f);
            } else {
                Fonts.montserrat.drawText(stack, text.getName(), getX() + 8 + offset, getY() + 11.5f + heightoff,
                        ColorUtils.rgb(46, 47, 51),
                        5.5f, 0.05f);
            }
            offset += off;
        }
        if (isHovered(mouseX, mouseY)) {
            if (anyHovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
            } else {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            }
        }
        width = plused ? getWidth() - 15 : offset;
        setHeight(22 + heightoff);
        heightPadding = heightoff;
    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int mouse) {

        float offset = 0;
        float heightoff = 0;
        for (BooleanSetting text : setting.get()) {
            float off = Fonts.montserrat.getWidth(text.getName(), 5.5f, 0.05f) + 2;
            if (offset + off >= (getWidth() - 10)) {
                offset = 0;
                heightoff += 8;
            }
            if (MathUtil.isHovered(mouseX, mouseY, getX() + 8 + offset, getY() + 11.5f + heightoff,
                    Fonts.montserrat.getWidth(text.getName(), 5.5f, 0.05f), Fonts.montserrat.getHeight(5.5f) + 1)) {
                text.set(!text.get());
            }
            offset += off;
        }


        super.mouseClick(mouseX, mouseY, mouse);
    }

    @Override
    public boolean isVisible() {
        return setting.visible.get();
    }

}
