package wtf.diablo.client.gui.clickgui.material.impl.setting;

import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.gui.clickgui.material.api.AbstractComponent;
import wtf.diablo.client.gui.clickgui.material.impl.module.ModulePanel;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.math.MathUtil;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;
import wtf.diablo.client.util.system.mouse.MouseUtils;

import java.awt.*;

public final class NumberSettingComponent extends AbstractComponent {

    private final NumberSetting<?> value;
    private boolean dragging;
    private float width;

    public NumberSettingComponent(final ModulePanel moduleButton, final NumberSetting abstractValue) {
        super(moduleButton, abstractValue);
        this.value = (NumberSetting) abstractValue;
    }

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        this.width = getModuleButton().getWidth() - 10;
        if (this.dragging) {
            double getValue = mouseX - x;

            boolean maximum = this.value.getMinimum().doubleValue() + ((this.value.getMaximum().doubleValue() - this.value.getMinimum().doubleValue()) / this.width) * getValue < this.value.getMaximum().doubleValue();
            boolean minimum = this.value.getMinimum().doubleValue() + ((this.value.getMaximum().doubleValue() - this.value.getMinimum().doubleValue()) / this.width) * getValue > this.value.getMinimum().doubleValue();

            if (minimum && maximum)
                setValue(this.value,this.value.getMinimum().doubleValue() + ((this.value.getMaximum().doubleValue() - this.value.getMinimum().doubleValue()) / this.width) * getValue);
            else if (minimum)
                setValue(this.value, this.value.getMaximum().doubleValue());
            else if (maximum)
                setValue(this.value, this.value.getMinimum().doubleValue());
        }

        FontRepository.SFREG18.drawString(value.getName(), x + 2, y + 2, new Color(255, 255, 255, 255).getRGB());
        FontRepository.SFREG18.drawString(String.valueOf(MathUtil.roundToPlace(value.getValue().doubleValue(), 1)), x + getModuleButton().getWidth() - FontRepository.SFREG18.getWidth(String.valueOf(MathUtil.roundToPlace(value.getValue().doubleValue(), 1))) - 2, y + 2, new Color(255, 255, 255, 185).getRGB());
        RenderUtil.drawRoundedRectangle((int) (x + 5), (int) (y + getExtendValue() - 4), (int) (width * (value.getValue().doubleValue() - value.getMinimum().doubleValue()) / (value.getMaximum().doubleValue() -
                value.getMinimum().doubleValue())), 2, 2, ColorUtil.PRIMARY_MAIN_COLOR.getRGB());
        RenderUtil.drawRoundedRectangle((float) (x + (width * (value.getValue().doubleValue() - value.getMinimum().doubleValue()) / (value.getMaximum().doubleValue() - value.getMinimum().doubleValue()))) + 2,
                (float) (y + getExtendValue() - 6), (6), (6), 3, Color.WHITE.getRGB());
        //RenderUtil.drawRect((float) (x + 1), (float) (y + getExtendValue() - 7), width + 8, 7, new Color(255,255,255,100).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtils.isHoveringCoords(this.x + 1, this.y + this.getExtendValue() - 7, this.width + 8, 7, mouseX, mouseY)) {
            this.dragging = true;
            this.getModuleButton().getModuleContainer().getMainPanel().setCanDrag(false);
        }
    }

    private void setValue(final NumberSetting<?> number, final double value) {
        if (number.getValue() instanceof Integer) {
            final NumberSetting<Integer> integer = (NumberSetting<Integer>) number;
            integer.setValue((int) value);
        } else if (number.getValue() instanceof Float) {
            final NumberSetting<Float> floatNumber = (NumberSetting<Float>) number;
            floatNumber.setValue((float) value);
        } else if (number.getValue() instanceof Double) {
            final NumberSetting<Double> doubleNumber = (NumberSetting<Double>) number;
            doubleNumber.setValue(value);
        } else if (number.getValue() instanceof Long) {
            final NumberSetting<Long> longNumber = (NumberSetting<Long>) number;
            longNumber.setValue((long) value);
        } else if (number.getValue() instanceof Short) {
            final NumberSetting<Short> shortNumber = (NumberSetting<Short>) number;
            shortNumber.setValue((short) value);
        } else if (number.getValue() instanceof Byte) {
            final NumberSetting<Byte> byteNumber = (NumberSetting<Byte>) number;
            byteNumber.setValue((byte) value);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

    @Override
    public void onGuiClosed() {
        this.dragging = false;
    }

    @Override
    public int getExtendValue() {
        return 19;
    }

}
