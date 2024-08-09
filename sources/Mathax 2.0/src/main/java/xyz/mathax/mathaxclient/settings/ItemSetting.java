package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONObject;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ItemSetting extends Setting<Item> {
    public final Predicate<Item> filter;

    public ItemSetting(String name, String description, Item defaultValue, Consumer<Item> onChanged, Consumer<Setting<Item>> onModuleEnabled, IVisible visible, Predicate<Item> filter) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);

        this.filter = filter;
    }

    @Override
    protected Item parseImpl(String string) {
        return parseId(Registries.ITEM, string);
    }

    @Override
    protected boolean isValueValid(Item value) {
        return filter == null || filter.test(value);
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registries.ITEM.getIds();
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", Registries.ITEM.getId(get()).toString());

        return json;
    }

    @Override
    public Item load(JSONObject json) {
        if (json.has("value")) {
            value = Registries.ITEM.get(new Identifier(json.getString("value")));
            if (filter != null && !filter.test(value)) {
                for (Item item : Registries.ITEM) {
                    if (filter.test(item)) {
                        value = item;
                        break;
                    }
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, Item, ItemSetting> {
        private Predicate<Item> filter;

        public Builder() {
            super(null);
        }

        public Builder filter(Predicate<Item> filter) {
            this.filter = filter;
            return this;
        }

        @Override
        public ItemSetting build() {
            return new ItemSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible, filter);
        }
    }
}
