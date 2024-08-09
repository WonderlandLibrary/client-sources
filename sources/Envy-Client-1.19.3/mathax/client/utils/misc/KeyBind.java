package mathax.client.utils.misc;

import mathax.client.utils.misc.input.Input;
import mathax.client.utils.Utils;
import net.minecraft.nbt.NbtCompound;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public class KeyBind implements ISerializable<KeyBind>, ICopyable<KeyBind> {
    private boolean isKey;
    private int value;

    private KeyBind(boolean isKey, int value) {
        set(isKey, value);
    }

    public static KeyBind none() {
        return new KeyBind(true, -1);
    }

    public static KeyBind fromKey(int key) {
        return new KeyBind(true, key);
    }

    public static KeyBind fromButton(int button) {
        return new KeyBind(false, button);
    }

    public int getValue() {
        return value;
    }

    public boolean isSet() {
        return value != -1;
    }

    public boolean canBindTo(boolean isKey, int value) {
        if (isKey) return true;
        return value != GLFW_MOUSE_BUTTON_LEFT && value != GLFW_MOUSE_BUTTON_RIGHT;
    }

    public void set(boolean isKey, int value) {
        this.isKey = isKey;
        this.value = value;
    }

    @Override
    public KeyBind set(KeyBind value) {
        this.isKey = value.isKey;
        this.value = value.value;

        return this;
    }

    public boolean matches(boolean isKey, int value) {
        if (this.isKey != isKey) return false;
        return this.value == value;
    }

    public boolean isValid() {
        return value != -1;
    }

    public boolean isKey() {
        return isKey;
    }

    public boolean isPressed() {
        return isKey ? Input.isKeyPressed(value) : Input.isButtonPressed(value);
    }

    @Override
    public KeyBind copy() {
        return new KeyBind(isKey, value);
    }

    @Override
    public String toString() {
        if (value == -1) return "None";
        return isKey ? Utils.getKeyName(value) : Utils.getButtonName(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        KeyBind keybind = (KeyBind) object;
        return isKey == keybind.isKey && value == keybind.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isKey, value);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putBoolean("isKey", isKey);
        tag.putInt("value", value);

        return tag;
    }

    @Override
    public KeyBind fromTag(NbtCompound tag) {
        isKey = tag.getBoolean("isKey");
        value = tag.getInt("value");

        return this;
    }
}
