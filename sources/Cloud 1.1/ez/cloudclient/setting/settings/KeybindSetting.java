package ez.cloudclient.setting.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ez.cloudclient.setting.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyBindingMap;

import java.util.Objects;

public class KeybindSetting extends Setting {
    public static KeyBindingMap BIND_MAP = new KeyBindingMap();

    @Expose
    @SerializedName("Key")
    private int key;
    private String keyName;

    public KeybindSetting(int defaultKey) {
        key = defaultKey;
        keyName = getKeyNameFromInt();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
        keyName = getKeyNameFromInt();
    }

    public String getKeyName() {
        return keyName;
    }

    public String getKeyNameFromInt() {
        KeyBinding keyBinding = BIND_MAP.lookupActive(key);
        if (keyBinding != null) {
            return keyBinding.getDisplayName();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "KeybindSetting{" +
                "key=" + key +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeybindSetting)) return false;
        KeybindSetting that = (KeybindSetting) o;
        return key == that.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
