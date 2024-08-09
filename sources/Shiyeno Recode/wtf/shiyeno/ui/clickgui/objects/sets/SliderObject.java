package wtf.shiyeno.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.math.MathUtil;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.ui.clickgui.objects.ModuleObject;
import wtf.shiyeno.ui.clickgui.objects.Object;
import wtf.shiyeno.util.render.animation.AnimationMath;

public class SliderObject extends Object {

    public ModuleObject object;
    public SliderSetting set;
    public boolean sliding;

    public float animatedVal;

    public SliderObject(ModuleObject object, SliderSetting set) {
        this.object = object;
        this.set = set;
        setting = set;
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        y -= 1;
        if (sliding) {
            float value = (float) ((mouseX - x - 10) / (width - 20) * (set.getMax() - set.getMin()) + set.getMin());
            value = (float) MathUtil.round(value, set.getIncrement());
            set.setValue(value);
        }
        float sliderWidth = ((set.getValue().floatValue() - set.getMin()) / (set.getMax() - set.getMin())) * (width - 20);
        animatedVal = AnimationMath.fast(animatedVal, sliderWidth, 20);
        RenderUtil.Render2D.drawRoundedRect(x + 10, y + height / 2f + 2, width - 20, 3, 1, ColorUtil.rgba(30, 30, 30, 255));
        RenderUtil.Render2D.drawRoundedRect(x + 10, y + height / 2f + 2, animatedVal, 3, 1, ColorUtil.rgba(100, 100, 100, 255));

        RenderUtil.Render2D.drawRoundCircle(x + 10 + animatedVal, y + height / 2f + 3.5f, 6, ColorUtil.rgba(100, 100, 100, 255));

        Fonts.msBold[12].drawString(stack, set.getName(), x + 10, y + height / 2f - 4, ColorUtil.rgba(100, 100, 100, 255));
        Fonts.msBold[12].drawString(stack, String.valueOf(set.getValue().floatValue()), x + width - 10 - Fonts.msBold[12].getWidth(String.valueOf(set.getValue().floatValue())), y + height / 2f - 4, ColorUtil.rgba(100, 100, 100, 255));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            sliding = true;
        }
    }

    @Override
    public void exit() {
        super.exit();
        sliding = false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        sliding = false;
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
    }
}