package client.metaware.api.clickgui.component.implementations;

import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.utils.render.RenderUtil;
import net.minecraft.util.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SliderComponent extends SettingComponent<DoubleProperty> {

    private boolean sliding;

    public SliderComponent(DoubleProperty setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
    }

    public SliderComponent(DoubleProperty setting, float x, float y, float width, float height, boolean visible) {
        super(setting, x, y, width, height, visible);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if(!visible) return;
        double deltaMaxMin = setting.getMax() - setting.getMin();
        float startX = x;
        float length = MathHelper.floor_double((setting.getValue() - setting.getMin()) / deltaMaxMin * width);
        if(sliding) {
            setting.setValue(round(((mouseX - startX) * deltaMaxMin / width + setting.getMin()), 2));
        }
        theme.drawSliderComponent(this, x, y, width, height, length);
    }

    private double round(final double val, final int places) {
        final double value = Math.round(val / setting.increment()) * setting.increment();
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (visible && !sliding && mouseButton == 0 && RenderUtil.isHovered(x, y, width, height, mouseX, mouseY))
            sliding = true;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        sliding = false;
        if(!visible) return;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }
}
