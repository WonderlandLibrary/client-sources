package wtf.shiyeno.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.shiyeno.modules.settings.imp.ButtonSetting;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.ui.clickgui.objects.ModuleObject;
import wtf.shiyeno.ui.clickgui.objects.Object;

public class ButtonObject extends Object {

    public ButtonSetting set;
    public ModuleObject object;

    public ButtonObject(ModuleObject object, ButtonSetting set) {
        this.object = object;
        this.set = set;
        setting = set;
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        Fonts.msBold[13].drawString(stack, set.getName(), x + 10, y + height / 2f - Fonts.msBold[13].getFontHeight() / 2f + 2, ColorUtil.rgba(100, 100, 100, 255));

        float wwidth = Math.max(10, Fonts.msBold[13].getWidth("Открыть") + 4);
        RenderUtil.Render2D.drawRoundedRect(x + width - wwidth - 10, y + 2, wwidth, 10, 2, ColorUtil.rgba(30, 30, 30, 255));

        Fonts.msBold[13].drawCenteredString(stack, "Открыть", x + width - wwidth - 10 + wwidth / 2f, y + height / 2f - Fonts.msBold[13].getFontHeight() / 2f + 2, ColorUtil.rgba(255, 255, 255, 255));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            float wwidth = Math.max(10, Fonts.msBold[13].getWidth("Открыть") + 4);
            if (RenderUtil.isInRegion(mouseX, mouseY, x + width - wwidth - 10, y + 2, wwidth, 10)) {
                set.getRun().run();
            }
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