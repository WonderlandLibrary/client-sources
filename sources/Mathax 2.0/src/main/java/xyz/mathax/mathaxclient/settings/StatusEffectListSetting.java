package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class StatusEffectListSetting extends Setting<List<StatusEffect>> {
    public StatusEffectListSetting(String name, String description, List<StatusEffect> defaultValue, Consumer<List<StatusEffect>> onChanged, Consumer<Setting<List<StatusEffect>>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    public void resetImpl() {
        value = new ArrayList<>(defaultValue);
    }

    @Override
    protected List<StatusEffect> parseImpl(String string) {
        String[] values = string.split(",");
        List<StatusEffect> effects = new ArrayList<>(values.length);

        try {
            for (String value : values) {
                StatusEffect effect = parseId(Registries.STATUS_EFFECT, value);
                if (effect != null) {
                    effects.add(effect);
                }
            }
        } catch (Exception ignored) {}

        return effects;
    }

    @Override
    protected boolean isValueValid(List<StatusEffect> value) {
        return true;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.STATUS_EFFECT.getIds();
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (StatusEffect effect : get()) {
            Identifier id = Registries.STATUS_EFFECT.getId(effect);
            if (id != null) {
                json.append("value", id.toString());
            }
        }

        return json;
    }

    @Override
    public List<StatusEffect> load(JSONObject json) {
        get().clear();

        if (json.has("value ") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String id) {
                    StatusEffect effect = Registries.STATUS_EFFECT.get(new Identifier(id));
                    if (effect != null) {
                        get().add(effect);
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, List<StatusEffect>, StatusEffectListSetting> {
        public Builder() {
            super(new ArrayList<>(0));
        }

        public Builder defaultValue(StatusEffect... defaults) {
            return defaultValue(defaults != null ? Arrays.asList(defaults) : new ArrayList<>());
        }

        @Override
        public StatusEffectListSetting build() {
            return new StatusEffectListSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}
