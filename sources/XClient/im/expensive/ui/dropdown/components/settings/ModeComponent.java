package im.expensive.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.ui.dropdown.impl.Component;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.Cursors;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import im.expensive.utils.text.GradientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;

public class ModeComponent extends Component {

    final ModeSetting setting;

    float width = 0;
    float heightplus = 0;

    public ModeComponent(ModeSetting setting) {
        this.setting = setting;
        setHeight(22);
    }


    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        super.render(stack, mouseX, mouseY);
        Fonts.sfMedium.drawText(stack, setting.getName(), getX() + 7, getY() + 2, ColorUtils.rgb(82, 82, 82), 5.5f,
                0.05f);
        DisplayUtils.drawRoundedRect(getX() + 7, getY() + 9, width, 10 + heightplus, 3, ColorUtils.rgb(20, 20, 20));

        float offset = 0;
        float heightoff = 0;
        boolean plused = false;
        boolean anyHovered = false;
        for (String text : setting.strings) {
            float off = Fonts.sfMedium.getWidth(text, 5.5f, 0.05f) + 2;
            if (offset + off >= (getWidth() - 10)) {
                offset = 0;
                heightoff += 8;
                plused = true;
            }

            ITextComponent PisatDva = GradientUtil.gradient(text);
            if (MathUtil.isHovered(mouseX, mouseY, getX() + 8 + offset, getY() + 11.5f + heightoff,
                    Fonts.sfMedium.getWidth(PisatDva.getString(), 5.5f, 0.05f), Fonts.sfMedium.getHeight(5.5f) + 1)) {
                anyHovered = true;
            }
            if (text.equals(setting.get())) {
                Fonts.sfMedium.drawText(stack, PisatDva, getX() + 8 + offset, getY() + 11.5f + heightoff, 5.5f, 255);
            } else {
                Fonts.sfMedium.drawText(stack, PisatDva.getString(), getX() + 8 + offset, getY() + 11.5f + heightoff,
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
        heightplus = heightoff;
    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int mouse) {

        float offset = 0;
        float heightoff = 0;
        for (String text : setting.strings) {
            float off = Fonts.sfMedium.getWidth(text, 5.5f, 0.05f) + 2;
            if (offset + off >= (getWidth() - 10)) {
                offset = 0;
                heightoff += 8;
            }
            if (MathUtil.isHovered(mouseX, mouseY, getX() + 8 + offset, getY() + 11.5f + heightoff,
                    Fonts.sfMedium.getWidth(text, 5.5f, 0.05f), Fonts.sfMedium.getHeight(5.5f) + 1)) {
                setting.set(text);
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
