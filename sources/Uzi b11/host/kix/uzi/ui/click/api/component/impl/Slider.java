package host.kix.uzi.ui.click.api.component.impl;

import host.kix.uzi.ui.click.api.component.Component;
import host.kix.uzi.ui.click.impl.GuiClick;

/**
 * @author Marc
 * @since 7/21/2016
 */
public class Slider extends Component {

    /**
     * The number value of the slider.
     */
    private Number value;

    /**
     * The minimum and maximum bounds of the slider.
     */
    private double min, max;

    /**
     * The x coordinate of the slider.
     */
    private Number sliderX;

    /**
     * The completion of the slider.
     */
    private double completion;

    /**
     * Whether the slider is being dragged.
     */
    private boolean dragging;

    public Slider(GuiClick parent, String label, int x, int y, int width, int height) {
        super(parent, label, x, y, width, height);
    }

    @Override
    public void draw(int x, int y) {
        parent.getTheme().drawComponent(this, x, y);
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        if (isHovering(x, y)) {
            switch (button) {
                case 0:
                    dragging = true;
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        dragging = false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }

    /**
     * Returns the value of the slider.
     *
     * @return value of the slider
     */
    public Number getSliderValue() {
        return value;
    }

    /**
     * Sets the value of the slider to the specified value.
     *
     * @param value to set
     */
    public void setSliderValue(Number value) {
        this.value = value;
    }

    /**
     * Returns the minimum value of the slider.
     *
     * @return minimum value of the slider
     */
    public double getMin() {
        return min;
    }

    /**
     * Sets the minimum value of the slider to the specified minimum.
     *
     * @param min to set
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * Returns the maximum value of the slider.
     *
     * @return maximum value of the slider
     */
    public double getMax() {
        return max;
    }

    /**
     * Sets the maximum value of the slider to the specified maximum value.
     *
     * @param max to set
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * Returns the x coordinate of the slider.
     *
     * @return x coordinate of the slider
     */
    public Number getSliderX() {
        return sliderX;
    }

    /**
     * Sets the x coordinate of the slider to the specified x coordinate.
     *
     * @param sliderX to set
     */
    public void setSliderX(Number sliderX) {
        this.sliderX = sliderX;
    }

    /**
     * Returns the completion of the slider.
     *
     * @return completion of the slider
     */
    public double getCompletion() {
        return completion;
    }

    /**
     * Sets the completion of the slider to the specified completion.
     *
     * @param completion to set
     */
    public void setCompletion(double completion) {
        this.completion = completion;
    }

    /**
     * Returns whether the slider is being dragged.
     *
     * @return whether the slider is being dragged
     */
    public boolean isDragging() {
        return dragging;
    }

    /**
     * Sets the dragging state of the slider to the specified state.
     *
     * @param dragging state to set
     */
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
}
