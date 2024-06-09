package ez.cloudclient.setting.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class NumberValueSetting extends ValueSetting<Double> {
    @Expose
    @SerializedName("Minimum Value")
    protected Double min;

    @Expose
    @SerializedName("Maximum Value")
    protected Double max;

    public NumberValueSetting(Double defaultValue, Double min, Double max) {
        super(defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberValueSetting)) return false;
        if (!super.equals(o)) return false;
        NumberValueSetting that = (NumberValueSetting) o;
        return Objects.equals(getMin(), that.getMin()) &&
                Objects.equals(getMax(), that.getMax());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMin(), getMax());
    }

    @Override
    public String toString() {
        return "NumberValueSetting{" +
                "min=" + min +
                ", max=" + max +
                ", value=" + value +
                '}';
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }
}
