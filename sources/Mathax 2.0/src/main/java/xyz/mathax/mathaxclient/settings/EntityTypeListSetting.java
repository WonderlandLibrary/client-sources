package xyz.mathax.mathaxclient.settings;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.function.Consumer;

// TODO: Change onlyAttackable to a filter
public class EntityTypeListSetting extends Setting<Object2BooleanMap<EntityType<?>>> {
    public final boolean onlyAttackable;

    public EntityTypeListSetting(String name, String description, Object2BooleanMap<EntityType<?>> defaultValue, Consumer<Object2BooleanMap<EntityType<?>>> onChanged, Consumer<Setting<Object2BooleanMap<EntityType<?>>>> onModuleEnabled, IVisible visible, boolean onlyAttackable) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);

        this.onlyAttackable = onlyAttackable;
    }

    @Override
    public void resetImpl() {
        value = new Object2BooleanOpenHashMap<>(defaultValue);
    }

    @Override
    protected Object2BooleanMap<EntityType<?>> parseImpl(String string) {
        String[] values = string.split(",");
        Object2BooleanMap<EntityType<?>> entities = new Object2BooleanOpenHashMap<>(values.length);

        try {
            for (String value : values) {
                EntityType<?> entity = parseId(Registries.ENTITY_TYPE, value);
                if (entity != null) {
                    entities.put(entity, true);
                }
            }
        } catch (Exception ignored) {}

        return entities;
    }

    @Override
    protected boolean isValueValid(Object2BooleanMap<EntityType<?>> value) {
        return true;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.ENTITY_TYPE.getIds();
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (EntityType<?> entityType : get().keySet()) {
            if (get().getBoolean(entityType)) {
                json.append("value", Registries.ENTITY_TYPE.getId(entityType).toString());
            }
        }

        return json;
    }

    @Override
    public Object2BooleanMap<EntityType<?>> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String id) {
                    EntityType<?> type = Registries.ENTITY_TYPE.get(new Identifier(id));
                    if (!onlyAttackable || EntityUtils.isAttackable(type)) {
                        get().put(type, true);
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, Object2BooleanMap<EntityType<?>>, EntityTypeListSetting> {
        private boolean onlyAttackable = false;

        public Builder() {
            super(new Object2BooleanOpenHashMap<>(0));
        }

        public Builder defaultValue(EntityType<?>... defaults) {
            return defaultValue(defaults != null ? Utils.asO2BMap(defaults) : new Object2BooleanOpenHashMap<>(0));
        }

        public Builder onlyAttackable() {
            onlyAttackable = true;
            return this;
        }

        @Override
        public EntityTypeListSetting build() {
            return new EntityTypeListSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible, onlyAttackable);
        }
    }
}
