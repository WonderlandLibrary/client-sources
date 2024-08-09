package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ParticleTypeListSetting extends Setting<List<ParticleType<?>>> {
    public ParticleTypeListSetting(String name, String description, List<ParticleType<?>> defaultValue, Consumer<List<ParticleType<?>>> onChanged, Consumer<Setting<List<ParticleType<?>>>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    public void resetImpl() {
        value = new ArrayList<>(defaultValue);
    }

    @Override
    protected List<ParticleType<?>> parseImpl(String string) {
        String[] values = string.split(",");
        List<ParticleType<?>> particleTypes = new ArrayList<>(values.length);

        try {
            for (String value : values) {
                ParticleType<?> particleType = parseId(Registries.PARTICLE_TYPE, value);
                if (particleType instanceof ParticleEffect) {
                    particleTypes.add(particleType);
                }
            }
        } catch (Exception ignored) {}

        return particleTypes;
    }

    @Override
    protected boolean isValueValid(List<ParticleType<?>> value) {
        return true;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.PARTICLE_TYPE.getIds();
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (ParticleType<?> particleType : get()) {
            Identifier id = Registries.PARTICLE_TYPE.getId(particleType);
            if (id != null) {
                json.append("value", id.toString());
            }
        }

        return json;
    }

    @Override
        public List<ParticleType<?>> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String id) {
                    ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(new Identifier(id));
                    if (particleType != null) {
                        get().add(particleType);
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, List<ParticleType<?>>, ParticleTypeListSetting> {
        public Builder() {
            super(new ArrayList<>(0));
        }

        public Builder defaultValue(ParticleType<?>... defaults) {
            return defaultValue(defaults != null ? Arrays.asList(defaults) : new ArrayList<>());
        }

        @Override
        public ParticleTypeListSetting build() {
            return new ParticleTypeListSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}
