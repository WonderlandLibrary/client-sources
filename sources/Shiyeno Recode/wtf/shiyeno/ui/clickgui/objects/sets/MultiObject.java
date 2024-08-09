package wtf.shiyeno.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;
import wtf.shiyeno.ui.clickgui.objects.ModuleObject;
import wtf.shiyeno.ui.clickgui.objects.Object;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;

public class MultiObject extends Object {

    public MultiBoxSetting set;
    public ModuleObject object;

    public MultiObject(ModuleObject object, MultiBoxSetting set) {
        this.object = object;
        this.set = set;
        setting = set;
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        int offset = 0;
        float offsetY = 0;
        int lines = 1;
        float size = 0;
        for (BooleanOption mode : set.options) {
            float preOffset = size + Fonts.msBold[11].getWidth(mode.getName()) + 3;
            if (preOffset > width - 20) {
                break;
            }
            size += Fonts.msBold[11].getWidth(mode.getName()) + 3;
        }

        for (BooleanOption mode : set.options) {
            float preOffset = offset + Fonts.msBold[11].getWidth(mode.getName()) + 3;
            if (preOffset > size) {
                lines++;
                offset = 0;
            }
            offset += Fonts.msBold[11].getWidth(mode.getName()) + 3;
        }

        height += 8;
        int textColor = ColorUtil.rgba(100, 100, 100, 255);
        Fonts.msBold[12].drawString(stack, set.getName(), x + 10, y + height / 2f - 10, textColor);

        RenderUtil.Render2D.drawRoundedCorner(x + 10, y + 9, size + 7, 11 * lines, 3, ColorUtil.rgba(30, 30, 30, 255));
        height += 11 * (lines - 1);
        offset = 0;
        offsetY = 0;
        int i = 0;
        for (BooleanOption mode : set.options) {

            float preOff = offset + Fonts.msBold[11].getWidth(mode.getName()) + 3;
            if (preOff > size) {
                offset = 0;
                offsetY += 11;
            }
            int color = set.get(i) ? ColorUtil.rgba(200, 200, 200, 255) : ColorUtil.rgba(100, 100, 100, 255);
            Fonts.msBold[11].drawString(stack, mode.getName(), x + 15 + offset, y + 14f + offsetY, color);
            offset += Fonts.msBold[11].getWidth(mode.getName()) + 3;
            i++;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float offset = 0;
        float offsetY = 0;
        int i = 0;
        float size = 0;
        for (BooleanOption mode : set.options) {

            float preOffset = size + Fonts.msBold[11].getWidth(mode.getName()) + 3;
            if (preOffset > width - 20) {
                break;
            }
            size += Fonts.msBold[11].getWidth(mode.getName()) + 3;
        }

        for (BooleanOption mode : set.options) {
            float preOff = offset + Fonts.msBold[11].getWidth(mode.getName()) + 3;
            if (preOff > size) {
                offset = 0;
                offsetY += 11;
            }

            if (RenderUtil.isInRegion(mouseX, mouseY, x + 15 + offset, y + 12f + offsetY, Fonts.msBold[11].getWidth(mode.getName()), Fonts.msBold[11].getFontHeight() / 2f + 3)) {
                set.set(i, !set.get(i));
            }
            offset += Fonts.msBold[11].getWidth(mode.getName()) + 3;
            i++;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
    }
}