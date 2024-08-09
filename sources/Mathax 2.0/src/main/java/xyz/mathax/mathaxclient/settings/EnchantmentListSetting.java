package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EnchantmentListSetting extends Setting<List<Enchantment>> {
    public EnchantmentListSetting(String name, String description, List<Enchantment> defaultValue, Consumer<List<Enchantment>> onChanged, Consumer<Setting<List<Enchantment>>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    public void resetImpl() {
        value = new ArrayList<>(defaultValue);
    }

    @Override
    protected List<Enchantment> parseImpl(String string) {
        String[] values = string.split(",");
        List<Enchantment> enchantments = new ArrayList<>(values.length);

        try {
            for (String value : values) {
                Enchantment enchantment = parseId(Registries.ENCHANTMENT, value);
                if (enchantment != null) {
                    enchantments.add(enchantment);
                }
            }
        } catch (Exception ignored) {}

        return enchantments;
    }

    @Override
    protected boolean isValueValid(List<Enchantment> value) {
        return true;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.ENCHANTMENT.getIds();
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (Enchantment enchantment : get()) {
            Identifier id = Registries.ENCHANTMENT.getId(enchantment);
            if (id != null) {
                json.append("value", id.toString());
            }
        }

        return json;
    }

    @Override
    public List<Enchantment> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String id) {
                    Enchantment enchantment = Registries.ENCHANTMENT.get(new Identifier(id));
                    if (enchantment != null) {
                        get().add(enchantment);
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, List<Enchantment>, EnchantmentListSetting> {
        public Builder() {
            super(new ArrayList<>(0));
        }

        public Builder defaultValue(Enchantment... defaults) {
            return defaultValue(defaults != null ? Arrays.asList(defaults) : new ArrayList<>());
        }

        @Override
        public EnchantmentListSetting build() {
            return new EnchantmentListSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}
