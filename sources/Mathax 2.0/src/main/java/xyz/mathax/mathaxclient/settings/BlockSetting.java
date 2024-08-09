package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONObject;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class BlockSetting extends Setting<Block> {
    public final Predicate<Block> filter;

    public BlockSetting(String name, String description, Block defaultValue, Consumer<Block> onChanged, Consumer<Setting<Block>> onModuleEnabled, IVisible visible, Predicate<Block> filter) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);

        this.filter = filter;
    }

    @Override
    protected Block parseImpl(String string) {
        return parseId(Registries.BLOCK, string);
    }

    @Override
    protected boolean isValueValid(Block value) {
        return filter == null || filter.test(value);
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.BLOCK.getIds();
    }

    @Override
    protected JSONObject save(JSONObject json) {
        json.put("value", Registries.BLOCK.getId(get()).toString());

        return json;
    }

    @Override
    protected Block load(JSONObject json) {
        if (json.has("value")) {
            value = Registries.BLOCK.get(new Identifier(json.getString("value")));

            if (filter != null && !filter.test(value)) {
                for (Block block : Registries.BLOCK) {
                    if (filter.test(block)) {
                        value = block;
                        break;
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, Block, BlockSetting> {
        private Predicate<Block> filter;

        public Builder() {
            super(null);
        }

        public Builder filter(Predicate<Block> filter) {
            this.filter = filter;
            return this;
        }

        @Override
        public BlockSetting build() {
            return new BlockSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible, filter);
        }
    }
}
