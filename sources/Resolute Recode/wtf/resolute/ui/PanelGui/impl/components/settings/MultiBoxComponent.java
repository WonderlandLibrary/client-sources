package wtf.resolute.ui.PanelGui.impl.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.ModeListSetting;
import wtf.resolute.ui.PanelGui.impl.Component;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.Cursors;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.font.Fonts;
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
        Fonted.msMedium[14].drawString(stack, setting.getName(), getX() + 5, getY() + 2, ColorUtils.rgb(160, 163, 175));
        DisplayUtils.drawShadow(getX() + 5, getY() + 9, width + 5, 10 + heightPadding, 10, ColorUtils.rgb(10, 10, 12));
        DisplayUtils.drawRoundedRect(getX() + 5, getY() + 9, width + 5, 10 + heightPadding, 2, ColorUtils.rgba(50,50,50,255));

        float offset = 0;
        float heightoff = 0;
        boolean plused = false;
        boolean anyHovered = false;
        for (BooleanSetting text : setting.get()) {
            float off = Fonted.gilroyBold[13].getWidth(text.getName()) + 2;
            if (offset + off >= (getWidth() - 10)) {
                offset = 0;
                heightoff += 8;
                plused = true;
            }
            if (MathUtil.isHovered(mouseX, mouseY, getX() + 8 + offset, getY() + 11.5f + heightoff,
                    Fonted.gilroyBold[13].getWidth(text.getName()), Fonts.montserrat.getHeight(5.5f) + 1)) {
                anyHovered = true;
            }
            if (text.get()) {
                Fonted.gilroyBold[13].drawString(stack, text.getName(), getX() + 8 + offset, getY() + 11.5f + heightoff,
                        -1);
            } else {
                Fonted.gilroyBold[13].drawString(stack, text.getName(), getX() + 8 + offset, getY() + 11.5f + heightoff,
                        ColorUtils.rgb(160, 163, 175));
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
            float off = Fonted.gilroyBold[13].getWidth(text.getName()) + 2;
            if (offset + off >= (getWidth() - 10)) {
                offset = 0;
                heightoff += 8;
            }
            if (MathUtil.isHovered(mouseX, mouseY, getX() + 8 + offset, getY() + 11.5f + heightoff,
                    Fonted.gilroyBold[13].getWidth(text.getName()), Fonts.montserrat.getHeight(5.5f) + 1)) {
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
