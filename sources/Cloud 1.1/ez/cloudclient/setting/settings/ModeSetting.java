package ez.cloudclient.setting.settings;


import com.google.gson.annotations.SerializedName;
import ez.cloudclient.setting.Setting;

import java.util.Arrays;
import java.util.Objects;

public class ModeSetting extends Setting {
    @SerializedName("Mode")
    private final String[] modes;

    @SerializedName("Current Mode")
    private int currentMode;

    public ModeSetting(String... modes) {
        this.modes = modes;
        this.currentMode = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModeSetting)) return false;
        ModeSetting that = (ModeSetting) o;
        return currentMode == that.currentMode &&
                Arrays.equals(modes, that.modes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentMode);
        result = 31 * result + Arrays.hashCode(modes);
        return result;
    }

    @Override
    public String toString() {
        return "\"ModeSetting\": {" +
                "\"modes\": " + Arrays.toString(modes) +
                ", \"currentMode\": \"" + currentMode +
                "\"}";
    }

    public String currentMode() {
        return modes[currentMode];
    }

    public void incrementMode() {
        currentMode++;
        if (currentMode > modes.length - 1) {
            currentMode = 0;
        }
    }

    public void setMode(String mode) {
        currentMode = Arrays.asList(modes).indexOf(mode);
    }
}
