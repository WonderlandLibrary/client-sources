package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.IChangeable;
import xyz.mathax.mathaxclient.utils.misc.ICopyable;
import xyz.mathax.mathaxclient.utils.misc.IGetter;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.settings.IBlockData;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BlockDataSetting<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> extends Setting<Map<Block, T>> {
    public final IGetter<T> defaultData;

    public BlockDataSetting(String name, String description, Map<Block, T> defaultValue, Consumer<Map<Block, T>> onChanged, Consumer<Setting<Map<Block, T>>> onModuleEnabled, IGetter<T> defaultData, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);

        this.defaultData = defaultData;
    }

    @Override
    public void resetImpl() {
        value = new HashMap<>(defaultValue);
    }

    @Override
    protected Map<Block, T> parseImpl(String string) {
        return new HashMap<>(0);
    }

    @Override
    protected boolean isValueValid(Map<Block, T> value) {
        return true;
    }

    @Override
    protected JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        for (Block block : get().keySet()) {
            JSONObject valueJson = new JSONObject();
            valueJson.put("id", Registries.BLOCK.getId(block).toString());
            valueJson.put("key", get().get(block).toJson());

            json.append("value", valueJson);
        }

        return json;
    }

    @Override
    protected Map<Block, T> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof JSONObject valueJson) {
                    if (valueJson.has("id") && valueJson.has("key")) {
                        get().put(Registries.BLOCK.get(new Identifier(valueJson.getString("id"))), defaultData.get().copy().fromJson(valueJson.getJSONObject("key")));
                    }
                }
            }
        }

        return get();
    }

    public static class Builder<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> extends SettingBuilder<Builder<T>, Map<Block, T>, BlockDataSetting<T>> {
        private IGetter<T> defaultData;

        public Builder() {
            super(new HashMap<>(0));
        }

        public Builder<T> defaultData(IGetter<T> defaultData) {
            this.defaultData = defaultData;
            return this;
        }

        @Override
        public BlockDataSetting<T> build() {
            return new BlockDataSetting<>(name, description, defaultValue, onChanged, onModuleEnabled, defaultData, visible);
        }
    }
}
