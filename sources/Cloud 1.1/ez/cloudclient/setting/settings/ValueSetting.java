package ez.cloudclient.setting.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ez.cloudclient.setting.Setting;

import java.util.Objects;

public class ValueSetting<T> extends Setting {
    @Expose
    @SerializedName("Current Value")
    public T value;

    public ValueSetting(T defaultValue) {
        this.value = defaultValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueSetting)) return false;
        ValueSetting<?> that = (ValueSetting<?>) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return "ValueSetting{" +
                "value=" + value +
                '}';
    }
}
