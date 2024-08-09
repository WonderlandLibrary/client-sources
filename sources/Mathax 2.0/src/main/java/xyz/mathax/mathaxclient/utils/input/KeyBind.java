package xyz.mathax.mathaxclient.utils.input;

import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.ICopyable;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import org.json.JSONObject;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

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
        if (isKey) {
            return value != GLFW_KEY_ESCAPE;
        }

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
        if (this.isKey != isKey) {
            return false;
        }

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
        if (value == -1) {
            return "None";
        }

        return isKey ? Utils.getKeyName(value) : Utils.getButtonName(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        KeyBind keybind = (KeyBind) object;
        return isKey == keybind.isKey && value == keybind.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isKey, value);
    }

    // Serialization

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("is-key", isKey);
        json.put("value", value);
        return json;
    }

    @Override
    public KeyBind fromJson(JSONObject json) {
        isKey = json.getBoolean("is-key");
        value = json.getInt("value");

        return this;
    }
}
