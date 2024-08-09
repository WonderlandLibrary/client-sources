package xyz.mathax.mathaxclient.settings;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.function.Consumer;

public class StatusEffectAmplifierMapSetting extends Setting<Object2IntMap<StatusEffect>> {
    public StatusEffectAmplifierMapSetting(String name, String description, Object2IntMap<StatusEffect> defaultValue, Consumer<Object2IntMap<StatusEffect>> onChanged, Consumer<Setting<Object2IntMap<StatusEffect>>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    public void resetImpl() {
        value = new Object2IntArrayMap<>(defaultValue);
    }

    @Override
    protected Object2IntMap<StatusEffect> parseImpl(String string) {
        String[] values = string.split(",");
        Object2IntMap<StatusEffect> effects = Utils.createStatusEffectMap();

        try {
            for (String value : values) {
                String[] split = value.split(" ");

                StatusEffect effect = parseId(Registries.STATUS_EFFECT, split[0]);
                int level = Integer.parseInt(split[1]);

                effects.put(effect, level);
            }
        } catch (Exception ignored) {}

        return effects;
    }

    @Override
    protected boolean isValueValid(Object2IntMap<StatusEffect> value) {
        return true;
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (StatusEffect statusEffect : get().keySet()) {
            Identifier id = Registries.STATUS_EFFECT.getId(statusEffect);
            if (id != null) {
                JSONObject valueJson = new JSONObject();
                valueJson.put("id", id);
                valueJson.put("key", get().getInt(statusEffect));
                json.append("value", valueJson);
            }
        }

        return json;
    }

    @Override
    public Object2IntMap<StatusEffect> load(JSONObject json) {
        get().clear();

        if (json.has("value ") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof JSONObject valueJson) {
                    if (valueJson.has("id") && valueJson.has("key")) {
                        StatusEffect statusEffect = Registries.STATUS_EFFECT.get(new Identifier(valueJson.getString("id")));
                        if (statusEffect != null) {
                            get().put(statusEffect, valueJson.getInt("key"));
                        }
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, Object2IntMap<StatusEffect>, StatusEffectAmplifierMapSetting> {
        public Builder() {
            super(new Object2IntArrayMap<>(0));
        }

        @Override
        public StatusEffectAmplifierMapSetting build() {
            return new StatusEffectAmplifierMapSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}
