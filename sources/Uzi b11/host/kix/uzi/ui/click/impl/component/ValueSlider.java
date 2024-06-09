package host.kix.uzi.ui.click.impl.component;

import host.kix.uzi.ui.click.api.component.impl.Slider;
import host.kix.uzi.utilities.value.Value;
import host.kix.uzi.ui.click.impl.GuiClick;

/**
 * @author Marc
 * @since 7/22/2016
 */
public class ValueSlider extends Slider {

    private Value value;

    public ValueSlider(GuiClick parent, Value value, int x, int y, int width, int height) {
        super(parent, value.getName(), x, y, width, height);
        this.value = value;
    }

    @Override
    public void draw(int x, int y) {
        super.draw(x, y);

        if (isDragging()) {
            double increment = 0.1;
            double mousePos = x - getX();
            double percentage = Math.min(Math.max(0.0, mousePos / getWidth()), 1.0);
            double completion = (getMax() - getMin()) * percentage;
            double value = Math.round((getMin() + completion) * (1.0 / increment)) / (1.0 / increment);

            if (this.value.getValue() instanceof Double) {
                this.value.setValue((double) value);
            } else if (this.value.getValue() instanceof Float) {
                this.value.setValue((float) value);
            } else if (this.value.getValue() instanceof Integer) {
                this.value.setValue((int) value);
            } else if (this.value.getValue() instanceof Long) {
                this.value.setValue((long) value);
            }
        }

        double completion;

        if (value.getValue() instanceof Double) {
            setMin((Double) value.getMin());
            setMax((Double) value.getMax());

            completion = (Double) value.getValue() - getMin();
        } else if (value.getValue() instanceof Float) {
            setMin((Float) value.getMin());
            setMax((Float) value.getMax());

            completion = (Float) value.getValue() - getMin();
        } else if (value.getValue() instanceof Integer) {
            setMin((Integer) value.getMin());
            setMax((Integer) value.getMax());

            completion = (Integer) value.getValue() - getMin();
        } else if(value.getValue() instanceof Long){
            setMin((Long) value.getMin());
            setMax((Long) value.getMax());

            completion = (Long) value.getValue() - getMin();
        }else{
            setMin(0);
            completion = 0D;
        }

        double percentage = completion / (getMax() - getMin());

        setCompletion(getWidth() * percentage);
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);

        if (isHovering(x, y)) {
            switch (button) {
                case 2:
                    this.value.setValue(this.value.getDefault());
                    break;
            }
        }
    }

    @Override
    public Number getSliderValue() {
        return (Number) value.getValue();
    }

    public Value getValue() {
        return value;
    }

}
