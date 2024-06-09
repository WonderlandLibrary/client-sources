package ez.cloudclient.setting.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ez.cloudclient.setting.Setting;

import java.util.Objects;

public class BooleanSetting extends Setting {
    @Expose
    @SerializedName("Current Value")
    private Boolean value;

    public BooleanSetting(Boolean defaultValue) {
        value = defaultValue;
    }

    @Override
    public String toString() {
        return "\"BooleanSetting\": {" + "\"value\": \"" + value + "\"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanSetting)) return false;
        BooleanSetting that = (BooleanSetting) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
