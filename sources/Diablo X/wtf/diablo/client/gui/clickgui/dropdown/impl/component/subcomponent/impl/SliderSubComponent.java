package wtf.diablo.client.gui.clickgui.dropdown.impl.component.subcomponent.impl;

import wtf.diablo.client.gui.clickgui.dropdown.impl.component.subcomponent.api.AbstractSubComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.impl.NumberSettingComponent;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

public final class SliderSubComponent extends AbstractSubComponent<Number> {
    private final static Color BACKGROUND_COLOR = new Color(135, 135, 135, 255);
    private final static Color SLIDER_COLOR = new Color(255, 255, 255, 255);
    private final NumberSettingComponent setting;
    private boolean sliding;

    SliderSubComponent(final Builder builder) {
        super(builder.x, builder.y, builder.width, builder.height);
        this.setting = builder.setting;
        this.value = setting.getSetting().getValue();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final int x = getX() + setting.getModulePanelComponent().getParent().getX();
        final int y = getY() + setting.getModulePanelComponent().getParent().getPanelY() + setting.getModulePanelComponent().getParent().getY();
        final int width = getWidth();

        final NumberSetting<?> number = setting.getSetting();

        if (sliding) {
            double getValue = mouseX - x;

            boolean maximum = number.getMinimum().doubleValue() + ((number.getMaximum().doubleValue() - number.getMinimum().doubleValue()) / width) * getValue < number.getMaximum().doubleValue();
            boolean minimum = number.getMinimum().doubleValue() + ((number.getMaximum().doubleValue() - number.getMinimum().doubleValue()) / width) * getValue > number.getMinimum().doubleValue();

            if (minimum && maximum)
                setValue(number,number.getMinimum().doubleValue() + ((number.getMaximum().doubleValue() - number.getMinimum().doubleValue()) / width) * getValue);
            else if (minimum)
                setValue(number, number.getMaximum().doubleValue());
            else if (maximum)
                setValue(number, number.getMinimum().doubleValue());
        }

        RenderUtil.drawRoundedRectangle(x, y, width, height - 3.5, 1, BACKGROUND_COLOR.getRGB());
        RenderUtil.drawRoundedRectangle(x, y, (int) (width * ((number.getValue().doubleValue() - number.getMinimum().doubleValue()) / (number.getMaximum().doubleValue() - number.getMinimum().doubleValue()))), height - 3.5, 2, ColorUtil.AMBIENT_COLOR.getValue().getRGB());
        RenderUtil.drawRoundedRectangle(x + (int) (width * ((number.getValue().doubleValue() - number.getMinimum().doubleValue()) / (number.getMaximum().doubleValue() - number.getMinimum().doubleValue()))) - 1, y - 2, 4, 4, 2, new Color(-1).getRGB());

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final int x = getX() + setting.getModulePanelComponent().getParent().getX();
        final int y = getY() + setting.getModulePanelComponent().getPanelY() + setting.getModulePanelComponent().getParent().getY();
        if(RenderUtil.isHovered(mouseX,mouseY,x - 1,y - 1,x + width + 1, y + height + 1)) {
            sliding = !sliding;
        } else {
            sliding = false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        sliding = false;
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private NumberSettingComponent setting;
        private int x;
        private int y;
        private int width;
        private int height;

        public Builder setting(final NumberSettingComponent setting) {
            this.setting = setting;
            return this;
        }

        public Builder x(final int x) {
            this.x = x;
            return this;
        }

        public Builder y(final int y) {
            this.y = y;
            return this;
        }

        public Builder width(final int width) {
            this.width = width;
            return this;
        }

        public Builder height(final int height) {
            this.height = height;
            return this;
        }

        public SliderSubComponent build() {
            return new SliderSubComponent(this);
        }
    }
}
