/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.data.BlockModeInfo;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockModelDefinition
implements Supplier<JsonElement> {
    private final Map<BlockModeInfo<?>, BlockModeInfo.Field> infoToInfoFieldMap = Maps.newLinkedHashMap();

    public <T> BlockModelDefinition replaceInfoValue(BlockModeInfo<T> blockModeInfo, T t) {
        BlockModeInfo.Field field = this.infoToInfoFieldMap.put(blockModeInfo, blockModeInfo.getFieldInfo(t));
        if (field != null) {
            throw new IllegalStateException("Replacing value of " + field + " with " + t);
        }
        return this;
    }

    public static BlockModelDefinition getNewModelDefinition() {
        return new BlockModelDefinition();
    }

    public static BlockModelDefinition mergeDefinitions(BlockModelDefinition blockModelDefinition, BlockModelDefinition blockModelDefinition2) {
        BlockModelDefinition blockModelDefinition3 = new BlockModelDefinition();
        blockModelDefinition3.infoToInfoFieldMap.putAll(blockModelDefinition.infoToInfoFieldMap);
        blockModelDefinition3.infoToInfoFieldMap.putAll(blockModelDefinition2.infoToInfoFieldMap);
        return blockModelDefinition3;
    }

    @Override
    public JsonElement get() {
        JsonObject jsonObject = new JsonObject();
        this.infoToInfoFieldMap.values().forEach(arg_0 -> BlockModelDefinition.lambda$get$0(jsonObject, arg_0));
        return jsonObject;
    }

    public static JsonElement serialize(List<BlockModelDefinition> list) {
        if (list.size() == 1) {
            return list.get(0).get();
        }
        JsonArray jsonArray = new JsonArray();
        list.forEach(arg_0 -> BlockModelDefinition.lambda$serialize$1(jsonArray, arg_0));
        return jsonArray;
    }

    @Override
    public Object get() {
        return this.get();
    }

    private static void lambda$serialize$1(JsonArray jsonArray, BlockModelDefinition blockModelDefinition) {
        jsonArray.add(blockModelDefinition.get());
    }

    private static void lambda$get$0(JsonObject jsonObject, BlockModeInfo.Field field) {
        field.serialize(jsonObject);
    }
}

