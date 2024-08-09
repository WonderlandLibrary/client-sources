package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SoundEventListSetting extends Setting<List<SoundEvent>> {
    public SoundEventListSetting(String name, String description, List<SoundEvent> defaultValue, Consumer<List<SoundEvent>> onChanged, Consumer<Setting<List<SoundEvent>>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    public void resetImpl() {
        value = new ArrayList<>(defaultValue);
    }

    @Override
    protected List<SoundEvent> parseImpl(String string) {
        String[] values = string.split(",");
        List<SoundEvent> sounds = new ArrayList<>(values.length);

        try {
            for (String value : values) {
                SoundEvent sound = parseId(Registries.SOUND_EVENT, value);
                if (sound != null) {
                    sounds.add(sound);
                }
            }
        } catch (Exception ignored) {}

        return sounds;
    }

    @Override
    protected boolean isValueValid(List<SoundEvent> value) {
        return true;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.SOUND_EVENT.getIds();
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (SoundEvent sound : get()) {
            Identifier id = Registries.SOUND_EVENT.getId(sound);
            if (id != null) {
                json.append("value", id.toString());
            }
        }

        return json;
    }

    @Override
    public List<SoundEvent> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String id) {
                    SoundEvent soundEvent = Registries.SOUND_EVENT.get(new Identifier(id));
                    if (soundEvent != null) {
                        get().add(soundEvent);
                    }
                }
            }
        }

        return get();
    }



    public static class Builder extends SettingBuilder<Builder, List<SoundEvent>, SoundEventListSetting> {
        public Builder() {
            super(new ArrayList<>(0));
        }

        public Builder defaultValue(SoundEvent... defaults) {
            return defaultValue(defaults != null ? Arrays.asList(defaults) : new ArrayList<>());
        }

        @Override
        public SoundEventListSetting build() {
            return new SoundEventListSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}
