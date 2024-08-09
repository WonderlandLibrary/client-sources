package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BlockListSetting extends Setting<List<Block>> {
    public final Predicate<Block> filter;

    public BlockListSetting(String name, String description, List<Block> defaultValue, Consumer<List<Block>> onChanged, Consumer<Setting<List<Block>>> onModuleEnabled, Predicate<Block> filter, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);

        this.filter = filter;
    }

    @Override
    public void resetImpl() {
        value = new ArrayList<>(defaultValue);
    }

    @Override
    protected List<Block> parseImpl(String string) {
        String[] values = string.split(",");
        List<Block> blocks = new ArrayList<>(values.length);

        try {
            for (String value : values) {
                Block block = parseId(Registries.BLOCK, value);
                if (block != null && (filter == null || filter.test(block))) {
                    blocks.add(block);
                }
            }
        } catch (Exception ignored) {}

        return blocks;
    }

    @Override
    protected boolean isValueValid(List<Block> value) {
        return true;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.BLOCK.getIds();
    }

    @Override
    protected JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (Block block : get()) {
            json.append("value", Registries.BLOCK.getId(block).toString());
        }

        return json;
    }

    @Override
    protected List<Block> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof String id) {
                    Block block = Registries.BLOCK.get(new Identifier(id));
                    if (filter == null || filter.test(block)) {
                        get().add(block);
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, List<Block>, BlockListSetting> {
        private Predicate<Block> filter;

        public Builder() {
            super(new ArrayList<>(0));
        }

        public Builder defaultValue(Block... defaults) {
            return defaultValue(defaults != null ? Arrays.asList(defaults) : new ArrayList<>());
        }

        public Builder filter(Predicate<Block> filter) {
            this.filter = filter;
            return this;
        }

        @Override
        public BlockListSetting build() {
            return new BlockListSetting(name, description, defaultValue, onChanged, onModuleEnabled, filter, visible);
        }
    }
}
