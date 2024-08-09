package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.util.math.BlockPos;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;

public class BlockPosSetting extends Setting<BlockPos> {
    public BlockPosSetting(String name, String description, BlockPos defaultValue, Consumer<BlockPos> onChanged, Consumer<Setting<BlockPos>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    protected BlockPos parseImpl(String string) {
        List<String> values = List.of(string.split(","));
        if (values.size() != 3) {
            return null;
        }

        BlockPos blockPos = null;
        try {
            blockPos = new BlockPos(Integer.parseInt(values.get(0)), Integer.parseInt(values.get(1)), Integer.parseInt(values.get(2)));
        } catch (NumberFormatException ignored) {}

        return blockPos;
    }

    @Override
    protected boolean isValueValid(BlockPos value) {
        return true;
    }

    @Override
    protected JSONObject save(JSONObject json) {
        JSONObject valueJson = new JSONObject();
        valueJson.put("x", get().getX());
        valueJson.put("y", get().getY());
        valueJson.put("z", get().getZ());

        json.put("value", valueJson);

        return json;
    }

    @Override
    protected BlockPos load(JSONObject json) {
        if (json.has("value")) {
            JSONObject valueJson = json.getJSONObject("value");
            if (valueJson.has("x") && valueJson.has("y") && valueJson.has("z")) {
                set(new BlockPos(valueJson.getInt("x"), valueJson.getInt("y"), valueJson.getInt("z")));
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, BlockPos, BlockPosSetting> {
        public Builder() {
            super(new BlockPos(0, 0, 0));
        }

        @Override
        public BlockPosSetting build() {
            return new BlockPosSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}
