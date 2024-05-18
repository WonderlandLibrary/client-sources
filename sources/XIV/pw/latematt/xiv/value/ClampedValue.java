package pw.latematt.xiv.value;

/**
 * @author TehNeon
 */
public final class ClampedValue<T> extends Value<T> {
    private T min;
    private T max;
    private float sliderX;

    public ClampedValue(String name, T value, T min, T max) {
        super(name, value);

        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public float getSliderX() {
        return sliderX;
    }

    public void setSliderX(float sliderX) {
        this.sliderX = sliderX;
    }
}
