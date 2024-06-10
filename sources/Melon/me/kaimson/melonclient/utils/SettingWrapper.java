package me.kaimson.melonclient.utils;

import com.google.gson.*;

public class SettingWrapper
{
    public static void setValue(final Setting setting, final JsonElement element) {
        switch (setting.getType()) {
            case COLOR: {
                final JsonObject colorProperties = element.getAsJsonObject();
                int chromaSpeed = 0;
                if (colorProperties.get("chromaSpeed") != null) {
                    chromaSpeed = colorProperties.get("chromaSpeed").getAsInt();
                }
                setting.setDefault(colorProperties.get("value").getAsInt(), chromaSpeed);
                break;
            }
            case INT_SLIDER:
            case MODE: {
                setting.setDefault(element.getAsInt());
                break;
            }
            case FLOAT_SLIDER: {
                setting.setDefault(element.getAsFloat());
                break;
            }
            case CHECKBOX: {
                setting.setDefault(element.getAsBoolean());
                break;
            }
            case KEYBIND: {
                setting.setDefault(new KeyBinding(element.getAsInt()));
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + setting.getType());
            }
        }
    }
    
    public static void addKey(final JsonObject jsonObject, final String key, final Object value) {
        addProperty(jsonObject, key, value);
    }
    
    public static void addSettingKey(final JsonObject jsonObject, final Setting setting, final Object value) {
        if (setting.getType() == Setting.Type.COLOR) {
            final JsonObject colorProperties = new JsonObject();
            final ColorObject color = setting.getColorObject();
            colorProperties.addProperty("value", color.getColor());
            if (color.isChroma()) {
                colorProperties.addProperty("chromaSpeed", color.getChromaSpeed());
            }
            addProperty(jsonObject, setting.getKey(), colorProperties);
        }
        else {
            addProperty(jsonObject, setting.getKey(), value);
        }
    }
    
    private static void addProperty(final JsonObject object, final String key, final Object value) {
        if (value instanceof String) {
            object.addProperty(key, (String)value);
        }
        else if (value instanceof Number) {
            object.addProperty(key, (Number)value);
        }
        else if (value instanceof Boolean) {
            object.addProperty(key, (Boolean)value);
        }
        else if (value instanceof KeyBinding) {
            object.addProperty(key, ((KeyBinding)value).getKeyCode());
        }
        else if (value instanceof JsonObject) {
            object.add(key, (JsonElement)value);
        }
    }
}
