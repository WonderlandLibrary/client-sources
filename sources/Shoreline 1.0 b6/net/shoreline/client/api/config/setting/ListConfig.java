package net.shoreline.client.api.config.setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.util.ClientIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @author linus
 * @since 1.0
 */
public class ListConfig<T extends List<?>> extends Config<T> {
    /**
     * Initializes the config with a default value. This constructor should
     * not be used to initialize a configuration, instead use the explicit
     * definitions of the configs in {@link com.caspian.client.api.config.setting}.
     *
     * @param name   The unique config identifier
     * @param desc   The config description
     * @param values The default config values
     * @throws NullPointerException if value is <tt>null</tt>
     */
    @SuppressWarnings("unchecked")
    public ListConfig(String name, String desc, Object... values) {
        super(name, desc, (T) List.of(values));
    }

    /**
     * @param obj
     * @return
     */
    public boolean contains(Object obj) {
        return value.contains(obj);
    }

    /**
     * Converts all data in the object to a {@link JsonObject}.
     *
     * @return The data as a json object
     */
    @Override
    public JsonObject toJson() {
        JsonObject jsonObj = super.toJson();
        JsonArray array = new JsonArray();
        for (Object element : getValue()) {
            if (element instanceof Block block) {
                Identifier id = Registries.BLOCK.getId(block);
                array.add(String.format("block:%s", id.getPath()));
            } else if (element instanceof Item item) {
                Identifier id = Registries.ITEM.getId(item);
                array.add(String.format("item:%s", id.getPath()));
            }
        }
        jsonObj.add("value", array);
        return jsonObj;
    }

    /**
     * Reads all data from a {@link JsonObject} and updates the values of the
     * data in the object.
     *
     * @param jsonObj The data as a json object
     * @return
     * @see #toJson()
     */
    @Override
    public T fromJson(JsonObject jsonObj) {
        if (jsonObj.has("value")) {
            JsonElement element = jsonObj.get("value");
            List<?> temp = null;
            for (JsonElement je : element.getAsJsonArray()) {
                String val = je.getAsString();
                if (val.contains("block")) {
                    if (temp == null) {
                        temp = new ArrayList<Block>();
                    }
                    Block block = Registries.BLOCK.get(ClientIdentifier.toId(val));
                    ((List<Block>) temp).add(block);
                } else if (val.contains("item")) {
                    if (temp == null) {
                        temp = new ArrayList<Item>();
                    }
                    Item item = Registries.ITEM.get(ClientIdentifier.toId(val));
                    ((List<Item>) temp).add(item);
                }
            }
            return (T) temp;
        }
        return null;
    }
}
