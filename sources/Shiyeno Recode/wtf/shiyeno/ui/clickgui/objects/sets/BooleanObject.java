package wtf.shiyeno.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.ui.clickgui.objects.ModuleObject;
import wtf.shiyeno.ui.clickgui.objects.Object;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.animation.AnimationMath;

public class BooleanObject extends Object {

    public ModuleObject object;
    public BooleanOption set;
    public float enabledAnimation;

    public BooleanObject(ModuleObject object, BooleanOption set) {
        this.object = object;
        this.set = set;
        setting = set;
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        y -= 1;
        double max = !set.get() ? -1.5f : 6.5f;
        this.enabledAnimation = AnimationMath.fast(enabledAnimation, (float) max, 10);

        int textColor = ColorUtil.rgba(100, 100, 100, 255);
        Fonts.msBold[15].drawString(stack, set.getName(), x + 10, y + height / 2f - Fonts.msBold[15].getFontHeight() / 2f + 2, textColor);

        RenderUtil.Render2D.drawRoundedRect(x + width - 23.5f, y + 5, 11.5f, 6, 3, ColorUtil.rgba(30, 30, 30, 255));
        int color = set.get() ? ColorUtil.rgba(200, 200, 200, 255) : ColorUtil.rgba(100, 100, 100, 255);

        RenderUtil.Render2D.drawRoundCircle(x + width - 23 + 3 + enabledAnimation, y + 8, 7, color);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (isHovered(mouseX, mouseY)) {
                set.toggle();
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